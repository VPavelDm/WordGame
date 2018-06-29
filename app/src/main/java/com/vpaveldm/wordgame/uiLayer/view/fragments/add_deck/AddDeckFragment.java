package com.vpaveldm.wordgame.uiLayer.view.fragments.add_deck;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vpaveldm.wordgame.R;
import com.vpaveldm.wordgame.dataLayer.store.model.Card;
import com.vpaveldm.wordgame.databinding.FragmentAddDeckBinding;
import com.vpaveldm.wordgame.uiLayer.view.activity.ActivityComponentManager;
import com.vpaveldm.wordgame.adapterLayer.viewModel.AddDeckViewModel;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;
import ru.terrakok.cicerone.Router;

public class AddDeckFragment extends Fragment {

    public static final int MINIMUM_CARDS = 10;
    @Inject
    Router mRouter;

    private FragmentAddDeckBinding mBinding;
    private AddDeckViewModel mAddDeckViewModel;
    private int currentWrongTranslate = 1;
    private Card mCard;
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityComponentManager.getActivityComponent().inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAddDeckViewModel = ViewModelProviders.of(this).get(AddDeckViewModel.class);
        mAddDeckViewModel.subscribe(this, dataMessage -> {
            if (Objects.requireNonNull(dataMessage).isSuccess()) {
                mRouter.exit();
            } else {
                Toast.makeText(getContext(),
                        dataMessage.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        }, translate -> {
            if (Objects.requireNonNull(translate).isSuccess()) {
                mBinding.translateET.setText(translate.getMessage());
            } else {
                Toast.makeText(getContext(),
                        translate.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentAddDeckBinding.inflate(inflater, container, false);
        ButterKnife.bind(this, mBinding.getRoot());

        mCard = new Card();

        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshCardWidgets();
    }

    @Override
    public void onStop() {
        super.onStop();
        mCompositeDisposable.clear();
    }

    @OnClick(R.id.addWrongTranslate)
    void clickAddWrongTranslateButton() {
        if (currentWrongTranslate == 5) {
            Toast.makeText(getContext(), R.string.label_add_wrong_translate_error, Toast.LENGTH_LONG).show();
            return;
        }
        if (mBinding.wrongTranslateET.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Entry text in wrong translate field", Toast.LENGTH_LONG).show();
            return;
        }
        mCard.wrongTranslates.add(mBinding.wrongTranslateET.getText().toString());
        //increase currentWrongTranslate
        mBinding.wrongTranslateET.setText("");
        mBinding.countTranslatesTV.setText(getString(R.string.label_count_translates, currentWrongTranslate));
        currentWrongTranslate++;
    }

    @OnClick(R.id.createCardButton)
    void clickCreateButton() {
        if (mBinding.wordET.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Entry word", Toast.LENGTH_LONG).show();
            return;
        }
        if (mBinding.translateET.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Entry translate", Toast.LENGTH_LONG).show();
            return;
        }
        if (mCard.wrongTranslates.size() != 4) {
            Toast.makeText(getContext(), "Entry wrong translates", Toast.LENGTH_LONG).show();
            return;
        }
        mCard.word = mBinding.wordET.getText().toString();
        mCard.translate = mBinding.translateET.getText().toString();
        mAddDeckViewModel.addCard(mCard);
        currentWrongTranslate = 1;
        refreshCardWidgets();
        mCard = new Card();
    }

    @OnClick(R.id.createDeckButton)
    void clickCreateDeckBtn() {
        if (mBinding.deckNameET.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Entry deck name", Toast.LENGTH_LONG).show();
            return;
        }
        if (mAddDeckViewModel.getCardSize() < MINIMUM_CARDS) {
            Toast.makeText(getContext(), "Add at least 10 words", Toast.LENGTH_LONG).show();
            return;
        }
        mCompositeDisposable.add(mAddDeckViewModel.createDeck(mBinding.deckNameET.getText().toString()));
    }

    @OnClick(R.id.autoTranslateBtn)
    void clickAutoTranslateBtn() {
        mCompositeDisposable.add(mAddDeckViewModel.getAutoTranslate(mBinding.wordET.getText().toString()));
    }

    private void refreshCardWidgets() {
        mBinding.countTranslatesTV.setText(getString(R.string.label_count_translates, currentWrongTranslate - 1));
        mBinding.countCardsTV.setText(getString(R.string.label_count_cards, mAddDeckViewModel.getCardSize()));
        mBinding.wordET.setText("");
        mBinding.translateET.setText("");
        mBinding.wrongTranslateET.setText("");
    }
}
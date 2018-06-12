package com.vpaveldm.wordgame.presentationLayer.view.fragments.add_deck;

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
import com.vpaveldm.wordgame.dataLayer.model.Card;
import com.vpaveldm.wordgame.databinding.FragmentAddDeckBinding;
import com.vpaveldm.wordgame.presentationLayer.view.activity.ActivityComponentManager;
import com.vpaveldm.wordgame.presentationLayer.viewModel.AddDeckViewModel;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.terrakok.cicerone.Router;

public class AddDeckFragment extends Fragment {

    @Inject
    Router mRouter;

    private FragmentAddDeckBinding mBinding;
    private AddDeckViewModel mAddDeckViewModel;
    private int currentWrongTranslate = 1;
    private Card mCard;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityComponentManager.getActivityComponent().inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAddDeckViewModel = ViewModelProviders.of(this).get(AddDeckViewModel.class);
        mAddDeckViewModel.subscribeOnMessageLiveData(this, dataMessage -> {
            if (dataMessage == null) {
                return;
            }
            if (dataMessage.isSuccess()) {
                mRouter.exit();
            } else {
                Toast.makeText(getContext(),
                        dataMessage.getMessage(),
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
        refreshCardWidgets();

        return mBinding.getRoot();
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
        mCard.addWrongTranslate(mBinding.wrongTranslateET.getText().toString());
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
        if (mCard.getWrongTranslates().size() != 4) {
            Toast.makeText(getContext(), "Entry wrong translates", Toast.LENGTH_LONG).show();
            return;
        }
        mCard.setWord(mBinding.wordET.getText().toString());
        mCard.setTranslate(mBinding.translateET.getText().toString());
        mAddDeckViewModel.addCard(mCard);
        currentWrongTranslate = 1;
        refreshCardWidgets();
    }

    @OnClick(R.id.createDeckButton)
    void clickCreateDeckBtn() {
        if (mBinding.deckNameET.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Entry deck name", Toast.LENGTH_LONG).show();
            return;
        }
        if (mAddDeckViewModel.getCardSize() < 1) {
            Toast.makeText(getContext(), "Add at least 10 words", Toast.LENGTH_LONG).show();
            return;
        }
        mAddDeckViewModel.createDeck(mBinding.deckNameET.getText().toString());
    }

    private void refreshCardWidgets() {
        mBinding.countTranslatesTV.setText(getString(R.string.label_count_translates, currentWrongTranslate - 1));
        mBinding.wordET.setText("");
        mBinding.translateET.setText("");
        mBinding.wrongTranslateET.setText("");
    }
}

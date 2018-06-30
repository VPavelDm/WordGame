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

import com.vpaveldm.wordgame.adapterLayer.viewModel.AddDeckViewModel;
import com.vpaveldm.wordgame.databinding.FragmentAddDeckBinding;
import com.vpaveldm.wordgame.uiLayer.view.activity.ActivityComponentManager;

import java.util.Objects;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

/**
 * @author Pavel Vaitsikhouski
 */
public class AddDeckFragment extends Fragment {

    @Inject
    Router mRouter;

    private FragmentAddDeckBinding mBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityComponentManager.getActivityComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentAddDeckBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AddDeckViewModel addDeckViewModel = ViewModelProviders.of(this).get(AddDeckViewModel.class);
        addDeckViewModel.subscribe(this, dataMessage -> {
            if (Objects.requireNonNull(dataMessage).isSuccess()) {
                mRouter.exit();
            } else {
                Toast.makeText(getContext(),
                        dataMessage.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
        mBinding.setViewmodel(addDeckViewModel);
        mBinding.setContext(Objects.requireNonNull(getContext()).getApplicationContext());
    }
}

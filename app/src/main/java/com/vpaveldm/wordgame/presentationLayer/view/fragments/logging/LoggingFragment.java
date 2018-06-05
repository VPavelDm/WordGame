package com.vpaveldm.wordgame.presentationLayer.view.fragments.logging;

import android.arch.lifecycle.Observer;
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
import com.vpaveldm.wordgame.databinding.FragmentLoggingBinding;
import com.vpaveldm.wordgame.presentationLayer.viewModel.LoggingViewModel;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.terrakok.cicerone.Router;

/**
 * Class representing the logging UI
 *
 * @author Pavel Vaitsikhouski
 */
public class LoggingFragment extends Fragment implements Observer<Boolean> {

    @Inject
    Router mRouter;

    private FragmentLoggingBinding mBinding;
    private LoggingViewModel loggingViewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loggingViewModel = ViewModelProviders.of(this).get(LoggingViewModel.class);
        loggingViewModel.init();
        loggingViewModel.getModelLiveData().observe(this, this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getLifecycle().addObserver(new LoggingComponentManager(this));
        mBinding = FragmentLoggingBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.registerButton)
    void clickRegisterButton() {
        String email = mBinding.emailET.getText().toString();
        String password = mBinding.passwordET.getText().toString();
        loggingViewModel.signUp(email, password);
    }

    @OnClick(R.id.loginButton)
    void clickLoginButton() {
        String email = mBinding.emailET.getText().toString();
        String password = mBinding.passwordET.getText().toString();
        loggingViewModel.signIn(email, password);
    }

    @Override
    public void onChanged(@Nullable Boolean isSuccess) {
        if (isSuccess == null) {
            return;
        }
        if (isSuccess) {
            mRouter.replaceScreen(getString(R.string.fragment_menu));
        } else {
            Toast.makeText(getContext(),
                    getString(R.string.label_no_user_found),
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}

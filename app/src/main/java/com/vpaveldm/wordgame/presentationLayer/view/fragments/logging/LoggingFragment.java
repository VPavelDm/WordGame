package com.vpaveldm.wordgame.presentationLayer.view.fragments.logging;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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
import com.vpaveldm.wordgame.presentationLayer.view.activity.ActivityComponentManager;
import com.vpaveldm.wordgame.presentationLayer.viewModel.LoggingViewModel;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ru.terrakok.cicerone.Router;

/**
 * Class representing the logging UI
 *
 * @author Pavel Vaitsikhouski
 */
public class LoggingFragment extends Fragment {

    public static final int RC_GOOGLE_LOGIN = 1;
    @Inject
    Router mRouter;

    private FragmentLoggingBinding mBinding;
    private LoggingViewModel loggingViewModel;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityComponentManager.getActivityComponent().inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loggingViewModel = ViewModelProviders.of(this).get(LoggingViewModel.class);
        loggingViewModel.subscribeOnMessageLiveData(this, dataMessage -> {
            if (dataMessage == null) {
                return;
            }
            mCompositeDisposable.clear();
            if (dataMessage.isSuccess()) {
                mRouter.replaceScreen(getString(R.string.fragment_menu));
            } else {
                Toast.makeText(getContext(),
                        dataMessage.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
        loggingViewModel.subscribeOnIntentLiveData(this, intent -> {
            mCompositeDisposable.clear();
            startActivityForResult(intent, RC_GOOGLE_LOGIN);
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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

    @OnClick(R.id.googleLoginButton)
    void clickGoogleLoginButton() {
        mCompositeDisposable.add(loggingViewModel.getIntentForGoogle());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RC_GOOGLE_LOGIN: {
                loggingViewModel.signInByGoogle(data);
                break;
            }
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

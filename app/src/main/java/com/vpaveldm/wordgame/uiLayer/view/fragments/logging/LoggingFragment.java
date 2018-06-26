package com.vpaveldm.wordgame.uiLayer.view.fragments.logging;

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
import com.vpaveldm.wordgame.uiLayer.view.activity.ActivityComponentManager;
import com.vpaveldm.wordgame.adapterLayer.viewModel.LoggingViewModel;

import java.util.Objects;

import javax.inject.Inject;

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

    private LoggingViewModel loggingViewModel;
    private FragmentLoggingBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityComponentManager.getActivityComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoggingBinding.inflate(inflater, container, false);
        binding.setHandler(loggingViewModel);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loggingViewModel = ViewModelProviders.of(this).get(LoggingViewModel.class);
        loggingViewModel.subscribe(this, dataMessage -> {
            if (Objects.requireNonNull(dataMessage).isSuccess()) {
                mRouter.replaceScreen(getString(R.string.fragment_menu));
            } else {
                Toast.makeText(getContext(),
                        dataMessage.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        }, intent -> startActivityForResult(intent, RC_GOOGLE_LOGIN));
        binding.setHandler(loggingViewModel);
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

package com.vpaveldm.wordgame.presentationLayer.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.vpaveldm.wordgame.errors.IErrorListener;
import com.vpaveldm.wordgame.domainLayer.interactors.LoggingInteractor;
import com.vpaveldm.wordgame.presentationLayer.model.LoggingModelInPresentationLayer;
import com.vpaveldm.wordgame.presentationLayer.transform.PresentationLayerTransformer;
import com.vpaveldm.wordgame.presentationLayer.view.activity.ActivityComponentManager;

import javax.inject.Inject;

public class LoggingViewModel extends ViewModel implements IErrorListener {

    @Inject
    PresentationLayerTransformer mTransformer;
    @Inject
    LoggingInteractor mLoggingInteractor;

    private MutableLiveData<LiveDataMessage> mModelLiveData;

    public void init() {
        ActivityComponentManager.getActivityComponent().inject(this);
    }

    public LiveData<LiveDataMessage> getModelLiveData() {
        if (mModelLiveData == null) {
            mModelLiveData = new MutableLiveData<>();
        }
        return mModelLiveData;
    }

    public void signIn(String email, String password) {
        LoggingModelInPresentationLayer model =
                mTransformer.getLoggingModelInPresentationLayer(email, password);
        mLoggingInteractor.signIn(model, this);
    }

    public void signUp(String email, String password) {
        LoggingModelInPresentationLayer model =
                mTransformer.getLoggingModelInPresentationLayer(email, password);
        mLoggingInteractor.signUp(model, this);
    }

    @Override
    public void success() {
        LiveDataMessage dataMessage = new LiveDataMessage(true, null);
        mModelLiveData.setValue(dataMessage);
    }

    @Override
    public void failure(String message) {
        LiveDataMessage dataMessage = new LiveDataMessage(false, message);
        mModelLiveData.setValue(dataMessage);
    }
}

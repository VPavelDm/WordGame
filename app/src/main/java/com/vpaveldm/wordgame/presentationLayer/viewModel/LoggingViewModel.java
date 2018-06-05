package com.vpaveldm.wordgame.presentationLayer.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.vpaveldm.wordgame.errors.IErrorListener;
import com.vpaveldm.wordgame.domainLayer.interactors.LoggingInteractor;
import com.vpaveldm.wordgame.presentationLayer.model.LoggingModelInPresentationLayer;
import com.vpaveldm.wordgame.presentationLayer.transform.PresentationLayerTransformer;
import com.vpaveldm.wordgame.presentationLayer.view.activity.ActivityComponentManager;
import com.vpaveldm.wordgame.presentationLayer.view.fragments.logging.LoggingComponentManager;

import javax.inject.Inject;

public class LoggingViewModel extends ViewModel implements IErrorListener {

    @Inject
    PresentationLayerTransformer mModelTransformer;
    @Inject
    LoggingInteractor mLoggingInteractor;

    private MutableLiveData<Boolean> mModelLiveData;

    public void init(){
        ActivityComponentManager.getActivityComponent().inject(this);
    }

    public LiveData<Boolean> getModelLiveData() {
        if (mModelLiveData == null) {
            mModelLiveData = new MutableLiveData<>();
        }
        return mModelLiveData;
    }

    public void signIn(String email, String password) {
        LoggingModelInPresentationLayer model =
                mModelTransformer.getLoggingModelInPresentationLayer(email, password);
        mLoggingInteractor.signIn(model, this);
    }

    public void signUp(String email, String password) {

    }

    @Override
    public void success() {
        mModelLiveData.setValue(true);
    }

    @Override
    public void failure() {
        mModelLiveData.setValue(false);
    }
}

package com.vpaveldm.wordgame.presentationLayer.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;

import com.vpaveldm.wordgame.domainLayer.interactors.LoggingInteractor;
import com.vpaveldm.wordgame.presentationLayer.model.LoggingModelInPresentationLayer;
import com.vpaveldm.wordgame.presentationLayer.transform.PresentationLayerTransformer;
import com.vpaveldm.wordgame.presentationLayer.view.activity.ActivityComponentManager;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoggingViewModel extends ViewModel {

    @Inject
    PresentationLayerTransformer mTransformer;
    @Inject
    LoggingInteractor mLoggingInteractor;

    private static MutableLiveData<LiveDataMessage> sLiveData;

    public LiveData<LiveDataMessage> getModelLiveData() {
        ActivityComponentManager.getActivityComponent().inject(this);
        if (sLiveData == null) {
            sLiveData = new MutableLiveData<>();
        }
        return sLiveData;
    }

    public Disposable signIn(String email, String password) {
        LoggingModelInPresentationLayer model =
                mTransformer.getLoggingModel(email, password);
        Single<Boolean> subject = mLoggingInteractor.signIn(model);
        return subject.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item -> sLiveData.setValue(new LiveDataMessage(true, null)),
                        throwable -> sLiveData.setValue(new LiveDataMessage(false, throwable.getMessage()))
                );
    }

    public Disposable signUp(String email, String password) {
        LoggingModelInPresentationLayer model =
                mTransformer.getLoggingModel(email, password);
        Single<Boolean> subject = mLoggingInteractor.signUp(model);
        return subject.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item -> sLiveData.setValue(new LiveDataMessage(true, null)),
                        throwable -> sLiveData.setValue(new LiveDataMessage(false, throwable.getMessage()))
                );
    }

    public Intent getIntentForGoogle() {
        LoggingModelInPresentationLayer model = mTransformer.getLoggingModel();
        return null;
    }

    public void signInByGoogle(Intent data) {

    }
}

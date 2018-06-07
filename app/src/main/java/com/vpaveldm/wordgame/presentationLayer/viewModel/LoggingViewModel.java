package com.vpaveldm.wordgame.presentationLayer.viewModel;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;

import com.vpaveldm.wordgame.domainLayer.interactors.LoggingInteractor;
import com.vpaveldm.wordgame.presentationLayer.model.LoggingModelInPresentationLayer;
import com.vpaveldm.wordgame.presentationLayer.model.transform.PresentationLayerTransformer;
import com.vpaveldm.wordgame.presentationLayer.view.activity.ActivityComponentManager;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoggingViewModel extends ViewModel {

    @Inject
    PresentationLayerTransformer mTransformer;
    @Inject
    LoggingInteractor mLoggingInteractor;

    private MutableLiveData<LiveDataMessage> mMessageLiveData;
    private MutableLiveData<Intent> mIntentLiveData;

    @SuppressWarnings("WeakerAccess")
    public LoggingViewModel() {
        super();
        ActivityComponentManager.getActivityComponent().inject(this);
    }

    public void subscribeOnMessageLiveData(LifecycleOwner owner, Observer<LiveDataMessage> listener) {
        if (mMessageLiveData == null) {
            mMessageLiveData = new MutableLiveData<>();
            mMessageLiveData.observe(owner, listener);
        }
    }

    public void subscribeOnIntentLiveData(LifecycleOwner owner, Observer<Intent> listener) {
        if (mIntentLiveData == null) {
            mIntentLiveData = new MutableLiveData<>();
            mIntentLiveData.observe(owner, listener);
        }
    }

    public void signIn(String email, String password) {
        LoggingModelInPresentationLayer.Builder builder = new LoggingModelInPresentationLayer.Builder();
        builder.addEmail(email)
                .addPassword(password);
        Completable subject = mLoggingInteractor.signIn(builder.create());
        subject.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        mMessageLiveData.setValue(new LiveDataMessage(true, null));
                    }

                    @Override
                    public void onError(Throwable e) {
                        mMessageLiveData.setValue(new LiveDataMessage(false, e.getMessage()));
                    }
                });
    }

    public void signUp(String email, String password) {
        LoggingModelInPresentationLayer.Builder builder = new LoggingModelInPresentationLayer.Builder();
        builder.addEmail(email)
                .addPassword(password);
        Completable subject = mLoggingInteractor.signUp(builder.create());
        subject.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        mMessageLiveData.setValue(new LiveDataMessage(true, null));
                    }

                    @Override
                    public void onError(Throwable e) {
                        mMessageLiveData.setValue(new LiveDataMessage(false, e.getMessage()));
                    }
                });
    }

    public Disposable getIntentForGoogle() {
        return mLoggingInteractor.getGoogleIntent().
                subscribe(item -> mIntentLiveData.setValue(mTransformer.transform(item).getData()));
    }

    public void signInByGoogle(Intent data) {
        LoggingModelInPresentationLayer.Builder builder = new LoggingModelInPresentationLayer.Builder();
        builder.addData(data);
        Completable subject = mLoggingInteractor.signIn(builder.create());
        subject.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        mMessageLiveData.setValue(new LiveDataMessage(true, null));
                    }

                    @Override
                    public void onError(Throwable e) {
                        mMessageLiveData.setValue(new LiveDataMessage(false, e.getMessage()));
                    }
                });
    }
}

package com.vpaveldm.wordgame.presentationLayer.viewModel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;

import com.vpaveldm.wordgame.dataLayer.model.LoggingModel;
import com.vpaveldm.wordgame.domainLayer.interactors.LoggingInteractor;
import com.vpaveldm.wordgame.presentationLayer.view.activity.ActivityComponentManager;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class LoggingViewModel extends ViewModel {

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
        }
        mMessageLiveData.observe(owner, listener);
    }

    public void subscribeOnIntentLiveData(LifecycleOwner owner, Observer<Intent> listener) {
        if (mIntentLiveData == null) {
            mIntentLiveData = new MutableLiveData<>();
            mIntentLiveData.observe(owner, listener);
        }
    }

    public void signIn(String email, String password) {
        LoggingModel.Builder builder = new LoggingModel.Builder();
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

    @SuppressLint("CheckResult")
    public void signUp(String email, String password) {
        LoggingModel.Builder builder = new LoggingModel.Builder();
        builder.addEmail(email)
                .addPassword(password);
        Completable subject = mLoggingInteractor.signUp(builder.create());
        subject.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> mMessageLiveData.setValue(new LiveDataMessage(true, null)),
                        e -> mMessageLiveData.setValue(new LiveDataMessage(false, e.getMessage()))
                );
    }

    @SuppressLint("CheckResult")
    public void getIntentForGoogle() {
        mLoggingInteractor.getGoogleIntent()
                .subscribe(item -> mIntentLiveData.setValue(item.getData()));
    }

    @SuppressLint("CheckResult")
    public void signInByGoogle(Intent data) {
        LoggingModel.Builder builder = new LoggingModel.Builder();
        builder.addData(data);
        Completable subject = mLoggingInteractor.signIn(builder.create());
        subject.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> mMessageLiveData.setValue(new LiveDataMessage(true, null)),
                        e -> mMessageLiveData.setValue(new LiveDataMessage(false, e.getMessage()))
                );
    }
}

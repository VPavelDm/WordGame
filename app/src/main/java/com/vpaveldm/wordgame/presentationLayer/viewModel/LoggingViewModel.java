package com.vpaveldm.wordgame.presentationLayer.viewModel;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;

import com.vpaveldm.wordgame.dataLayer.store.model.LoggingModel;
import com.vpaveldm.wordgame.domainLayer.interactors.LoggingInteractor;
import com.vpaveldm.wordgame.presentationLayer.view.activity.ActivityComponentManager;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoggingViewModel extends ViewModel {

    @Inject
    LoggingInteractor mLoggingInteractor;

    private MutableLiveData<LiveDataMessage> mMessageLiveData;
    private MutableLiveData<Intent> mIntentLiveData;

    public LoggingViewModel() {
        super();
        ActivityComponentManager.getActivityComponent().inject(this);
    }

    public void subscribe(LifecycleOwner owner, Observer<LiveDataMessage> messageListener, Observer<Intent> intentListener) {
        if (mMessageLiveData == null) {
            mMessageLiveData = new MutableLiveData<>();
        }
        if (mIntentLiveData == null) {
            mIntentLiveData = new MutableLiveData<>();
        }
        mMessageLiveData.observe(owner, messageListener);
        mIntentLiveData.observe(owner, intentListener);
    }

    public Disposable signIn(String email, String password) {
        LoggingModel.Builder builder = new LoggingModel.Builder();
        builder.addEmail(email)
                .addPassword(password);
        Observable<Boolean> subject = mLoggingInteractor.signIn(builder.create());
        return subject
                .filter(isConnected -> isConnected)
                .subscribe(
                        isConnected -> mMessageLiveData.setValue(new LiveDataMessage(true, null)),
                        e -> mMessageLiveData.setValue(new LiveDataMessage(false, e.getMessage()))
                );
    }

    public Disposable signUp(String email, String password) {
        LoggingModel.Builder builder = new LoggingModel.Builder();
        builder.addEmail(email)
                .addPassword(password);
        Observable<Boolean> subject = mLoggingInteractor.signUp(builder.create());
        return subject.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(isConnected -> isConnected)
                .subscribe(
                        isConnected -> mMessageLiveData.setValue(new LiveDataMessage(true, null)),
                        e -> mMessageLiveData.setValue(new LiveDataMessage(false, e.getMessage()))
                );
    }

    public Disposable getIntentForGoogle() {
        return mLoggingInteractor.getGoogleIntent()
                .subscribe(item -> mIntentLiveData.setValue(item.getData()));
    }

    public Disposable signInByGoogle(Intent data) {
        LoggingModel.Builder builder = new LoggingModel.Builder();
        builder.addData(data);
        Observable<Boolean> subject = mLoggingInteractor.signIn(builder.create());
        return subject
                .filter(isConnected -> isConnected)
                .subscribe(
                        isConnected -> mMessageLiveData.setValue(new LiveDataMessage(true, null)),
                        e -> mMessageLiveData.setValue(new LiveDataMessage(false, e.getMessage()))
                );
    }
}

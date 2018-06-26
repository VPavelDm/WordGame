package com.vpaveldm.wordgame.adapterLayer.viewModel;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.vpaveldm.wordgame.dataLayer.store.model.LoggingModel;
import com.vpaveldm.wordgame.domainLayer.interactors.LoggingInteractor;
import com.vpaveldm.wordgame.uiLayer.view.activity.ActivityComponentManager;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class LoggingViewModel extends ViewModel {

    @Inject
    LoggingInteractor mLoggingInteractor;

    private MutableLiveData<LiveDataMessage> mMessageLiveData;
    private MutableLiveData<Intent> mIntentLiveData;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public LoggingViewModel() {
        super();
        ActivityComponentManager.getActivityComponent().inject(this);
    }

    public void subscribe(LifecycleOwner owner, Observer<LiveDataMessage> messageListener, Observer<Intent> intentListener) {
        if (mMessageLiveData == null) {
            mMessageLiveData = new MutableLiveData<>();
        }
        mIntentLiveData = new MutableLiveData<>();

        mMessageLiveData.observe(owner, messageListener);
        mIntentLiveData.observe(owner, intentListener);
    }

    public void clickLogin(@NonNull String email, @NonNull String password) {
        LoggingModel model = new LoggingModel(email, password);
        Disposable d = mLoggingInteractor.signIn(model)
                .filter(isConnected -> isConnected)
                .subscribe(
                        isConnected -> mMessageLiveData.setValue(new LiveDataMessage(true, null)),
                        e -> mMessageLiveData.setValue(new LiveDataMessage(false, e.getMessage()))
                );
        mCompositeDisposable.add(d);
    }

    public void clickRegister(@NonNull String email, @NonNull String password) {
        LoggingModel model = new LoggingModel(email, password);
        Disposable d = mLoggingInteractor.signUp(model)
                .filter(isConnected -> isConnected)
                .subscribe(
                        isConnected -> mMessageLiveData.setValue(new LiveDataMessage(true, null)),
                        e -> mMessageLiveData.setValue(new LiveDataMessage(false, e.getMessage()))
                );
        mCompositeDisposable.add(d);
    }

    public void clickEntryGoogle() {
        Disposable d = mLoggingInteractor.getGoogleIntent()
                .subscribe(item -> mIntentLiveData.setValue(item.data));
        mCompositeDisposable.add(d);
    }

    public void signInByGoogle(Intent data) {
        LoggingModel model = new LoggingModel(data);
        Disposable d = mLoggingInteractor.signIn(model)
                .filter(isConnected -> isConnected)
                .subscribe(
                        isConnected -> mMessageLiveData.setValue(new LiveDataMessage(true, null)),
                        e -> mMessageLiveData.setValue(new LiveDataMessage(false, e.getMessage()))
                );
        mCompositeDisposable.add(d);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.clear();
    }
}

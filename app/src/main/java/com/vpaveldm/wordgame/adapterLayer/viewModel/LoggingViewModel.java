package com.vpaveldm.wordgame.adapterLayer.viewModel;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import com.vpaveldm.wordgame.dataLayer.store.model.LoggingModel;
import com.vpaveldm.wordgame.domainLayer.interactors.LoggingInteractor;
import com.vpaveldm.wordgame.uiLayer.view.activity.ActivityComponentManager;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author Pavel Vaitsikhouski
 */
public class LoggingViewModel extends ViewModel {

    @Inject
    LoggingInteractor mLoggingInteractor;

    public ObservableBoolean visible = new ObservableBoolean();

    private MutableLiveData<LiveDataMessage> mMessageLiveData;
    private MutableLiveData<Intent> mIntentLiveData;
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public LoggingViewModel() {
        super();
        ActivityComponentManager.getActivityComponent().inject(this);
        visible.set(false);
    }

    /**
     * The method that subscribes to updates
     *
     * @param owner           object that is used to handle lifecycle changes
     * @param messageListener callback object that is used for notifications about login's,
     *                        register's and login's by google result
     * @param intentListener  callback object that is used for notifications when an intent is received
     */
    public void subscribe(LifecycleOwner owner, Observer<LiveDataMessage> messageListener, Observer<Intent> intentListener) {
        if (mMessageLiveData == null) {
            mMessageLiveData = new MutableLiveData<>();
        }
        mIntentLiveData = new MutableLiveData<>();

        mMessageLiveData.observe(owner, messageListener);
        mIntentLiveData.observe(owner, intentListener);
    }

    /**
     * Processing a button press to login by email and password
     *
     * @param email    email address to login
     * @param password password to login
     */
    public void clickLogin(@NonNull String email, @NonNull String password) {
        LoggingModel model = new LoggingModel(email, password);
        Disposable d = mLoggingInteractor.signIn(model)
                .filter(isConnected -> isConnected)
                .doOnEach(o -> visible.set(false))
                .doOnSubscribe(s -> visible.set(true))
                .subscribe(
                        isConnected -> mMessageLiveData.setValue(new LiveDataMessage(true, null)),
                        e -> mMessageLiveData.setValue(new LiveDataMessage(false, e.getMessage()))
                );
        mCompositeDisposable.add(d);
    }

    /**
     * Processing a button press to register by email and password
     *
     * @param email    email address to register
     * @param password password to register
     */
    public void clickRegister(@NonNull String email, @NonNull String password) {
        LoggingModel model = new LoggingModel(email, password);
        Disposable d = mLoggingInteractor.signUp(model)
                .filter(isConnected -> isConnected)
                .doOnEach(o -> visible.set(false))
                .doOnSubscribe(s -> visible.set(true))
                .subscribe(
                        isConnected -> mMessageLiveData.setValue(new LiveDataMessage(true, null)),
                        e -> mMessageLiveData.setValue(new LiveDataMessage(false, e.getMessage()))
                );
        mCompositeDisposable.add(d);
    }

    /**
     * Processing a button press to entry by google
     */
    public void clickEntryGoogle() {
        Disposable d = mLoggingInteractor.getGoogleIntent()
                .subscribe(item -> mIntentLiveData.setValue(item.data));
        mCompositeDisposable.add(d);
    }

    /**
     * The sign in method
     *
     * @param data Google's intent
     */
    public void signInByGoogle(Intent data) {
        LoggingModel model = new LoggingModel(data);
        Disposable d = mLoggingInteractor.signIn(model)
                .filter(isConnected -> isConnected)
                .doOnEach(o -> visible.set(false))
                .doOnSubscribe(s -> visible.set(true))
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

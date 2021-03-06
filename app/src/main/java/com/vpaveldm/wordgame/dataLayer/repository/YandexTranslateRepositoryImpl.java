package com.vpaveldm.wordgame.dataLayer.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import com.vpaveldm.wordgame.R;
import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.interfaces.IYandexTranslateRepository;
import com.vpaveldm.wordgame.dataLayer.store.model.YandexResponse;
import com.vpaveldm.wordgame.dataLayer.store.retrofit.IYandexTranslate;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@ActivityScope
public class YandexTranslateRepositoryImpl implements IYandexTranslateRepository {

    @Inject
    IYandexTranslate mTranslator;

    @Inject
    YandexTranslateRepositoryImpl() {
    }

    @Inject
    @Named("Application")
    Context mContext;

    @Override
    public Single<String> getAutoTranslateWord(String word) {
        Call<YandexResponse> call = mTranslator.getTranslate(
                mContext.getString(R.string.yandex_key), "en-ru", word);
        return Single.create(subject -> call.enqueue(new Callback<YandexResponse>() {
            @Override
            public void onResponse(@NonNull Call<YandexResponse> call, @NonNull Response<YandexResponse> response) {
                YandexResponse yandexResponse = response.body();
                if (yandexResponse == null) {
                    subject.onError(new IllegalArgumentException("Can not translate " + word));
                    return;
                }
                List<String> textList = yandexResponse.getText();
                if (textList.size() == 0) {
                    subject.onError(new IllegalArgumentException("Can not translate " + word));
                    return;
                }
                subject.onSuccess(textList.get(0));
            }

            @Override
            public void onFailure(@NonNull Call<YandexResponse> call, @NonNull Throwable t) {
                subject.onError(t);
            }
        }));
    }
}

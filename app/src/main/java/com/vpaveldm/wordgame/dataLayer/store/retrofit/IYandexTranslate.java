package com.vpaveldm.wordgame.dataLayer.store.retrofit;

import com.vpaveldm.wordgame.dataLayer.store.model.YandexResponse;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IYandexTranslate {
    @POST("api/v1.5/tr.json/translate")
    Call<YandexResponse> getTranslate(@Query("key") String key, @Query("lang") String lang, @Query("text") String word);
}

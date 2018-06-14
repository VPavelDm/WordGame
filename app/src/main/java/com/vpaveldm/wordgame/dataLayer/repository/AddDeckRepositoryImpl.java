package com.vpaveldm.wordgame.dataLayer.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vpaveldm.wordgame.R;
import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.interfaces.IAddDeckRepository;
import com.vpaveldm.wordgame.dataLayer.store.model.Card;
import com.vpaveldm.wordgame.dataLayer.store.model.Deck;
import com.vpaveldm.wordgame.dataLayer.store.model.YandexResponse;
import com.vpaveldm.wordgame.dataLayer.store.retrofit.IYandexTranslate;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Completable;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@ActivityScope
public class AddDeckRepositoryImpl implements IAddDeckRepository {

    @Inject
    IYandexTranslate mTranslator;

    @Inject
    AddDeckRepositoryImpl() {
    }

    @Inject
    @Named("Application")
    Context mContext;

    @Override
    public Completable addDeck(Deck model) {
        return Completable.create(source -> {
            Log.i("firebaseTAG", "addDeck: " + Thread.currentThread().getName());
            DatabaseReference decksRef = FirebaseDatabase.getInstance().getReference("decks");
            DatabaseReference modelRef = decksRef.push();
            String id = modelRef.getKey();
            if (id == null) {
                model.id = "No_ID";
            } else {
                model.id = modelRef.getKey();
                for (int i = 0; i < model.cards.size(); i++) {
                    DatabaseReference cardRef = modelRef.child(model.id).child("cards").child(String.valueOf(i)).child("id").push();
                    String cardId = cardRef.getKey();
                    Card card = model.cards.get(i);
                    card.deck_id = id;
                    if (cardId == null) {
                        card.id = "No_ID";
                    } else {
                        card.id = cardRef.getKey();
                    }
                }
            }
            modelRef.setValue(model, (databaseError, databaseReference) -> {
                if (databaseError != null) {
                    source.onError(databaseError.toException());
                } else {
                    source.onComplete();
                }
            });
        });
    }

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

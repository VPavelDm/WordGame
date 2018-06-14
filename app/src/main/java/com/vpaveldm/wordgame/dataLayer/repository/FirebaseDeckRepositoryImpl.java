package com.vpaveldm.wordgame.dataLayer.repository;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.interfaces.IFirebaseRepository;
import com.vpaveldm.wordgame.dataLayer.store.local.AppDatabase;
import com.vpaveldm.wordgame.dataLayer.store.model.Card;
import com.vpaveldm.wordgame.dataLayer.store.model.Deck;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@ActivityScope
public class FirebaseDeckRepositoryImpl implements IFirebaseRepository {

    private AppDatabase db;

    @Inject
    FirebaseDeckRepositoryImpl(@Named("Application") Context context) {
        db = Room.databaseBuilder(context, AppDatabase.class, "decks").build();
    }

    @Override
    public Flowable<List<Deck>> getDecks() {
        Flowable<List<Deck>> flowable = db.deckDao().getDecksWithCards();
        //Send asynchronous request to connect to firebase
        DatabaseReference decksRef = FirebaseDatabase.getInstance().getReference("decks");
        decksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Deck> decks = new ArrayList<>();
                Iterable<DataSnapshot> iterator = dataSnapshot.getChildren();
                for (DataSnapshot snapshot : iterator) {
                    Deck deck = snapshot.getValue(Deck.class);
                    decks.add(deck);
                }
                if (decks.size() != 0) {
                    Thread t = new Thread(() -> db.deckDao().insertDecksWithCards(decks));
                    t.start();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return flowable;
    }

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
                        card.id = cardId;
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
}

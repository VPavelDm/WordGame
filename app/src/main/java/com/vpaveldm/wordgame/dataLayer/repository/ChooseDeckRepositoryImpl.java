package com.vpaveldm.wordgame.dataLayer.repository;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.interfaces.IChooseDeckRepository;
import com.vpaveldm.wordgame.dataLayer.store.local.AppDatabase;
import com.vpaveldm.wordgame.dataLayer.store.model.Deck;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Flowable;

@ActivityScope
public class ChooseDeckRepositoryImpl implements IChooseDeckRepository {

    private AppDatabase db;

    @Inject
    ChooseDeckRepositoryImpl(@Named("Application") Context context) {
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
}

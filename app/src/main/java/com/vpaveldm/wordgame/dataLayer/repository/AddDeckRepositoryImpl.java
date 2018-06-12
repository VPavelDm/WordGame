package com.vpaveldm.wordgame.dataLayer.repository;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.interfaces.IAddDeckRepository;
import com.vpaveldm.wordgame.dataLayer.model.Deck;

import javax.inject.Inject;

import io.reactivex.Completable;

@ActivityScope
public class AddDeckRepositoryImpl implements IAddDeckRepository {
    @Inject
    AddDeckRepositoryImpl() {
    }

    @Override
    public Completable addDeck(Deck model) {
        return Completable.create(source -> {
            DatabaseReference decksRef = FirebaseDatabase.getInstance().getReference("decks");
            decksRef.push().setValue(model, (databaseError, databaseReference) -> {
                if (databaseError != null) {
                    source.onError(databaseError.toException());
                } else {
                    source.onComplete();
                }
            });
        });
    }
}

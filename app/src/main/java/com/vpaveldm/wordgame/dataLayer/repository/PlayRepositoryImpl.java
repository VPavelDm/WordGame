package com.vpaveldm.wordgame.dataLayer.repository;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vpaveldm.wordgame.dagger.scope.ActivityScope;
import com.vpaveldm.wordgame.dataLayer.interfaces.IPlayRepository;
import com.vpaveldm.wordgame.dataLayer.model.PlayModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

@ActivityScope
public class PlayRepositoryImpl implements IPlayRepository {

    @Inject
    PlayRepositoryImpl() {
    }

    @Override
    public Observable<List<PlayModel>> getDecks() {
        List<PlayModel> decks = new ArrayList<>();
        BehaviorSubject<List<PlayModel>> subject = BehaviorSubject.createDefault(decks);
        DatabaseReference decksRef = FirebaseDatabase.getInstance().getReference("decks");
        decksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                decks.clear();
                Iterable<DataSnapshot> iterator = dataSnapshot.getChildren();
                for (DataSnapshot snapshot : iterator) {
                    PlayModel playModel = snapshot.getValue(PlayModel.class);
                    decks.add(playModel);
                }
                subject.onNext(decks);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                subject.onError(databaseError.toException().getCause());
            }
        });

        return subject;
    }

    @Override
    public Completable addDeck(PlayModel model) {
        return Completable.create(source -> {
            DatabaseReference decksRef = FirebaseDatabase.getInstance().getReference("decks");
            Task<Void> task = decksRef.push().setValue(model);
            if (task.isSuccessful()) {
                source.onComplete();
            } else {
                Exception e = task.getException();
                if (e != null)
                    source.onError(e.getCause());
            }
        });
    }
}

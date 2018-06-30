package com.vpaveldm.wordgame.dataLayer.repository;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import com.vpaveldm.wordgame.dataLayer.store.model.TopUserList;
import com.vpaveldm.wordgame.dataLayer.store.model.User;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

@ActivityScope
public class FirebaseRepositoryImpl implements IFirebaseRepository {

    private final AppDatabase db;

    @Inject
    FirebaseRepositoryImpl(@Named("Application") Context context) {
        db = Room.databaseBuilder(context, AppDatabase.class, "decks").build();
    }

    @Override
    public DataSource.Factory<Integer, Deck> getDecks() {
        return db.deckDao().getDeckWithCardsDataSource();
    }

    @Override
    public Completable subscribeOnUpdate() {
        BehaviorSubject<Boolean> subject = BehaviorSubject.create();
        DatabaseReference decksRef = FirebaseDatabase.getInstance().getReference("decks");
        ValueEventListener valueEventListener = new ValueEventListener() {
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
                decksRef.removeEventListener(this);
                subject.onComplete();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                decksRef.removeEventListener(this);
                subject.onError(databaseError.toException());
            }
        };
        decksRef.addValueEventListener(valueEventListener);
        return subject.doOnDispose(() -> decksRef.removeEventListener(valueEventListener)).ignoreElements();
    }

    @Override
    public Single<Deck> getDeckById(String id) {
        return Single.create(source -> source.onSuccess(db.deckDao().getDeck(id)));
    }

    @Override
    public Completable addDeck(Deck deck) {
        return Completable.create(source -> {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference decksRef = database.getReference("decks");
            //Create node for deck
            DatabaseReference modelRef = decksRef.push();
            String id = modelRef.getKey();
            if (id == null) {
                source.onError(new ConnectException("Connect to firebase is lost"));
                return;
            }
            deck.id = modelRef.getKey();
            for (int i = 0; i < deck.cards.size(); i++) {
                DatabaseReference cardRef = modelRef.child(deck.id).child("cards").child(String.valueOf(i)).child("id").push();
                String cardId = cardRef.getKey();
                Card card = deck.cards.get(i);
                card.deck_id = id;
                if (cardId == null) {
                    source.onError(new ConnectException("Connect to firebase is lost"));
                    return;
                }
                card.id = cardId;
            }
            modelRef.setValue(deck);
            //Init empty top list for deck
            TopUserList topUserList = new TopUserList();
            topUserList.deckName = deck.deckName;
            database.getReference("top_list")
                    .child(deck.id)
                    .setValue(topUserList);
            source.onComplete();
        });
    }

    @Override
    public Completable updateTopList(Deck deck, long time) {
        BehaviorSubject<Boolean> subject = BehaviorSubject.create();
        DatabaseReference deckRef = FirebaseDatabase.getInstance().getReference("top_list").child(deck.id);
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TopUserList list = dataSnapshot.getValue(TopUserList.class);
                if (list == null) {
                    subject.onError(new NullPointerException("Connect to firebase is lost"));
                    return;
                }
                User user = new User();
                String userName = getUserName();
                if (userName == null) {
                    subject.onError(new ConnectException("Connect to firebase is lost"));
                }
                user.name = userName;
                user.time = time;
                list.users.add(user);
                Collections.sort(list.users);
                if (list.users.size() >= 10) {
                    list.users.remove(list.users.size() - 1);
                }
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("users", list.users);
                deckRef.updateChildren(childUpdates, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        subject.onError(databaseError.toException());
                    }
                    subject.onComplete();
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                subject.onError(databaseError.toException());
            }
        };
        deckRef.addListenerForSingleValueEvent(listener);
        return subject.doOnDispose(
                () -> deckRef.removeEventListener(listener)
        ).ignoreElements();
    }

    @Override
    public Observable<TopUserList> getTopList(Deck deck) {
        PublishSubject<TopUserList> source = PublishSubject.create();
        DatabaseReference deckRef = FirebaseDatabase.getInstance().getReference("top_list").child(deck.id);
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TopUserList userList = dataSnapshot.getValue(TopUserList.class);
                if (userList == null) {
                    source.onError(new ConnectException("Connect to firebase is lost"));
                } else {
                    source.onNext(userList);
                    source.onComplete();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                source.onError(databaseError.toException());
            }
        };
        deckRef.addListenerForSingleValueEvent(listener);
        return source.doOnDispose(
                () -> deckRef.removeEventListener(listener)
        );
    }

    private String getUserName() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return null;
        }
        if (user.getDisplayName() != null) {
            return user.getDisplayName();
        }
        if (user.getEmail() != null) {
            return user.getEmail();
        }
        return user.getUid();
    }
}

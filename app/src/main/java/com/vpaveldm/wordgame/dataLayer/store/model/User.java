package com.vpaveldm.wordgame.dataLayer.store.model;

import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User implements Comparable<User> {
    public String name;
    public long time;

    @Exclude
    @Override
    public int compareTo(@NonNull User o) {
        return (time - o.time) > 0 ? 1 : -1;
    }

}

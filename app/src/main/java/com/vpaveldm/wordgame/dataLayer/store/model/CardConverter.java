package com.vpaveldm.wordgame.dataLayer.store.model;

import android.arch.persistence.room.TypeConverter;

import java.util.Arrays;
import java.util.List;

public class CardConverter {

    @TypeConverter
    public List<String> toList(String cards) {
        return Arrays.asList(cards.split(";"));
    }

    @TypeConverter
    public String toArray(List<String> cards) {
        StringBuilder result = new StringBuilder();
        for (String card : cards) {
            result.append(card).append(";");
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

}

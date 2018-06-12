package com.vpaveldm.wordgame.dataLayer.store.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
public class Card {
    private String word;
    private String translate;
    private List<String> wrongTranslates = new ArrayList<>();

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public List<String> getWrongTranslates() {
        return wrongTranslates;
    }

    public void setWrongTranslates(List<String> wrongTranslates) {
        this.wrongTranslates = wrongTranslates;
    }

    public void addWrongTranslate(String wrongTranslate) {
        wrongTranslates.add(wrongTranslate);
    }
}

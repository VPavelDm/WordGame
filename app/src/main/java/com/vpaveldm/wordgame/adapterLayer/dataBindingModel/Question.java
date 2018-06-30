package com.vpaveldm.wordgame.adapterLayer.dataBindingModel;

import java.util.List;

public class Question {
    public String firstAnswer;
    public String secondAnswer;
    public String thirdAnswer;
    public String fourthAnswer;
    public String otherAnswer;
    public String word;

    public Question(String word, List<String> answers) {
        this.word = word;
        this.firstAnswer = answers.get(0);
        this.secondAnswer = answers.get(1);
        this.thirdAnswer = answers.get(2);
        this.fourthAnswer = answers.get(3);
        this.otherAnswer = answers.get(4);
    }
}

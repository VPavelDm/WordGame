package com.vpaveldm.wordgame.errors;

public interface IErrorListener {
    void success();
    void failure(String message);
}

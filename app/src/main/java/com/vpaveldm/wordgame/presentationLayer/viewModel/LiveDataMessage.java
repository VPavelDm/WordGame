package com.vpaveldm.wordgame.presentationLayer.viewModel;

public class LiveDataMessage {
    private boolean isSuccess;
    private String message;

    public LiveDataMessage(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }
}

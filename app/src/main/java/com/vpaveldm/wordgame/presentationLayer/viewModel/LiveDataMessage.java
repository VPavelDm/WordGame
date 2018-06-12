package com.vpaveldm.wordgame.presentationLayer.viewModel;

public class LiveDataMessage {
    private final boolean isSuccess;
    private final String message;

    LiveDataMessage(boolean isSuccess, String message) {
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

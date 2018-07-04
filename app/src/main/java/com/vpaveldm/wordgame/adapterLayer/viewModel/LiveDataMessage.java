package com.vpaveldm.wordgame.adapterLayer.viewModel;

/**
 * @author Pavel Vaitsikhouski
 */
public class LiveDataMessage {
    private final boolean isSuccess;
    private final String message;

    /**
     * Constructs a message to link view models and fragments
     *
     * @param isSuccess a variable to check result of work
     * @param message   an object with additional information about result
     */
    LiveDataMessage(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    /**
     * Returns <tt>true</tt> if the result is success
     *
     * @return <tt>true</tt> if the result is success
     */
    public boolean isSuccess() {
        return isSuccess;
    }

    /**
     * Returns information about result of work
     *
     * @return an object with information about result of work
     */
    public String getMessage() {
        return message;
    }
}

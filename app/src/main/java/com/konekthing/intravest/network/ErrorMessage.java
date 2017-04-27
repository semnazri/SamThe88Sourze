/*
 * Copyright (c) 2015 Konekthing.
 */

package com.konekthing.intravest.network;

public class ErrorMessage {

    private boolean status;
    private String error;

    ErrorMessage() {
    }

    public boolean getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setError(String error) {
        this.error = error;
    }

}

package com.arya.flashchatnewfirebase;

/**
 * Created by arya on 4/3/18.
 */

class InstantMessage {

    private String message;
    private String email;
    private static  String yoga="Pranayam";

    InstantMessage(String message, String email) {
        this.message = message;
        this.email = email;
    }

    public InstantMessage() {



    }

    String getMessage() {
        return message;
    }

    String getEmail() {
        return email;
    }
}

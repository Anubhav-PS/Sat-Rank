package com.anubhavps.satrank.interfaces;

public interface iResponseResult {

    void errorReported(Exception e);

    void onSuccess(String message);

    void onFailure(String message);

}

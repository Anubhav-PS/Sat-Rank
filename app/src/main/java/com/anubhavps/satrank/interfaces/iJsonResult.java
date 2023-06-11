package com.anubhavps.satrank.interfaces;

import com.anubhavps.satrank.models.Result;
import com.google.android.material.color.utilities.Score;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface iJsonResult {

    void onJsonParsingFailed(Exception e);

    void onJsonParsedSuccessfully(Result result);

    void onJsonParsedSuccessfully(ArrayList<Result> results);

}

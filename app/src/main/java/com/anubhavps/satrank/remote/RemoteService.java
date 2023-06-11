package com.anubhavps.satrank.remote;

import android.util.Log;

import androidx.annotation.NonNull;

import com.anubhavps.satrank.interfaces.iJsonRankResult;
import com.anubhavps.satrank.interfaces.iJsonResult;
import com.anubhavps.satrank.interfaces.iRemoteServices;
import com.anubhavps.satrank.interfaces.iResponseResult;
import com.anubhavps.satrank.models.Marks;
import com.anubhavps.satrank.models.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RemoteService implements iRemoteServices {

    @Override
    public void insertScore(Marks marks, iResponseResult responseResult) {

        final String url = "https://us-central1-sat-mark.cloudfunctions.net/addScore";

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new FormBody.Builder()
                .add("name", marks.getName().toLowerCase())
                .add("address", marks.getAddress())
                .add("city", marks.getCity())
                .add("country", marks.getCountry())
                .add("pinCode", marks.getCountry())
                .add("score", String.valueOf(marks.getScore()))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("Remote Service", "Failed to call insert score API", e);
                //report error using callback
                responseResult.errorReported(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                assert response.body() != null;
                String responseBody = response.body().string();
                if (response.isSuccessful()) {
                    responseResult.onSuccess(responseBody);
                } else {
                    responseResult.onFailure(responseBody);
                }
                response.close();
            }
        });
    }

    @Override
    public void updateScore(Marks marks, iResponseResult responseResult) {

        final String url = "https://us-central1-sat-mark.cloudfunctions.net/updateScore";

        String urlWithParams = url + "?name=" + marks.getName();

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new FormBody.Builder()
                .add("address", marks.getAddress())
                .add("city", marks.getCity())
                .add("country", marks.getCountry())
                .add("pinCode", marks.getCountry())
                .add("score", String.valueOf(marks.getScore()))
                .build();

        Request request = new Request.Builder()
                .url(urlWithParams)
                .post(requestBody)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("Remote Service", "Failed to call update score API", e);
                //report error using callback
                responseResult.errorReported(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                assert response.body() != null;
                String responseBody = response.body().string();
                if (response.isSuccessful()) {
                    responseResult.onSuccess(responseBody);
                } else {
                    responseResult.onFailure(responseBody);
                }
                response.close();
            }
        });
    }

    @Override
    public void deleteScore(String name, iResponseResult responseResult) {
        final String url = "https://us-central1-sat-mark.cloudfunctions.net/deleteScore";

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new FormBody.Builder()
                .add("name", name)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("Remote Service", "Failed to call update score API", e);
                //report error using callback
                responseResult.errorReported(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                assert response.body() != null;
                String responseBody = response.body().string();
                if (response.isSuccessful()) {
                    responseResult.onSuccess(responseBody);
                } else {
                    responseResult.onFailure(responseBody);
                }
                response.close();
            }
        });
    }

    @Override
    public void getAllScores(iResponseResult responseResult, iJsonResult jsonResult) {
        final String url = "https://us-central1-sat-mark.cloudfunctions.net/getAllScore";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("Remote Service", "Failed to call get all score API", e);
                responseResult.errorReported(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                assert response.body() != null;
                String responseBody = response.body().string();
                ArrayList<Result> allResults = new ArrayList<>();
                // Parse the JSON response
                try {
                    JSONArray jsonArray = new JSONArray(responseBody);
                    Result result;

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String name = jsonObject.getString("name");
                        String address = jsonObject.getString("address");
                        String city = jsonObject.getString("city");
                        String country = jsonObject.getString("country");
                        String pinCode = jsonObject.getString("pinCode");
                        int score = jsonObject.getInt("score");
                        boolean passed = jsonObject.getBoolean("passed");
                        result = new Result(name, address, city, country, pinCode, score, passed);
                        allResults.add(result);
                    }

                    jsonResult.onJsonParsedSuccessfully(allResults);

                } catch (JSONException e) {
                    jsonResult.onJsonParsingFailed(e);
                }
                response.close();
            }
        });
    }

    @Override
    public void getScore(String name, iResponseResult responseResult, iJsonResult jsonResult) {

        final String url = "https://us-central1-sat-mark.cloudfunctions.net/getScore";

        OkHttpClient client = new OkHttpClient();

        String urlWithParams = url + "?name=" + name;

        Request request = new Request.Builder()
                .url(urlWithParams)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("Remote Service", "Failed to call get score API", e);
                //report error using callback
                responseResult.errorReported(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                assert response.body() != null;
                String responseBody = response.body().string();
                if (response.isSuccessful()) {

                    try {

                        JSONObject jsonObject = new JSONObject(responseBody);
                        String name = jsonObject.getString("name");
                        String address = jsonObject.getString("address");
                        String city = jsonObject.getString("city");
                        String country = jsonObject.getString("country");
                        String pinCode = jsonObject.getString("pinCode");
                        int score = jsonObject.getInt("score");
                        boolean passed = jsonObject.getBoolean("passed");
                        Result result = new Result(name, address, city, country, pinCode, score, passed);
                        jsonResult.onJsonParsedSuccessfully(result);
                    } catch (JSONException e) {
                        jsonResult.onJsonParsingFailed(e);
                    }

                } else {
                    responseResult.onFailure(responseBody);
                }
                response.close();
            }
        });

    }

    @Override
    public void getRank(String name, String score, iResponseResult responseResult, iJsonRankResult jsonRankResult) {
        final String url = "https://us-central1-sat-mark.cloudfunctions.net/getRank";

        OkHttpClient client = new OkHttpClient();

        String urlWithParams = url + "?name=" + name + "&score=" + score;

        Request request = new Request.Builder()
                .url(urlWithParams)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("Remote Service", "Failed to call get rank API", e);
                //report error using callback
                responseResult.errorReported(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                assert response.body() != null;
                String responseBody = response.body().string();
                if (response.isSuccessful()) {
                    String rank = responseBody.split("\\|")[1];
                    jsonRankResult.onRankDownloaded(Integer.parseInt(rank));
                } else {
                    responseResult.onFailure(responseBody);
                }
                response.close();
            }
        });
    }

}

package com.anubhavps.satrank.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import com.anubhavps.satrank.R;
import com.anubhavps.satrank.adapters.ViewAllScoreAdapters;
import com.anubhavps.satrank.interfaces.iAdapterViewPressed;
import com.anubhavps.satrank.models.Result;
import com.anubhavps.satrank.remote.RemoteService;
import com.anubhavps.satrank.interfaces.iJsonRankResult;
import com.anubhavps.satrank.interfaces.iJsonResult;
import com.anubhavps.satrank.interfaces.iResponseResult;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity implements iResponseResult, iJsonResult, iAdapterViewPressed, View.OnClickListener, iJsonRankResult {

    private ViewAllScoreAdapters viewAllScoreAdapters;
    private RemoteService remoteService;
    private RecyclerView recyclerView;

    private Dialog dialog;

    private String studentName;

    private FloatingActionButton addScore;

    private LinearLayout loadingLinearLayout;

    private Result result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        recyclerView = findViewById(R.id.satMarksRecyclerView);
        addScore = findViewById(R.id.homePageAddScoreBtn);
        loadingLinearLayout = findViewById(R.id.homePageLoadingBar);

        remoteService = new RemoteService();

        loadingLinearLayout.setVisibility(View.VISIBLE);
        remoteService.getAllScores(this, this);

        addScore.setOnClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomePage.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    @Override
    public void errorReported(Exception e) {
        runOnUiThread(() -> {
            loadingLinearLayout.setVisibility(View.GONE);
            callSnackBar(e.getLocalizedMessage());
        });
    }

    @Override
    public void onSuccess(String message) {
        runOnUiThread(() -> {
            callSnackBar(message);
            loadingLinearLayout.setVisibility(View.VISIBLE);
            remoteService.getAllScores(this, this);
        });
    }

    @Override
    public void onFailure(String message) {
        runOnUiThread(() -> {
            loadingLinearLayout.setVisibility(View.GONE);
            callSnackBar(message);
        });
    }

    @Override
    public void onJsonParsingFailed(Exception e) {
        runOnUiThread(() -> {
            loadingLinearLayout.setVisibility(View.GONE);
            callSnackBar(e.getLocalizedMessage());
        });
    }

    @Override
    public void onJsonParsedSuccessfully(Result result) {

    }

    @Override
    public void onJsonParsedSuccessfully(ArrayList<Result> results) {
        runOnUiThread(() -> {
            loadingLinearLayout.setVisibility(View.GONE);
            viewAllScoreAdapters = new ViewAllScoreAdapters(results, HomePage.this, HomePage.this);
            recyclerView.setAdapter(viewAllScoreAdapters);
        });
    }

    private void callSnackBar(String message) {
        Snackbar snackbar = Snackbar
                .make(HomePage.this, findViewById(R.id.homePage), message, Snackbar.LENGTH_LONG);
        snackbar.setTextColor(Color.BLACK);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(HomePage.this, R.color.white));
        snackbar.show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadingLinearLayout.setVisibility(View.VISIBLE);
        remoteService.getAllScores(this, this);

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onViewPressed(Result result) {
        dialog = new Dialog(HomePage.this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_choose_operation);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);

        MaterialTextView showRankTxt = dialog.findViewById(R.id.dialogShowRankTxt);
        MaterialTextView updateTxt = dialog.findViewById(R.id.dialogUpdateTxt);
        MaterialTextView deleteTxt = dialog.findViewById(R.id.dialogDeleteTxt);

        showRankTxt.setOnClickListener(this);
        updateTxt.setOnClickListener(this);
        deleteTxt.setOnClickListener(this);

        this.studentName = result.getName();

        this.result = result;

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.BottomDialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.dialogDeleteTxt) {
            deleteScore();
        } else if (id == R.id.dialogUpdateTxt) {
            updateScore();
        } else if (id == R.id.dialogShowRankTxt) {
            showRank();
        } else if (id == R.id.homePageAddScoreBtn) {
            addScore();
        }
    }

    private void deleteScore() {
        dialog.dismiss();
        remoteService.deleteScore(studentName, this);
    }

    private void showRank() {
        remoteService.getRank(result.getName(),String.valueOf(result.getScore()), this, this);
        dialog.dismiss();
    }

    private void updateScore() {
        dialog.dismiss();
        Intent intent = new Intent(HomePage.this, UpdateScorePage.class);
        intent.putExtra("name", result.getName());
        intent.putExtra("address", result.getAddress());
        intent.putExtra("country", result.getCountry());
        intent.putExtra("city", result.getCity());
        intent.putExtra("pinCode", result.getPinCode());
        intent.putExtra("score", result.getScore());
        startActivity(intent);

    }

    private void addScore() {
        Intent intent = new Intent(HomePage.this, AddScorePage.class);
        startActivity(intent);
    }

    @Override
    public void onRankDownloaded(int rank) {

        runOnUiThread(() -> {
            dialog = new Dialog(HomePage.this);
            dialog.setContentView(R.layout.dialog_rank);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.BottomDialogAnimation;
            dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setCancelable(true);

            MaterialTextView rankTxt = dialog.findViewById(R.id.dialogRankTxt);

            rankTxt.setText(String.valueOf(rank));

            dialog.show();
        });

    }
}
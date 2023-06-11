package com.anubhavps.satrank.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.anubhavps.satrank.R;
import com.anubhavps.satrank.models.Marks;
import com.anubhavps.satrank.remote.RemoteService;
import com.anubhavps.satrank.interfaces.iResponseResult;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

public class AddScorePage extends AppCompatActivity implements iResponseResult {

    private TextInputLayout studentNameEt, studentAddressEt, studentCityEt, studentPinCodeEt, studentScoreEt;
    private AutoCompleteTextView studentCountryEt;

    private ProgressBar progressBar;
    private MaterialButton addScoreBtn;

    private MaterialTextView progressStatus;

    private ImageButton backButton;

    private String studentName, address, country, city, pinCode, score;

    private String[] countryList;

    private final String pinCodeRegex = "^[1-9][0-9]{5}$";
    private final String studentNameRegex = "^[A-Za-z\\s-]{2,}$";
    private final String scoreRegex = "^(?:1600|1[0-5]\\d{2}|[1-9]\\d{2}|\\d{1,2})$";

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_score_page);

        studentNameEt = findViewById(R.id.addScorePageStudentNameEt);
        studentAddressEt = findViewById(R.id.addScorePageStudentAddressEt);
        studentCityEt = findViewById(R.id.addScorePageStudentCityEt);
        studentPinCodeEt = findViewById(R.id.addScorePageStudentPinCodeEt);
        studentScoreEt = findViewById(R.id.addScorePageStudentScoreEt);
        studentCountryEt = findViewById(R.id.addScorePageStudentCountryEt);
        addScoreBtn = findViewById(R.id.addScorePageAddBtn);
        backButton = findViewById(R.id.addScoreBackBtn);

        studentCountryEt.setOnItemClickListener((parent, view, position, id) -> callCountrySelection(position));

        countryList = getResources().getStringArray(R.array.all_counties);
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this, R.layout.countries_drop_down_item, countryList);
        studentCountryEt.setAdapter(countryAdapter);
        studentCountryEt.setSelection(0);

        addScoreBtn.setOnClickListener(v -> validateAllInput());

        backButton.setOnClickListener(v->onBackPressed());


    }

    private void callCountrySelection(int position) {
        String sel_country = countryList[position];
        if (sel_country.equalsIgnoreCase("SELECT")) {
            country = sel_country;
            studentCountryEt.setError("Please enter your country");
            studentCountryEt.requestFocus();
        } else {
            studentCountryEt.setError(null);
            country = sel_country;
            Toast.makeText(this, "Country is " + sel_country, Toast.LENGTH_SHORT).show();
        }
    }


    private boolean validateName() {
        studentName = Objects.requireNonNull(studentNameEt.getEditText()).getText().toString();

        if (studentName.isEmpty()) {
            studentNameEt.setError("Student name Is Required");
            return false;
        }

        if (!Pattern.matches(studentNameRegex, studentName)) {
            studentNameEt.setError("Enter Proper Name");
            return false;
        }

        studentNameEt.setError(null);
        return true;
    }

    private boolean validateAddress() {
        address = Objects.requireNonNull(studentAddressEt.getEditText()).getText().toString();

        if (address.isEmpty()) {
            studentAddressEt.setError("Address Is Required");
            return false;
        }

        studentAddressEt.setError(null);
        return true;
    }


    private boolean validateCountry() {
        if (studentCountryEt.getText().toString().equalsIgnoreCase("SELECT")) {
            studentCountryEt.setError("Please choose the Country");
            studentCountryEt.requestFocus();
            return false;
        }
        studentCountryEt.setError(null);
        return true;
    }

    private boolean validateCity() {
        city = Objects.requireNonNull(studentCityEt.getEditText()).getText().toString();

        if (city.isEmpty()) {
            studentCityEt.setError("City Is Required");
            return false;
        }

        studentCityEt.setError(null);
        return true;
    }


    private boolean validatePinCode() {
        pinCode = Objects.requireNonNull(studentPinCodeEt.getEditText()).getText().toString();

        if (pinCode.isEmpty()) {
            studentPinCodeEt.setError("PinCode Is Required");
            return false;
        }

        if (!Pattern.matches(pinCodeRegex, pinCode)) {
            studentPinCodeEt.setError("PinCode Is Invalid");
            return false;
        }

        studentPinCodeEt.setError(null);
        return true;
    }

    private boolean validateScore() {
        score = Objects.requireNonNull(studentScoreEt.getEditText()).getText().toString();

        if (score.isEmpty()) {
            studentScoreEt.setError("Score Is Required");
            return false;
        }

        if (!Pattern.matches(scoreRegex, score)) {
            studentScoreEt.setError("Score is not in range from 0 to 1600");
            return false;
        }

        studentScoreEt.setError(null);
        return true;
    }


    private void validateAllInput() {

        if (!(validateName() && validateAddress() && validateCity() && validateCountry() && validatePinCode() && validateScore())) {
            return;
        }


        Marks marks = new Marks(studentName.toLowerCase(Locale.ROOT), address, city, country, pinCode, Integer.parseInt(score));

        dialog = new Dialog(AddScorePage.this);
        dialog.setContentView(R.layout.dialog_upload);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.BottomDialogAnimation;
        dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        progressStatus = dialog.findViewById(R.id.dialogUploadStatusTxt);
        progressBar = dialog.findViewById(R.id.dialogUploadProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        String status = "Uploading....";
        progressStatus.setText(status);
        dialog.show();

        RemoteService remoteService = new RemoteService();
        remoteService.insertScore(marks, this);


    }

    @Override
    public void errorReported(Exception e) {
        dialog.dismiss();
        callSnackBar(e.getLocalizedMessage());
    }

    @Override
    public void onSuccess(String message) {
        dialog.dismiss();
        callSnackBar(message);
    }

    @Override
    public void onFailure(String message) {
        dialog.dismiss();
        callSnackBar(message);
    }

    private void callSnackBar(String message) {
        Snackbar snackbar = Snackbar
                .make(AddScorePage.this, findViewById(R.id.addScorePage), message, Snackbar.LENGTH_LONG);
        snackbar.setTextColor(this.getResources().getColor(R.color.navy_blue));
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(AddScorePage.this, R.color.white));
        snackbar.show();

    }

}
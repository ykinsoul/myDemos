package com.example.servicenovigrad.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.servicenovigrad.Profile;
import com.example.servicenovigrad.R;
import com.example.servicenovigrad.ServiceProviderUser;
import com.example.servicenovigrad.UserDatabase;
import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileUpdateActivity extends AppCompatActivity {


    private final Pattern patternPostal = Pattern.compile("^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] ?[0-9][A-Z][0-9]$");
    //https://howtodoinjava.com/java/regex/canada-postal-code-validation/#:~:text=A%20Canadian%20postal%20code%20is,a%20postal%20address%20in%20Canada.&text=in%20the%20format%20A1A%201A1,%2C%20O%2C%20Q%20or%20U.
    private final Pattern patternAddress = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
    private final Pattern patternName = Pattern.compile("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$");
//    https://stackoverflow.com/questions/46326822/java-regex-first-name-validation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);

        Button applyButton = findViewById(R.id.applyButton);
        final EditText firstNameText = findViewById(R.id.firstNameText);
        final EditText lastNameText = findViewById(R.id.lastNameText);
        final EditText addressLineText = findViewById(R.id.addressLineText);
        final EditText postalText = findViewById(R.id.postalText);
        final EditText numberText = findViewById(R.id.numberText);

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firstNameText.getText().toString().isEmpty() || lastNameText.getText().toString().isEmpty()
                        || addressLineText.getText().toString().isEmpty()
                        || postalText.getText().toString().isEmpty() || numberText.getText().toString().isEmpty()) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    Snackbar.make(view, "Empty fields", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                } else {
                    Matcher matcher = patternPostal.matcher(postalText.getText().toString());
                    Matcher matcher2 = patternAddress.matcher(addressLineText.getText().toString());
                    Matcher matcher3 = patternName.matcher(firstNameText.getText().toString());
                    Matcher matcher4 = patternName.matcher(lastNameText.getText().toString());
                    if (!matcher.find()) {
                        postalText.setError("Invalid Postal Code. Example: K1S 5G5");
                    } else if (!matcher3.find()) {
                        firstNameText.setError("Invalid name");
                    } else if (!matcher4.find()) {
                        lastNameText.setError("Invalid name");
                    } else if (matcher2.find()) {
                        addressLineText.setError("Invalid Address");
                    } else if (numberText.getText().toString().length() < 10) {
                        numberText.setError("Invalid Phone Number");
                    } else {
                        ServiceProviderUser employee = (ServiceProviderUser) UserDatabase.loggedInUser;
                        Profile profile = new Profile();
                        profile.setFirstName(firstNameText.getText().toString());
                        profile.setLastName(lastNameText.getText().toString());
                        profile.setAddressLine(addressLineText.getText().toString());
                        profile.setPostalCode(postalText.getText().toString());
                        profile.setPhoneNumber(numberText.getText().toString());
                        employee.setProfile(profile);
                        employee.setCompleted();
                        Toast.makeText(getApplicationContext(), "Updated profile successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });
    }
}
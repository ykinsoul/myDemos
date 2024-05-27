package com.example.servicenovigrad.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.servicenovigrad.Constants;
import com.example.servicenovigrad.R;
import com.example.servicenovigrad.User;
import com.example.servicenovigrad.UserDatabase;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!Constants.isInitialized()) {
            Constants.initializeData();
        }

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final Button registerButton = findViewById(R.id.btnRegister);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = new User(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                if (usernameEditText.getText().toString().isEmpty() && passwordEditText.getText().toString().isEmpty()) {

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    Snackbar.make(v, "Enter your login info", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                } else if (usernameEditText.getText().toString().isEmpty()) {
                    usernameEditText.setError("Enter your username");
                } else if (passwordEditText.getText().toString().isEmpty()) {
                    passwordEditText.setError("Enter your password");
                } else {
                    if (UserDatabase.users.userValidated(user)) {
                        UserDatabase.loggedInUser = UserDatabase.users.getUserInfo(user);
                        login();
                    } else {
                        Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void login() {
        Intent intent;
        if (UserDatabase.loggedInUser.getRole().equals("Administrator")) {
            intent = new Intent(this, WelcomeAdminActivity.class);
        } else if (!UserDatabase.loggedInUser.getRole().equals("Customer")) {
            intent = new Intent(this, EmployeeActivity.class);
        } else {
            intent = new Intent(this, CustomerActivity.class);
        }
        Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    private void register() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
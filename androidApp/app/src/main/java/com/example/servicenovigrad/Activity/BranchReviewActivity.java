package com.example.servicenovigrad.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.servicenovigrad.CustomerUser;
import com.example.servicenovigrad.R;
import com.example.servicenovigrad.ServiceProviderUser;
import com.example.servicenovigrad.UserDatabase;

public class BranchReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_review);
    }

    public void reviewButton(View view) {
        EditText comment = findViewById(R.id.reviewComment);
        if (comment.getText().toString().isEmpty()) {
            comment.setError("Please leave a comment.");
        } else {
            final CustomerUser customer = (CustomerUser) UserDatabase.loggedInUser;
            final RatingBar ratingBar = findViewById(R.id.ratingBar);
            Bundle extras = getIntent().getExtras();
            ServiceProviderUser employee = (ServiceProviderUser) UserDatabase.users.getUserByName(customer.getRequests().get(Integer.parseInt(extras.getString("position"))).getEmployeeUsername());
            employee.addRating(ratingBar.getRating());
            employee.addReview(comment.getText().toString());
            customer.getRequests().get(Integer.parseInt(extras.getString("position"))).setReviewed();
            Toast.makeText(getApplicationContext(), "Thank you for leaving a review", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
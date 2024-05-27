package com.example.servicenovigrad.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.servicenovigrad.CustomerUser;
import com.example.servicenovigrad.Dialog.NotificationUserDialog;
import com.example.servicenovigrad.R;
import com.example.servicenovigrad.UserDatabase;

public class CustomerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        setTitle("Welcome " + UserDatabase.loggedInUser.getUsername());
        CustomerUser customer = ((CustomerUser) UserDatabase.loggedInUser);
        if (customer.requestNotification) {
            DialogFragment newFragment = new NotificationUserDialog();
            newFragment.show(getSupportFragmentManager(), "Update");
            customer.requestNotification = false;
        }

        Button btnSearchAddress = findViewById(R.id.btnSearchAddress);
        btnSearchAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText address = findViewById(R.id.address);
                if (address.getText().toString().isEmpty()) {
                    address.setError("Enter an address");
                } else {
                    int results = ((CustomerUser) UserDatabase.loggedInUser).searchThroughAddress(address.getText().toString());
                    Toast.makeText(getApplicationContext(), "Found " + results + " result(s)", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), CustomerSelectEmployee.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void searchTime(View view) {
        EditText from = findViewById(R.id.from);
        if (from.getText().toString().isEmpty()) {
            from.setError("Enter a time");
        } else {
            try {
                int results = ((CustomerUser) UserDatabase.loggedInUser).searchThroughTime(from.getText().toString().concat(":00"));
                Toast.makeText(getApplicationContext(), "Found " + results + " result(s)", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, CustomerSelectEmployee.class);
                startActivity(intent);
            } catch (IllegalArgumentException e) {
                from.setError("Failed to get the time. Example: 12:53");
            }
        }
    }

    public void searchService(View view) {
        EditText service = findViewById(R.id.service);
        if (service.getText().toString().isEmpty()) {
            service.setError("Enter a Service");
        } else {
            int results = ((CustomerUser) UserDatabase.loggedInUser).searchThroughService(service.getText().toString());
            Toast.makeText(getApplicationContext(), "Found " + results + " result(s)", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, CustomerSelectEmployee.class);
            startActivity(intent);
        }
    }

    public void myServices(View view) {
        Intent intent = new Intent(this, CustomerServiceRequests.class);
        startActivity(intent);
    }
}
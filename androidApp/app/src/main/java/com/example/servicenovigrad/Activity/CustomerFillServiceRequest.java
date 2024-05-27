package com.example.servicenovigrad.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.Service;
import com.example.servicenovigrad.ServiceRequest;
import com.example.servicenovigrad.UserDatabase;

import java.util.ArrayList;
import java.util.LinkedList;

public class CustomerFillServiceRequest extends AppCompatActivity {

    private TextView textView;
    private EditText editText;
    private Button button;
    private LinkedList<String> inputs;
    private Service service;
    private final ArrayList<String> customerInputs = new ArrayList<>();
    private String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_fill_service_request);

        setTitle("Fill the Information");
        Bundle extras = getIntent().getExtras();
        String position = extras.getString("position");
        service = UserDatabase.selectedBranch.getServiceAt(Integer.parseInt(position));

        inputs = new LinkedList<>();
        inputs.addAll((ArrayList<String>) service.getInfo().clone());
        inputs.addAll((ArrayList<String>) service.getDocs().clone());

        textView = findViewById(R.id.message);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.btnNext);

        if (!inputs.isEmpty()) {
            value = inputs.removeFirst();
            textView.setText(String.format("Please provide your %s: ", value));
        } else {
            textView.setText(String.format("Please pay these fees: %s", service.getPrice()));
            editText.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            editText.setText(service.getPrice() + "");
            button.setText("Pay");
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
    }

    private void next() {
        if (button.getText().toString().equals("Pay")) {
            try {
                if (editText.getText().toString().isEmpty()) {
                    editText.setError("Enter the amount to pay");
                } else if (Float.parseFloat(editText.getText().toString()) == service.getPrice()) {
                    UserDatabase.currentRequest = new ServiceRequest(UserDatabase.loggedInUser.getUsername(), UserDatabase.selectedBranch.getUsername(), service.getPrice(), customerInputs, service.getName());
                    Intent intent = new Intent(this, AppointmentBookingActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    editText.setError("Please pay the correct amount");
                }
            } catch (NumberFormatException e) {
                editText.setError("Enter a valid price");
            }
        } else if (editText.getText().toString().isEmpty()) {
            editText.setError("Enter your information");
        } else if (inputs.isEmpty()) {
            customerInputs.add(value + ": " + editText.getText().toString());
            textView.setText(String.format("Please pay these fees: %s", service.getPrice()));
            editText.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            editText.setText(String.format("%s", service.getPrice()));
            button.setText("Pay");
        } else {
            customerInputs.add(value + ": " + editText.getText().toString());
            value = inputs.removeFirst();
            textView.setText(String.format("Please provide your %s: ", value));
            editText.setText("");
        }
    }
}
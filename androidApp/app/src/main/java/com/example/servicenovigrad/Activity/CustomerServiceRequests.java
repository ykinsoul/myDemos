package com.example.servicenovigrad.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.servicenovigrad.CustomerUser;
import com.example.servicenovigrad.R;
import com.example.servicenovigrad.Status;
import com.example.servicenovigrad.UserDatabase;
import com.google.android.material.snackbar.Snackbar;

public class CustomerServiceRequests extends AppCompatActivity {

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service_requests);

        final CustomerUser customer = (CustomerUser) UserDatabase.loggedInUser;
        if (!customer.getRequests().isEmpty()) {
            TextView srCus = findViewById(R.id.srCus);
            srCus.setVisibility(View.INVISIBLE);
        }
        ListView mListView = findViewById(R.id.customerR);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, customer.getRequestsName());
        mListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter2, View v, int position, long arg3) {
                if (customer.getRequests().get(position).getStatus().equals(Status.PENDING)) {
                    Snackbar.make(v, "Please wait for the Branch to accept your request before leaving a review.", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                } else {
                    if (customer.getRequests().get(position).getReviewed()) {
                        Snackbar.make(v, "You have already reviewed this request.", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), BranchReviewActivity.class);
                        intent.putExtra("position", position + "");
                        startActivity(intent);
                    }
                }
            }
        });
    }

    public void refresh() {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

}
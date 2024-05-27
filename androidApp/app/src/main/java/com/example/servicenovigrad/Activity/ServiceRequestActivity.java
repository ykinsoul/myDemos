package com.example.servicenovigrad.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.servicenovigrad.Dialog.ServiceRequestDialog;
import com.example.servicenovigrad.R;
import com.example.servicenovigrad.ServiceProviderUser;
import com.example.servicenovigrad.UserDatabase;

public class ServiceRequestActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private ServiceProviderUser employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_request);

        employee = (ServiceProviderUser) UserDatabase.loggedInUser;
        if (!employee.getRequests().isEmpty()) {
            TextView srText = findViewById(R.id.srText);
            srText.setVisibility(View.INVISIBLE);
        }
        ListView mListView = findViewById(R.id.customerServiceRequests);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, employee.getRequestsName());
        mListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter2, View v, int position,
                                    long arg3) {
                DialogFragment dialog = new ServiceRequestDialog();
                dialog.show(getSupportFragmentManager(), String.valueOf(position));
            }
        });
    }

    public void refresh() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, employee.getRequestsName());
        adapter.notifyDataSetChanged();
        recreate();
        //shows visual glitch but couldn't find another way to refresh it. MF.
    }

}
package com.example.servicenovigrad.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.UserDatabase;

public class CustomerSelectService extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_select_service);

        setTitle("Available Services");
        ListView mListView = findViewById(R.id.customerSelection);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, UserDatabase.selectedBranch.getRegisteredServicesName());
        mListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter2, View v, int position,
                                    long arg3) {
                select(position);
            }
        });
    }

    private void select(int i) {
        Intent intent = new Intent(this, CustomerFillServiceRequest.class);
        intent.putExtra("position", i + "");
        startActivity(intent);
        finish();
    }

}
package com.example.servicenovigrad.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.servicenovigrad.CustomerUser;
import com.example.servicenovigrad.R;
import com.example.servicenovigrad.ServiceProviderUser;
import com.example.servicenovigrad.UserDatabase;
import com.example.servicenovigrad.UserList;

public class CustomerSelectEmployee extends AppCompatActivity {

    UserList users = new UserList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_select_employee);

        setTitle("Available Branches");

        ListView mListView = findViewById(R.id.employeeSelection);
        users = ((CustomerUser) UserDatabase.loggedInUser).getCurrentViewedBranches();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users.getUsersName());
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
        Intent intent = new Intent(this, CustomerSelectService.class);
        UserDatabase.selectedBranch = (ServiceProviderUser) users.get(i);
        startActivity(intent);
        finish();
    }
}
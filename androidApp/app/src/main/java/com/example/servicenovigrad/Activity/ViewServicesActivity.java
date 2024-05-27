package com.example.servicenovigrad.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.UserDatabase;

public class ViewServicesActivity extends AppCompatActivity {

    private ArrayAdapter<String> serviceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_services);

        ListView mListView = findViewById(R.id.ListView);
        serviceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, UserDatabase.services.getList());
        mListView.setAdapter(serviceAdapter);
        serviceAdapter.notifyDataSetChanged();
        registerForContextMenu(mListView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.editservice_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        switch (item.getItemId()) {
            case R.id.edit:
                UserDatabase.serviceToUpdate = UserDatabase.services.get(index);
                Intent serviceIntent = new Intent(this, ServiceViewActivity.class);
                startActivity(serviceIntent);
                break;
            case R.id.delete:
                UserDatabase.services.delete(index);
                serviceAdapter.notifyDataSetChanged();
                break;
        }
        return false;
    }

    public void addService(View view) {
        Intent serviceIntent = new Intent(this, ServiceViewActivity.class);
        startActivity(serviceIntent);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        serviceAdapter.notifyDataSetChanged();
    }
}
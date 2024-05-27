package com.example.servicenovigrad.Activity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.servicenovigrad.Adapter.UserListAdapter;
import com.example.servicenovigrad.R;
import com.example.servicenovigrad.UserDatabase;

public class ViewUsersActivity extends AppCompatActivity {

    private UserListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);

        ListView mListView = findViewById(R.id.ListView);
        adapter = new UserListAdapter(this, R.layout.adapter_view_layout, UserDatabase.users.getList());
        mListView.setAdapter(adapter);
        registerForContextMenu(mListView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        if (UserDatabase.users.get(info.position).getRole().equals("Employee") || UserDatabase.users.get(info.position).getRole().equals("Customer")) {
            super.onCreateContextMenu(menu, v, menuInfo);
            getMenuInflater().inflate(R.menu.edituser_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        adapter.remove(UserDatabase.users.get(info.position));
        adapter.notifyDataSetChanged();
        return false;
    }
}
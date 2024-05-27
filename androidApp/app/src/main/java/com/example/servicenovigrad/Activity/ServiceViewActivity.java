package com.example.servicenovigrad.Activity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.Service;
import com.example.servicenovigrad.UserDatabase;

import java.util.ArrayList;

public class ServiceViewActivity extends AppCompatActivity {
    private final ArrayList<String> informationList = new ArrayList<>();
    private final ArrayList<String> documentList = new ArrayList<>();
    private ArrayAdapter<String> informationAdapter;
    private ArrayAdapter<String> documentAdapter;
    private ArrayAdapter<String> selectedListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceview);

        ListView infoList = findViewById(R.id.infolist);
        informationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, informationList);
        infoList.setAdapter(informationAdapter);
        registerForContextMenu(infoList);

        ListView docList = findViewById(R.id.docList);
        documentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, documentList);
        docList.setAdapter(documentAdapter);
        registerForContextMenu(docList);

        if (UserDatabase.serviceToUpdate != null) {
            TextView service = findViewById(R.id.servicename);
            service.setText(UserDatabase.serviceToUpdate.getName());

            TextView priceText = findViewById(R.id.priceText);
            priceText.setText(String.format("%s", UserDatabase.serviceToUpdate.getPrice()));
            informationList.addAll(UserDatabase.serviceToUpdate.getInfo());
            documentList.addAll(UserDatabase.serviceToUpdate.getDocs());

        }
    }

    public void addInformation(View view) {
        TextView infoText = findViewById(R.id.itemtoadd);
        if (infoText.getText().toString().isEmpty()) {
            infoText.setError("Add text");
            return;
        }
        informationList.add(infoText.getText().toString());
        informationAdapter.notifyDataSetChanged();
    }

    public void addDocument(View view) {
        TextView docText = findViewById(R.id.itemtoadd);
        if (docText.getText().toString().isEmpty()) {
            docText.setError("Add text");
            return;
        }
        documentList.add(docText.getText().toString());
        documentAdapter.notifyDataSetChanged();
    }

    public void updateService(View view) {
        EditText service = findViewById(R.id.servicename);
        EditText priceText = findViewById(R.id.priceText);
        if (service.getText().toString().isEmpty()) {
            service.setError("Enter a name");
        } else if (priceText.getText().toString().isEmpty()) {
            service.setError("Enter a price");
        } else if (UserDatabase.services.nameExists(service.getText().toString()) && UserDatabase.serviceToUpdate == null) {
            Toast.makeText(getApplicationContext(), "Service already exists", Toast.LENGTH_SHORT).show();
        } else if (UserDatabase.serviceToUpdate != null && !UserDatabase.serviceToUpdate.getName()
                .equals((service.getText().toString())) && UserDatabase.services.nameExists(service.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Service already exists", Toast.LENGTH_SHORT).show();
        } else {
            Service newService = new Service(service.getText().toString(), Float.parseFloat(priceText.getText()
                    .toString().replace("$", "")));
            newService.addAllInfo(informationList);
            newService.addAllDocs(documentList);

            if (UserDatabase.serviceToUpdate != null) {
                UserDatabase.services.edit(newService, UserDatabase.services.indexAt(UserDatabase.serviceToUpdate));
                UserDatabase.serviceToUpdate = null;
            } else {
                UserDatabase.services.add(newService);
            }
            super.onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        UserDatabase.serviceToUpdate = null;
        super.onBackPressed();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.infolist) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.edituser_menu, menu);
        }
        if (v.getId() == R.id.docList) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.edituser_menu, menu);
        }
        ListView selectedListView = (ListView) v;
        selectedListViewAdapter = (ArrayAdapter<String>) selectedListView.getAdapter();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        selectedListViewAdapter.remove(selectedListViewAdapter.getItem(index));
        selectedListViewAdapter.notifyDataSetChanged();
        return false;
    }

}
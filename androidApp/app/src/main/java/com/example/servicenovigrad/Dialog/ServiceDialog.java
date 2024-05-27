package com.example.servicenovigrad.Dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.Service;
import com.example.servicenovigrad.ServiceProviderUser;
import com.example.servicenovigrad.UserDatabase;

import java.util.ArrayList;

public class ServiceDialog extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.service_layout, container, false);

        Service service = ((ServiceProviderUser) UserDatabase.loggedInUser).getRegisteredServices().get(Integer.parseInt(getTag()));

        TextView name = view.findViewById(R.id.serviceTextView);
        name.setText(service.getName());

        TextView price = view.findViewById(R.id.priceTextView);
        price.setText(String.format("$%s", service.getPrice()));

        ListView infoListView = view.findViewById(R.id.informationListView);
        ArrayList<String> infoList = service.getInfo();
        ArrayAdapter<String> informationAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, infoList);
        infoListView.setAdapter(informationAdapter);

        ListView docListView = view.findViewById(R.id.documentListView);
        ArrayList<String> docList = service.getDocs();
        ArrayAdapter<String> documentAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, docList);
        docListView.setAdapter(documentAdapter);
        return view;
    }
}
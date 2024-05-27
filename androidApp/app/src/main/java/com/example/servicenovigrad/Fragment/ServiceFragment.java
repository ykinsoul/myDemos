package com.example.servicenovigrad.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.servicenovigrad.Activity.EmployeeActivity;
import com.example.servicenovigrad.Activity.ServiceRequestActivity;
import com.example.servicenovigrad.Dialog.ServiceDialog;
import com.example.servicenovigrad.R;
import com.example.servicenovigrad.ServiceProviderUser;
import com.example.servicenovigrad.UserDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ServiceFragment extends Fragment {
    private final ArrayList<Integer> temp = new ArrayList<>();
    private final ServiceProviderUser employee = (ServiceProviderUser) UserDatabase.loggedInUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.service_frag_layout, container, false);

        if (!employee.isServiceEmpty()) {
            TextView rsText = v.findViewById(R.id.rsText);
            rsText.setVisibility(View.INVISIBLE);
        }

        ListView mListView = v.findViewById(R.id.serviceProfileList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, employee.getRegisteredServicesName());
        mListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3) {
                DialogFragment dialog = new ServiceDialog();
                dialog.show(getFragmentManager(), String.valueOf(position));
                //here I sent the position as a tag, probably not the best solution
            }
        });

        FloatingActionButton addServiceCheck = v.findViewById(R.id.addServiceCheck);
        addServiceCheck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showServiceDialog();
            }
        });

        Button serviceRequestButton = v.findViewById(R.id.serviceRequestButton);
        serviceRequestButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ServiceRequestActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

    private void showServiceDialog() {
        AlertDialog.Builder serviceDialog = new AlertDialog.Builder(getContext());
        serviceDialog.setTitle("Available Services");
        serviceDialog.setMultiChoiceItems(UserDatabase.services.getArray(), null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                if (isChecked) {
                    temp.add(position);
                } else {
                    temp.remove((Integer) position);
                }
            }
        });
        serviceDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                employee.clearServices();
                for (Integer integer : temp) {
                    employee.addService(UserDatabase.services.get(integer));
                }
                ((EmployeeActivity) requireActivity()).refreshPage();
            }
        });
        serviceDialog.setNegativeButton("Cancel", null);
        AlertDialog alert = serviceDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

}
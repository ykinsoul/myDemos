package com.example.servicenovigrad.Dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.servicenovigrad.Activity.ServiceRequestActivity;
import com.example.servicenovigrad.R;
import com.example.servicenovigrad.ServiceProviderUser;
import com.example.servicenovigrad.ServiceRequest;
import com.example.servicenovigrad.UserDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ServiceRequestDialog extends DialogFragment {
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.requested_service_layout, container, false);
        final ServiceProviderUser employee = (ServiceProviderUser) UserDatabase.loggedInUser;
        final ServiceRequest service = employee.getRequests().get(Integer.parseInt(getTag()));
        TextView name = view.findViewById(R.id.serviceTextView2);
        name.setText(String.format("Requesting: %s", service.getServiceName()));

        TextView price = view.findViewById(R.id.priceTextView2);
        price.setText(String.format("Paid: $%s", service.getPaid()));

        TextView appointmentDateText = view.findViewById(R.id.appointmentDateText);
        appointmentDateText.setText(String.format("%s at %s", service.getAppointmentDate(), service.getAppointmentTime()));

        ListView infoListView = view.findViewById(R.id.informationListView2);
        ArrayList<String> infoList = service.getCustomerInputs();
        ArrayAdapter<String> informationAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, infoList);
        infoListView.setAdapter(informationAdapter);

        Button acceptButton = view.findViewById(R.id.acceptButton);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                employee.accept(service);
                ((ServiceRequestActivity) requireActivity()).refresh();
                Toast.makeText(getContext(), "Request from " + service.getCustomerUsername() + " accepted", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        Button declineButton = view.findViewById(R.id.declineButton);
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                employee.refuse(service);
                ((ServiceRequestActivity) requireActivity()).refresh();
                Toast.makeText(getContext(), "Request from " + service.getCustomerUsername() + " declined", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
        return view;
    }
}
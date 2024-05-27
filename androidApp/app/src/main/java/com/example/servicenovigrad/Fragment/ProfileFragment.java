package com.example.servicenovigrad.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.servicenovigrad.Profile;
import com.example.servicenovigrad.R;
import com.example.servicenovigrad.ServiceProviderUser;
import com.example.servicenovigrad.UserDatabase;

public class ProfileFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile_frag_layout, container, false);
        ServiceProviderUser employee = (ServiceProviderUser) UserDatabase.loggedInUser;

        TextView emailTextView = v.findViewById(R.id.emailtextview);
        emailTextView.setText(employee.getEmail());

        LinearLayout profileLayout = v.findViewById(R.id.profileLayout);

        Profile profile = employee.getProfile();
        profileLayout.setVisibility(View.VISIBLE);
        TextView nameView = v.findViewById(R.id.nameView);
        nameView.setText(profile.getFirstName());
        TextView lastNameView = v.findViewById(R.id.lastnameView);
        lastNameView.setText(profile.getLastName());
        TextView addressView = v.findViewById(R.id.addressView);
        addressView.setText(profile.getAddressLine());
        TextView cityView = v.findViewById(R.id.cityView);
        cityView.setText(profile.getCity());
        TextView provinceView = v.findViewById(R.id.provinceView);
        provinceView.setText(profile.getProvince());
        TextView postalCodeView = v.findViewById(R.id.postalCodeView);
        postalCodeView.setText(profile.getPostalCode());
        TextView numberView = v.findViewById(R.id.numberView);
        numberView.setText(profile.getPhoneNumber());
        return v;
    }
}

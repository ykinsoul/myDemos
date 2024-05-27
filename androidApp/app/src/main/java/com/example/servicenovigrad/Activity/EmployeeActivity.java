package com.example.servicenovigrad.Activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

import com.example.servicenovigrad.Adapter.SectionsPagerAdapter;
import com.example.servicenovigrad.Dialog.ProfileDialog;
import com.example.servicenovigrad.R;
import com.example.servicenovigrad.ServiceProviderUser;
import com.example.servicenovigrad.UserDatabase;
import com.google.android.material.tabs.TabLayout;

public class EmployeeActivity extends AppCompatActivity {

    private final SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        TextView title = findViewById(R.id.title);
        title.setText(String.format("Welcome %s", UserDatabase.loggedInUser.getUsername()));

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        ServiceProviderUser employee = (ServiceProviderUser) UserDatabase.loggedInUser;
        if (!employee.getCompleted()) {
            DialogFragment newFragment = new ProfileDialog();
            newFragment.show(getSupportFragmentManager(), "Profile");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshPage();
    }

    public void refreshPage() {
        sectionsPagerAdapter.notifyDataSetChanged();
    }

}
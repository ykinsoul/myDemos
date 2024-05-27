package com.example.servicenovigrad.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.User;

import java.util.ArrayList;

//followed this tutorial for custom ArrayAdapters https://www.youtube.com/watch?v=E6vE8fqQPTE&t=207s
public class UserListAdapter extends ArrayAdapter<User> {
    private final Context mContext;
    int mResource;

    public UserListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<User> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView textViewRole = convertView.findViewById(R.id.textView1);
        TextView textViewUsername = convertView.findViewById(R.id.textView2);
        TextView textViewPassword = convertView.findViewById(R.id.textView3);

        textViewRole.setText(String.format("Role: %s", getItem(position).getRole()));
        textViewUsername.setText(String.format("Username: %s", getItem(position).getUsername()));
        textViewPassword.setText(String.format("Password: %s", getItem(position).getPassword()));

        return convertView;
    }
}

package com.example.servicenovigrad.Fragment;

import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.servicenovigrad.Constants;
import com.example.servicenovigrad.R;
import com.example.servicenovigrad.ServiceProviderUser;
import com.example.servicenovigrad.UserDatabase;

import java.sql.Time;

public class HoursFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.hours_frag_layout, container, false);

        ListView listDays = v.findViewById(R.id.listDays);
        ArrayAdapter<String> daysAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, Constants.WEEK);
        listDays.setAdapter(daysAdapter);

        final ServiceProviderUser employee = (ServiceProviderUser) UserDatabase.loggedInUser;
        ListView listHoursFrom = v.findViewById(R.id.listHoursFrom);
        final ArrayAdapter<Time> hoursAdapterFrom = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, employee.getWorkingHoursFrom());
        listHoursFrom.setAdapter(hoursAdapterFrom);

        ListView listHoursTo = v.findViewById(R.id.listHoursTo);
        final ArrayAdapter<Time> hoursAdapterTo = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, employee.getWorkingHoursTo());
        listHoursTo.setAdapter(hoursAdapterTo);

        listHoursFrom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, final int position, long arg3) {
                //code from https://stackoverflow.com/questions/17901946/timepicker-dialog-from-clicking-edittext
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Time tme = new Time(selectedHour, selectedMinute, 0);
                        if (!employee.getWorkingHoursTo().get(position).toString().equals("00:00:00") && employee.getWorkingHoursTo().get(position).before(tme)) {
                            Toast.makeText(getContext(), "Invalid time", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        employee.getWorkingHoursFrom().set(position, tme);
                        hoursAdapterFrom.notifyDataSetChanged();
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Opening Time");
                mTimePicker.show();
            }
        });

        listHoursTo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, final int position, long arg3) {
                //code from https://stackoverflow.com/questions/17901946/timepicker-dialog-from-clicking-edittext
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Time tme = new Time(selectedHour, selectedMinute, 0);
                        if (employee.getWorkingHoursFrom().get(position).after(tme)) {
                            Toast.makeText(getContext(), "Invalid time", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        employee.getWorkingHoursTo().set(position, tme);
                        hoursAdapterTo.notifyDataSetChanged();
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Closing Time");
                mTimePicker.show();
            }
        });

        return v;
    }

}

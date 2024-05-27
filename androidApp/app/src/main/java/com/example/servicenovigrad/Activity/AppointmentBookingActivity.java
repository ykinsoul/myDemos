package com.example.servicenovigrad.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.servicenovigrad.Constants;
import com.example.servicenovigrad.R;
import com.example.servicenovigrad.UserDatabase;
import com.google.android.material.snackbar.Snackbar;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AppointmentBookingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private String date;
    private TextView dateText;
    private TextView timeSelect;
    private int dayOfWeek;
    private Spinner spinnerDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_booking);

        timeSelect = findViewById(R.id.timeSelect);
        dateText = findViewById(R.id.dateText);
        spinnerDate = findViewById(R.id.dateSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Constants.WEEK);
        spinnerDate.setAdapter(adapter);
        spinnerDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = findViewById(R.id.availableTime);
                tv.setText(String.format("%s to %s", UserDatabase.selectedBranch.getWorkingHoursFrom().get(position).toString().substring(0, 5), UserDatabase.selectedBranch.getWorkingHoursTo().get(position).toString().substring(0, 5)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button selectTime = findViewById(R.id.selectTime);
        selectTime.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(final View v) {
                if (dateText.getText().toString().isEmpty()) {
                    Snackbar.make(v, "Select a date before selecting a time", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    return;
                }
                android.icu.util.Calendar mcurrentTime = android.icu.util.Calendar.getInstance();
                int hour = mcurrentTime.get(android.icu.util.Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(android.icu.util.Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AppointmentBookingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Time tme = new Time(selectedHour, selectedMinute, 0);
                        Time from = UserDatabase.selectedBranch.getWorkingHoursFrom().get(dayOfWeek);
                        Time to = UserDatabase.selectedBranch.getWorkingHoursTo().get(dayOfWeek);
                        if (tme.before(from) || tme.after(to)) {
                            Toast.makeText(AppointmentBookingActivity.this, "Invalid time", Toast.LENGTH_SHORT).show();
                        } else {
                            timeSelect.setText(tme.toString().substring(0, 5));
                        }
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select appointment time");
                mTimePicker.show();
            }
        });
    }

    //https://github.com/mitchtabian/DatePickerDialog-Example/blob/master/app/src/main/java/com/codingwithmitch/datepickerdialogexample/MainActivity.java
    public void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month++;
        SimpleDateFormat format = new SimpleDateFormat("dd/M/yyyy");
        date = dayOfMonth + "/" + month + "/" + year;
        dateText.setText(date);
        try {
            Date c = format.parse(dateText.getText().toString());
            dayOfWeek = c.getDay();
            if (dayOfWeek != 0) {
                dayOfWeek = dayOfWeek - 1;
            } else {
                dayOfWeek = 6;
            }
            spinnerDate.setSelection(dayOfWeek);
            Toast.makeText(AppointmentBookingActivity.this, "Selected " + Constants.WEEK.get(dayOfWeek), Toast.LENGTH_SHORT).show();
        } catch (ParseException ignored) {
        }
        timeSelect.setText("");
    }

    public void dateSelect(View view) {
        showDatePickerDialog();
    }

    public void confirmRequest(View view) {
        if (dateText.getText().toString().isEmpty()) {
            Snackbar.make(view, "Select a date before submitting", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        } else if (timeSelect.getText().toString().isEmpty()) {
            Snackbar.make(view, "Select a time before submitting", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        } else {
            UserDatabase.currentRequest.setAppointmentDate(date);
            UserDatabase.currentRequest.setAppointmentTime(timeSelect.getText().toString());
            UserDatabase.requests.request(UserDatabase.currentRequest);
            Toast.makeText(getApplicationContext(), "Your request was submitted", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}
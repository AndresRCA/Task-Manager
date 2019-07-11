package com.example.taskmanager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;

public class EditTask extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;

    private TextView edit_date;
    private TextView edit_time;
    private EditText edit_description;

    private int id;
    private String date;
    private String time;

    private int year;
    private int month;
    private int day;
    private int hours;
    private int minutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        edit_description = findViewById(R.id.edit_description);
        edit_date = findViewById(R.id.edit_date);
        edit_time = findViewById(R.id.edit_time);

        Bundle extras = getIntent().getExtras();
        edit_description.setText(extras.getString("description"));
        edit_date.setText(extras.getString("date"));
        edit_time.setText(extras.getString("time"));
        id = extras.getInt("id");

        // Date picker callback when a date is added
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++; // months starts with 0 (Jan = 0)
                EditTask.this.year = year;
                EditTask.this.month = month;
                EditTask.this.day = dayOfMonth;

                Toast.makeText(EditTask.this, "date: "+year+"/"+month+"/"+dayOfMonth, Toast.LENGTH_SHORT).show();
                EditTask.this.edit_date.setText(year+"-"+month+"-"+dayOfMonth);
            }
        };

        // Time picker callback when a time is added
        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                EditTask.this.hours = hourOfDay;
                EditTask.this.minutes = minute;
                Toast.makeText(EditTask.this, hourOfDay+":"+minute, Toast.LENGTH_SHORT);
                EditTask.this.edit_time.setText(hourOfDay+":"+minute);
            }
        };
    }

    /**
     * metodo que inicia el fragmento de fecha
     * @param view
     */
    public void changeDate(View view) {
        //int[] y_m_d = MyTime.getDateOrTime(date, "-");
        Calendar cal = Calendar.getInstance(); // instead of this, show current year, month and day from sql
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getDatePicker().setMinDate(cal.getTimeInMillis() - 1000); // no idea why it shows last month and day above current time
        dialog.show();
    }

    /**
     * metodo que inicia el fragmento de tiempo
     * @param view
     */
    public void changeTime(View view) {
        //int[] hour_min_sec = MyTime.getDateOrTime(time, ":");
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        Toast.makeText(this, hour+":"+minute, Toast.LENGTH_SHORT);
        TimePickerDialog dialog = new TimePickerDialog(this, mTimeSetListener, hour, minute, DateFormat.is24HourFormat(this));
        dialog.show(); // show time dialog
    }

    /**
     * metodo que aplica los cambios actualizando la base de datos y se dirige a la actividad principal
     * @param view
     */
    public void applyChanges(View view) {
        String new_description = edit_description.getText().toString();
        if(new_description == "") {
            Toast.makeText(this, "description can't be empty", Toast.LENGTH_SHORT);
            return;
        }
        String new_complete_time = edit_date.getText().toString()+" "+edit_time.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("id", id);
        intent.putExtra("description", new_description);
        intent.putExtra("complete_time", new_complete_time);

        ContentValues cv = new ContentValues();
        cv.put(TaskOpenHelper.KEY_TASK, new_description);
        cv.put(TaskOpenHelper.KEY_COMPLETE_TIME, new_complete_time);
        MainActivity.dbHelper.update(cv, id);
        setResult(RESULT_OK, intent);
        finish();
    }
}

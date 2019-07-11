package com.example.taskmanager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ContentValues;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AddTask extends AppCompatActivity {

	private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;

    private TextView add_date;
    private TextView add_time;
    private EditText add_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        add_description = findViewById(R.id.add_description);
        add_date = findViewById(R.id.add_date);
        add_time = findViewById(R.id.add_time);

        // Date picker callback when a date is added
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++; // months starts with 0 (Jan = 0)              
                AddTask.this.add_date.setText(year+"-"+month+"-"+dayOfMonth);
            }
        };

        // Time picker callback when a time is added
        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                AddTask.this.add_time.setText(hourOfDay+":"+minute);
            }
        };
    }

    /**
     * metodo que inicia el fragmento de fecha
     * @param view
     */
    public void addDate(View view) {
        //int[] y_m_d = getDateOrTime(date, "-");
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
    public void addTime(View view) {
        //int[] hour_min_sec = getDateOrTime(time, ":");
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        Toast.makeText(this, hour+":"+minute, Toast.LENGTH_SHORT);
        TimePickerDialog dialog = new TimePickerDialog(this, mTimeSetListener, hour, minute, DateFormat.is24HourFormat(this));
        dialog.show(); // show time dialog
    }

    /**
     * metodo que agrega la tarea a la base de datos
     * @param view
     */
    public void addTask(View view) {
        String description = add_description.getText().toString();
        if(description == "") {
            Toast.makeText(this, "description can't be empty", Toast.LENGTH_SHORT);
            return;
        }
        String complete_time = add_date.getText().toString()+" "+add_time.getText().toString();
        ContentValues cv = new ContentValues();
        cv.put(TaskOpenHelper.KEY_TASK, description);
        cv.put(TaskOpenHelper.KEY_COMPLETE_TIME, complete_time);
        int id = (int) MainActivity.dbHelper.insert(cv);
        if(id == 0) return;

        Intent intent = new Intent();
        intent.putExtra("id", id);
        intent.putExtra("description", description);
        intent.putExtra("created_at", MyTime.getDateTime());
        intent.putExtra("complete_time", complete_time);
        // is_completed is default 0
        setResult(RESULT_OK, intent);
        finish();
    }
}

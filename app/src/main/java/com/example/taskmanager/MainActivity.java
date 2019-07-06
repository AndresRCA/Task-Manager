package com.example.taskmanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private final static int ADD_TASK = 1;
    private final static int EDIT_TASK = 2;
    private TaskOpenHelper dbHelper;
    private ArrayList<Task> task_data;
    private RecyclerView recyclerView;
    private TaskAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // lleno task_data con la base de datos
        dbHelper = new TaskOpenHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tasks ORDER BY "+TaskOpenHelper.KEY_START_TIME,null); // selecciona todos en orden ascendente por tiempo de creacion
        task_data = new ArrayList<>();
        while(cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(TaskOpenHelper.KEY_ID));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(TaskOpenHelper.KEY_TASK));
            String created_at = cursor.getString(cursor.getColumnIndexOrThrow(TaskOpenHelper.KEY_START_TIME));
            String complete_time = cursor.getString(cursor.getColumnIndexOrThrow(TaskOpenHelper.KEY_COMPLETE_TIME));
            int bit = cursor.getInt(cursor.getColumnIndexOrThrow(TaskOpenHelper.KEY_IS_COMPLETED));
            boolean is_completed = (bit == 1);
            task_data.add(new Task(id, description, created_at, complete_time, is_completed));
        }
        /* aqui lo habia llenado sin la base de datos */
        //String date = DateFormat.getDateTimeInstance().format(new Date()); // if something breaks, it's here
        //task_data = new Task[]{new Task(1, "Tarea 1", date, date, true), new Task(2, "Tarea 2", date, date, false)};

        // specify an adapter (see also next example)
        mAdapter = new TaskAdapter(task_data);
        recyclerView.setAdapter(mAdapter);

        // toolbar and fab
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddTask.class);
                startActivityForResult(intent, ADD_TASK);
            }
        });
    }

    public void onActivityResult(int request_code, int result_code, Intent data) {
        super.onActivityResult(request_code, result_code, data);
        // update db and adapter
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_completed_tasks) {
            Intent intent = new Intent(this, CompletedTasks.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Metodo que inicia la actividad EditTask para editar un Task en especifico
     * @param view
     */
    public void goToEdit(View view) {
        ViewGroup parent = (ViewGroup) view.getParent();
        CheckBox checkBox = (CheckBox) parent.getChildAt(1); // CheckBox is in position 1
        if(checkBox.isChecked()) return; // if it's checked, don't edit

        Intent intent = new Intent(this, EditTask.class);
        int id = parent.getId(); // the id of the task essentially
        intent.putExtra("id", id); // this will tell me which task to edit
        startActivityForResult(intent, EDIT_TASK);
    }

    /**
     * Metodo que actualiza la base de datos y el adaptador cuando edito es status del task
     * @param view
     */
    public void onCheckBoxClicked(View view) {
        CheckBox checkBox = (CheckBox) view;
        int id = ((ViewGroup) checkBox.getParent()).getId();
        if(checkBox.isChecked()) {
            // update db, replace created_at for complete_time

        }
        else {
            checkBox.setChecked(true); // in a way I basically keep the completed status on
        }
    }
}

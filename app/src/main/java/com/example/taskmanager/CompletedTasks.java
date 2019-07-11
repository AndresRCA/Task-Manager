package com.example.taskmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CompletedTasks extends AppCompatActivity {

	private ArrayList<Task> task_data;
    private RecyclerView recyclerView;
    private TaskAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_tasks);

        recyclerView = findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // lleno task_data con la base de datos
        Cursor cursor = Main.Activity.dbHelper.query("SELECT * FROM tasks WHERE is_completed = 1", TaskOpenHelper.READ);     
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
        cursor.close();

        // specify an adapter
        mAdapter = new TaskAdapter(task_data);
        recyclerView.setAdapter(mAdapter);
    }
}

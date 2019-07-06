package com.example.taskmanager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {
    private ArrayList<Task> task_data;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView description;
        public CheckBox is_completed;
        public TextView date;
        public TextView time;
        public MyViewHolder(View v) {
            super(v);
            description = v.findViewById(R.id.description);
            is_completed = v.findViewById(R.id.is_completed);
            date = v.findViewById(R.id.date);
            time = v.findViewById(R.id.time);
        }
    }

    public TaskAdapter(ArrayList<Task> task_data) {
        this.task_data = task_data;
    }

    @Override
    public TaskAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(TaskAdapter.MyViewHolder holder, int i) {
        holder.description.setText(task_data.get(i).getDescription());
        holder.is_completed.setChecked(task_data.get(i).getIs_completed());
        holder.date.setText(task_data.get(i).getComplete_time()); // for now I wont separate time and date
        holder.time.setText(task_data.get(i).getComplete_time());
    }

    @Override
    public int getItemCount() {
        return task_data.size();
    }
}


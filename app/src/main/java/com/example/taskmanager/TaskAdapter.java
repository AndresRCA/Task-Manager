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

        public TextView description;
        public TextView date;
        public TextView time;
        public CheckBox is_completed;

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
        holder.itemView.setId(task_data.get(i).getId()); // the id will tell which checkbox is checked and which task to edit

        holder.description.setText(task_data.get(i).getDescription());
        holder.date.setText(getDate(task_data.get(i).getComplete_time()));
        holder.time.setText(getTime(task_data.get(i).getComplete_time()));
        holder.is_completed.setChecked(task_data.get(i).getIs_completed());
    }

    /**
     * metodo que retorna yyyy-mm-dd
     * @param full_date
     * @return
     */
    private String getDate(String full_date) {
        return full_date.split(" ")[0];
    }

    /**
     * metodo que retorna HH:MM:SS
     * @param full_date
     * @return
     */
    private String getTime(String full_date) {
        return full_date.split(" ")[1];
    }

    @Override
    public int getItemCount() {
        return task_data.size();
    }
}


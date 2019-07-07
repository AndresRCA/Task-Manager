package com.example.taskmanager;

import android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TaskOpenHelper extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 1;
    private static final String TASKS_TABLE = "tasks";
    private static final String DATABASE_NAME = "Tasks_DB";

    // Column names...
    public final static String KEY_ID = "id"; // this is automatic
    public final static String KEY_TASK = "description";
    public final static String KEY_START_TIME = "created_at"; // this is automatic
    public final static String KEY_COMPLETE_TIME = "complete_time";
    public final static String KEY_IS_COMPLETED = "is_completed";

    // CREATE TABLE tasks (id INTEGER PRIMARY KEY, description TEXT, created_at TIMESTAMP, complete_time TIMESTAMP, is_completed BIT); me faltan las demas columnas
    private static final String TASKS_TABLE_CREATE = "CREATE TABLE "+TASKS_TABLE+
            " ("+KEY_ID+" INTEGER PRIMARY KEY, "
            +KEY_TASK+" TEXT, "
            +KEY_START_TIME+" TIMESTAMP, "
            +KEY_COMPLETE_TIME+" TIMESTAMP, "
            +KEY_IS_COMPLETED+" BIT NOT NULL DEFAULT 0);"; // a boolean basically

    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;

    public TaskOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TASKS_TABLE_CREATE);
        fillDatabaseWithData(db);
    }

    /**
     * Metodo que rellena la base de datos al momento de crearla por primera vez
     * @param db
     */
    private void fillDatabaseWithData(SQLiteDatabase db) {
        String date_time = getDateTime();

        // Create a container for the data.
        ContentValues values = new ContentValues();
        // Aqui ingreso lo necesario para dos tasks
        for (int i = 0; i < 2; i++) {
            // Put column/value pairs into the container.
            // put() overrides existing values.
            values.put(KEY_TASK, "Tarea "+(i+1));
            values.put(KEY_START_TIME, date_time);
            values.put(KEY_COMPLETE_TIME, date_time);
            //values.put(KEY_IS_COMPLETED, tasks[i].getIs_completed());
            db.insert(TASKS_TABLE, null, values);
        }

    }

    /**
     * Metodo que retorna el timestamp actual en el formato de la tabla de la base de datos
     * @return
     */
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

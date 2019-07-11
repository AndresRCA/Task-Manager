package com.example.taskmanager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyTime {

	/**
     * Metodo que retorna el timestamp actual en el formato de la tabla de la base de datos
     * @return
     */
    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * metodo que retorna los numeros dentro del string yyyy-mm-dd o HH:MM:SS     
     * @param  info  puede ser yyyy-mm-dd o HH:MM:SS
     * @param  regex puede ser - o :
     * @return int[] date_numbers = {yyyy, mm, dd} o {HH:MM:SS}
     */
    public static int[] getDateOrTime(String info, String regex) {
        String[] date_info = info.split(regex);
        int i1 = Integer.parseInt(date_info[0]);
        int i2 = Integer.parseInt(date_info[1]);
        int i3 = Integer.parseInt(date_info[2]);
        int[] date_numbers = {i1, i2, i3};
        return date_numbers;
    }

	/**
     * metodo que retorna yyyy-mm-dd
     * @param full_date yyyy-mm-dd HH:MM:SS
     * @return
     */
    public static String getDate(String full_date) {
        return full_date.split(" ")[0];
    }

    /**
     * metodo que retorna HH:MM:SS
     * @param full_date yyyy-mm-dd HH:MM:SS
     * @return
     */
    public static String getTime(String full_date) {
        return full_date.split(" ")[1];
    }
}
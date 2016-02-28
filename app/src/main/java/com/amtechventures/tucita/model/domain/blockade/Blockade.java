package com.amtechventures.tucita.model.domain.blockade;


import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

@ParseClassName("Blockade")
public class Blockade extends ParseObject{

    List<Date> days;

    public String getType(){

        return getString(BlockadeAttributes.type);

    }

    public Date getDate(){

        return getDate(BlockadeAttributes.date);

    }

    public List<String> getDataArray(){

        return (List<String>) get(BlockadeAttributes.dataArray);

    }

    public List<Date> getDates() {

        if(days == null) {

            days = new ArrayList<>();

            List<String>  data = getDataArray();

            for (String day : data) {

                StringTokenizer stringTokenizer = new StringTokenizer(day);

                Calendar calendar = Calendar.getInstance();

                int dayInt = Integer.parseInt(stringTokenizer.nextToken());

                int month = Integer.parseInt(stringTokenizer.nextToken());

                int year = Integer.parseInt(stringTokenizer.nextToken());

                calendar.set(year, month, dayInt);

                Log.i("date "+calendar.getTime().toLocaleString(), "fecga");

                days.add(calendar.getTime());

            }

        }

       return days;

    }

    public void putType(String type){

        put(BlockadeAttributes.type, type);

    }

    public void putDate(Date date){

        put(BlockadeAttributes.date, date);

    }

    public void putDataArray(List<String> dataArray){

        addAllUnique(BlockadeAttributes.dataArray, dataArray);

    }

    public static ParseQuery<Blockade> getQuery() {

        return ParseQuery.getQuery(Blockade.class);

    }
}

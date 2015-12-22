package com.amtechventures.tucita.utils.strings;


import android.util.Log;

public class Slot {

    private int startMinute;
    private int startHour;
    private int increment;
    private String formmatedHour;

    public Slot(int startMinute, int startHour, int increment, String formmatedHour) {
        this.startMinute = startMinute;
        this.startHour = startHour;
        this.increment = increment;
        this.formmatedHour = formmatedHour;
    }

    public int getEndHour() {

        int endHour = startHour;

        if(startMinute + increment >= 60){

            endHour ++;
        }
        return endHour;
    }

    public int getEndMinute(){

        int minute;

        int incremented = startMinute + increment;

        if(incremented >= 60){

            minute = incremented - 60;
        } else{
            minute = incremented;
        }
        return minute;
    }

    public int getStartMinute() {

        return startMinute;
    }

    public String getFormmatedHour() {

        return formmatedHour;
    }

    public int getStartHour() {

        return startHour;
    }

    public boolean isGreater(int hour, int minute){

        boolean isGreater = (hour < startHour) || ((hour == startHour) && (minute < startMinute));

        return isGreater;
    }

    public boolean endIsGreater(int hour, int minute){

        boolean isGreater = (hour < getEndHour() || ((hour == getEndHour()) && (minute < getEndMinute())));

        return isGreater;
    }

    public boolean endIsGreaterOrEqual(int hour, int minute){

        boolean isGreaterOrEqual = (hour < getEndHour()) || ((hour == getEndHour()) && (minute <= getEndMinute()));

        return isGreaterOrEqual;
    }

    public boolean isSmaller(int hour, int minute){

        boolean isSmaller = (hour > startHour) || ((hour == startHour) && (minute > startMinute));

        return isSmaller;
    }

    public boolean endIsSmaller(int hour, int minute){

        boolean isSmaller = (hour > getEndHour()) || ((hour == getEndHour()) && (minute > getEndMinute()));

        return isSmaller;
    }

    public boolean isSmallerOrEqual(int hour, int minute){

        boolean isSmallerOrEqual = (hour > startHour) || ((hour == startHour) && (minute >= startMinute));

        return isSmallerOrEqual;
    }
}

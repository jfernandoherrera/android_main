package com.amtechventures.tucita.utils.strings;


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

        int minute = startMinute;

        int incremented = startMinute + increment;

        if(incremented >= 60){

            minute = incremented - 60;
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
}

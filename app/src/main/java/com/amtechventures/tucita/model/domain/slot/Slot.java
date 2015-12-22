package com.amtechventures.tucita.model.domain.slot;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("Slot")
public class Slot extends ParseObject{

    public int getDay(){

        return getInt(SlotAttributes.day);
    }

    public int[] getDuration(){

            int[] duration = new int[2];

            int durationHours = 0;

            int durationMinutes = getInt(SlotAttributes.duration);

            if(durationMinutes >= 60){

                durationHours = durationMinutes / 60;

                durationMinutes = durationMinutes - (60 * durationHours);
            }
            duration[0] = durationHours;

            duration[1] = durationMinutes;

            return duration;
    }

    public int getStartMinute() {

        return getInt(SlotAttributes.startMinute);
    }

    public int getStartHour() {

        return getInt(SlotAttributes.startHour);
    }

    public static ParseQuery<Slot> getQuery(){

        return ParseQuery.getQuery(Slot.class);
    }
}

package com.amtechventures.tucita.model.context.slot;

import com.amtechventures.tucita.model.domain.slot.Slot;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.domain.venue.VenueAttributes;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

public class SlotContext {

    SlotRemote slotRemote;

    public SlotContext(){

        slotRemote = new SlotRemote();
    }
    public static SlotContext context(SlotContext slotContext) {

        if (slotContext == null) {

            slotContext = new SlotContext();

        }

        return  slotContext;
    }

    public void loadDaySlots(Venue venue, int day, SlotCompletion.SlotErrorCompletion completion){

        ParseRelation object = (ParseRelation) venue.get(VenueAttributes.slots);

        ParseQuery<Slot> queryLocal = object.getQuery();

        slotRemote.loadDaySlots(queryLocal, day, completion);
    }
}

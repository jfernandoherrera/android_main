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

    public void loadSlots(Venue venue, SlotCompletion.SlotErrorCompletion completion){

        ParseRelation object = (ParseRelation) venue.get(VenueAttributes.slots);

        ParseQuery<Slot> queryLocal = object.getQuery();

        slotRemote.loadSlots(queryLocal, completion);
    }
}

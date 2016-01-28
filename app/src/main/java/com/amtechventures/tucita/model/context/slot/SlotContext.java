package com.amtechventures.tucita.model.context.slot;

import com.amtechventures.tucita.activities.main.MainActivity;
import com.amtechventures.tucita.activities.splash.SplashActivity;
import com.amtechventures.tucita.model.domain.slot.Slot;
import com.amtechventures.tucita.model.domain.venue.Venue;
import com.amtechventures.tucita.model.domain.venue.VenueAttributes;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.SaveCallback;

public class SlotContext {

    private SlotRemote slotRemote;

    public SlotContext() {

        slotRemote = new SlotRemote();
    }

    public static SlotContext context(SlotContext slotContext) {

        if (slotContext == null) {

            slotContext = new SlotContext();

        }

        return slotContext;

    }

    public void loadDaySlots(Venue venue, int day, SlotCompletion.SlotErrorCompletion completion) {

        try {

            ParseRelation object = (ParseRelation) venue.get(VenueAttributes.slots);

            ParseQuery<Slot> queryLocal = object.getQuery();

            slotRemote.loadDaySlots(queryLocal, day, completion);

        }catch (NullPointerException e){

            e.printStackTrace();

        }

    }

    public void lockSlot(Slot slot, SaveCallback callback) {

        slotRemote.lockSlot(slot, callback);

    }

    public boolean isLocked(Slot slot) {

        return slotRemote.isLocked(slot);

    }

}

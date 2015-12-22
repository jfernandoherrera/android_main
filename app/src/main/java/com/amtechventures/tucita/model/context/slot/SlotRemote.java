package com.amtechventures.tucita.model.context.slot;

import com.amtechventures.tucita.model.domain.slot.Slot;
import com.amtechventures.tucita.model.error.AppError;
import com.parse.FindCallback;
import com.parse.ParseQuery;

import java.util.List;

public class SlotRemote {

    ParseQuery<Slot> query;

    private void setQuery(){

        query = Slot.getQuery();
    }

    public void cancelQuery(){

        if(query != null){

            query.cancel();
        }
    }

    public void loadSlots(ParseQuery<Slot> slotParseQuery, final SlotCompletion.SlotErrorCompletion completion) {

        query = slotParseQuery;

        query.findInBackground(new FindCallback<Slot>() {
            @Override
            public void done(List<Slot> objects, com.parse.ParseException e) {

                AppError appError = e != null ? new AppError(Slot.class.toString(), 0, null) : null;

                completion.completion(objects, appError);
            }

        });
    }
}

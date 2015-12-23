package com.amtechventures.tucita.model.context.slot;

import com.amtechventures.tucita.model.domain.slot.Slot;
import com.amtechventures.tucita.model.error.AppError;

import java.util.List;

public class SlotCompletion {


    public interface SlotErrorCompletion {

        void completion(List<Slot> slotList, AppError error);

    }
}

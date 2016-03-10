package com.amtechventures.tucita.model.context.blockade;


import com.amtechventures.tucita.model.domain.blockade.Blockade;
import com.amtechventures.tucita.model.error.AppError;

import java.util.List;

public class BlockadeCompletion {

    public interface ErrorCompletion {

        void completion(List<Blockade> blockadeList, AppError error);

    }

}

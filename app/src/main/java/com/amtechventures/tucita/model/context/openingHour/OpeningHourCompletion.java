package com.amtechventures.tucita.model.context.openingHour;


import com.amtechventures.tucita.model.domain.category.Category;
import com.amtechventures.tucita.model.domain.openingHour.OpeningHour;
import com.amtechventures.tucita.model.error.AppError;

import java.util.List;

public class OpeningHourCompletion {

    public interface OpeningHourErrorCompletion {

        void completion(List<OpeningHour> openingHourList, AppError error);

    }
}

package com.amtechventures.tucita.model.context.city;

import com.amtechventures.tucita.model.domain.city.City;
import com.amtechventures.tucita.model.error.AppError;
import java.util.List;

public class CityCompletion {

    public interface ErrorCompletion {

        void completion(List<City> cities, AppError error);

    }
}


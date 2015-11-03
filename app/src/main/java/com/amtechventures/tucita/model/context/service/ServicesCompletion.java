package com.amtechventures.tucita.model.context.service;


import com.amtechventures.tucita.model.domain.category.Category;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.error.AppError;

import java.util.List;

public class ServicesCompletion {

    public interface ErrorCompletion {

        void completion(List<Service> servicesList, AppError error);

    }
}

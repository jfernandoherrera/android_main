package com.amtechventures.tucita.model.context.user;

import com.amtechventures.tucita.model.error.AppError;
import com.amtechventures.tucita.model.domain.user.User;

public class UserCompletion {


    public interface UserErrorCompletion {

        void completion(User user, AppError error);

    }

}

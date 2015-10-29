package com.amtechventures.tucita.model.context.user;


import com.amtechventures.tucita.utils.strings.Strings;

public class UserLocal {

public static void logout(){

    UserContext.context().me().getParseUser().logOut();

    UserContext.context().me().setAuthType(Strings.ANONYMOUS);

}

}

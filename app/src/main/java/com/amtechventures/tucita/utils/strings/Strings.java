package com.amtechventures.tucita.utils.strings;

public class Strings {

    public static final String FIELDS = "fields";

    public static final String PUBLIC_PROFILE = "public_profile";

    public static final String PROFILE_PICTURE = "fieldname_of_type_ProfilePictureSource";

    public static final String APP_PARSE_ID = "EazCp42Fw4yJTEGCuZ9ofl75izJY7a49nw2xufH6";

    public static final String CLIENT_PARSE_ID = "Fr71FqI5chgnNWBbrDa6d9rhxo3PiL8pngM7MUt4";

    public static final String  capitalize(String word){
        String s1 = word.substring(0, 1).toUpperCase();
        String nameCapitalized = s1 + word.substring(1);
        return nameCapitalized;
    }
}

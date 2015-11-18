package com.amtechventures.tucita.utils.blocks;

import org.json.JSONArray;
import org.json.JSONObject;
import com.amtechventures.tucita.model.error.AppError;

public class Completion {

    public interface DictionaryErrorCompletion {

        void completion(JSONObject jsonObject, AppError error);

    }

    public interface LongArrayErrorCompletion {

        void completion(long aLong, JSONArray array, AppError error);

    }

    public interface IntErrorCompletion{

        void completion(int number, AppError error);
    }


}
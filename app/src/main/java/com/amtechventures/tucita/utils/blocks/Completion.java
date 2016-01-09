package com.amtechventures.tucita.utils.blocks;

import com.amtechventures.tucita.model.error.AppError;

import org.json.JSONArray;
import org.json.JSONObject;

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
package com.amtechventures.tucita.utils.blocks;

import android.content.Loader;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;

import com.amtechventures.tucita.model.error.AppError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.List;
import java.util.Map;

public class Completion {

    public interface VoidCompletion {

        public void completion();

    }

    public interface BoolCompletion {

        public void completion(boolean bool);

    }

    public interface BoolErrorCompletion {

        public void completion(boolean bool, AppError error);

    }

    public interface ErrorCompletion {

        public void completion(AppError error);

    }

    public interface IntErrorCompletion {

        public void completion(int integer, AppError error);

    }

    public interface ImageCompletion {

        public void completion(Image image);

    }

    public interface URLErrorCompletion {

        public void completion(URL url, AppError error);

    }

    public interface StringErrorCompletion {

        public void completion(String string, AppError error);

    }

    public interface ArrayErrorCompletion {

        public void completion(JSONArray array, AppError error);

    }

    public interface AnyErrorCompletion {

        public void completion(Object object, AppError error);

    }

    public interface DictionaryErrorCompletion {

        public void completion(JSONObject jsonObject, AppError error);

    }
    public interface DictionaryErrorCompletion2 {

        public void completion(JSONObject jsonObject, AppError error);

    }
    public interface LongArrayErrorCompletion {

        void completion(long aLong, JSONArray array, AppError error);

    }
    public interface ArrayWithDictionaryErrorCompletion {

        public void completion(List<Map<?, ?>> list, AppError error);

    }
    public interface BoolBoolCompletion {

        void completion(boolean bool1, boolean bool2);

        Loader<Cursor> onCreateLoader(int id, Bundle args);

        void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor);

        void onLoaderReset(Loader<Cursor> cursorLoader);
    }
    public interface LongErrorCompletion {

        void completion(long aLong, Error error);

    }


}
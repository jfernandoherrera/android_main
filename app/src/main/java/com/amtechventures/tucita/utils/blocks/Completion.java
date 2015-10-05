package com.amtechventures.tucita.utils.blocks;

import android.media.Image;

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

    public interface ArrayWithDictionaryErrorCompletion {

        public void completion(List<Map<?, ?>> list, AppError error);

    }

}
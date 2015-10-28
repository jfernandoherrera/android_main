package com.amtechventures.tucita.utils.blocks;

import java.net.URL;
import java.util.Map;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import android.media.Image;
import com.amtechventures.tucita.model.error.AppError;
import com.parse.ParseUser;

public class Completion {

    public interface VoidCompletion {

        void completion();

    }

    public interface BoolCompletion {

        void completion(boolean bool);

    }

    public interface BoolErrorCompletion {

        void completion(boolean bool, AppError error);

    }

    public interface ErrorCompletion {

        void completion(AppError error);

    }

    public interface IntErrorCompletion {

        void completion(int integer, AppError error);

    }

    public interface ImageCompletion {

        void completion(Image image);

    }

    public interface URLErrorCompletion {

        void completion(URL url, AppError error);

    }

    public interface StringErrorCompletion {

        void completion(String string, AppError error);

    }

    public interface ArrayErrorCompletion {

        void completion(JSONArray array, AppError error);

    }

    public interface AnyErrorCompletion {

        void completion(Object object, AppError error);

    }

    public interface DictionaryErrorCompletion {

        void completion(JSONObject jsonObject, AppError error);

    }
    public interface DictionaryErrorCompletion2 {

        void completion(JSONObject jsonObject, AppError error);

    }
    public interface LongArrayErrorCompletion {

        void completion(long aLong, JSONArray array, AppError error);

    }
    public interface ArrayWithDictionaryErrorCompletion {

        void completion(List<Map<?, ?>> list, AppError error);

    }
    public interface BoolBoolCompletion {

        void completion(boolean bool1, boolean bool2);

    }


    public interface LongErrorCompletion {

        void completion(long aLong, Error error);

    }

    public interface BoolBoolUserCompletion {

        void completion(ParseUser user,boolean bool1, boolean bool2);

    }
    public interface BoolErrorUserCompletion {

        void completion(ParseUser user,boolean bool1, Error error);

    }
}
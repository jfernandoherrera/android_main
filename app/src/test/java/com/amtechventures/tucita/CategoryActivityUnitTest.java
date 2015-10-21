package com.amtechventures.tucita;




import android.support.v7.widget.RecyclerView;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import com.amtechventures.tucita.activities.main.CategoryActivity;

import org.junit.runner.RunWith;

import static java.lang.Thread.*;

@RunWith(RobolectricDataBindingTestRunner .class)
@Config(constants = BuildConfig.class,sdk =  21, manifest = "app/src/main/AndroidManifest.xml")
public class CategoryActivityUnitTest {

    private CategoryActivity activity;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(CategoryActivity.class)
                .create()
                .resume()
                .visible()
                .get();
    }

    @Test
    public void testHomeActivityCreation() {

        assert(activity!=null);
    }

    @Test
    public void testAdapterWasAttached() throws Exception {
        final RecyclerView recyclerView=(RecyclerView)activity.findViewById(R.id.recycler_view);
sleep(10000);
        assert(recyclerView.getAdapter()!=null);




    }

}
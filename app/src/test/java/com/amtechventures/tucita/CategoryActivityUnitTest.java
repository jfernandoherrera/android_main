package com.amtechventures.tucita;




import com.amtechventures.tucita.activities.main.fragments.category.CategoryFragment;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import org.junit.runner.RunWith;

@RunWith(RobolectricDataBindingTestRunner .class)
@Config(constants = BuildConfig.class,sdk =  19, manifest = "app/manifests/AndroidManifest.xml")
public class CategoryActivityUnitTest {

    private CategoryFragment activity;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(CategoryFragment.class).create()
                .get();
    }

    @Test
    public void testHomeActivityCreation() {

        assert(activity!=null);
    }


}
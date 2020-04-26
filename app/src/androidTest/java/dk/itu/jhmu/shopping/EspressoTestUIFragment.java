package dk.itu.jhmu.shopping;

import android.content.Context;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest

public class EspressoTestUIFragment {
    private static ItemsDB itemsDB;
    private static ShopsDB shopsDB;

    @Rule
    public ActivityTestRule<ShoppingActivity> mShoppingActivityTestRule =
            new ActivityTestRule<>(ShoppingActivity.class);

    //set up an empty database before running test suite
    //clear the database after running test suite
    @BeforeClass
    @AfterClass
    public static void setUp() {
        itemsDB = ItemsDB.get(InstrumentationRegistry.getInstrumentation().getTargetContext());
        shopsDB = ShopsDB.get(InstrumentationRegistry.getInstrumentation().getTargetContext());
        itemsDB.deleteAllItems();
    }

    @AfterClass
    public static void cleanUp() {
        shopsDB.deleteAllShops();
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("dk.itu.jhmu.shopping", appContext.getPackageName());
    }

    @Test
    public void ensureItemIsAdded() {
        onView(withId(R.id.whatEditText)).perform(typeText("Milk"), closeSoftKeyboard());
        onView(withId(R.id.whereEditText)).perform(click());
        onView(withText("Bilka")).perform(click());
        onView(withText("Ok")).perform(click());
        onView(withId(R.id.addItemBtn)).perform(click());

        onView(withId(R.id.whatEditText)).check(matches(withText("")));
        onView(withId(R.id.whereEditText)).check(matches(withText("")));
        assertEquals(1, itemsDB.getItemCount());
    }

    @Test
    public void ensureShopPickerOpens() {
        onView(withId(R.id.whereEditText)).perform(click());
        onView(withText("Irma")).perform(click());
        onView(withText("Ok")).perform(click());

        onView(withId(R.id.whereEditText)).check(matches(withText("Irma")));
    }

    @Test
    public void ensureShopAdderOpens() {
        onView(withId(R.id.add_shop_item)).perform(click());
        onView(withId(R.id.add_shop)).check(matches(isDisplayed()));
    }

    @Test
    public void ensureShopIsAdded() {
        onView(withId(R.id.add_shop_item)).perform(click());
        onView(withId(R.id.add_shop)).perform(typeText("Lidl"));
        onView(withText("Add")).perform(click());

        assertEquals(true, shopsDB.containsShop("Lidl"));
        assertEquals(5, shopsDB.getShopCount());

        onView(withId(R.id.whereEditText)).perform(click());
        onView(withText("Lidl")).check(matches(isDisplayed()));
    }

}

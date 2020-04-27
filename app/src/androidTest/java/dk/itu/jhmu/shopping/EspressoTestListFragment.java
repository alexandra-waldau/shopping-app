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

//TEST CLASS//-------------------------------------------------------------------------------------
public class EspressoTestListFragment {

    //FIELDS//-------------------------------------------------------------------------------------
    private static ItemsDB itemsDB;

    //RULES AND SETUP//----------------------------------------------------------------------------
    @Rule
    public ActivityTestRule<ShoppingActivity> mShoppingActivityTestRule =
            new ActivityTestRule<>(ShoppingActivity.class);

    @BeforeClass
    @AfterClass
    public static void setUp() {
        itemsDB = ItemsDB.get(InstrumentationRegistry.getInstrumentation().getTargetContext());
        itemsDB.deleteAllItems();
    }

    //TESTS//--------------------------------------------------------------------------------------

    //Not 100% sure what this test does except check we're using the right package? :P
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("dk.itu.jhmu.shopping", appContext.getPackageName());
    }

    //Check that opening the recycler view works properly.
    @Test
    public void ensureRecyclerViewOpens() {
        onView(withId(R.id.listItemsBtn)).perform(click());
        onView(withId(R.id.list_recycler_view)).check(matches(isDisplayed()));
    }

    //Check that the item count TextView is working properly.
    @Test
    public void ensureTextViewItemCountDisplaysCorrectly() {
        onView(withId(R.id.add_items)).perform(click());
        onView(withId(R.id.itemCount_TextView)).check(matches(withText("5 items")));
    }

    //I'd like to test the toolbar item count as well but after a lot of stackoverflow and trial and error
    //I don't really have a solution yet...
    /*
    public void ensureToolbarItemCountDisplaysCorrectly() {
        onView(withId(R.id.show_subtitle)).perform(click());
        onView(withId(R.id.itemCount_TextView)).check(matches(withText("5 items")));
    }
    */

    //Check that the batch add works correctly.
    @Test
    public void ensureBatchAddItemsAddsFiveItems() {
        onView(withId(R.id.add_items)).perform(click());
        onView(withId(R.id.add_items)).perform(click());
        onView(withId(R.id.add_items)).perform(click());
        assertEquals(15, itemsDB.getItemCount());;
    }

    //Check if the database is empty once the delete button is pressed.
    @Test
    public void ensureDeleteItemsDeletesAllItems() {
        onView(withId(R.id.delete_all)).perform(click());
        assertEquals(0, itemsDB.getItemCount());
    }

    //Need to run this to see if it actually works as intended or if it ends up pressing all the
    //deleteItemImgButtons...
    public void ensureDeleteOneItemButtonDeletesSingleItem() {
        onView(withId(R.id.add_items)).perform(click());
        onView(withId(R.id.deleteItemImgBtn)).perform(click());
        assertEquals(4, itemsDB.getItemCount());
    }

    //Not entirely sure how to test for the context sharing coming up...
    //Just looking for a string in the shared window atm.
    @Test
    public void ensureShareItemsSharesItems() {
        onView(withId(R.id.share_list).perform(click()));
        onView(withText("Send list via")).check(matches(isDisplayed()));
    }

}
//END OF LINE//------------------------------------------------------------------------------------


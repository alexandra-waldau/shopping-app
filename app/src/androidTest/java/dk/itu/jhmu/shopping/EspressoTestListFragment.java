package dk.itu.jhmu.shopping;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
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

    //Need Alex to explain this one to me.
    @Rule
    public ActivityTestRule<ShoppingActivity> mShoppingActivityTestRule =
            new ActivityTestRule<>(ShoppingActivity.class);

    @BeforeClass
    @AfterClass
    public static void setUp() {
        itemsDB = ItemsDB.get(getInstrumentation().getTargetContext());
        itemsDB.deleteAllItems();
    }

    //TESTS//--------------------------------------------------------------------------------------

    //Checks that the delete buttons on the row elements in the recycler view work correctly.
    @Test
    public void shouldDeleteAnItemFromTheRecyclerView() {
        itemsDB.deleteAllItems();
        onView(withId(R.id.whatEditText)).perform(typeText("Banana"),closeSoftKeyboard());
        onView(withId(R.id.whereEditText)).perform(click());
        onView(withText("Netto")).perform(click());
        onView(withText("Ok")).perform(click());

        onView(withId(R.id.addItemBtn)).perform(click());
        onView(withId(R.id.listItemsBtn)).perform(click());

        onView(withId(R.id.item_what)).check(matches(withText("Banana")));
        onView(withId(R.id.item_where)).check(matches(withText("Netto")));

        onView(withId(R.id.deleteItemImgBtn)).perform(click());
        assertEquals(0, itemsDB.getItemCount());
    }

    //Check that the item count is working properly.
    @Test
    public void ensureTextViewItemCountDisplaysCorrectly() {
        itemsDB.deleteAllItems();
        onView(withId(R.id.listItemsBtn)).perform(click());
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("DEV: Add Items")).perform(click());
        onView(withId(R.id.itemCount_TextView)).check(matches(withText("5 items")));
    }

    //Check that the batch add works correctly.
    @Test
    public void ensureBatchAddItemsAddsItemsCorrectly() {
        itemsDB.deleteAllItems();
        onView(withId(R.id.listItemsBtn)).perform(click());
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("DEV: Add Items")).perform(click());
        assertEquals(5, itemsDB.getItemCount());
    }

    //Check if the database is empty once the delete button is pressed.
    @Test
    public void ensureDeleteItemsDeletesAllItems() {
        itemsDB.deleteAllItems();
        onView(withId(R.id.listItemsBtn)).perform(click());
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("DEV: Add Items")).perform(click());
        onView(withId(R.id.delete_all)).perform(click());
        assertEquals(0, itemsDB.getItemCount());
    }

    //Checks if the toolbar back button works correctly.
    @Test
    public void ensureBackButtonWorksProperly() {
        itemsDB.deleteAllItems();
        onView(withId(R.id.listItemsBtn)).perform(click());
        onView(withContentDescription("Navigate up")).perform(click());
        onView(withText("List All Items")).check(matches(isDisplayed()));
    }
}
//END OF LINE//------------------------------------------------------------------------------------


package dk.itu.jhmu.shopping;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import android.content.Context;

//UNIT TEST//-------------------------------------------------------------------------------------
public class ItemsDBUnitTest {
    //Variable to hold our DB for testing.
    ItemsDB itemsDB;
    Context context = null;

    //Performs this step before each test.
    @Before
    public void setup () {
        itemsDB = ItemsDB.get(context);
        itemsDB.deleteAllItems(); //This is to remove the items that are currently added by default.
        itemsDB.addItem("coffee", "Irma");
        itemsDB.addItem("carrots", "Netto");
        itemsDB.addItem("milk", "Netto");
        itemsDB.addItem("bread", "bakery");
        itemsDB.addItem("butter", "Irma");
    }

    //Performs this step after each test.
    @After
    public void cleanup() {
        itemsDB.deleteAllItems();
    }

    //And now for our tests. Added a few extra for fun.
    @Test
    public void ShouldListAllItemsCorrectly() {
        assertEquals("\n Buy coffee in: Irma"+
                        "\n Buy carrots in: Netto" +
                        "\n Buy milk in: Netto" +
                        "\n Buy bread in: bakery" +
                        "\n Buy butter in: Irma",
                        itemsDB.listItems());
    }

    /*
    @Test //Need to rethink how adding and removing items works in general, before I change all the tests.
    public void BreadItemShouldBeDeleted () {
        Item item = new Item("bread","bakery");
        itemsDB.addItem(item);
        itemsDB.deleteItem(item);
        assertEquals("\n Buy coffee in: Irma"+
                        "\n Buy carrots in: Netto" +
                        "\n Buy milk in: Netto" +
                        "\n Buy butter in: Irma",
                itemsDB.listItems());
    }
    */

    @Test
    public void LastItemShouldBeDeleted () {
        itemsDB.deleteLastItem();
        assertEquals("\n Buy coffee in: Irma"+
                        "\n Buy carrots in: Netto" +
                        "\n Buy milk in: Netto" +
                        "\n Buy bread in: bakery",
                itemsDB.listItems());
    }

    @Test
    public void ShouldDeleteAllItems() {
        itemsDB.deleteAllItems();
        assertEquals("", itemsDB.listItems());
    }

    @Test
    public void ShouldReturnTheEmptySize() {
        itemsDB.deleteAllItems();
        assertEquals(0,itemsDB.getSize());
    }
}
//END OF LINE//------------------------------------------------------------------------------------

package dk.itu.jhmu.shopping;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

//UNIT TEST//-------------------------------------------------------------------------------------
public class ItemsDBUnitTest {
    //Variable to hold our DB for testing.
    ItemsDB itemsDB;

    //Performs this step before each test.
    @Before
    public void setup () {
        itemsDB = ItemsDB.get();
        itemsDB.addItem("coffee", "Irma");
        itemsDB.addItem("carrots", "Netto");
        itemsDB.addItem("milk", "Netto");
        itemsDB.addItem("bread", "bakery");
        itemsDB.addItem("butter", "Irma");
    }

    //Performs this step after each test.
    @After
    public void cleanup() {
        itemsDB.removeAllItems();
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

    @Test
    public void BreadItemShouldBeDeleted () {
        itemsDB.removeItem("bread");
        assertEquals("\n Buy coffee in: Irma"+
                        "\n Buy carrots in: Netto" +
                        "\n Buy milk in: Netto" +
                        "\n Buy butter in: Irma",
                itemsDB.listItems());
    }

    @Test
    public void LastItemShouldBeDeleted () {
        itemsDB.removeLastItem();
        assertEquals("\n Buy coffee in: Irma"+
                        "\n Buy carrots in: Netto" +
                        "\n Buy milk in: Netto" +
                        "\n Buy bread in: bakery",
                itemsDB.listItems());
    }

    @Test
    public void ShouldDeleteAllItems() {
        itemsDB.removeAllItems();
        assertEquals("", itemsDB.listItems());
    }

    @Test
    public void ShouldReturnTheEmptySize() {
        itemsDB.removeAllItems();
        assertEquals(0,itemsDB.getSize());
    }
}
//END OF LINE//------------------------------------------------------------------------------------

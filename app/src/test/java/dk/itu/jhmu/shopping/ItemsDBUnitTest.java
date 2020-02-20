package dk.itu.jhmu.shopping;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ItemsDBUnitTest {
    //Variable to hold our DB for testing.
    ItemsDB itemsDB;

    //Create our DB and fill it with items.
    @Before
    public void setup () {
        itemsDB = ItemsDB.get();
        itemsDB.addItem("coffee", "Irma");
        itemsDB.addItem("carrots", "Netto");
        itemsDB.addItem("milk", "Netto");
        itemsDB.addItem("bread", "bakery");
        itemsDB.addItem("butter", "Irma");
    }

    @After
    public void cleanup() {
        itemsDB.removeAllItems();
    }

    //Should list all the items added.
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

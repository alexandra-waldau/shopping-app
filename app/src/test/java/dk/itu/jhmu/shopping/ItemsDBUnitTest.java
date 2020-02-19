package dk.itu.jhmu.shopping;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ItemsDBUnitTest {
    //Variable to hold our DB for testing.
    ItemsDB itemsDB;

    //Create our DB and fill it with items.
    @BeforeClass
    public void setup () {
        itemsDB = ItemsDB.get();
        itemsDB.addItem("coffee", "Irma");
        itemsDB.addItem("carrots", "Netto");
        itemsDB.addItem("milk", "Netto");
        itemsDB.addItem("bread", "bakery");
        itemsDB.addItem("butter", "Irma");
    }

    //Should list all the items added.
    @Test
    public void ShouldListAllItemsCorrectly() {
        assertEquals(itemsDB.listItems(),
                "\n Buy coffee in: Irma"+
                        "\n Buy carrots in: Netto" +
                        "\n Buy milk in: Netto" +
                        "\n Buy bread in: bakery" +
                        "\n Buy butter in: Irma");
    }

    @Test
    public void ShouldReturnTheRightSize() {
        assertEquals(itemsDB.getSize(),5);
    }

    //First Test, looking to return the first item in the Array.
    @Test
    public void ShouldDeleteAllItems() {
        itemsDB.removeAllItems();
        assertEquals(itemsDB.listItems(),
                "");
    }

    @Test
    public void ShouldReturnTheEmptySize() {
        assertEquals(itemsDB.getSize(),0);
    }

}

package dk.itu.jhmu.shopping;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

//TEST CLASS//-------------------------------------------------------------------------------------
public class ItemUnitTest {

    //FIELDS//-------------------------------------------------------------------------------------
    Item item;

    //SETUP//--------------------------------------------------------------------------------------
    //Performs this step before each test.
    @Before
    public void setup () {
        item = new Item("Milk","Netto");
    }

    //TESTS//--------------------------------------------------------------------------------------

    //I know testing getters and setters is not really worth doing in unit testing.
    //It's just for practice here. :)
    @Test
    public void ShouldReturnTheName() {
        assertEquals("Milk",item.getWhat());
    }

    @Test
    public void ShouldReturnTheLocation() {
        assertEquals("Netto", item.getWhere());
    }

    //These toString methods are probably worth testing though. Not much logic in them but again, practice.
    @Test
    public void ShouldReturnAString() {
        assertEquals("Milk in: Netto",item.toString());
    }

    @Test
    public void ShouldReturnASpecialString() {
        assertEquals("Chocolate Milk Super Netto", item.oneLine("Chocolate "," Super "));
    }

}
//END OF LINE//------------------------------------------------------------------------------------
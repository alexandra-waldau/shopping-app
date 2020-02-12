package dk.itu.jhmu.shopping;

import java.util.ArrayList;
import java.util.List;

//CLASS HEADER//-----------------------------------------------------------------------------------
class ItemsDB {
    //FIELDS//-------------------------------------------------------------------------------------
    private List<Item> ItemsDB;

    //CONSTRUCTOR//--------------------------------------------------------------------------------
    ItemsDB() {
        ItemsDB= new ArrayList<>();
    }

    //METHODS//------------------------------------------------------------------------------------

    //Takes two Strings as an input to create and add a new Item to the Database.
    void addItem(String what, String where) {
        ItemsDB.add(new Item(what,where));
    }

    //Lists all items currently in the Database.
    String listItems() {
        String r= "";
        for (int i= 0; i<ItemsDB.size(); i++) {
            r += "\n Buy " + ItemsDB.get(i).toString();
        }
        return r;
    }

    //Fills the database with a bunch of items.
    @Deprecated
    void fillItemsDB() {
        ItemsDB.add(new Item("coffee", "Irma"));
        ItemsDB.add(new Item("carrots", "Netto"));
        ItemsDB.add(new Item("milk", "Netto"));
        ItemsDB.add(new Item("bread", "bakery"));
        ItemsDB.add(new Item("butter", "Irma"));
    }
}
//END OF LINE//------------------------------------------------------------------------------------
package dk.itu.jhmu.shopping;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

//CLASS HEADER//-----------------------------------------------------------------------------------
class ItemsDB {
    //FIELDS//-------------------------------------------------------------------------------------
    private List<Item> ItemsDB;
    private static ItemsDB sItemsDB;
    private int size;

    //CONSTRUCTOR//--------------------------------------------------------------------------------
    private ItemsDB(Context context) {
        ItemsDB= new ArrayList<>();
        //Sets an initial size of -1 for testing the List size.
        size = -1;
    }

    //METHODS//------------------------------------------------------------------------------------

    //Singleton method to return the current ItemsDB. Used when switching activities.
    static ItemsDB get (Context context) {
        if (sItemsDB == null) {
            sItemsDB = new ItemsDB(context);
        }
        return sItemsDB;
    }

    //Takes two Strings as an input to create and add a new Item to the Database.
    void addItem(String what, String where) {
        ItemsDB.add(new Item(what,where));
        size++;
    }

    //Removes the most recent item added to the database.
    void removeItem() {
        if (ItemsDB.size() > 0) {
            ItemsDB.remove(size);
            size--;
        }
    }

    //Returns true if there are no more items in the List.
    boolean isEmpty() {
        return size == -1;
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
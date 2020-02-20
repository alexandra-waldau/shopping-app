package dk.itu.jhmu.shopping;

import android.content.Context; //Think we will need this eventually?

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//VERSION 4.2// Week 4 //--------------------------------------------------------------------------
//VERSION NOTES: Fragments! Unit Tests! Everything's broken! Help I'm unsupervised!
/*
 * @author John Henrik Muller
 */
//-------------------------------------------------------------------------------------------------

//CLASS HEADER//-----------------------------------------------------------------------------------
class ItemsDB {
    //FIELDS//-------------------------------------------------------------------------------------
    private List<Item> ItemsDB;
    private static ItemsDB sItemsDB;
    private int size;

    //CONSTRUCTOR//--------------------------------------------------------------------------------
    private ItemsDB() {
        ItemsDB = new ArrayList<>();
        //Sets an initial size of -1 for testing the List size.
        size = -1;
    }

    //METHODS//------------------------------------------------------------------------------------

    //Singleton method to return the current ItemsDB. Used when switching activities.
    static ItemsDB get() {
        if (sItemsDB == null) {
            sItemsDB = new ItemsDB();
        }
        return sItemsDB;
    }

    //Returns the current amount of items in the database.
    public int getSize(){
        return size + 1;
    }

    //Takes two Strings as an input to create and add a new Item to the Database.
    void addItem(String what, String where) {
        ItemsDB.add(new Item(what,where));
        size++;
    }

    //Can search the list for a given item name and remove it. Would like to implement this functionality
    //later.
    void removeItem(String itemName) {

        Iterator<Item> iterator = ItemsDB.iterator();

        while (iterator.hasNext()) {
            if (iterator.next().getWhat().equals(itemName)) {
                iterator.remove();
            }
        }
    }

    //Removes the most recent item added to the database.
    //Advise checking if the Database is empty first with isEmpty(), otherwise risk NullPointerException.
    void removeLastItem() {
        ItemsDB.remove(size);
        size--;
    }

    //Removes all items in the database.
    void removeAllItems() {
        ItemsDB.clear();
        size = -1;
    }

    //Returns true if there are no more items in the List.
    boolean isEmpty() {
        return size == -1;
    }

    //Lists all items currently in the Database.
    String listItems() {
        String r= "";
        for (int i = 0; i<ItemsDB.size(); i++) {
            r += "\n Buy " + ItemsDB.get(i).toString();
        }
        return r;
    }

    //Fills the database with a bunch of items. No longer used.
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
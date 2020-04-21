package dk.itu.jhmu.shopping;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

//VERSION 7.0//------------------------------------------------------------------------------------
/* VERSION NOTES: Adding SQL Lite Database! Constraint layouts! And more...
 * @author John Henrik Muller
 */
//-------------------------------------------------------------------------------------------------

//CLASS HEADER//-----------------------------------------------------------------------------------
class ItemsDB extends Observable {
    //FIELDS//-------------------------------------------------------------------------------------

    private List<Item> itemsDBList;
    private static ItemsDB sItemsDB;

    //CONSTRUCTOR//--------------------------------------------------------------------------------
    private ItemsDB(Context context) {
        itemsDBList = new ArrayList<>();
    }

    //METHODS//------------------------------------------------------------------------------------

    //Singleton method to return the current itemsDBList. Used when switching activities
    static ItemsDB get(Context context) {
        if (sItemsDB == null) {
            sItemsDB = new ItemsDB(context);
            //When it's first created, the itemsDB will be filled with junk items, for testing purposes.
            sItemsDB.fillItemsDB();
        }

        return sItemsDB;
    }

    //Returns all the items in the DB as a List of Items.
    List<Item> getItems(){
        return itemsDBList;
    }

    //Returns the current amount of items in the database.
    int getSize(){
        return itemsDBList.size();
    }

    //Takes two Strings as an input to create and add a new Item to the Database.
    void addItem(String what, String where) {
        itemsDBList.add(new Item(what,where));
        this.setChanged();
        notifyObservers();
    }

    //Removes a given item from the ItemsDB.
    //Used to accept a string, and search for the item. Now requires a specific item given as a
    //parameter to successfully delete.
    //Need to consider if addItem method should be changed to accept an Item only...
    void deleteItem (Item item) {
        itemsDBList.remove(item);
        this.setChanged();
        notifyObservers();
    }

    //Deletes the most recent item added to the database.
    //Doesn't check if the database isn't already empty. Use at your own risk.
    void deleteLastItem() throws NullPointerException {
        itemsDBList.remove(itemsDBList.size()-1);
        this.setChanged();
        notifyObservers();
    }

    //Deletes all items in the database.
    void deleteAllItems() {
        itemsDBList.clear();
        this.setChanged();
        notifyObservers();
    }

    //Returns true if there are no more items in the List.
    boolean isEmpty() {
        return itemsDBList.isEmpty();
    }

    //Lists all items currently in the Database.
    String listItems() {
        String r = "";
        for (int i = 0; i< itemsDBList.size(); i++) {
            r += "\n Buy " + itemsDBList.get(i).toString();
        }
        return r;
    }

    //Fills the database with a bunch of items. Used for testing!
    void fillItemsDB() {
        itemsDBList.add(new Item("coffee", "Irma"));
        itemsDBList.add(new Item("carrots", "Netto"));
        itemsDBList.add(new Item("milk", "REMA1000"));
        itemsDBList.add(new Item("bread", "Bakery"));
        itemsDBList.add(new Item("butter", "Aldi"));
        this.setChanged();
        notifyObservers();
    }
}
//END OF LINE//------------------------------------------------------------------------------------
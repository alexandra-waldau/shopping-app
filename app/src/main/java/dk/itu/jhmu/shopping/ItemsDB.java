package dk.itu.jhmu.shopping;

import android.content.Context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

//VERSION 5.0//------------------------------------------------------------------------------------
/*
 * @author John Henrik Muller
 */
//-------------------------------------------------------------------------------------------------

//CLASS HEADER//-----------------------------------------------------------------------------------
class ItemsDB extends Observable {
    //FIELDS//-------------------------------------------------------------------------------------

    private List<Item> itemsDBList;
    private static ItemsDB sItemsDB;
    private int size;

    //CONSTRUCTOR//--------------------------------------------------------------------------------
    private ItemsDB(Context context) {
        itemsDBList = new ArrayList<>();
        //Sets an initial size of -1 for testing the List size.
        size = -1;
    }

    //METHODS//------------------------------------------------------------------------------------

    //Singleton method to return the current itemsDBList. Used when switching activities
    static ItemsDB get(Context context) {
        if (sItemsDB == null) {
            sItemsDB = new ItemsDB(context);
        }
        return sItemsDB;
    }

    //Returns all the items in the DB as a List of Items.
    List<Item> getItems(){
        return itemsDBList;
    }

    //Returns the current amount of items in the database.
    int getSize(){
        return size + 1;
    }

    //Takes two Strings as an input to create and add a new Item to the Database.
    void addItem(String what, String where) {
        itemsDBList.add(new Item(what,where));
        size++;
        this.setChanged();
        notifyObservers();
    }

    /* Might have to use this method
     public void addItem(Item i) {
        itemsDBList.add(i);
        this.setChanged();
        notifyObservers();
    }
     */


    //Can search the list for a given item name and delete it. Would like to implement this functionality
    //later.
    void deleteItem (String itemName) {

        Iterator<Item> iterator = itemsDBList.iterator();

        while (iterator.hasNext()) {
            if (iterator.next().getWhat().equals(itemName)) {
                iterator.remove();
            }
        }

        this.setChanged();
        notifyObservers();
    }

    //Deletes the most recent item added to the database.
    //Advise checking if the Database is empty first with isEmpty(), otherwise risk NullPointerException.
    void deleteLastItem() {
        itemsDBList.remove(size);
        size--;
        this.setChanged();
        notifyObservers();
    }

    //Deletes all items in the database.
    void deleteAllItems() {
        itemsDBList.clear();
        size = -1;
        this.setChanged();
        notifyObservers();
    }

    //Returns true if there are no more items in the List.
    boolean isEmpty() {
        return size == -1;
    }

    //Lists all items currently in the Database.
    String listItems() {
        String r = "";
        for (int i = 0; i< itemsDBList.size(); i++) {
            r += "\n Buy " + itemsDBList.get(i).toString();
        }
        return r;
    }

    @Deprecated
    //Fills the database with a bunch of items. No longer used.
    void fillItemsDB() {
        itemsDBList.add(new Item("coffee", "Irma"));
        itemsDBList.add(new Item("carrots", "Netto"));
        itemsDBList.add(new Item("milk", "Netto"));
        itemsDBList.add(new Item("bread", "bakery"));
        itemsDBList.add(new Item("butter", "Irma"));
    }
}
//END OF LINE//------------------------------------------------------------------------------------
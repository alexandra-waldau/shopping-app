package dk.itu.jhmu.shopping;

import dk.itu.jhmu.shopping.database.ShoppingBaseHelper;
import dk.itu.jhmu.shopping.database.ShoppingCursorWrapper;
import dk.itu.jhmu.shopping.database.ShoppingDbSchema;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Observable;

//VERSION 8.5//------------------------------------------------------------------------------------
/* VERSION NOTES: TextView item counter added!
 * @author John Henrik Muller
 * @author Alexandra Waldau
 */
//-------------------------------------------------------------------------------------------------

//CLASS HEADER//-----------------------------------------------------------------------------------
class ItemsDB extends Observable {
    //FIELDS//-------------------------------------------------------------------------------------

    private static ItemsDB sItemsDB;
    private static SQLiteDatabase mDatabase;

    //CONSTRUCTOR//--------------------------------------------------------------------------------

    private ItemsDB(Context context) {
        mDatabase = ShoppingBaseHelper.getHelper(context.getApplicationContext())
                .getWritableDatabase();
    }

    //METHODS//------------------------------------------------------------------------------------

    //Singleton method to return the current itemsDBList. Used when switching activities.
    public static ItemsDB get(Context context) {
        if (sItemsDB == null) {
            sItemsDB= new ItemsDB(context);
        }
        return sItemsDB;
    }

    //Returns an ArrayList of items that are stored in the database.
    public ArrayList<Item> getItemsDB() {
        ArrayList<Item> items = new ArrayList<Item>();
        ShoppingCursorWrapper cursor = queryItems(null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            items.add(cursor.getItem());
            cursor.moveToNext();
        }

        cursor.close();
        return items;
    }

    //Returns a count of all the items in the database.
    public int getItemCount() {
        ArrayList<Item> items = getItemsDB();
        return items.size();
    }

    //Allows you to query the database for Items
    private static ShoppingCursorWrapper queryItems(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ShoppingDbSchema.ItemTable.NAME,
                null, //Columns - null selects all columns
                whereClause, whereArgs,
                null, //GroupBy
                null, //Having
                null //OrderBy
        );
        return new ShoppingCursorWrapper(cursor);
    }

    //Given a specific item, returns the content values of that item.
    private static ContentValues getContentValues(Item item) {
        ContentValues values = new ContentValues();
        values.put(ShoppingDbSchema.ItemTable.Cols.WHAT, item.getWhat());
        values.put(ShoppingDbSchema.ItemTable.Cols.WHERE, item.getWhere());
        return values;
    }


    //Takes two Strings as an input to create and add a new Item to the Database.
    void addItem(String what, String where){
        Item newItem = new Item(what, where);
        ContentValues values = getContentValues(newItem);
        mDatabase.insert(ShoppingDbSchema.ItemTable.NAME, null, values);
        this.setChanged();
        notifyObservers();
    }

    //Given the item name and shop, that item is removed from the database.
    //Items with the same name but different shop are deleted separately
    public void deleteItem(String what, String where) {
        String whereClause = ShoppingDbSchema.ItemTable.Cols.WHAT + "=?" + "AND " +
                ShoppingDbSchema.ItemTable.Cols.WHERE + "=?";
        String whereArgs[] = {what, where};
        mDatabase.delete(ShoppingDbSchema.ItemTable.NAME, whereClause, whereArgs);
        this.setChanged();
        notifyObservers();
    }

    //Removes all items from the database.
    //Does not delete the table, simply deletes all rows currently on it.
    public void deleteAllItems() {
        //Passing two null values into the where clauses will clear the table.
        mDatabase.delete(ShoppingDbSchema.ItemTable.NAME, null, null);
        this.setChanged();
        notifyObservers();
    }

    //Populates the database with a bunch of items for the purposes of testing.
    public void batchAddItems() {
        addItem("Bananas", "Irma");
        addItem("Oreos", "Netto");
        addItem("Milk", "Fotex");
        addItem("Bread", "Rema1000");
        addItem("Sugar", "Aldi");
    }

    //This method builds a string out of our database to send through an Implicit Intent to another app.
    public String getListReport() {
        int itemCount = sItemsDB.getItemCount();
        ArrayList<Item> itemsArray = sItemsDB.getItemsDB();

        String itemList = "";
        //This isn't exactly a sexy way of doing it, but it works. :P
        for(Item item: itemsArray) {
            itemList += item.getWhat() + " from " + item.getWhere() + ".\n";
        }

        String listReport = "You need " + itemCount + " items: \n" + itemList;
        return listReport;
    }
}
//END OF LINE//------------------------------------------------------------------------------------
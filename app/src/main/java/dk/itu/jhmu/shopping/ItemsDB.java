package dk.itu.jhmu.shopping;

import dk.itu.jhmu.shopping.database.ItemBaseHelper;
import dk.itu.jhmu.shopping.database.ItemCursorWrapper;
import dk.itu.jhmu.shopping.database.ItemsDbSchema;

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
    private ItemsDB(Context context) { }

    //METHODS//------------------------------------------------------------------------------------

    //Singleton method to return the current itemsDBList. Used when switching activities.
    public static ItemsDB get(Context context) {
        if (sItemsDB == null) {
            mDatabase= new ItemBaseHelper(context.getApplicationContext())
                    .getWritableDatabase();
            sItemsDB= new ItemsDB(context);
        }
        return sItemsDB;
    }

    //Returns an ArrayList of items that are stored in the database.
    public ArrayList<Item> getItemsDB() {
        ArrayList<Item> items = new ArrayList<Item>();
        ItemCursorWrapper cursor = queryItems(null, null);
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

    //Allows you to query the database for Items... I think. :P
    private static ItemCursorWrapper queryItems(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ItemsDbSchema.ItemTable.NAME,
                null, //Columns - null selects all columns
                whereClause, whereArgs,
                null, //GroupBy
                null, //Having
                null //OrderBy
        );
        return new ItemCursorWrapper(cursor);
    }

    //Given a specific item, returns the content values of that item.
    private static ContentValues getContentValues(Item item) {
        ContentValues values = new ContentValues();
        values.put(ItemsDbSchema.ItemTable.Cols.WHAT, item.getWhat());
        values.put(ItemsDbSchema.ItemTable.Cols.WHERE, item.getWhere());
        return values;
    }


    //Takes two Strings as an input to create and add a new Item to the Database.
    void addItem(String what, String where){
        Item newItem = new Item(what, where);
        ContentValues values = getContentValues(newItem);
        mDatabase.insert(ItemsDbSchema.ItemTable.NAME, null, values);
        this.setChanged();
        notifyObservers();
    }

    //Given a string naming the item, removes that item from the database.
    //BUG: THIS WILL DELETE TWO OR MORE ITEMS WHICH HAPPEN TO HAVE THE SAME NAME.
    //While it's hard to imagine a scenario where you'd list the same grocery twice,
    //this is hardly the intended functionality.
    public void deleteItem(String what) {
        String whereClause = ItemsDbSchema.ItemTable.Cols.WHAT + "=?";
        String whereArgs[] = {what};
        mDatabase.delete(ItemsDbSchema.ItemTable.NAME, whereClause, whereArgs);
        this.setChanged();
        notifyObservers();
    }

    //Removes all items from the database.
    //Does not delete the table, simply deletes all rows currently on it.
    public void deleteAllItems() {
        //Passing two null values into the where clauses will clear the table.
        mDatabase.delete(ItemsDbSchema.ItemTable.NAME, null, null);
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
}
//END OF LINE//------------------------------------------------------------------------------------
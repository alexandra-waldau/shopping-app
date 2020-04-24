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

//VERSION 8.1//----------------------------------------------------------------------------------
/* VERSION NOTES: Minor code cleanup.
 * @author John Henrik Muller
 */
//-------------------------------------------------------------------------------------------------

//CLASS HEADER//-----------------------------------------------------------------------------------
class ItemsDB extends Observable {
    //FIELDS//-------------------------------------------------------------------------------------

    //private List<Item> itemsDBList;
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

    //Allows you to query the database for Items... I think. :P
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

    //Given a string naming the item, removes that item from the database.
    //This new implementation means I don't have to worry about my previous problems with this method.
    public void deleteItem(String what) {
        String whereclause = ShoppingDbSchema.ItemTable.Cols.WHAT + "=?";
        String whereArgs[] = {what};
        mDatabase.delete(ShoppingDbSchema.ItemTable.NAME, whereclause, whereArgs);
        this.setChanged();
        notifyObservers();
    }

}
//END OF LINE//------------------------------------------------------------------------------------
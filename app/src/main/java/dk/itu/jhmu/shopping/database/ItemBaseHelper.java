package dk.itu.jhmu.shopping.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import dk.itu.jhmu.shopping.database.ItemsDbSchema.ItemTable;

//CLASS HEADER//-----------------------------------------------------------------------------------
public class ItemBaseHelper extends SQLiteOpenHelper {

    //FIELDS//-------------------------------------------------------------------------------------
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "ShoppingList.db";

    //CONSTRUCTOR//--------------------------------------------------------------------------------
    public ItemBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    //METHODS//------------------------------------------------------------------------------------
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ItemTable.NAME + "(" +
                ItemTable.Cols.WHAT + ", " +
                ItemTable.Cols.WHERE + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
//END OF LINE//------------------------------------------------------------------------------------
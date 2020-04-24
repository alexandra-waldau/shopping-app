package dk.itu.jhmu.shopping.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import dk.itu.jhmu.shopping.database.ShoppingDbSchema.ItemTable;
import dk.itu.jhmu.shopping.database.ShoppingDbSchema.ShopTable;

//CLASS HEADER//-----------------------------------------------------------------------------------
public class ShoppingBaseHelper extends SQLiteOpenHelper {

    //FIELDS//-------------------------------------------------------------------------------------
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "ShoppingList.db";

    private static ShoppingBaseHelper sHelper;

    //CONSTRUCTOR//--------------------------------------------------------------------------------
    private ShoppingBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    //METHODS//------------------------------------------------------------------------------------
    public static ShoppingBaseHelper getHelper(Context context) {
        if (sHelper == null) {
            sHelper = new ShoppingBaseHelper(context);
        }
        return sHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ItemTable.NAME + "(" +
                ItemTable.Cols.WHAT + ", " + ItemTable.Cols.WHERE + ")"
        );
        db.execSQL("create table " + ShopTable.NAME + "(" +
                ShopTable.Cols.TITLE + " String primary key" + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
//END OF LINE//------------------------------------------------------------------------------------
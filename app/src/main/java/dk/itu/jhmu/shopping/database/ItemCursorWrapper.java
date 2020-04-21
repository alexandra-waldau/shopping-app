package dk.itu.jhmu.shopping.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import dk.itu.jhmu.shopping.Item;
import dk.itu.jhmu.shopping.database.ItemsDbSchema.ItemTable;

//CLASS HEADER//-----------------------------------------------------------------------------------
public class ItemCursorWrapper extends CursorWrapper {

    //CONSTRUCTOR//--------------------------------------------------------------------------------
    public ItemCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    //METHODS//------------------------------------------------------------------------------------
    public Item getItem() {
        String what = getString(getColumnIndex(ItemTable.Cols.WHAT));
        String where = getString(getColumnIndex(ItemTable.Cols.WHERE));
        return new Item(what, where);
    }
}
//END OF LINE//------------------------------------------------------------------------------------
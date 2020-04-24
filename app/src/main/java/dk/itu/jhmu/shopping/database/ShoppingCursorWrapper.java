package dk.itu.jhmu.shopping.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import dk.itu.jhmu.shopping.Item;
import dk.itu.jhmu.shopping.Shop;
import dk.itu.jhmu.shopping.database.ShoppingDbSchema.ItemTable;
import dk.itu.jhmu.shopping.database.ShoppingDbSchema.ShopTable;

//CLASS HEADER//-----------------------------------------------------------------------------------
public class ShoppingCursorWrapper extends CursorWrapper {

    //CONSTRUCTOR//--------------------------------------------------------------------------------
    public ShoppingCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    //METHODS//------------------------------------------------------------------------------------
    public Item getItem() {
        String what = getString(getColumnIndex(ItemTable.Cols.WHAT));
        String where = getString(getColumnIndex(ItemTable.Cols.WHERE));
        return new Item(what, where);
    }

    public Shop getShop() {
        String name = getString(getColumnIndex(ShopTable.Cols.TITLE));
        return new Shop(name);
    }
}
//END OF LINE//------------------------------------------------------------------------------------
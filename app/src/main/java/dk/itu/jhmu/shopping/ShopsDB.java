package dk.itu.jhmu.shopping;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dk.itu.jhmu.shopping.database.ShoppingBaseHelper;
import dk.itu.jhmu.shopping.database.ShoppingCursorWrapper;
import dk.itu.jhmu.shopping.database.ShoppingDbSchema;
import dk.itu.jhmu.shopping.database.ShoppingDbSchema.ShopTable;

public class ShopsDB {
    private static ShopsDB sShopDB;
    private SQLiteDatabase mDatabase;

    private ShopsDB(Context context) {
        mDatabase = ShoppingBaseHelper.getHelper(context.getApplicationContext())
                .getWritableDatabase();
        if (getShopNames().size() == 0) {
            addShop("Netto");
            addShop("Irma");
            addShop("Bilka");
            addShop("Rema 1000");
        }
    }

    public static ShopsDB get(Context context) {
        if (sShopDB == null) {
            sShopDB = new ShopsDB(context);
        }
        return sShopDB;
    }

    public void addShop(String name) {
        Shop shop = new Shop(name);
        mDatabase.insert(ShopTable.NAME, null, getContentValues(shop));
    }

    public boolean containsShop(String name) {
        ShoppingCursorWrapper cursor = queryShops(ShopTable.Cols.TITLE + " = ?", new String[] {name});
        if (cursor.getCount() == 0) {
            return false;
        }
        return true;
    }

    public List<String> getShopNames() {
        ShoppingCursorWrapper cursor = queryShops(null, null);
        List<String> shopList = new ArrayList<>();

        try {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                shopList.add(cursor.getShop().getName());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return shopList;
    }

    //set up for espresso tests
    public void deleteAllShops() {
        mDatabase.delete(ShopTable.NAME, null, null);
    }

    //set up for espresso tests
    public int getShopCount() {
        ShoppingCursorWrapper cursor = queryShops(null, null);
        return cursor.getCount();
    }

    private static ContentValues getContentValues(Shop shop) {
        ContentValues values = new ContentValues();
        values.put(ShopTable.Cols.TITLE, shop.getName());

        return values;
    }

    private ShoppingCursorWrapper queryShops(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ShoppingDbSchema.ShopTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new ShoppingCursorWrapper(cursor);
    }
}

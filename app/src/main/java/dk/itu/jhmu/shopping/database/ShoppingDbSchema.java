package dk.itu.jhmu.shopping.database;

//CLASS HEADER//-----------------------------------------------------------------------------------
public class ShoppingDbSchema {

    //TABLE: ITEMS//--------------------------------------------------------------------------------
    public static final class ItemTable {
        public static final String NAME = "Items";

        //COLUMNS//----------------------------------------------------------------------------
        public static final class Cols {
            public static final String WHAT = "what";
            public static final String WHERE = "whereC"; //where is a keyword in SQL
        }
    }

    //TABLE: SHOPS//--------------------------------------------------------------------------------
    public static final class ShopTable {
        public  static final String NAME = "Shops";

        //COLUMNS//----------------------------------------------------------------------------
        public static final class Cols {
            public static final String TITLE = "name";
        }
    }
}
//END OF LINE//------------------------------------------------------------------------------------
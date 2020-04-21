package dk.itu.jhmu.shopping.database;

//CLASS HEADER//-----------------------------------------------------------------------------------
public class ItemsDbSchema {

    //INNER CLASS//--------------------------------------------------------------------------------
    public static final class ItemTable {
        public static final String NAME = "Items";
        //INNER CLASS//----------------------------------------------------------------------------
        public static final class Cols {
            public static final String WHAT = "what";
            public static final String WHERE = "whereC"; //where is a keyword in SQL
        }
    }
}
//END OF LINE//------------------------------------------------------------------------------------
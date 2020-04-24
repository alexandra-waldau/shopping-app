package dk.itu.jhmu.shopping;

//VERSION 8.1//----------------------------------------------------------------------------------
/* VERSION NOTES: Dialog boxes introduced.
 * @author John Henrik Muller, Alexandra Waldau
 */
//-------------------------------------------------------------------------------------------------

//CLASS HEADER//-----------------------------------------------------------------------------------
public class Item {
    //FIELDS//-------------------------------------------------------------------------------------

    private String mWhat = null;
    private String mWhere = null;

    //CONSTRUCTOR//--------------------------------------------------------------------------------

    public Item(String what, String where) {
        mWhat = what;
        mWhere = where;
    }

    //METHODS//------------------------------------------------------------------------------------

    //Returns the item as a string, including name and location for use in the list display.
    @Override
    public String toString() {
        return oneLine(""," in: ");
    }

    //Returns the name of the item as a String.
    String getWhat() {
        return mWhat;
    }

    //Accepts a string to set the name of the item.
    void setWhat(String what) {
        mWhat = what;
    }

    //Returns the location of the item as a String.
    String getWhere() {
        return mWhere;
    }

    //Accepts a string to set the location of the item.
    void setWhere(String where) {
        mWhere = where;
    }

    //Returns a string with the given values, pre and post, wrapped around the what and where fields.
    String oneLine(String pre, String post) {
        return pre + mWhat + post + mWhere;
    }
}

//END OF LINE//------------------------------------------------------------------------------------

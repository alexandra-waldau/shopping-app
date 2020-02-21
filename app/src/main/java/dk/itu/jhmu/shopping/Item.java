package dk.itu.jhmu.shopping;

//VERSION 4.3.1// Week 4 //--------------------------------------------------------------------------
//VERSION NOTES: Fragments! Unit Tests! Everything's broken! Help I'm unsupervised!
/*
 * @author John Henrik Muller
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

    //Getters and setters for the what field.
    String getWhat() {
        return mWhat;
    }

    void setWhat(String what) {
        mWhat = what;
    }

    //Getters and setters for the where field.
    String getWhere() {
        return mWhere;
    }

    void setWhere(String where) {
        mWhere = where;
    }

    //Returns a string with the given values, pre and post, wrapped around the what and where fields.
    String oneLine(String pre, String post) {
        return pre + mWhat + post + mWhere;
    }
}
//END OF LINE//------------------------------------------------------------------------------------

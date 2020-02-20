package dk.itu.jhmu.shopping;

//VERSION 4.1// Week 4 //--------------------------------------------------------------------------
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
    @Override
    public String toString() {
        return oneLine(""," in: ");
    }

    public String getWhat() {
        return mWhat;
    }

    public void setWhat(String what) {
        mWhat = what;
    }

    public String getWhere() {
        return mWhere;
    }

    public void setWhere(String where) {
        mWhere = where;
    }

    public String oneLine(String pre, String post) {
        return pre + mWhat + post + mWhere;
    }
}
//END OF LINE//------------------------------------------------------------------------------------

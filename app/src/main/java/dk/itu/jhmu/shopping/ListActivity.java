package dk.itu.jhmu.shopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;

//VERSION 4.3// Week 4 //--------------------------------------------------------------------------
//VERSION NOTES: Fragments! Unit Tests! Everything's broken! Help I'm unsupervised!
/*
 * @author John Henrik Muller
 */
//-------------------------------------------------------------------------------------------------

//CLASS HEADER//-----------------------------------------------------------------------------------
public class ListActivity extends AppCompatActivity {

    //MAIN METHOD//--------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        //Here's our fragment manager! It manages fragments!
        //I don't know why we still need a ListActivity but I guess we will find out soon...
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new ListFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
//END OF LINE//------------------------------------------------------------------------------------

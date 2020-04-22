package dk.itu.jhmu.shopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;

//VERSION 8.0//------------------------------------------------------------------------------------
/* VERSION NOTES: Moving on to the final project!
 * @author John Henrik Muller
 * @author Alexandra Waldau
 */
//-------------------------------------------------------------------------------------------------
//CLASS HEADER//-----------------------------------------------------------------------------------
public class ListActivity extends AppCompatActivity {

    //MAIN METHOD//--------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        //Here we initialize our fragment manager and accompanying fragments.
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        //If there is no fragment currently, create a new ListFragment and set it up.
        if (fragment == null) {
            fragment = new ListFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
//END OF LINE//------------------------------------------------------------------------------------

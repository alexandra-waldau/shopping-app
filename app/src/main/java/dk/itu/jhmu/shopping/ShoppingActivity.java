package dk.itu.jhmu.shopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.res.Configuration;
import android.os.Bundle;

//VERSION 9.0//------------------------------------------------------------------------------------
/* VERSION NOTES: Tests and all the features!
 * @author John Henrik Muller
 * @author Alexandra Waldau
 */
//-------------------------------------------------------------------------------------------------

//CLASS HEADER//-----------------------------------------------------------------------------------
public class ShoppingActivity extends AppCompatActivity {

    //MAIN METHOD//--------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        //Here we initialize our fragment manager and accompanying fragments.
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        Fragment fragmentList = fm.findFragmentById(R.id.list_container);

        //First checks for landscape orientation and loads two fragments if so, if not only loads
        //one fragment.
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (fragment == null) {
                fragment = new UIFragment();
                fm.beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .commit();
            }
            if (fragmentList == null) {
                fragmentList = new ListFragment();
                fm.beginTransaction()
                        .add(R.id.list_container, fragmentList)
                        .commit();
            }

        } else {
            if (fragment == null) {
                fragment = new UIFragment();
                fm.beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .commit();
            }
        }
    }
}
//END OF LINE//------------------------------------------------------------------------------------


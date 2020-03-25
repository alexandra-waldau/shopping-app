package dk.itu.jhmu.shopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;

//VERSION 6.0//------------------------------------------------------------------------------------
/* VERSION NOTES: Recycler view comes into play!
 * @author John Henrik Muller
 */
//-------------------------------------------------------------------------------------------------

//CLASS HEADER//-----------------------------------------------------------------------------------
public class ShoppingActivity extends AppCompatActivity {

    //MAIN METHOD//--------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        //Here's our fragment manager! It manages fragments!
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        //If there's no fragment when the Shopping Activity is created, this creates a new UI Fragment and adds it.
        if (fragment == null) {
            fragment = new UIFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

        //If the orientation of the phone is in landscape, it loads another fragment for us and adds it.
        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            Fragment fragmentList = fm.findFragmentById(R.id.list_container);
            if (fragmentList == null) {
                fragmentList = new ListFragment();
                fm.beginTransaction()
                        .add(R.id.list_container, fragmentList)
                        .commit();
            }
        }
    }
}
//END OF LINE//------------------------------------------------------------------------------------


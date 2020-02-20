package dk.itu.jhmu.shopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//VERSION 4.1// Week 4 //--------------------------------------------------------------------------
//VERSION NOTES: Fragments! Unit Tests! Everything's broken! Help I'm unsupervised!
/*
 * @author John Henrik Muller
 */
//-------------------------------------------------------------------------------------------------
public class ShoppingActivity extends AppCompatActivity {

    //MAIN METHOD//--------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        //Here's our fragment manager! It manages fragments!
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new UIFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
//END OF LINE//------------------------------------------------------------------------------------


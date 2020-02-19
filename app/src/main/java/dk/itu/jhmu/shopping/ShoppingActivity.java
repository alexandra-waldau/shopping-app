package dk.itu.jhmu.shopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//VERSION 3.1// Week 3 //
//VERSION NOTES: Singletons! New Activity! Remove items! And more!
/*
* @author John Henrik Muller
*/
//CLASS HEADER//-----------------------------------------------------------------------------------
public class ShoppingActivity extends AppCompatActivity {
    //FIELDS//-------------------------------------------------------------------------------------
    // GUI variables //
    private Button listItemsBtn;
    private Button addItemBtn;
    private EditText whereEditText;
    private EditText whatEditText;
    // Model: Database of items //
    private ItemsDB itemsDB;

    //MAIN METHOD//--------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_activity);

        //Creates the item database, uses the Singleton method rather than the constructor.
        itemsDB = ItemsDB.get();

        //Attaching a bunch of variables to elements in our XML.
        whereEditText = (EditText) findViewById(R.id.whereEditText);
        whatEditText = (EditText) findViewById(R.id.whatEditText);

        //When clicked, the addItemButton will add an item to the ItemsDB.
        addItemBtn = (Button) findViewById(R.id.addItemBtn);
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Checks if either field is empty, and creates a toast if they are.
                if (isEmpty(whatEditText)) {
                    makeToast(getString(R.string.pleaseAddItemToast)); return; }
                if (isEmpty(whereEditText)) {
                    makeToast(getString(R.string.pleaseAddLocationToast)); return; }

                //If both fields are filled, creates an item and adds it to the database.
                String what = whatEditText.getText().toString();
                String where = whereEditText.getText().toString();
                itemsDB.addItem(what,where);

                //Clears the fields and plays a toast.
                whereEditText.getText().clear();
                whatEditText.getText().clear();
                makeToast(getString(R.string.addedItemToast));
            }
        });

        //When clicked, the listItemsButton will open the ListActivity.
        listItemsBtn = (Button) findViewById(R.id.listItemsBtn);
        listItemsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListActivity
                Intent intent = new Intent(ShoppingActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });
    }

    //HELPER METHODS//-----------------------------------------------------------------------------

    //Presents a toast using the given string.
    public void makeToast(String input){
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
    }

    //Checks if an EditText field is empty. Returns True if empty, False if not.
    private boolean isEmpty(EditText text) {
        return text.getText().toString().trim().length() == 0;
    }

    //Some potential code to hide the keyboard? Needs further investigation.
    //public static void hideKeyboardFrom(Activity activity) {
     //   InputMethodManager imm =
        //(ScriptGroup.Input) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),0);
    //}
}
//END OF LINE//------------------------------------------------------------------------------------


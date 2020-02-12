package dk.itu.jhmu.shopping;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

//VERSION 2.2// Week 3 //
//VERSION NOTES: Added strings to the XML for our toasts. Experimented with a hide keyboard method.
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
    private TextView itemsList;
    // Model: Database of items //
    private ItemsDB itemsDB;

    //MAIN METHOD//--------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_activity);

        //Creates the item database.
        itemsDB = new ItemsDB();
        //Attaching a bunch of variables to elements in our XML.
        whereEditText = (EditText) findViewById(R.id.whereEditText);
        whatEditText = (EditText) findViewById(R.id.whatEditText);
        itemsList = (TextView) findViewById(R.id.itemsListTextView);

        //When clicked, the addItemButton will add an item to the ItemsDB.
        addItemBtn = (Button) findViewById(R.id.addItemBtn);
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Checks if either field is empty, and creates a toast if they are.
                if (isEmpty(whatEditText)) {
                    String request = getString(R.string.pleaseAddItemToast);
                    makeToast(request); return; }
                if (isEmpty(whereEditText)) {
                    String request = getString(R.string.pleaseAddLocationToast);
                    makeToast(request); return; }

                //If both fields are filled, creates an item and adds it to the database.
                String what = whatEditText.getText().toString();
                String where = whereEditText.getText().toString();
                itemsDB.addItem(what,where);
                //Clears the fields and plays a toast.
                whereEditText.getText().clear();
                whatEditText.getText().clear();
                String congrats = getString(R.string.addedItemToast);;
                makeToast(congrats);
            }
        });

        //When clicked, the listItemsButton will list the items using the ItemsDB.
        listItemsBtn = (Button) findViewById(R.id.listItemsBtn);
        listItemsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemsList.setBackgroundColor(Color.parseColor("#FFFFFF"));
                String list = "Shopping List:" + itemsDB.listItems();
                itemsList.setText(list);
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

    //public static void hideKeyboardFrom(Activity activity) {
     //   InputMethodManager imm =
        //(ScriptGroup.Input) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),0);
    //}
}
//END OF LINE//------------------------------------------------------------------------------------


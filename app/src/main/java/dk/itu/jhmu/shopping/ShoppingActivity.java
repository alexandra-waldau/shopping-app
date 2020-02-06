package dk.itu.jhmu.shopping;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

//VERSION 2.0// Week 2
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
                if (isEmpty(whatEditText)) { pleaseAddItemToast(); return; }
                if (isEmpty(whereEditText)) { pleaseAddLocationToast();return; }

                //If both fields are filled, creates an item and adds it to the database.
                String what = whatEditText.getText().toString();
                String where = whereEditText.getText().toString();
                itemsDB.addItem(what,where);
                //Clears the fields and plays a toast.
                whereEditText.getText().clear();
                whatEditText.getText().clear();
                addItemToast();
            }
        });

        //When clicked, the listItemsButton will list the items using the ItemsDB.
        listItemsBtn = (Button) findViewById(R.id.listItemsBtn);
        listItemsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemsList.setBackgroundColor(Color.parseColor("#FFFFFF"));
                String list = "Shopping List:"+itemsDB.listItems();
                itemsList.setText(list);
            }
        });
    }

    //HELPER METHODS//-----------------------------------------------------------------------------

    //Presents a toast if an item is correctly added.
    public void addItemToast(){
        String addedItem = "Item added!";
        Toast.makeText(this, addedItem, Toast.LENGTH_SHORT).show();
    }

    //Presents a toast asking the user to fill in the item field.
    public void pleaseAddItemToast(){
        String addedItem = "Please fill in an item.";
        Toast.makeText(this, addedItem, Toast.LENGTH_SHORT).show();
    }

    //Presents a toast asking the user to fill in the location field.
    public void pleaseAddLocationToast(){
        String addedItem = "Please fill in a location.";
        Toast.makeText(this, addedItem, Toast.LENGTH_SHORT).show();
    }

    //Checks if an EditText field is empty. Returns True if empty, False if not.
    private boolean isEmpty(EditText Text) {
        return Text.getText().toString().trim().length() == 0;
    }
}
//END OF LINE//------------------------------------------------------------------------------------


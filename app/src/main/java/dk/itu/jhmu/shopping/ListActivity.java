package dk.itu.jhmu.shopping;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//CLASS HEADER//-----------------------------------------------------------------------------------
public class ListActivity extends AppCompatActivity {
    //FIELDS//-------------------------------------------------------------------------------------
    // GUI variables //
    private TextView itemsList;
    private ItemsDB itemsDB;
    private Button removeItemBtn;
    private Button removeAllItemsBtn;

    //MAIN METHOD//--------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        //Sets up the itemsDB using the singleton method rather than the usual constructor.
        itemsDB = ItemsDB.get();

        //Setting up our other XML elements.
        itemsList = (TextView) findViewById(R.id.itemsListTextView);
        updateList();

        //Clicking the remove item button will remove items in order of most recent to least recent.
        //If there are no items left, will play a special toast.
        removeItemBtn = (Button) findViewById(R.id.removeItemBtn);
        removeItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!itemsDB.isEmpty()) {
                    itemsDB.removeItem();
                    updateList();
                    makeToast(getString(R.string.removedItemToast));
                } else {
                    makeToast(getString(R.string.noItemsToast));
                }
            }
        });

        //Clicking this button will remove all items on the list.
        removeAllItemsBtn = (Button) findViewById(R.id.removeAllItemsBtn);
        removeAllItemsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (!itemsDB.isEmpty()) {
                    itemsDB.removeAllItems();
                    updateList();
                    makeToast(getString(R.string.removedAllItemsToast));
                } else {
                    makeToast(getString(R.string.noItemsToast));
                }
            }
        });

    }

    //HELPER METHODS//-----------------------------------------------------------------------------

    //Updates the shopping list display.
    public void updateList() {
        itemsList.setBackgroundColor(Color.parseColor("#FFFFFF"));
        String list = "Shopping List:" + itemsDB.listItems();
        itemsList.setText(list);
    }

    //Presents a toast using the given string.
    //Code duplication here... I suppose I would make an interface that supports these methods? Depends.
    public void makeToast(String input){
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
    }
}
//END OF LINE//------------------------------------------------------------------------------------

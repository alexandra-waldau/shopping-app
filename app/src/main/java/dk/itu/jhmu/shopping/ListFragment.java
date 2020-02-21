package dk.itu.jhmu.shopping;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

//VERSION 4.3.1// Week 4 //--------------------------------------------------------------------------
//VERSION NOTES: Fragments! Unit Tests! Everything's broken! Help I'm unsupervised!
/*
 * @author John Henrik Muller
 */
//-------------------------------------------------------------------------------------------------

//CLASS HEADER//-----------------------------------------------------------------------------------
public class ListFragment extends Fragment {
    //FIELDS//-------------------------------------------------------------------------------------

    private TextView itemsList;
    private ItemsDB itemsDB;
    private Button removeItemBtn;
    private Button removeAllItemsBtn;

    //MAIN METHOD//--------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        //Sets up the itemsDB using the singleton method rather than the usual constructor.
        itemsDB = ItemsDB.get();

        //Setting up our other XML elements.
        itemsList = (TextView) v.findViewById(R.id.itemsListTextView);
        updateList();

        //Clicking the remove item button will remove items in order of most recent to least recent.
        //If there are no items left, will play a special toast.
        removeItemBtn = (Button) v.findViewById(R.id.removeItemBtn);
        removeItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!itemsDB.isEmpty()) {
                    itemsDB.removeLastItem();
                    updateList();
                    makeToast(getString(R.string.removedItemToast));
                } else {
                    makeToast(getString(R.string.noItemsToast));
                }
            }
        });

        //Clicking this button will remove all items on the list.
        removeAllItemsBtn = (Button) v.findViewById(R.id.removeAllItemsBtn);
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

        return v;
    }

    //HELPER METHODS//-----------------------------------------------------------------------------

    //Updates the shopping list display.
    private void updateList() {
        itemsList.setBackgroundColor(Color.parseColor("#FFFFFF"));
        String list = "Shopping List:" + itemsDB.listItems();
        itemsList.setText(list);
    }

    //Presents a toast using the given string.
    //Code duplication here... I suppose I would make an interface that supports these methods? Depends.
    private void makeToast(String input){
        //Not sure why we need to generate a context in a fragment but not an activity...
        Toast.makeText(getContext(), input, Toast.LENGTH_SHORT).show();
    }
}
//END OF LINE//------------------------------------------------------------------------------------
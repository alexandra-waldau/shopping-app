package dk.itu.jhmu.shopping;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//VERSION 4.1// Week 4 //--------------------------------------------------------------------------
//VERSION NOTES: Fragments! Unit Tests! Everything's broken! Help I'm unsupervised!
/*
 * @author John Henrik Muller
 */
//-------------------------------------------------------------------------------------------------
import androidx.fragment.app.Fragment;

//VERSION 4.2// Week 4 //--------------------------------------------------------------------------
//VERSION NOTES: Fragments! Unit Tests! Everything's broken! Help I'm unsupervised!
/*
 * @author John Henrik Muller
 */
//-------------------------------------------------------------------------------------------------

//CLASS HEADER//-----------------------------------------------------------------------------------
public class UIFragment extends Fragment {
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ui, container, false);

        //Creates the item database, uses the Singleton method rather than the constructor.
        itemsDB = ItemsDB.get();

        //Here's how to hide the keyboard... Thanks StackOverflow!
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //Attaching a bunch of variables to elements in our XML.
        whereEditText = (EditText) v.findViewById(R.id.whereEditText);
        whatEditText = (EditText) v.findViewById(R.id.whatEditText);

        //When clicked, the addItemButton will add an item to the ItemsDB.
        addItemBtn = (Button) v.findViewById(R.id.addItemBtn);
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

                //Added a line to return the focus to the first panel after adding an item.
                whatEditText.requestFocus();
            }
        });

        //When clicked, the listItemsButton will open the ListActivity.
        listItemsBtn = (Button) v.findViewById(R.id.listItemsBtn);
        listItemsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListActivity
                Intent intent = new Intent(getActivity(), ListActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

    //HELPER METHODS//-----------------------------------------------------------------------------

    //Presents a toast using the given string.
    public void makeToast(String input){
        Context context = getContext();
        Toast.makeText(context, input, Toast.LENGTH_SHORT).show();
    }

    //Checks if an EditText field is empty. Returns True if empty, False if not.
    private boolean isEmpty(EditText text) {
        return text.getText().toString().trim().length() == 0;
    }

    //Hide keyboard helper method!
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
//END OF LINE//------------------------------------------------------------------------------------
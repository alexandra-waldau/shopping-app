package dk.itu.jhmu.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

//VERSION 9//--------------------------------------------------------------------------------------
/* VERSION NOTES: Tests and all the features!
 * @author John Henrik Muller
 * @author Alexandra Waldau
 */
//-------------------------------------------------------------------------------------------------

//CLASS HEADER//-----------------------------------------------------------------------------------
public class UIFragment extends Fragment {
    //FIELDS//-------------------------------------------------------------------------------------

    private Button listItemsBtn;
    private Button addItemBtn;
    private EditText whereEditText;
    private EditText whatEditText;

    private ItemsDB itemsDB;
    private ShopsDB shopDB;

    private static final int REQUEST_VIEW_SHOP = 0;
    private static final String DIALOG_VIEW_SHOP = "DialogViewShop";
    private static final int REQUEST_ADD_SHOP = 1;
    private static final String DIALOG_ADD_SHOP = "DialogAddShop";

    //MAIN METHOD//--------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemsDB = ItemsDB.get(getContext());
        shopDB = ShopsDB.get(getActivity());
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ui, container, false);

        //Hide keyboard...sometimes. :P
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

        whereEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                ShopPickerFragment dialog = new ShopPickerFragment();
                dialog.setTargetFragment(UIFragment.this, REQUEST_VIEW_SHOP);
                dialog.show(manager, DIALOG_VIEW_SHOP);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_VIEW_SHOP) {
            String shopName = data.getStringExtra(ShopPickerFragment.EXTRA_NAME);
            whereEditText.setText(shopName);
        }
        else if (requestCode == REQUEST_ADD_SHOP) {
            String name = data.getStringExtra(ShopAdderFragment.EXTRA_SHOP);
            if (shopDB.containsShop(name)) {
                makeToast("Shop already exists!");
            }
            else {
                shopDB.addShop(name);
                makeToast("Shop added!");
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_ui, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_shop_item:
                FragmentManager fm = getActivity().getSupportFragmentManager();
                ShopAdderFragment dialog = new ShopAdderFragment();
                dialog.setTargetFragment(UIFragment.this, REQUEST_ADD_SHOP);
                dialog.show(fm, DIALOG_ADD_SHOP);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //HELPER METHODS//-----------------------------------------------------------------------------

    //Presents a toast using the given string.
    private void makeToast(String input){
        //Not sure why we need to generate a context in a fragment but not an activity...
        Toast.makeText(getContext(), input, Toast.LENGTH_SHORT).show();
    }

    //Checks if a given EditText is empty. Returns True if empty, False if not.
    private boolean isEmpty(EditText text) {
        return text.getText().toString().trim().length() == 0;
    }

}
//END OF LINE//------------------------------------------------------------------------------------
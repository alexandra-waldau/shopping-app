package dk.itu.jhmu.shopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

//VERSION 9.0//------------------------------------------------------------------------------------
/* VERSION NOTES: Tests and all the features!
 * @author John Henrik Muller
 * @author Alexandra Waldau
 */
//-------------------------------------------------------------------------------------------------

//CLASS HEADER//-----------------------------------------------------------------------------------
public class ListFragment extends Fragment implements Observer {
    //FIELDS//-------------------------------------------------------------------------------------

    private RecyclerView mListRecyclerView;
    private ItemAdapter mAdapter;
    private ItemsDB itemsDB;
    private TextView itemCountTextView;
    private Resources stringResources;

    //These fields are used for the subtitle.
    private boolean mSubtitleVisible;
    private static final String SAVED_SUBSTITUTE_VISIBLE = "subtitle";

    //MAIN METHOD//--------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemsDB = ItemsDB.get(getContext());
        itemsDB.addObserver(this);
        stringResources = getActivity().getResources();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Now inflates a recycler view for our list instead of the list we had before.
        View v = inflater.inflate(R.layout.fragment_list_recyclerview, container, false);

        //This creates the recycler view and attaches it to our variable.
        mListRecyclerView = (RecyclerView) v.findViewById(R.id.list_recycler_view);
        mListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        itemCountTextView = (TextView) v.findViewById(R.id.itemCount_TextView);

        //Used to check what the status of the item counter is.
        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBSTITUTE_VISIBLE);
        }

        updateUI();
        return v;
    }

    //This method initializes and inflates our toolbar, and the actions (items) it contains.
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_list, menu);

        //This manages our show item count action.
        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }

    }

    //This is the code that actually gets run when you click on an item in the toolbar.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.show_subtitle: //Code for show item count.
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;

            //Function to delete all items.
            case R.id.delete_all:
                itemsDB.deleteAllItems();
                return true;

            //Developer function to add 5 items at a time.
            case R.id.add_items:
                itemsDB.batchAddItems();
                return true;

            //This code implements the share list fuction.
            case R.id.share_list:
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, itemsDB.getListReport());
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.list_report_subject));
                i = Intent.createChooser(i,getString(R.string.send_report)); //This allows us to choose every time.
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //This saves the state of the item counter beneath the toolbar header.
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBSTITUTE_VISIBLE, mSubtitleVisible);
    }

    //This method updates the current item count in the toolbar.
    public void updateSubtitle() {

        int itemCount = itemsDB.getItemCount();

        if (isAdded()) {
            String subtitle = getActivity().getResources().getQuantityString(R.plurals.subtitle_plural, itemCount, itemCount);

            if (!mSubtitleVisible) {
                subtitle = null;
            }

            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.getSupportActionBar().setSubtitle(subtitle);
        }
    }

    //HELPER METHODS//-----------------------------------------------------------------------------

    //This method updates the UI with the latest ItemsDB and puts it into the RecyclerView.
    private void updateUI() {
        List<Item> items = itemsDB.getItemsDB();
        if (mAdapter == null) {
            mAdapter = new ItemAdapter(itemsDB.getItemsDB());
            mListRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setItems(items);
            mAdapter.notifyDataSetChanged();
        }

        updateItemCount();
        updateSubtitle();
    }

    //Updates the Adapter as well as the UI.
    public void update(Observable observable, Object data) {
        mAdapter.notifyDataSetChanged();
        updateUI();
    }

    //This updates the itemCount TextView.
    public void updateItemCount() {
        int itemCount = itemsDB.getItemCount();

        //This long piece of code basically allows us to use our plural Strings to append the right word (item/items) to the count.
        String items = stringResources.getQuantityString(R.plurals.subtitle_plural, itemCount, itemCount);

        itemCountTextView.setText(items);
    }

    //INNER CLASS//================================================================================

    // The ItemHolder is an extension of the ViewHolder class. It simply holds the information about
    // a given item.

    //CLASS HEADER//-------------------------------------------------------------------------------
    private class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //FIELDS//---------------------------------------------------------------------------------

        private ImageButton mDeleteItemImgBtn;
        private TextView mWhatTextView;
        private TextView mWhereTextView;
        private TextView mNoView;

        //CONSTRUCTOR//----------------------------------------------------------------------------
        public ItemHolder(View itemView) {
            super(itemView);
            //Sets up text and number views.
            mNoView = itemView.findViewById(R.id.item_no);
            mWhatTextView = itemView.findViewById(R.id.item_what);
            mWhereTextView = itemView.findViewById(R.id.item_where);
            //Sets up our ImageButton used for deleting items.
            mDeleteItemImgBtn = itemView.findViewById(R.id.deleteItemImgBtn);
            mDeleteItemImgBtn.setOnClickListener(this);
        }

        //METHODS//--------------------------------------------------------------------------------

        //Binds the item, providing a positional number, what and where for the TextViews.
        public void bind(Item item, int position) {
            mNoView.setText(" " + (position + 1) + " ");
            mWhatTextView.setText(item.getWhat());
            mWhereTextView.setText(item.getWhere());
        }

        //This onClick method allows us to delete items again.
        @Override
        public void onClick(View v) {
            String deleteItem = mWhatTextView.getText().toString().trim();
            String deletePlace = mWhereTextView.getText().toString().trim();
            itemsDB.deleteItem(deleteItem, deletePlace);
        }

    }

    //INNER CLASS//================================================================================

    // The ItemAdapter class is an extension of the Adapter class, it communicates between the
    // RecyclerView and our ItemHolders.

    //CLASS HEADER//-------------------------------------------------------------------------------
    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {
        //FIELDS//---------------------------------------------------------------------------------
        private List<Item> mItems;

        //CONSTRUCTOR//----------------------------------------------------------------------------
        public ItemAdapter(List<Item> items) {
            mItems = items;
        }

        //METHODS//--------------------------------------------------------------------------------
        @NonNull
        @Override
        public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View v = layoutInflater.inflate(R.layout.one_row, parent, false);
            return new ItemHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemHolder holder, final int position) {
            final Item item = mItems.get(position);
            holder.bind(item, position);
        }

        //Accepts a List of Item objects and sets them into the database.
        void setItems(List<Item> items) {
            mItems = items;
        }

        //Returns the amount of items in the database.
        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }
}
//END OF LINE//------------------------------------------------------------------------------------
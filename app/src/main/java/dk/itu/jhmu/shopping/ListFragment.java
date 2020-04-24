package dk.itu.jhmu.shopping;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

//VERSION 8.1//----------------------------------------------------------------------------------
/* VERSION NOTES: Dialog boxes introduced.
 * @author John Henrik Muller, Alexandra Waldau
 */
//-------------------------------------------------------------------------------------------------

//CLASS HEADER//-----------------------------------------------------------------------------------
public class ListFragment extends Fragment implements Observer {
    //FIELDS//-------------------------------------------------------------------------------------

    private RecyclerView mListRecyclerView;
    private ItemAdapter mAdapter;
    private ItemsDB itemsDB;

    //MAIN METHOD//--------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemsDB = ItemsDB.get(getContext());
        itemsDB.addObserver(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Now inflates a recycler view for our list instead of the list we had before.
        View v = inflater.inflate(R.layout.fragment_list_recyclerview, container, false);

        //This creates the recycler view and attaches it to our variable.
        mListRecyclerView = (RecyclerView) v.findViewById(R.id.list_recycler_view);
        mListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return v;
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
    }

    //Updates the Adapter as well as the UI.
    public void update(Observable observable, Object data) {
        mAdapter.notifyDataSetChanged();
        updateUI();
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
            itemsDB.deleteItem(deleteItem);
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
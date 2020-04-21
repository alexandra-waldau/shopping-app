package dk.itu.jhmu.shopping;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

//VERSION 6.2//------------------------------------------------------------------------------------
/* VERSION NOTES: Trying to reimplement delete features and improve the look of the recycler view.
 * @author John Henrik Muller
 */
//-------------------------------------------------------------------------------------------------

//CLASS HEADER//-----------------------------------------------------------------------------------
public class ListFragment extends Fragment implements Observer {
    //FIELDS//-------------------------------------------------------------------------------------

    private RecyclerView mListRecyclerView;
    private ItemAdapter mAdapter;
    private ItemsDB itemsDB; //Not sure if we still need to hold on to this...

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
        //Sets the background colour of the RecyclerView to 'whitesmoke'.
        mListRecyclerView.setBackgroundColor(Color.parseColor("#f5f5f5"));

        updateUI();
        return v;
    }

    //HELPER METHODS//-----------------------------------------------------------------------------

    //This method updates the UI with the latest ItemsDB and puts it into the RecyclerView.
    private void updateUI() {
        mAdapter = new ItemAdapter(itemsDB.getItems());
        mListRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void update(Observable observable, Object data) {
        mAdapter.notifyDataSetChanged();
    }

    //INNER CLASSES//------------------------------------------------------------------------------

    //The ItemHolder is an extension of the ViewHolder class. It simply holds the information about a given item.
    private class ItemHolder extends RecyclerView.ViewHolder {
        //FIELDS//

        //Want to thank Rasmus Hervig for showing me how he implemented this delete button!
        //This will allow us to delete individual items from the recycler view!
        private Button mDeleteButton;

        private TextView mWhatTextView;
        private TextView mWhereTextView;
        private TextView mNoView;

        //CONSTRUCTOR//
        public ItemHolder(View itemView) {
            super(itemView);

            mNoView = itemView.findViewById(R.id.item_no);
            mWhatTextView = itemView.findViewById(R.id.item_what);
            mWhereTextView = itemView.findViewById(R.id.item_where);
            mDeleteButton = itemView.findViewById(R.id.deleteItemBtn);
        }

        //METHODS//
        public void bind(Item item, int position) {
            mNoView.setText(" " + position + " ");
            mWhatTextView.setText(item.getWhat());
            mWhereTextView.setText(item.getWhere());
        }

    }

    //The ItemAdapter class is an extension of the Adapter class, it communicates between the
    //RecyclerView and our ItemHolders.
    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {
        //FIELDS//
        private List<Item> mItems;

        //CONSTRUCTOR//
        public ItemAdapter(List<Item> items) {
            mItems = items;
        }

        //METHODS//
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

            // Here's the delete button functionality. Again, thanks to Rasmus Hervig for showing me
            // how this works!
            holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    itemsDB.deleteItem(item);  //Remove the item from the list.
                    notifyItemChanged(position); //Notify the adapter about the removed item.
                }
            });
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

    }

}
//END OF LINE//------------------------------------------------------------------------------------
package dk.itu.jhmu.shopping;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//VERSION 6.1//------------------------------------------------------------------------------------
/* VERSION NOTES: Trying to reimplement delete features and improve the look of the recycler view.
 * @author John Henrik Muller
 */
//-------------------------------------------------------------------------------------------------

//CLASS HEADER//-----------------------------------------------------------------------------------
public class ListFragment extends Fragment {
    //FIELDS//-------------------------------------------------------------------------------------

    //private ItemsDB itemsDB;
    private RecyclerView mListRecyclerView;
    private ItemAdapter mAdapter;

    //MAIN METHOD//--------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Now inflates a recyclerview for our list instead of the list we had before.
        View v = inflater.inflate(R.layout.fragment_list_recyclerview, container, false);

        //This creates the recycler view and attaches it to our variable.
        mListRecyclerView = (RecyclerView) v
                .findViewById(R.id.list_recycler_view);
        mListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //This is our new helper method which updates the UI.
        updateUI();

        return v;
    }

    //HELPER METHODS//-----------------------------------------------------------------------------

    //This method updates the UI with the latest ItemsDB and puts it into the RecyclerView.
    private void updateUI() {
        ItemsDB itemsDB = ItemsDB.get(getActivity());
        List<Item> items = itemsDB.getItems();
        mAdapter = new ItemAdapter(items);
        mListRecyclerView.setAdapter(mAdapter);
    }

    //INNER CLASSES//------------------------------------------------------------------------------

    //The ItemHolder is an extension of the ViewHolder class. It simply holds the information about a given item.
    private class ItemHolder extends RecyclerView.ViewHolder {
        //FIELDS//
        private TextView mWhatTextView;
        private TextView mWhereTextView;
        private TextView mNoView;

        //CONSTRUCTOR//
        public ItemHolder(View itemView) {
            super(itemView);

            mNoView = itemView.findViewById(R.id.item_no);
            mWhatTextView = itemView.findViewById(R.id.item_what);
            mWhereTextView = itemView.findViewById(R.id.item_where);

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
        public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
            final Item item = mItems.get(position);
            holder.bind(item, position);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

    }

}
//END OF LINE//------------------------------------------------------------------------------------
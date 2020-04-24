package dk.itu.jhmu.shopping;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import java.util.List;

public class ShopPickerFragment extends DialogFragment {
    private ShopsDB shopDB;
    private String selectedItem;
    private String[] shopList;

    public static final String EXTRA_NAME = "shopping.shop.name";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shopDB = ShopsDB.get(getActivity());
    }

    //returns a new dialog instance to be displayed by the fragment
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        List<String> shops = shopDB.getShopNames();
        shopList = shops.toArray(new String[shops.size()]);

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Choose a shop:")
                //no item is selected in the beginning
                .setSingleChoiceItems(shopList, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectedItem = shopList[i];
                    }
                })
                .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendResult(Activity.RESULT_OK, selectedItem);
                    }
                })
                .setNegativeButton(R.string.cancel_button, null);


        AlertDialog alertDialog = dialog.create();
        return  alertDialog;
    }

    private void sendResult(int resultCode, String shopName) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_NAME, shopName);
        //call onActivityResult on target fragment to pass data
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}

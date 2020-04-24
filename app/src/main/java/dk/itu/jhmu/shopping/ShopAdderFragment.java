package dk.itu.jhmu.shopping;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class ShopAdderFragment extends DialogFragment {
    private EditText textBox;

    public static final String EXTRA_SHOP = "shopping.shop.new_shop";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_shop_add, null);
        textBox = v.findViewById(R.id.add_shop);

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setView(v)
                .setTitle("Add a shop:")
                .setPositiveButton(R.string.add_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String shopName = textBox.getText().toString();
                        sendResponse(Activity.RESULT_OK, shopName);
                    }
                })
                .setNegativeButton(R.string.cancel_button, null);

        AlertDialog alertDialog = dialog.create();
        return alertDialog;
    }

    private void sendResponse(int resultCode, String shopName) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SHOP, shopName);

        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);
    }
}

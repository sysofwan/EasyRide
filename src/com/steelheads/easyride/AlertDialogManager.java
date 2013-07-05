package com.steelheads.easyride;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

public class AlertDialogManager {

    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        Log.d("alertDialog", "Starting");
        if (status != null) {
            alertDialog.setIcon((status) ? R.drawable.btn_check_buttonless_on
                    : R.drawable.ic_menu_notifications);
        }
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });

        alertDialog.show();
    }
}

package com.mahoneydev.usdafmexchange;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

/**
 * Created by bichongg on 8/10/2016.
 */
public abstract class LongPressDeleteDialogListener implements View.OnLongClickListener{
    private Frontpage context;
    private String title;
    private String message;
    public LongPressDeleteDialogListener(Frontpage contexti, String titlei, String messagei)
    {
        title=titlei;
        message=messagei;
        context=contexti;
    }
    public boolean onLongClick(View v)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do your work here
                deleteaction();
                dialog.dismiss();

            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        alert.show();
        return true;
    }
    public abstract void deleteaction();
}

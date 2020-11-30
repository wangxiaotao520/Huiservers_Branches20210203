package com.huacheng.huiservers.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.huacheng.huiservers.R;


public class DialogUtil {

    public static void alert(Context context, String msg) {

        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setMessage(msg);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();

    }

    public static void confirm(Context context, String msg, DialogInterface.OnClickListener ocl) {

        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setMessage(msg);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", ocl);
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();

    }

    public static void alertWithTitle(Context context, String title, String msg) {

        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();

    }

    public static void customMsgAlert(Context context, String title, String msg) {

        final Dialog d = new Dialog(context, R.style.my_dialog);
        d.setContentView(R.layout.dialog_msg_alert);
        TextView titleTx = d.findViewById(R.id.title);
        TextView contentTx = d.findViewById(R.id.tv_content);
        d.findViewById(R.id.tv_argee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });
        titleTx.setText(title);
        contentTx.setText(msg);
        d.show();

    }

    public static Dialog bottomList(final Context context, String[] items, final AdapterView.OnItemClickListener ocl) {


        final Dialog d = new Dialog(context, R.style.my_dialog);
        d.setContentView(R.layout.dialog_bottomlist);
        ListView listView = d.findViewById(R.id.list);
        listView.setAdapter(new ArrayAdapter<String>(context, R.layout.item_text_center, R.id.text, items));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                d.dismiss();
                ocl.onItemClick(adapterView, view, i, l);
            }
        });

        d.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });


        Window win = d.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
        win.setWindowAnimations(R.style.popUpwindow_anim);
        d.show();

        return d;
    }


}
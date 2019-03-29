package com.huacheng.huiservers.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.protocol.ShopProtocol;
import com.huacheng.huiservers.utils.XToast;

/**
 * 类：
 * 时间：2018/3/20 17:22
 * 功能描述:Huiservers
 */
public class InviteAddPersonDialog extends Dialog {
    OnCustomDialogListener listener;
    Context mContext;
    EditText edt_name;
    ShopProtocol mShopProtocol = new ShopProtocol();
    private String strinfo;
    private String bind_type;

    /**
     * 自定义Dialog监听器
     */
    public interface OnCustomDialogListener {
        public void back(String name,String s);
    }

    public InviteAddPersonDialog(Context context, String bind_type, OnCustomDialogListener listener) {
        super(context);
        this.mContext = context;
        this.listener = listener;
        this.bind_type = bind_type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.invite_add_people_dialog);

        LinearLayout lin_btn = (LinearLayout) findViewById(R.id.lin_btn);
        edt_name = (EditText) findViewById(R.id.edt_name);
        System.out.println("^^^^^^^^^^^^" + bind_type);
        lin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_name.getText().toString().equals("")) {
                    XToast.makeText(mContext, "请输入访客姓名", XToast.LENGTH_SHORT).show();
                } else {
                    if (bind_type.equals("1")) {
                        listener.back("1",edt_name.getText().toString().trim());
                    } else if (bind_type.equals("2")) {
                        listener.back("2",edt_name.getText().toString().trim());
                    }
                    InviteAddPersonDialog.this.dismiss();
                }
            }
        });

    }


}

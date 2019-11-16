package com.huacheng.huiservers.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.shop.adapter.ConfirmOrderDialogAdapter;
import com.huacheng.huiservers.ui.shop.bean.ConfirmBean;
import com.huacheng.huiservers.view.MyListView;
import com.huacheng.libraryservice.utils.DeviceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：确认订单配送方式
 * 时间：2019/11/14 11:02
 * created by DFF
 */
public class ConfirmOrderDialog extends Dialog {
    Context mContext;
    private MyListView listView;
    private LinearLayout lin_btn;
    private  ConfirmOrderDialogAdapter adapter;
    List<ConfirmBean.DeliversBean> mdata = new ArrayList();//配送方式数组
    ConfirmBean.DeliversBean selected_bean ;//选中的bean
    private int listview_position = 0;
    OnClickDiliverItemListener listener;

    public ConfirmOrderDialog(@NonNull Context context,int listview_position, List<ConfirmBean.DeliversBean> mdata,ConfirmBean.DeliversBean selected_bean,OnClickDiliverItemListener listener) {
        super(context, R.style.Dialog_down);
        this.mContext = context;
        this.mdata = mdata;
        this.selected_bean = selected_bean;
        this.listview_position=listview_position;
        this.listener=listener;

    }

    public ConfirmOrderDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ConfirmOrderDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置对话框在窗口中显示的位置,因为对话框默认是显示在中心的
        // 得到对话框所显示在的窗口对象

        setContentView(R.layout.dialog_confirm_order);
        Window window = this.getWindow();
        // 得到窗口的布局参数对象，可以使用它来设置对话框在窗口中的布局参数
        WindowManager.LayoutParams params = window.getAttributes();

        // 设置对话框显示的位置
        params.gravity = Gravity.BOTTOM;

        params.width = params.MATCH_PARENT;
        params.height = DeviceUtils.dip2px(mContext,250);

        window.setAttributes(params);
        listView = findViewById(R.id.listview);
        lin_btn = findViewById(R.id.lin_btn);

        adapter = new ConfirmOrderDialogAdapter(mContext, R.layout.item_confirm_order, mdata,selected_bean);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mdata.get(position).getSign().equals(selected_bean.getSign())) {//还是选择了当前配送方式
                    dismiss();
                }else {
                    //选择了新的配送方式
                    if (listener!=null){
                        listener.onClickDiliverItem(listview_position,position);
                    }
                    dismiss();
                }

            }
        });
    }

    public interface OnClickDiliverItemListener{
        /**
         *
         * @param listview_position 选择的商户的position
         * @param diliver_position 选择的配送方式的position
         */
        void onClickDiliverItem(int listview_position,int diliver_position);
    }

    @Override
    public void show() {
        Window window = getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        setCanceledOnTouchOutside(true);
        super.show();
    }
}

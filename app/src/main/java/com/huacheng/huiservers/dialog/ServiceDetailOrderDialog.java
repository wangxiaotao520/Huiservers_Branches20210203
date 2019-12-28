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
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.servicenew.model.ModelServiceDetail;
import com.huacheng.huiservers.ui.servicenew.ui.adapter.ServiceDetailDialogAdapter;
import com.huacheng.huiservers.view.MyListView;
import com.huacheng.libraryservice.utils.DeviceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：服务详情分类
 * 时间：2019/12/27 19:02
 * created by DFF
 */
public class ServiceDetailOrderDialog extends Dialog {
    Context mContext;
    private MyListView listView;
    private LinearLayout lin_btn;
    private TextView tv_name;
    private ServiceDetailDialogAdapter adapter;
    List<ModelServiceDetail.TagListBean> mdata = new ArrayList();//配送方式数组
    ModelServiceDetail.TagListBean selected_bean ;//选中的bean
    private int listview_position = 0;
    OnClickCatItemListener listener;

    public ServiceDetailOrderDialog(@NonNull Context context, List<ModelServiceDetail.TagListBean> mdata, ModelServiceDetail.TagListBean select_cat, OnClickCatItemListener listener) {
        super(context, R.style.Dialog_down);
        this.mContext = context;
        this.mdata = mdata;
        this.selected_bean = select_cat;
        //this.listview_position=listview_position;
        this.listener=listener;

    }

    public ServiceDetailOrderDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ServiceDetailOrderDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
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
        tv_name = findViewById(R.id.tv_name);
        tv_name.setText("选择规格");
        listView = findViewById(R.id.listview);
        lin_btn = findViewById(R.id.lin_btn);

        adapter = new ServiceDetailDialogAdapter(mContext, R.layout.item_confirm_order, mdata,selected_bean);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mdata.get(position).getId().equals(selected_bean.getId())) {//还是选择了当前配送方式
                    dismiss();
                }else {
                    //选择了新的规格
                    if (listener!=null){
                        listener.onClickCatItem(position);
                    }
                    dismiss();
                }

            }
        });
    }

    public interface OnClickCatItemListener{
        /**
         *
         * @param
         * @param position 选择的规格的position
         */
        void onClickCatItem(int position);
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

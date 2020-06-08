package com.huacheng.huiservers.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelCouponNew;
import com.huacheng.huiservers.ui.center.coupon.CouponListAdapter;

import java.util.List;

/**
 * Description:
 * created by wangxiaotao
 * 2020/6/5 0005 17:31
 */
public class ChooseCouponDialog  extends Dialog {


    private ViewPager view_pager;
    private Context mContext;
    private List<ModelCouponNew> mDatas ;
    private CouponListAdapter adapter;
    private CouponListAdapter.OnClickRightBtnListener mListener;
    private ListView listview;
    private View back;
    private TextView txt_btn;
    private LinearLayout ll_no_data;
    private int type = 4; //4是商品详情弹窗 5是下单弹窗

    public ChooseCouponDialog(@NonNull Context context, List <ModelCouponNew>list, int type,CouponListAdapter.OnClickRightBtnListener listener) {
        super(context, R.style.my_dialog_DimEnabled);
        mContext=context;
        this.mDatas=list;
        mListener=listener;
        this.type=type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_choose_coupon);
        init();

        Window window = getWindow();
        // 设置显示动画
        //     window.setWindowAnimations(R.style.main_menu_animstyle);
        //此处可以设置dialog显示的位置
        WindowManager.LayoutParams params = window.getAttributes();
        // 设置对话框显示的位置
        params.gravity = Gravity.BOTTOM;

        params.width = params.MATCH_PARENT;
        //  params.width = params.WRAP_CONTENT;
        params.height = params.WRAP_CONTENT;
        window.setAttributes(params);
    }

    private void init() {
        back = findViewById(R.id.back);
        listview = findViewById(R.id.listview);
        adapter= new CouponListAdapter(mContext,R.layout.item_coupon_list_new,mDatas,type,mListener);
        listview.setAdapter(adapter);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        txt_btn = findViewById(R.id.txt_btn);
        txt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ll_no_data = findViewById(R.id.ll_no_data);
        if (mDatas.size()>0){
            ll_no_data.setVisibility(View.GONE);
            listview.setVisibility(View.VISIBLE);
        }else {
            ll_no_data.setVisibility(View.VISIBLE);
            listview.setVisibility(View.GONE);
        }
        if (type==5){
            txt_btn.setVisibility(View.GONE);
        }

    }

    /**
     * 将条目变为已领取
     * 领取成功后调用 对话框已经show
     */
    public void notifyOneItem(int position) {
        //
        mDatas.get(position).setStatus("2");
        adapter.notifyDataSetChanged();
    }

    @Override
    public void show() {
        super.show();
        this.getWindow().setWindowAnimations(R.style.main_menu_animstyle);
    }
}
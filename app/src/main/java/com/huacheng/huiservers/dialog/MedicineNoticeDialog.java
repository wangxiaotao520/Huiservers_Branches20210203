package com.huacheng.huiservers.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 用药提醒对话框
 * created by wangxiaotao
 * 2019/8/20 0020 下午 5:11
 */
public class MedicineNoticeDialog extends Dialog {
    private Context mContext;
    private ImageView iv_delete;
    private ListView listview;
    private CommonAdapter<String> adapter ;
    private List<String> mDatas;
    private TextView tv_medicine_time;
    private TextView tv_add_person;
    private TextView tv_beizhu;
    private View headerView;
    private View footerView;

    public MedicineNoticeDialog(@NonNull Context context) {
        super(context, R.style.my_dialog_DimEnabled);
        mContext=context;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog_medicine_notice);
        init();
        Window window = getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        //此处可以设置dialog显示的位置
        WindowManager.LayoutParams params = window.getAttributes();
        // 设置对话框显示的位置
        params.gravity = Gravity.BOTTOM;

        params.width = params.MATCH_PARENT;
        //  params.width = params.WRAP_CONTENT;
        params.height = params.WRAP_CONTENT;
        window.setAttributes(params);
    }

    /**
     *初始化数据
     */
    private void init() {
        iv_delete = findViewById(R.id.iv_delete);
        listview = findViewById(R.id.listview);
        mDatas= new ArrayList<>();

        headerView = LayoutInflater.from(mContext).inflate(R.layout.item_dialog_medicine_header, null, false);
        tv_medicine_time = headerView.findViewById(R.id.tv_medicine_time);
        listview.addHeaderView(headerView);
        headerView.setVisibility(View.INVISIBLE);
        footerView = LayoutInflater.from(mContext).inflate(R.layout.item_dialog_medicine_footer, null, false);
        tv_add_person = footerView.findViewById(R.id.tv_add_person);
        tv_beizhu = footerView.findViewById(R.id.tv_beizhu);
        listview.addFooterView(footerView);
        footerView.setVisibility(View.INVISIBLE);
        adapter= new CommonAdapter<String>(mContext,R.layout.item_dialog_medicine,mDatas) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {

            }
        };
        listview.setAdapter(adapter);

        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void show() {
        super.show();
        //TODO 测试
        headerView.setVisibility(View.VISIBLE);
        footerView.setVisibility(View.VISIBLE);
        for (int i = 0; i < 3; i++) {
            mDatas.add("");
        }
        if (adapter!=null)
        adapter.notifyDataSetChanged();
    }
}

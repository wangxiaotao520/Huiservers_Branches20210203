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
import com.huacheng.huiservers.model.ModelOldDrugList;
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
    private CommonAdapter<ModelOldDrugList.DrugListBean> adapter ;
    private List<ModelOldDrugList.DrugListBean> mDatas;
    private TextView tv_medicine_time;
    private TextView tv_add_person;
    private TextView tv_beizhu;
    private View headerView;
    private View footerView;
    ModelOldDrugList modelOldDrugList;

    public MedicineNoticeDialog(@NonNull Context context, ModelOldDrugList modelOldDrugList) {
        super(context, R.style.my_dialog_DimEnabled);
        mContext=context;
        this.modelOldDrugList=modelOldDrugList;

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
        adapter= new CommonAdapter<ModelOldDrugList.DrugListBean>(mContext,R.layout.item_dialog_medicine,mDatas) {
            @Override
            protected void convert(ViewHolder viewHolder, ModelOldDrugList.DrugListBean item, int position) {
                viewHolder.<TextView>getView(R.id.tv_medicine_name).setText(item.getD_name()+"");
                viewHolder.<TextView>getView(R.id.tv_medicine_num).setText(item.getDose()+"");
                viewHolder.<TextView>getView(R.id.tv_medicine_content).setText(item.getTips()+"");
            }
        };
        listview.setAdapter(adapter);

        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        if (modelOldDrugList!=null){
            tv_medicine_time.setText(modelOldDrugList.getEatime()+"");
            tv_add_person.setText(modelOldDrugList.getUser_name()+"");
            tv_beizhu.setText(modelOldDrugList.getNote()+"");
            if (modelOldDrugList.getDrug_list()!=null&&modelOldDrugList.getDrug_list().size()>0){
                mDatas.clear();
                mDatas.addAll(modelOldDrugList.getDrug_list());
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void show() {
        super.show();
        headerView.setVisibility(View.VISIBLE);
        footerView.setVisibility(View.VISIBLE);

        if (adapter!=null){
            adapter.notifyDataSetChanged();
        }

    }
}

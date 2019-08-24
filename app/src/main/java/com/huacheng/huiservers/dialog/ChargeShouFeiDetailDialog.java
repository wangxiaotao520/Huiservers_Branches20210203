package com.huacheng.huiservers.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelChargeDetail;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 类描述：充电桩收费详情
 * 时间：2019/8/19 19:51
 * created by DFF
 */
public class ChargeShouFeiDetailDialog extends Dialog implements View.OnClickListener {
    private TagFlowLayout id_flowlayout;
    private Context mContext;
    private ImageView iv_delete;
    private TextView tv_address, tv_bianhao, tv_biaozhun, tv_content;
    List<String> list_string;
    Set<Integer> list_selected_position;
    OnClickEnsureListener listener;
    private ModelChargeDetail modelChargeDetail;

    public ChargeShouFeiDetailDialog(Context context,ModelChargeDetail modelChargeDetail) {
        super(context, R.style.Dialog_down);
        this.mContext = context;
        // this.listener=listener;
        // this.mSelectListener = mSelectListener;
        this.modelChargeDetail=modelChargeDetail;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_charge_shoufei_detail);

      /*  tv_cancel = findViewById(R.id.tv_cancel);
        tv_sure = findViewById(R.id.tv_sure);
        tv_cancel.setOnClickListener(this);
        tv_sure.setOnClickListener(this);*/
        tv_address = findViewById(R.id.tv_address);
        tv_bianhao = findViewById(R.id.tv_bianhao);
        tv_biaozhun = findViewById(R.id.tv_biaozhun);
        tv_content = findViewById(R.id.tv_content);
        iv_delete = findViewById(R.id.iv_delete);
        iv_delete.setOnClickListener(this);
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

        list_selected_position = new HashSet<>();

        tv_address.setText(modelChargeDetail.getMc()+"");
        tv_bianhao.setText("设备编号："+modelChargeDetail.getGtel());
        tv_biaozhun.setText(""+modelChargeDetail.getPrice());
    }

    private void getTagFlowLayout(final List<String> list) {
        final LayoutInflater mInflater = LayoutInflater.from(mContext);
        id_flowlayout = findViewById(R.id.id_flowlayout);
        TagAdapter adapter = new TagAdapter<String>(list) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv_tag,
                        id_flowlayout, false);
                tv.setText(list.get(position));
                return tv;
            }
        };
        adapter.setSelectedList(list_selected_position);
        id_flowlayout.setAdapter(adapter);

        id_flowlayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                //    ToastUtils.showShort(mContext, "choose:" + selectPosSet.toString());
                list_selected_position.clear();
                for (Integer integer : selectPosSet) {
                    list_selected_position.add(integer);
                }
            }
        });

    }

    //设置数据
  /*  public void  setData(List<ModelEditGoodsDetail.ServiceTagBean> all_serviceTaglist,List<ModelEditGoodsDetail.ServiceTagBean> serviceTaglist_selected){
        if (all_serviceTaglist!=null){
            for (int i = 0; i < all_serviceTaglist.size(); i++) {
                list_string.add(all_serviceTaglist.get(i).getC_name()+"");
            }
        }
        if (serviceTaglist_selected!=null){
            for (int i = 0; i < serviceTaglist_selected.size(); i++) {
                ModelEditGoodsDetail.ServiceTagBean serviceTagBean_selected = serviceTaglist_selected.get(i);
                if (all_serviceTaglist.contains(serviceTagBean_selected)){
                    list_selected_position.add(all_serviceTaglist.indexOf(serviceTagBean_selected));
                }
            }
        }
        getTagFlowLayout(list_string);


    }*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_delete:
                this.dismiss();

                break;
            case R.id.tv_sure:
                if (listener != null) {
                    listener.onEnSure(list_selected_position);
                }
                this.dismiss();

                break;
        }

    }

    public interface OnClickEnsureListener {
        void onEnSure(Set<Integer> list_selected_position);
    }
}

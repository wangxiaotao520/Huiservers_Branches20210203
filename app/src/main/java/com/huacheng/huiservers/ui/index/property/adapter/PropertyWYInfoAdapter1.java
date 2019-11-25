package com.huacheng.huiservers.ui.index.property.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.index.property.bean.ModelWuye;
import com.huacheng.huiservers.ui.index.property.inter.OnCheckJFListener1;
import com.huacheng.huiservers.utils.StringUtils;
import com.stx.xhb.xbanner.OnDoubleClickListener;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Description: 物业账单Adapter 新版
 * created by wangxiaotao
 * 2019/11/25 0025 上午 9:56
 */
public class PropertyWYInfoAdapter1 extends CommonAdapter<ModelWuye>{

    private OnCheckJFListener1 listener;//点击选择item的回调


    private String selected_invoice_type = "";//选中的账单类型 如果该参数为0，能多选账单，且只能选该参数为0的账单，如果该参数为1，只能单选，不可选其他任何账单)
    private String selected_bill_id = ""; //选中的账单id 且只有在 selected_invoice_type=“1”时有值 只能选择它
    public PropertyWYInfoAdapter1(Context context, int layoutId, List<ModelWuye> datas,OnCheckJFListener1 listener) {
        super(context, layoutId, datas);
        this.listener=listener;
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelWuye item, final int position) {
        if (item.getPosition()==0){
            viewHolder.<TextView>getView(R.id.tv_tag_name).setText(item.getCharge_type());
            viewHolder.<TextView>getView(R.id.tv_tag_name).setVisibility(View.VISIBLE);
        }else {
            viewHolder.<TextView>getView(R.id.tv_tag_name).setVisibility(View.GONE);
        }
        if (!item.getStartdate().equals("0") && !TextUtils.isEmpty(item.getStartdate())) {
            viewHolder.<TextView>getView(R.id.tv_timeInterval).setText(StringUtils.getDateToString(item.getStartdate(), "2") + "/" +
                    StringUtils.getDateToString(item.getEnddate(), "2"));
        } else {
            viewHolder.<TextView>getView(R.id.tv_timeInterval).setText(StringUtils.getDateToString(item.getBill_time(), "2"));
        }
        viewHolder.<TextView>getView(R.id.tv_timePrice).setText(item.getSumvalue() + "元");
        viewHolder.<ImageView>getView(R.id.iv_check).setVisibility(View.VISIBLE);
        if (item.isChecked()){
            viewHolder.<ImageView>getView(R.id.iv_check).setBackgroundResource(R.drawable.icon_shop_onclick);
        }else {
            viewHolder.<ImageView>getView(R.id.iv_check).setBackgroundResource(R.drawable.icon_shop_no);
        }

        viewHolder.<RelativeLayout>getView(R.id.rl_click).setOnClickListener(new OnDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (listener!=null){
                    listener.onClickChildItem(position);
                }
            }
        });
        if ("0".equals(selected_invoice_type)) {
            //能多选
            if ("0".equals(item.getIs_invoice())){
                viewHolder.<TextView>getView(R.id.tv_timeInterval).setTextColor(Color.parseColor("#555555"));
                viewHolder.<TextView>getView(R.id.tv_timePrice).setTextColor(Color.parseColor("#555555"));
            }else {
                //是1的不能选
                viewHolder.<TextView>getView(R.id.tv_timeInterval).setTextColor(mContext.getResources().getColor(R.color.gray_D2));
                viewHolder.<TextView>getView(R.id.tv_timePrice).setTextColor(mContext.getResources().getColor(R.color.gray_D2));
            }
        }else if ("1".equals(selected_invoice_type)){
            if ("0".equals(item.getIs_invoice())){
                viewHolder.<TextView>getView(R.id.tv_timeInterval).setTextColor(mContext.getResources().getColor(R.color.gray_D2));
                viewHolder.<TextView>getView(R.id.tv_timePrice).setTextColor(mContext.getResources().getColor(R.color.gray_D2));
            }else {
                //是1的id相同才能选
                if (item.getBill_id().equals(selected_bill_id)){
                    viewHolder.<TextView>getView(R.id.tv_timeInterval).setTextColor(Color.parseColor("#555555"));
                    viewHolder.<TextView>getView(R.id.tv_timePrice).setTextColor(Color.parseColor("#555555"));
                }else {
                    viewHolder.<TextView>getView(R.id.tv_timeInterval).setTextColor(mContext.getResources().getColor(R.color.gray_D2));
                    viewHolder.<TextView>getView(R.id.tv_timePrice).setTextColor(mContext.getResources().getColor(R.color.gray_D2));
                }
            }
        }else {
            //没选择的情况
            viewHolder.<TextView>getView(R.id.tv_timeInterval).setTextColor(Color.parseColor("#555555"));
            viewHolder.<TextView>getView(R.id.tv_timePrice).setTextColor(Color.parseColor("#555555"));
        }
    }

    public String getSelected_invoice_type() {
        return selected_invoice_type;
    }

    public void setSelected_invoice_type(String selected_invoice_type) {
        this.selected_invoice_type = selected_invoice_type;
    }

    public String getSelected_bill_id() {
        return selected_bill_id;
    }

    public void setSelected_bill_id(String selected_bill_id) {
        this.selected_bill_id = selected_bill_id;
    }

}

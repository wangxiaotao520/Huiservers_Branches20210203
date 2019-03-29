package com.huacheng.huiservers.ui.index.workorder.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.model.ModelWorkDetail;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.ToastUtils;
import com.huacheng.libraryservice.utils.glide.GlideUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：详情派单人员
 * 时间：2018/12/10 15:50
 * created by DFF
 */
public class WorkDetailImgAdapter extends CommonAdapter<ModelWorkDetail.Send_Distribute_UserBean> {
    public WorkDetailImgAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void convert(ViewHolder viewHolder, final ModelWorkDetail.Send_Distribute_UserBean item, int position) {
        GlideUtils.getInstance().glideLoad(mContext, ApiHttpClient.IMG_URL + item.getHead_img(),
                viewHolder.<ImageView>getView(R.id.iv_img), R.drawable.ic_default_head);
        if ("1".equals(item.getUser_status())){//未确认
            viewHolder.<TextView>getView(R.id.tv_status).setText("未确认");
            viewHolder.<TextView>getView(R.id.tv_status).setBackgroundResource(R.drawable.bg_round_red);
        }else {
            viewHolder.<TextView>getView(R.id.tv_status).setText("已确认");
            viewHolder.<TextView>getView(R.id.tv_status).setBackgroundResource(R.drawable.bg_round_green5);
        }
        viewHolder.<TextView>getView(R.id.tv_name).setText(item.getName());

        viewHolder.<ImageView>getView(R.id.iv_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CommomDialog(mContext, R.style.my_dialog_DimEnabled, "确定拨打电话给"+item.getName()+"？", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            if (!NullUtil.isStringEmpty(item.getPhone())) {
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:"
                                        + item.getPhone()));
                                mContext. startActivity(intent);
                            }else {
                                ToastUtils.showShort(mContext,"该员工未录入手机号");
                            }
                            dialog.dismiss();
                        }
                    }
                }).show();
            }
        });
    }
}

package com.huacheng.huiservers.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelCommonCat;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * Description: 添加公共报修选项标签
 * created by wangxiaotao
 * 2018/12/10 0010 上午 11:45
 */
public class AddPublicRepairTagDialog extends Dialog implements View.OnClickListener {
    OnClickTagListener onClickTagListener;
    protected TagFlowLayout id_flowlayout;
    protected Context mContext;
    protected TextView tv_cancel, tv_sure;
    List<ModelCommonCat> list_string;
    protected RelativeLayout rl_title;
    public AddPublicRepairTagDialog(List<ModelCommonCat> list_string, Context context, OnClickTagListener listener) {
        super(context, R.style.my_dialog_DimEnabled);
        this.onClickTagListener=listener;
        this.mContext = context;
        this.list_string = list_string;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_tag);
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

        getTagFlowLayout(list_string);

    }

    protected void init() {
        rl_title = findViewById(R.id.rl_title);
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_sure = findViewById(R.id.tv_sure);
        tv_cancel.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
        rl_title.setVisibility(View.GONE);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                this.dismiss();

                break;
            case R.id.tv_sure:


                break;
        }

    }
    protected void getTagFlowLayout(final List<ModelCommonCat> list) {
        final LayoutInflater mInflater = LayoutInflater.from(mContext);
        id_flowlayout = findViewById(R.id.id_flowlayout);
        TagAdapter adapter = new TagAdapter<ModelCommonCat>(list) {

            @Override
            public View getView(FlowLayout parent, int position, ModelCommonCat s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv_tag,
                        id_flowlayout, false);
                if (list_string.get(position).isIs_selected()) {
                    tv.setSelected(true);
                    tv.setBackgroundResource(R.drawable.tag_background_selectedt);
                    tv.setTextColor(Color.parseColor("#ffffff"));
                } else {
                    tv.setSelected(false);
                    tv.setBackgroundResource(R.drawable.tag_background_unselectedt);
                    tv.setTextColor(mContext.getResources().getColor(R.color.text_color));
                }
                // 到时候要改根据字段名
                tv.setText(list.get(position).getLabel_name());
                return tv;
            }
        };

        id_flowlayout.setAdapter(adapter);


        id_flowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {

                if (onClickTagListener!=null){
                    onClickTagListener.onEnSure(position);
                }
                dismiss();
                return false;
            }
        });
    }
    public interface OnClickTagListener {
        void onEnSure(int position);
    }
}
package com.huacheng.huiservers.ui.index.coronavirus;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelPermit;
import com.huacheng.huiservers.utils.StringUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：电子通行证适配器
 * 时间：2020/2/22 16:13
 * created by DFF
 */
public class PermitListAdapter extends CommonAdapter<ModelPermit> {


    public PermitListAdapter(Context context, int layoutId, List<ModelPermit> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelPermit item, int position) {
        if ("1".equals(item.getType())) {

            addTagToTextView(viewHolder.<TextView>getView(R.id.tv_title), item.getTitle(), "临时");
        } else if ("2".equals(item.getType())) {

            addTagToTextView(viewHolder.<TextView>getView(R.id.tv_title), item.getTitle(), "长期");
        } else {

            addTagToTextView(viewHolder.<TextView>getView(R.id.tv_title), item.getTitle(), "访客");
        }

        viewHolder.<TextView>getView(R.id.tv_address).setText(item.getRoom_info());
        viewHolder.<TextView>getView(R.id.tv_name).setText(item.getOwner_name());
    }

    private void addTagToTextView(TextView target, String title, String tag) {
        if (TextUtils.isEmpty(title)) {
            title = "";
        }

        String content = title + tag;


        /**
         * 创建TextView对象，设置drawable背景，设置字体样式，设置间距，设置文本等
         * 这里我们为了给TextView设置margin，给其添加了一个父容器LinearLayout。不过他俩都只是new出来的，不会添加进任何布局
         */
        LinearLayout layout = new LinearLayout(mContext);
        TextView textView = new TextView(mContext);
        textView.setText(tag);
        textView.setBackground(mContext.getResources().getDrawable(R.drawable.allshape_zorange_k_25));
        textView.setTextSize(12);
        textView.setTextColor(Color.parseColor("#ED8D37"));
        textView.setIncludeFontPadding(false);
        textView.setPadding(StringUtils.dip2px(mContext, 6), 0,
                StringUtils.dip2px(mContext, 6), 0);
        textView.setHeight(StringUtils.dip2px(mContext, 17));
        textView.setGravity(Gravity.CENTER_VERTICAL);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置左间距
        layoutParams.leftMargin = StringUtils.dip2px(mContext, 6);
        // 设置下间距，简单解决ImageSpan和文本竖直方向对齐的问题
        layoutParams.bottomMargin = StringUtils.dip2px(mContext, 3);
        layout.addView(textView, layoutParams);

        /**
         * 第二步，测量，绘制layout，生成对应的bitmap对象
         */
        layout.setDrawingCacheEnabled(true);
        layout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        // 给上方设置的margin留出空间
        layout.layout(0, 0, textView.getMeasuredWidth() + StringUtils.dip2px(mContext, (6 + 3)), textView.getMeasuredHeight());
        // 获取bitmap对象
        Bitmap bitmap = Bitmap.createBitmap(layout.getDrawingCache());
        //千万别忘最后一步
        layout.destroyDrawingCache();

        /**
         * 第三步，通过bitmap生成我们需要的ImageSpan对象
         */
        ImageSpan imageSpan = new ImageSpan(mContext, bitmap);


        /**
         * 第四步将ImageSpan对象设置到SpannableStringBuilder的对应位置
         */
        SpannableStringBuilder ssb = new SpannableStringBuilder(content);
        //将尾部tag字符用ImageSpan替换
        ssb.setSpan(imageSpan, title.length(), content.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        target.setText(ssb);
    }
}

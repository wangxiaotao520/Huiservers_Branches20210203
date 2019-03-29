package com.huacheng.huiservers.view.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.huacheng.huiservers.R;
import com.stx.xhb.xbanner.XBanner;

import java.util.List;

/**
 * Description:自定义Xbanner
 * created by wangxiaotao
 * 2018/11/23 0023 上午 11:18
 */
public class MyXBanner extends XBanner{
    public MyXBanner(Context context) {
        super(context);
    }

    public MyXBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyXBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置数据模型和文案，布局资源默认为ImageView
     *
     * @param models 每一页的数据模型集合
     * @param tips   每一页的提示文案集合
     */
    @Override
    public void setData(List<? extends Object> models, List<String> tips) {
        setData(R.layout.layout_xbanner_item, models, tips);
    }

}

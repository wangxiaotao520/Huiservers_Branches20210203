package com.huacheng.huiservers.ui.index.oldservice.adapter;

import android.content.Context;
import android.util.AttributeSet;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.view.NineGridLayout;
import com.huacheng.huiservers.view.RatioImageView;
import com.huacheng.libraryservice.utils.glide.GlideUtils;

import java.util.List;

/**
 * Description: 九宫格
 * created by wangxiaotao
 * 2019/8/15 0015 下午 6:39
 */
public class MyNineGridLayout extends NineGridLayout {

    private  OnClickNineGridImageListener listener;
    public MyNineGridLayout(Context context) {
        super(context);
    }

    public MyNineGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean displayOneImage(RatioImageView imageView, String url, int parentWidth) {
        return false;
    }

    @Override
    protected void displayImage(RatioImageView imageView, String url) {
        GlideUtils.getInstance().glideLoad(mContext,url,imageView, R.drawable.ic_default_rectange);
    }

    @Override
    protected void onClickImage(int position, String url, List<String> urlList) {
        if (listener!=null){
            listener.onClickImage(position,url,urlList);
        }
    }

    interface OnClickNineGridImageListener{
        void onClickImage (int position, String url, List<String> urlList);
    }

    public void setListener(OnClickNineGridImageListener listener) {
        this.listener = listener;
    }

}

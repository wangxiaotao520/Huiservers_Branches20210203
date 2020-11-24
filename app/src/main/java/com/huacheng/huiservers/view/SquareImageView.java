package com.huacheng.huiservers.view;

import android.content.Context;
import android.util.AttributeSet;

public final class SquareImageView extends android.support.v7.widget.AppCompatImageView {

    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int spec = MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY ? widthMeasureSpec : heightMeasureSpec;

        super.onMeasure(spec, spec);
    }
}
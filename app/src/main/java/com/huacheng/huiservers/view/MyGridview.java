package com.huacheng.huiservers.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

public class MyGridview extends GridView {

    private float mTouchX;
    private float mTouchY;

    OnTouchInvalidPositionListener mTouchInvalidPosListener;

    public MyGridview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridview(Context context) {
        super(context);
    }

    public MyGridview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    public interface OnTouchInvalidPositionListener {
        /**
         * motionEvent 可使用 MotionEvent.ACTION_DOWN 或者 MotionEvent.ACTION_UP等来按需要进行判断
         *
         * @return 是否要终止事件的路由
         */
        boolean onTouchInvalidPosition(int motionEvent);

    }

    /**
     * 点击空白区域时的响应和处理接口
     */
    public void setOnTouchInvalidPositionListener(OnTouchInvalidPositionListener listener) {
        mTouchInvalidPosListener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mTouchInvalidPosListener == null) {
            return super.onTouchEvent(event);
        }
        if (!isEnabled()) {
            // A disabled view that is clickable still consumes the touch
            // events, it just doesn't respond to them.
            return isClickable() || isLongClickable();
        }

        if (event.getActionMasked() == MotionEvent.ACTION_UP) {
            final int motionPosition = pointToPosition((int) event.getX(), (int) event.getY());
            if (motionPosition == INVALID_POSITION) {
                super.onTouchEvent(event);
                return mTouchInvalidPosListener.onTouchInvalidPosition(event.getActionMasked());
            }
        }

        return super.onTouchEvent(event);
    }
}

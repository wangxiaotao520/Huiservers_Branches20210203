package com.huacheng.huiservers.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.index.vote.adapter.ViewPagerGiftAdapter;

import java.util.List;

/**
 * Description: 投票对话框送礼物对话框
 * created by wangxiaotao
 * 2020/6/2 0002 15:21
 * 暂时先不用了
 *
 *         ArrayList<String> strings = new ArrayList<>();
 *                 for (int i = 0; i <18; i++) {
 *                     strings.add("") ;
 *                 }
 *                 new VoteGiftDialog(this, strings, new ViewPagerGiftAdapter.OnClickGiftItemListener() {
 *                     @Override
 *                     public void onClickItemGift(int viewpager_position, int grid_position) {
 *                         SmartToast.showInfo("[position]"+(viewpager_position*8+grid_position));
 *                     }
 *                 }).show();
 *
 *
 */
public class VoteGiftDialog extends Dialog {


    private ViewPager view_pager;
    private Context mContext;
    private List <String>mDatas ;
    private ViewPagerGiftAdapter viewPagerGiftAdapter;
    private ViewPagerGiftAdapter.OnClickGiftItemListener mListener;

    public VoteGiftDialog(@NonNull Context context, List <String>list, ViewPagerGiftAdapter.OnClickGiftItemListener listener) {
        super(context,R.style.my_dialog_DimEnabled);
        mContext=context;
        this.mDatas=list;
        mListener=listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_vote_gift);
        init();

        Window window = getWindow();
        // 设置显示动画
        //     window.setWindowAnimations(R.style.main_menu_animstyle);
        //此处可以设置dialog显示的位置
        WindowManager.LayoutParams params = window.getAttributes();
        // 设置对话框显示的位置
        params.gravity = Gravity.BOTTOM;

        params.width = params.MATCH_PARENT;
        //  params.width = params.WRAP_CONTENT;
        params.height = params.WRAP_CONTENT;
        window.setAttributes(params);
    }

    private void init() {
        view_pager = findViewById(R.id.view_pager);
        viewPagerGiftAdapter = new ViewPagerGiftAdapter(mContext, mDatas,mListener);
        view_pager.setAdapter(viewPagerGiftAdapter);
    }
    @Override
    public void show() {
        super.show();
        this.getWindow().setWindowAnimations(R.style.main_menu_animstyle);
    }
}

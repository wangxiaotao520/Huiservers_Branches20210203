package com.huacheng.huiservers.ui.index.vote.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.huacheng.huiservers.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 送礼物的Adapter
 * created by wangxiaotao
 * 2020/6/2 0002 15:44
 */
public class ViewPagerGiftAdapter extends PagerAdapter {

    private List<String> mDatas = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private OnClickGiftItemListener listener;


    public ViewPagerGiftAdapter(Context mContext, List<String> datas,OnClickGiftItemListener listener) {
        this.mContext = mContext;
        this.mDatas = datas;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.listener=listener;
    }

    @Override public Object instantiateItem(ViewGroup container, int position) {

        View itemView = mLayoutInflater.inflate(R.layout.item_viewpager_gift, container, false);
        GridView gridview = itemView.findViewById(R.id.gridview);
        List<String> giftList = new ArrayList<>();
        if (mDatas.size() <= 8) {
            giftList.addAll(mDatas);
        } else {
            for(int i=position*8;i<(position+1)*8&&i<mDatas.size();i++) {
                giftList.add(mDatas.get(i));
            }
        }
        CommonAdapter  adapter = new CommonAdapter<String>(mContext, R.layout.item_item_vote_gift, giftList) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {


            }
        };
        gridview.setAdapter(adapter);
        final int viewpager_position= position;
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener!=null) {
                    listener.onClickItemGift(viewpager_position,position);
                }
            }
        });

        container.addView(itemView);

        return itemView;
    }


    @Override public int getCount() {
        //向上取整
        return (int) Math.ceil( mDatas.size()/8f);
    }


    @Override public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition (Object object) { return POSITION_NONE; }

    public interface OnClickGiftItemListener{
        void onClickItemGift(int viewpager_position,int grid_position);
    }

}

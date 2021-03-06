package com.huacheng.huiservers.ui.shop.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.Jump;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.MyCookieStore;
import com.huacheng.huiservers.model.ModelHomeIndex;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.glide.GlideUtils;

import java.util.Arrays;
import java.util.List;

/**
 * GridView加载数据的适配器
 *
 * @author Administrator
 */
public class MyGridViewAdpter extends BaseAdapter {

    private Context context;
    private List<ModelHomeIndex> lists;//数据源
    private int mIndex; // 页数下标，标示第几页，从0开始
    private int mPargerSize;// 每页显示的最大的数量
    private SharePrefrenceUtil prefrenceUtil;

    //private int num=9;
    public MyGridViewAdpter(Context context, List<ModelHomeIndex> lists,
                            int mIndex, int mPargerSize) {
        this.context = context;
        this.lists = lists;
        this.mIndex = mIndex;
        this.mPargerSize = mPargerSize;
        prefrenceUtil=new SharePrefrenceUtil(context);
    }

    /**
     * 先判断数据及的大小是否显示满本页lists.size() > (mIndex + 1)*mPagerSize
     * 如果满足，则此页就显示最大数量lists的个数
     * 如果不够显示每页的最大数量，那么剩下几个就显示几个
     */
    @Override
    public int getCount() {
        return lists.size() > (mIndex + 1) * mPargerSize ?
                mPargerSize : (lists.size() - mIndex * mPargerSize);

    }

    @Override
    public Object getItem(int arg0) {
        return lists.get(arg0 + mIndex * mPargerSize);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0 + mIndex * mPargerSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            /*convertView = View.inflate(context, R.layout.item_view, null);
			holder.tv_name = (TextView)convertView.findViewById(R.id.item_name);
			holder.iv_nul = (ImageView)convertView.findViewById(R.id.item_image);*/
            convertView = View.inflate(context, R.layout.home_my_cate_item, null);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.iv_nul = (ImageView) convertView.findViewById(R.id.iv_img);
            holder.ly_onclick = (LinearLayout) convertView.findViewById(R.id.ly_onclick);
//            holder.txt_shop_price = (TextView)convertView.findViewById(R.id.txt_shop_price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //重新确定position因为拿到的总是数据源，数据源是分页加载到每页的GridView上的
        final int pos = position + mIndex * mPargerSize;//假设mPageSiez
        //假设mPagerSize=8，假如点击的是第二页（即mIndex=1）上的第二个位置item(position=1),那么这个item的实际位置就是pos=9
        holder.tv_name.setText(lists.get(pos).getMenu_name() + "");
//		holder.txt_shop_price.setText("¥"+lists.get(pos).getMin_price() + "");
        GlideUtils.getInstance().glideLoad(context, MyCookieStore.URL + lists.get(pos).getMenu_logo(), holder.iv_nul, R.drawable.ic_default);

        holder.ly_onclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("1".equals(lists.get(pos).getPid())){//若等于1即物业服务方面的导航 则需要判断是否匹配到小区
                    if (!NullUtil.isStringEmpty(prefrenceUtil.getXiaoQuId())){//匹配住了
                        String type = lists.get(pos).getUrl_type();
                        String[] typeStr = new String[]{"14", "15", "16", "17", "18", "19", "20", "21", "26","28","29"};
                        if (Arrays.asList(typeStr).contains(type)) {
                            Jump jump = new Jump(context, lists.get(pos).getUrl_type(), lists.get(pos).getUrl_id(),
                                    lists.get(pos).getUrl_type_cn());
                        } else {
                            Jump jump = new Jump(context, lists.get(pos).getUrl_type(), lists.get(pos).getUrl_id(), "",
                                    lists.get(pos).getUrl_type_cn());
                        }
                    }else {
                        SmartToast.showInfo("该小区暂未开通此服务");
                    }
                }else {
                    String type = lists.get(pos).getUrl_type();
                    String[] typeStr = new String[]{"14", "15", "16", "17", "18", "19", "20", "21", "26","28","29"};
                    if (Arrays.asList(typeStr).contains(type)) {
                        Jump jump = new Jump(context, lists.get(pos).getUrl_type(), lists.get(pos).getUrl_id(),
                                lists.get(pos).getUrl_type_cn());
                    } else {
                        Jump jump = new Jump(context, lists.get(pos).getUrl_type(), lists.get(pos).getUrl_id(), "",
                                lists.get(pos).getUrl_type_cn());
                    }
                }


            }
        });
       /* gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                Object obj = gridView.getItemAtPosition(position);
                if (obj != null && obj instanceof ModelHomeIndex) {
                    System.out.println(obj);
                    String type = ((ModelHomeIndex) obj).getUrl_type();
                    String[] typeStr = new String[]{"14", "15", "16", "17", "18", "19", "20", "21", "26"};
                    if (Arrays.asList(typeStr).contains(type)) {
                        jump = new Jump(getActivity(), ((ModelHomeIndex) obj).getUrl_type(), ((ModelHomeIndex) obj).getUrl_id(),
                                ((ModelHomeIndex) obj).getUrl_type_cn());
                    } else {
                        jump = new Jump(getActivity(), ((ModelHomeIndex) obj).getUrl_type(), ((ModelHomeIndex) obj).getUrl_id(), "",
                                ((ModelHomeIndex) obj).getUrl_type_cn());
                    }
                }
            }
        });*/
        return convertView;
    }

    static class ViewHolder {
        private TextView tv_name;
        private ImageView iv_nul;
        private LinearLayout ly_onclick;
    }
}

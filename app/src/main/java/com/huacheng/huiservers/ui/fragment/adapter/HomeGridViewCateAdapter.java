package com.huacheng.huiservers.ui.fragment.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.huacheng.huiservers.ui.fragment.indexcat.IndexAllServiesActivity;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.glide.GlideUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Description: 新版首页 GridView
 * created by wangxiaotao
 * 2019/10/17 0017 上午 8:20
 */
public class HomeGridViewCateAdapter extends BaseAdapter {

    private Context context;
    private List<ModelHomeIndex> lists;//数据源
    private int type;
    private OnClickItemListener listener;
    private SharePrefrenceUtil prefrenceUtil;

    public HomeGridViewCateAdapter(Context context, List<ModelHomeIndex> lists, int type, OnClickItemListener listener) {
        this.context = context;
        this.lists = lists;
        this.type = type;
        this.listener = listener;
        prefrenceUtil=new SharePrefrenceUtil(context);

    }

    /**
     * 先判断数据及的大小是否显示满本页lists.size() > (mIndex + 1)*mPagerSize
     * 如果满足，则此页就显示最大数量lists的个数
     * 如果不够显示每页的最大数量，那么剩下几个就显示几个
     */
    @Override
    public int getCount() {
        if (type==1){
            if (lists.size()>=8) {
                return 8;
            }else {
                return lists.size();
            }
        }else {
            return lists.size();
        }
    }

    @Override
    public Object getItem(int arg0) {
        return lists.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
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
            holder.iv_cancel = (ImageView) convertView.findViewById(R.id.iv_cancel);
            holder.ly_onclick = (LinearLayout) convertView.findViewById(R.id.ly_onclick);
//            holder.txt_shop_price = (TextView)convertView.findViewById(R.id.txt_shop_price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //type 1 首页分类 2 我的服务 3 更多服务
        if (type == 1) {
            holder.iv_cancel.setVisibility(View.GONE);
            final int pos = position;
            if (position==7){
                holder.tv_name.setText( "查看更多");
                holder.iv_nul.setImageResource(R.mipmap.ic_index_more);
            }else {
                //假设mPagerSize=8，假如点击的是第二页（即mIndex=1）上的第二个位置item(position=1),那么这个item的实际位置就是pos=9
                holder.tv_name.setText(lists.get(pos).getMenu_name() + "");
//		holder.txt_shop_price.setText("¥"+lists.get(pos).getMin_price() + "");
                GlideUtils.getInstance().glideLoad(context, MyCookieStore.URL + lists.get(pos).getMenu_logo(), holder.iv_nul, R.color.default_color);
            }

            holder.ly_onclick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if("1".equals(lists.get(pos).getPid())){//若等于1即物业服务方面的导航 则需要判断是否匹配到小区
                        if (!NullUtil.isStringEmpty(prefrenceUtil.getXiaoQuId())){//匹配住了
                            if (pos ==7){
                                Intent intent = new Intent(context, IndexAllServiesActivity.class);
                                intent.putExtra("myCatelist",(ArrayList<ModelHomeIndex>)lists);
                                context.startActivity(intent);
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
                        }else {
                            SmartToast.showInfo("该小区暂未开通此服务");
                        }
                    }else {
                        if (pos ==7){
                            Intent intent = new Intent(context, IndexAllServiesActivity.class);
                            intent.putExtra("myCatelist",(ArrayList<ModelHomeIndex>)lists);
                            context.startActivity(intent);
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
                }
            });
        } else {
            //这里暂时先处理这种逻辑
//            if (lists.get(pos).isIsselect()) {
//                holder.iv_cancel.setVisibility(View.VISIBLE);
//                if (type == 2) {
//                    holder.tv_name.setText("我的服务" + (pos + 1));
//                    holder.iv_cancel.setBackgroundColor(context.getResources().getColor(R.color.red_ed));
//                } else {
//                    holder.tv_name.setText("更多服务" + (pos + 1));
//                    holder.iv_cancel.setBackgroundColor(context.getResources().getColor(R.color.orange_bg));
//                }
//                holder.iv_cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (listener!=null) {
//                            listener.onClickImg(v,pos,type);
//                        }
//                    }
//                });
//            } else {
//                holder.iv_cancel.setVisibility(View.GONE);
//                if (type == 2) {
//                    holder.tv_name.setText("我的服务" + (pos + 1));
//                } else {
//                    holder.tv_name.setText("更多服务" + (pos + 1));
//                }
//            }
            holder.iv_cancel.setVisibility(View.GONE);
            //重新确定position因为拿到的总是数据源，数据源是分页加载到每页的GridView上的
            final int pos = position;
            //假设mPagerSize=8，假如点击的是第二页（即mIndex=1）上的第二个位置item(position=1),那么这个item的实际位置就是pos=9
            holder.tv_name.setText(lists.get(pos).getMenu_name() + "");
//		holder.txt_shop_price.setText("¥"+lists.get(pos).getMin_price() + "");
            GlideUtils.getInstance().glideLoad(context, MyCookieStore.URL + lists.get(pos).getMenu_logo(), holder.iv_nul, R.color.default_color);

            holder.ly_onclick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    String type = lists.get(pos).getUrl_type();
//                    String[] typeStr = new String[]{"14", "15", "16", "17", "18", "19", "20", "21", "26","28","29"};
//                    if (Arrays.asList(typeStr).contains(type)) {
//                        Jump jump = new Jump(context, lists.get(pos).getUrl_type(), lists.get(pos).getUrl_id(),
//                                lists.get(pos).getUrl_type_cn());
//                    } else {
//                        Jump jump = new Jump(context, lists.get(pos).getUrl_type(), lists.get(pos).getUrl_id(), "",
//                                lists.get(pos).getUrl_type_cn());
//                    }
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

        }
        return convertView;
    }

    static class ViewHolder {
        private TextView tv_name;
        private ImageView iv_nul;
        private LinearLayout ly_onclick;
        private ImageView iv_cancel;
    }

    public interface OnClickItemListener {
        /**
         * 点击图标
         *
         * @param v
         * @param position
         */
        void onClickImg(View v, int position, int type);


    }
}

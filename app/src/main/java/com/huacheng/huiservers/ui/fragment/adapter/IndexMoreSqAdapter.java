package com.huacheng.huiservers.ui.fragment.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.model.ModelAds;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;

import java.util.List;

/**
 * 类描述：更多商圈
 * 时间：2020/9/7 11:44
 * created by DFF
 */
/*public class IndexMoreSqAdapter extends CommonAdapter<ModelAds> {

    public IndexMoreSqAdapter(Context context, int layoutId, List<ModelAds> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelAds item, int position) {
        viewHolder.<TextView>getView(R.id.tv_name).setText(item.getService_name());
        FrescoUtils.getInstance().setImageUri(viewHolder.<SimpleDraweeView>getView(R.id.sdv_shangquan), ApiHttpClient.IMG_URL+item.getImg());
    }
}*/

public class IndexMoreSqAdapter extends BaseAdapter {
    private Context context;
    private List<ModelAds> lists;//数据源

    public IndexMoreSqAdapter(Context context, List<ModelAds> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IndexMoreSqAdapter.ViewHolder holder = null;
        if (convertView == null) {
            holder = new IndexMoreSqAdapter.ViewHolder();
            /*convertView = View.inflate(context, R.layout.item_view, null);
			holder.tv_name = (TextView)convertView.findViewById(R.id.item_name);
			holder.iv_nul = (ImageView)convertView.findViewById(R.id.item_image);*/
            convertView = View.inflate(context, R.layout.activity_index_more_sq_item, null);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.sdv_shangquan = (SimpleDraweeView) convertView.findViewById(R.id.sdv_shangquan);
//            holder.txt_shop_price = (TextView)convertView.findViewById(R.id.txt_shop_price);
            convertView.setTag(holder);
        } else {
            holder = (IndexMoreSqAdapter.ViewHolder) convertView.getTag();
        }
        holder.tv_name.setText(lists.get(position).getBd_name());
        FrescoUtils.getInstance().setImageUri(holder.sdv_shangquan, ApiHttpClient.IMG_URL+lists.get(position).getImg());
        return convertView;
    }

    static class ViewHolder {
        private TextView tv_name;
        private SimpleDraweeView sdv_shangquan;

    }
}




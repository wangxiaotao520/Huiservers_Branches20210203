package com.huacheng.huiservers.ui.fragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.index.HomeArticleWebviewActivity;
import com.huacheng.huiservers.model.ModelIndex;
import com.huacheng.huiservers.http.MyCookieStore;
import com.huacheng.huiservers.utils.StringUtils;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

import static com.huacheng.huiservers.utils.UIUtils.startActivity;

/**
 * 首页协议 列表适配器
 * Created by badge on 2018/3/28.
 */

public class HomeArticleListAdapter extends BaseAdapter {

    List<ModelIndex> mList;
    private Context mContext;

    public HomeArticleListAdapter(List<ModelIndex> list, Context context) {
        this.mList = list;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size() - 2;
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


        ViewHoldler holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.home_article_list_item, null);
            holder = new ViewHoldler(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHoldler) convertView.getTag();
        }
        BitmapUtils bitmap = new BitmapUtils(mContext);
        String article_image = mList.get(position + 2).getArticle_image();
        if (!StringUtils.isEmpty(article_image)) {
            holder.iv_article_item.setVisibility(View.VISIBLE);
            bitmap.display(holder.iv_article_item, MyCookieStore.URL + article_image);
        } else {
            holder.iv_article_item.setVisibility(View.INVISIBLE);
        }

        final String title = mList.get(position + 2).getTitle();
        if (!StringUtils.isEmpty(title)) {
            holder.tv_article_item.setText(title);
        }
        final String content = mList.get(position + 2).getContent();

        holder.lin_article23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, HomeArticleWebviewActivity.class);
                intent.putExtra("articleTitle", title);
                intent.putExtra("articleCnt", content);
                startActivity(intent);
            }
        });

        return convertView;
    }

    class ViewHoldler {
        LinearLayout lin_article23;
        ImageView iv_article_item;
        TextView tv_article_item;

        public ViewHoldler(View v) {
            lin_article23 = (LinearLayout) v.findViewById(R.id.lin_article23);
            iv_article_item = (ImageView) v.findViewById(R.id.iv_article_item);
            tv_article_item = (TextView) v.findViewById(R.id.tv_article_item);
        }
    }
}

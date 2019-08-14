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
 * 首页协议 网格适配器
 * Created by badge on 2018/3/28.
 */

public class HomeArticleGridAdapter extends BaseAdapter {

    List<ModelIndex> mList;
    private Context mContext;

    public HomeArticleGridAdapter(List<ModelIndex> list, Context context) {
        this.mList = list;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if (mList.size() >= 2) {
            return 2;
        } else {
            return mList.size();
        }
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.home_article_gridview_item, null);
            holder = new ViewHoldler(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHoldler) convertView.getTag();
        }
        ModelIndex article = mList.get(position);
        String article_image = article.getArticle_image();
        if (!StringUtils.isEmpty(article_image)) {
            BitmapUtils bitmap = new BitmapUtils(mContext);
            holder.iv_article_img1.setVisibility(View.VISIBLE);
            bitmap.display(holder.iv_article_img1, MyCookieStore.URL + article_image);
        } else {
            holder.iv_article_img1.setVisibility(View.INVISIBLE);
        }

        final String title1 = article.getTitle();
        final String content1 = article.getContent();
        holder.tv_article_title1.setText(title1);

        holder.tv_article_content1.setText(StringUtils.delHTMLTag(content1));

        holder.lin_article1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, HomeArticleWebviewActivity.class);
                intent.putExtra("articleTitle", title1);
                intent.putExtra("articleCnt", content1);
                startActivity(intent);
            }
        });


        return convertView;
    }

    class ViewHoldler {
        LinearLayout lin_article1;
        ImageView iv_article_img1;
        TextView tv_article_title1, tv_article_content1;

        public ViewHoldler(View v) {
            lin_article1 = (LinearLayout) v.findViewById(R.id.lin_article1);
            iv_article_img1 = (ImageView) v.findViewById(R.id.iv_article_img1);
            tv_article_title1 = (TextView) v.findViewById(R.id.tv_article_title1);
            tv_article_content1 = (TextView) v.findViewById(R.id.tv_article_content1);
        }
    }
}

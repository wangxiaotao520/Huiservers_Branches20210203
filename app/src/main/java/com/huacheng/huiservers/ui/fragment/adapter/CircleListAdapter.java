package com.huacheng.huiservers.ui.fragment.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.BaseApplication;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.MyCookieStore;
import com.huacheng.huiservers.model.ModelCircle;
import com.huacheng.huiservers.ui.center.geren.adapter.CircleItemImageAdapter;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.huiservers.view.MyGridview;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：邻里首页adapter
 * 时间：2019/12/18 10:29
 * created by DFF
 */
public class CircleListAdapter extends CommonAdapter<ModelCircle> {
    private onItemDeleteListener mOnItemDeleteListener;
    private int tag;

    //删除接口
    public interface onItemDeleteListener {
        void onDeleteClick(String id);
    }

    public CircleListAdapter(Context context, int layoutId, List<ModelCircle> datas, onItemDeleteListener mOnItemDeleteListener, int tag) {
        super(context, layoutId, datas);
        this.mOnItemDeleteListener = mOnItemDeleteListener;
        this.tag = tag;
    }

    @Override
    protected void convert(ViewHolder viewHolder, final ModelCircle item, final int position) {
        if ("0".equals(item.getAdmin_id())) { //0为用户发布的
            viewHolder.<LinearLayout>getView(R.id.ly_admin).setVisibility(View.GONE);
            viewHolder.<LinearLayout>getView(R.id.ly_user).setVisibility(View.VISIBLE);
            viewHolder.<TextView>getView(R.id.tv_line).setVisibility(View.VISIBLE);

            FrescoUtils.getInstance().setImageUri(viewHolder.<SimpleDraweeView>getView(R.id.sdv_user_head), StringUtils.getImgUrl(item.getAvatars()));
            viewHolder.<TextView>getView(R.id.tv_username).setText(item.getNickname());
            viewHolder.<TextView>getView(R.id.tv_usertime).setText(item.getAddtime());
            if (String.valueOf(BaseApplication.getUser().getUid()).equals(item.getUid())) {
                viewHolder.<ImageView>getView(R.id.iv_del).setVisibility(View.VISIBLE);
                viewHolder.<TextView>getView(R.id.tv_my_reply).setText(item.getReply_num() + "条新评论 >");
                viewHolder.<ImageView>getView(R.id.iv_del).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemDeleteListener != null) {
                            mOnItemDeleteListener.onDeleteClick(item.getId());
                        }
                    }
                });
                if (tag == 0) {//邻里首页
                    viewHolder.<RelativeLayout>getView(R.id.ry_my).setVisibility(View.VISIBLE);
                } else {
                    viewHolder.<RelativeLayout>getView(R.id.ry_my).setVisibility(View.GONE);
                }
            } else {
                viewHolder.<ImageView>getView(R.id.iv_del).setVisibility(View.GONE);

            }
            byte[] bytes1 = Base64.decode(item.getContent(), Base64.DEFAULT);
            viewHolder.<TextView>getView(R.id.tv_content).setText(new String(bytes1));
            if (item.getImg_list() != null && item.getImg_list().size() > 0) {
                viewHolder.<MyGridview>getView(R.id.gridView).setVisibility(View.VISIBLE);
                CircleItemImageAdapter mGridViewAdapter = new CircleItemImageAdapter(mContext, item.getImg_list());
                viewHolder.<MyGridview>getView(R.id.gridView).setAdapter(mGridViewAdapter);
              /*  viewHolder.<MyGridview>getView(R.id.gridView).setOnTouchInvalidPositionListener(new MyGridview.OnTouchInvalidPositionListener() {
                    @Override
                    public boolean onTouchInvalidPosition(int motionEvent) {
                        Intent intent = new Intent();
                        intent.setClass(mContext, CircleDetailsActivity.class);
                        intent.putExtra("id", item.getId());
                        mContext.startActivity(intent);
                        return true;
                    }
                });*/
            } else {
                viewHolder.<MyGridview>getView(R.id.gridView).setVisibility(View.GONE);
            }
            viewHolder.<TextView>getView(R.id.tv_tagname).setText("#" + item.getC_name() + "#");
            viewHolder.<TextView>getView(R.id.tv_read).setText(item.getClick());
            viewHolder.<TextView>getView(R.id.tv_reply).setText(item.getReply_num());

        } else {
            viewHolder.<LinearLayout>getView(R.id.ly_admin).setVisibility(View.VISIBLE);
            viewHolder.<LinearLayout>getView(R.id.ly_user).setVisibility(View.GONE);
            viewHolder.<TextView>getView(R.id.tv_line).setVisibility(View.GONE);

            byte[] bytes1 = Base64.decode(item.getTitle(), Base64.DEFAULT);
            viewHolder.<TextView>getView(R.id.tv_title).setText(new String(bytes1));
            viewHolder.<TextView>getView(R.id.tv_catname).setText(item.getC_name());
            viewHolder.<TextView>getView(R.id.tv_readnum).setText(item.getClick() + "阅读");
            viewHolder.<TextView>getView(R.id.tv_addtime).setText(item.getAddtime());

            if (item.getImg_list() != null && item.getImg_list().size() > 0) {
                if (!TextUtils.isEmpty(item.getImg_list().get(0).getImg())) {
                    viewHolder.<SimpleDraweeView>getView(R.id.sdv_head).setVisibility(View.VISIBLE);
                    FrescoUtils.getInstance().setImageUri(viewHolder.<SimpleDraweeView>getView(R.id.sdv_head), MyCookieStore.URL + item.getImg_list().get(0).getImg());
                } else {
                    viewHolder.<SimpleDraweeView>getView(R.id.sdv_head).setVisibility(View.GONE);
                }

            } else {
                viewHolder.<SimpleDraweeView>getView(R.id.sdv_head).setVisibility(View.GONE);
            }

        }

    }
}

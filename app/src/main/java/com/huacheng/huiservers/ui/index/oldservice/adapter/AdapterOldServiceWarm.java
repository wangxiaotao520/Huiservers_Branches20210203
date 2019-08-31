package com.huacheng.huiservers.ui.index.oldservice.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.model.ModelItemServiceWarm;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.huiservers.view.PhotoViewPagerAcitivity;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.huacheng.libraryservice.utils.glide.GlideUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 亲情关怀Adapter
 * created by wangxiaotao
 * 2019/8/15 0015 下午 5:54
 */
public class AdapterOldServiceWarm extends CommonAdapter<ModelItemServiceWarm>{


    public AdapterOldServiceWarm(Context context, int layoutId, List<ModelItemServiceWarm> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, final ModelItemServiceWarm item, int position) {
    FrescoUtils.getInstance().setImageUri(viewHolder.<SimpleDraweeView>getView(R.id.sdv_head), ApiHttpClient.IMG_URL+item.getHead_img());
     //   FrescoUtils.getInstance().setImageUri(viewHolder.<SimpleDraweeView>getView(R.id.sdv_head), StringUtils.getImgUrl(item.get));
        viewHolder.<TextView>getView(R.id.tv_name).setText(item.getP_name()+"");
        viewHolder.<TextView>getView(R.id.tv_time).setText(StringUtils.getDateToString(item.getAddtime(),"7"));
        viewHolder.<TextView>getView(R.id.tv_content).setText(item.getContent()+"");
        if (item.getImg()!=null&&item.getImg().size()>0){
            if (item.getImg().size()==1){
            viewHolder.<MyNineGridLayout>getView(R.id.nine_grid_layout).setVisibility(View.GONE);
            GlideUtils.getInstance().glideLoad(mContext,ApiHttpClient.IMG_URL+item.getImg().get(0).getImg(), viewHolder.<ImageView>getView(R.id.iv_single_img),R.drawable.ic_default_rectange);
            viewHolder.<ImageView>getView(R.id.iv_single_img).setVisibility(View.VISIBLE);
            viewHolder.<ImageView>getView(R.id.iv_single_img).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // SmartToast.showInfo("single");

                    ArrayList<String> imgs = new ArrayList<>();
                    imgs.add(ApiHttpClient.IMG_URL + item.getImg().get(0).getImg());

                    Intent intent = new Intent(mContext, PhotoViewPagerAcitivity.class);
                    intent.putExtra("img_list",imgs);
                    intent.putExtra("position",0);
                    mContext.startActivity(intent);

                }
            });
        }else {
            viewHolder.<ImageView>getView(R.id.iv_single_img).setVisibility(View.GONE);
            viewHolder.<MyNineGridLayout>getView(R.id.nine_grid_layout).setVisibility(View.VISIBLE);

            List<String> urlList= new ArrayList<>();

            for (int i = 0; i < item.getImg().size(); i++) {
                if (i>8){
                    break;
                }
                urlList.add(ApiHttpClient.IMG_URL+item.getImg().get(i).getImg());
            }
            viewHolder.<MyNineGridLayout>getView(R.id.nine_grid_layout).setUrlList(urlList);
            viewHolder.<MyNineGridLayout>getView(R.id.nine_grid_layout).setListener(new MyNineGridLayout.OnClickNineGridImageListener() {
                @Override
                public void onClickImage(int position, String url, List<String> urlList) {
                   // SmartToast.showInfo(""+position);
                    ArrayList<String> imgs = new ArrayList<>();
                    for (int i = 0; i < item.getImg().size(); i++) {
                        imgs.add(ApiHttpClient.IMG_URL + item.getImg().get(i).getImg());
                    }
                    Intent intent = new Intent(mContext, PhotoViewPagerAcitivity.class);
                    intent.putExtra("img_list",imgs);
                    intent.putExtra("position",position);
                    mContext.startActivity(intent);
                }
            });
        }

        }else {
            viewHolder.<MyNineGridLayout>getView(R.id.nine_grid_layout).setVisibility(View.GONE);
            viewHolder.<ImageView>getView(R.id.iv_single_img).setVisibility(View.GONE);
        }
//        if (item.urlList.size()==0){
//            viewHolder.<MyNineGridLayout>getView(R.id.nine_grid_layout).setVisibility(View.GONE);
//            viewHolder.<ImageView>getView(R.id.iv_single_img).setVisibility(View.GONE);
//        }else if (item.urlList.size()==1){
//            viewHolder.<MyNineGridLayout>getView(R.id.nine_grid_layout).setVisibility(View.GONE);
//            GlideUtils.getInstance().glideLoad(mContext,item.urlList.get(0), viewHolder.<ImageView>getView(R.id.iv_single_img),R.drawable.ic_default_rectange);
//            viewHolder.<ImageView>getView(R.id.iv_single_img).setVisibility(View.VISIBLE);
//            viewHolder.<ImageView>getView(R.id.iv_single_img).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    SmartToast.showInfo("single");
//                }
//            });
//        }else {
//            viewHolder.<ImageView>getView(R.id.iv_single_img).setVisibility(View.GONE);
//            viewHolder.<MyNineGridLayout>getView(R.id.nine_grid_layout).setVisibility(View.VISIBLE);
//
//            List<String> urlList= new ArrayList<>();
//
//            for (int i = 0; i < item.urlList.size(); i++) {
//                if (i>8){
//                    break;
//                }
//                urlList.add(item.urlList.get(i));
//            }
//            viewHolder.<MyNineGridLayout>getView(R.id.nine_grid_layout).setUrlList(urlList);
//            viewHolder.<MyNineGridLayout>getView(R.id.nine_grid_layout).setListener(new MyNineGridLayout.OnClickNineGridImageListener() {
//                @Override
//                public void onClickImage(int position, String url, List<String> urlList) {
//                    SmartToast.showInfo(""+position);
//                }
//            });
//        }
    }
}

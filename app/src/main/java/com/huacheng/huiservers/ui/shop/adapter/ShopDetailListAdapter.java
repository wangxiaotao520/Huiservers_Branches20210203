package com.huacheng.huiservers.ui.shop.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.xlhratingbar_lib.XLHRatingBar;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.shop.bean.ShopMainBean;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.huiservers.view.MyGridview;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;

import java.util.List;

public class ShopDetailListAdapter extends BaseAdapter {
	private Context context;
	List<ShopMainBean> bean;
	String num;
	public ShopDetailListAdapter(Context context,List<ShopMainBean> bean){
		this.context=context;
		this.bean=bean;
	}

	@Override
	public int getCount() {
		/*if (num.equals("all")) {
			return bean.size();
		}else {
			if (bean.size()>3) {
				return 3;
			}else {
				return bean.size();
			}
		}*/
		return bean.size();

	}

	@Override
	public Object getItem(int arg0) {

		return null;
	}

	@Override
	public long getItemId(int arg0) {

		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder = null;
		if (null==arg1) {
			// 获得ViewHolder对象  
			holder = new ViewHolder();
			arg1=LinearLayout.inflate(context,R.layout.shop_detail_list_item,null);
			holder.sdv_user_head= arg1.findViewById(R.id.sdv_user_head);
			holder.tv_pingjia_name= arg1.findViewById(R.id.tv_pingjia_name);
			holder.ratingBar=arg1.findViewById(R.id.ratingBar);
			holder.tv_pingjia_time=arg1.findViewById(R.id.tv_pingjia_time);
			holder.tv_pingjia_content=arg1.findViewById(R.id.tv_pingjia_content);
			holder.tv_pingjia_guige=arg1.findViewById(R.id.tv_pingjia_guige);
			holder.gridView=arg1.findViewById(R.id.gridView);
			holder.view=arg1.findViewById(R.id.view);

			//holder.txt_name=(TextView) arg1.findViewById(R.id.txt_name);
			//holder.txt_content=(TextView) arg1.findViewById(R.id.txt_content);
			//holder.room_ratingbar=(RatingBar) arg1.findViewById(R.id.room_ratingbar);
			arg1.setTag(holder);
		}else{ 
			holder=(ViewHolder)arg1.getTag(); 
		}
		FrescoUtils.getInstance().setImageUri(holder.sdv_user_head, StringUtils.getImgUrl(bean.get(arg0).getAvatars()));
		holder.tv_pingjia_name.setText(bean.get(arg0).getUsername());
		holder.tv_pingjia_name.setTextSize(15);
		holder.ratingBar.setCountSelected(Integer.valueOf(bean.get(arg0).getScore()));
		holder.tv_pingjia_time.setText(StringUtils.getDateToString(bean.get(arg0).getAddtime(),"2"));
		holder.tv_pingjia_content.setText(bean.get(arg0).getDescription());
		holder.tv_pingjia_guige.setText("");
		holder.view.setVisibility(View.VISIBLE);
		/*if (item.getImg_list() != null && item.getImg_list().size() > 0) {
			holder.gridView.setVisibility(View.VISIBLE);
			CircleItemImageAdapter mGridViewAdapter = new CircleItemImageAdapter(context, bean.get(arg0).getImg_list());
			holder.gridView.setAdapter(mGridViewAdapter);

		} else {
			holder.gridView.setVisibility(View.GONE);
		}*/
		//holder.room_ratingbar.setRating(Float.parseFloat(bean.get(arg0).getScore()));
		//holder.txt_name.setText(bean.get(arg0).getUsername());
		//holder.txt_content.setText(bean.get(arg0).getDescription());
		return arg1;
	}
	public  class ViewHolder {
		private TextView txt_name,txt_content;
		private RatingBar room_ratingbar;
		private SimpleDraweeView sdv_user_head;
		private TextView tv_pingjia_name;
		private XLHRatingBar ratingBar;
		private TextView tv_pingjia_time;
		private TextView tv_pingjia_content;
		private TextView tv_pingjia_guige;
		private MyGridview gridView;
		private View view;
	}

}

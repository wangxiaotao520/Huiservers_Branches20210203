package com.huacheng.huiservers.ui.index.oldservice;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelOldFile;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.index.oldservice.adapter.OldFileAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：老人档案
 * 时间：2019/8/14 17:40
 * created by DFF
 */
public class OldFileActivity extends BaseActivity {
    View headerView;
    private ListView listview;
    private SmartRefreshLayout refreshLayout;
    private TagFlowLayout flowlayout1;
    private TagFlowLayout flowlayout2;
    private SimpleDraweeView sdv_head;
    private TextView tv_name, tv_sf_ID, tv_brithday, tv_age, tv_urgent_person, tv_phone, tv_xueli, tv_zhengzhi, tv_shengao, tv_tizhong,
            tv_bingli_content, tv_last_time, tv_tijian_time, tv_xinlv, tv_xuetang;
    private ImageView iv_old_img;
    List<String> mBingLiList = new ArrayList<>();
    List<String> mXinXiList = new ArrayList<>();
    List<ModelOldFile> mdata = new ArrayList<>();
    OldFileAdapter oldFileAdapter;

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("老人档案");

        listview = findViewById(R.id.listview);
        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setEnableRefresh(false);
        initHeaderView();

    }

    private void initHeaderView() {
        //添加list头布局
        headerView = LayoutInflater.from(this).inflate(R.layout.activity_old_file_header, null);
        sdv_head = headerView.findViewById(R.id.sdv_head);//头像
        tv_name = headerView.findViewById(R.id.tv_name);//老人名字
        iv_old_img = headerView.findViewById(R.id.iv_old_img);//老人性别图标
        tv_sf_ID = headerView.findViewById(R.id.tv_sf_ID);//身份证号
        tv_brithday = headerView.findViewById(R.id.tv_brithday);//出生年月
        tv_age = headerView.findViewById(R.id.tv_age);//年龄
        tv_urgent_person = headerView.findViewById(R.id.tv_urgent_person);//联系紧急人
        tv_phone = headerView.findViewById(R.id.tv_phone);//联系方式
        tv_xueli = headerView.findViewById(R.id.tv_xueli);//学历
        tv_zhengzhi = headerView.findViewById(R.id.tv_zhengzhi);//政治面貌
        tv_shengao = headerView.findViewById(R.id.tv_shengao);//身高
        tv_tizhong = headerView.findViewById(R.id.tv_tizhong);//体重
        flowlayout1 = headerView.findViewById(R.id.flowlayout1);
        flowlayout2 = headerView.findViewById(R.id.flowlayout2);
        tv_bingli_content = headerView.findViewById(R.id.tv_bingli_content);//病例描述
        tv_last_time = headerView.findViewById(R.id.tv_last_time);//上次体检时间

        addFlowView();
        listview.addHeaderView(headerView);
        mdata.add(new ModelOldFile());
        oldFileAdapter = new OldFileAdapter(this, R.layout.activity_old_file_item, mdata);
        listview.setAdapter(oldFileAdapter);

    }

    private void addFlowView() {

        mBingLiList.clear();
        mBingLiList.add("两个多点字吧");
        mBingLiList.add("四个字吧");
        mBingLiList.add("三个字");

      /*  for (int i = 0; i < houseRentInfo.getLabel().size(); i++) {
            StoreCatedata.add(houseRentInfo.getLabel().get(i).getLabel_name());
        }*/
        final LayoutInflater mInflater = LayoutInflater.from(mContext);
        TagAdapter adapter = new TagAdapter<String>(mBingLiList) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.activity_old_file_flow_tag,
                        flowlayout1, false);
                tv.setText(mBingLiList.get(position));
                return tv;
            }
        };
        //adapter.setSelectedList(list_selected_position);
        flowlayout1.setAdapter(adapter);
        final LayoutInflater mInflater1 = LayoutInflater.from(mContext);
        TagAdapter adapter1 = new TagAdapter<String>(mBingLiList) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater1.inflate(R.layout.activity_old_file_flow_tag,
                        flowlayout2, false);
                tv.setText(mBingLiList.get(position));
                return tv;
            }
        };
        //adapter.setSelectedList(list_selected_position);
        flowlayout2.setAdapter(adapter1);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                requestData();
            }
        });
    }

    private void requestData() {
        refreshLayout.finishLoadMore();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_old_file_list;
    }

    @Override
    protected void initIntentData() {

    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }
}

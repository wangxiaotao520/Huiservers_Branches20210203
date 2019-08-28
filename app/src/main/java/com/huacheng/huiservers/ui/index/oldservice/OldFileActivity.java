package com.huacheng.huiservers.ui.index.oldservice;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelOldCheckRecord;
import com.huacheng.huiservers.model.ModelOldFileDetail;
import com.huacheng.huiservers.model.ModelOldIndexTop;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.index.oldservice.adapter.OldFileAdapter;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
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
            tv_bingli_content, tv_last_time, tv_tijian_time, tv_xinlv, tv_xuetang,tv_xuexing;
    private ImageView iv_old_img;
    List<String> mBingLiList = new ArrayList<>();
    List<String> mXinXiList = new ArrayList<>();
    List<ModelOldCheckRecord> mDatas = new ArrayList<>();
    OldFileAdapter oldFileAdapter;
    private ModelOldIndexTop modelOldIndexTop;
    private int page = 1;
    private View ll_healthy_file_title;
    private View ll_healthy_file;

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("老人档案");

        listview = findViewById(R.id.listview);
        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(false);
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

        ll_healthy_file_title = headerView.findViewById(R.id.ll_healthy_file_title);
        ll_healthy_file = headerView.findViewById(R.id.ll_healthy_file);
        tv_shengao = headerView.findViewById(R.id.tv_shengao);//身高
        tv_tizhong = headerView.findViewById(R.id.tv_tizhong);//体重
        tv_xuexing = headerView.findViewById(R.id.tv_xuexing);//血型
        flowlayout1 = headerView.findViewById(R.id.flowlayout1); //过往病史
        flowlayout2 = headerView.findViewById(R.id.flowlayout2);  //过敏信息
        tv_bingli_content = headerView.findViewById(R.id.tv_bingli_content);//病例描述
        tv_last_time = headerView.findViewById(R.id.tv_last_time);//上次体检时间

       // addFlowView();
        listview.addHeaderView(headerView);
        oldFileAdapter = new OldFileAdapter(this, R.layout.activity_old_file_item, mDatas);
        listview.setAdapter(oldFileAdapter);

    }

    private void addFlowView() {



      /*  for (int i = 0; i < houseRentInfo.getLabel().size(); i++) {
            StoreCatedata.add(houseRentInfo.getLabel().get(i).getLabel_name());
        }*/
      if (mBingLiList.size()>0){
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
      }

        if (mXinXiList.size()>0){
            final LayoutInflater mInflater1 = LayoutInflater.from(mContext);
            TagAdapter adapter1 = new TagAdapter<String>(mXinXiList) {

                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv = (TextView) mInflater1.inflate(R.layout.activity_old_file_flow_tag,
                            flowlayout2, false);
                    tv.setText(mXinXiList.get(position));
                    return tv;
                }
            };
            //adapter.setSelectedList(list_selected_position);
            flowlayout2.setAdapter(adapter1);
        }
    }

    @Override
    protected void initData() {
        if (modelOldIndexTop==null){
            return;
        }
        showDialog(smallDialog);
        requestDataTop();
    }

    /**
     * 请求顶部的信息
     */
    private void requestDataTop() {
        HashMap<String, String> params = new HashMap<>();

        params.put("par_uid",modelOldIndexTop.getPar_uid());
        params.put("o_company_id",modelOldIndexTop.getO_company_id());
        MyOkHttp.get().post(ApiHttpClient.PENSION_OLDFILE_DETAIL, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (JsonUtil.getInstance().isSuccess(response)) {
                    ModelOldFileDetail info = (ModelOldFileDetail) JsonUtil.getInstance().parseJsonFromResponse(response, ModelOldFileDetail.class);
                    ModelOldFileDetail.BasisBean basis = info.getBasis();
                    if (basis!=null){
                        //头部数据
                        FrescoUtils.getInstance().setImageUri(sdv_head,ApiHttpClient.IMG_URL+basis.getPhoto());
                        tv_name.setText(basis.getName()+"");
                        iv_old_img.setBackgroundResource("1".equals(basis.getSex())?R.mipmap.ic_old_nan:R.mipmap.ic_old_nv);
                        tv_sf_ID.setText(basis.getIdcard()+"");
                        tv_brithday.setText(basis.getChusheng()+"");
                        tv_age.setText(basis.getBirthday()+"岁");
                        tv_urgent_person.setText(basis.getContact()+"");
                        tv_phone.setText(basis.getTelphone()+"");
                        tv_xueli.setText(basis.getEducation_cn()+"");
                        tv_zhengzhi.setText(basis.getPolitical_cn()+"");
                    }
                    ModelOldFileDetail.ArchivesBean archives = info.getArchives();
                    if (archives!=null){
                        ll_healthy_file_title.setVisibility(View.VISIBLE);
                        ll_healthy_file.setVisibility(View.VISIBLE);
                        tv_shengao.setText(archives.getHeight()+"");
                        tv_tizhong.setText(archives.getWeight()+"");
                        if ("1".equals(archives.getBlood())){
                            tv_xuexing.setText("A型");
                        }else if ("2".equals(archives.getBlood())){
                            tv_xuexing.setText("B型");
                        }else if ("3".equals(archives.getBlood())){
                            tv_xuexing.setText("O型");
                        }else if ("4".equals(archives.getBlood())){
                            tv_xuexing.setText("AB型");
                        }
                        //病例
                        if (archives.getIll()!=null&&archives.getIll().size()>0){
                            for (int i = 0; i < archives.getIll().size(); i++) {
                                mBingLiList.add(archives.getIll().get(i).getC_name());
                            }
                        }else {

                        }
                        //过敏
                        if (archives.getAllergy()!=null&&archives.getAllergy().size()>0){
                            for (int i = 0; i < archives.getAllergy().size(); i++) {
                                mXinXiList.add(archives.getAllergy().get(i).getC_name());
                            }
                        }else {

                        }
                        tv_bingli_content.setText(archives.getBody()+"");
                        tv_last_time.setText(StringUtils.getDateToString(archives.getCheckuptime(),"8"));

                    }else {
                        ll_healthy_file_title.setVisibility(View.GONE);
                        ll_healthy_file.setVisibility(View.GONE);
                    }
                    //请求下方体检记录
                    requestDataCheckRecord();
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });
    }

    /**
     * 请求体检记录
     */
    private void requestDataCheckRecord() {
        HashMap<String, String> params = new HashMap<>();
        params.put("p", page + "");
        params.put("old_id",modelOldIndexTop.getI_id()+"");
        params.put("o_company_id",modelOldIndexTop.getO_company_id()+"");
        MyOkHttp.get().post(ApiHttpClient.PENSION_CHECKUP_LIST, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                if (JsonUtil.getInstance().isSuccess(response)) {
                    ModelOldCheckRecord info = (ModelOldCheckRecord) JsonUtil.getInstance().parseJsonFromResponse(response, ModelOldCheckRecord.class);
                    if (info != null) {
                        if (info.getList() != null && info.getList().size() > 0) {
                            if (page == 1) {
                                mDatas.clear();
                            }
                            mDatas.addAll(info.getList());
                            page++;
                            if (page > info.getTotalPages()) {
                                refreshLayout.setEnableLoadMore(false);
                            } else {
                                refreshLayout.setEnableLoadMore(true);
                            }
                            oldFileAdapter.notifyDataSetChanged();
                        } else {
                            if (page == 1) {
                                mDatas.clear();
                                ModelOldCheckRecord modelOldCheckRecord = new ModelOldCheckRecord();
                                modelOldCheckRecord.setList_type(1);
                                mDatas.add(modelOldCheckRecord);
                            }
                            refreshLayout.setEnableLoadMore(false);
                            oldFileAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    hideDialog(smallDialog);
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                SmartToast.showInfo("网络异常，请检查网络设置");
                if (page == 1) {
                    refreshLayout.setEnableLoadMore(false);
                }
            }
        });
    }

    @Override
    protected void initListener() {
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //
                requestDataCheckRecord();
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
                requestDataTop();
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (id<0){
                    return;
                }
                if (mDatas.get((int) id).getList_type()==1){
                    return;
                }
                Intent intent = new Intent(mContext, OldFileDetailActivity.class);
                intent.putExtra("o_company_id",modelOldIndexTop.getO_company_id()+"");
                intent.putExtra("checkup_id",mDatas.get((int) id).getId()+"");
                mContext.startActivity(intent);
            }
        });
    }



    @Override
    protected int getLayoutId() {
        return R.layout.activity_old_file_list;
    }

    @Override
    protected void initIntentData() {
        modelOldIndexTop= (ModelOldIndexTop) getIntent().getSerializableExtra("model");
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }
}

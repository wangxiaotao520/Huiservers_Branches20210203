package com.huacheng.huiservers.ui.index.houserent;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.IBinder;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.HouseRentDetail;
import com.huacheng.huiservers.model.HouseRentTagListBean;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.index.houserent.adapter.HouseRentListAdapter;
import com.huacheng.huiservers.ui.index.houserent.adapter.HouseRentTagAdapter;
import com.huacheng.huiservers.ui.index.houserent.presenter.HouseListInterface;
import com.huacheng.huiservers.ui.index.houserent.presenter.HouseRentListPresenter;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.view.widget.loadmorelistview.PagingListView;
import com.huacheng.libraryservice.utils.DeviceUtils;
import com.huacheng.libraryservice.utils.NullUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Description: 租售房列表Activity
 * created by wangxiaotao
 * 2018/11/6 0006 上午 11:07
 */
public class HouseRentListActivity extends BaseActivity implements View.OnClickListener, HouseListInterface {

    private SwipeRefreshLayout refreshLayout;
    private PagingListView listView;
    private RelativeLayout rel_no_data;

   // private LinearLayout ll_search_tag;
    private ListView listview_tag;
    private RecyclerView rv_tag;
    private EditText edt_low_price;
    private EditText edt_high_price;
    private TextView tv_confirm;
    private TextView tv_buxian;
    private View v_trans_tag;
    private View v_trans;
    private TagFlowLayout flowlayout_taglist;
    //顶部
    private LinearLayout ll_neighbor_top;
    private ImageView iv_left;
    private FrameLayout fl_left;
    private EditText edt_search;
    private TextView tv_search_go;
    //租售房
    private RelativeLayout rl_rent_sale;
    private TextView tv_rent_sale;
    private ImageView iv_rent_sale;
    //面积
    private RelativeLayout rl_acreage;
    private TextView tv_acreage;
    private ImageView iv_acreage;
    //房型
    private RelativeLayout rl_house_type;
    private TextView tv_house_type;
    private ImageView iv_house_type;
    //排序
    private RelativeLayout rl_order_type;

    private ImageView iv_order_type;


    private List<HouseRentDetail> mDatas = new ArrayList<>();

    private HouseRentListAdapter houseRentListAdapter;

    private List<HouseRentTagListBean> mDatas_rent_sale = new ArrayList<>();
    private List<HouseRentTagListBean> mDatas_acreage = new ArrayList<>();
    private List<HouseRentTagListBean> mDatas_house_type = new ArrayList<>();
    private List<HouseRentTagListBean> mDatas_order_type = new ArrayList<>();

    private int current_type = 0;//0租金 //1面积 //2房型 //3排序
    private LinearLayout ll_custom_search;
    private TextView tv_tag_title;

    private PopupWindow popupWindow;
    private PopupWindow popupWindow_tag;
    private LinearLayout ll_tag_container;

    private List<HouseRentTagListBean> mDatas_tag = new ArrayList<>(); //排序
    private List<HouseRentTagListBean> mDatas_tag2 = new ArrayList<>();//租金 //面积 //房型
    String[] filters;
    private HouseRentTagAdapter houseRentTagAdapter;
    SharePrefrenceUtil prefrenceUtil;
    // 把所有请求的参数都设置成成员变量
    private HouseRentListPresenter mPresenter;
    private int jump_type=1;//1租房2售房
    private int page=1;
    private String money ="";//价格区间id

    private String moneyOne=""; //输入价格的第一个参数

    private String moneyTwo ="";// 输入价格的第二个参数

    private String acreage ="";// 面积区间ID

    private  String areaOne ="";//输入面积的第一个参数

    private String areaTwo ="";// 输入面积的第二个参数

    private  String  housetype =""; // 房型参数ID

    private  String defaultid=""; //排序条件ID

    private String community_name="";//搜索小区名称



    @Override
    protected void initView() {
        prefrenceUtil = new SharePrefrenceUtil(this);
        ll_neighbor_top = findViewById(R.id.ll_neighbor_top);
        iv_left = findViewById(R.id.iv_left);
        fl_left = findViewById(R.id.fl_left);
        edt_search = findViewById(R.id.edt_search);
        tv_search_go = findViewById(R.id.tv_search_go);

        rl_rent_sale = findViewById(R.id.rl_rent_sale);
        tv_rent_sale = findViewById(R.id.tv_rent_sale);
        iv_rent_sale = findViewById(R.id.iv_rent_sale);

        rl_acreage = findViewById(R.id.rl_acreage);
        tv_acreage = findViewById(R.id.tv_acreage);
        iv_acreage = findViewById(R.id.iv_acreage);

        rl_house_type = findViewById(R.id.rl_house_type);
        tv_house_type = findViewById(R.id.tv_house_type);
        iv_house_type = findViewById(R.id.iv_house_type);

        rl_order_type = findViewById(R.id.rl_order_type);

        iv_order_type = findViewById(R.id.iv_order_type);

        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
    //    refreshLayout.setEnabled(false);
        listView = findViewById(R.id.listView);
        houseRentListAdapter = new HouseRentListAdapter(this, R.layout.item_house_rent_list, mDatas,this.jump_type);
        listView.setAdapter(houseRentListAdapter);
        rel_no_data = findViewById(R.id.rel_no_data);
//        if (jump_type==1){//租房
//            tv_rent_sale.setText("租金");
//        }else {
//            //售房
//            tv_rent_sale.setText("售价");
//        }
        tv_rent_sale.setText("价格");
    }

    private void initPopupWindow() {
        View tagView = LayoutInflater.from(this).inflate(R.layout.layout_house_rent_tag, null);
        int[] position = new int[2];
        listView.getLocationOnScreen(position);
        popupWindow = new PopupWindow(tagView, ViewGroup.LayoutParams.MATCH_PARENT, DeviceUtils.getWindowHeight(this)-position[1], true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        tagView.setFocusableInTouchMode(true);
        tagView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
                if (arg1 == KeyEvent.KEYCODE_BACK) {
                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }
                }
                return false;
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
                hideSearchTag();
            }
        });

        v_trans = tagView.findViewById(R.id.v_trans);
      //  ll_tag_container = tagView.findViewById(R.id.ll_tag_container);

        listview_tag=tagView.findViewById(R.id.listview_tag);
        houseRentTagAdapter = new HouseRentTagAdapter(this, R.layout.item_house_list_tag, mDatas_tag);
        listview_tag.setAdapter(houseRentTagAdapter);
        v_trans.setOnClickListener(this);
        listview_tag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              if (current_type==3){
                    defaultid=mDatas_tag.get(position).getId();
//                    tv_order_type.setText(mDatas_tag.get(position).getStatus());
//                    tv_order_type.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                HouseRentTagListBean houseRentTagListBean = mDatas_tag.get(position);
                if (!houseRentTagListBean.isSelected()){
                    //先将所有的变成false
                    for (HouseRentTagListBean rentTagListBean : mDatas_tag) {
                        rentTagListBean.setSelected(false);
                    }
                    //再将点击的选中
                    houseRentTagListBean.setSelected(true);
                    houseRentTagAdapter.notifyDataSetChanged();
                    hideSearchTag();
                    showDialog(smallDialog);
                    page=1;
                    requestData();
                    if (popupWindow!=null){
                        popupWindow.dismiss();
                    }
                }
            }
            //请求
        });
    }

    /**
     * 价格 面积 房型popupwindow
     */
    private void initPopupWindowTag() {
        View tagView = LayoutInflater.from(this).inflate(R.layout.layout_house_rent_tag_flowlayout, null);
        int[] position = new int[2];
        listView.getLocationOnScreen(position);
        popupWindow_tag = new PopupWindow(tagView, ViewGroup.LayoutParams.MATCH_PARENT,  DeviceUtils.getWindowHeight(this)-position[1], true);
        popupWindow_tag.setTouchable(true);
        popupWindow_tag.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        popupWindow_tag.setBackgroundDrawable(new BitmapDrawable());
        popupWindow_tag.setOutsideTouchable(true);
        popupWindow_tag.setFocusable(true);
        popupWindow_tag.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        popupWindow_tag.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        tagView.setFocusableInTouchMode(true);
        tagView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
                if (arg1 == KeyEvent.KEYCODE_BACK) {
                    if (popupWindow_tag != null) {
                        popupWindow_tag.dismiss();
                    }
                }
                return false;
            }
        });
        popupWindow_tag.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
                hideSearchTag();
            }
        });
        ll_custom_search = tagView.findViewById(R.id.ll_custom_search_tag);
        edt_low_price = tagView.findViewById(R.id.edt_low_price);
        edt_high_price = tagView.findViewById(R.id.edt_high_price);
        if (jump_type==1){
            //租房
            edt_low_price.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
            edt_high_price.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        }else {
            //售房
            edt_low_price.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
            edt_high_price.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        }
        tv_tag_title = tagView.findViewById(R.id.tv_tag_title);
        tv_confirm =tagView. findViewById(R.id.tv_confirm);
        tv_buxian =tagView. findViewById(R.id.tv_buxian);
        v_trans_tag = tagView.findViewById(R.id.v_trans_tag);
        //  ll_tag_container = tagView.findViewById(R.id.ll_tag_container);

        flowlayout_taglist=tagView.findViewById(R.id.flowlayout_taglist);
        v_trans_tag.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
        tv_buxian.setOnClickListener(this);

    }
    @Override
    protected void initData() {
        mPresenter=new HouseRentListPresenter(this,this);
        showDialog(smallDialog);
        //请求第一房屋租售筛选条件个列表
        requestData();

    }

    /**
     * 请求网络（）
     */
    private void requestData() {
        HashMap<String, String> params = new HashMap<>();
        if (!NullUtil.isStringEmpty(prefrenceUtil.getXiaoQuId())){
            params.put("community_id",prefrenceUtil.getXiaoQuId());
        }
        if (!NullUtil.isStringEmpty(money)){
            params.put("money",money+"");
        }
        if (!NullUtil.isStringEmpty(moneyOne)){
            params.put("moneyOne",moneyOne+"");
        }
        if (!NullUtil.isStringEmpty(moneyTwo)){
            params.put("moneyTwo",moneyTwo+"");
        }
        if (!NullUtil.isStringEmpty(acreage)){
            params.put("acreage",acreage +"");
        }
        if (!NullUtil.isStringEmpty(areaOne)){
            params.put("areaOne",areaOne +"");
        }
        if (!NullUtil.isStringEmpty(areaTwo)){
            params.put("areaTwo",areaTwo +"");
        }
        if (!NullUtil.isStringEmpty(housetype)){
            params.put("housetype",housetype +"");
        }
        if (!NullUtil.isStringEmpty(defaultid)){
            params.put("default",defaultid +"");
        }
        if (!NullUtil.isStringEmpty(community_name)){
            params.put("community_name",community_name +"");
        }
        if (jump_type==1){//租房
            mPresenter.getLeaseList(page,params);
        }else {
            //售房
            mPresenter.getSellList(page,params);
        }
    }


    @Override
    protected void initListener() {
        rl_rent_sale.setOnClickListener(this);
        rl_acreage.setOnClickListener(this);
        rl_house_type.setOnClickListener(this);
        rl_order_type.setOnClickListener(this);
    //    v_trans.setOnClickListener(this);
        tv_search_go.setOnClickListener(this);
   //     tv_confirm.setOnClickListener(this);
        listView.setHasMoreItems(false);
        listView.setPagingableListener(new PagingListView.Pagingable() {
            @Override
            public void onLoadMoreItems() {
                requestData();

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 跳转到详情页
                // 区别租售房
                String id1 = mDatas.get(position).getId();
                Intent intent = new Intent(HouseRentListActivity.this, HouserentDetailActivity.class);
                intent.putExtra("jump_type",jump_type);
                intent.putExtra("id",id1);
                startActivity(intent);
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                requestData();
            }
        });
        iv_left.setOnClickListener(this);
        fl_left.setOnClickListener(this);
        edt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean isOK = true;
                switch (actionId) {

                    case EditorInfo.IME_ACTION_SEARCH:
                        String search_name = edt_search.getText().toString().trim();
                        HouseRentListActivity.this.community_name=search_name;
                        hideSoftInput(edt_search.getWindowToken());
                        showDialog(smallDialog);
                        page=1;
                        requestData();
                        break;
                    default:
                        isOK = false;
                        break;

                }
                return isOK;
            }});
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_houserent_list;
    }

    @Override
    protected void initIntentData() {
        jump_type=getIntent().getIntExtra("jump_type",1);
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_rent_sale:
                if (popupWindow_tag==null){
                   initPopupWindowTag();
                }
                if (mDatas_rent_sale.size()>0){
                   inflateTagListAndShow(mDatas_rent_sale,0);
                }else {
                    //1租房，2售房
                    showDialog(smallDialog);
                    mPresenter.getPriceList(jump_type,0);
                }
                break;
            case R.id.rl_acreage:
                if (popupWindow_tag==null){
                    initPopupWindowTag();
                }
                if (mDatas_acreage.size()>0){
                    inflateTagListAndShow(mDatas_acreage,1);
                }else {
                    showDialog(smallDialog);
                    mPresenter.getAcreageList(1);
                }
                break;
            case R.id.rl_house_type:
                if (popupWindow_tag==null){
                    initPopupWindowTag();
                }
                if (mDatas_house_type.size()>0){
                    inflateTagListAndShow(mDatas_house_type,2);
                }else {
                    showDialog(smallDialog);
                   mPresenter.getHouseTypeList(2);
                }
                break;
            case R.id.rl_order_type:
                if (popupWindow==null){
                    initPopupWindow();
                }
                if (mDatas_order_type.size()>0){
                    inflateTagListAndShow(mDatas_order_type,3);
                }else {
                    showDialog(smallDialog);
                    mPresenter.getDefultList(3);
                }
                break;
            case R.id.v_trans:
                hideSearchTag();
                if (popupWindow!=null){
                    popupWindow.dismiss();
                }
                break;
            case R.id.v_trans_tag:
                hideSearchTag();
                if (popupWindow_tag!=null){
                    popupWindow_tag.dismiss();
                }
                break;
            case R.id.tv_search_go:
               // 搜索
                String search_name = edt_search.getText().toString().trim();
                this.community_name=search_name;
                hideSoftInput(edt_search.getWindowToken());
                showDialog(smallDialog);
                page=1;
                requestData();
                break;
            case R.id.tv_confirm:
                // 确定
                if (current_type==0){
                    //租售房
                    String low_price = edt_low_price.getText().toString().trim();
                    String high_price = edt_high_price.getText().toString().trim();
                    if (!NullUtil.isStringEmpty(low_price)&&!NullUtil.isStringEmpty(high_price)){
                        try {
                            if (Integer.parseInt(high_price)-Integer.parseInt(low_price)<0) {
                                SmartToast.showInfo("低价不能大于高价");
                                return;
                            }
                            money="";
                            if (jump_type==1){
                                this.moneyOne=low_price+"";
                                this.moneyTwo=high_price+"";
                            }else {
                                //售房
                                this.moneyOne=(Integer.parseInt(low_price)*10000)+"";
                                this.moneyTwo=(Integer.parseInt(high_price)*10000)+"";
                            }
                            showDialog(smallDialog);
                            page=1;
                            requestData();
                            for (int i = 0; i < mDatas_rent_sale.size(); i++) {
                                mDatas_rent_sale.get(i).setSelected(false);
                            }
                            //
                            if (jump_type==1){
                                tv_rent_sale.setText(low_price+"-"+high_price);
                            }else {
                                tv_rent_sale.setText(low_price+"-"+high_price+"万元");
                            }

                            tv_rent_sale.setTextColor(getResources().getColor(R.color.colorPrimary));
                            hideSoftInput(edt_high_price.getWindowToken());
                            if (popupWindow!=null){
                                popupWindow.dismiss();
                            }
                            if (popupWindow_tag!=null){
                                popupWindow_tag.dismiss();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                }else if (current_type==1){
                    //面积
                    String low_acreage = edt_low_price.getText().toString().trim();
                    String high_acreage = edt_high_price.getText().toString().trim();
                    if (!NullUtil.isStringEmpty(low_acreage)&&!NullUtil.isStringEmpty(high_acreage)){
                        try {
                            if (Integer.parseInt(high_acreage)-Integer.parseInt(low_acreage)<0) {
                                SmartToast.showInfo("小面积不能大于大面积");
                                return;
                            }
                            acreage="";
                            this.areaOne=low_acreage+"";
                            this.areaTwo=high_acreage+"";
                            showDialog(smallDialog);
                            page=1;
                            requestData();
                            for (int i = 0; i < mDatas_acreage.size(); i++) {
                                mDatas_acreage.get(i).setSelected(false);
                            }
                            tv_acreage.setText(low_acreage+"-"+high_acreage);
                            tv_acreage.setTextColor(getResources().getColor(R.color.colorPrimary));

                            hideSoftInput(edt_high_price.getWindowToken());
                            if (popupWindow!=null){
                                popupWindow.dismiss();
                            }
                            if (popupWindow_tag!=null){
                                popupWindow_tag.dismiss();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }

                break;
            case R.id.tv_buxian:
                if (current_type==0){
                    //租金
                    money="";
                    tv_rent_sale.setText("价格");
                    tv_rent_sale.setTextColor(getResources().getColor(R.color.title_color));
                    moneyOne="";
                    moneyTwo="";
                }else if(current_type==1){
                    acreage="";
                    tv_acreage.setText("面积");
                    tv_acreage.setTextColor(getResources().getColor(R.color.title_color));
                    areaOne="";
                    areaTwo="";
                }else if (current_type==2){
                    housetype="";
                    tv_house_type.setText("房型");
                    tv_house_type.setTextColor(getResources().getColor(R.color.title_color));
                }
                    //先将所有的变成false
                for (HouseRentTagListBean rentTagListBean : mDatas_tag2) {
                        rentTagListBean.setSelected(false);
                }
                hideSearchTag();
                showDialog(smallDialog);
                page=1;
                requestData();
                if (popupWindow_tag!=null){
                    popupWindow_tag.dismiss();
                }
                break;
               case  R.id.iv_left:
               case  R.id.fl_left:
                   if (edt_search!=null){
                       hideSoftInput(edt_search.getWindowToken());
                   }
                   finish();
            break;
                default:
                    break;
        }
    }

    /**
     * 显示筛选列表
     * @param datas
     */
    private void inflateTagListAndShow(final List <HouseRentTagListBean> datas, int type ){
        this.current_type=type;


        if (type==0){
            ll_custom_search.setVisibility(View.VISIBLE);
            edt_low_price.setHint("最低价");
            edt_high_price.setHint("最高价");
            if (jump_type==1){
              //  tv_unit.setText("元");
                tv_tag_title.setText("价格区间（元）");
            }else {
             //   tv_unit.setText("万元");
                tv_tag_title.setText("价格区间（万元）");
            }

            // 显示刚才的搜索结果
            if (!NullUtil.isStringEmpty(moneyOne)){
                if (jump_type==1){
                    edt_low_price.setText(moneyOne);
                }else
                    try {
                        edt_low_price.setText(((int)(Integer.parseInt(moneyOne)/10000f))+"");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
            }else {
                edt_low_price.setText("");
            }
            if (!NullUtil.isStringEmpty(moneyTwo)){
                if (jump_type==1){
                    edt_high_price.setText(moneyTwo);
                }else {
                    try {
                        edt_high_price.setText(((int)(Integer.parseInt(moneyTwo)/10000f))+"");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }else {
                edt_high_price.setText("");
            }
            mDatas_tag2.clear();
            for (int i = 0; i < datas.size(); i++) {
                if (!"不限".equals(datas.get(i).getPrice())){
                    mDatas_tag2.add(datas.get(i));
                }
            }
            //mDatas_tag2.addAll(datas);
            filters=new String[this.mDatas_tag2.size()];
            for (int i = 0; i < this.mDatas_tag2.size(); i++) {
                filters[i]= this.mDatas_tag2.get(i).getPrice()+"";
            }
            inflateFlowTagLayout();
            showpopup2();
            iv_rent_sale.setBackgroundResource(R.mipmap.ic_arrow_up_orange_sloid);
            tv_confirm.setVisibility(View.VISIBLE);
        }else if (type==1){
            ll_custom_search.setVisibility(View.VISIBLE);
            edt_low_price.setHint("小面积");
            edt_high_price.setHint("大面积");
          //  tv_unit.setText("平米");
            // 显示刚才的搜索结果
            if (!NullUtil.isStringEmpty(areaOne)){
                edt_low_price.setText(areaOne);
            }else {
                edt_low_price.setText("");
            }
            if (!NullUtil.isStringEmpty(areaTwo)){
                edt_high_price.setText(areaTwo);
            }else {
                edt_high_price.setText("");
            }
            tv_tag_title.setText("面积区间（平米）");
            mDatas_tag2.clear();
            for (int i = 0; i < datas.size(); i++) {
                if (!"不限".equals(datas.get(i).getSize())){
                    mDatas_tag2.add(datas.get(i));
                }
            }
            filters=new String[this.mDatas_tag2.size()];
            for (int i = 0; i < this.mDatas_tag2.size(); i++) {
                filters[i]= this.mDatas_tag2.get(i).getSize()+"";
            }
            inflateFlowTagLayout();
            showpopup2();
            iv_acreage.setBackgroundResource(R.mipmap.ic_arrow_up_orange_sloid);
            tv_confirm.setVisibility(View.VISIBLE);
        }else if (type==2){

            ll_custom_search.setVisibility(View.GONE);
            iv_house_type.setBackgroundResource(R.mipmap.ic_arrow_up_orange_sloid);
            tv_tag_title.setText("房型选择");
            mDatas_tag2.clear();
            for (int i = 0; i < datas.size(); i++) {
                if (!"不限".equals(datas.get(i).getType())){
                    mDatas_tag2.add(datas.get(i));
                }
            }
            filters=new String[this.mDatas_tag2.size()];
            for (int i = 0; i < this.mDatas_tag2.size(); i++) {
                filters[i]= this.mDatas_tag2.get(i).getType()+"";
            }
            inflateFlowTagLayout();
            showpopup2();
            tv_confirm.setVisibility(View.GONE);
        }else if (type==3){
            iv_order_type.setBackgroundResource(R.mipmap.ic_rent_order_orange);
            // 显示筛选图标
            mDatas_tag.clear();
            mDatas_tag.addAll(datas);
            houseRentTagAdapter.notifyDataSetChanged();
            showpopup();
        }

    }

    /**
     * 显示flowlayout
     */
    private void inflateFlowTagLayout() {
        flowlayout_taglist.setAdapter(
                new TagAdapter<String>(filters) {

                    @Override
                    public View getView(FlowLayout parent, int position, String mTitle2) {
                        View convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_rent_tag_textview, flowlayout_taglist, false);
                        TextView tv = convertView.findViewById(R.id.tv_tag);
                        int selectedPosition = -1;
                        for (int i = 0; i <mDatas_tag2.size() ; i++) {
                            if (mDatas_tag2.get(i).isSelected()){
                                selectedPosition=i;
                            }
                        }
                        if (selectedPosition == position) {
                            tv.setSelected(true);
                            tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.allshape_orange));
                            tv.setTextColor(getResources().getColor(R.color.white));
                        } else {
                            tv.setSelected(false);
                            tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.allshape_gray_f8));
                            tv.setTextColor(getResources().getColor(R.color.title_sub_color));
                        }
                        tv.setText(mTitle2);
                        return convertView;
                    }
                }
        );

        flowlayout_taglist.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (current_type==0){
                    //租金
                    money=mDatas_tag2.get(position).getId();
                    tv_rent_sale.setText(mDatas_tag2.get(position).getPrice());
                    tv_rent_sale.setTextColor(getResources().getColor(R.color.colorPrimary));
                    moneyOne="";
                    moneyTwo="";
                }else if(current_type==1){
                    acreage=mDatas_tag2.get(position).getId();
                    tv_acreage.setText(mDatas_tag2.get(position).getSize());
                    tv_acreage.setTextColor(getResources().getColor(R.color.colorPrimary));
                    areaOne="";
                    areaTwo="";
                }else if (current_type==2){
                    housetype=mDatas_tag2.get(position).getId();
                    tv_house_type.setText(mDatas_tag2.get(position).getType());
                    tv_house_type.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                HouseRentTagListBean houseRentTagListBean = mDatas_tag2.get(position);
                if (!houseRentTagListBean.isSelected()){
                    //先将所有的变成false
                    for (HouseRentTagListBean rentTagListBean : mDatas_tag2) {
                        rentTagListBean.setSelected(false);
                    }
                    //再将点击的选中
                    houseRentTagListBean.setSelected(true);
                    hideSearchTag();
                    showDialog(smallDialog);
                    page=1;
                    requestData();
                    if (popupWindow_tag!=null){
                        popupWindow_tag.dismiss();
                    }
                }
                return true;
            }
        });


    }

    private void showpopup() {
        if (popupWindow!=null){
           popupWindow.showAsDropDown(rl_rent_sale);

        }
    }
    private void showpopup2() {
        if (popupWindow_tag!=null){
            popupWindow_tag.showAsDropDown(rl_rent_sale);

        }
    }

    /**
     * 限制listview高度 最多显示六条
     * @param listView
     */
    private void setListViewHeight(ListView listView){
        ListAdapter listAdapter = listView.getAdapter(); //得到ListView 添加的适配器
        if(listAdapter == null){
            return;
        }

        View itemView = listAdapter.getView(0, null, listView); //获取其中的一项
        //进行这一项的测量，为什么加这一步，具体分析可以参考 https://www.jianshu.com/p/dbd6afb2c890这篇文章
        itemView.measure(0,0);
        int itemHeight = itemView.getMeasuredHeight(); //一项的高度
        int itemCount = listAdapter.getCount();//得到总的项数
        LinearLayout.LayoutParams layoutParams = null; //进行布局参数的设置
        if(itemCount <= 4){
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT ,itemHeight*itemCount);
        }else if(itemCount > 4){
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT ,itemHeight*4);
        }
        listView.setLayoutParams(layoutParams);
    }



    /**
     * 隐藏筛选条件
     */
    private void hideSearchTag() {
        iv_rent_sale.setBackgroundResource(R.mipmap.ic_arrow_down_grey_sloid);
        iv_acreage.setBackgroundResource(R.mipmap.ic_arrow_down_grey_sloid);
        iv_house_type.setBackgroundResource(R.mipmap.ic_arrow_down_grey_sloid);
        iv_order_type.setBackgroundResource(R.mipmap.ic_rent_order_black);
        if (edt_low_price!=null){
            hideSoftInput(edt_low_price.getWindowToken());
        }
    }
    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onGetSearchTagList(int status, String msg, int bean_type, List<HouseRentTagListBean> datas) {
        hideDialog(smallDialog);
        if (status==1){
            if (bean_type==0){
                if (datas!=null){
                    for (int i = 0; i < datas.size(); i++) {
                        datas.get(i).setBean_type(bean_type);
                    }
                    mDatas_rent_sale.clear();
                    mDatas_rent_sale.addAll(datas);
                    inflateTagListAndShow(mDatas_rent_sale,0);
                }
            }else if (bean_type==1){
                if (datas!=null){
                    for (int i = 0; i < datas.size(); i++) {
                        datas.get(i).setBean_type(bean_type);
                    }
                    mDatas_acreage.clear();
                    mDatas_acreage.addAll(datas);
                    inflateTagListAndShow(mDatas_acreage,1);
                }
            }else if (bean_type==2){
                if (datas!=null){
                    for (int i = 0; i < datas.size(); i++) {
                        datas.get(i).setBean_type(bean_type);
                    }
                    mDatas_house_type.clear();
                    mDatas_house_type.addAll(datas);
                    inflateTagListAndShow(mDatas_house_type,2);
                }
            }else if (bean_type==3){
                for (int i = 0; i < datas.size(); i++) {
                    datas.get(i).setBean_type(bean_type);
                }
                if (datas!=null){
                    mDatas_order_type.clear();
                    mDatas_order_type.addAll(datas);
                    inflateTagListAndShow(mDatas_order_type,3);
                }
            }
        }else {
            SmartToast.showInfo(msg);
        }
    }

    @Override
    public void onGetHouseList(int status, String msg, int page,int CountPage,  List<HouseRentDetail> datas) {
        hideDialog(smallDialog);
        refreshLayout.setRefreshing(false);
        listView.setIsLoading(false);
        if (status==1){
            if (datas!=null&&datas.size()>0){
                rel_no_data.setVisibility(View.GONE);
                if (page==1){
                    mDatas.clear();
                }
                mDatas.addAll(datas);
                this.page++;
                if ( this.page>CountPage){
                    listView.setHasMoreItems(false);
                }else {
                    listView.setHasMoreItems(true);
                }
                houseRentListAdapter.notifyDataSetChanged();
            }else {
                if (page==1){
                    rel_no_data.setVisibility(View.VISIBLE);
                    mDatas.clear();
                    houseRentListAdapter.notifyDataSetChanged();
                }
                listView.setHasMoreItems(false);
            }
        }else {
            SmartToast.showInfo(msg);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (popupWindow!=null){
            popupWindow.dismiss();
        }
    }
}

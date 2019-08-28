package com.huacheng.huiservers.ui.index.oldservice;

import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.MedicineNoticeDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelOldDrugList;
import com.huacheng.huiservers.model.ModelOldIndexTop;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Description:
 * created by wangxiaotao
 * 2019/8/13 0013 下午 6:31
 */
public class CalendarViewActivity extends BaseActivity implements CalendarView.OnCalendarSelectListener, CalendarView.OnYearChangeListener, CalendarView.OnMonthChangeListener {

    private TextView tv_month;
    private TextView tv_year;
    private CalendarLayout calendarlayout;
    private CalendarView calendarView;
    private int mYear;
    private FrameLayout fl_pre;
    private FrameLayout fl_next;
    private NestedScrollView nestedScrollView;
    private ListView listview;
    private CommonAdapter<ModelOldDrugList> adapter ;
    private List<ModelOldDrugList> mDatas = new ArrayList<>();

    private ModelOldIndexTop modelOldIndexTop;

    @Override
    protected void initView() {
        //CalendarView   mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        findTitleViews();
        titleName.setText("用药提醒");
        tv_year = findViewById(R.id.tv_year);
        tv_month = findViewById(R.id.tv_month);
        fl_pre = findViewById(R.id.fl_pre);
        fl_next = findViewById(R.id.fl_next);
        calendarlayout = findViewById(R.id.calendarlayout);
        calendarView = findViewById(R.id.calendarView);
        mYear = calendarView.getCurYear();
        tv_year.setText(calendarView.getCurYear()+"年");
        tv_month.setText(calendarView.getCurMonth()+"月");
        listview = findViewById(R.id.listview);
        adapter= new CommonAdapter<ModelOldDrugList>(this,R.layout.item_medcine_notice,mDatas) {
            @Override
            protected void convert(ViewHolder viewHolder, ModelOldDrugList item, int position) {
                viewHolder.<TextView>getView(R.id.tv_time).setText(item.getEatime()+"");
            }
        };
        listview.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        if (modelOldIndexTop==null){
            return;
        }
        //请求当天的数据
        request(getDateFormat(System.currentTimeMillis()));
    }

    @Override
    protected void initListener() {
        tv_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!calendarlayout.isExpand()) {
                    calendarlayout.expand();
                    return;
                }
                calendarView.showYearSelectLayout(mYear);
                tv_year.setVisibility(View.VISIBLE);
                tv_month.setVisibility(View.GONE);
            }
        });
        calendarView.setOnCalendarSelectListener(this);
        calendarView.setOnYearChangeListener(this);
        calendarView.setOnMonthChangeListener(this);
        fl_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calendarView!=null) {
                    calendarView.scrollToPre();
                }
            }
        });
        fl_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calendarView!=null) {
                    calendarView.scrollToNext();
                }
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ModelOldDrugList modelOldDrugList = mDatas.get(position);
                new MedicineNoticeDialog(mContext,modelOldDrugList).show();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_calendar_view;
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

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        tv_year.setVisibility(View.VISIBLE);
        tv_month.setVisibility(View.VISIBLE);
        tv_year.setText(calendar.getYear()+"年");
        tv_month.setText(calendar.getMonth()+"月");
        mYear = calendar.getYear();

        if (modelOldIndexTop==null){
            return;
        }
        request(getDateFormat(calendar.getTimeInMillis()));
    }

    @Override
    public void onYearChange(int year) {
        tv_year.setVisibility(View.VISIBLE);
        tv_month.setVisibility(View.VISIBLE);
        tv_year.setText(year+"年");
        mYear = year;
    }

    @Override
    public void onMonthChange(int year, int month) {
        tv_year.setVisibility(View.VISIBLE);
        tv_month.setVisibility(View.VISIBLE);
        tv_year.setText(year+"年");
        tv_month.setText(month+"月");
        mYear = year;
    }

    /**
     * 请求数据
     */
    private void request(String time) {

        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("time",time+"");
        params.put("p_id",modelOldIndexTop.getOld_id()+"");
        params.put("o_company_id",modelOldIndexTop.getO_company_id()+"");
        MyOkHttp.get().post(ApiHttpClient.PENSION_DRUG_LIST, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    List <ModelOldDrugList>data = JsonUtil.getInstance().getDataArrayByName(response, "data", ModelOldDrugList.class);
                    if (data!=null&&data.size()>0){
                        mDatas.clear();
                        mDatas.addAll(data);
                        adapter.notifyDataSetChanged();
                    }else {
                        mDatas.clear();
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                    SmartToast.showInfo(msg);
                    mDatas.clear();
                    adapter.notifyDataSetChanged();
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
     * 获取格式
     * @param timeMillis
     * @return
     */
    private String getDateFormat(long timeMillis) {
        SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(timeMillis);
        return date+"";
    }
}

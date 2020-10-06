package com.huacheng.huiservers.ui.index.oldservice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelOldFootmark;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.index.oldservice.adapter.AdapterMyTrak;
import com.huacheng.huiservers.utils.json.JsonUtil;
import com.huacheng.huiservers.view.MyListView;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 类描述：查看足迹
 * 时间：2020/9/30 17:12
 * created by DFF
 */
public class MyTrackActivity extends BaseActivity implements CalendarView.OnCalendarSelectListener, CalendarView.OnYearChangeListener, CalendarView.OnMonthChangeListener {
    private String date;
    private ModelOldFootmark mOldFootmark;
    private List<ModelOldFootmark.PosBean> mDatas = new ArrayList<>();
    private AdapterMyTrak mAdapterMyTrak;
    MyListView mMyListView;
    private TextView tv_data;
    private TextView tv_city_num;
    private TextView tv_zuji_num;
    private String par_uid = "";
    private LinearLayout ly_select_time;
    private PopupWindow popupWindow;
    private RelativeLayout rel_no_data;
    CalendarLayout calendarlayout;
    CalendarView calendarView;
    TextView tv_year;
    TextView tv_month;
    int mYear;
    private Calendar calendar_selected;


    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("我的足迹");

        tv_data = findViewById(R.id.tv_data);
        tv_city_num = findViewById(R.id.tv_city_num);
        tv_zuji_num = findViewById(R.id.tv_zuji_num);
        mMyListView = findViewById(R.id.listview);
        ly_select_time = findViewById(R.id.ly_select_time);
        rel_no_data = findViewById(R.id.rel_no_data);

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        date = sdf.format(System.currentTimeMillis());
        tv_data.setText("您" + date+ "跨越了");

        mAdapterMyTrak = new AdapterMyTrak(this, R.layout.item_my_track, mDatas);
        mMyListView.setAdapter(mAdapterMyTrak);

    }

    @Override
    protected void initData() {
        requestData(getDateFormatCommit(System.currentTimeMillis()));
    }

    private void requestData(String date) {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("par_uid", par_uid);
        params.put("date", date + "");

        MyOkHttp.get().post(ApiHttpClient.DEVICE_ZUJI, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    mOldFootmark = (ModelOldFootmark) JsonUtil.getInstance().parseJsonFromResponse(response, ModelOldFootmark.class);
                    if (mOldFootmark != null) {
                        tv_city_num.setText(mOldFootmark.getCityNum() + "");
                        tv_zuji_num.setText(mOldFootmark.getPosNum() + "");
                        if (mOldFootmark.getPos() != null && mOldFootmark.getPos().size() > 0) {
                            rel_no_data.setVisibility(View.GONE);
                            mDatas.clear();
                            mDatas.addAll(mOldFootmark.getPos());
                            mAdapterMyTrak.notifyDataSetChanged();
                        }else {
                            rel_no_data.setVisibility(View.VISIBLE);
                            mDatas.clear();
                            mAdapterMyTrak.notifyDataSetChanged();
                        }
                    }
                } else {
                    SmartToast.showInfo(JsonUtil.getInstance().getMsgFromResponse(response, "获取数据失败"));
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });
    }


    @Override
    protected void initListener() {
        ly_select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setpop();
            }
        });

    }
    private void setpop() {

        View contentView = LayoutInflater.from(this).inflate(R.layout.layout_calendar_view, null);

        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,  ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setContentView(contentView);

        FrameLayout fl_pre = contentView.findViewById(R.id.fl_pre);
        FrameLayout fl_next = contentView.findViewById(R.id.fl_next);
        tv_year = contentView.findViewById(R.id.tv_year);
        tv_month = contentView.findViewById(R.id.tv_month);
        calendarlayout = contentView.findViewById(R.id.calendarlayout);
        calendarView = contentView.findViewById(R.id.calendarView);
        if (calendar_selected!=null){
            calendarView.addSchemeDate(calendar_selected);
        }

        mYear = calendarView.getCurYear();
        tv_year.setText(calendarView.getCurYear() + "年");
        tv_month.setText(calendarView.getCurMonth() + "月");

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
                if (calendarView != null) {
                    calendarView.scrollToPre();
                }
            }
        });
        fl_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calendarView != null) {
                    calendarView.scrollToNext();
                }
            }
        });
        popupWindow.showAsDropDown(ly_select_time);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_my_track;
    }

    @Override
    protected void initIntentData() {
        par_uid = getIntent().getStringExtra("par_uid");

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
        popupWindow.dismiss();
        tv_year.setVisibility(View.VISIBLE);
        tv_month.setVisibility(View.VISIBLE);
        tv_year.setText(calendar.getYear() + "年");
        tv_month.setText(calendar.getMonth() + "月");
        mYear = calendar.getYear();

        tv_data.setText("您" +calendar.getMonth()+"-"+calendar.getDay()+ "跨越了");
        requestData(getDateFormatCommit(calendar.getTimeInMillis()));

  //      calendar_selected=calendar;
    }

    @Override
    public void onYearChange(int year) {
        tv_year.setVisibility(View.VISIBLE);
        tv_month.setVisibility(View.VISIBLE);
        tv_year.setText(year + "年");
        mYear = year;
    }

    @Override
    public void onMonthChange(int year, int month) {
        tv_year.setVisibility(View.VISIBLE);
        tv_month.setVisibility(View.VISIBLE);
        tv_year.setText(year + "年");
        tv_month.setText(month + "月");
        mYear = year;
    }

    /**
     * 获取格式
     *
     * @param timeMillis
     * @return
     */
    private String getDateFormatCommit(long timeMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(timeMillis);
        return date + "";
    }
}

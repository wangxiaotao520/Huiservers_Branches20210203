package com.huacheng.huiservers.ui.index.oldservice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.huacheng.huiservers.utils.json.JsonUtil;
import com.huacheng.libraryservice.utils.NullUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * 类描述：健康计步
 * 时间：2020/10/3 15:07
 * created by DFF
 */
public class OldMyStepActivity extends BaseActivity implements CalendarView.OnCalendarSelectListener, CalendarView.OnYearChangeListener, CalendarView.OnMonthChangeListener {
    private LinearLayout ly_select_time;
    private TextView tv_data;
    private TextView tv_step_num;
    private String date;
    private ModelOldFootmark mOldFootmark;
    private String par_uid = "";
    private PopupWindow popupWindow;
    CalendarLayout calendarlayout;
    CalendarView calendarView;
    TextView tv_year;
    TextView tv_month;
    int mYear;
    private int jump_type = 0;

    @Override
    protected void initView() {

        findTitleViews();
        titleName.setText("健康计步");

        tv_data = findViewById(R.id.tv_data);
        ly_select_time = findViewById(R.id.ly_select_time);
        tv_step_num = findViewById(R.id.tv_step_num);

        //默认获取当天的日期
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        date = sdf.format(System.currentTimeMillis());
        tv_data.setText(date+ "步数");
    }

    @Override
    protected void initData() {
        requestData(getDateFormatCommit(System.currentTimeMillis()));
    }

    private void requestData(String date) {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        String url = "";
        if (!NullUtil.isStringEmpty(par_uid)){
            params.put("par_uid", par_uid);
        }
        if (jump_type==0){
            url=ApiHttpClient.STEP_INFO;
        }else {
            url=ApiHttpClient.STEP_INFO1;
        }
        params.put("date", date);

        MyOkHttp.get().post(url, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    mOldFootmark = (ModelOldFootmark) JsonUtil.getInstance().parseJsonFromResponse(response, ModelOldFootmark.class);
                    if (mOldFootmark != null) {
                        tv_step_num.setText(mOldFootmark.getS());

                    }
                } else {
                    try {
                        SmartToast.showInfo(response.getString("msg"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
        return R.layout.layout_my_step;
    }

    @Override
    protected void initIntentData() {
        par_uid = getIntent().getStringExtra("par_uid");
        jump_type=getIntent().getIntExtra("jump_type",0);
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

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

        tv_data.setText(calendar.getMonth()+"-"+calendar.getDay()+ "步数");
        requestData(getDateFormatCommit(calendar.getTimeInMillis()));

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

}

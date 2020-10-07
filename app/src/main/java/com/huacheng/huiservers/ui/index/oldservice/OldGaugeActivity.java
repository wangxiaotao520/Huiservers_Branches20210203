package com.huacheng.huiservers.ui.index.oldservice;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.huacheng.huiservers.ui.index.oldservice.adapter.AdapterGauge;
import com.huacheng.huiservers.utils.json.JsonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 类描述：远程测心率/血压/云测温
 * 时间：2020/10/4 10:27
 * created by DFF
 */
public class OldGaugeActivity extends BaseActivity implements CalendarView.OnCalendarSelectListener, CalendarView.OnYearChangeListener, CalendarView.OnMonthChangeListener{

    private int type = 0;//1心率 2血压 3 云测温
    View headerView;
    private ListView listview;
    private SmartRefreshLayout refreshLayout;
    private ImageView iv_type;
    private TextView tv_data;
    private TextView tv_type_name;
    private TextView tv_type_unit;
    private TextView tv_type_xinlv;
    private TextView tv_type_content;
    private TextView tv_type_btn;
    private LinearLayout ly_select_time;
    private String date;
    private ModelOldFootmark mOldFootmark;
    private String str_url = "";
    private String par_uid = "";
    private List<ModelOldFootmark> mDatas = new ArrayList<>();
    private AdapterGauge mAdapterGauge;
    private PopupWindow popupWindow;
    CalendarLayout calendarlayout;
    CalendarView calendarView;
    TextView tv_year;
    TextView tv_month;
    int mYear;

    @Override
    protected void initView() {
        findTitleViews();

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        date = sdf.format(System.currentTimeMillis());

        listview = findViewById(R.id.listview);
        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(false);
        initHeaderView();

        if (type == 1) {
            titleName.setText("远程测心率");
        } else if (type == 2) {
            titleName.setText("远程测血压");
        } else {
            titleName.setText("云测温");
        }
        mAdapterGauge = new AdapterGauge(this, R.layout.item_old_gauge, mDatas, type);
        listview.setAdapter(mAdapterGauge);

    }

    private void initHeaderView() {
        headerView = LayoutInflater.from(this).inflate(R.layout.layout_gauge_header, null);
        iv_type = headerView.findViewById(R.id.iv_type);
        tv_data = headerView.findViewById(R.id.tv_data);
        ly_select_time = headerView.findViewById(R.id.ly_select_time);
        tv_type_name = headerView.findViewById(R.id.tv_type_name);
        tv_type_unit = headerView.findViewById(R.id.tv_type_unit);
        tv_type_xinlv = headerView.findViewById(R.id.tv_type_xinlv);
        tv_type_content = headerView.findViewById(R.id.tv_type_content);
        tv_type_btn = headerView.findViewById(R.id.tv_type_btn);
        if (type == 1) {
            tv_type_unit.setText("次/分");
            tv_type_xinlv.setVisibility(View.VISIBLE);
            iv_type.setBackgroundResource(R.mipmap.bg_heart_rate);
            tv_data.setText(date+ "心率");
        } else if (type == 2) {
            tv_type_unit.setText("mmHg");
            tv_type_content.setVisibility(View.VISIBLE);
            tv_type_content.setText("正常值：90~140/60~90mmHg(收缩压/舒张压）");
            iv_type.setBackgroundResource(R.mipmap.bg_cxy);
            tv_data.setText(date+ "血压");
        } else {
            tv_type_unit.setText("℃");
            tv_type_content.setVisibility(View.VISIBLE);
            tv_type_content.setText("所测温度为被测物体表面温度，仅供参考，佩戴不正确或外部环境偏差均会影响数据准确性。");
            iv_type.setBackgroundResource(R.mipmap.bg_ycw);
            tv_data.setText(date+ "体温");
        }
        listview.addHeaderView(headerView);
    }

    @Override
    protected void initData() {
        requestData(getDateFormatCommit(System.currentTimeMillis()));
    }

    private void requestData(String date) {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("par_uid", par_uid);
        params.put("date", date);

        if (type == 1) {
            str_url = ApiHttpClient.DEVICE_HEART;
        } else if (type == 2) {
            str_url = ApiHttpClient.DEVICE_BIOOD;
        } else {
            str_url = ApiHttpClient.DEVICE_WD;
        }
        MyOkHttp.get().post(str_url, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                refreshLayout.finishRefresh();
                if (JsonUtil.getInstance().isSuccess(response)) {
                    if (type == 1) {
                        List<ModelOldFootmark> data = JsonUtil.getInstance().getDataArrayByName(response, "data", ModelOldFootmark.class);
                        if (data != null && data.size() > 0) {
                            mDatas.clear();
                            mDatas.addAll(data);
                            mAdapterGauge.notifyDataSetChanged();
                            //默认第一条数据
                            tv_type_name.setText(mDatas.get(0).getH());

                        }
                    } else if (type == 2) {
                        List<ModelOldFootmark> data = JsonUtil.getInstance().getDataArrayByName(response, "data", ModelOldFootmark.class);
                        if (data != null && data.size() > 0) {
                            mDatas.clear();
                            mDatas.addAll(data);
                            mAdapterGauge.notifyDataSetChanged();
                            //默认第一条数据
                            tv_type_name.setText(mDatas.get(0).getH() + "/" + mDatas.get(0).getL());
                        }
                    } else {
                        mOldFootmark = (ModelOldFootmark) JsonUtil.getInstance().parseJsonFromResponse(response, ModelOldFootmark.class);
                        if (mOldFootmark != null) {
                            if (mOldFootmark.getWDInfo() != null && mOldFootmark.getWDInfo().size() > 0) {
                                mDatas.clear();
                                mDatas.addAll(mOldFootmark.getWDInfo());
                                mAdapterGauge.notifyDataSetChanged();

                                tv_type_name.setText(mOldFootmark.getWDInfo().get(0).getW());
                            }
                        }
                    }

                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                refreshLayout.finishRefresh();
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });
    }

    @Override
    protected void initListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                requestData(getDateFormatCommit(System.currentTimeMillis()));
            }
        });
        //立即测量
        tv_type_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit(type);
            }
        });
        //时间筛选
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
    /**
     * 立即测量
     */
    private void submit(int type) {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("par_uid", par_uid);

        if (type == 1) {
            str_url = ApiHttpClient.DEVICE_HEART_COMMIT;
        } else if (type == 2) {
            str_url = ApiHttpClient.DEVICE_BIOOD_COMMIT;
        } else {
            str_url = ApiHttpClient.DEVICE_WD_COMMIT;
        }
        MyOkHttp.get().post(str_url, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    try {
                        SmartToast.showInfo(response.getString("msg"));
                    } catch (JSONException e) {
                        e.printStackTrace();
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
    protected int getLayoutId() {
        return R.layout.layout_old_gauge;
    }

    @Override
    protected void initIntentData() {

        type = this.getIntent().getIntExtra("type", 0);
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

        if (type == 1) {
            tv_data.setText(calendar.getMonth()+"-"+calendar.getDay()+ "心率");
        } else if (type == 2) {
            tv_data.setText(calendar.getMonth()+"-"+calendar.getDay()+ "血压");
        } else {
            tv_data.setText(calendar.getMonth()+"-"+calendar.getDay()+ "体温");
        }
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

    private String getDateFormatCommit(long timeMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(timeMillis);
        return date + "";
    }
}

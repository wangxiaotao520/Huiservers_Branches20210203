package com.huacheng.huiservers.ui.index.oldservice;

import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.MedicineNoticeDialog;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
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
    private CommonAdapter<String> adapter ;
    private List<String> mDatas = new ArrayList<>();

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
        adapter= new CommonAdapter<String>(this,R.layout.item_medcine_notice,mDatas) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {

            }
        };
        listview.setAdapter(adapter);
    }

    @Override
    protected void initData() {

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
                new MedicineNoticeDialog(mContext).show();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_calendar_view;
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
        //TODO 测试
        for (int i = 0; i < 1; i++) {
            mDatas.add("");
        }
        adapter.notifyDataSetChanged();
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
}

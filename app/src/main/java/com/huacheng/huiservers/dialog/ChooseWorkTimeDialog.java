package com.huacheng.huiservers.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelWorkTime;
import com.huacheng.huiservers.ui.index.workorder_second.adapter.AdapterChooseWorkTime;
import com.huacheng.huiservers.ui.index.workorder_second.inter.OnChooseTimeListener;
import com.huacheng.huiservers.utils.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Description: 工单选择时间对话框
 * created by wangxiaotao
 * 2019/4/8 0008 下午 6:50
 */
public class ChooseWorkTimeDialog extends Dialog{

    private LinearLayout ll_date_container;
    private GridView gridview;
    private List<ModelWorkTime> date_list = new ArrayList<>();
    private List<ModelWorkTime> timeList = new ArrayList<>();
    private AdapterChooseWorkTime adapterChooseWorkTime;
    private OnChooseTimeListener listener;


    public ChooseWorkTimeDialog(@NonNull Context context,OnChooseTimeListener listener) {
        super(context, R.style.my_dialog_DimEnabled);
        this.listener=listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_choose_work_time);
        init();

        Window window = getWindow();
        // 设置显示动画
        //     window.setWindowAnimations(R.style.main_menu_animstyle);
        //此处可以设置dialog显示的位置
        WindowManager.LayoutParams params = window.getAttributes();
        // 设置对话框显示的位置
        params.gravity = Gravity.BOTTOM;

        params.width = params.MATCH_PARENT;
        //  params.width = params.WRAP_CONTENT;
        params.height = params.WRAP_CONTENT;
        window.setAttributes(params);
    }

    private void init() {
        ll_date_container = findViewById(R.id.ll_date_container);
        gridview = findViewById(R.id.gridview);
        for (int i = 0; i < 5; i++) {
            ModelWorkTime modelWorkTime = new ModelWorkTime();
            //设置每一个上方日期的时间戳
            modelWorkTime.setTimemills((System.currentTimeMillis()+24*60*60*1000*i));
            if (i==0){
                modelWorkTime.setIs_selected(true);
                modelWorkTime.setWeek("今天");
            }else {
                modelWorkTime.setWeek(getDayOfWeek(System.currentTimeMillis()+24*60*60*1000*i));
            }
            modelWorkTime.setDate(StringUtils.getDateToString((System.currentTimeMillis()+24*60*60*1000*i)/1000+"","9"));
            date_list.add(modelWorkTime);
        }
        for (int i = 0; i < date_list.size(); i++) {
            View view = View.inflate(this.getContext(), R.layout.item_choose_worktime, null);
            TextView tv_week = view.findViewById(R.id.tv_week);
            TextView tv_date = view.findViewById(R.id.tv_date);
            if (i==0){
                tv_week.setText("今天");
            }else {
                tv_week.setText(date_list.get(i).getWeek());
            }
            tv_date.setText(date_list.get(i).getDate());
            if (date_list.get(i).isIs_selected()){
                tv_week.setTextColor(getContext().getResources().getColor(R.color.orange));
                tv_date.setTextColor(getContext().getResources().getColor(R.color.orange));
            }else {
                tv_week.setTextColor(getContext().getResources().getColor(R.color.text_color));
                tv_date.setTextColor(getContext().getResources().getColor(R.color.text_color_hint));
            }
            view.setTag(i);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int  position = (int) v.getTag();
                    for (int i1 = 0; i1 < date_list.size(); i1++) {
                        date_list.get(i1).setIs_selected(false);
                    }
                    date_list.get(position).setIs_selected(true);
                    invalidate();
                    //回调
                    //上方日期
                    String result=StringUtils.getDateToString(( date_list.get(position).getTimemills()/1000)+"","2");
                    String week=date_list.get(position).getWeek()+"";
                    //下方时间
                    for (ModelWorkTime modelWorkTime : timeList) {
                        if (modelWorkTime.isIs_selected()){
                            result=result+" "+modelWorkTime.getTime();
                        }
                    }

                    if (listener!=null){
                        listener.onClickTime(result,result+"("+week+")");
                    }
                }
            });
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
            params.weight=1;
            ll_date_container.addView(view,params);
        }
        ModelWorkTime modelWorkTime = new ModelWorkTime("08:00");
        modelWorkTime.setIs_selected(true);
        timeList.add(modelWorkTime);
        timeList.add(new ModelWorkTime("09:00"));
        timeList.add(new ModelWorkTime("10:00"));
        timeList.add(new ModelWorkTime("11:00"));
        timeList.add(new ModelWorkTime("12:00"));
        timeList.add(new ModelWorkTime("14:00"));
        timeList.add(new ModelWorkTime("15:00"));
        timeList.add(new ModelWorkTime("16:00"));
        timeList.add(new ModelWorkTime("17:00"));
        timeList.add(new ModelWorkTime("18:00"));
        adapterChooseWorkTime = new AdapterChooseWorkTime(this.getContext(), R.layout.item_choose_worktime1, timeList);
        gridview.setAdapter(adapterChooseWorkTime);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < timeList.size(); i++) {
                    timeList.get(i).setIs_selected(false);
                }
                timeList.get(position).setIs_selected(true);
                adapterChooseWorkTime.notifyDataSetChanged();
                //回调处理
                String result="";
                String week="";
                //上方日期
                for (ModelWorkTime modelWorkTime : date_list) {
                    if (modelWorkTime.isIs_selected()){
                        result=StringUtils.getDateToString((modelWorkTime.getTimemills()/1000)+"","2");
                        week=modelWorkTime.getWeek()+"";
                    }
                }
                //下方时间
                result=result+" "+timeList.get(position).getTime();
                if (listener!=null){
                    listener.onClickTime(result,result+"("+week+")");
                }
                dismiss();
            }
        });
    }

    /**
     * 刷新日期
     * @param
     */
    private void invalidate() {
        for (int i = 0; i < ll_date_container.getChildCount(); i++) {
            View view_child = ll_date_container.getChildAt(i);
            TextView tv_week = view_child.findViewById(R.id.tv_week);
            TextView tv_date = view_child.findViewById(R.id.tv_date);
            if (i==0){
                tv_week.setText("今天");
            }else {
                tv_week.setText(date_list.get(i).getWeek());
            }
            tv_date.setText(date_list.get(i).getDate());
            if (date_list.get(i).isIs_selected()){
                tv_week.setTextColor(getContext().getResources().getColor(R.color.orange));
                tv_date.setTextColor(getContext().getResources().getColor(R.color.orange));
            }else {
                tv_week.setTextColor(getContext().getResources().getColor(R.color.text_color));
                tv_date.setTextColor(getContext().getResources().getColor(R.color.text_color_hint));
            }
        }
    }

    private String getDayOfWeek(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                return "周日";
            case Calendar.MONDAY:

                return "周一";
            case Calendar.TUESDAY:

                return "周二";
            case Calendar.WEDNESDAY:

                return "周三";
            case Calendar.THURSDAY:

                return "周四";
            case Calendar.FRIDAY:

                return "周五";
            case Calendar.SATURDAY:

                return "周六";
            default:
                return "今天";
        }
    }

    @Override
    public void show() {
        super.show();
        this.getWindow().setWindowAnimations(R.style.main_menu_animstyle);
    }
}

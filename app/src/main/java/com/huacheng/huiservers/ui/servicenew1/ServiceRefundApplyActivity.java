package com.huacheng.huiservers.ui.servicenew1;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.utils.NoDoubleClickListener;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description: 申请退款
 * created by wangxiaotao
 * 2020/6/18 0018 16:46
 */
public class ServiceRefundApplyActivity extends BaseActivity {
    @BindView(R.id.tv_service_name)
    TextView tvServiceName;
    @BindView(R.id.ly_service_name)
    LinearLayout lyServiceName;
    @BindView(R.id.tv_service_merchant)
    TextView tvServiceMerchant;
    @BindView(R.id.ly_service_merchant)
    LinearLayout lyServiceMerchant;
    @BindView(R.id.tv_service_num)
    TextView tvServiceNum;
    @BindView(R.id.ly_refund_num)
    LinearLayout lyRefundNum;
    @BindView(R.id.tv_refund_price)
    TextView tvRefundPrice;
    @BindView(R.id.ly_refund_price)
    LinearLayout lyRefundPrice;
    @BindView(R.id.tv_service_account)
    TextView tvServiceAccount;
    @BindView(R.id.ly_refund_account)
    LinearLayout lyRefundAccount;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout idFlowlayout;
    List<String> mListFlowLayout = new ArrayList<>();
    @BindView(R.id.tv_btn)
    TextView tvBtn;
    @BindView(R.id.fl_bottom)
    FrameLayout flBottom;

    private int seleted_position = -1;//选中的位置

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("申请退款");
        ButterKnife.bind(this);
        initFlowlayout();

    }

    private void initFlowlayout() {
        //测试
        mListFlowLayout.add("我的原因");
        mListFlowLayout.add("商家原因");
        mListFlowLayout.add("买多/买错/计划有变");
        mListFlowLayout.add("没预约不能用");
        mListFlowLayout.add("没预约不能用");
        mListFlowLayout.add("疫情期间，担心安全问题");
        mListFlowLayout.add("手动自定义添加");
        mListFlowLayout.add("其他原因");

        if (mListFlowLayout.size() > 0) {
            final LayoutInflater mInflater = LayoutInflater.from(mContext);
            TagAdapter adapter = new TagAdapter<String>(mListFlowLayout) {

                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv = (TextView) mInflater.inflate(R.layout.layout_refund_tag,
                            idFlowlayout, false);
                    tv.setText("" + mListFlowLayout.get(position));

                    return tv;
                }
            };
            //adapter.setSelectedList(list_selected_position);
            idFlowlayout.setAdapter(adapter);
            idFlowlayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                @Override
                public void onSelected(Set<Integer> selectPosSet) {

                    if (selectPosSet.isEmpty()) {

                    } else {
                        for (Integer integer : selectPosSet) {
                            seleted_position = integer;
                        }
                    }

                }
            });
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        tvBtn.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                SmartToast.showInfo(""+seleted_position);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_refund_apply;
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

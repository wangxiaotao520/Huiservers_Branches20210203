package com.huacheng.huiservers.ui.center;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.huacheng.huiservers.BaseApplication;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.db.UserSql;
import com.huacheng.huiservers.dialog.ImgDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelRegion;
import com.huacheng.huiservers.model.ModelUser;
import com.huacheng.huiservers.model.PersoninfoBean;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.utils.GetJsonDataUtil;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.huiservers.utils.ucrop.ImgCropUtil;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.UriUtils;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yalantis.ucrop.UCrop;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 类描述：个人基础信息
 * 时间：2020/11/24 16:13
 * created by DFF
 */
public class MyInfoEditActivity extends BaseActivity {

    @BindView(R.id.title_name)
    TextView mTitleName;
    /*@BindView(R.id.img_head_1)
    CircularImage mImgHead1;*/
    @BindView(R.id.rl_head)
    RelativeLayout mRlHead;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.rl_name)
    RelativeLayout mRlName;
    @BindView(R.id.tv_nickname)
    TextView mTvNickname;
    @BindView(R.id.rl_nickname)
    RelativeLayout mRlNickname;
    @BindView(R.id.tv_qianming)
    TextView mTvQianming;
    @BindView(R.id.rl_qianming)
    RelativeLayout mRlQianming;
    @BindView(R.id.tv_sex)
    TextView mTvSex;
    @BindView(R.id.rl_sex)
    RelativeLayout mRlSex;
    @BindView(R.id.tv_birthday)
    TextView mTvBirthday;
    @BindView(R.id.rl_birthday)
    RelativeLayout mRlBirthday;
    @BindView(R.id.tv_city)
    TextView mTvCity;
    @BindView(R.id.rl_city)
    RelativeLayout mRlCity;
    @BindView(R.id.tv_juzhu)
    TextView mTvJuzhu;
    @BindView(R.id.rl_juzhu)
    RelativeLayout mRlJuzhu;
    @BindView(R.id.scrollView)
    ScrollView mScrollView;
    @BindView(R.id.sdv_head)
    SimpleDraweeView mSdvHead;

    private ArrayList<ModelRegion> jsonBean;
    private ArrayList<String> options1Items = new ArrayList<>();//省
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();//市
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();//区
    private int selected_options1, selected_options2, selected_options3;//默认选中
    private String location_provice, location_district, location_city, location_code;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private Thread thread_parse_json;

    public static final int ACT_SELECT_PHOTO = 111;//选择图片
    private ImgDialog dialog;
    // private File file = new File(Environment.getExternalStorageDirectory() + "/Gphoto.png");

    private PersoninfoBean bean = new PersoninfoBean();
    private Intent intent;
    private RxPermissions rxPermission;
    private String head_url="";

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_SUCCESS:
                    hideDialog(smallDialog);
                    //   showCityWheel();
                    break;
                case MSG_LOAD_FAILED:
                    hideDialog(smallDialog);
                    SmartToast.showInfo("解析失败");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void initView() {
        findTitleViews();
        ButterKnife.bind(this);
        titleName.setText("基础信息");
        rxPermission = new RxPermissions(this);
    }

    @Override
    protected void initData() {
        //请求数据
        requestData();
        //解析json
        parseJson();
    }

    /**
     * 获取基础信息
     */
    private void requestData() {
        showDialog(smallDialog);
        Map<String, String> params = new HashMap<>();
        MyOkHttp.get().post(ApiHttpClient.MY_CENTER_INFO, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    bean = (PersoninfoBean) JsonUtil.getInstance().parseJsonFromResponse(response, PersoninfoBean.class);
                    if (bean != null) {
                        FrescoUtils.getInstance().setImageUri(mSdvHead, StringUtils.getImgUrl(bean.getAvatars()));
                        mTvName.setText(bean.getUid());
                        mTvNickname.setText(bean.getNickname());
                        mTvQianming.setText(bean.getSignature());
                        mTvSex.setText(bean.getSex_cn());
                        mTvBirthday.setText(bean.getBirthday());
                        mTvCity.setText(bean.getAddress());
                        mTvJuzhu.setText(bean.getHouse_status_cn());
//                        file = new File(Environment.getExternalStorageDirectory() + "/Gphoto.png");
//                        if (file.exists()) {
//                            file.delete();
//                        }
                    }
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

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.my_info;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
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

    @OnClick({R.id.sdv_head, R.id.rl_head, R.id.rl_name, R.id.rl_nickname, R.id.rl_qianming, R.id.rl_sex, R.id.rl_birthday, R.id.rl_city, R.id.rl_juzhu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sdv_head:
            case R.id.rl_head:
                //修改头像
                dialog = new ImgDialog(MyInfoEditActivity.this, new ImgDialog.OnCustomDialogListener() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void back(String name) {
                        if (name.equals("1")) {// 拍照
                            rxPermission.request(
                                    Manifest.permission.CAMERA)
                                    .subscribe(new Consumer<Boolean>() {
                                        @Override
                                        public void accept(Boolean isGranted) throws Exception {
                                            if (isGranted) {
                                                ImgCropUtil.openCamera(MyInfoEditActivity.this);
                                            } else {
                                                SmartToast.showInfo("未打开摄像头权限");
                                            }
                                        }
                                    });
                        } else if (name.equals("2")) {// 获取相册
                            rxPermission.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE)
                                    .subscribe(new Consumer<Boolean>() {
                                        @Override
                                        public void accept(Boolean isGranted) throws Exception {
                                            if (isGranted) {
                                                jumpToImageSelector();
                                            } else {
                                                SmartToast.showInfo("未打开摄像头权限");
                                            }
                                        }
                                    });
                        } else if (name.equals("3")) {// 取消

                        }
                    }
                });
                dialog.show();
                break;
            case R.id.rl_name:

                break;
            case R.id.rl_nickname:
                //昵称
                intent = new Intent(this, NamenickVerfityActivity.class);
                intent.putExtra("nickname", mTvNickname.getText().toString());
                intent.putExtra("tv_sex", mTvSex.getText().toString());
                startActivityForResult(intent, 22);

                break;
            case R.id.rl_qianming:
                //签名
                intent = new Intent(this, MyAutographActivity.class);
                intent.putExtra("sign", bean.getSignature());
                startActivity(intent);
                break;
            case R.id.rl_sex:
                //性别
                intent = new Intent(this, SexVerfityActivity.class);
                intent.putExtra("type_value", bean.getSex());
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
            case R.id.rl_birthday:
                //生日
                TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        if (date != null) {
                            long time = date.getTime();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                            String format = sdf.format(time);
                            getMyinfoBirthDay(format);
                        }
                    }
                }).setSubmitColor(this.getResources().getColor(R.color.orange))//确定按钮文字颜色
                        .setCancelColor(this.getResources().getColor(R.color.text_color_hint)).build();
                String birthDay = mTvBirthday.getText().toString();
                if (NullUtil.isStringEmpty(birthDay)) {
                    Calendar instance = Calendar.getInstance();
                    pvTime.setDate(instance);
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date date = sdf.parse(birthDay);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        pvTime.setDate(calendar);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                pvTime.show();
                break;
            case R.id.rl_city:
                //地区
                if (options1Items.size() > 0) {
                    showCityWheel();
                }
                break;
            case R.id.rl_juzhu:
                //居住状态
                intent = new Intent(this, SexVerfityActivity.class);
                intent.putExtra("type_value", bean.getHouse_status());
                intent.putExtra("type", 2);
                startActivity(intent);
                break;
        }
    }

    /**
     * 显示三级城市联动
     */
    private void showCityWheel() {
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                selected_options1 = options1;
                selected_options2 = option2;
                selected_options3 = options3;
                if (jsonBean != null && jsonBean.size() > 0) {
                    if (jsonBean.get(options1).getS_list().get(option2).getSs_list().size() == 0) {
                        return;
                    }
//                    mTvCity.setText( "" +
//                            jsonBean.get(options1).getS_list().get(option2).getSs_list().get(options3).getRegion_name());

                    location_provice = jsonBean.get(options1).getRegion_name();
                    location_city = jsonBean.get(options1).getS_list().get(option2).getRegion_name();
                    location_district = jsonBean.get(options1).getS_list().get(option2).getSs_list().get(options3).getRegion_name();

                    mTvCity.setText(location_provice + " " + location_city + " " + location_district);
                    String id = jsonBean.get(options1).getRegion_id() + "," + jsonBean.get(options1).getS_list().get(option2).getRegion_id() + "," +
                            jsonBean.get(options1).getS_list().get(option2).getSs_list().get(options3).getRegion_id();
                    getMyCity(id);
                }
            }
        }).setSubmitColor(this.getResources().getColor(R.color.orange))//确定按钮文字颜色
                .setCancelColor(this.getResources().getColor(R.color.text_color_hint))
                .build();//取消按钮文字颜色;
        pvOptions.setPicker(options1Items, options2Items, options3Items);
        pvOptions.setSelectOptions(selected_options1, selected_options2, selected_options3);
        pvOptions.show();

    }

    private void jumpToImageSelector() {
        Intent imageIntent = new Intent(this, MultiImageSelectorActivity.class);
        // 是否显示相机
        imageIntent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, false);

        // 单选多选 (MultiImageSelectorActivity.MODE_SINGLE OR MultiImageSelectorActivity.MODE_SINGLE)
        imageIntent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
        // 默认选择

        startActivityForResult(imageIntent, ACT_SELECT_PHOTO);
    }

    /**
     * 解析cityjson
     */
    private void parseJson() {
        showDialog(smallDialog);
        if (thread_parse_json == null) {
            thread_parse_json = new Thread(new Runnable() {
                @Override
                public void run() {
                    // 子线程中解析省市区数据
                    initJsonData();
                }
            });
        }
        thread_parse_json.start();
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "region.json");//获取assets目录下的json文件数据

        //用Gson 转成实体
        jsonBean = parseData(JsonData);

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        for (int i = 0; i < jsonBean.size(); i++) {
            options1Items.add(jsonBean.get(i).getRegion_name());
            if (jsonBean.get(i).getRegion_name().equals(location_provice)) {
                selected_options1 = i;
            }
        }
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getS_list().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getS_list().get(c).getRegion_name();
                CityList.add(CityName);//添加城市
                //默认选中
                if (CityName.equals(location_city)) {
                    selected_options2 = c;
                }
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getS_list().get(c).getSs_list() == null
                        || jsonBean.get(i).getS_list().get(c).getSs_list().size() == 0) {
                    City_AreaList.add("");
                } else {
                    for (int i1 = 0; i1 < jsonBean.get(i).getS_list().get(c).getSs_list().size(); i1++) {
                        City_AreaList.add(jsonBean.get(i).getS_list().get(c).getSs_list().get(i1).getRegion_name());
                        if (jsonBean.get(i).getS_list().get(c).getSs_list().get(i1).getRegion_name().equals(location_district)) {
                            selected_options3 = i1;
                        }
                    }

                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);
    }

    public ArrayList<ModelRegion> parseData(String result) {//Gson 解析
        ArrayList<ModelRegion> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                ModelRegion entity = gson.fromJson(data.optJSONObject(i).toString(), ModelRegion.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }


    /**
     * 修改个人信息生日
     *
     * @param param
     */
    private void getMyinfoBirthDay(String param) {
        showDialog(smallDialog);
        Map<String, String> params = new HashMap<>();
        params.put("type", "setBirthday");
        params.put("value", param);
        MyOkHttp.get().post(ApiHttpClient.MY_EDIT_CENTER, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    requestData();
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
     * 修改个人信息地区
     *
     * @param param
     */
    private void getMyCity(String param) {
        showDialog(smallDialog);
        Map<String, String> params = new HashMap<>();
        params.put("type", "setAddress");
        params.put("value", param);
        MyOkHttp.get().post(ApiHttpClient.MY_EDIT_CENTER, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    requestData();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ImgCropUtil.TAKE_PHOTO://相机返回
                    //相机返回图片，调用裁剪的方法
                    ImgCropUtil.startUCrop(this, ImgCropUtil.imageUri, 1, 1);
                    break;
                case ACT_SELECT_PHOTO://选择相册返回
                    if (data != null) {
                        ArrayList<String> backList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                        Uri uri = UriUtils.pathToUri(this, backList.get(0));
                        ImgCropUtil.startUCrop(this, uri, 1, 1);
                    }
                    break;

                case UCrop.REQUEST_CROP://裁剪返回
                    Uri resultUri = UCrop.getOutput(data);
                    // 压缩头像
                    compressImage(resultUri);
                    break;

                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 压缩图片
     */
    private void compressImage(Uri uri_path) {
        head_url = uri_path.getPath();
        Luban.with(this)
                .load(uri_path)
                .ignoreBy(100)
                .setTargetDir(getPath())
                .setFocusAlpha(false)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(final File file) {
                        //完成了
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                uploadCameraAvatar(file);
                            }
                        });

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SmartToast.showInfo("图片压缩失败");
                                hideDialog(smallDialog);
                            }
                        });

                    }
                }).launch();
    }

    private String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/huiservers/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    private void uploadCameraAvatar(File file) {
        showDialog(smallDialog);
        HashMap<String, File> params_file = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        params.put("type", "setAvatars");
        params_file.put("value", file);
        MyOkHttp.get().upload(this, ApiHttpClient.MY_EDIT_CENTER, params, params_file, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "提交成功");
                    SmartToast.showInfo(msg);
                    requestData();

                    //更改昵称更新数据库
                    ModelUser modelUser = BaseApplication.getUser();
                    modelUser.setAvatars(head_url);
                    UserSql.getInstance().updateObject(modelUser);
                    EventBus.getDefault().post(new PersoninfoBean());

//                    //删除缓存文件夹中的图片
                    ImgCropUtil.deleteCacheFile(new File(ImgCropUtil.getCacheDir()));
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "提交失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }

            @Override
            public void onProgress(long currentBytes, long totalBytes) {
                super.onProgress(currentBytes, totalBytes);
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(PersoninfoBean bean) {
        //1.绑定房屋后刷新
        //2.更换个人信息后刷新
        requestData();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top
                    && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
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
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();


    }

}

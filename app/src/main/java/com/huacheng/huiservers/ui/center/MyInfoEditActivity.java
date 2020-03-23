package com.huacheng.huiservers.ui.center;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.ImgDialog;
import com.huacheng.huiservers.http.HttpHelper;
import com.huacheng.huiservers.http.MyCookieStore;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.RequestParams;
import com.huacheng.huiservers.http.okhttp.response.RawResponseHandler;
import com.huacheng.huiservers.model.PersoninfoBean;
import com.huacheng.huiservers.model.protocol.CenterProtocol;
import com.huacheng.huiservers.model.protocol.ShopProtocol;
import com.huacheng.huiservers.ui.base.BaseActivityOld;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.huiservers.utils.ucrop.ImgCropUtil;
import com.huacheng.huiservers.view.CircularImage;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.UriUtils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yalantis.ucrop.UCrop;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import io.reactivex.functions.Consumer;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 个人信息编辑页面
 */
public class MyInfoEditActivity extends BaseActivityOld implements OnClickListener {


    private static final int PICK_CAMERA = 4;
    private LinearLayout lin_left;
    private RelativeLayout rl_name, rl_nickname, rl_sex, rl_birthday, rl_changepwd;
    private TextView title_name, tv_name, tv_nickname, tv_nickname1, tv_sex, tv_birthday;
    private ImgDialog dialog;
    private CircularImage img_head_1;
    private File file = new File(Environment.getExternalStorageDirectory() + "/Gphoto.png");
    private BitmapUtils bitmapUtils;

    private CenterProtocol cprotocol = new CenterProtocol();
    private PersoninfoBean bean = new PersoninfoBean();
    private String strData;
    private String result;
    private Intent intent;
    private Bundle bundle = new Bundle();
    private ScrollView scrollView;
    private RxPermissions rxPermission;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        super.init();
        setContentView(R.layout.my_info);
        MyCookieStore.My_info = 0;
        //    SetTransStatus.GetStatus(this);// 系统栏默认为黑色
        scrollView =  findViewById(R.id.scrollView);
        lin_left = (LinearLayout) findViewById(R.id.lin_left);
        title_name = (TextView) findViewById(R.id.title_name);
        img_head_1 = (CircularImage) findViewById(R.id.img_head_1);
        rl_name = (RelativeLayout) findViewById(R.id.rl_name);
        rl_nickname = (RelativeLayout) findViewById(R.id.rl_nickname);
        rl_sex = (RelativeLayout) findViewById(R.id.rl_sex);
        rl_birthday = (RelativeLayout) findViewById(R.id.rl_birthday);
        rl_changepwd = (RelativeLayout) findViewById(R.id.rl_changepwd);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_nickname = (TextView) findViewById(R.id.tv_nickname);
        tv_nickname1 = (TextView) findViewById(R.id.tv_nickname1);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_birthday = (TextView) findViewById(R.id.tv_birthday);
        //
        title_name.setText("个人信息");
        lin_left.setOnClickListener(this);
        img_head_1.setOnClickListener(this);
        rl_name.setOnClickListener(this);
        rl_nickname.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
        rl_birthday.setOnClickListener(this);
        rl_changepwd.setOnClickListener(this);
        //请求数据
        getinfo();
        rxPermission = new RxPermissions(this);
    }

    public static final int ACT_SELECT_PHOTO = 111;//选择图片

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.lin_left:
                finish();
                break;
            case R.id.img_head_1:// 修改头像
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
            case R.id.rl_nickname:
                intent = new Intent(this, NamenickVerfityActivity.class);
                bundle.putString("nickname", tv_nickname.getText().toString());
                bundle.putString("tv_sex", tv_sex.getText().toString());
                intent.putExtras(bundle);
                startActivityForResult(intent, 22);
                break;
            case R.id.rl_sex:
                intent = new Intent(this, SexVerfityActivity.class);
                bundle.putString("sex", tv_sex.getText().toString());
                intent.putExtras(bundle);
                startActivityForResult(intent, 3);
                break;
            case R.id.rl_birthday:
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
                String birthDay = tv_birthday.getText().toString();
                if (NullUtil.isStringEmpty(birthDay)) {
                    Calendar instance = Calendar.getInstance();
                    pvTime.setDate(instance);
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
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
            case R.id.rl_changepwd:
                intent = new Intent(this, ChangePwdVerifyActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
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
     * 修改个人信息
     *
     * @param param
     */
    private void getMyinfoBirthDay(final String param) {
        showDialog(smallDialog);
        Url_info info = new Url_info(this);

        RequestParams params = new RequestParams();
        params.addBodyParameter("birthday", param);

        MyOkHttp.get().post(info.edit_center, params.getParams(), new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                ShopProtocol protocol = new ShopProtocol();
                String str = protocol.setShop(response);
                if ("1".equals(str)) {
                    getinfo();
                } else {
                    hideDialog(smallDialog);
                    SmartToast.showInfo(str);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });

    }

    public void data() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        strData = formatter.format(curDate);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //  getinfo();
    }

    private void getinfo() {// 个人信息
        showDialog(smallDialog);
        Url_info info = new Url_info(this);
        RequestParams params = new RequestParams();
        new HttpHelper(info.get_person_index, params, MyInfoEditActivity.this) {

            @Override
            protected void setData(String json) {
                hideDialog(smallDialog);
                bean = cprotocol.getinfo3(json);
                scrollView.setVisibility(View.VISIBLE);
                //获取头像
                if (bean.getAvatars().equals("null")) {
                } else {
                    bitmapUtils = new BitmapUtils(MyInfoEditActivity.this);
                    bitmapUtils.display(img_head_1, StringUtils.getImgUrl(bean.getAvatars()));
                }
                file = new File(Environment.getExternalStorageDirectory() + "/Gphoto.png");
                if (file.exists()) {
                    file.delete();
                }
                //姓名
                if (bean.getFullname().equals("null")) {
                    tv_name.setHint("请填写姓名");
                } else {
                    tv_name.setText(bean.getFullname());
                }
                //昵称
                if (bean.getNickname().equals("null")) {
                    tv_nickname.setHint("请填写昵称");
                } else {
                    tv_nickname.setText(bean.getNickname());
                    // tv_nickname1.setText(bean.getNickname());
                }

                //获取性别
                String sex_str = bean.getSex();
                if (sex_str.equals("null")) {
                    tv_sex.setHint("请选择性别");
                } else {
                    if (sex_str.equals("1")) {
                        tv_sex.setText("男");
                    } else if (sex_str.equals("2")) {
                        tv_sex.setText("女");
                    } else {
                        tv_sex.setText("");
                    }
                }
                //获取生日
                String birthday_str = bean.getBirthday();
                if (birthday_str.equals("null")) {
                    tv_birthday.setHint("请选择出生日期");
                } else {
                    if (!StringUtils.isEmpty(birthday_str)) {
//                        birthday_str=  DateUtil.StrToDate(birthday_str);
                        tv_birthday.setText(birthday_str);
                    }
                }
            }

            @Override
            protected void requestFailure(Exception error, String msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        };
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
        HttpUtils http = new HttpUtils();
        HashMap<String, File> params_file = new HashMap<>();
        params_file.put("avatars", file);
        MyOkHttp.get().upload(MyCookieStore.SERVERADDRESS + "userCenter/edit_center/", params_file, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                ShopProtocol protocol = new ShopProtocol();
                String str = protocol.setShop(response);
                if (str.equals("1")) {
                    getinfo();
                    //删除缓存文件夹中的图片

                    ImgCropUtil.deleteCacheFile(new File(ImgCropUtil.getCacheDir()));

                    EventBus.getDefault().post(new PersoninfoBean());
                } else {
                    hideDialog(smallDialog);
                    SmartToast.showInfo(str);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo(error_msg);
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(PersoninfoBean bean) {
        //1.绑定房屋后刷新
        //2.更换个人信息后刷新
        getinfo();
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

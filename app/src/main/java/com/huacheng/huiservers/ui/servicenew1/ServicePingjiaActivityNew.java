package com.huacheng.huiservers.ui.servicenew1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.example.xlhratingbar_lib.XLHRatingBar;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.servicenew.model.ModelOrderList;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.utils.uploadimage.GlideImageLoader;
import com.huacheng.huiservers.utils.uploadimage.ImagePickerAdapter;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Description: 服务2.0评价页面
 * created by wangxiaotao
 * 2020/6/19 0019 09:00
 */
public class ServicePingjiaActivityNew extends BaseActivity implements ImagePickerAdapter.OnRecyclerViewItemClickListener {

    @BindView(R.id.lin_left)
    LinearLayout mLinLeft;
    @BindView(R.id.title_name)
    TextView mTitleName;
    @BindView(R.id.txt_right)
    TextView mTvRight;
    @BindView(R.id.sdv_pingjia)
    SimpleDraweeView mSdvPingjia;
    @BindView(R.id.ratingBar)
    XLHRatingBar mRatingBar;
    @BindView(R.id.tv_pingjia_content)
    TextView mTvPingjiaContent;
    @BindView(R.id.ly_pingjia)
    LinearLayout mLyPingjia;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.sdv_one)
    SimpleDraweeView mSdvOne;
    @BindView(R.id.tv_title_one)
    TextView mTvTitleOne;
    @BindView(R.id.tv_sub_title_one)
    TextView mTvSubTitleOne;
    @BindView(R.id.tv_shop_price_one)
    TextView mTvShopPriceOne;
    @BindView(R.id.tv_num_one)
    TextView mTvNumOne;
    @BindView(R.id.lin_price_container)
    LinearLayout mLinPriceContainer;
    @BindView(R.id.tv_btn)
    TextView mTvBtn;
    @BindView(R.id.ly_onclick)
    LinearLayout mLyOnclick;
    @BindView(R.id.ly_tuikuan)
    LinearLayout mLyTuikuan;
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private String order_id="";
    private String title_img="";

    private RxPermissions rxPermission;
    public static final int ACT_SELECT_PHOTO = 111;//选择图片
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private SharePrefrenceUtil sharePrefrenceUtil;
    private ImagePickerAdapter adapter;
    private ArrayList<File> images_submit = new ArrayList<>();
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 3;//允许选择图片最大数
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
//                        XorderDetailBean XorderDetail = new XorderDetailBean();
//                        XorderDetail.setId(data_info.getId());
//                        XorderDetail.setBack_type(2);
//                        // XorderDetail.setPingjia_type(1);//这个没有用到
//                        EventBus.getDefault().post(XorderDetail);
//                        finish();
//                    //删除缓存文件夹中的图片
//                    ImgCropUtil.deleteCacheFile(new File(ImgCropUtil.getCacheDir()));
                    break;
                case 2:
                    mTvRight.setText("确认");
                    mTvRight.setClickable(true);
                    String strmsg = (String) msg.obj;
                    SmartToast.showInfo(strmsg);
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rxPermission = new RxPermissions(this);
        mTitleName.setText("发表评价");
        mTvRight.setVisibility(View.VISIBLE);
        mTvRight.setText("发布");
        mTvRight.setTextColor(getResources().getColor(R.color.orange));
        mRecyclerView.setVisibility(View.GONE);
        initImagePicker();
        initWidget();
    }

    @Override
    protected void initData() {

            mLyPingjia.setVisibility(View.VISIBLE);
            mLyTuikuan.setVisibility(View.GONE);
            FrescoUtils.getInstance().setImageUri(mSdvPingjia,title_img );


    }

    @Override
    protected void initListener() {
        mLinLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRatingBar.setOnRatingChangeListener(new XLHRatingBar.OnRatingChangeListener() {
            @Override
            public void onChange(int countSelected) {
                if (countSelected == 1) {
                    mTvPingjiaContent.setText("比较差");
                } else if (countSelected == 2) {
                    mTvPingjiaContent.setText("一般");
                } else if (countSelected == 3) {
                    mTvPingjiaContent.setText("满意");
                } else if (countSelected == 4) {
                    mTvPingjiaContent.setText("满意");
                } else if (countSelected == 5) {
                    mTvPingjiaContent.setText("非常满意");
                }
            }
        });

        mTvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mEtContent.getText().toString().trim())) {
                    SmartToast.showInfo("请填写内容");
                } else {
                    // 换了一种压缩图片的方式
//                    try {
//                        if (selImageList.size() > 0) {
//                            showDialog(smallDialog);
//                            compressImg();
//                        } else {
//                            getSubmit();
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    showDialog(smallDialog);
                    getSubmit();
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_commit_pingjia_tuikuan;
    }

    @Override
    protected void initIntentData() {
      order_id=this.getIntent().getStringExtra("order_id");
      title_img=this.getIntent().getStringExtra("title_img");
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    String url;

    private void getSubmit( )  {// 发布圈子
//        showDialog(smallDialog);
//        RequestParams params = new RequestParams();
//        HashMap<String, File> params_file = new HashMap<>();
//
//            params.addBodyParameter("oid", data_info.getOid());//订单id
//            params.addBodyParameter("p_id", data_info.getP_id());//商品id
//            params.addBodyParameter("order_info_id", data_info.getId());//商品信息id
//            params.addBodyParameter("score", mRatingBar.getCountSelected() + "");
//            params.addBodyParameter("pic_num", selImageList.size() + "");
//            params.addBodyParameter("description", mEtContent.getText().toString());
//            // 换了一种压缩图片的方式
//            //添加新压缩
//            if (images_submit.size() > 0) {
//                for (int i1 = 0; i1 < images_submit.size(); i1++) {
//                    params_file.put("scoreimg" + i1, images_submit.get(i1));
//                }
//            }
//            url = Url_info.shopping_order_score;
//
//
//        MyOkHttp.get().upload(url, params.getParams(), params_file, new RawResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, String response) {
//                hideDialog(smallDialog);
//                JSONObject jsonObject;
//                try {
//                    jsonObject = new JSONObject(response);
//                    String status = jsonObject.getString("status");
//                    String data = jsonObject.getString("data");
//                    String strmsg = jsonObject.getString("msg");
//                    if (status.equals("1")) {
//                        Message msg = new Message();
//                        msg.what = 1;
//                        mHandler.sendMessage(msg);
//                    } else {
//                        Message msg = new Message();
//                        msg.what = 2;
//                        msg.obj = strmsg;
//                        mHandler.sendMessage(msg);
//                    }
//                } catch ( JSONException e) {
//                    hideDialog(smallDialog);
//                    mTvRight.setEnabled(false);
//                    e.printStackTrace();
//
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, String error_msg) {
//                hideDialog(smallDialog);
//                mTvRight.setEnabled(true);
//                SmartToast.showInfo("网络异常，请检查网络设置");
//            }
//        });
        String content = mEtContent.getText().toString().trim();
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("id", order_id);
        params.put("score",mRatingBar.getCountSelected()+"");
        params.put("evaluate",content);
        params.put("anonymous","2");
        MyOkHttp.get().post(ApiHttpClient.COMMIT_PINGJIA, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "评价成功");
                    SmartToast.showInfo(msg);

                    // 评价成功后也得发eventbus,把服务列表页和详情页的状态改一下
                    ModelOrderList modelOrderList = new ModelOrderList();
                    modelOrderList.setEvent_type(2);
                    EventBus.getDefault().post(modelOrderList);
                    finish();

                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "评价失败");
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
     * 压缩图片（git）
     */
//    private void compressImg() {
//        images_submit.clear();
//        ArrayList<File> files = new ArrayList<>();
//        for (int i1 = 0; i1 < selImageList.size(); i1++) {
//            File file = new File(selImageList.get(i1).path);
//            files.add(file);
//        }
//        Luban.with(this)
//                .load(files)
//                .ignoreBy(100)
//                .setTargetDir(getPath())
//                .setFocusAlpha(false)
//                .filter(new CompressionPredicate() {
//                    @Override
//                    public boolean apply(String path) {
//                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
//                    }
//                })
//                .setCompressListener(new OnCompressListener() {
//                    @Override
//                    public void onStart() {
//                    }
//
//                    @Override
//                    public void onSuccess(File file) {
//                        //这个方法重复调用
//                        images_submit.add(file);
//                        if (images_submit.size() == selImageList.size()) {
//                            //完成了
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    try {
//                                        getSubmit();
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            });
//
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                SmartToast.showInfo("图片压缩失败");
//                                hideDialog(smallDialog);
//                            }
//                        });
//
//                    }
//                }).launch();
//
//    }

    private String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/huiservers/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    private void jumpToImageSelector(int position) {
        Intent imageIntent = new Intent(this, MultiImageSelectorActivity.class);
        // 是否显示相机
        imageIntent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);

        // 单选多选 (MultiImageSelectorActivity.MODE_SINGLE OR MultiImageSelectorActivity.MODE_SINGLE)
        imageIntent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
        // 默认选择

        ArrayList<String> list_jump = new ArrayList<String>();
        for (int i = 0; i < selImageList.size(); i++) {
            //只要localpath不为空则说明是刚选上的
            if (!NullUtil.isStringEmpty(selImageList.get(i).path)) {
                list_jump.add(selImageList.get(i).path);
            }
        }
        imageIntent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, maxImgCount);

        imageIntent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, list_jump);
        startActivityForResult(imageIntent, ACT_SELECT_PHOTO);
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                            //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setMultiMode(true);                      //多选
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }


    private void initWidget() {
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, final int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                rxPermission.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean isGranted) throws Exception {
                                if (isGranted) {
                                    jumpToImageSelector(position);
                                } else {
                                    SmartToast.showInfo("未打开摄像头权限");
                                }
                            }
                        });
                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ACT_SELECT_PHOTO:
                    if (data != null) {
                        ArrayList<String> backList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                        // 删除成功后判断哪些是新图，
                        selImageList.clear();

                        for (int i = 0; i < backList.size(); i++) {
                            String back_path = backList.get(i);
                            ImageItem item = new ImageItem();
                            item.path = back_path;
                            selImageList.add(item);
                        }
                        if (adapter != null) {
                            adapter.setImages(selImageList);
                            adapter.notifyDataSetChanged();
                        }

                    }
                    break;
                default:
                    break;
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null) {
                    selImageList.clear();
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        }
    }

    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除指定文件夹下所有文件
//param path 文件夹完整绝对路径
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

}

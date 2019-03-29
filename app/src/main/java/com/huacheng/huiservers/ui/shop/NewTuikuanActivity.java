package com.huacheng.huiservers.ui.shop;

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
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.huacheng.huiservers.ui.base.BaseActivityOld;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.MyCookieStore;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.utils.XToast;
import com.huacheng.huiservers.utils.ucrop.ImgCropUtil;
import com.huacheng.huiservers.utils.uploadimage.GlideImageLoader;
import com.huacheng.huiservers.utils.uploadimage.ImagePickerAdapter;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.ToastUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.huacheng.huiservers.R.id.recyclerView;

/**
 * 类：
 * 时间：2017/10/14 08:56
 * 功能描述:Huiservers
 */
public class NewTuikuanActivity extends BaseActivityOld implements ImagePickerAdapter.OnRecyclerViewItemClickListener {
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    @BindView(R.id.iv_title)
    ImageView mIvTitle;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.edt_content)
    EditText mEdtContent;
    @BindView(R.id.lin_left)
    LinearLayout mLinLeft;
    @BindView(R.id.title_name)
    TextView mTitleName;
    @BindView(R.id.txt_bin)
    TextView mTxtBin;
    @BindView(recyclerView)
    RecyclerView mRecyclerView;

    private String o_id, p_id, p_info_id, price, numer, img, p_title;
    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 4;//允许选择图片最大数

    public static final int ACT_SELECT_PHOTO = 111;//选择图片
    private ArrayList<File> images_submit=new ArrayList<>();
    private RxPermissions rxPermission;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("item_detete_id", "");
//                    bundle.putString("status", "6");
                    intent.putExtras(bundle);
                    setResult(4, intent);
                    finish();
                //    XToast.makeText(NewTuikuanActivity.this, "提交成功，请耐心等待", XToast.LENGTH_SHORT).show();
                    ToastUtils.showShort(NewTuikuanActivity.this,"提交成功，请耐心等待");
                    //删除缓存文件夹中的图片
                    ImgCropUtil.deleteCacheFile(new File(ImgCropUtil.getCacheDir()));
                    break;
                case 2:
                    mTxtBin.setText("提交申请");
                    mTxtBin.setClickable(true);
                    String strmsg = (String) msg.obj;
                    XToast.makeText(NewTuikuanActivity.this, strmsg, XToast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_tuikuan);
        ButterKnife.bind(this);
 //       SetTransStatus.GetStatus(this);
        mTitleName.setText("退款");
        o_id = this.getIntent().getExtras().getString("o_id");
        p_info_id = this.getIntent().getExtras().getString("p_info_id");
        price = this.getIntent().getExtras().getString("price");
        numer = this.getIntent().getExtras().getString("numer");
        img = this.getIntent().getExtras().getString("img");
        p_title = this.getIntent().getExtras().getString("p_title");
        mTvTitle.setText("已选：" + p_title + " x " + numer);
        Glide
                .with(this)
                .load(MyCookieStore.URL + img)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.icon_girdview)
                .into(mIvTitle);
        mTvPrice.setText("¥" + price);
        initImagePicker();
        initWidget();
        rxPermission=new RxPermissions(this);
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
        /*LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);*/
        //  RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.lin_left, R.id.txt_bin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_left:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("item_detete_id", "");
                intent.putExtras(bundle);
                setResult(1111, intent);
                finish();
                break;
            case R.id.txt_bin:
                if (NullUtil.isStringEmpty(mEdtContent.getText().toString())) {
                    XToast.makeText(NewTuikuanActivity.this, "描述不能为空", XToast.LENGTH_SHORT).show();
                } else {

                    if (selImageList.size()>0) {
                        showDialog(smallDialog);
                        compressImg();
                    }else {
                        getsubmint();
                    }
                    mTxtBin.setText("提交中");
                    mTxtBin.setClickable(false);
                }
                break;
        }
    }
    /**
     * 压缩图片（git）
     */
    private void compressImg() {
        images_submit.clear();
        ArrayList<File> files = new ArrayList<>();
        for (int i1 = 0; i1 < selImageList.size(); i1++) {
            File file = new File(selImageList.get(i1).path);
            files.add(file);
        }
        Luban.with(this)
                .load(files)
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
                    public void onStart() { }

                    @Override
                    public void onSuccess(File file) {
                        //这个方法重复调用
                        images_submit.add(file);
                        if (images_submit.size()==selImageList.size()){
                            //完成了
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getsubmint();

                                }
                            });

                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                XToast.makeText(NewTuikuanActivity.this,"图片压缩失败", Toast.LENGTH_SHORT).show();
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
    private void getsubmint() {//提交退款
        showDialog(smallDialog);
        Url_info info = new Url_info(this);
        HttpUtils utils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addBodyParameter("oid", o_id);//订单id
        params.addBodyParameter("order_info_id", p_info_id);//商品信息id
        params.addBodyParameter("pic_num", selImageList.size() + "");
        params.addBodyParameter("cancel_reason", mEdtContent.getText().toString());
        if (ApiHttpClient.TOKEN != null && ApiHttpClient.TOKEN_SECRET != null) {
            params.addBodyParameter("token", ApiHttpClient.TOKEN + "");
            params.addBodyParameter("tokenSecret", ApiHttpClient.TOKEN_SECRET + "");
        }
        // 以for循环方式上传多张图片， Bimp.tempSelectBitmap为存放图片集合
//        for (int i = 0; i < selImageList.size(); i++) {
//            String path = selImageList.get(i).path;
//            File filepaths = new File(path);
//            try {
//                ToolUtils.compressBmpToFile(Bimp.revitionImageSize(path), filepaths);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            params.addBodyParameter("refundimg" + i, filepaths);
//
//        }
        //添加新压缩
        if (images_submit.size()>0){
            for (int i1 = 0; i1 < images_submit.size(); i1++) {
                params.addBodyParameter("refundimg"+i1,images_submit.get(i1));
            }
        }
        utils.configCookieStore(MyCookieStore.cookieStore);
        utils.configCurrentHttpCacheExpiry(1000 * 10);
        utils.configTimeout(1000 * 5);
        utils.send(HttpMethod.POST, info.shop_refund, params, new RequestCallBack<String>() {

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                hideDialog(smallDialog);
                mTxtBin.setText("提交中");
                mTxtBin.setClickable(true);
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                hideDialog(smallDialog);
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(arg0.result);
                    String status = jsonObject.getString("status");
                    String data = jsonObject.getString("data");
                    String strmsg = jsonObject.getString("msg");
                    if (status.equals("1")) {
                        Message msg = new Message();
                        msg.what = 1;
                        mHandler.sendMessage(msg);
                    } else {
                        Message msg = new Message();
                        msg.what = 2;
                        msg.obj = strmsg;
                        mHandler.sendMessage(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onItemClick(View view, final int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
//                List<String> names = new ArrayList<>();
//                names.add("拍照");
//                names.add("相册");
//                showDialog(new SelectDialog.SelectDialogListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        switch (position) {
//                            case 0: // 直接调起相机
//                                //打开选择,本次允许选择的数量
//                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
//                                Intent intent = new Intent(NewTuikuanActivity.this, ImageGridActivity.class);
//                                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
//                                startActivityForResult(intent, REQUEST_CODE_SELECT);
//                                break;
//                            case 1:
//                                //打开选择,本次允许选择的数量
//                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
//                                Intent intent1 = new Intent(NewTuikuanActivity.this, ImageGridActivity.class);
//                                startActivityForResult(intent1, REQUEST_CODE_SELECT);
//                                break;
//                            default:
//                                break;
//                        }
//                    }
//                }, names);
                rxPermission.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean isGranted) throws Exception {
                                if (isGranted){
                                    jumpToImageSelector(position);
                                }else {
                                    Toast.makeText(NewTuikuanActivity.this, "未打开摄像头权限", Toast.LENGTH_SHORT).show();
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
        imageIntent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 4);

        imageIntent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, list_jump);
        startActivityForResult(imageIntent, ACT_SELECT_PHOTO);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("item_detete_id", "");
            intent.putExtras(bundle);
            setResult(1111, intent);
            finish();
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
//            //添加图片返回
//            if (data != null && requestCode == REQUEST_CODE_SELECT) {
//                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
//                if (images != null) {
//                    selImageList.addAll(images);
//                    adapter.setImages(selImageList);
//                }
//            }
//        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
//            //预览图片返回
//            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
//                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
//                if (images != null) {
//                    selImageList.clear();
//                    selImageList.addAll(images);
//                    adapter.setImages(selImageList);
//                }
//            }
//        }
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
                            item.path=back_path;
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
        }else if (resultCode == ImagePicker.RESULT_CODE_BACK){
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
}

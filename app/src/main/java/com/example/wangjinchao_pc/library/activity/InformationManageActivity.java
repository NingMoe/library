package com.example.wangjinchao_pc.library.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.example.wangjinchao_pc.library.Constant.Constant;
import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.application.MyApplication;
import com.example.wangjinchao_pc.library.base.ToolbarActivity;
import com.example.wangjinchao_pc.library.api.EditHobbyidApi;
import com.example.wangjinchao_pc.library.api.EditSexApi;
import com.example.wangjinchao_pc.library.api.GetHobbyListApi;
import com.example.wangjinchao_pc.library.api.UploadPhoteImgApi;
import com.example.wangjinchao_pc.library.enity.domain.Hobby;
import com.example.wangjinchao_pc.library.enity.picker.Category;
import com.example.wangjinchao_pc.library.enity.baseResult.BaseResultEntity;
import com.example.wangjinchao_pc.library.util.Logger;
import com.example.wangjinchao_pc.library.util.ResourceUtils;
import com.example.wangjinchao_pc.library.util.Utils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kevin.crop.UCrop;
import com.qqtheme.framework.picker.SinglePicker;
import com.retrofit_rx.exception.ApiException;
import com.retrofit_rx.http.HttpManager;
import com.retrofit_rx.listener.HttpOnNextListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.wangjinchao_pc.library.Constant.Configure.PHOTOURL_PREX;

/**
 * Created by wangjinchao-PC on 2017/7/8.
 */

public class InformationManageActivity extends ToolbarActivity implements View.OnClickListener,HttpOnNextListener{

    public static void start(Context context){
        Intent intent =new Intent(context,InformationManageActivity.class);
        context.startActivity(intent);
    }

    private static final int GALLERY_REQUEST_CODE = 0;    // 相册选图标记
    private static final int CAMERA_REQUEST_CODE = 1;    // 相机拍照标记
    private static final int GET_CONTENT_NICKNAME=2;
    private static final int GET_CONTENT_SEX=3;
    private static final int GET_CONTENT_INTEREST=4;
    private static final int GET_CONTENT_ACADEMY=5;
    private static final int GET_CONTENT_PROFESSION=6;


    public static final String STYLE="STYLE";
    public static final String CONTENT="CONTENT";


    // 拍照临时图片
    private String mTempPhotoPath;
    // 剪切后图像文件
    private Uri mDestinationUri;

    @BindView(R.id.headset_container) LinearLayout headset_container;
    @BindView(R.id.nickname_container) LinearLayout nickname_container;
    @BindView(R.id.sex_container) LinearLayout sex_container;
    @BindView(R.id.interest_container) LinearLayout interest_container;
    /*@BindView(R.id.academy_container) LinearLayout academy_container;
    @BindView(R.id.profession_container) LinearLayout profession_container;*/

    @BindView(R.id.headPhoto) SimpleDraweeView headPhoto;

    @BindView(R.id.nickname) TextView nickname;
    @BindView(R.id.sex) TextView sex;
    @BindView(R.id.interest) TextView interest;
    /*@BindView(R.id.academy) TextView academy;
    @BindView(R.id.profession) TextView profession;*/

    //网络请求接口
    private UploadPhoteImgApi uploadPhoteImgApi;
    private EditHobbyidApi editHobbyidApi;
    private EditSexApi editSexApi;
    private GetHobbyListApi getHobbyListApi;
    private HttpManager httpManager;

    List<Category> sexList=null;
    List<Category> hobbyList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        ButterKnife.bind(this);

        initActionBar();
        initHttp();
        initView();
        init_pick();
        init_dataList();

    }

    /**
     * 初始化导航栏
     */
    private void initActionBar(){
        setTitle("个人信息");
    }
    /**
     * 初始化网络
     */
    private void initHttp(){
        httpManager=new HttpManager(this,this);
        uploadPhoteImgApi=new UploadPhoteImgApi();
        editHobbyidApi=new EditHobbyidApi();
        getHobbyListApi=new GetHobbyListApi();
        editSexApi=new EditSexApi();
    }
    /**
     * 初始化头像
     */
    void initView(){
        Glide.with(this.getApplicationContext())
                .load(PHOTOURL_PREX+MyApplication.getToken().getAccount()+".jpg")
                .crossFade(300)
                .placeholder(R.mipmap.headphoto)
                .into(headPhoto);
        String temp=MyApplication.getUser().getNickname();
        Logger.d(this.getClass(),"nickname"+temp);
        if(temp!=null&&!temp.isEmpty())
            nickname.setText(temp);
        temp=MyApplication.getUser().getSex();
        if(temp!=null&&!temp.isEmpty())
            sex.setText(temp.trim().equals("1")?"男":"女");
        temp=MyApplication.getUser().getHobbyName();
        if(temp!=null&&!temp.isEmpty())
            interest.setText(temp);
    }
    /**
     * 初始化
     */
    void init_pick(){
        mDestinationUri = Uri.fromFile(new File(this.getCacheDir(), "headphoto.jpg"));
        mTempPhotoPath = Environment.getExternalStorageDirectory() + File.separator + "tempheadphoto.jpg";
    }
    /**
     * 初始化
     */
    void init_dataList(){
        sexList = new ArrayList<>();
        sexList.add(new Category(1, "男"));
        sexList.add(new Category(2, "女"));

        httpManager.doHttpDeal(getHobbyListApi);
    }

    @OnClick({R.id.headset_container, R.id.headPhoto,R.id.nickname_container,R.id.sex_container,R.id.interest_container})/*R.id.academy_container,R.id.profession_container*/
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.headset_container:
                //选择图片作为头像
                pickFromGallery();
                break;
            case R.id.headPhoto:
                //点击图片查看
                ShowPhotoActivity.start(this);
                break;
            case R.id.nickname_container:
                toSetInformation(0,nickname.getText().toString(),GET_CONTENT_NICKNAME);
                break;
            case R.id.sex_container:

                onSinglePicker(sexList, new SinglePicker.OnItemPickListener<Category>() {
                    @Override
                    public void onItemPicked(int index, Category item) {
                        editSexApi.setAllParam(MyApplication.getToken().getAccount(),Integer.toString(item.getId()));
                        httpManager.doHttpDeal(editSexApi);
                    }
                });
                /*new AlertDialog.Builder(this).setSingleChoiceItems(new String[]{"男", "女"}, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(i==0)
                                    sex.setText("男");
                                else
                                    sex.setText("女");
                                dialogInterface.dismiss();
                            }
                        }).show();*/
                break;
            case R.id.interest_container:
                /*toSetInformation(2,interest.getText().toString(),GET_CONTENT_INTEREST);*/
                if(hobbyList==null||hobbyList.size()<1)
                    break;
                onSinglePicker(hobbyList, new SinglePicker.OnItemPickListener<Category>() {
                    @Override
                    public void onItemPicked(int index, Category item) {
                        editHobbyidApi.setAllParam(MyApplication.getToken().getAccount(),Integer.toString(item.getId()),item.getName());
                        httpManager.doHttpDeal(editHobbyidApi);
                    }
                });
                break;
            /*case R.id.academy_container:
                data = new ArrayList<>();
                data.add(new Category(1, "计算机学院"));
                data.add(new Category(2, "法学院"));
                data.add(new Category(3, "医学院"));
                data.add(new Category(4, "建筑学院"));
                data.add(new Category(5, "外语学院"));
                data.add(new Category(6, "电子信息学院"));
                onSinglePicker(data, new SinglePicker.OnItemPickListener<Category>() {
                    @Override
                    public void onItemPicked(int index, Category item) {
                        academy.setText(item.getName());
                    }
                });
                *//*new AlertDialog.Builder(this).setSingleChoiceItems(new String[]{"计算机学院", "法学院","医学院"}, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(i==0)
                                    academy.setText("计算机学院");
                                else if(i==1)
                                    academy.setText("法学院");
                                else
                                    academy.setText("医学院");
                                dialogInterface.dismiss();
                            }
                        }).show();*//*
                break;
            case R.id.profession_container:
                data = new ArrayList<>();
                data.add(new Category(1, "数媒专业"));
                data.add(new Category(2, "计算机自动化专业"));
                data.add(new Category(3, "软件专业"));
                onSinglePicker(data, new SinglePicker.OnItemPickListener<Category>() {
                    @Override
                    public void onItemPicked(int index, Category item) {
                        academy.setText(item.getName());
                    }
                });
                *//*new AlertDialog.Builder(this).setSingleChoiceItems(new String[]{"数媒专业", "计算机自动化专业","软件专业"}, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(i==0)
                                    profession.setText("数媒专业");
                                else if(i==1)
                                    profession.setText("计算机自动化专业");
                                else
                                    profession.setText("软件专业");

                                dialogInterface.dismiss();
                            }
                        }).show();*//*
                break;*/
        }

    }

    /**
     * 跳转到修改页面
     */
    private void toSetInformation(int style,String content,int tag){
        if(style<0||style>4)
            return;
        Intent intent = new Intent();
        intent.setClass(this, SetInformationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(STYLE,style);
        intent.putExtra(CONTENT,content);
        startActivityForResult(intent, tag);
    }

    /**
     * 选择照片
     */
    private void pickFromGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.permission_read_storage_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {
            Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
            // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
            pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(pickIntent, GALLERY_REQUEST_CODE);
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_STORAGE_READ_ACCESS_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickFromGallery();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == this.RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:   // 调用相机拍照
                    File temp = new File(mTempPhotoPath);
                    startCropActivity(Uri.fromFile(temp));
                    break;
                case GALLERY_REQUEST_CODE:  // 直接从相册获取
                    startCropActivity(data.getData());
                    break;
                case UCrop.REQUEST_CROP:    // 裁剪图片结果
                    handleCropResult(data);
                    break;
                case UCrop.RESULT_ERROR:    // 裁剪图片错误
                    handleCropError(data);
                    break;
                case GET_CONTENT_NICKNAME:
                    handleModify(nickname,data);
                    break;
                case GET_CONTENT_SEX:
                    handleModify(sex,data);
                    break;
                case GET_CONTENT_INTEREST:
                    handleModify(interest,data);
                    break;
                case GET_CONTENT_ACADEMY:
                    /*handleModify(academy,data);*/
                    break;
                case GET_CONTENT_PROFESSION:
                    /*handleModify(profession,data);*/
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 裁剪图片方法实现----压缩成100*100
     *
     * @param uri
     */
    public void startCropActivity(Uri uri) {
        UCrop.of(uri, mDestinationUri)
                .withAspectRatio(1, 1)
                .withMaxResultSize(100, 100)
                .withTargetActivity(CropActivity.class)
                .start(this);
    }

    /**
     * 处理剪切成功的返回值
     *
     * @param result
     */
    private void handleCropResult(Intent result) {
        deleteTempPhotoFile();
        final Uri resultUri = UCrop.getOutput(result);
        if (null != resultUri) {
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            String filePath = resultUri.getEncodedPath();
            String imagePath = Uri.decode(filePath);
            uploadPhoteImgApi.setAllParam(MyApplication.getToken().getAccount(),
                    PHOTOURL_PREX+MyApplication.getToken().getAccount()+".jpg",imagePath);
            httpManager.doHttpDeal(uploadPhoteImgApi);
            /*Toast.makeText(this, "图片已经保存到:" + imagePath, Toast.LENGTH_LONG).show();*/
        } else {
            Toast.makeText(this, "无法剪切选择图片", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 处理剪切失败的返回值
     *
     * @param result
     */
    private void handleCropError(Intent result) {
        deleteTempPhotoFile();
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            //Log.e(TAG, "handleCropError: ", cropError);
            Toast.makeText(this, cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "无法剪切选择图片", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 处理信息修改返回值
     *
     */
    private void handleModify(TextView textView,Intent result){
        if(textView!=null){
            String string;
            try{
               string=result.getExtras().getString(SetInformationActivity.CONTENT).toString();
            }catch(Exception e){
                e.printStackTrace();
                return;
            }
            textView.setText(string);
        }
    }


    /**
     * 删除拍照临时文件
     */
    private void deleteTempPhotoFile() {
        File tempFile = new File(mTempPhotoPath);
        if (tempFile.exists() && tempFile.isFile()) {
            tempFile.delete();
        }
    }
    /**
     * 删除裁剪文件
     */
    private void deletePhotoFile() {
        File tempFile = new File(mDestinationUri.getEncodedPath());
        if (tempFile.exists() && tempFile.isFile()) {
            tempFile.delete();
        }
    }
    /**
     *选择框显示
     */
    public void onSinglePicker(List<Category> data,SinglePicker.OnItemPickListener<Category> listener) {
        SinglePicker<Category> picker = new SinglePicker<>(this, data);
        picker.setCanceledOnTouchOutside(true);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        //设置大小
        picker.setTextSize(ResourceUtils.getXmlDef(this, R.dimen.picker_content_size));
        picker.setCancelTextSize(ResourceUtils.getXmlDef(this, R.dimen.picker_cancel_size));
        picker.setSubmitTextSize(ResourceUtils.getXmlDef(this, R.dimen.picker_submit_size));
        picker.setOnItemPickListener(listener);

                /*new SinglePicker.OnItemPickListener<Category>() {
            @Override
            public void onItemPicked(int index, Category item) {
                Utils.showToast("index=" + index + ", id=" + item.getId() + ", name=" + item.getName());
            }
        });*/
        picker.show();
    }

    @Override
    public void onNext(String resulte, String method) {
        if (method.equals(uploadPhoteImgApi.getMethod())) {
            BaseResultEntity<String> result=null;
            try{
                result = JSONObject.parseObject(resulte, new
                        TypeReference<BaseResultEntity<String>>() {
                        });
            }catch (Exception e){
                e.printStackTrace();
                Utils.showToast("解析错误");
                Logger.e(this.getClass(),"解析错误！！！！！！！！！！");
                return;
            }
            if(result!=null) {
                if(result.getStatus()== Constant.SUCCESS){
//                    Logger.d(this.getClass(),PHOTOURL_PREX+MyApplication.getToken().getAccount()+".jpg");
                    Glide.with(this.getApplicationContext())
                            .load(PHOTOURL_PREX+MyApplication.getToken().getAccount()+".jpg")
                            .skipMemoryCache(true)
                            .crossFade(300)
                            .placeholder(R.mipmap.headphoto)
                            .into(headPhoto);
                    deletePhotoFile();
                    Utils.showToast("头像置换成功");
                }
                else {
                    Utils.showErrorMsgToast(result.getMessage(),"头像置换失败");
                }
            }

        }else if(method.equals(getHobbyListApi.getMethod())){
            BaseResultEntity<List<Hobby>> result=null;
            try{
                result = JSONObject.parseObject(resulte, new
                        TypeReference<BaseResultEntity<List<Hobby>>>() {
                        });
            }catch (Exception e){
                e.printStackTrace();
                Utils.showToast("解析错误");
                Logger.e(this.getClass(),"解析错误！！！！！！！！！！");
                return;
            }
            if(result!=null) {
                if(result.getStatus()== Constant.SUCCESS&&changeHobbyToCategory(result.getData())){
                    Utils.showToast("成功");
                }
                else {
                    Utils.showErrorMsgToast(result.getMessage(),"失败");
                }
            }
        }else if(method.equals(editSexApi.getMethod())){
            BaseResultEntity<String> result=null;
            try{
                result = JSONObject.parseObject(resulte, new
                        TypeReference<BaseResultEntity<String>>() {
                        });
            }catch (Exception e){
                e.printStackTrace();
                Utils.showToast("解析错误");
                Logger.e(this.getClass(),"解析错误！！！！！！！！！！");
                return;
            }
            if(result!=null) {
                if(result.getStatus()== Constant.SUCCESS){
                    sex.setText(editSexApi.getSex().trim().equals("1")?"男":"女");
                    MyApplication.getUser().setSex(editSexApi.getSex().trim().equals("1")?"1":"2");
                    Utils.showToast("修改成功");
                }
                else {
                    Utils.showErrorMsgToast(result.getMessage(),"修改失败");
                }
            }
        }else if(method.equals(editHobbyidApi.getMethod())){
            BaseResultEntity<String> result=null;
            try{
                result = JSONObject.parseObject(resulte, new
                        TypeReference<BaseResultEntity<String>>() {
                        });
            }catch (Exception e){
                e.printStackTrace();
                Utils.showToast("解析错误");
                Logger.e(this.getClass(),"解析错误！！！！！！！！！！");
                return;
            }
            if(result!=null) {
                if(result.getStatus()== Constant.SUCCESS){
                    interest.setText(editHobbyidApi.getName());
                    MyApplication.getUser().setHobbyName(editHobbyidApi.getName());
                    MyApplication.getUser().setHobbyid(editHobbyidApi.getHobbyid());
                    Utils.showToast("修改成功");
                }
                else {
                    Utils.showErrorMsgToast(result.getMessage(),"修改失败");
                }
            }
        }
    }

    @Override
    public void onError(ApiException e, String method) {
        if (method.equals(uploadPhoteImgApi.getMethod())) {
            Utils.showToast(e.getDisplayMessage());
        }else if (method.equals(getHobbyListApi.getMethod())) {
            Utils.showToast(e.getDisplayMessage());
        }else if (method.equals(editSexApi.getMethod())) {
            Utils.showToast(e.getDisplayMessage());
        }else if (method.equals(editHobbyidApi.getMethod())) {
            Utils.showToast(e.getDisplayMessage());
        }
    }

    //将hobby转为category
    private boolean changeHobbyToCategory(List<Hobby> hobbies){
        if(hobbies==null)
            return false;
        for(int i=0;i<hobbies.size();i++){
            hobbyList.add(new Category(hobbies.get(i)));
        }
        return true;
    }
}

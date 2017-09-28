package com.example.wangjinchao_pc.library.activity;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.wangjinchao_pc.library.Fragment.FindFragment;
import com.example.wangjinchao_pc.library.Fragment.HomePageFragment;
import com.example.wangjinchao_pc.library.Fragment.LibraryFragment;
import com.example.wangjinchao_pc.library.Fragment.MineFragment;
import com.example.wangjinchao_pc.library.Fragment.OrderFragment;
import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.adapter.MyViewPageAdapter;
import com.example.wangjinchao_pc.library.base.ToolbarActivity;
import com.example.wangjinchao_pc.library.api.GetBannerApi;
import com.example.wangjinchao_pc.library.api.GetNoticeApi;
import com.example.wangjinchao_pc.library.api.GetOrderApi;
import com.example.wangjinchao_pc.library.api.GetRankApi;
import com.example.wangjinchao_pc.library.enity.baseResult.BaseResultEntity;
import com.example.wangjinchao_pc.library.util.Utils;
import com.jauker.widget.BadgeView;
import com.retrofit_rx.exception.ApiException;
import com.retrofit_rx.http.HttpManager;
import com.retrofit_rx.listener.HttpOnNextListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends ToolbarActivity implements HttpOnNextListener{

    public static void start(Context context){
        Intent intent =new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    private MenuItem scanner;
    private MenuItem search;
    private MenuItem recommend;
    private MenuItem notice;

    //5大Fragment页面
    private Fragment find,homePage,library,mine,order;

    //网络请求接口
    private HttpManager httpManager;
    private GetBannerApi getBannerApi;
    private GetOrderApi getOrderApi;
    private GetRankApi getRankApi;
    private GetNoticeApi getNoticeApi;

    private HttpOnNextListener httpOnNextListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        httpManager=new HttpManager(this,this);

        initActionBar();
        initViewPager();
    }
    /**
     * 初始化导航栏
     */
    private void initActionBar(){
        setTitle(R.string.app_name);
        setDisplayHomeAsUpEnabled(false);
    }
    /**
     * 初始化viewpager，tablayout
     */
    private void initViewPager(){

        List<Fragment> fragmentList = new ArrayList<>();
        homePage=new HomePageFragment();
        fragmentList.add(homePage);
        library=new LibraryFragment();
        fragmentList.add(library);
        order=new OrderFragment();
        fragmentList.add(order);
        find=new FindFragment();
        fragmentList.add(find);
        mine=new MineFragment();
        fragmentList.add(mine);

        viewPager.setAdapter(new MyViewPageAdapter(fragmentList, getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                checkMenuItemIsVisiable(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText(R.string.home_page).setIcon(Utils.setDrawableTint(getResources().getDrawable(R.drawable.home),
                getResources().getColorStateList(R.color.main_tab_color_selector)));
        tabLayout.getTabAt(1).setText(R.string.library).setIcon(Utils.setDrawableTint(getResources().getDrawable(R.drawable.library),
                getResources().getColorStateList(R.color.main_tab_color_selector)));
        tabLayout.getTabAt(2).setText(R.string.order).setIcon(Utils.setDrawableTint(getResources().getDrawable(R.drawable.order),
                getResources().getColorStateList(R.color.main_tab_color_selector)));
        tabLayout.getTabAt(3).setText(R.string.find).setIcon(Utils.setDrawableTint(getResources().getDrawable(R.drawable.find),
                getResources().getColorStateList(R.color.main_tab_color_selector)));
        tabLayout.getTabAt(4).setText(R.string.mine).setIcon(Utils.setDrawableTint(getResources().getDrawable(R.drawable.mine),
                getResources().getColorStateList(R.color.main_tab_color_selector)));
    }
    /**
     * 刷新(Notice?????????????????????????????)
     */
    public void refresh(){
        getBannerApi=new GetBannerApi();
        getOrderApi=new GetOrderApi();
        getRankApi=new GetRankApi();
        getNoticeApi=new GetNoticeApi();
        httpManager.doHttpDeal(getBannerApi);
        httpManager.doHttpDeal(getOrderApi);
        httpManager.doHttpDeal(getRankApi);
        httpManager.doHttpDeal(getNoticeApi);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkMenuItemIsVisiable(viewPager.getCurrentItem());
    }
    /**
     * 创建菜单栏
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.e("Activity", "onCreateOptionMenu");
        getMenuInflater().inflate(R.menu.zxing, menu);
        scanner = menu.findItem(R.id.start_zxing);
        search = menu.findItem(R.id.search);
        recommend=menu.findItem(R.id.start_recommend);
        notice=menu.findItem(R.id.start_notice);

        scanner.setIcon(Utils.setDrawableTint(scanner.getIcon(), getResources().getColor(R.color.textIcon)));
        search.setIcon(Utils.setDrawableTint(search.getIcon(),getResources().getColor(R.color.textIcon)));
        //notice.setIcon(Utils.setDrawableTint(notice.getIcon(),getResources().getColor(R.color.textIcon)));

        if(notice==null){
            Log.d("setNoticeNumber","notice==null");
            return true;
        }
        ImageView v=(ImageView) MenuItemCompat.getActionView(notice).findViewById(R.id.badge_img);
        checkMenuItemIsVisiable(viewPager.getCurrentItem());
        setNoticeNumber((View)v,3);
        if(v!=null)
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("setOnClickListener", "Badge");
                    NoticeActivity.start(MainActivity.this);
                }
            });
        return true;
    }
    /**
     * 菜单栏监听
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.e("Activity", "onOptionsItemSelected");

        switch (item.getItemId()){
            case R.id.start_zxing:

                break;
            case R.id.search:
                //SearchActivity.start(this);
                break;

            case R.id.start_notice:
                NoticeActivity.start(this);
                break;
            case R.id.start_recommend:
                RecommendActivity.start(this);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void changePage(int position){
        viewPager.setCurrentItem(position);
    }

//    //改变登录状态时，修改所有界面
//    public void initUI(){
//        ((ShelfFragment)book_shelf).initBookShelf();
//        ((RecommendFragment)recommend).getRecommendDir();
//        ((MineFragment)mine).init();
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
//        if(requestCode == CaptureActivity.ZXING_REQUEST_CODE && resultCode == RESULT_OK && data!=null){
//            String capture_data = data.getStringExtra(CaptureActivity.EXTRA_DATA);
//        }
//        if(requestCode == ChoosePhotoDialog.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data!=null){
//
//            Bundle extras = data.getExtras();
//            Bitmap bitmap = (Bitmap) extras.get("data");
//            ((MineFragment)mine).setHeadPhoto(bitmap);
//            FileHelper.getInstance().saveHeadphoto(bitmap);
//        }
//        if(requestCode == ChoosePhotoDialog.REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data!=null){
//            Uri uri = data.getData();
//            FileHelper.getInstance().saveHeadphoto(uri);
//            ((MineFragment)mine).setHeadPhoto(FileHelper.getInstance().getHeadphoto());
//
//
//        }
    }

    private void checkMenuItemIsVisiable(int position){

        switch(position){
            case 0:
                setTitle(R.string.home_page);
                ((HomePageFragment)homePage).changeBannerState(true);
                setMenuItem(search,false);
                setMenuItem(scanner,false);
                setMenuItem(notice,false);
                setMenuItem(recommend,false);
                if(this.getCurrentFocus()!=null&&this.getCurrentFocus() instanceof EditText)
                    hideSoftInput(this.getCurrentFocus().getWindowToken());
                break;
            case 1:
                setTitle(R.string.library);
                ((HomePageFragment)homePage).changeBannerState(false);
                setMenuItem(search,false);
                setMenuItem(scanner,false);
                setMenuItem(notice,false);
                setMenuItem(recommend,true);
                break;
            case 2:
                setTitle(R.string.order);
                ((HomePageFragment)homePage).changeBannerState(false);
                setMenuItem(search,false);
                setMenuItem(scanner,false);
                setMenuItem(notice,false);
                setMenuItem(recommend,false);
                if(this.getCurrentFocus()!=null&&this.getCurrentFocus() instanceof EditText)
                    hideSoftInput(this.getCurrentFocus().getWindowToken());
                break;
            case 3:
                setTitle(R.string.find);
                ((HomePageFragment)homePage).changeBannerState(false);
                setMenuItem(search,false);
                setMenuItem(scanner,false);
                setMenuItem(notice,false);
                setMenuItem(recommend,false);
                if(this.getCurrentFocus()!=null&&this.getCurrentFocus() instanceof EditText)
                    hideSoftInput(this.getCurrentFocus().getWindowToken());
                break;
            case 4:
                setTitle(R.string.mine);
                ((HomePageFragment)homePage).changeBannerState(false);
                setMenuItem(search,false);
                setMenuItem(scanner,false);
                setMenuItem(notice,true);
                setMenuItem(recommend,false);
                if(this.getCurrentFocus()!=null&&this.getCurrentFocus() instanceof EditText)
                    hideSoftInput(this.getCurrentFocus().getWindowToken());
                break;

        }
    }

    //判断menuItem是否为空，减小冗余
    public void setMenuItem(MenuItem menuItem,boolean flag){
        if(menuItem==null)
            return;
        menuItem.setVisible(flag);
    }

/*    private void requestPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
            }
        }else{
            startActivityForResult(new Intent(this, CaptureActivity.class), CaptureActivity.ZXING_REQUEST_CODE);
        }
    }*/

/*    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case CAMERA_PERMISSION:{
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    startActivityForResult(new Intent(this, CaptureActivity.class), CaptureActivity.ZXING_REQUEST_CODE);
                }else{
                    Utils.showToast("您已经拒绝了相机权限，请在权限管理中设置");
                }
                return;
            }
        }
    }*/

    public Fragment getFragment(Class<? extends Fragment> tClass){
        if(tClass.getSimpleName().equals("ShelfFragment")){
//            return book_shelf;
        }
        if(tClass.getSimpleName().equals("MineFragment")){
            return mine;
        }
        return null;
    }

    public void setNoticeNumber(View v,int number){
        if(v==null){
            Log.d("setNoticeNumber","(View)notice==null");
            return;
        }
        BadgeView badgeView = new BadgeView(this);
        badgeView.setTargetView(v);
        badgeView.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
        badgeView.setText(""+number);
        badgeView.setTextSize(10);
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
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getRawX() > left && event.getRawX() < right
                    && event.getRawY() > top && event.getRawY() < bottom) {
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
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onNext(String resulte, String method) {
        if(method.equals(getBannerApi.getMethod())){
            BaseResultEntity<String> result = JSONObject.parseObject(resulte, new
                    TypeReference<BaseResultEntity<String>>() {
                    });
        }else if(method.equals(getNoticeApi.getMethod())){
            BaseResultEntity<String> result = JSONObject.parseObject(resulte, new
                    TypeReference<BaseResultEntity<String>>() {
                    });
        }else if(method.equals(getOrderApi.getMethod())){
            BaseResultEntity<String> result = JSONObject.parseObject(resulte, new
                    TypeReference<BaseResultEntity<String>>() {
                    });
        }else if(method.equals(getRankApi.getMethod())){
            BaseResultEntity<String> result = JSONObject.parseObject(resulte, new
                    TypeReference<BaseResultEntity<String>>() {
                    });
        }
        //商榷
        if(httpOnNextListener!=null)
            httpOnNextListener.onNext(resulte, method);
    }

    @Override
    public void onError(ApiException e, String method) {
        Utils.showToast(e.getDisplayMessage());
        //商榷
        if(httpOnNextListener!=null)
            httpOnNextListener.onError(e, method);
    }

    public HttpOnNextListener getHttpOnNextListener() {
        return httpOnNextListener;
    }

    public void setHttpOnNextListener(HttpOnNextListener httpOnNextListener) {
        this.httpOnNextListener = httpOnNextListener;
    }
}

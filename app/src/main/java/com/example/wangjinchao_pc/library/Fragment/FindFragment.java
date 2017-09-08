package com.example.wangjinchao_pc.library.Fragment;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.activity.LeaveWorkActivity;
import com.example.wangjinchao_pc.library.activity.ScanActivity;
import com.example.wangjinchao_pc.library.application.MyApplication;
import com.example.wangjinchao_pc.library.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.Manifest.permission.CAMERA;
import static android.app.Activity.RESULT_OK;
import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

/**
 * Created by wangjinchao-PC on 2017/7/6.
 */

public class FindFragment extends BaseFragment implements View.OnClickListener{

    private final static int SCANNIN_GREQUEST_CODE = 1;

    private final int CAMERA_PERMISSION = 1;

    View view;
    @BindView(R.id.leave_work)
    LinearLayout leave_work;
    @BindView(R.id.scan)
    LinearLayout scan;
    @BindView(R.id.print)
    LinearLayout print;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(getContext()).from(getContext()).inflate(R.layout.fragment_find, null);
        ButterKnife.bind(this, view);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return view;
    }

    @OnClick({R.id.leave_work, R.id.scan,R.id.print})
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.leave_work:

                LeaveWorkActivity.start(view.getContext());
                break;
            case R.id.scan:
                if( ContextCompat.checkSelfPermission(this.getContext(), CAMERA) == PERMISSION_GRANTED){
                    Intent intent = new Intent();
                    intent.setClass(MyApplication.getContext(), ScanActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
                }
                break;
            case R.id.print:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if(resultCode == RESULT_OK){
                    Bundle bundle = data.getExtras();
                    //显示扫描到的内容
                    Toast.makeText(getContext(),bundle.getString("result"),Toast.LENGTH_LONG).show();
                    //显示
                    //mImageView.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));
                }
                break;
        }
    }

    public static boolean isCameraUseable() {
        boolean canUse =true;
        Camera mCamera =null;
        try{
            mCamera = Camera.open();
            // setParameters 是针对魅族MX5。MX5通过Camera.open()拿到的Camera对象不为null
            Camera.Parameters mParameters = mCamera.getParameters();
            mCamera.setParameters(mParameters);
        }catch(Exception e) {
            canUse =false;
        }
        if(mCamera !=null) {
            mCamera.release();
        }
        return canUse;
    }

}

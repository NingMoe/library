package com.example.wangjinchao_pc.library.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.wangjinchao_pc.library.util.Logger;
import com.example.wangjinchao_pc.library.util.Utils;
import com.zxing.activity.DefaultQRScanActivity;

/**
 * Created by wangjinchao-PC on 2017/9/9.
 */

public class ScanActivity extends DefaultQRScanActivity {
    @Override
    protected void handleDecodeResult(String rawResult, Bundle bundle) {
        /*Log.d("+++++wjc+++++",rawResult);*/
        if(TextUtils.isEmpty(rawResult)){
            Utils.showToast("Scan failed!");
            this.finish();
            return;
        }
        Intent resultIntent = new Intent();
        bundle.putString("result", rawResult);
        resultIntent.putExtras(bundle);
        this.setResult(RESULT_OK, resultIntent);
        this.finish();
    }

    @Override
    protected void onAlbumResult(int requestCode, int resultCode, String resultData) {
        /*Log.d("+++++wjc+++++",resultData);*/
        if(TextUtils.isEmpty(resultData)){
            Utils.showToast("Scan failed!");
            this.finish();
            return;
        }
        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("result", resultData);
        resultIntent.putExtras(bundle);
        this.setResult(RESULT_OK, resultIntent);
        this.finish();
    }

    @Override
    protected void initCustomViewAndEvents() {

    }
}

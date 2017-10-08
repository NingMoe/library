package com.example.wangjinchao_pc.library.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.View.X5WebView;
import com.example.wangjinchao_pc.library.application.MyApplication;
import com.example.wangjinchao_pc.library.base.ToolbarActivity;
import com.example.wangjinchao_pc.library.util.Logger;
import com.kevin.crop.UCrop;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.utils.TbsLog;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by wangjinchao-PC on 2017/9/11.
 */

public class OrderActivity extends ToolbarActivity {
    public static final String URL="url";
    public static void start(Context context, String url){
        Intent intent = new Intent(context, OrderActivity.class);
        intent.putExtra(URL, url);
        context.startActivity(intent);
    }

    public final static int ORDER_REQUEST=120;
    /**
     * 作为一个浏览器的示例展示出来，采用android+web的模式
     */
    private X5WebView mWebView;
    private ViewGroup mViewParent;
    private ProgressBar progressBar;
	/*private ImageButton mBack;
	private ImageButton mForward;
	private ImageButton mExit;
	private ImageButton mHome;
	private ImageButton mMore;
	private Button mGo;
	private EditText mUrl;*/

    private static String mHomeUrl = "";
    private static final String TAG = "SdkDemo";
    private static final int MAX_LENGTH = 14;
    private boolean mNeedTestPage = false;

    private final int disable = 120;
    private final int enable = 255;

    private ProgressBar mPageLoadingProgressBar = null;

    private ValueCallback<Uri> uploadFile;
    private ValueCallback<Uri[]> uploadFiles;

    private URL mIntentUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        Intent intent = getIntent();
        if (intent != null) {
            try {
                mHomeUrl=intent.getExtras().getString(URL).toString();
                mIntentUrl = new URL(mHomeUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {

            } catch (Exception e) {
            }
        }
        //
        try {
            if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 11) {
                getWindow()
                        .setFlags(
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
        }

		/*
		 * getWindow().addFlags(
		 * android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 */
        setContentView(R.layout.activity_printer);
        initActionBar();
        mViewParent = (ViewGroup) findViewById(R.id.webView1);
        progressBar=(ProgressBar)findViewById(R.id.progressBar1);

		/*initBtnListenser();*/

        mTestHandler.sendEmptyMessageDelayed(MSG_INIT_UI, 10);

    }

    /**
     * 初始化导航栏
     */
    private void initActionBar(){
        setTitle("预约");
        setDisplayHomeAsUpEnabled(true);
    }

    private void changGoForwardButton(com.tencent.smtt.sdk.WebView view) {
		/*if (view.canGoBack())
			mBack.setAlpha(enable);
		else
			mBack.setAlpha(disable);
		if (view.canGoForward())
			mForward.setAlpha(enable);
		else
			mForward.setAlpha(disable);
		if (view.getUrl() != null && view.getUrl().equalsIgnoreCase(mHomeUrl)) {
			mHome.setAlpha(disable);
			mHome.setEnabled(false);
		} else {
			mHome.setAlpha(enable);
			mHome.setEnabled(true);
		}*/
    }

    private void initProgressBar() {
        mPageLoadingProgressBar = (ProgressBar) findViewById(R.id.progressBar1);// new
        // ProgressBar(getApplicationContext(),
        // null,
        // android.R.attr.progressBarStyleHorizontal);
        mPageLoadingProgressBar.setMax(100);
        mPageLoadingProgressBar.setProgressDrawable(this.getResources()
                .getDrawable(R.drawable.color_progressbar));
    }

    private void init() {

        mWebView = new X5WebView(this, null);

        mViewParent.addView(mWebView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.FILL_PARENT,
                FrameLayout.LayoutParams.FILL_PARENT));

        initProgressBar();

        mWebView.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView view, String url) {
                Logger.d(this.getClass(),"shouldOverrideUrlLoading");

                return false;
            }

            @Override
            public void onPageFinished(com.tencent.smtt.sdk.WebView view, String url) {
                super.onPageFinished(view, url);
                // mTestHandler.sendEmptyMessage(MSG_OPEN_TEST_URL);
                mTestHandler.sendEmptyMessageDelayed(MSG_OPEN_TEST_URL, 5000);// 5s?
                if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 16)
                    changGoForwardButton(view);
				/* mWebView.showLog("test Log"); */
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
            }


        });

        mWebView.setWebChromeClient(new com.tencent.smtt.sdk.WebChromeClient() {

            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);

                if (i == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    if (progressBar.getVisibility() == View.GONE)
                        progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(i);
                }
            }

            @Override
            public boolean onJsConfirm(com.tencent.smtt.sdk.WebView arg0, String arg1, String arg2,
                                       JsResult arg3) {
                return super.onJsConfirm(arg0, arg1, arg2, arg3);
            }

            View myVideoView;
            View myNormalView;
            IX5WebChromeClient.CustomViewCallback callback;

            // /////////////////////////////////////////////////////////
            //
            /**
             * 全屏播放配置
             */
            @Override
            public void onShowCustomView(View view,
                                         IX5WebChromeClient.CustomViewCallback customViewCallback) {
				/*FrameLayout normalView = (FrameLayout) findViewById(R.id.web_filechooser);
				ViewGroup viewGroup = (ViewGroup) normalView.getParent();
				viewGroup.removeView(normalView);
				viewGroup.addView(view);
				myVideoView = view;
				myNormalView = normalView;
				callback = customViewCallback;*/
            }

            @Override
            public void onHideCustomView() {
                if (callback != null) {
                    callback.onCustomViewHidden();
                    callback = null;
                }
                if (myVideoView != null) {
                    ViewGroup viewGroup = (ViewGroup) myVideoView.getParent();
                    viewGroup.removeView(myVideoView);
                    viewGroup.addView(myNormalView);
                }
            }

            @Override
            public boolean onJsAlert(com.tencent.smtt.sdk.WebView arg0, String arg1, String arg2,
                                     JsResult arg3) {
                /**
                 * 这里写入你自定义的window alert
                 */
                return super.onJsAlert(null, arg1, arg2, arg3);
            }

            // For Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                Log.i("test", "openFileChooser 1");
                OrderActivity.this.uploadFile = uploadFile;
                openFileChooseProcess();
            }

            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsgs) {
                Log.i("test", "openFileChooser 2");
                OrderActivity.this.uploadFile = uploadFile;
                openFileChooseProcess();
            }

            // For Android  > 4.1.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                Log.i("test", "openFileChooser 3");
                OrderActivity.this.uploadFile = uploadFile;
                openFileChooseProcess();
            }

            // For Android  >= 5.0
            public boolean onShowFileChooser(com.tencent.smtt.sdk.WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             com.tencent.smtt.sdk.WebChromeClient.FileChooserParams fileChooserParams) {
                Log.i("test", "openFileChooser 4:" + filePathCallback.toString());
                OrderActivity.this.uploadFiles = filePathCallback;
                openFileChooseProcess();
                return true;
            }
        });

		/*mWebView.setDownloadListener(new MyWebViewDownLoadListener());*/
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String arg0, String arg1, String arg2,
                                        String arg3, long arg4) {
                TbsLog.d(TAG, "url: " + arg0);
                Logger.d(this.getClass(),"onDownloadStart");
                Uri uri = Uri.parse(arg0);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

                new AlertDialog.Builder(OrderActivity.this)
                        .setTitle("allow to download？")
                        .setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        Toast.makeText(
                                                OrderActivity.this,
                                                "fake message: i'll download...",
                                                Toast.LENGTH_LONG).show();
                                    }
                                })
                        .setNegativeButton("no",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        // TODO Auto-generated method stub
                                        Toast.makeText(
                                                OrderActivity.this,
                                                "fake message: refuse download...",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setOnCancelListener(
                                new DialogInterface.OnCancelListener() {

                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        // TODO Auto-generated method stub
                                        Toast.makeText(
                                                OrderActivity.this,
                                                "fake message: refuse download...",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }).show();
            }
        });

        com.tencent.smtt.sdk.WebSettings webSetting = mWebView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(com.tencent.smtt.sdk.WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);
        long time = System.currentTimeMillis();
        if (mIntentUrl == null) {
            new AlertDialog.Builder(OrderActivity.this).setTitle("提示").setMessage("您未绑定，请前往绑定页面绑定")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            BindCollegeActivity.start(OrderActivity.this);

                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            BindCollegeActivity.start(OrderActivity.this);
                        }
                    }).show();
        } else {
            mWebView.loadUrl(mIntentUrl.toString());
        }
        TbsLog.d("time-cost", "cost time: "
                + (System.currentTimeMillis() - time));
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().sync();
    }

	/*private void initBtnListenser() {
		mBack = (ImageButton) findViewById(R.id.btnBack1);
		mForward = (ImageButton) findViewById(R.id.btnForward1);
		mExit = (ImageButton) findViewById(R.id.btnExit1);
		mHome = (ImageButton) findViewById(R.id.btnHome1);
		mGo = (Button) findViewById(R.id.btnGo1);
		mUrl = (EditText) findViewById(R.id.editUrl1);
		mMore = (ImageButton) findViewById(R.id.btnMore);
		if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 16) {
			mBack.setAlpha(disable);
			mForward.setAlpha(disable);
			mHome.setAlpha(disable);
		}
		mHome.setEnabled(false);

		mBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mWebView != null && mWebView.canGoBack())
					mWebView.goBack();
			}
		});

		mForward.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mWebView != null && mWebView.canGoForward())
					mWebView.goForward();
			}
		});

		mGo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String url = mUrl.getText().toString();
				mWebView.loadUrl(url);
				mWebView.requestFocus();
			}
		});

		mMore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(BrowserActivity.this, "not completed",
						Toast.LENGTH_LONG).show();
			}
		});

		mUrl.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					mGo.setVisibility(View.VISIBLE);
					if (null == mWebView.getUrl())
						return;
					if (mWebView.getUrl().equalsIgnoreCase(mHomeUrl)) {
						mUrl.setText("");
						mGo.setText("首页");
						mGo.setTextColor(0X6F0F0F0F);
					} else {
						mUrl.setText(mWebView.getUrl());
						mGo.setText("进入");
						mGo.setTextColor(0X6F0000CD);
					}
				} else {
					mGo.setVisibility(View.GONE);
					String title = mWebView.getTitle();
					if (title != null && title.length() > MAX_LENGTH)
						mUrl.setText(title.subSequence(0, MAX_LENGTH) + "...");
					else
						mUrl.setText(title);
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}

		});

		mUrl.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

				String url = null;
				if (mUrl.getText() != null) {
					url = mUrl.getText().toString();
				}

				if (url == null
						|| mUrl.getText().toString().equalsIgnoreCase("")) {
					mGo.setText("请输入网址");
					mGo.setTextColor(0X6F0F0F0F);
				} else {
					mGo.setText("进入");
					mGo.setTextColor(0X6F0000CD);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}
		});

		mHome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mWebView != null)
					mWebView.loadUrl(mHomeUrl);
			}
		});

		mExit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Process.killProcess(Process.myPid());
			}
		});
	}*/

    boolean[] m_selected = new boolean[] { true, true, true, true, false,
            false, true };

    private void openFileChooseProcess() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        startActivityForResult(Intent.createChooser(i, "test"), 0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView != null && mWebView.canGoBack()) {
                mWebView.goBack();
                if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 16)
                    changGoForwardButton(mWebView);
                return true;
            } else{
                Intent resultIntent = new Intent();
                this.setResult(RESULT_OK, resultIntent);
                this.finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        TbsLog.d(TAG, "onActivityResult, requestCode:" + requestCode
                + ",resultCode:" + resultCode);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    if (null != uploadFile) {
                        Uri result = data == null || resultCode != RESULT_OK ? null
                                : data.getData();
                        uploadFile.onReceiveValue(result);
                        uploadFile = null;
                    }
                    if (null != uploadFiles) {
                        Uri result = data == null || resultCode != RESULT_OK ? null
                                : data.getData();
                        uploadFiles.onReceiveValue(new Uri[]{result});
                        uploadFiles = null;
                    }
                    break;
                case ORDER_REQUEST:
                    try {
                        mHomeUrl= MyApplication.getIdent().getOrderUrl();
                        mIntentUrl = new URL(mHomeUrl);
                        mWebView.loadUrl(mIntentUrl.toString());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e) {

                    } catch (Exception e) {
                    }
                    break;
                default:
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (null != uploadFile) {
                uploadFile.onReceiveValue(null);
                uploadFile = null;
            }

        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent == null || mWebView == null || intent.getData() == null)
            return;
        mWebView.loadUrl(intent.getData().toString());
    }

    @Override
    protected void onDestroy() {
        if (mTestHandler != null)
            mTestHandler.removeCallbacksAndMessages(null);
        if (mWebView != null)
            mWebView.destroy();
        super.onDestroy();
    }

    public static final int MSG_OPEN_TEST_URL = 0;
    public static final int MSG_INIT_UI = 1;
    private final int mUrlStartNum = 0;
    private int mCurrentUrl = mUrlStartNum;
    private Handler mTestHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_OPEN_TEST_URL:
                    if (!mNeedTestPage) {
                        return;
                    }

                    String testUrl = "file:///sdcard/outputHtml/html/"
                            + Integer.toString(mCurrentUrl) + ".html";
                    if (mWebView != null) {
                        mWebView.loadUrl(testUrl);
                    }

                    mCurrentUrl++;
                    break;
                case MSG_INIT_UI:
                    init();
                    break;
            }
            super.handleMessage(msg);
        }
    };

//	private class MyWebViewDownLoadListener implements DownloadListener {
//
//		@Override
//		public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
//									long contentLength) {
//			if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
//				Toast t=Toast.makeText(BrowserActivity.this, "需要SD卡。", Toast.LENGTH_LONG);
//				t.setGravity(Gravity.CENTER, 0, 0);
//				t.show();
//				return;
//			}
//			DownloaderTask task=new DownloaderTask();
//			task.execute(url);
//		}
//
//	}
//	//内部类
//	private class DownloaderTask extends AsyncTask<String, Void, String> {
//
//		public DownloaderTask() {
//		}
//
//		@Override
//		protected String doInBackground(String... params) {
//			// TODO Auto-generated method stub
//			String url=params[0];
////          Log.i("tag", "url="+url);
//			String fileName=url.substring(url.lastIndexOf("/")+1);
//			fileName= URLDecoder.decode(fileName);
//			Log.i("tag", "fileName="+fileName);
//
//			File directory=Environment.getExternalStorageDirectory();
//			File file=new File(directory,fileName);
//			if(file.exists()){
//				Log.i("tag", "The file has already exists.");
//				return fileName;
//			}
//			try {
//				HttpClient client = new DefaultHttpClient();
////                client.getParams().setIntParameter("http.socket.timeout",3000);//设置超时
//				HttpGet get = new HttpGet(url);
//				HttpResponse response = client.execute(get);
//				if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){
//					HttpEntity entity = response.getEntity();
//					InputStream input = entity.getContent();
//
//					writeToSDCard(fileName,input);
//
//					input.close();
////                  entity.consumeContent();
//					return fileName;
//				}else{
//					return null;
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				return null;
//			}
//		}
//
//		@Override
//		protected void onCancelled() {
//			// TODO Auto-generated method stub
//			super.onCancelled();
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//			// TODO Auto-generated method stub
//			super.onPostExecute(result);
//			closeProgressDialog();
//			if(result==null){
//				Toast t=Toast.makeText(BrowserActivity.this, "连接错误！请稍后再试！", Toast.LENGTH_LONG);
//				t.setGravity(Gravity.CENTER, 0, 0);
//				t.show();
//				return;
//			}
//
//			Toast t=Toast.makeText(BrowserActivity.this, "已保存到SD卡。", Toast.LENGTH_LONG);
//			t.setGravity(Gravity.CENTER, 0, 0);
//			t.show();
//			File directory=Environment.getExternalStorageDirectory();
//			File file=new File(directory,result);
//			Log.i("tag", "Path="+file.getAbsolutePath());
//
//			Intent intent = getFileIntent(file);
//
//			startActivity(intent);
//
//		}
//
//		@Override
//		protected void onPreExecute() {
//			// TODO Auto-generated method stub
//			super.onPreExecute();
//			showProgressDialog();
//		}
//
//		@Override
//		protected void onProgressUpdate(Void... values) {
//			// TODO Auto-generated method stub
//			super.onProgressUpdate(values);
//		}
//
//
//	}
//
//	private ProgressDialog mDialog;
//	private void showProgressDialog(){
//		if(mDialog==null){
//			mDialog = new ProgressDialog(this);
//			mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置风格为圆形进度条
//			mDialog.setMessage("正在加载 ，请等待...");
//			mDialog.setIndeterminate(false);//设置进度条是否为不明确
//			mDialog.setCancelable(true);//设置进度条是否可以按退回键取消
//			mDialog.setCanceledOnTouchOutside(false);
//			mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//					// TODO Auto-generated method stub
//					mDialog=null;
//				}
//			});
//			mDialog.show();
//
//		}
//	}
//	private void closeProgressDialog(){
//		if(mDialog!=null){
//			mDialog.dismiss();
//			mDialog=null;
//		}
//	}
//	public Intent getFileIntent(File file){
////       Uri uri = Uri.parse("http://m.ql18.com.cn/hpf10/1.pdf");
//		Uri uri = Uri.fromFile(file);
//		String type = getMIMEType(file);
//		Log.i("tag", "type="+type);
//		Intent intent = new Intent("android.intent.action.VIEW");
//		intent.addCategory("android.intent.category.DEFAULT");
//		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		intent.setDataAndType(uri, type);
//		return intent;
//	}
//
//	public void writeToSDCard(String fileName,InputStream input){
//
//		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
//			File directory=Environment.getExternalStorageDirectory();
//			File file=new File(directory,fileName);
////          if(file.exists()){
////              Log.i("tag", "The file has already exists.");
////              return;
////          }
//			try {
//				FileOutputStream fos = new FileOutputStream(file);
//				byte[] b = new byte[2048];
//				int j = 0;
//				while ((j = input.read(b)) != -1) {
//					fos.write(b, 0, j);
//				}
//				fos.flush();
//				fos.close();
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}else{
//			Log.i("tag", "NO SDCard.");
//		}
//	}
//
//	private String getMIMEType(File f){
//		String type="";
//		String fName=f.getName();
//      /* 取得扩展名 */
//		String end=fName.substring(fName.lastIndexOf(".")+1,fName.length()).toLowerCase();
//
//      /* 依扩展名的类型决定MimeType */
//		if(end.equals("pdf")){
//			type = "application/pdf";//
//		}
//		else if(end.equals("m4a")||end.equals("mp3")||end.equals("mid")||
//				end.equals("xmf")||end.equals("ogg")||end.equals("wav")){
//			type = "audio/*";
//		}
//		else if(end.equals("3gp")||end.equals("mp4")){
//			type = "video/*";
//		}
//		else if(end.equals("jpg")||end.equals("gif")||end.equals("png")||
//				end.equals("jpeg")||end.equals("bmp")){
//			type = "image/*";
//		}
//		else if(end.equals("apk")){
//        /* android.permission.INSTALL_PACKAGES */
//			type = "application/vnd.android.package-archive";
//		}
////      else if(end.equals("pptx")||end.equals("ppt")){
////        type = "application/vnd.ms-powerpoint";
////      }else if(end.equals("docx")||end.equals("doc")){
////        type = "application/vnd.ms-word";
////      }else if(end.equals("xlsx")||end.equals("xls")){
////        type = "application/vnd.ms-excel";
////      }
//		else{
////        /*如果无法直接打开，就跳出软件列表给用户选择 */
//			type="*/*";
//		}
//		return type;
//	}
}

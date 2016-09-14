package com.yuan.mobilesafe.chapter01;

import com.yuan.mobilesafe.R;
import com.yuan.mobilesafe.chapter01.utils.MyUtils;
import com.yuan.mobilesafe.chapter01.utils.VersionUpdateUtils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

public class SplashActivity extends Activity{
	String TAG = "SplashActivity";
	//应用版本号
	private TextView mVersionTV;
	//本地版本号
	private String mVersion;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		mVersion = MyUtils.getVersion(getApplicationContext());
		initView();
		final VersionUpdateUtils versionUpdateUtils = new VersionUpdateUtils(mVersion, SplashActivity.this);
		new Thread(){
			public void run() {
				//获取服务器版本号
				Log.d(TAG, "hi");
				versionUpdateUtils.getCloudVersion();
			};
		}.start();
	}
	private void initView() {
		mVersionTV = (TextView) findViewById(R.id.tv_splash_version);
		mVersionTV.setText("版本号" + mVersion);
	}

}

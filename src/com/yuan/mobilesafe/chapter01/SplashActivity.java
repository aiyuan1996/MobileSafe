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
	//Ӧ�ð汾��
	private TextView mVersionTV;
	//���ذ汾��
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
				//��ȡ�������汾��
				Log.d(TAG, "hi");
				versionUpdateUtils.getCloudVersion();
			};
		}.start();
	}
	private void initView() {
		mVersionTV = (TextView) findViewById(R.id.tv_splash_version);
		mVersionTV.setText("�汾��" + mVersion);
	}

}

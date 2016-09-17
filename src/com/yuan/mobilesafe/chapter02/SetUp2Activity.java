package com.yuan.mobilesafe.chapter02;

import com.yuan.mobilesafe.R;

import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class SetUp2Activity extends BaseSetUpActivity implements OnClickListener{
	private TelephonyManager mTelephonyManager;
	private Button mBindSIMBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup2);
		mTelephonyManager = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
		initView();
	}
	
	private boolean isBind(){
		String simString = sp.getString("sim", null);
		if(simString.isEmpty()){
			return false;
		}else {
			return true;
		}
	}
	private void initView() {
		//设置第二个点的颜色
		((RadioButton)(findViewById(R.id.rb_second))).setChecked(true);
		mBindSIMBtn = (Button) findViewById(R.id.btn_bind_sim);
		mBindSIMBtn.setOnClickListener(this);
		if(isBind()){
			mBindSIMBtn.setEnabled(false);
		}else {
			mBindSIMBtn.setEnabled(true);
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_bind_sim:
			//绑定手机卡
			bindSIM();
			break;

		default:
			break;
		}
	}

	private void bindSIM() {
		if(!isBind()){
			String simSerialNumber = mTelephonyManager.getSimSerialNumber();
			Editor editor = sp.edit();
			editor.putString("sim", simSerialNumber);
			editor.commit();
			Toast.makeText(this, "SIm卡已经绑定成功", 0).show();
			mBindSIMBtn.setEnabled(false);
		}else {
			//已经绑定，提醒用户
			Toast.makeText(this, "sim卡已经绑定", 0).show();
			mBindSIMBtn.setEnabled(false);
		}
	}

	@Override
	public void showNext() {
		if(!isBind()){
			Toast.makeText(this, "您还没有绑定sim卡", 0).show();
			return ;
		}
		startActivityAndFinishSelf(SetUp3Activity.class);
	}

	@Override
	public void showPre() {
		startActivityAndFinishSelf(SetUp3Activity.class);
	}

}

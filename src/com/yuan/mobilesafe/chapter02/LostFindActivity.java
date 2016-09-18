package com.yuan.mobilesafe.chapter02;

import com.yuan.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class LostFindActivity extends Activity implements OnClickListener{
	private String TAG = "LostFindActivity";
	private TextView mSafePhoneTV;
	private RelativeLayout mInterSetupRL;
	private SharedPreferences msharedPreferences;
	private ToggleButton mToggleButton;
	private TextView mProtectStatusTV;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_lostfind);
		Log.d(TAG, "进入LostFindActivity");
		msharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
		
		if(!isSetUp()){
			//如果没有进入过设置向导
			Log.d(TAG, "没有设置向导");
			startSetUp1Activity();
		}
		Log.d(TAG, "设置了向导");
		initview();
	}

	private void initview() {
		TextView mTitleTV = (TextView) findViewById(R.id.tv_title);
		mTitleTV.setText("手机防盗");
		ImageView mLeftImgv = (ImageView) findViewById(R.id.imgv_leftbtn);
		mLeftImgv.setOnClickListener(this);
		mLeftImgv.setImageResource(R.drawable.back);
		findViewById(R.id.rl_titlebar).setBackgroundColor(getResources().getColor(R.color.purple));
		mSafePhoneTV = (TextView) findViewById(R.id.tv_safephone);
		mSafePhoneTV.setText(msharedPreferences.getString("safephone", ""));
		mToggleButton = (ToggleButton) findViewById(R.id.togglebtn_lostfind);
		mInterSetupRL = (RelativeLayout) findViewById(R.id.rl_inter_setup_wizard);
		mInterSetupRL.setOnClickListener(this);
		mProtectStatusTV = (TextView) findViewById(R.id.tv_lostfind_protectstauts);
		//查询手机防盗是否开启
		boolean protecting = msharedPreferences.getBoolean("protecting", false);
		Log.d(TAG, "protecting="+protecting);
		if(protecting){
			mProtectStatusTV.setText("防盗保护已经开启");
			mToggleButton.setChecked(true);
		}else{
			mProtectStatusTV.setText("防盗保护没有开启");
			mToggleButton.setChecked(false);
		}
		mToggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					mProtectStatusTV.setText("防盗保护已经开启");
				}else {
					mProtectStatusTV.setText("防盗保护没有开启");
				}
				Editor editor = msharedPreferences.edit();
				editor.putBoolean("protecting", isChecked);
				Log.d(TAG, "protecting:" + isChecked);
				editor.commit();
			}
		});
	}

	private void startSetUp1Activity() {
		Log.d(TAG, "准备进入设置向导页面");
		Intent intent = new Intent(LostFindActivity.this,SetUp1Activity.class);
		startActivity(intent);
		finish();
	}

	private boolean isSetUp() {
		return msharedPreferences.getBoolean("isSetUp", false);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_inter_setup_wizard:
			//重新进入设置向导
			startSetUp1Activity();
			break;
		case R.id.imgv_leftbtn:
			//返回homeactivity
			finish();
			break;

		default:
			break;
		}
	}

}

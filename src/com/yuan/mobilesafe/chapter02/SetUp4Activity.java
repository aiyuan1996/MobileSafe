package com.yuan.mobilesafe.chapter02;

import com.yuan.mobilesafe.R;

import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class SetUp4Activity extends BaseSetUpActivity{
	private TextView mStatusTv;
	private ToggleButton mToggleButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup4);
		initview();
	}

	private void initview() {
		((RadioButton)findViewById(R.id.rb_four)).setChecked(true);
		mStatusTv = (TextView) findViewById(R.id.tv_setup4_status);
		mToggleButton = (ToggleButton) findViewById(R.id.togglebtn_securityfunction);
		mToggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked)
					mStatusTv.setText("���������Ѿ�����");
				else {
					mStatusTv.setText("�ŵ�����û�п���");
				}
				Editor editor = sp.edit();
				editor.putBoolean("Protecting", isChecked);
				editor.commit();
			}
		});
		boolean protecting = sp.getBoolean("protecting", true);
		if(protecting){
			mStatusTv.setText("���������Ѿ�����");
			mToggleButton.setChecked(true);
		}else {
			mStatusTv.setText("�ŵ�����û�п���");
			mToggleButton.setChecked(false);
		}
	}

	@Override
	public void showNext() {
		//��ת���ŵ�����ҳ��
		Editor editor = sp.edit();
		editor.putBoolean("isSetUp", true);
		editor.commit();
		startActivityAndFinishSelf(LostFindActivity.class);
	}

	@Override
	public void showPre() {
		startActivityAndFinishSelf(SetUp3Activity.class);
	}

}

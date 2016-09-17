package com.yuan.mobilesafe.chapter02;

import com.yuan.mobilesafe.R;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.Toast;

public class SetUp1Activity extends BaseSetUpActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup1);
		init();
	}

	private void init() {
		//���õ�һ��СԲ�����ɫ
		((RadioButton) findViewById(R.id.rb_first)).setChecked(true);
	}

	@Override
	public void showNext() {
		startActivityAndFinishSelf(SetUp2Activity.class);
	}

	@Override
	public void showPre() {
		Toast.makeText(this, "��ǰҳ���Ѿ��ǵ�һҳ", 0).show();
	}

}

package com.yuan.mobilesafe.chapter02;

import com.yuan.mobilesafe.R;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class SetUp3Activity extends BaseSetUpActivity implements OnClickListener{
	private EditText mInputPhone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup3);
		initview();
	}

	private void initview() {
		((RadioButton)findViewById(R.id.rb_third)).setChecked(true);
		findViewById(R.id.btn_addcontact).setOnClickListener(this);
		mInputPhone = (EditText) findViewById(R.id.et_inputphone);
		String safephone = sp.getString("safephone", null);
		if(!TextUtils.isEmpty(safephone))
			mInputPhone.setText(safephone);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_addcontact:
			startActivityForResult(new Intent(this,ContactSelectActivity.class), 0);
			break;

		default:
			break;
		}
	}

	@Override
	public void showNext() {
		//判断文本输入框中是否有电话号码
		String safephone = mInputPhone.getText().toString().trim();
		if(TextUtils.isEmpty(safephone)){
			Toast.makeText(this,"请输入安全号码" ,0).show();
			return ;
		}
		Editor editor = sp.edit();
		editor.putString("safephone", safephone);
		editor.commit();
		startActivityAndFinishSelf(SetUp4Activity.class);
	}

	@Override
	public void showPre() {
		startActivityAndFinishSelf(SetUp2Activity.class);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(data != null){
			String phone = data.getStringExtra("phone");
			mInputPhone.setText(phone);
		}
	}

}

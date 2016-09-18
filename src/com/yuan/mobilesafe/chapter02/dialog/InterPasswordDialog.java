package com.yuan.mobilesafe.chapter02.dialog;

import com.yuan.mobilesafe.R;
import com.yuan.mobilesafe.chapter02.dialog.SetUpPasswordDialog.MyCallBack;
import com.yuan.mobilesafe.chapter02.utils.MD5utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;

public class InterPasswordDialog extends Dialog implements android.view.View.OnClickListener{
	private String TAG = "InterPasswordDialog";
	/**对话框标题*/
	private TextView mTitleTV;
	/**输入密码文本框*/
	private EditText mInterET;
	/**确认按钮*/
	private Button mOKBtn;
	/**取消按钮*/
	private Button mCancleBtn;
	/**回调接口*/
	private MyCallBack myCallBack;
	private Context context;

	public InterPasswordDialog(Context context) {
		super(context,R.style.dialog_custom);
		this.context = context;
	}
	
	public void setCallBack(MyCallBack myCallBack){
		this.myCallBack = myCallBack;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		setContentView(R.layout.inter_password_dialog);
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		mTitleTV = (TextView) findViewById(R.id.tv_interpwd_title);
		mInterET = (EditText) findViewById(R.id.et_inter_password);
		mOKBtn = (Button) findViewById(R.id.btn_comfirm);
		mCancleBtn = (Button) findViewById(R.id.btn_dismiss);
		mOKBtn.setOnClickListener(this);
		mCancleBtn.setOnClickListener(this);
		
	}
	/***
	 * 设置对话框标题
	 * @param title
	 */
	public void setTitle(String title){
		if(!TextUtils.isEmpty(title)){
			mTitleTV.setText(title);
		}
	}
	
	public String getPassword(){
		Log.d(TAG, "从输入框的道德密码的md5加密是：" + MD5utils.encode(mInterET.getText().toString()));
		return mInterET.getText().toString();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_comfirm:
			myCallBack.confirm();
			break;
		case R.id.btn_dismiss:
			myCallBack.cancle();
			break;
		}
	}
	
	/**
	 * 回调接口
	 */
	public interface MyCallBack{
		void confirm();
		void cancle();
	}

}

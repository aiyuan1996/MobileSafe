package com.yuan.mobilesafe;

import com.yuan.mobilesafe.chapter01.adapter.HomeAdapter;
import com.yuan.mobilesafe.chapter02.LostFindActivity;
import com.yuan.mobilesafe.chapter02.dialog.InterPasswordDialog;
import com.yuan.mobilesafe.chapter02.dialog.SetUpPasswordDialog;
import com.yuan.mobilesafe.chapter02.dialog.SetUpPasswordDialog.MyCallBack;
import com.yuan.mobilesafe.chapter02.receiver.MyDeviceAdminReciever;
import com.yuan.mobilesafe.chapter02.utils.MD5utils;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class HomeActivity extends Activity{
	//声明GridView
	private GridView gv_home;
	private SharedPreferences sharedPreferences;
	//设备管理员
	private DevicePolicyManager policyManager;
	//申请权限
	private ComponentName componentName;
	private long mExitTime;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
		sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
		gv_home = (GridView) findViewById(R.id.gv_home);
		gv_home.setAdapter(new HomeAdapter(HomeActivity.this));
		//设置条目的点击事件
		gv_home.setOnItemClickListener(new OnItemClickListener() {
			//parent代表gridview，view代表每个条目的item对象，position代表每个条目的位置

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				switch (position) {
				case 0://手机防盗
					if(isSetUpPassword()){
						//弹出输入密码对话框
						showInterPswdDialog();
					}else {
						//弹出设置密码对话框
						showSetUpPswdDialog();
					}
					//Toast.makeText(HomeActivity.this,position + "", Toast.LENGTH_LONG).show();
					break;
				case 1://通讯卫士
					Toast.makeText(HomeActivity.this,position + "", Toast.LENGTH_LONG).show();
					//startActivity(SecurityPhoneActivity.class);
					break;
				case 2: // 软件管家
					Toast.makeText(HomeActivity.this,position + "", Toast.LENGTH_LONG).show();
					//startActivity(AppManagerActivity.class);
					break;
				case 3:// 手机杀毒
					Toast.makeText(HomeActivity.this,position + "", Toast.LENGTH_LONG).show();
					//startActivity(VirusScanActivity.class);
					break;
				case 4:// 缓存清理
					Toast.makeText(HomeActivity.this,position + "", Toast.LENGTH_LONG).show();
					//startActivity(CacheClearListActivity.class);
					break;
				case 5:// 进程管理
					Toast.makeText(HomeActivity.this,position + "", Toast.LENGTH_LONG).show();
					//startActivity(ProcessManagerActivity.class);
					break;
				case 6: // 流量统计
					Toast.makeText(HomeActivity.this,position + "", Toast.LENGTH_LONG).show();
					//startActivity(TrafficMonitoringActivity.class);
					break;
				case 7: // 高级工具
					Toast.makeText(HomeActivity.this,position + "", Toast.LENGTH_LONG).show();
					//startActivity(AdvancedToolsActivity.class);
					break;
				case 8: // 设置中心
					Toast.makeText(HomeActivity.this,position + "", Toast.LENGTH_LONG).show();
					//startActivity(SettingsActivity.class);
					break;
				default:
					break;
				}
			}
		});
		//获取设备管理员
		policyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
		//申请权限
		componentName = new ComponentName(this,MyDeviceAdminReciever.class);
		//判断，如果没有权限则申请权限
		boolean active = policyManager.isAdminActive(componentName);
		if(!active){
			//没有管理员权限，则获取管理员的权限
			Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
			intent.putExtra("DevicePolicyManager.EXTRA_DEVICE_ADMIN", componentName);
			//获取超级管理员，用于远程锁屏和清除数据
			startActivity(intent);
		}
				
	}
	/**
	 * 开启新的activity，不关闭自己
	 * @param cls 新的activity的字节码
	 */
	protected void startActivity(Class<?> cls) {
		Intent intent = new Intent(HomeActivity.this,cls);
		startActivity(intent);
	}
	protected void showSetUpPswdDialog() {
		final SetUpPasswordDialog setUpPasswordDialog = new SetUpPasswordDialog(HomeActivity.this);
		setUpPasswordDialog.setCallBack(new MyCallBack() {
			
			@Override
			public void ok() {
				String firstPwsd = setUpPasswordDialog.mFirstPWDET.getText().toString().trim();
				String affirmPwsd = setUpPasswordDialog.mAffirmET.getText().toString().trim();
				if(!TextUtils.isEmpty(firstPwsd) && !TextUtils.isEmpty(affirmPwsd)){
					if(firstPwsd.equals(affirmPwsd)){
						//两次密码一至，储存密码
						savePswd(affirmPwsd);
						setUpPasswordDialog.dismiss();
						//显示输入密码对话框
						showInterPswdDialog();
					}else {
						Toast.makeText(HomeActivity.this, "两次输入密码不一致", 0).show();
					}
				}else {
					Toast.makeText(HomeActivity.this, "密码不能为空", 0).show();
				}
			}
			
			@Override
			public void cancle() {
				setUpPasswordDialog.dismiss();
			}
		});
		setUpPasswordDialog.setCancelable(false);
		setUpPasswordDialog.show();
	}
	protected void savePswd(String affirmPwsd) {
		Editor editor = sharedPreferences.edit();
		//为了防止用户隐私泄露，加密密码
		editor.putString("PhoneAntiTheftPWD", MD5utils.encode(affirmPwsd));
		editor.commit();
	}
	protected void showInterPswdDialog() {
		final String password = getPassword();
		final InterPasswordDialog interPasswordDialog = new InterPasswordDialog(HomeActivity.this);
		interPasswordDialog.setCallBack(new com.yuan.mobilesafe.chapter02.dialog.InterPasswordDialog.MyCallBack() {
			
			@Override
			public void confirm() {
				if(!TextUtils.isEmpty(interPasswordDialog.getPassword())){
					Toast.makeText(HomeActivity.this, "密码不能为空", 0).show();
				}else if(password.equals(MD5utils.encode(interPasswordDialog.getPassword()))){
					//进入防盗主界面
					interPasswordDialog.dismiss();
					startActivity(LostFindActivity.class);
				}else {
					//对话框消失
					interPasswordDialog.dismiss();
					Toast.makeText(HomeActivity.this, "密码错误", 0).show();
				}
			}
			
			@Override
			public void cancle() {
				interPasswordDialog.dismiss();
			}
		});
		interPasswordDialog.setCancelable(false);
		interPasswordDialog.show();
	}
	
	/**
	 * 获取密码
	 * @return
	 */
	private String getPassword() {
		String password = sharedPreferences.getString("PhoneAntiTheftPWD", null);
		if(TextUtils.isEmpty(password)){
			return "";
		}
		return password;
	}
	
	/**
	 * 判断用户是否设置过手机防盗密码
	 * @return
	 */
	protected boolean isSetUpPassword() {
		String password = sharedPreferences.getString("PhoneAntiTheftPWD", null);
		if(TextUtils.isEmpty(password))
			return false;
		return true;
	}
	
	/**
	 * 按两次返回键退出程序
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if((System.currentTimeMillis() - mExitTime) < 2000){
				System.exit(0);
			}else {
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_LONG).show();
				mExitTime = System.currentTimeMillis();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}

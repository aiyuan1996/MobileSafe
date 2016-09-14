package com.yuan.mobilesafe;

import com.yuan.mobilesafe.chapter01.adapter.HomeAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
	private long mExitTime;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
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
//					if(isSetUpPassword()){
//						//弹出输入密码对话框
//						showInterPswdDialog();
//					}else {
//						//弹出设置密码对话框
//						showSetUpPswdDialog();
//					}
					Toast.makeText(HomeActivity.this,position + "", Toast.LENGTH_LONG).show();
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
		// TODO Auto-generated method stub
		
	}
	protected void showInterPswdDialog() {
		// TODO Auto-generated method stub
		
	}
	protected boolean isSetUpPassword() {
		// TODO Auto-generated method stub
		return false;
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

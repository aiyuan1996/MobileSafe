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
	//����GridView
	private GridView gv_home;
	private long mExitTime;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
		gv_home = (GridView) findViewById(R.id.gv_home);
		gv_home.setAdapter(new HomeAdapter(HomeActivity.this));
		//������Ŀ�ĵ���¼�
		gv_home.setOnItemClickListener(new OnItemClickListener() {
			//parent����gridview��view����ÿ����Ŀ��item����position����ÿ����Ŀ��λ��

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				switch (position) {
				case 0://�ֻ�����
//					if(isSetUpPassword()){
//						//������������Ի���
//						showInterPswdDialog();
//					}else {
//						//������������Ի���
//						showSetUpPswdDialog();
//					}
					Toast.makeText(HomeActivity.this,position + "", Toast.LENGTH_LONG).show();
					break;
				case 1://ͨѶ��ʿ
					Toast.makeText(HomeActivity.this,position + "", Toast.LENGTH_LONG).show();
					//startActivity(SecurityPhoneActivity.class);
					break;
				case 2: // ����ܼ�
					Toast.makeText(HomeActivity.this,position + "", Toast.LENGTH_LONG).show();
					//startActivity(AppManagerActivity.class);
					break;
				case 3:// �ֻ�ɱ��
					Toast.makeText(HomeActivity.this,position + "", Toast.LENGTH_LONG).show();
					//startActivity(VirusScanActivity.class);
					break;
				case 4:// ��������
					Toast.makeText(HomeActivity.this,position + "", Toast.LENGTH_LONG).show();
					//startActivity(CacheClearListActivity.class);
					break;
				case 5:// ���̹���
					Toast.makeText(HomeActivity.this,position + "", Toast.LENGTH_LONG).show();
					//startActivity(ProcessManagerActivity.class);
					break;
				case 6: // ����ͳ��
					Toast.makeText(HomeActivity.this,position + "", Toast.LENGTH_LONG).show();
					//startActivity(TrafficMonitoringActivity.class);
					break;
				case 7: // �߼�����
					Toast.makeText(HomeActivity.this,position + "", Toast.LENGTH_LONG).show();
					//startActivity(AdvancedToolsActivity.class);
					break;
				case 8: // ��������
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
	 * �����µ�activity�����ر��Լ�
	 * @param cls �µ�activity���ֽ���
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
	 * �����η��ؼ��˳�����
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if((System.currentTimeMillis() - mExitTime) < 2000){
				System.exit(0);
			}else {
				Toast.makeText(this, "�ٰ�һ���˳�����", Toast.LENGTH_LONG).show();
				mExitTime = System.currentTimeMillis();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}

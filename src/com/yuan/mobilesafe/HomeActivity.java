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
	//����GridView
	private GridView gv_home;
	private SharedPreferences sharedPreferences;
	//�豸����Ա
	private DevicePolicyManager policyManager;
	//����Ȩ��
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
		//������Ŀ�ĵ���¼�
		gv_home.setOnItemClickListener(new OnItemClickListener() {
			//parent����gridview��view����ÿ����Ŀ��item����position����ÿ����Ŀ��λ��

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				switch (position) {
				case 0://�ֻ�����
					if(isSetUpPassword()){
						//������������Ի���
						showInterPswdDialog();
					}else {
						//������������Ի���
						showSetUpPswdDialog();
					}
					//Toast.makeText(HomeActivity.this,position + "", Toast.LENGTH_LONG).show();
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
		//��ȡ�豸����Ա
		policyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
		//����Ȩ��
		componentName = new ComponentName(this,MyDeviceAdminReciever.class);
		//�жϣ����û��Ȩ��������Ȩ��
		boolean active = policyManager.isAdminActive(componentName);
		if(!active){
			//û�й���ԱȨ�ޣ����ȡ����Ա��Ȩ��
			Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
			intent.putExtra("DevicePolicyManager.EXTRA_DEVICE_ADMIN", componentName);
			//��ȡ��������Ա������Զ���������������
			startActivity(intent);
		}
				
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
		final SetUpPasswordDialog setUpPasswordDialog = new SetUpPasswordDialog(HomeActivity.this);
		setUpPasswordDialog.setCallBack(new MyCallBack() {
			
			@Override
			public void ok() {
				String firstPwsd = setUpPasswordDialog.mFirstPWDET.getText().toString().trim();
				String affirmPwsd = setUpPasswordDialog.mAffirmET.getText().toString().trim();
				if(!TextUtils.isEmpty(firstPwsd) && !TextUtils.isEmpty(affirmPwsd)){
					if(firstPwsd.equals(affirmPwsd)){
						//��������һ������������
						savePswd(affirmPwsd);
						setUpPasswordDialog.dismiss();
						//��ʾ��������Ի���
						showInterPswdDialog();
					}else {
						Toast.makeText(HomeActivity.this, "�����������벻һ��", 0).show();
					}
				}else {
					Toast.makeText(HomeActivity.this, "���벻��Ϊ��", 0).show();
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
		//Ϊ�˷�ֹ�û���˽й¶����������
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
					Toast.makeText(HomeActivity.this, "���벻��Ϊ��", 0).show();
				}else if(password.equals(MD5utils.encode(interPasswordDialog.getPassword()))){
					//�������������
					interPasswordDialog.dismiss();
					startActivity(LostFindActivity.class);
				}else {
					//�Ի�����ʧ
					interPasswordDialog.dismiss();
					Toast.makeText(HomeActivity.this, "�������", 0).show();
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
	 * ��ȡ����
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
	 * �ж��û��Ƿ����ù��ֻ���������
	 * @return
	 */
	protected boolean isSetUpPassword() {
		String password = sharedPreferences.getString("PhoneAntiTheftPWD", null);
		if(TextUtils.isEmpty(password))
			return false;
		return true;
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

package com.yuan.mobilesafe;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsManager;
import android.text.TextUtils;
import android.util.Log;

public class APP extends Application{
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		correctSIM();
	}

	/**
	 * ���sim���Ƿ����仯
	 */
	public void correctSIM() {
		SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
		//��ȡ����������״̬
		boolean protecting = sp.getBoolean("protecting", true);
		if(protecting){
			//�õ��󶨵�sim��
			String bindsim = sp.getString("sim", "");
			//�õ��ֻ����ڵ�sim����
			TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
			//Ϊ�˲������ֻ����к��ϵ�data��ģ��sim�������������
			String realsim = telephonyManager.getSimSerialNumber();
			if(bindsim.equals(realsim)){
				Log.i("", "sim��δ�����仯�����������ֻ�");
			}else {
				Log.i("", "sim���仯��");
				//����ϵͳ�汾��ԭ������Ⱥ���Ķ��ſ����������ֻ��汾������
				String safenumber = sp.getString("safephone", "");
				if(!TextUtils.isEmpty(safenumber)){
					SmsManager smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage(safenumber, null, "���������ֻ���sim���ѱ�����", null, null);
				}
			}
		}
	}

}

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
	 * 检查sim卡是否发生变化
	 */
	public void correctSIM() {
		SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
		//获取防盗保护的状态
		boolean protecting = sp.getBoolean("protecting", true);
		if(protecting){
			//得到绑定的sim卡
			String bindsim = sp.getString("sim", "");
			//得到手机现在的sim卡号
			TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
			//为了测试在手机序列号上的data已模拟sim卡被更换的情况
			String realsim = telephonyManager.getSimSerialNumber();
			if(bindsim.equals(realsim)){
				Log.i("", "sim卡未发生变化，还是您的手机");
			}else {
				Log.i("", "sim卡变化了");
				//由于系统版本的原因，这里群发的短信可能与其他手机版本不兼容
				String safenumber = sp.getString("safephone", "");
				if(!TextUtils.isEmpty(safenumber)){
					SmsManager smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage(safenumber, null, "您的亲友手机的sim卡已被更换", null, null);
				}
			}
		}
	}

}

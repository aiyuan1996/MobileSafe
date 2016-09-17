package com.yuan.mobilesafe.chapter02.receiver;

import javax.crypto.BadPaddingException;

import com.yuan.mobilesafe.R;
import com.yuan.mobilesafe.chapter02.service.GPSLocationService;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.telephony.gsm.SmsMessage;
import android.text.TextUtils;
import android.util.Log;

public class SmsLostFindReciver extends BroadcastReceiver{
	private static final String TAG = SmsLostFindReciver.class.getSimpleName();
	private SharedPreferences sharedPreferences;

	@Override
	public void onReceive(Context context, Intent intent) {
		sharedPreferences = context.getSharedPreferences("config", Activity.MODE_PRIVATE);
		boolean protecting = sharedPreferences.getBoolean("protecting", true);
		if(protecting){
			//��ȡ��������Ա
			DevicePolicyManager dpm = (DevicePolicyManager) context.getSystemService(context.DEVICE_POLICY_SERVICE);
			Object[] objs = (Object[]) intent.getExtras().get("pdus");
			for(Object obj:objs){
				SmsMessage smsMessage = SmsMessage.createFromPdu((byte[])obj);
				String sender = smsMessage.getOriginatingAddress();
				String body = smsMessage.getMessageBody();
				String safephone = sharedPreferences.getString("safephone", null);
				//����ö����ǰ�ȫ���뷢�͵�
				if(!TextUtils.isEmpty(safephone) & sender.equals(safephone)){
					if("#*location*#".equals(body)){
						Log.d(TAG, "����λ����Ϣ");
						//��ȡλ�ã����ڷ�������ȥʵ��
						Intent service = new Intent(context,GPSLocationService.class);
						context.startService(service);
						abortBroadcast();
					}else if("#*alarm*#".equals(body)){
						Log.d(TAG, "���ű�������");
						MediaPlayer player = MediaPlayer.create(context,R.raw.ylzs);
						player.setVolume(1.0f, 1.0f);
						player.start();
						abortBroadcast();
					}else if("#*wipedata*#".equals(body)){
						Log.d(TAG, "Զ���������");
						dpm.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);
						abortBroadcast();
					}else if("#*lockscreen*#".equals(body)){
						Log.d(TAG, "Զ������");
						dpm.resetPassword("123", 0);
						abortBroadcast();
					}
				}
			}
		}
	}

}

package com.yuan.mobilesafe.chapter02.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.gsm.SmsManager;
import android.util.Log;

public class GPSLocationService extends Service{
	private LocationManager manager;
	private MyListener listener;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		manager = (LocationManager)getSystemService(LOCATION_SERVICE);
		listener = new MyListener();
		//criteria��ѯ����
		//trueֻ���ؿ��õ�Ϊ֮�ṩ��
		Criteria criteria = new Criteria();
		//��ȡ׼ȷ��λ��
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		//�����������
		criteria.setCostAllowed(true);
		String name = manager.getBestProvider(criteria, true);
		Log.d("GPSLocationService", "��õ�λ���ṩ��" + name);
		manager.requestLocationUpdates(name, 0, 0, listener);
	}
	public class MyListener implements LocationListener{

		@Override
		public void onLocationChanged(Location location) {
			StringBuilder builder = new StringBuilder();
			builder.append("accuracy:" + location.getAccuracy()+"\n");
			builder.append("speed:" + location.getSpeed() + "\n");
			builder.append("jingdu:" + location.getLongitude() + "\n");
			builder.append("weidu:" + location.getLatitude() + "\n");
			String result = builder.toString();
			SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
			String safenumber = sharedPreferences.getString("safephone", "");
			SmsManager.getDefault().sendTextMessage(safenumber, null, result, null, null);
			stopSelf();
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		manager.removeUpdates(listener);
		listener = null;
	}

}

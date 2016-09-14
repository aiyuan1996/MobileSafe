package com.yuan.mobilesafe.chapter01.utils;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;

public class MyUtils {
	/**
	 * ��ȡ�汾��
	 * @param context
	 * @return ���ذ汾��
	 */
	public static String getVersion(Context context){
		//packageManager���Ի�ȡ�嵥�ļ��е�������Ϣ
		PackageManager manager = context.getPackageManager();
		try{
			//getPackageName���Ի�ȡ��ǰ����İ���
			PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionName;
		}catch(NameNotFoundException e){
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * ��װ�°汾
	 * @param activity
	 */
	public static void installApk(Activity activity){
		Intent intent = new Intent("android.intent.action.VIEW");
		//���Ĭ�Ϸ���
		intent.addCategory("android.intent.category.DEFAULT");
		//�������ݺ�����
		intent.setDataAndType(Uri.fromFile(new File("/mnt/sdcard/MobileSave2.0.apk")), "application/vnd.android.package-archive");
		//���������Activity�˳�ʱ����õ�ǰActivity��onActivityResult
		activity.startActivityForResult(intent, 0);
	}

}

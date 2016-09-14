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
	 * 获取版本号
	 * @param context
	 * @return 返回版本号
	 */
	public static String getVersion(Context context){
		//packageManager可以获取清单文件中的所有信息
		PackageManager manager = context.getPackageManager();
		try{
			//getPackageName可以获取当前程序的包名
			PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionName;
		}catch(NameNotFoundException e){
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 安装新版本
	 * @param activity
	 */
	public static void installApk(Activity activity){
		Intent intent = new Intent("android.intent.action.VIEW");
		//添加默认分类
		intent.addCategory("android.intent.category.DEFAULT");
		//设置数据和类型
		intent.setDataAndType(Uri.fromFile(new File("/mnt/sdcard/MobileSave2.0.apk")), "application/vnd.android.package-archive");
		//如果开启的Activity退出时会调用当前Activity的onActivityResult
		activity.startActivityForResult(intent, 0);
	}

}

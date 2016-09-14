package com.yuan.mobilesafe.chapter01.utils;

import java.io.File;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * �����ļ��Ĺ�����
 * @author aiyuan
 *
 */


public class DownLoadUtils {
	
	/**
	 * ����apk�ķ���
	 * @param url
	 * @param targerFile
	 * @param myCallBack
	 */
	public void downapk(String url,String targerFile,final MyCallBack myCallBack){
		//����HttpUtils����
		HttpUtils httpUtils = new HttpUtils();
		//����HttpUtils���صķ�������ָ���ļ�
		httpUtils.download(url, targerFile, new RequestCallBack<File>() {

			
			@Override
			public void onSuccess(ResponseInfo<File> arg0) {
				myCallBack.onSuccess(arg0);
			}
			
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				myCallBack.onFailure(arg0, arg1);
			}
			
			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				super.onLoading(total, current, isUploading);
				myCallBack.onLoadding(total, current, isUploading);
			}
		});
	}
	
	/**
	 * �ӿڣ����ڼ�������״̬�Ľӿ�
	 */
	interface MyCallBack{
		/*
		 * ���سɹ�ʱ����
		 */
		void onSuccess(ResponseInfo<File> arg0);
		/**
		 * ����ʧ��ʱ����
		 */
		void onFailure(HttpException arg0,String arg1);
		/**
		 * ����ʱ����
		 */
		void onLoadding(long total,long current,boolean isUploading);
	}

}

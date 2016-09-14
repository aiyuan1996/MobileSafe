package com.yuan.mobilesafe.chapter01.utils;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.yuan.mobilesafe.HomeActivity;
import com.yuan.mobilesafe.chapter01.entity.VersionEntity;
import com.yuan.mobilesafe.chapter01.utils.DownLoadUtils.MyCallBack;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

/**
 * �������ѹ�����
 * ��ӭ�����ȡ�������г���İ汾��
 * ��ʵ�ְ汾�ŶԱ�-->>�����������ѶԻ��� -->>��������APK������ -->>�滻��װ����
 * @author aiyuan
 *
 */
public class VersionUpdateUtils {
	String TAG = "VersionUpdateUtils";
	private static final int MESSAGE_NRT_ERROR = 101;
	private static final int MESSAGE_IO_ERROR = 102;
	private static final int MESSAGE_JSON_ERROR = 103;
	private static final int MESSAGE_SHOW_DIALOG = 104;
	private static final int MESSAGE_ENTERHOME = 105;
	
	//���ذ汾��
	private String mVersion;
	private Activity context;
	private ProgressDialog progressDialog;
	private VersionEntity versionEntity;
	
	public VersionUpdateUtils(String verison,Activity activity) {
		mVersion = verison;
		context = activity;
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MESSAGE_IO_ERROR:
				Toast.makeText(context, "IO����", Toast.LENGTH_LONG).show();
				enterHome();
				break;
			case MESSAGE_JSON_ERROR:
				Toast.makeText(context, "JSON��������", Toast.LENGTH_LONG).show();
				enterHome();
				break;
			case MESSAGE_NRT_ERROR:
				Toast.makeText(context, "�����쳣", Toast.LENGTH_LONG).show();
				enterHome();
				break;
			case MESSAGE_SHOW_DIALOG:
				showUpdateDialog(versionEntity);
				break;
			case MESSAGE_ENTERHOME:
				Intent intent = new Intent(context,HomeActivity.class);
				context.startActivity(intent);
				context.finish();
				break;

			default:
				break;
			}
		}

		
	};
	
	public void getCloudVersion(){
		try {
			HttpClient client = new DefaultHttpClient();
			//���ӳ�ʱ
			HttpConnectionParams.setConnectionTimeout(client.getParams(), 5000);
			//����ʱ
			HttpConnectionParams.setSoTimeout(client.getParams(), 5000);
			HttpGet httpGet = new HttpGet("http://192.168.1.146:8080/updateinfo.html");
			HttpResponse execute = client.execute(httpGet);
			if(execute.getStatusLine().getStatusCode() == 200){
				//�������Ӧ���ɹ���
				HttpEntity entity = execute.getEntity();
				String result = EntityUtils.toString(entity, "utf-8");
				Log.d(TAG, result);
				//����һ��JSONObject����
				JSONObject jsonObject = new JSONObject(result);
				versionEntity = new VersionEntity();
				String code = jsonObject.getString("code");
				versionEntity.versionCode = code;
				String des = jsonObject.getString("des");
				versionEntity.description = des;
				String apkurl = jsonObject.getString("apkurl");
				versionEntity.apkUrl = apkurl;
				Log.d(TAG, mVersion);
				Log.d(TAG, versionEntity.versionCode);
				
				
				if(!mVersion.equals(versionEntity.versionCode)){
					//�汾��һ��
					handler.sendEmptyMessage(MESSAGE_SHOW_DIALOG);
				}
			}
			
		} catch (ClientProtocolException e) {
			handler.sendEmptyMessage(MESSAGE_NRT_ERROR);
			e.printStackTrace();
		}catch (IOException e) {
			handler.sendEmptyMessage(MESSAGE_IO_ERROR);
			e.printStackTrace();
		}catch (JSONException e) {
			handler.sendEmptyMessage(MESSAGE_JSON_ERROR);
			Log.d(TAG, "json error");
			e.printStackTrace();
		}
	}
	
	private void enterHome() {
		handler.sendEmptyMessageDelayed(MESSAGE_ENTERHOME,2000);
	}

	/**
	 * �������¶Ի���
	 * @param versionEntivity
	 */
	protected void showUpdateDialog(final VersionEntity versionEntity) {
		//�����Ի���
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("��鵽�°汾��" + versionEntity.versionCode);
		builder.setMessage(versionEntity.description);
		//���ݷ�����������������������������Ϣ
		
		//���ò��ܵ���ֻ����ذ�ť���ضԻ���
		builder.setCancelable(false);
		builder.setPositiveButton("��������", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				initProgressDialog();
				downLoadNewApk(versionEntity.apkUrl);
			}
		});
		builder.setNegativeButton("�ݲ�����", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				enterHome();
			}
		});
		builder.show();
	}

	/**
	 * �����°汾
	 * @param apkUrl
	 */
	protected void downLoadNewApk(String apkUrl) {
		DownLoadUtils downLoadUtils = new DownLoadUtils();
		downLoadUtils.downapk(apkUrl, "/mnt/sdcard/MobileSafe2.0.apk", new MyCallBack() {
			
			@Override
			public void onSuccess(ResponseInfo<File> arg0) {
				Log.d(TAG, "download finish");
				progressDialog.dismiss();
				MyUtils.installApk(context);
			}
			
			@Override
			public void onLoadding(long total, long current, boolean isUploading) {
				progressDialog.setMax((int) total);
				progressDialog.setMessage("�������ء���");
			}
			
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				progressDialog.setMessage("����ʧ��");
				progressDialog.dismiss();
				enterHome();
			}
		});
	}
	
	/**
	 * ��ʼ���������Ի���
	 */
	protected void initProgressDialog() {
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage("�������ء���");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.show();
	}

}

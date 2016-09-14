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
 * 更新提醒工具类
 * 欢迎界面获取服务器中程序的版本号
 * 【实现版本号对比-->>弹出更新提醒对话框 -->>弹出下载APK进度条 -->>替换安装程序
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
	
	//本地版本号
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
				Toast.makeText(context, "IO错误", Toast.LENGTH_LONG).show();
				enterHome();
				break;
			case MESSAGE_JSON_ERROR:
				Toast.makeText(context, "JSON解析错误", Toast.LENGTH_LONG).show();
				enterHome();
				break;
			case MESSAGE_NRT_ERROR:
				Toast.makeText(context, "网络异常", Toast.LENGTH_LONG).show();
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
			//连接超时
			HttpConnectionParams.setConnectionTimeout(client.getParams(), 5000);
			//请求超时
			HttpConnectionParams.setSoTimeout(client.getParams(), 5000);
			HttpGet httpGet = new HttpGet("http://192.168.1.146:8080/updateinfo.html");
			HttpResponse execute = client.execute(httpGet);
			if(execute.getStatusLine().getStatusCode() == 200){
				//请求和相应都成功了
				HttpEntity entity = execute.getEntity();
				String result = EntityUtils.toString(entity, "utf-8");
				Log.d(TAG, result);
				//创建一个JSONObject对象
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
					//版本不一致
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
	 * 弹出更新对话框
	 * @param versionEntivity
	 */
	protected void showUpdateDialog(final VersionEntity versionEntity) {
		//创建对话框
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("检查到新版本：" + versionEntity.versionCode);
		builder.setMessage(versionEntity.description);
		//根据服务器返回描述，设置升级描述信息
		
		//设置不能点击手机返回按钮隐藏对话框
		builder.setCancelable(false);
		builder.setPositiveButton("立即升级", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				initProgressDialog();
				downLoadNewApk(versionEntity.apkUrl);
			}
		});
		builder.setNegativeButton("暂不升级", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				enterHome();
			}
		});
		builder.show();
	}

	/**
	 * 下载新版本
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
				progressDialog.setMessage("正在下载……");
			}
			
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				progressDialog.setMessage("下载失败");
				progressDialog.dismiss();
				enterHome();
			}
		});
	}
	
	/**
	 * 初始化进度条对话框
	 */
	protected void initProgressDialog() {
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage("正在下载……");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.show();
	}

}

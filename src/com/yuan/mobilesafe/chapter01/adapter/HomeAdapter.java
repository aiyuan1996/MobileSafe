package com.yuan.mobilesafe.chapter01.adapter;

import com.yuan.mobilesafe.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Gridview显示界面用到的Adapter
 * @author aiyuan
 *
 */
public class HomeAdapter extends BaseAdapter{
	int[] imageId = {R.drawable.safe, 
			 R.drawable.callmsgsafe, R.drawable.app, R.drawable.trojan, R.drawable.sysoptimize,R.drawable.taskmanager, 
			 R.drawable.netmanager,R.drawable.atools,R.drawable.settings};
	String[] names = { "手机防盗",   "通讯卫士","软件管家","手机杀毒","缓存清理","进程管理", 
			"流量统计", "高级工具", "设置中心" };
	
	private Context context;
	public HomeAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return 9;
	}

	
	@Override
	public Object getItem(int arg0) {
		
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	//设置每个条目的item
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = View.inflate(context, R.layout.item_home, null);
		ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
		TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
		iv_icon.setImageResource(imageId[position]);
		tv_name.setText(names[position]);
		return view;
	}

}

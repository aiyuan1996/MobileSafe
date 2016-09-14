package com.yuan.mobilesafe.chapter01.adapter;

import com.yuan.mobilesafe.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Gridview��ʾ�����õ���Adapter
 * @author aiyuan
 *
 */
public class HomeAdapter extends BaseAdapter{
	int[] imageId = {R.drawable.safe, 
			 R.drawable.callmsgsafe, R.drawable.app, R.drawable.trojan, R.drawable.sysoptimize,R.drawable.taskmanager, 
			 R.drawable.netmanager,R.drawable.atools,R.drawable.settings};
	String[] names = { "�ֻ�����",   "ͨѶ��ʿ","����ܼ�","�ֻ�ɱ��","��������","���̹���", 
			"����ͳ��", "�߼�����", "��������" };
	
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

	//����ÿ����Ŀ��item
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

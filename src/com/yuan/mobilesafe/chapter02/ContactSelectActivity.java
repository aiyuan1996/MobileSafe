package com.yuan.mobilesafe.chapter02;

import java.util.List;

import com.yuan.mobilesafe.R;
import com.yuan.mobilesafe.chapter02.adapter.ContactAdapter;
import com.yuan.mobilesafe.chapter02.entity.ContactInfo;
import com.yuan.mobilesafe.chapter02.utils.ContactInfoParser;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ContactSelectActivity extends Activity implements OnClickListener{
	private ListView mListview;
	private ContactAdapter adapter;
	private List<ContactInfo> systemContacts;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 10:
				if(systemContacts != null){
					adapter = new ContactAdapter(systemContacts, ContactSelectActivity.this);
					mListview.setAdapter(adapter);
				}
				break;

			default:
				break;
			}
		};
	};

	
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_contact_select);
		initview();
	};
	private void initview() {
		((TextView)findViewById(R.id.tv_title)).setText("ѡ����ϵ��");
		ImageView mLeftImgv = (ImageView) findViewById(R.id.imgv_leftbtn);
		mLeftImgv.setOnClickListener(this);
		mLeftImgv.setImageResource(R.drawable.back);
		//���õ�������ɫ
		findViewById(R.id.rl_titlebar).setBackgroundColor(getResources().getColor(R.color.purple));
		mListview = (ListView) findViewById(R.id.lv_contact);
		new Thread(){
			public void run() {
				systemContacts = ContactInfoParser.getSystemContact(ContactSelectActivity.this);
				systemContacts.addAll(ContactInfoParser.getSimContacts(ContactSelectActivity.this));
				handler.sendEmptyMessage(10);
			};
		}.start();
		mListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				ContactInfo item = (ContactInfo) adapter.getItem(position);
				Intent intent = new Intent();
				intent.putExtra("phone", item.phone);
				setResult(0,intent);
				finish();
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgv_leftbtn:
			finish();
			break;

		default:
			break;
		}
	}

}

package com.yuan.mobilesafe.chapter02.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.yuan.mobilesafe.chapter02.entity.ContactInfo;

/**
 * ��ϵ����Ϣ���洢��sqlite���ݿ��У������Ҫ�Ȼ�ȡ����ϵ�˵�id������id��data���в�ѯ��ϵ�������Լ��绰���룬����װ��contactinfo��Ȼ�����list����
 * @author aiyuan
 *
 */
public class ContactInfoParser {
	private static String TAG = "ContactInfoParser";
	public static List<ContactInfo> getSystemContact(Context context){
		ContentResolver resolver = context.getContentResolver();
		//1.��ѯraw_contacts������ϵ�˵�idȡ����
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		Uri dataUri = Uri.parse("content://com.android.contacts/data");
		List<ContactInfo>infos = new ArrayList<ContactInfo>();
		Cursor cursor = resolver.query(uri, new String[]{"contact_id"}, null, null, null);
		while(cursor.moveToNext()){
			String id = cursor.getString(0);
			if(id != null){
				Log.d(TAG,"��ϵ��id��" + id);
				ContactInfo info = new ContactInfo();
				info.id = id;
				//������ϵ�˵�id��ѯdata�������id��������ȡ����
				//ϵͳapi��ѯdata���ʱ�򣬲���������ѯdata�����ǲ�ѯdata�����ͼ
				Cursor dataCursor = resolver.query(dataUri, new String[]{"data1","mimetype"} ,"raw_contact_id=?" ,new String[]{id}, null);
				while(dataCursor.moveToNext()){
					String data1 = dataCursor.getString(0);
					String mimetype = dataCursor.getString(1);
					if("vnd.android.cursor.item/name".equals(mimetype)){
						Log.d(TAG,"����="+ data1);
						info.name = data1;
					}else if("vnd.android.cursor.item/phone_v2".equals(mimetype)){
						Log.d(TAG,"�绰="+ data1);
						info.phone = data1;
					}
				}
				//����������ֻ���Ϊ�գ���������������
				if(TextUtils.isEmpty(info.name) && TextUtils.isEmpty(info.phone))
					continue;
				infos.add(info);
				dataCursor.close();
			}
		}
		cursor.close();
		return infos;
	}
	
	/**
	 * �õ�sim����������ϵ����Ϣ
	 * @param context
	 * @return contactinfo��list����
	 */
	public static  List<ContactInfo> getSimContacts( Context context){
		Uri uri = Uri.parse("content://icc/adn");
		List<ContactInfo> infos = new ArrayList<ContactInfo>();
	    Cursor   mCursor = context.getContentResolver().query(uri, null, null, null, null);
	    if (mCursor != null) {
            while (mCursor.moveToNext()) {
            	ContactInfo info = new ContactInfo();
                // ȡ����ϵ������
                int nameFieldColumnIndex = mCursor.getColumnIndex("name");
                info.name =  mCursor.getString(nameFieldColumnIndex);
                // ȡ�õ绰����
                int numberFieldColumnIndex = mCursor.getColumnIndex("number");
                info.phone= mCursor.getString(numberFieldColumnIndex);
                infos.add(info);
            }
        }
	    mCursor.close();
        return infos;
	}

}

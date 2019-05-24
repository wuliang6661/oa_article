package com.article.oa_article.util.phone;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/2316:42
 * desc   :
 * version: 1.0
 */
public class PhoneUtil {

    // 号码
    public final static String NUM = ContactsContract.CommonDataKinds.Phone.NUMBER;
    // 联系人姓名
    public final static String NAME = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
    // 联系人头像
    public final static String PHOTO = ContactsContract.CommonDataKinds.Photo.PHOTO_URI;


    //上下文对象
    private Context context;
    //联系人提供者的uri
    private Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

    public PhoneUtil(Context context) {
        this.context = context;
    }

    //获取所有联系人
    public List<PhoneDto> getPhone() {
        List<PhoneDto> phoneDtos = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(phoneUri, new String[]{NUM, NAME}, null, null, null);
        while (cursor.moveToNext()) {
            PhoneDto phoneDto = new PhoneDto(cursor.getString(cursor.getColumnIndex(NAME)), cursor.getString(cursor.getColumnIndex(NUM)), "");
            phoneDtos.add(phoneDto);
        }
        return phoneDtos;
    }

    /**
     * 模糊搜索通讯录
     */
    public List<PhoneDto> searchContacts(String keyword) {
        ContentResolver cr = context.getContentResolver();
        List<PhoneDto> phoneDtos = new ArrayList<>();
        if (isPhoneNum(keyword)) {
            Cursor cursorP = cr.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.NUMBER + " like " + "'%" + keyword + "%'",
                    null,
                    null);
            while (cursorP.moveToNext()) {
                String phoneNumber = cursorP.getString(cursorP.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String photo = cursorP.getString(cursorP.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_URI));

                String contactId = cursorP.getString(cursorP.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                Cursor nameC = cr.query(ContactsContract.Contacts.CONTENT_URI, null, ContactsContract.Contacts._ID + "=" + contactId, null, null);
                while (nameC.moveToNext()) {
                    String name = nameC.getString(nameC.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    Log.i(TAG, "name=" + name + "phoneNumber=" + phoneNumber + ",photo=" + photo);
                    PhoneDto phoneDto = new PhoneDto(name, phoneNumber, photo);
                    phoneDtos.add(phoneDto);
                }

            }
            cursorP.close();
        } else {
            Cursor cursorName = cr.query(
                    ContactsContract.Contacts.CONTENT_URI,
                    null,
                    ContactsContract.PhoneLookup.DISPLAY_NAME + " like " + "'%" + keyword + "%'",
                    null,
                    null);
            while (cursorName.moveToNext()) {

                String name = cursorName.getString(
                        cursorName.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String contactId = cursorName.getString(cursorName.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
                while (phone.moveToNext()) {

                    String phoneNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    String photo = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_URI));
                    Log.i(TAG, "name=" + name + "phoneNumber=" + phoneNumber + ",photo=" + photo);

                    PhoneDto phoneDto = new PhoneDto(name, phoneNumber, photo);
                    phoneDtos.add(phoneDto);
                }

            }
            cursorName.close();
        }


        return phoneDtos;
    }


    private boolean isPhoneNum(String keyword) {
        //正则 匹配以数字或者加号开头的字符串(包括了带空格及-分割的号码
        if (keyword.matches("^([0-9]|[/+]).*")) {
            return true;
        } else {
            return false;
        }
    }

}

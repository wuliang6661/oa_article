package com.wul.oa_article.util;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PhotoUtils {


    /**
     * 通过相册获取图片
     */
    private static void takePictureByPic(Activity context, int requestCode) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 没有获得授权，申请授权
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // 返回值：
                //如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
                //如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
                //如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
                // 弹窗需要解释为何需要该权限，再次请求授权
                Toast.makeText(context, "请授权！", Toast.LENGTH_LONG).show();
                // 帮跳转到该应用的设置界面，让用户手动授权
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri1 = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri1);
                context.startActivity(intent);
            } else {
                // 不需要解释为何需要该权限，直接请求授权
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        } else {
            // 已经获得授权，可以获取图片
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            context.startActivityForResult(intent, requestCode);
        }
    }


    /**
     * 通过相机访问图片
     */
    public static void takePictureByCamera(Activity context, int requestCode) {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.CAMERA},
                    1);
        } else {
            Intent intent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE, null);
            context.startActivityForResult(intent, requestCode);
        }
    }


    /**
     * 获取图片路径
     */
    public static String getUriPath(Activity activity, Uri uri) {
        String data = null;
        if (null == uri)
            return null;
        final String scheme = uri.getScheme();
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = activity.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }


    public static String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


}

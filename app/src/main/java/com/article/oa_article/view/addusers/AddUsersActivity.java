package com.article.oa_article.view.addusers;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.article.oa_article.R;
import com.article.oa_article.mvp.MVPBaseActivity;
import com.article.oa_article.util.phone.PhoneDto;
import com.article.oa_article.util.phone.PhoneUtil;

import java.util.List;


/**
 * MVPPlugin
 * 批量从通讯录添加好友
 */

public class AddUsersActivity extends MVPBaseActivity<AddUsersContract.View, AddUsersPresenter>
        implements AddUsersContract.View {

    @Override
    protected int getLayout() {
        return R.layout.act_add_users;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goBack();
        setTitleText("添加手机通讯录");


        requestPermission();
    }


    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {//未开启定位权限
            //开启定位权限,200是标识码
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 200);
        } else {
            getPersonList();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 200://刚才的识别码
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//用户同意权限,执行我们的操作
                    getPersonList();
                } else {//用户拒绝之后,当然我们也可以弹出一个窗口,直接跳转到系统设置页面
                    Toast.makeText(this, "未开启通讯录权限,请手动到设置去开启权限", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }


    /**
     * 获取通讯录用户
     */
    private void getPersonList() {
        List<PhoneDto> phones = new PhoneUtil(this).getPhone();
    }


}

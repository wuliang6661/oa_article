package com.article.oa_article.view.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.article.oa_article.R;
import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.http.PersonServiceImpl;
import com.article.oa_article.base.BaseActivity;
import com.article.oa_article.bean.request.PersonPasswordRequest;
import com.blankj.utilcode.util.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 更改密码
 */
public class PasswordSettingAct extends BaseActivity {

    @BindView(R.id.edit_old_password)
    EditText editOldPassword;
    @BindView(R.id.edit_new_password)
    EditText editNewPassword;
    @BindView(R.id.edit_password_commrit)
    EditText editPasswordCommrit;

    @Override
    protected int getLayout() {
        return R.layout.act_setpassword;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("修改登录密码");
    }


    @OnClick(R.id.next_button)
    public void commit() {
        String oldPass = editOldPassword.getText().toString().trim();
        String newPass = editNewPassword.getText().toString().trim();
        String comprimPass = editPasswordCommrit.getText().toString().trim();

        if (StringUtils.isEmpty(oldPass)) {
            showToast("请输入旧密码！");
            return;
        }
        if (StringUtils.isEmpty(newPass)) {
            showToast("请输入新密码！");
            return;
        }
        if (StringUtils.isEmpty(comprimPass)) {
            showToast("请再次输入新密码！");
            return;
        }
        if (oldPass.equals(newPass)) {
            showToast("新密码不能跟旧密码一致！");
            return;
        }
        if (!newPass.equals(comprimPass)) {
            showToast("两次输入新密码不一致！");
            return;
        }
        updatePassword(oldPass, newPass, comprimPass);
    }


    private void updatePassword(String ordPass, String newPass, String passComrim) {
        PersonPasswordRequest request = new PersonPasswordRequest();
        request.setOldPassword(ordPass);
        request.setNewPassword(newPass);
        request.setConfirmPassword(passComrim);
        PersonServiceImpl.updatePassword(request).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                finish();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }

}

package com.article.oa_article.view.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.article.oa_article.R;
import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.http.PersonServiceImpl;
import com.article.oa_article.base.BaseActivity;
import com.blankj.utilcode.util.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 修改昵称
 */
public class PersonNameSettingAct extends BaseActivity {

    @BindView(R.id.edit_person_name)
    EditText editPersonName;

    @Override
    protected int getLayout() {
        return R.layout.act_person_name;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("修改昵称");
    }


    @OnClick(R.id.next_button)
    public void commit() {
        String name = editPersonName.getText().toString().trim();
        if (StringUtils.isEmpty(name)) {
            showToast("请输入昵称！");
            return;
        }
        if (name.length() < 2) {
            showToast("昵称不能少于2位");
            return;
        }
        updateName(name);
    }


    private void updateName(String name) {
        PersonServiceImpl.updateNikeName(name).subscribe(new HttpResultSubscriber<String>() {
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

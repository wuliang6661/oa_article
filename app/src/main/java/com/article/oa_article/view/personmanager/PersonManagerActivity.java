package com.article.oa_article.view.personmanager;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.article.oa_article.R;
import com.article.oa_article.bean.BumenBO;
import com.article.oa_article.mvp.MVPBaseActivity;
import com.article.oa_article.view.addusers.AddUsersActivity;
import com.article.oa_article.view.main.personlist.PopPersonAdd;
import com.article.oa_article.view.moveaddperson.MoveAddPersonActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 员工管理
 */

public class PersonManagerActivity extends MVPBaseActivity<PersonManagerContract.View, PersonManagerPresenter>
        implements PersonManagerContract.View {

    @BindView(R.id.btn_album)
    Button btnAlbum;
    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.person_list)
    ExpandableListView personList;

    @Override
    protected int getLayout() {
        return R.layout.act_person_manager;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("员工管理");
        btnAlbum.setVisibility(View.VISIBLE);
        btnAlbum.setText("邀请同事");

        setListener();
        mPresenter.getNeiUsers("");
    }


    private void setListener() {
        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPresenter.getNeiUsers(editName.getText().toString().trim());
            }
        });
    }


    @OnClick(R.id.btn_album)
    public void btn() {
        PopPersonAdd addPop = new PopPersonAdd(this);
        addPop.setListener(new PopPersonAdd.onCommitListener() {
            @Override
            public void shoudongAdd() {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isNeiBu", true);
                gotoActivity(MoveAddPersonActivity.class, bundle, false);
            }

            @Override
            public void piliangAdd() {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isNeiBu", true);
                gotoActivity(AddUsersActivity.class, bundle, false);
            }
        });
        addPop.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }


    @Override
    public void getPersonListByNeiBu(List<BumenBO> list) {
        PersonExpandAdapter adapter = new PersonExpandAdapter(this, list, false);
        personList.setAdapter(adapter);
        for (int i = 0; i < list.size(); i++) {
            personList.expandGroup(i);
        }
    }

    @Override
    public void onRequestError(String msg) {
        showToast(msg);
    }
}

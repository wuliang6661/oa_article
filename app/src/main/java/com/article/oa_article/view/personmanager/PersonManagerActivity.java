package com.article.oa_article.view.personmanager;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.article.oa_article.R;
import com.article.oa_article.mvp.MVPBaseActivity;

import butterknife.BindView;


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
    }
}

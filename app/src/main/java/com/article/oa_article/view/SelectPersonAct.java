package com.article.oa_article.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.FragmentUtils;
import com.article.oa_article.R;
import com.article.oa_article.base.BaseActivity;
import com.article.oa_article.view.main.personlist.PersonListFragment;

public class SelectPersonAct extends BaseActivity {


    @Override
    protected int getLayout() {
        return R.layout.act_person;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("选择执行人");

        PersonListFragment fragment = new PersonListFragment();
        fragment.setIsSelectPerson(true);
        FragmentUtils.replace(getSupportFragmentManager(), fragment, R.id.person_list);
    }
}

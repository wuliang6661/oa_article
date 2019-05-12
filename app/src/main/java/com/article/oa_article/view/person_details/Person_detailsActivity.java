package com.article.oa_article.view.person_details;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.article.oa_article.R;
import com.article.oa_article.mvp.MVPBaseActivity;


/**
 * MVPPlugin
 * 名片
 */

public class Person_detailsActivity extends MVPBaseActivity<Person_detailsContract.View, Person_detailsPresenter>
        implements Person_detailsContract.View {

    @Override
    protected int getLayout() {
        return R.layout.act_person_details;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("");


    }
}

package com.article.oa_article.view.optionsfankui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.article.oa_article.R;
import com.article.oa_article.mvp.MVPBaseActivity;

import butterknife.BindView;


/**
 * MVPPlugin
 * 意见反馈
 */

public class OptionsFankuiActivity extends MVPBaseActivity<OptionsFankuiContract.View, OptionsFankuiPresenter>
        implements OptionsFankuiContract.View {

    @BindView(R.id.fankui_type)
    LinearLayout fankuiType;
    @BindView(R.id.edit_message)
    EditText editMessage;
    @BindView(R.id.edit_lianxifangshi)
    EditText editLianxifangshi;
    @BindView(R.id.image_recycle)
    RecyclerView imageRecycle;
    @BindView(R.id.next_button)
    Button nextButton;

    @Override
    protected int getLayout() {
        return R.layout.act_options_fankui;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("意见反馈");
    }
}

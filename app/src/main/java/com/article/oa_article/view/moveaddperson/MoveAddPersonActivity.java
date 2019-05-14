package com.article.oa_article.view.moveaddperson;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.mvp.MVPBaseActivity;
import com.article.oa_article.view.bumen.BumenActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MoveAddPersonActivity extends MVPBaseActivity<MoveAddPersonContract.View, MoveAddPersonPresenter>
        implements MoveAddPersonContract.View {

    @BindView(R.id.edit_phone_num)
    EditText editPhoneNum;
    @BindView(R.id.bumen_name)
    TextView bumenName;
    @BindView(R.id.select_bumen)
    RelativeLayout selectBumen;
    @BindView(R.id.waibu_flow_name)
    TextView waibuFlowName;
    @BindView(R.id.select_waibu_flow)
    RelativeLayout selectWaibuFlow;
    @BindView(R.id.hint_message_layout)
    TextView hintMessageLayout;
    @BindView(R.id.next_button)
    Button nextButton;
    @BindView(R.id.waibu_layout)
    LinearLayout waibuLayout;
    @BindView(R.id.waibu_line)
    View waibuLine;

    private boolean isNeiBu = true;   //默认添加内部联系人

    @Override
    protected int getLayout() {
        return R.layout.act_moveadd_person;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("手动添加");

        isNeiBu = getIntent().getExtras().getBoolean("isNeiBu");
        if (isNeiBu) {
            hintMessageLayout.setVisibility(View.GONE);
            waibuLayout.setVisibility(View.GONE);
            waibuLine.setVisibility(View.GONE);
        } else {
            hintMessageLayout.setVisibility(View.VISIBLE);
            waibuLayout.setVisibility(View.VISIBLE);
            waibuLine.setVisibility(View.VISIBLE);
        }
    }


    @OnClick(R.id.next_button)
    public void savePerson() {

    }


    @OnClick(R.id.select_bumen)
    public void selectBumen() {
        gotoActivity(BumenActivity.class, false);
    }


}

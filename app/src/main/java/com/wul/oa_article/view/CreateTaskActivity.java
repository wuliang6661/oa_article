package com.wul.oa_article.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.FragmentUtils;
import com.wul.oa_article.R;
import com.wul.oa_article.api.HttpResultSubscriber;
import com.wul.oa_article.api.HttpServerImpl;
import com.wul.oa_article.base.BaseActivity;
import com.wul.oa_article.bean.request.IdRequest;
import com.wul.oa_article.module.create_order.CreateOrderFragment;
import com.wul.oa_article.module.task_allot.Task_allotFragment;
import com.wul.oa_article.widget.AlertDialog;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 创建任务
 */

public class CreateTaskActivity extends BaseActivity {


    @BindView(R.id.kehu_order_check)
    CheckBox kehuOrderCheck;
    @BindView(R.id.order_edit)
    TextView orderEdit;
    @BindView(R.id.kehu_msg_bar)
    LinearLayout kehuMsgBar;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @BindView(R.id.btn_album)
    Button btnAlbum;

    int orderId;


    @Override
    protected int getLayout() {
        return R.layout.act_create_task;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("订单详情");
        orderId = getIntent().getExtras().getInt("id");
        btnAlbum.setText("取消订单");
        btnAlbum.setVisibility(View.VISIBLE);
        FragmentUtils.replace(getSupportFragmentManager(), Task_allotFragment.newInstance(0, orderId), R.id.task_allot);
    }

    @OnClick(R.id.kehu_msg_bar)
    public void barClick(View view) {
        fragmentContainer.setVisibility(View.VISIBLE);
        kehuMsgBar.setVisibility(View.GONE);
        FragmentUtils.replace(getSupportFragmentManager(), CreateOrderFragment.newInstance(2, orderId), R.id.fragment_container);
    }


    @OnClick(R.id.btn_album)
    public void cancleOrderClick() {
        new AlertDialog(Objects.requireNonNull(this)).builder().setGone().setMsg("是否确定取消订单？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", v -> cancleOrder()).show();
    }


    private void cancleOrder() {
        IdRequest request = new IdRequest();
        request.setId(orderId);
        HttpServerImpl.cancleOrder(request).subscribe(new HttpResultSubscriber<String>() {
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

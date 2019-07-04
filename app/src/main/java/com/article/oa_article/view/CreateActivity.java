package com.article.oa_article.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.article.oa_article.R;
import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.HttpServerImpl;
import com.article.oa_article.base.BaseActivity;
import com.article.oa_article.bean.ClientOrderBo;
import com.article.oa_article.bean.OrderInfoBo;
import com.article.oa_article.bean.request.IdRequest;
import com.article.oa_article.bean.request.IdTypeRequest;
import com.article.oa_article.module.create_order.CreateOrderFragment;
import com.article.oa_article.widget.AlertDialog;
import com.blankj.utilcode.util.FragmentUtils;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建订单页面
 */
public class CreateActivity extends BaseActivity {


    ClientOrderBo clientOrderBo;
    int taskId;
    @BindView(R.id.btn_album)
    Button btnAlbum;
    @BindView(R.id.next_button)
    Button nextButton;

    CreateOrderFragment fragment;

    boolean isEditOrder;

    @Override
    protected int getLayout() {
        return R.layout.act_create_order;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("创建订单");
        boolean isEditOrder = getIntent().getExtras().getBoolean("isEditOrder", false);
        if (isEditOrder) {
            boolean isOrder = getIntent().getExtras().getBoolean("isOrder", true);
            int id = getIntent().getExtras().getInt("id");
            IdTypeRequest request = new IdTypeRequest();
            request.setId(id);
            if (isOrder) {
                request.setType(0);
            } else {
                request.setType(1);
            }
            getOrderInfo(request);
            nextButton.setText("保存");
        } else {
            boolean isWaiBu = Objects.requireNonNull(getIntent().getExtras()).getBoolean("isWaibu", false);
            if (isWaiBu) {
                clientOrderBo = (ClientOrderBo) getIntent().getExtras().getSerializable("client");
                taskId = getIntent().getExtras().getInt("taskId");
            }
            fragment = new CreateOrderFragment();
            FragmentUtils.replace(getSupportFragmentManager(), fragment, R.id.fragment_container);
            if (isWaiBu) {
                fragment.setClientData(taskId, clientOrderBo);
            }
        }
    }


    @OnClick(R.id.next_button)
    public void commit() {
        fragment.commit();
    }


    @OnClick(R.id.btn_album)
    public void cancleOrder() {
        new AlertDialog(Objects.requireNonNull(this)).builder().setGone().setMsg("是否确定取消订单？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", v -> orderCancle()).show();

    }

    private void orderCancle() {
        IdRequest request = new IdRequest();
        request.setId(infoBo.getOrderInfo().getId());
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


    OrderInfoBo infoBo;

    /**
     * 获取订单详情
     */
    public void getOrderInfo(IdTypeRequest request) {
        HttpServerImpl.getOrderInfo(request).subscribe(new HttpResultSubscriber<OrderInfoBo>() {
            @Override
            public void onSuccess(OrderInfoBo orderInfoBo) {
                infoBo = orderInfoBo;
                CreateOrderFragment fragment = new CreateOrderFragment();
                FragmentUtils.replace(getSupportFragmentManager(), fragment, R.id.fragment_container);
                fragment.setData(2, orderInfoBo);   //编辑订单
                btnAlbum.setText("取消订单");
                btnAlbum.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }

}

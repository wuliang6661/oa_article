package com.wul.oa_article.module.order_details;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wul.oa_article.R;
import com.wul.oa_article.mvp.MVPBaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 订单详情模块
 */

public class Order_detailsFragment extends MVPBaseFragment<Order_detailsContract.View, Order_detailsPresenter>
        implements Order_detailsContract.View {


    @BindView(R.id.kehu_order_check)
    CheckBox kehuOrderCheck;
    @BindView(R.id.kehu_msg_bar)
    LinearLayout kehuMsgBar;
    @BindView(R.id.create_name)
    TextView createName;
    @BindView(R.id.kehu_name)
    TextView kehuName;
    @BindView(R.id.kehu_order_num)
    TextView kehuOrderNum;
    @BindView(R.id.kehu_order_name)
    TextView kehuOrderName;
    @BindView(R.id.order_num)
    TextView orderNum;
    @BindView(R.id.order_danwei)
    TextView orderDanwei;
    @BindView(R.id.order_date)
    TextView orderDate;
    @BindView(R.id.chicun_recycle_view)
    RecyclerView chicunRecycleView;
    @BindView(R.id.beizhu)
    TextView beizhu;
    @BindView(R.id.image_recycle)
    RecyclerView imageRecycle;
    @BindView(R.id.kehu_order_layout)
    LinearLayout kehuOrderLayout;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_order_details, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }


    /**
     * 初始化布局
     */
    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        chicunRecycleView.setLayoutManager(manager);

        GridLayoutManager imageManager = new GridLayoutManager(getActivity(), 3);
        imageRecycle.setLayoutManager(imageManager);

        chicunRecycleView.setNestedScrollingEnabled(false);
        imageRecycle.setNestedScrollingEnabled(false);
    }

    @OnClick(R.id.kehu_msg_bar)
    public void barOnClick() {
        if (kehuOrderLayout.getVisibility() == View.VISIBLE) {
            kehuOrderLayout.setVisibility(View.GONE);
            kehuOrderCheck.setChecked(true);
        } else {
            kehuOrderLayout.setVisibility(View.VISIBLE);
            kehuOrderCheck.setChecked(false);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

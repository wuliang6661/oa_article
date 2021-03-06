package com.article.oa_article.view.main.home;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.HttpServerImpl;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.AcceptedOrderBo;
import com.article.oa_article.bean.event.MsgNumEvent;
import com.article.oa_article.bean.event.OpenDrawableEvent;
import com.article.oa_article.bean.request.AsseptRequest;
import com.article.oa_article.mvp.MVPBaseFragment;
import com.article.oa_article.view.CreateActivity;
import com.article.oa_article.view.FragmentPaerAdapter;
import com.article.oa_article.view.SelectActivity;
import com.article.oa_article.view.SelectOrderActivity;
import com.article.oa_article.view.main.home.accepted.AcceptedFragment;
import com.article.oa_article.view.main.home.compony.ComponyFragment;
import com.article.oa_article.view.main.home.myorder.MyOrderFragment;
import com.article.oa_article.view.mobanmanager.MobanManagerActivity;
import com.article.oa_article.widget.HomeAddPopWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class HomeFragment extends MVPBaseFragment<HomeContract.View, HomePresenter>
        implements HomeContract.View {


    @BindView(R.id.view_pager)
    ViewPager viewPager;
    Unbinder unbinder;
    @BindView(R.id.shaixuan_img)
    ImageView shaixuanImg;
    @BindView(R.id.add_img)
    ImageView addImg;
    @BindView(R.id.my_order)
    TextView myOrder;
    @BindView(R.id.gongsi_order)
    TextView gongsiOrder;
    @BindView(R.id.unknow_order)
    TextView unknowOrder;

    List<Fragment> fragments;
    TextView[] texts;

    MyOrderFragment myOrderFragment;
    ComponyFragment componyFragment;
    AcceptedFragment acceptedFragment;
    @BindView(R.id.unknow_order_layout)
    RelativeLayout unknowOrderLayout;
    @BindView(R.id.msg_text)
    TextView msgText;
    @BindView(R.id.edit_select)
    TextView editSelect;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_home, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EventBus.getDefault().register(this);
        fragments = new ArrayList<>();
        myOrderFragment = new MyOrderFragment();
        componyFragment = new ComponyFragment();
        acceptedFragment = new AcceptedFragment();
        fragments.add(myOrderFragment);
        fragments.add(componyFragment);
        fragments.add(acceptedFragment);
        viewPager.setAdapter(new FragmentPaerAdapter(getFragmentManager(), fragments));
        viewPager.setOffscreenPageLimit(3);

        texts = new TextView[]{myOrder, gongsiOrder, unknowOrder};
        setPagerListener();
    }


    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        getAsseptOrder();
    }

    /**
     * 设置viewpager监听
     */
    private void setPagerListener() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0 || i == 2) {
                    editSelect.setText("搜索任务");
                } else {
                    editSelect.setText("搜索订单");
                }
                setTextStyle(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        viewPager.setOffscreenPageLimit(3);
    }


    private void setTextStyle(int select) {
        for (int i = 0; i < texts.length; i++) {
            if (select == i) {
                texts[i].setTextColor(Color.parseColor("#4166BE"));
            } else {
                texts[i].setTextColor(Color.parseColor("#656565"));
            }
        }
    }


    @OnClick({R.id.my_order, R.id.gongsi_order, R.id.unknow_order_layout})
    public void orderClick(View view) {
        switch (view.getId()) {
            case R.id.my_order:
                setTextStyle(0);
                viewPager.setCurrentItem(0);
                editSelect.setText("搜索任务");
                break;
            case R.id.gongsi_order:
                setTextStyle(1);
                viewPager.setCurrentItem(1);
                editSelect.setText("搜索订单");
                break;
            case R.id.unknow_order_layout:
                setTextStyle(2);
                viewPager.setCurrentItem(2);
                editSelect.setText("搜索任务");
                break;
        }
    }

    @OnClick(R.id.select_layout)
    public void goSelect() {
        int count = viewPager.getCurrentItem();
        if (count == 0 || count == 2) {
            gotoActivity(SelectActivity.class, false);
        } else {
            gotoActivity(SelectOrderActivity.class, false);
        }
    }


    @OnClick(R.id.shaixuan_img)
    public void shaixuan() {
        OpenDrawableEvent event = new OpenDrawableEvent();
        switch (viewPager.getCurrentItem()) {
            case 0:
                event.type = myOrderFragment.getPosition();
                EventBus.getDefault().post(event);
                break;
            case 1:    //公司订单
                event.type = 4;
                EventBus.getDefault().post(event);
                break;
            case 2:    //待接受
                event.type = 5;
                EventBus.getDefault().post(event);
                break;
        }
    }


    @OnClick(R.id.add_img)
    public void add() {
        HomeAddPopWindow popWindow = new HomeAddPopWindow(getActivity());
        popWindow.setListener(new HomeAddPopWindow.OnClickListener() {
            @Override
            public void clickCreateOrder() {
                if (!MyApplication.isHaveCommon()) {
                    showToast("暂无企业，不能操作！");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putBoolean("isWaibu", false);
                gotoActivity(CreateActivity.class, bundle, false);
            }

            @Override
            public void clickCreateMoBan() {
                if (!MyApplication.isHaveCommon()) {
                    showToast("暂无企业，不能操作！");
                    return;
                }
                Intent intent = new Intent(getActivity(), MobanManagerActivity.class);
                intent.putExtra("isShowMake", false);
                startActivity(intent);
            }
        });
        popWindow.showPop(addImg, 0, 20);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MsgNumEvent event) {
        if (event.num > 0) {
            msgText.setVisibility(View.VISIBLE);
            msgText.setText(event.num + "");
        } else {
            msgText.setVisibility(View.GONE);
            msgText.setText("");
        }
    }


    /**
     * 获取待接受的订单列表
     */
    private void getAsseptOrder() {
        AsseptRequest request = new AsseptRequest();
        request.setPageNum(1);
        request.setPageSize(1000);
        HttpServerImpl.getAsseptOrder(request).subscribe(new HttpResultSubscriber<List<AcceptedOrderBo>>() {
            @Override
            public void onSuccess(List<AcceptedOrderBo> s) {
                MsgNumEvent event = new MsgNumEvent();
                event.num = s.size();
                onEvent(event);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}

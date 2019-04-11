package com.wul.oa_article.view.main.home;


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

import com.wul.oa_article.R;
import com.wul.oa_article.bean.event.MsgNumEvent;
import com.wul.oa_article.bean.event.OpenDrawableEvent;
import com.wul.oa_article.mvp.MVPBaseFragment;
import com.wul.oa_article.view.FragmentPaerAdapter;
import com.wul.oa_article.view.SelectActivity;
import com.wul.oa_article.view.createorder.CreateActivity;
import com.wul.oa_article.view.main.home.accepted.AcceptedFragment;
import com.wul.oa_article.view.main.home.compony.ComponyFragment;
import com.wul.oa_article.view.main.home.myorder.MyOrderFragment;
import com.wul.oa_article.widget.HomeAddPopWindow;

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

        texts = new TextView[]{myOrder, gongsiOrder, unknowOrder};
        setPagerListener();
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
                break;
            case R.id.gongsi_order:
                setTextStyle(1);
                viewPager.setCurrentItem(1);
                break;
            case R.id.unknow_order_layout:
                setTextStyle(2);
                viewPager.setCurrentItem(2);
                break;
        }
    }

    @OnClick(R.id.select_layout)
    public void goSelect() {
        gotoActivity(SelectActivity.class, false);
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
                gotoActivity(CreateActivity.class, false);
            }

            @Override
            public void clickCreateMoBan() {

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

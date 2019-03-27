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
import android.widget.TextView;

import com.wul.oa_article.R;
import com.wul.oa_article.mvp.MVPBaseFragment;
import com.wul.oa_article.view.FragmentPaerAdapter;
import com.wul.oa_article.view.main.home.accepted.AcceptedFragment;
import com.wul.oa_article.view.main.home.compony.ComponyFragment;
import com.wul.oa_article.view.main.home.myorder.MyOrderFragment;

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

        fragments = new ArrayList<>();
        fragments.add(new MyOrderFragment());
        fragments.add(new ComponyFragment());
        fragments.add(new AcceptedFragment());
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


    @OnClick({R.id.my_order, R.id.gongsi_order, R.id.unknow_order})
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
            case R.id.unknow_order:
                setTextStyle(2);
                viewPager.setCurrentItem(2);
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


//    if (!drawerLayout.isDrawerOpen(GravityCompat.END)) {
//        drawerLayout.openDrawer(GravityCompat.END);
//    }

}

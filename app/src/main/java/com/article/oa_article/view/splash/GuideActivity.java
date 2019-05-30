package com.article.oa_article.view.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.article.oa_article.R;
import com.article.oa_article.base.BaseActivity;
import com.article.oa_article.view.FragmentPaerAdapter;
import com.article.oa_article.view.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/3014:16
 * desc   :
 * version: 1.0
 */
public class GuideActivity extends BaseActivity {


    @BindView(R.id.vp_guide)
    ViewPager vpGuide;
    @BindView(R.id.ll_container)
    LinearLayout llContainer;
    @BindView(R.id.iv_red)
    ImageView ivRed;
    @BindView(R.id.point_layout)
    RelativeLayout pointLayout;
    @BindView(R.id.start_btn)
    Button startBtn;

    List<Fragment> fragments;
    private int mPaintDis;

    @Override
    protected int getLayout() {
        return R.layout.act_guide;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SplashFragment fragment1 = SplashFragment.getInstance(0);
        SplashFragment fragment2 = SplashFragment.getInstance(1);
        SplashFragment fragment3 = SplashFragment.getInstance(2);

        fragments = new ArrayList<>();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);

        initData();
        FragmentPaerAdapter adapter = new FragmentPaerAdapter(getSupportFragmentManager(), fragments);
        vpGuide.setAdapter(adapter);

        //监听布局是否已经完成  布局的位置是否已经确定
        ivRed.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //避免重复回调        出于兼容性考虑，使用了过时的方法
                ivRed.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //布局完成了就获取第一个小灰点和第二个之间left的距离
                mPaintDis = llContainer.getChildAt(1).getLeft() - llContainer.getChildAt(0).getLeft();
            }
        });
        //ViewPager滑动Pager监听
        vpGuide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //滑动过程中的回调
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //当滑到第二个Pager的时候，positionOffset百分比会变成0，position会变成1，所以后面要加上position*mPaintDis
                int letfMargin = (int) (mPaintDis * positionOffset) + position * mPaintDis;
                //在父布局控件中设置他的leftMargin边距
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivRed.getLayoutParams();
                params.leftMargin = letfMargin;
                ivRed.setLayoutParams(params);
            }


            /**
             * 设置按钮最后一页显示，其他页面隐藏
             * @param position
             */
            @Override
            public void onPageSelected(int position) {
                System.out.println("position:" + position);
                if (position == fragments.size() - 1) {
                    startBtn.setVisibility(View.VISIBLE);
                } else {
                    startBtn.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                System.out.println("state:" + state);
            }
        });
    }


    protected void initData() {
        for (int i = 0; i < fragments.size(); i++) {
            //小圆点
            ImageView pointView = new ImageView(this);
            pointView.setImageResource(R.drawable.shape_point1);
            //初始化布局参数，父控件是谁，就初始化谁的布局参数
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i > 0) {
                //当添加的小圆点的个数超过一个的时候就设置当前小圆点的左边距为20dp;
                params.leftMargin = 35;
            }
            //设置小灰点的宽高包裹内容
            pointView.setLayoutParams(params);
            //将小灰点添加到LinearLayout中
            llContainer.addView(pointView);
        }
    }


    @OnClick(R.id.start_btn)
    public void start() {
        gotoActivity(LoginActivity.class, true);
    }
}

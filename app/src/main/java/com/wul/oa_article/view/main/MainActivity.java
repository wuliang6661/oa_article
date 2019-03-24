package com.wul.oa_article.view.main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;

import com.wul.oa_article.R;
import com.wul.oa_article.mvp.MVPBaseActivity;
import com.wul.oa_article.util.AppManager;
import com.xyz.tabitem.BottmTabItem;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MainActivity extends MVPBaseActivity<MainContract.View, MainPresenter>
        implements MainContract.View, View.OnClickListener {

    @BindView(R.id.main1)
    BottmTabItem main1;
    @BindView(R.id.main2)
    BottmTabItem main2;
    @BindView(R.id.main3)
    BottmTabItem main3;
    @BindView(R.id.main4)
    BottmTabItem main4;
    @BindView(R.id.main5)
    BottmTabItem main5;

    private int selectPosition = 0;
    private BottmTabItem[] buttms;
    private SupportFragment[] mFragments = new SupportFragment[5];

    @Override
    protected int getLayout() {
        return R.layout.act_main;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buttms = new BottmTabItem[]{main1, main2, main3, main4, main5};
        initFragment();
        main1.setOnClickListener(this);
        main2.setOnClickListener(this);
        main3.setOnClickListener(this);
        main4.setOnClickListener(this);
        main5.setOnClickListener(this);
    }


    /**
     * 初始化fragment
     */
    private void initFragment() {
        SupportFragment firstFragment = findFragment(NoneFragment1.class);
        if (firstFragment == null) {
            mFragments[0] = NoneFragment1.newInstance();
            mFragments[1] = new NoneFragment2();
            mFragments[2] = new NoneFragment3();
            mFragments[3] = new NoneFragment4();
            mFragments[4] = new NoneFragment5();

            loadMultipleRootFragment(R.id.fragment_container, 0,
                    mFragments[0],
                    mFragments[1],
                    mFragments[2],
                    mFragments[3],
                    mFragments[4]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用
            mFragments[0] = firstFragment;
            mFragments[1] = findFragment(NoneFragment2.class);
            mFragments[2] = findFragment(NoneFragment3.class);
            mFragments[3] = findFragment(NoneFragment4.class);
            mFragments[4] = findFragment(NoneFragment5.class);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main1:
                showHideFragment(mFragments[0], mFragments[selectPosition]);
                selectPosition = 0;
                setButtom(0);
                break;
            case R.id.main2:
                showHideFragment(mFragments[1], mFragments[selectPosition]);
                selectPosition = 1;
                setButtom(1);
                break;
            case R.id.main3:
                showHideFragment(mFragments[2], mFragments[selectPosition]);
                selectPosition = 2;
                setButtom(2);
                break;
            case R.id.main4:
                showHideFragment(mFragments[3], mFragments[selectPosition]);
                selectPosition = 3;
                setButtom(3);
                break;
            case R.id.main5:
                showHideFragment(mFragments[4], mFragments[selectPosition]);
                selectPosition = 4;
                setButtom(4);
                break;
        }
    }


    /**
     * 设置底部按钮显示
     */
    private void setButtom(int position) {
        for (int i = 0; i < buttms.length; i++) {
            if (position == i) {
                buttms[i].setSelectState(true);
            } else {
                buttms[i].setSelectState(false);
            }
        }
    }


    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    showToast("再按一次退出程序");
                    firstTime = secondTime;
                    return true;
                } else {
                    AppManager.getAppManager().finishAllActivity();
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }
}

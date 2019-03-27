package com.wul.oa_article.view.main;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.TimeUtils;
import com.wul.oa_article.R;
import com.wul.oa_article.mvp.MVPBaseActivity;
import com.wul.oa_article.util.AppManager;
import com.wul.oa_article.view.main.none.NoneFragment1;
import com.wul.oa_article.view.main.none.NoneFragment2;
import com.wul.oa_article.view.main.none.NoneFragment3;
import com.wul.oa_article.view.main.none.NoneFragment4;
import com.wul.oa_article.view.main.none.NoneFragment5;
import com.xyz.tabitem.BottmTabItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
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
    @BindView(R.id.create_time_start)
    TextView createTimeStart;
    @BindView(R.id.create_time_end)
    TextView createTimeEnd;
    @BindView(R.id.stop_time_start)
    TextView stopTimeStart;
    @BindView(R.id.stop_time_end)
    TextView stopTimeEnd;
    @BindView(R.id.task_unfinish)
    RadioButton taskUnfinish;
    @BindView(R.id.task_way)
    RadioButton taskWay;
    @BindView(R.id.task_off)
    RadioButton taskOff;
    @BindView(R.id.radio_group_task)
    RadioGroup radioGroupTask;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private int selectPosition = 0;
    private BottmTabItem[] buttms;
    private SupportFragment[] mFragments = new SupportFragment[5];

    TimePickerView pvTime;

    @SuppressLint("SimpleDateFormat")
    DateFormat format = new SimpleDateFormat("yyyy/MM/dd");


    @Override
    protected int getLayout() {
        return R.layout.act_main_all;
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

        //禁止手势滑出侧滑菜单
//        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
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

    @OnClick({R.id.create_time_start, R.id.create_time_end, R.id.stop_time_start, R.id.stop_time_end})
    public void timeClick(View view) {
        switch (view.getId()) {
            case R.id.create_time_start:
                initTimePicker(0);
                break;
            case R.id.create_time_end:
                initTimePicker(1);
                break;
            case R.id.stop_time_start:
                initTimePicker(2);
                break;
            case R.id.stop_time_end:
                initTimePicker(3);
                break;
        }
    }


    /**
     * 时间选择器
     *
     * @param timeType 0:创建开始时间  1：创建结束时间  2：截止开始时间 3：截止结束时间
     */
    @SuppressLint("SimpleDateFormat")
    private void initTimePicker(int timeType) {
        Calendar startDate = Calendar.getInstance();
//        Calendar endDate = Calendar.getInstance();
//        endDate.set(Calendar.DAY_OF_YEAR, endDate.get(Calendar.DAY_OF_YEAR) + (10 * 365));   //默认设置可选择一年，可配置
        pvTime = new TimePickerBuilder(this, (date, v) -> {
            switch (timeType) {
                case 0:
                    setTimeSelect(createTimeStart, date);
                    break;
                case 1:
                    setTimeSelect(createTimeEnd, date);
                    break;
                case 2:
                    setTimeSelect(stopTimeStart, date);
                    break;
                case 3:
                    setTimeSelect(stopTimeEnd, date);
                    break;
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .setDate(startDate)
                .setLineSpacingMultiplier(1.8f)
                .build();
        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);
            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);
            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.1f);
            }
        }
        pvTime.show();
    }


    /**
     * 设置时间按钮选中之后的样式
     */
    private void setTimeSelect(TextView view, Date date) {
        view.setText(TimeUtils.date2String(date, format));
        view.setTextColor(Color.parseColor("#5678FF"));
        view.setBackgroundResource(R.drawable.menu_item_select);
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

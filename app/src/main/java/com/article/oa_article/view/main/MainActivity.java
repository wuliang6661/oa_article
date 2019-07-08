package com.article.oa_article.view.main;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.SalesBo;
import com.article.oa_article.bean.event.MsgFragmentEvent;
import com.article.oa_article.bean.event.OpenDrawableEvent;
import com.article.oa_article.bean.request.AsseptRequest;
import com.article.oa_article.bean.request.ComplayRequest;
import com.article.oa_article.bean.request.OrderRequest;
import com.article.oa_article.bean.request.SelectRequest;
import com.article.oa_article.mvp.MVPBaseActivity;
import com.article.oa_article.util.AppManager;
import com.article.oa_article.view.main.none.NoneFragment1;
import com.article.oa_article.view.main.none.NoneFragment2;
import com.article.oa_article.view.main.none.NoneFragment3;
import com.article.oa_article.view.main.none.NoneFragment4;
import com.article.oa_article.view.main.none.NoneFragment5;
import com.article.oa_article.widget.PopSplash;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.xyz.tabitem.BottmTabItem;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.leolin.shortcutbadger.ShortcutBadger;
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
    @BindView(R.id.stop_time_text)
    TextView stopTimeText;
    @BindView(R.id.three_text)
    TextView threeText;
    @BindView(R.id.four_text)
    TextView fourText;
    @BindView(R.id.four_radio_bt1)
    RadioButton fourRadioBt1;
    @BindView(R.id.four_radio_bt2)
    RadioButton fourRadioBt2;
    @BindView(R.id.four_radio_layout)
    RadioGroup fourRadioLayout;
    @BindView(R.id.edit_keybord)
    EditText editKeybord;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout idFlowlayout;
    @BindView(R.id.today_point)
    TextView todayPoint;
    @BindView(R.id.last_radio)
    CheckBox lastRadio;

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

        EventBus.getDefault().register(this);
        buttms = new BottmTabItem[]{main1, main2, main3, main4, main5};
        initFragment();
        main1.setOnClickListener(this);
        main2.setOnClickListener(this);
        main3.setOnClickListener(this);
        main4.setOnClickListener(this);
        main5.setOnClickListener(this);

        //禁止手势滑出侧滑菜单
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        SelectRequest request = new SelectRequest();
        request.setPageNum(1 + "");
        request.setPageSize(1000 + "");
        request.setCompanyId(MyApplication.getCommonId());
        request.setType("1");
        mPresenter.getCommplayList(request);

        new Handler().post(() -> {
            if (MyApplication.spUtils.getBoolean("isSplash", true)) {
                PopSplash popSplash = new PopSplash(MainActivity.this);
                popSplash.showAtLocation(drawerLayout, Gravity.TOP, 0, 0);
                MyApplication.spUtils.put("isSplash", false);
            }
        });
        ShortcutBadger.removeCount(this); //for 1.1.4+
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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

    String taskRadio;
    String fourSelector;

    private void setGroupListener() {
        radioGroupTask.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.task_unfinish:
                    taskRadio = "0";
                    break;
                case R.id.task_way:
                    taskRadio = "1";
                    break;
                case R.id.task_off:
                    taskRadio = "2";
                    break;
                case -1:
                    taskUnfinish.setChecked(false);
                    taskWay.setChecked(false);
                    taskOff.setChecked(false);
                    taskRadio = null;
                    break;
            }
            lastRadio.setChecked(false);
        });
        lastRadio.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                radioGroupTask.clearCheck();
                lastRadio.setChecked(true);
                taskRadio = "3";
            }
        });
        fourRadioLayout.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.four_radio_bt1:
                    fourSelector = "0";
                    break;
                case R.id.four_radio_bt2:
                    fourSelector = "1";
                    break;
                case -1:
                    fourRadioBt1.setChecked(false);
                    fourRadioBt1.setChecked(false);
                    fourSelector = null;
                    break;
            }
        });
    }

    CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            switch (compoundButton.getId()) {
                case R.id.task_unfinish:
                    taskRadio = "0";
                    break;
                case R.id.task_way:
                    taskRadio = "1";
                    break;
                case R.id.task_off:
                    taskRadio = "2";
                    break;
                case R.id.last_radio:
                    taskRadio = "3";
                    break;
                case -1:
                    taskUnfinish.setChecked(false);
                    taskWay.setChecked(false);
                    taskOff.setChecked(false);
                    lastRadio.setChecked(false);
                    taskRadio = null;
                    break;
            }
        }
    };


    OpenDrawableEvent event;

    /**
     * 接受调用筛选的页面
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(OpenDrawableEvent event) {
        this.event = event;
        showDrawableType(event.type);
    }


    /**
     * 根据不同类型显示不同侧滑菜单
     */
    private void showDrawableType(int type) {
        setGroupListener();
        stopTimeText.setText("截止日期");
        threeText.setText("任务状态");
        fourText.setText("完成状态");
        fourRadioBt1.setText("已逾期");
        fourRadioBt2.setText("今日到期");
        threeText.setVisibility(View.VISIBLE);
        radioGroupTask.setVisibility(View.VISIBLE);
        fourText.setVisibility(View.VISIBLE);
        fourRadioLayout.setVisibility(View.VISIBLE);
        lastRadio.setVisibility(View.GONE);
        switch (type) {
            case 0:// 我的任务（全部）
                taskUnfinish.setText("未分派");
                taskWay.setText("进行中");
                taskOff.setText("已取消");
                lastRadio.setText("已完成");
                lastRadio.setVisibility(View.VISIBLE);
                setMenu(all, type);
                break;
            case 1:   // 我的任务（我自己的）
                taskUnfinish.setText("进行中");
                taskWay.setText("已取消");
                taskOff.setText("已完成");
                setMenu(myziji, type);
                break;
            case 2:    //我的任务（我分派的）
                taskUnfinish.setText("未接受");
                taskWay.setText("进行中");
                taskOff.setText("已取消");
                lastRadio.setText("已完成");
                lastRadio.setVisibility(View.VISIBLE);
                setMenu(myfenpai, type);
                break;
            case 3:    //我的任务 （已完成）
                threeText.setVisibility(View.GONE);
                radioGroupTask.setVisibility(View.GONE);
                setMenu(wancheng, type);
                break;
            case 4:    // 公司订单
                stopTimeText.setText("交货日期");
                fourText.setVisibility(View.GONE);
                fourRadioLayout.setVisibility(View.GONE);
                threeText.setText("订单状态");
                taskUnfinish.setText("已逾期");
                taskWay.setText("今日到期");
                taskOff.setText("已完成");
                editKeybord.setText(complay.getKeyWord());
                createTimeStart.setText(complay.getCreatStartDate());
                createTimeEnd.setText(complay.getCreatEndDate());
                stopTimeStart.setText(complay.getStartDate());
                stopTimeEnd.setText(complay.getEndDate());
                radioGroupTask.clearCheck();
                fourRadioLayout.clearCheck();
                idFlowlayout.onChanged();
                if (!StringUtils.isEmpty(complay.getTaskType())) {
                    switch (complay.getTaskType()) {
                        case "0":
                            taskUnfinish.setChecked(true);
                            break;
                        case "1":
                            taskWay.setChecked(true);
                            break;
                        case "2":
                            taskOff.setChecked(true);
                            break;
                    }
                }
                break;
            case 5:    // 待接单
                threeText.setVisibility(View.GONE);
                radioGroupTask.setVisibility(View.GONE);
                fourRadioBt1.setText("公司内部");
                fourRadioBt2.setText("公司外部");
                editKeybord.setText(assept.getKeyWord());
                createTimeStart.setText(assept.getCreatStartDate());
                createTimeEnd.setText(assept.getCreatEndDate());
                stopTimeStart.setText(assept.getStartDate());
                stopTimeEnd.setText(assept.getEndDate());
                radioGroupTask.clearCheck();
                fourRadioLayout.clearCheck();
                idFlowlayout.onChanged();
                if (!StringUtils.isEmpty(assept.getTaskType())) {
                    switch (assept.getTaskType()) {
                        case "0":
                            fourRadioBt1.setChecked(true);
                            break;
                        case "1":
                            fourRadioBt2.setChecked(true);
                            break;
                    }
                }
                break;
        }
        if (!drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.openDrawer(GravityCompat.END);
        } else {
            drawerLayout.closeDrawer(GravityCompat.END);
        }
    }


    private void setMenu(OrderRequest request, int type) {
        editKeybord.setText(request.getKeyWord());
        createTimeStart.setText(request.getCreatStartDate());
        createTimeEnd.setText(request.getCreatEndDate());
        stopTimeStart.setText(request.getStartDate());
        stopTimeEnd.setText(request.getEndDate());
        radioGroupTask.clearCheck();
        fourRadioLayout.clearCheck();
        lastRadio.setChecked(false);
        idFlowlayout.onChanged();
        if (!StringUtils.isEmpty(request.getTaskType())) {
            switch (type) {
                case 0:
                    if ("1".equals(request.getTaskType())) {  //未分派
                        taskUnfinish.setChecked(true);
                    } else if ("4".equals(request.getTaskType())) {  //进行中
                        taskWay.setChecked(true);
                    } else if ("5".equals(request.getTaskType())) {   //已取消
                        taskOff.setChecked(true);
                    } else if ("2".equals(request.getTaskType())) {  //已完成
                        lastRadio.setChecked(true);
                    }
                    break;
                case 1:
                    if ("4".equals(request.getTaskType())) {  //未分派
                        taskUnfinish.setChecked(true);
                    } else if ("5".equals(request.getTaskType())) {  //进行中
                        taskWay.setChecked(true);
                    } else if ("2".equals(request.getTaskType())) {   //已取消
                        taskOff.setChecked(true);
                    }
                    break;
                case 2:
                    if ("3".equals(request.getTaskType())) {  //未分派
                        taskUnfinish.setChecked(true);
                    } else if ("4".equals(request.getTaskType())) {  //进行中
                        taskWay.setChecked(true);
                    } else if ("5".equals(request.getTaskType())) {   //已取消
                        taskOff.setChecked(true);
                    } else if ("2".equals(request.getTaskType())) {  //已完成
                        lastRadio.setChecked(true);
                    }
                    break;
            }
        }
        if (!StringUtils.isEmpty(request.getDays())) {
            switch (request.getDays()) {
                case "0":
                    fourRadioBt1.setChecked(true);
                    break;
                case "1":
                    fourRadioBt2.setChecked(true);
                    break;
            }
        }
    }


    @OnClick(R.id.menu_reset)
    public void reset(View view) {
        switch (event.type) {
            case 0:
                all = new OrderRequest();
                all.menuType = "0";
                resetOrder(all);
                break;
            case 1:
                myziji = new OrderRequest();
                myziji.menuType = "1";
                resetOrder(myziji);
                break;
            case 2:
                myfenpai = new OrderRequest();
                myfenpai.menuType = "2";
                resetOrder(myfenpai);
                break;
            case 3:
                wancheng = new OrderRequest();
                wancheng.menuType = "3";
                resetOrder(wancheng);
                break;
            case 4:
                complay = new ComplayRequest();
                initComplay(complay);
                EventBus.getDefault().post(complay);
                break;
            case 5:
                assept = new AsseptRequest();
                initAsset(assept);
                EventBus.getDefault().post(assept);
                break;
        }
        showDrawableType(event.type);
    }


    /**
     * 重置订单
     */
    private void resetOrder(OrderRequest request) {
        initOrder(request);
        EventBus.getDefault().post(request);
    }


    OrderRequest all = new OrderRequest();
    OrderRequest myziji = new OrderRequest();
    OrderRequest myfenpai = new OrderRequest();
    OrderRequest wancheng = new OrderRequest();
    AsseptRequest assept = new AsseptRequest();
    ComplayRequest complay = new ComplayRequest();

    /**
     * 侧滑菜单确定
     */
    @OnClick(R.id.menu_commit)
    public void commit(View view) {
        switch (event.type) {
            case 0:
                setOrderBean(all, 0);
                all.menuType = "0";
                EventBus.getDefault().post(all);
                break;
            case 1:
                setOrderBean(myziji, 1);
                myziji.menuType = "1";
                EventBus.getDefault().post(myziji);
                break;
            case 2:
                setOrderBean(myfenpai, 2);
                myfenpai.menuType = "2";
                EventBus.getDefault().post(myfenpai);
                break;
            case 3:
                setOrderBean(wancheng, 3);
                wancheng.menuType = "3";
                EventBus.getDefault().post(wancheng);
                break;
            case 4:
                initComplay(complay);
                complay.setCreatStartDate(createTimeStart.getText().toString().replaceAll("/", "-"));
                complay.setCreatEndDate(createTimeEnd.getText().toString().replaceAll("/", "-"));
                complay.setStartDate(stopTimeStart.getText().toString().replaceAll("/", "-"));
                complay.setEndDate(stopTimeEnd.getText().toString().replaceAll("/", "-"));
                complay.setKeyWord(editKeybord.getText().toString().trim());
                complay.setTaskType(taskRadio);
                EventBus.getDefault().post(complay);
                break;
            case 5:
                initAsset(assept);
                assept.setCreatStartDate(createTimeStart.getText().toString().replaceAll("/", "-"));
                assept.setCreatEndDate(createTimeEnd.getText().toString().replaceAll("/", "-"));
                assept.setStartDate(stopTimeStart.getText().toString().replaceAll("/", "-"));
                assept.setEndDate(stopTimeEnd.getText().toString().replaceAll("/", "-"));
                assept.setKeyWord(editKeybord.getText().toString().trim());
                assept.setTaskType(fourSelector);
                EventBus.getDefault().post(assept);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.END);
    }


    private void setOrderBean(OrderRequest request, int position) {
        request.setCreatStartDate(createTimeStart.getText().toString().replaceAll("/", "-"));
        request.setCreatEndDate(createTimeEnd.getText().toString().replaceAll("/", "-"));
        request.setStartDate(stopTimeStart.getText().toString().replaceAll("/", "-"));
        request.setEndDate(stopTimeEnd.getText().toString().replaceAll("/", "-"));
        request.setKeyWord(editKeybord.getText().toString().trim());
        switch (event.type) {
            case 0:
                if ("0".equals(taskRadio)) {  //未分派
                    request.setTaskType("1");
                } else if ("1".equals(taskRadio)) {  //进行中
                    request.setTaskType("4");
                } else if ("2".equals(taskRadio)) {   //已取消
                    request.setTaskType("5");
                } else if("3".equals(taskRadio)){                        //已完成
                    request.setTaskType("2");
                }
                break;
            case 1:
                if ("0".equals(taskRadio)) {  //进行中
                    request.setTaskType("4");
                } else if ("1".equals(taskRadio)) {  //已取消
                    request.setTaskType("5");
                } else if ("2".equals(taskRadio)) {   //已完成
                    request.setTaskType("2");
                }
                break;
            case 2:
                if ("0".equals(taskRadio)) {  //未分派
                    request.setTaskType("3");
                } else if ("1".equals(taskRadio)) {  //进行中
                    request.setTaskType("4");
                } else if ("2".equals(taskRadio)) {   //已取消
                    request.setTaskType("5");
                } else if("3".equals(taskRadio)){                        //已完成
                    request.setTaskType("2");
                }
                break;
            default:
                request.setTaskType(null);
                break;
        }
        request.setDays(fourSelector);
        request.setType(position + "");
        initOrder(request);
    }


    private void initOrder(OrderRequest request) {
        request.setPageNum(1);
        request.setPageSize(1000);
        request.setUserId(MyApplication.userBo.getId() + "");
        String commonId;
        if (MyApplication.userBo.getCompanys() == null || MyApplication.userBo.getCompanys().size() == 0) {
            commonId = "0";
        } else {
            commonId = MyApplication.userBo.getCompanys().get(0).getId() + "";
        }
        request.setCompanyId(commonId);
    }


    private void initComplay(ComplayRequest request) {
        request.setPageNum(1);
        request.setPageSize(1000);
        request.setUserId(MyApplication.userBo.getId() + "");
        String commonId;
        if (MyApplication.userBo.getCompanys() == null || MyApplication.userBo.getCompanys().size() == 0) {
            commonId = "0";
        } else {
            commonId = MyApplication.userBo.getCompanys().get(0).getId() + "";
        }
        request.setCompanyId(commonId);
    }


    private void initAsset(AsseptRequest request) {
        request.setPageNum(1);
        request.setPageSize(1000);
        String commonId;
        if (MyApplication.userBo.getCompanys() == null || MyApplication.userBo.getCompanys().size() == 0) {
            commonId = "0";
        } else {
            commonId = MyApplication.userBo.getCompanys().get(0).getId() + "";
        }
        request.setId(commonId);
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
                    createTimeStart.setText(TimeUtils.date2String(date, format));
                    break;
                case 1:
                    createTimeEnd.setText(TimeUtils.date2String(date, format));
                    break;
                case 2:
                    stopTimeStart.setText(TimeUtils.date2String(date, format));
                    break;
                case 3:
                    stopTimeEnd.setText(TimeUtils.date2String(date, format));
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


    @Override
    public void onRequestError(String msg) {
        showToast(msg);
    }


    List<SalesBo> list;

    @Override
    public void getSales(List<SalesBo> s) {
        list = new ArrayList<>();
        if (s.size() > 5) {
            for (int i = 0; i < 5; i++) {
                list.add(s.get(i));
            }
        } else {
            list = s;
        }
        idFlowlayout.setAdapter(new TagAdapter<SalesBo>(list) {
            @Override
            public View getView(FlowLayout parent, int position, SalesBo o) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.item_tag_fllow,
                        idFlowlayout, false);
                tv.setText(o.getContent());
                return tv;
            }
        });
        idFlowlayout.setOnTagClickListener((view, position, parent) -> {
            editKeybord.setText(list.get(position).getContent());
            return true;
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MsgFragmentEvent event) {
        if (event.num > 0) {
            todayPoint.setVisibility(View.VISIBLE);
            if (event.num > 999) {
                todayPoint.setText("...");
            } else {
                todayPoint.setText(event.num + "");
            }
        } else {
            todayPoint.setVisibility(View.GONE);
        }
    }


}

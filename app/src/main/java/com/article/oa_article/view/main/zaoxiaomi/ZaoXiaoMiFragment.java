package com.article.oa_article.view.main.zaoxiaomi;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.bean.DateShemeBO;
import com.article.oa_article.bean.DateTaskBo;
import com.article.oa_article.mvp.MVPBaseFragment;
import com.article.oa_article.util.DateUtils;
import com.article.oa_article.view.AcceptedTaskActivity;
import com.article.oa_article.view.MyOrderActivity;
import com.article.oa_article.view.order_details.Order_detailsActivity;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * MVPPlugin
 * 造小蜜
 */

public class ZaoXiaoMiFragment extends MVPBaseFragment<ZaoXiaoMiContract.View, ZaoXiaoMiPresenter>
        implements ZaoXiaoMiContract.View, CalendarView.OnCalendarSelectListener,
        CalendarView.OnCalendarLongClickListener,
        CalendarView.OnMonthChangeListener,
        CalendarView.OnYearChangeListener,
        CalendarView.OnWeekChangeListener,
        CalendarView.OnViewChangeListener,
        CalendarView.OnCalendarInterceptListener,
        CalendarView.OnYearViewChangeListener {


    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.calendarView)
    CalendarView calendarView;
    @BindView(R.id.calendarLayout)
    CalendarLayout calendarLayout;
    Unbinder unbinder;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.image_down)
    ImageView imageDown;
    @BindView(R.id.date_text)
    TextView dateText;
    @BindView(R.id.date_layout)
    RelativeLayout dateLayout;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    private String selectDate;  //当前选中日期,默认为当前日期
    private String currenMonth; //当前滑动到的月份

    private List<DateTaskBo> yuqiTasks;   //已逾期的任务列表
    private List<DateTaskBo> todayTasks;   //今日到期的任务列表


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_zaoxiaomi, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initTab();
        titleText.setText(calendarView.getCurYear() + "年" + calendarView.getCurMonth() + "月");
        dateText.setText(calendarView.getCurMonth() + "月" + calendarView.getCurDay() + "日  " + DateUtils.DateToWeek(new Date()));
        currenMonth = calendarView.getCurYear() + "-" + (calendarView.getCurMonth() < 10 ?
                "0" + calendarView.getCurMonth() : calendarView.getCurMonth());
        selectDate = TimeUtils.date2String(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
    }


    @Override
    public void onSupportVisible() {
        super.onSupportVisible();

        mPresenter.getDateSchedule(currenMonth);
        mPresenter.getTaskByDate(selectDate, 0);
        mPresenter.getTaskByDate(selectDate, 1);
    }


    private void initView() {
        calendarView.setOnYearChangeListener(this);
        calendarView.setOnCalendarSelectListener(this);
        calendarView.setOnMonthChangeListener(this);
        calendarView.setOnCalendarLongClickListener(this, true);
        calendarView.setOnWeekChangeListener(this);
        calendarView.setOnYearViewChangeListener(this);
        //设置日期拦截事件，仅适用单选模式，当前无效
        calendarView.setOnCalendarInterceptListener(this);
        calendarView.setOnViewChangeListener(this);
        calendarView.setFixMode();
        calendarView.setCalendarItemHeight(SizeUtils.dp2px(40));

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);
//        recycleView.setNestedScrollingEnabled(false);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider_inset));
        recycleView.addItemDecoration(itemDecoration);

        calendarView.scrollToCurrent();
    }


    /**
     * 初始化TabLayout
     */
    private void initTab() {
        for (int i = 0; i < 2; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            View inflate = View.inflate(getActivity(), R.layout.tab_zaoxiaomi_layout, null);
            TextView textView = inflate.findViewById(R.id.today_text);
            if (i == 0) {
                textView.setText("已逾期任务");
                textView.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()), R.color.blue_color));
            } else {
                textView.setText("今日到期任务");
            }
            tab.setCustomView(inflate);
            tabLayout.addTab(tab);
        }
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                TextView today = view.findViewById(R.id.today_text);
                today.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_color));
                setAdapter(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                TextView today = view.findViewById(R.id.today_text);
                today.setTextColor(ContextCompat.getColor(getActivity(), R.color.hint_color));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.getTabAt(0).select();
    }


    private Calendar getSchemeCalendar(int year, int month, int day) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(0);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(".");
        return calendar;
    }


    @OnClick({R.id.date_layout, R.id.image_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.date_layout:
                calendarLayout.expand();
                break;
            case R.id.image_layout:
                if (calendarLayout.isExpand()) {
                    calendarLayout.shrink();
                } else {
                    calendarLayout.expand();
                }
                break;
        }
    }


    @Override
    public boolean onCalendarIntercept(Calendar calendar) {
        return false;
    }

    @Override
    public void onCalendarInterceptClick(Calendar calendar, boolean isClick) {

    }

    @Override
    public void onCalendarLongClickOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarLongClick(Calendar calendar) {

    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        selectDate = calendar.getYear() + "-" + (calendar.getMonth() < 10 ? "0" + calendar.getMonth() : calendar.getMonth())
                + "-" + (calendar.getDay() < 10 ? "0" + calendar.getDay() : calendar.getDay());
        mPresenter.getTaskByDate(selectDate, 0);
        mPresenter.getTaskByDate(selectDate, 1);
        tabLayout.post(() -> tabLayout.getTabAt(0).select());
    }

    @Override
    public void onMonthChange(int year, int month) {
        titleText.setText(year + "年" + month + "月");
        currenMonth = year + "-" + (month < 10 ? "0" + month : month);
        mPresenter.getDateSchedule(currenMonth);
    }

    @Override
    public void onViewChange(boolean isMonthView) {
        if (isMonthView) {   //收缩
            imageDown.setImageResource(R.drawable.jiantou_up);
            dateLayout.setVisibility(View.GONE);
        } else {
            imageDown.setImageResource(R.drawable.jiantou_down);
            dateLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onWeekChange(List<Calendar> weekCalendars) {

    }

    @Override
    public void onYearChange(int year) {

    }

    @Override
    public void onYearViewChange(boolean isClose) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRequestError(String msg) {
        showToast(msg);
    }

    @Override
    public void getShameDate(List<DateShemeBO> shemeBOS) {
        Map<String, Calendar> maps = new HashMap<>();
        for (int i = 0; i < shemeBOS.size(); i++) {
            String[] dates = shemeBOS.get(i).getDay().split("-");
            maps.put(getSchemeCalendar(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), Integer.parseInt(dates[2])).toString(),
                    getSchemeCalendar(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), Integer.parseInt(dates[2])));
        }
        calendarView.setSchemeDate(maps);
    }

    @Override
    public void getTaskByYuqi(List<DateTaskBo> dateTaskBos) {
        View view = tabLayout.getTabAt(0).getCustomView();
        TextView point = view.findViewById(R.id.today_point);
        if (dateTaskBos.size() > 0) {
            point.setBackgroundResource(R.drawable.range_red);
            point.setVisibility(View.VISIBLE);
        } else {
            point.setVisibility(View.GONE);
        }
        this.yuqiTasks = dateTaskBos;
        setAdapter(tabLayout.getSelectedTabPosition());
    }

    @Override
    public void getTaskByToday(List<DateTaskBo> dateTaskBos) {
        View view = tabLayout.getTabAt(1).getCustomView();
        TextView point = view.findViewById(R.id.today_point);
        if (dateTaskBos.size() > 0) {
            point.setBackgroundResource(R.drawable.range_yello);
            point.setVisibility(View.VISIBLE);
        } else {
            point.setVisibility(View.GONE);
        }
        this.todayTasks = dateTaskBos;
        setAdapter(tabLayout.getSelectedTabPosition());
    }


    /**
     * 设置适配器
     */
    private void setAdapter(int type) {
        LGRecycleViewAdapter<DateTaskBo> adapter = new LGRecycleViewAdapter<DateTaskBo>(type == 0 ? yuqiTasks : todayTasks) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_moban;
            }

            @Override
            public void convert(LGViewHolder holder, DateTaskBo dateTaskBo, int position) {
                holder.setText(R.id.xuhao_text, position + 1 + "");
                if (type == 0) {
                    holder.setText(R.id.moban_title, dateTaskBo.getTaskName() + "  已逾期" +
                            dateTaskBo.getDayBetween() + "天");
                } else {
                    holder.setText(R.id.moban_title, dateTaskBo.getTaskName() + "  今日到期");
                }
                holder.setText(R.id.moban_message, dateTaskBo.getCompanyOrderNum() + "   "
                        + dateTaskBo.getCompanyOrderName());
                holder.setText(R.id.select_button, "查看");
                holder.getView(R.id.item_layout).setOnClickListener(view -> {
                    switch (dateTaskBo.getPage()) {
                        case 1:   //待接受
                            Bundle bundle = new Bundle();
                            bundle.putInt("taskId", dateTaskBo.getId());
                            bundle.putBoolean("isHome", true);
                            gotoActivity(AcceptedTaskActivity.class, bundle, false);
                            break;
                        case 2:   //  我的任务
                            Bundle bundle1 = new Bundle();
                            bundle1.putInt("taskId", dateTaskBo.getId());
                            gotoActivity(MyOrderActivity.class, bundle1, false);
                            break;
                        case 3:   // 任务详情
                            Bundle bundle2 = new Bundle();
                            bundle2.putInt("id", dateTaskBo.getId());
                            bundle2.putBoolean("isOrder", false);
                            gotoActivity(Order_detailsActivity.class, bundle2, false);
                            break;
                        case 4:  //订单详情
                            Bundle bundle3 = new Bundle();
                            bundle3.putInt("id", dateTaskBo.getId());
                            bundle3.putBoolean("isOrder", true);
                            gotoActivity(Order_detailsActivity.class, bundle3, false);
                            break;
                    }
//
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("id", dateTaskBo.getId());
//                    bundle.putBoolean("isOrder", false);
//                    gotoActivity(Order_detailsActivity.class, bundle, false);
                });
            }
        };
        recycleView.setAdapter(adapter);
    }
}

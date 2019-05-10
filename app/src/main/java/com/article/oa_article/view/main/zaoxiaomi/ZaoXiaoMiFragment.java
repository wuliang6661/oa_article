package com.article.oa_article.view.main.zaoxiaomi;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.mvp.MVPBaseFragment;
import com.article.oa_article.util.DateUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.calendarLayout)
    CalendarLayout calendarLayout;
    Unbinder unbinder;
    @BindView(R.id.yuqi_text)
    TextView yuqiText;
    @BindView(R.id.today_text)
    TextView todayText;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.image_down)
    ImageView imageDown;
    @BindView(R.id.date_text)
    TextView dateText;
    @BindView(R.id.date_layout)
    RelativeLayout dateLayout;

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

        Date date = new Date();
        titleText.setText((1900 + date.getYear()) + "年" + (date.getMonth() + 1) + "月");
        dateText.setText((date.getMonth() + 1) + "月" + date.getDay() + "日  " + DateUtils.DateToWeek(date));
        initView();
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

        Map<String, Calendar> maps = new HashMap<>();
        maps.put(getSchemeCalendar(2019, 5, 10, 0, ".").toString(),
                getSchemeCalendar(2019, 5, 10, 0, "."));
        calendarView.setSchemeDate(maps);
    }


    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
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

    }

    @Override
    public void onMonthChange(int year, int month) {
        titleText.setText(year + "年" + month + "月");
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
}

package com.article.oa_article.module.chatline;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.ChartBO;
import com.article.oa_article.bean.request.AddOutRequest;
import com.article.oa_article.bean.request.ChartRequest;
import com.article.oa_article.mvp.MVPBaseFragment;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.ContentValues.TAG;

/**
 * MVPPlugin
 * <p>
 * 折线图fragment
 */

public class ChatLineFragment extends MVPBaseFragment<ChatLineContract.View, ChatLinePresenter>
        implements ChatLineContract.View {


    @BindView(R.id.unit_text)
    TextView unitText;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.week_line)
    TextView weekLine;
    @BindView(R.id.month_line)
    TextView monthLine;
    @BindView(R.id.year_line)
    TextView yearLine;
    @BindView(R.id.chart1)
    LineChart chart;
    Unbinder unbinder;
    @BindView(R.id.complan_bar)
    LinearLayout complanBar;
    @BindView(R.id.date_text)
    TextView dateText;
    @BindView(R.id.date_select_layout)
    LinearLayout dateSelectLayout;
    @BindView(R.id.edit_jihua)
    TextView editJihua;

    private int userId;
    private int complanId;

    private int month = 1;  //1 :月表 0：季表  2：年表
    private YAxis leftAxis;
    private XAxis xAxis;

    int type = 0;  //默认是取公司产能，  1为个人

    TextView[] texts;

    private String monthBegenDate;   //默认月开始日期
    private String monthEndDate;     //默认月结束日期

    private String weekBegenDate;    //默认周开始日期
    private String weekEndDate;      //默认周结束日期

    private String yearBegenDate;     //默认年开始日期
    private String yearEndDate;       //默认年结束日期

    public static ChatLineFragment getInstance(int type) {
        ChatLineFragment fragment = new ChatLineFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_chat_line, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initChatLine();

        type = getArguments().getInt("type");
        if (type == 0) {
            userId = MyApplication.userBo.getId();
            complanId = Integer.parseInt(MyApplication.getCommonId());
            complanBar.setVisibility(View.GONE);
            getChartData();
            unitText.setText(MyApplication.getCommon().getUnit());
            if (MyApplication.getCommon().getIsAdmin() == 1) {   //管理员
                editJihua.setVisibility(View.VISIBLE);
            }
            getBiaoNum();
        }
    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycleView.setLayoutManager(manager);
        texts = new TextView[]{weekLine, monthLine, yearLine};

        Date date = new Date();
        monthBegenDate = date.getYear() + 1900 + "-01";
        monthEndDate = date.getYear() + 1900 + "-12";
        yearBegenDate = date.getYear() + 1900 - 12 + "";
        yearEndDate = date.getYear() + 1900 + "";
        weekBegenDate = date.getYear() + 1900 - 3 + "-01";
        weekEndDate = date.getYear() + 1900 + "-12";
        setBar(1);
    }

    /**
     * 获取表格数据
     */
    private void getBiaoNum() {
        Date date = new Date();
        ChartRequest request = new ChartRequest();
        request.setUserId(userId);
        request.setCompanyId(complanId);
        request.setBeginDate(date.getYear() + 1900 + "-01");
        request.setEndDate(date.getYear() + 1900 + "-12");
        request.setMethod(1);
        mPresenter.getbiaoData(request);
    }


    /**
     * 获取折线图数据
     */
    private void getChartData() {
        ChartRequest request = new ChartRequest();
        request.setUserId(userId);
        request.setCompanyId(complanId);
        request.setMethod(month);
        switch (month) {
            case 0:   //季表
                request.setBeginDate(weekBegenDate);
                request.setEndDate(weekEndDate);
                break;
            case 1:   //月表
                request.setBeginDate(monthBegenDate);
                request.setEndDate(monthEndDate);
                break;
            case 2:    //年表
                request.setBeginDate(yearBegenDate);
                request.setEndDate(yearEndDate);
                break;
        }
        mPresenter.getChartData(request);
    }


    @OnClick({R.id.week_line, R.id.month_line, R.id.year_line})
    public void barClick(View view) {
        switch (view.getId()) {
            case R.id.week_line:
                setBar(0);
                if (month != 0) {
                    month = 0;
                    getChartData();
                }
                break;
            case R.id.month_line:
                setBar(1);
                if (month != 1) {
                    month = 1;
                    getChartData();
                }
                break;
            case R.id.year_line:
                setBar(2);
                if (month != 2) {
                    month = 2;
                    getChartData();
                }
                break;
        }
    }

    private void setBar(int position) {
        for (int i = 0; i < texts.length; i++) {
            if (position == i) {
                texts[i].setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_color));
            } else {
                texts[i].setTextColor(ContextCompat.getColor(getActivity(), R.color.hint_color));
            }
        }
        switch (position) {
            case 0:  //季表
                dateText.setText(weekBegenDate + " - " + weekEndDate);
                break;
            case 1:    //月表
                dateText.setText(monthBegenDate + " - " + monthEndDate);
                break;
            case 2:    //年表
                dateText.setText(yearBegenDate + " - " + yearEndDate);
                break;
        }
    }


    @OnClick(R.id.date_select_layout)
    public void dateSelect() {
        switch (month) {
            case 0:   //季表
                PopQuarterDialog dialog = new PopQuarterDialog(getActivity());
                dialog.setListener((startTime, endTime) -> {
                    weekBegenDate = startTime;
                    weekEndDate = endTime;
                    getChartData();
                    dateText.setText(weekBegenDate + " - " + weekEndDate);
                });
                dialog.showAtLocation(getActivity().getWindow().getDecorView());
                break;
            case 1:    //月表
                PopMonthDialog popMonthDialog = new PopMonthDialog(getActivity());
                popMonthDialog.setListener((startTime, endTime) -> {
                    monthBegenDate = startTime;
                    monthEndDate = endTime;
                    getChartData();
                    dateText.setText(monthBegenDate + " - " + monthEndDate);
                });
                popMonthDialog.showAtLocation(getActivity().getWindow().getDecorView());
                break;
            case 2:     //年表
                PopYearDialog dialog1 = new PopYearDialog(getActivity());
                dialog1.setListener((startTime, endTime) -> {
                    yearBegenDate = startTime;
                    yearEndDate = endTime;
                    getChartData();
                    dateText.setText(yearBegenDate + " - " + yearEndDate);
                });
                dialog1.showAtLocation(getActivity().getWindow().getDecorView());
                break;
        }
    }

    @OnClick(R.id.edit_jihua)
    public void editJihua() {
        PopJiHuaNumDialog dialog = new PopJiHuaNumDialog(getActivity());
        dialog.setCommitListener((one, two, three, four, five, six, seven, eight, nine, ten, shiyi, shier) -> {
            AddOutRequest request = new AddOutRequest();
            request.setOne(Integer.parseInt(one));
            request.setTwo(Integer.parseInt(two));
            request.setThree(Integer.parseInt(three));
            request.setFour(Integer.parseInt(four));
            request.setFive(Integer.parseInt(five));
            request.setSix(Integer.parseInt(six));
            request.setSeven(Integer.parseInt(seven));
            request.setEight(Integer.parseInt(eight));
            request.setNine(Integer.parseInt(nine));
            request.setTen(Integer.parseInt(ten));
            request.setEleven(Integer.parseInt(shiyi));
            request.setTwelve(Integer.parseInt(shier));
            mPresenter.addOutPut(request);
        });
        dialog.showAtLocation(getActivity().getWindow().getDecorView());
    }


    /**
     * 初始化折线图布局
     */
    private void initChatLine() {
        // no description text
        chart.getDescription().setEnabled(false);
        // enable touch gestures
        chart.setTouchEnabled(false);
        chart.setDragDecelerationFrictionCoef(0.9f);
        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDrawGridBackground(false);
        chart.setHighlightPerDragEnabled(true);
        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(true);
        // set an alternative background color
        chart.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.layout_bg));

        setData(new ArrayList<>());
        chart.animateY(1000);

        leftAxis = chart.getAxisLeft();
        leftAxis.setTextColor(Color.parseColor("#6F6F6F"));
        leftAxis.setTextSize(11f);
//        leftAxis.setAxisMaximum(200f);
//        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(false);
        leftAxis.setGranularityEnabled(false);
        //设置样式
        YAxis rightAxis = chart.getAxisRight();
        //设置图表右边的y轴禁用
        rightAxis.setEnabled(false);
        Legend legend = chart.getLegend();
        legend.setEnabled(false);
        //设置x轴
        xAxis = chart.getXAxis();
        xAxis.setTextColor(Color.parseColor("#6F6F6F"));
        xAxis.setTextSize(11f);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(12);
        xAxis.setDrawAxisLine(true);//是否绘制轴线
        xAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setLabelRotationAngle(40);
        xAxis.setLabelCount(12);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置

        // 设置x轴的LimitLine，index是从0开始的
//        LimitLine xLimitLine = new LimitLine(4f,"xL 测试");
//        xLimitLine.setLineColor(Color.GREEN);
//        xLimitLine.setTextColor(Color.GREEN);
//        xAxis.addLimitLine(xLimitLine);
//
//        // 设置x轴的LimitLine
//        LimitLine yLimitLine = new LimitLine(50f,"yLimit 测试");
//        yLimitLine.setLineColor(Color.RED);
//        yLimitLine.setTextColor(Color.RED);
//        // 获得左侧侧坐标轴
//        YAxis leftAxis = chart.getAxisLeft();
//        leftAxis.addLimitLine(yLimitLine);
    }


    private void setData(List<ChartBO> chartBOS) {
        ArrayList<Entry> yVals1 = new ArrayList<>();
        ArrayList<Entry> yVals2 = new ArrayList<>();
        ArrayList<Entry> yVals3 = new ArrayList<>();
        for (int i = 0; i < chartBOS.size(); i++) {
            yVals1.add(new Entry(i, chartBOS.get(i).getJihuaNum()));
            yVals2.add(new Entry(i, chartBOS.get(i).getShijiNum()));
            yVals3.add(new Entry(i, chartBOS.get(i).getYipaiNum()));
        }
        if (chartBOS.isEmpty()) {
            yVals1.add(new Entry(0, 0));
            yVals2.add(new Entry(0, 0));
            yVals3.add(new Entry(0, 0));
        }
        LineDataSet set1;
        LineDataSet set2;
        LineDataSet set3;
        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) chart.getData().getDataSetByIndex(1);
            set3 = (LineDataSet) chart.getData().getDataSetByIndex(2);
            set1.setValues(yVals1);
            set2.setValues(yVals2);
            set3.setValues(yVals3);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
            chart.invalidate();
            chart.animateY(1000);
        } else {
            set1 = new LineDataSet(yVals1, "");
            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(Color.parseColor("#5678FF"));
            set1.setCircleColor(Color.parseColor("#5678FF"));
            set1.setCircleHoleColor(Color.parseColor("#5678FF"));
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setDrawValues(false);
//            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            // create a data object with the datasets
            // create a dataset and give it a type
            set2 = new LineDataSet(yVals2, "");
            set2.setAxisDependency(YAxis.AxisDependency.LEFT);
            set2.setColor(Color.parseColor("#F4CA40"));
            set2.setCircleColor(Color.parseColor("#F4CA40"));
            set2.setLineWidth(2f);
            set2.setCircleRadius(3f);
            set2.setCircleHoleColor(Color.parseColor("#F4CA40"));
            set1.setDrawValues(false);

            set3 = new LineDataSet(yVals3, "");
            set3.setAxisDependency(YAxis.AxisDependency.LEFT);
            set3.setColor(Color.parseColor("#71EA45"));
            set3.setCircleColor(Color.parseColor("#71EA45"));
            set3.setCircleHoleColor(Color.parseColor("#71EA45"));
            set3.setLineWidth(2f);
            set3.setCircleRadius(3f);
            set1.setDrawValues(false);

            // create a data object with the data sets
            LineData data = new LineData(set1, set2, set3);
            data.setDrawValues(false);
            // set data
            chart.setData(data);
        }
    }


    public void setUserBo(int userId, int complanyId) {
        new Handler().post(() -> {
            ChatLineFragment.this.userId = userId;
            ChatLineFragment.this.complanId = complanyId;
            getChartData();
            getBiaoNum();
        });
    }


    public void setUnit(String text) {
        new Handler().post(() -> unitText.setText(text));
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
    public void getChatLine(List<ChartBO> chartBOS) {
        int maxNum = 0;
        int minNum = 0;
        for (ChartBO chartBO : chartBOS) {
            if (maxNum < chartBO.getJihuaNum()) {
                maxNum = chartBO.getJihuaNum();
            }
            if (maxNum < chartBO.getShijiNum()) {
                maxNum = chartBO.getShijiNum();
            }
            if (maxNum < chartBO.getYipaiNum()) {
                maxNum = chartBO.getYipaiNum();
            }
            if (minNum > chartBO.getJihuaNum()) {
                minNum = chartBO.getJihuaNum();
            }
            if (minNum > chartBO.getShijiNum()) {
                minNum = chartBO.getShijiNum();
            }
            if (minNum > chartBO.getYipaiNum()) {
                minNum = chartBO.getYipaiNum();
            }
        }
        Log.e(TAG, maxNum + "       " + minNum);
        leftAxis.setAxisMaximum(maxNum + 200);
        leftAxis.setAxisMinimum(0);
        xAxis.setValueFormatter(new XValues(1, chartBOS));
        setData(chartBOS);
    }

    @Override
    public void addOutSoress() {
        getBiaoNum();
        getChartData();
    }

    @Override
    public void getBiaoData(List<ChartBO> chartBOS) {
        setRecycleAdapter(chartBOS);
    }

    private void setRecycleAdapter(List<ChartBO> chartBOS) {
        LGRecycleViewAdapter<ChartBO> adapter = new LGRecycleViewAdapter<ChartBO>(chartBOS) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_chanliang;
            }

            @Override
            public void convert(LGViewHolder holder, ChartBO chartBO, int position) {
                holder.setText(R.id.month_text, position + 1 + "月");
                holder.setText(R.id.jihua_num, chartBO.getJihuaNum() + "");
                holder.setText(R.id.yipai_num, chartBO.getYipaiNum() + "");
                holder.setText(R.id.shiji_num, chartBO.getShijiNum() + "");
            }
        };
        recycleView.setAdapter(adapter);
    }

}

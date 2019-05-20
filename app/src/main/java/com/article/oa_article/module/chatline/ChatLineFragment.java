package com.article.oa_article.module.chatline;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.bean.ChartBO;
import com.article.oa_article.bean.request.ChartRequest;
import com.article.oa_article.mvp.MVPBaseFragment;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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

    private int userId;
    private int complanId;
    private String beginDate;
    private String endDate;

    private int month = 1;  //1 :月表 0：季表  2：年表
    private YAxis leftAxis;

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

        beginDate = "2018-05";
        endDate = "2019-05";

    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycleView.setLayoutManager(manager);
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
        chart.animateX(1000);

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
        //设置x轴
        XAxis xAxis = chart.getXAxis();
        xAxis.setTextColor(Color.parseColor("#6F6F6F"));
        xAxis.setTextSize(11f);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(12);
        xAxis.setDrawAxisLine(true);//是否绘制轴线
        xAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        xAxis.setGranularity(1f);//禁止放大后x轴标签重绘
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
        } else {
            set1 = new LineDataSet(yVals1, "");
            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(ColorTemplate.getHoloBlue());
            set1.setCircleColor(Color.parseColor("#49baff"));
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);
            set1.setDrawCircles(false);
            set1.setDrawValues(false);
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            // create a data object with the datasets
            // create a dataset and give it a type
            set2 = new LineDataSet(yVals2, "");
            set2.setAxisDependency(YAxis.AxisDependency.LEFT);
            set2.setColor(Color.RED);
            set2.setCircleColor(Color.WHITE);
            set2.setLineWidth(2f);
            set2.setCircleRadius(3f);
            set2.setFillAlpha(65);
            set2.setFillColor(Color.RED);
            set2.setDrawCircleHole(false);
            set2.setHighLightColor(Color.rgb(244, 117, 117));
            //set2.setFillFormatter(new MyFillFormatter(900f));

            set3 = new LineDataSet(yVals3, "");
            set3.setAxisDependency(YAxis.AxisDependency.LEFT);
            set3.setColor(Color.YELLOW);
            set3.setCircleColor(Color.WHITE);
            set3.setLineWidth(2f);
            set3.setCircleRadius(3f);
            set3.setFillAlpha(65);
            set3.setFillColor(ColorTemplate.colorWithAlpha(Color.YELLOW, 200));
            set3.setDrawCircleHole(false);
            set3.setHighLightColor(Color.rgb(244, 117, 117));

            // create a data object with the data sets
            LineData data = new LineData(set1, set2, set3);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);
            // set data
            chart.setData(data);
        }
    }


    public void setUserBo(int userId, int complanyId) {
        new Handler().post(() -> {
            ChatLineFragment.this.userId = userId;
            ChatLineFragment.this.complanId = complanyId;
            ChartRequest request = new ChartRequest();
            request.setUserId(userId);
            request.setCompanyId(complanyId);
            request.setBeginDate(beginDate);
            request.setEndDate(endDate);
            request.setMethod(month);
            mPresenter.getChartData(request);
        });
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
        if (month == 1) {
            setRecycleAdapter(chartBOS);
        }
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
        leftAxis.setAxisMaximum(maxNum + 10);
        leftAxis.setAxisMinimum(minNum - 10);
        setData(chartBOS);
    }

    private void setRecycleAdapter(List<ChartBO> chartBOS) {
        LGRecycleViewAdapter<ChartBO> adapter = new LGRecycleViewAdapter<ChartBO>(chartBOS) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_chanliang;
            }

            @Override
            public void convert(LGViewHolder holder, ChartBO chartBO, int position) {
                holder.setText(R.id.month_text, chartBO.getDay());
                holder.setText(R.id.jihua_num, chartBO.getJihuaNum() + "");
                holder.setText(R.id.yipai_num, chartBO.getYipaiNum() + "");
                holder.setText(R.id.shiji_num, chartBO.getShijiNum() + "");
            }
        };
        recycleView.setAdapter(adapter);
    }

}

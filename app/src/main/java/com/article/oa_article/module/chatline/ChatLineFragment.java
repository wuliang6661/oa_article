package com.article.oa_article.module.chatline;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.mvp.MVPBaseFragment;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

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

        initChatLine();
    }

    /**
     * 初始化折线图布局
     */
    private void initChatLine() {
        // no description text
        chart.getDescription().setEnabled(false);
        // enable touch gestures
        chart.setTouchEnabled(true);
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

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();
        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setTextSize(11f);
        l.setTextColor(Color.BLUE);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTextColor(Color.parseColor("#6F6F6F"));
        leftAxis.setTextSize(11f);
        leftAxis.setAxisMaximum(200f);
        leftAxis.setAxisMinimum(0f);
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
        xAxis.setAxisMinimum(-10);
        xAxis.setDrawAxisLine(true);//是否绘制轴线
        xAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        xAxis.setGranularity(1f);//禁止放大后x轴标签重绘
    }


    private void setData() {
        ArrayList<Entry> yVals1 = new ArrayList<>();
//        int j = 0;
//        for (int i = monitorBos.size() - 1; i >= 0; i--) {
//            yVals1.add(new Entry((float) j, Float.parseFloat(monitorBos.get(i).getValue())));
//            j++;
//        }
//        if (monitorBos.isEmpty()) {
//            yVals1.add(new Entry(0, 0));
//        }
        LineDataSet set1;
        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
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
            LineData data = new LineData(set1);
            data.setValueTextColor(Color.parseColor("#49baff"));
            data.setValueTextSize(9f);
            // set data
            chart.setData(data);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

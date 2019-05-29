package com.article.oa_article.module.chatline;

import com.article.oa_article.bean.ChartBO;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.List;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/2913:28
 * desc   :
 * version: 1.0
 */
public class XValues extends ValueFormatter {

    private int type; // 0周表  1：月表  2 年表

    private List<ChartBO> chartBOS;

    public XValues(int type, List<ChartBO> chartBOS) {
        this.type = type;
        this.chartBOS = chartBOS;
    }


    @Override
    public String getFormattedValue(float value) {
//        switch (type) {
//            case 0:
//                return chartBOS.get((int) value).getDay();
//            case 1:
//                return chartBOS.get((int) value).getDay();
//            case 2:
//
//                break;
//        }
        if (value >= chartBOS.size()) {
            return "";
        }
        return chartBOS.get((int) value).getDay();
    }
}

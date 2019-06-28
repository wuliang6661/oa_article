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

    private int type; // 0季表  1：月表  2 年表

    private List<ChartBO> chartBOS;

    public XValues(int type, List<ChartBO> chartBOS) {
        this.type = type;
        this.chartBOS = chartBOS;
    }


    @Override
    public String getFormattedValue(float value) {
        if (value >= chartBOS.size()) {
            return "";
        }
//        return chartBOS.get((int) value).getDay();
        switch (type) {
            case 0:
                if (value % 4 == 0) {
                    return chartBOS.get((int) value).getDay();
                }
                return "";
            case 1:
                if (value % 3 == 0) {
                    return chartBOS.get((int) value).getDay();
                }
                return "";
            case 2:
                if (value % 2 == 0) {
                    return chartBOS.get((int) value).getDay();
                }
        }
        return "";
    }
}

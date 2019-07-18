package com.article.oa_article.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.article.oa_article.util.DateUtils;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateDialog {


    @SuppressLint("SimpleDateFormat")
    static DateFormat format = new SimpleDateFormat("yyyy/MM/dd");


    /**
     * 时间选择器
     */
    public static void show(Context context, TextView textView, long time) {
        Calendar endTime = Calendar.getInstance();
        endTime.setTime(new Date(time));
        Calendar startDate = Calendar.getInstance();
        startDate.set(1990, 1, 1);
        TimePickerView pvTime = new TimePickerBuilder(context, (date, v) -> {
            if (date.getTime() < DateUtils.getTodayZero()) {
                ToastUtils.showShort("任务时限不能小于今天!");
                textView.setText("");
                return;
            }
            if (date.getTime() > time) {
                ToastUtils.showShort("任务时限不能大于上级任务时限!");
                textView.setText("");
                return;
            }
            textView.setText(TimeUtils.date2String(date, format));
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .setDate(endTime)
//                .setRangDate(startDate, endTime)
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


}

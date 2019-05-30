package com.article.oa_article.module.chatline;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.util.DateUtils;
import com.article.oa_article.widget.wheelview.WheelView;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/3010:31
 * desc   :
 * version: 1.0
 */
public class PopQuarterDialog extends PopupWindow {

    private Activity activity;
    private View dialogView;

    private WheelView startYear;
    private WheelView startMonth;
    private WheelView endYear;
    private WheelView endMonth;

    private TextView iv_cancel;
    private TextView tv_finish;

    private int todayPosition;

    public PopQuarterDialog(Activity activity) {
        super(activity);
        this.activity = activity;
        dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_month_picker, null);

        initView();
        this.setBackgroundDrawable(new ColorDrawable(0));
        this.setContentView(dialogView);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.anim_menu_bottombar);
        //实例化一个ColorDrawable颜色为半透明
        // ColorDrawable dw = new ColorDrawable(0x808080);
        //设置SelectPicPopupWindow弹出窗体的背景
        // this.setBackgroundDrawable(dw);
        this.setOnDismissListener(() -> backgroundAlpha(1f));
    }


    private void initView() {
        startYear = dialogView.findViewById(R.id.start_year);
        startMonth = dialogView.findViewById(R.id.start_month);
        endYear = dialogView.findViewById(R.id.end_year);
        endMonth = dialogView.findViewById(R.id.end_month);
        iv_cancel = dialogView.findViewById(R.id.iv_cancel);
        tv_finish = dialogView.findViewById(R.id.tv_finish);

        startYear.setItems(getYear(), todayPosition);
        startMonth.setItems(getMonth(), 0);
        endYear.setItems(getYear(), todayPosition);
        endMonth.setItems(getMonth(), getMonth().size() - 1);

        iv_cancel.setOnClickListener(view -> dismiss());
        tv_finish.setOnClickListener(view -> {
            String strStartMonth = null;
            switch (startMonth.getSelectedPosition()) {
                case 0:
                    strStartMonth = "01";
                    break;
                case 1:
                    strStartMonth = "04";
                    break;
                case 2:
                    strStartMonth = "07";
                    break;
                case 3:
                    strStartMonth = "10";
                    break;
            }
            String strEndMonth = null;
            switch (endMonth.getSelectedPosition()) {
                case 0:
                    strEndMonth = "03";
                    break;
                case 1:
                    strEndMonth = "06";
                    break;
                case 2:
                    strEndMonth = "09";
                    break;
                case 3:
                    strEndMonth = "12";
                    break;
            }
            String startDate = startYear.getSelectedItem() + "-" + strStartMonth;
            String endDate = endYear.getSelectedItem() + "-" + strEndMonth;
            if (DateUtils.dayCompare(TimeUtils.string2Date(startDate, new SimpleDateFormat("yyyy-MM")),
                    TimeUtils.string2Date(endDate, new SimpleDateFormat("yyyy-MM"))) > 36) {
                ToastUtils.showShort("季度间隔不能超过12个季度");
                return;
            }
            if (listener != null) {
                dismiss();
                listener.commit(startDate, endDate);
            }
        });
    }


    private List<String> getYear() {
        int todayYear = new Date().getYear() + 1900;
        List<String> year = new ArrayList<>();
        int start = 1970;
        for (int i = 0; i < 100; i++) {
            year.add(start++ + "");
            if (start - 1 == todayYear) {
                todayPosition = i;
            }
        }
        return year;
    }

    private List<String> getMonth() {
        List<String> month = new ArrayList<>();
        month.add("一季度");
        month.add("二季度");
        month.add("三季度");
        month.add("四季度");
        return month;
    }


    /***
     * 显示时将屏幕置为透明
     */
    public void showAtLocation(View parent) {
        super.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        backgroundAlpha(0.5f);
    }


    /**
     * 设置添加屏幕的背景透明度
     */
    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

    onSelectListener listener;

    public void setListener(onSelectListener listener) {
        this.listener = listener;
    }


    interface onSelectListener {

        void commit(String startTime, String endTime);
    }
}
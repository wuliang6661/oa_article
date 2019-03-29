package com.wul.oa_article.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.blankj.utilcode.util.SizeUtils;
import com.wul.oa_article.R;

public class HomeAddPopWindow extends PopupWindow {

    Activity activity;


    public HomeAddPopWindow(Activity activity) {
        super(activity);
        this.activity = activity;
        View window = activity.getLayoutInflater().inflate(R.layout.pop_home_add, null);
        this.setContentView(window);
        this.setWidth(SizeUtils.dp2px(155));
        this.setHeight(SizeUtils.dp2px(107));
        this.setFocusable(true);
        //设置宽高
        setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(activity, R.color.tab_bg)));

        this.setOnDismissListener(() -> bgAlpha(1.0f));

    }


    public void showPop(View view, int x, int y) {
        bgAlpha(0.4f);
        showAsDropDown(view, x, y);
    }


    /**
     * 设置窗口的背景透明度
     *
     * @param f 0.0-1.0
     */
    public void bgAlpha(float f) {
        WindowManager.LayoutParams layoutParams = activity.getWindow().getAttributes();
        layoutParams.alpha = f;
        activity.getWindow().setAttributes(layoutParams);
    }

}

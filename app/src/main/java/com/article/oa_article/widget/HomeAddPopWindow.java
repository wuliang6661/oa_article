package com.article.oa_article.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.blankj.utilcode.util.SizeUtils;
import com.article.oa_article.R;

public class HomeAddPopWindow extends PopupWindow implements View.OnClickListener {

    Activity activity;
    LinearLayout createOrder;
    LinearLayout createMoBan;


    public HomeAddPopWindow(Activity activity) {
        super(activity);
        this.activity = activity;
        View window = activity.getLayoutInflater().inflate(R.layout.pop_home_add, null);
        createOrder = window.findViewById(R.id.create_order);
        createMoBan = window.findViewById(R.id.create_moban);
        createOrder.setOnClickListener(this);
        createMoBan.setOnClickListener(this);
        this.setContentView(window);
        this.setWidth(SizeUtils.dp2px(155));
        this.setHeight(SizeUtils.dp2px(106));
        this.setFocusable(true);
        //设置宽高
//        setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(activity, R.color.dialog_bg)));
        this.setOnDismissListener(() -> bgAlpha(1.0f));
    }


    public void showPop(View view, int x, int y) {
        bgAlpha(0.5f);
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


    private OnClickListener listener;

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_order:
                if (listener != null) {
                    listener.clickCreateOrder();
                }
                break;
            case R.id.create_moban:
                if (listener != null) {
                    listener.clickCreateMoBan();
                }
                break;
        }
        dismiss();
    }

    public interface OnClickListener {

        void clickCreateOrder();

        void clickCreateMoBan();

    }

}

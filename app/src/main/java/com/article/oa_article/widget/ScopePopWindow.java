package com.article.oa_article.widget;

import android.app.Activity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.article.oa_article.R;
import com.blankj.utilcode.util.SizeUtils;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/1616:10
 * desc   :  评分弹窗
 * version: 1.0
 */
public class ScopePopWindow extends PopupWindow {


    Activity activity;


    public ScopePopWindow(Activity activity) {
        super(activity);
        this.activity = activity;
        View window = activity.getLayoutInflater().inflate(R.layout.pop_scope, null);
        this.setContentView(window);
        this.setWidth(SizeUtils.dp2px(238));
        this.setHeight(SizeUtils.dp2px(160));
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


}

package com.article.oa_article.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.article.oa_article.R;
import com.example.xlhratingbar_lib.XLHRatingBar;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/1616:10
 * desc   :  评分弹窗
 * version: 1.0
 */
public class ScopePopWindow extends PopupWindow {


    Activity activity;
    View window;
    private XLHRatingBar fuwuRatingBar;
    private XLHRatingBar zhiliangRatingBar;
    private XLHRatingBar zhunshiRattingBar;
    private XLHRatingBar jiageRattingBar;
    private XLHRatingBar wuliuRattingBar;

    private LinearLayout commitLayout;
    private TextView commit;

    public ScopePopWindow(Activity activity, int fuwuScope, int zhiliangScope, int zhunshiScope,
                          int jiageScope, int wuliuScope, boolean isEnable) {
        super(activity);
        this.activity = activity;
        window = activity.getLayoutInflater().inflate(R.layout.pop_scope, null);
        fuwuRatingBar = window.findViewById(R.id.fuwu_ratting);
        zhiliangRatingBar = window.findViewById(R.id.zhiliang_ratting);
        zhunshiRattingBar = window.findViewById(R.id.zhunshi_ratting);
        jiageRattingBar = window.findViewById(R.id.jiage_ratting);
        wuliuRattingBar = window.findViewById(R.id.wuliu_ratting);
        commitLayout = window.findViewById(R.id.commit_layout);
        commit = window.findViewById(R.id.commit);

        if (!isEnable) {   //可以设置值
            fuwuRatingBar.setEnabled(false);
            zhiliangRatingBar.setEnabled(false);
            zhunshiRattingBar.setEnabled(false);
            jiageRattingBar.setEnabled(false);
            wuliuRattingBar.setEnabled(false);
            fuwuRatingBar.setRating(fuwuScope);
            zhiliangRatingBar.setRating(zhiliangScope);
            zhunshiRattingBar.setRating(zhunshiScope);
            jiageRattingBar.setRating(jiageScope);
            wuliuRattingBar.setRating(wuliuScope);
        } else {
            commitLayout.setVisibility(View.VISIBLE);
        }
        this.setContentView(window);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        //设置宽高
        setBackgroundDrawable(new ColorDrawable(0));
        this.setOnDismissListener(() -> bgAlpha(1.0f));
    }


    public void showPop(View view) {
        bgAlpha(0.5f);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        window.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int mShowMorePopupWindowWidth = window.getMeasuredWidth();
        showAtLocation(view, Gravity.NO_GRAVITY, location[0] - mShowMorePopupWindowWidth - 10, location[1]);
//        showAsDropDown(view, mShowMorePopupWindowWidth, mShowMorePopupWindowHeight);
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

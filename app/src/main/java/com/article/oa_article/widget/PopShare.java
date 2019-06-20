package com.article.oa_article.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.article.oa_article.R;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/6/209:17
 * desc   :
 * version: 1.0
 */
public class PopShare extends PopupWindow {

    private Activity activity;

    private LinearLayout shareFriend;
    private LinearLayout shareComments;
    private TextView cancle;

    public PopShare(Activity activity) {
        super(activity);

        this.activity = activity;
        View dialogView = activity.getLayoutInflater().inflate(R.layout.pop_share, null);
        shareFriend = dialogView.findViewById(R.id.share_friend);
        shareComments = dialogView.findViewById(R.id.share_moments);
        cancle = dialogView.findViewById(R.id.cancle);

        cancle.setOnClickListener(view -> dismiss());
        shareFriend.setOnClickListener(view -> {
            dismiss();
            if (listener != null) {
                listener.shareFriend();
            }
        });
        shareComments.setOnClickListener(view -> {
            dismiss();
            if (listener != null) {
                listener.shareMenmens();
            }
        });
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


    /***
     * 显示时将屏幕置为透明
     */
    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
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


    private onCommitListener listener;

    public void setListener(onCommitListener listener) {
        this.listener = listener;
    }

    public interface onCommitListener {

        void shareFriend();

        void shareMenmens();
    }

}

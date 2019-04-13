package com.wul.oa_article.module.task_allot;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.wul.oa_article.R;
import com.wul.oa_article.view.EditPhotoNamePop;

public class PopAddTaskWindow extends PopupWindow {

    private Activity activity;

    private Button commit;
    private TextView cancle;

    public PopAddTaskWindow(Activity activity) {
        super(activity);

        this.activity = activity;
        View dialogView = activity.getLayoutInflater().inflate(R.layout.pop_add_task, null);
        commit = dialogView.findViewById(R.id.next_button);
        cancle = dialogView.findViewById(R.id.cancle);
        commit.setOnClickListener(v -> {
            String name = "";
            if (StringUtils.isEmpty(name)) {
                ToastUtils.showShort("请输入图片名称!");
            } else {
                if (listener != null) {
                    listener.commit(name);
                }
                dismiss();
            }
        });
        cancle.setOnClickListener(v -> dismiss());

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


    private EditPhotoNamePop.onCommitListener listener;

    public void setListener(EditPhotoNamePop.onCommitListener listener) {
        this.listener = listener;
    }

    public interface onCommitListener {

        void commit(String text);
    }

}

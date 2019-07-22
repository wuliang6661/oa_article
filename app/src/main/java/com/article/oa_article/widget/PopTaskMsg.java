package com.article.oa_article.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.text.InputFilter;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.bean.LableBo;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/2315:07
 * desc   :
 * version: 1.0
 */
public class PopTaskMsg extends PopupWindow {

    private Activity activity;

    private EditText photoName;
    private Button commit;
    private TextView cancle, txTitle, txLineTitle;

    LableBo.CustomLabelsBean customLabelsBean;

    public PopTaskMsg(Activity activity, String title, String lineTitle, String hintMessage) {
        super(activity);

        this.activity = activity;
        View dialogView = activity.getLayoutInflater().inflate(R.layout.pop_add_msg, null);
        photoName = dialogView.findViewById(R.id.edit_photo_name);
        commit = dialogView.findViewById(R.id.next_button);
        cancle = dialogView.findViewById(R.id.cancle);
        txTitle = dialogView.findViewById(R.id.title);
        txLineTitle = dialogView.findViewById(R.id.line_title);
        txTitle.setText(title);
        txLineTitle.setText(lineTitle);
        photoName.setHint(hintMessage);

        commit.setOnClickListener(v -> {
            String name = photoName.getText().toString().trim();
            if (StringUtils.isEmpty(name)) {
                ToastUtils.showShort(hintMessage);
            } else {
                if (listener != null) {
                    if (customLabelsBean == null) {
                        listener.commit(name);
                    } else {
                        listener.update(name, customLabelsBean);
                    }
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


    public void setText(String text, LableBo.CustomLabelsBean customLabelsBean) {
        photoName.setText(text);
        this.customLabelsBean = customLabelsBean;
    }


    /**
     * 修改输入框输入长度
     */
    public void setLength(int length) {
        photoName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
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

        void commit(String text);

        void update(String text, LableBo.CustomLabelsBean customLabelsBean);
    }
}
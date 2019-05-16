package com.article.oa_article.module.create_order;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.article.oa_article.R;

public class PopAddPinleiWindow extends PopupWindow {

    private Activity activity;
    private View dialogView;

    private EditText pingleiName;
    private EditText pingleiNum;
    private EditText pingleiUnit;
    private EditText pingleiSize;

    PopAddPinleiWindow(Activity activity, String name, String num, String guige, String danwei) {
        super(activity);

        this.activity = activity;
        dialogView = activity.getLayoutInflater().inflate(R.layout.pop_add_pinglei, null);
        initView(name, num, guige, danwei);
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


    /**
     * 初始化布局
     */
    private void initView(String name, String num, String guige, String danwei) {
        pingleiName = dialogView.findViewById(R.id.edit_pinlei_name);
        pingleiNum = dialogView.findViewById(R.id.edit_num);
        pingleiSize = dialogView.findViewById(R.id.edit_guige);
        pingleiUnit = dialogView.findViewById(R.id.edit_danwei);
        TextView cancle = dialogView.findViewById(R.id.cancle);
        Button commit = dialogView.findViewById(R.id.next_button);

        if (!StringUtils.isEmpty(name)) {
            pingleiName.setText(name);
            pingleiNum.setText(num);
            pingleiUnit.setText(danwei);
            pingleiSize.setText(guige);
        }
        cancle.setOnClickListener(view -> dismiss());
        commit.setOnClickListener(view -> {
            String strName = pingleiName.getText().toString().trim();
            String strNum = pingleiNum.getText().toString().trim();
            String strSize = pingleiSize.getText().toString().trim();
            String strUnit = pingleiUnit.getText().toString().trim();

            if (StringUtils.isEmpty(strName)) {
                ToastUtils.showShort("请输入品类名称！");
                return;
            }
            if (!StringUtils.isEmpty(strSize) || !StringUtils.isEmpty(strNum) || !StringUtils.isEmpty(strUnit)) {
                if (listener != null) {
                    listener.commit(strName, strNum, strSize, strUnit);
                    dismiss();
                }
                return;
            }
            ToastUtils.showShort("请完善品类信息！");
        });
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

        void commit(String name, String num, String guige, String danwei);
    }
}
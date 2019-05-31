package com.article.oa_article.module.complanziyuanedit;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.article.oa_article.R;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/3114:43
 * desc   :
 * version: 1.0
 */
public class PopAddDevice extends PopupWindow {

    private Activity activity;

    private EditText photoName;
    private EditText deviceNum;
    private Button commit;
    private TextView cancle;

    private boolean isEdit;


    public PopAddDevice(Activity activity) {
        super(activity);

        this.activity = activity;
        View dialogView = activity.getLayoutInflater().inflate(R.layout.pop_add_devices, null);
        photoName = dialogView.findViewById(R.id.edit_devices_name);
        deviceNum = dialogView.findViewById(R.id.edit_device_num);
        commit = dialogView.findViewById(R.id.next_button);
        cancle = dialogView.findViewById(R.id.cancle);

        commit.setOnClickListener(v -> {
            String name = photoName.getText().toString().trim();
            String num = deviceNum.getText().toString().trim();
            if (StringUtils.isEmpty(name)) {
                ToastUtils.showShort("请输入设备名称");
                return;
            }
            if (StringUtils.isEmpty(num)) {
                ToastUtils.showShort("请输入设备数量");
                return;
            }
            if (listener != null) {
                if (isEdit) {
                    listener.update(name, num);
                } else {
                    listener.commit(name, num);
                }
            }
            dismiss();

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
    public void showAtLocation(View parent) {
        super.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        backgroundAlpha(0.5f);
    }


    public void setData(boolean isEdit, String text, int num) {
        this.isEdit = isEdit;
        photoName.setText(text);
        deviceNum.setText(num + "");
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

        void commit(String name, String num);

        void update(String name, String num);

    }
}

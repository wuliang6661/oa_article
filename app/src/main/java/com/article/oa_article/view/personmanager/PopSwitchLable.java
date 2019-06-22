package com.article.oa_article.view.personmanager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.bean.BuMenFlowBO;
import com.article.oa_article.bean.PersonBO;
import com.article.oa_article.view.bumen.BumenActivity;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/2417:07
 * desc   :
 * version: 1.0
 */
public class PopSwitchLable extends PopupWindow {

    private Activity activity;

    RelativeLayout selectLable;
    TextView bumenText;
    TextView cancle;
    Button commit;

    BuMenFlowBO buMenFlowBO;
    PersonBO personBO;

    public PopSwitchLable(Activity activity) {
        super(activity);

        this.activity = activity;
        View dialogView = activity.getLayoutInflater().inflate(R.layout.pop_switch_lable, null);
        commit = dialogView.findViewById(R.id.next_button);
        cancle = dialogView.findViewById(R.id.cancle);
        selectLable = dialogView.findViewById(R.id.select_person);
        bumenText = dialogView.findViewById(R.id.bumen_name);

        commit.setOnClickListener(v -> {
            if (listener != null) {
                listener.commit(personBO, buMenFlowBO);
            }
            dismiss();
        });
        cancle.setOnClickListener(v -> dismiss());
        selectLable.setOnClickListener(view -> {
            Intent intent = new Intent(activity, BumenActivity.class);
            activity.startActivityForResult(intent, 0x11);
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


    public PopSwitchLable(Fragment fragment) {
        super(fragment.getActivity());

        this.activity = fragment.getActivity();
        View dialogView = activity.getLayoutInflater().inflate(R.layout.pop_switch_lable, null);
        commit = dialogView.findViewById(R.id.next_button);
        cancle = dialogView.findViewById(R.id.cancle);
        selectLable = dialogView.findViewById(R.id.select_person);
        bumenText = dialogView.findViewById(R.id.bumen_name);

        commit.setOnClickListener(v -> {
            if (listener != null) {
                listener.commit(personBO, buMenFlowBO);
            }
            dismiss();
        });
        cancle.setOnClickListener(v -> dismiss());
        selectLable.setOnClickListener(view -> {
            Intent intent = new Intent(activity, BumenActivity.class);
            fragment.startActivityForResult(intent, 0x11);
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


    public void setFlow(BuMenFlowBO buMenFlowBO) {
        if (buMenFlowBO != null) {
            this.buMenFlowBO = buMenFlowBO;
            bumenText.setText(buMenFlowBO.getName());
        } else {
            bumenText.setText("");
        }
    }

    public void setText(PersonBO personBO) {
        this.personBO = personBO;
        bumenText.setText(personBO.getDepart());
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

        void commit(PersonBO personBO, BuMenFlowBO text);

    }

}

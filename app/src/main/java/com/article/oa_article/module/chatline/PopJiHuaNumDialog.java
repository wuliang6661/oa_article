package com.article.oa_article.module.chatline;

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
import com.article.oa_article.bean.ChartBO;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.util.List;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/3012:05
 * desc   :
 * version: 1.0
 */
public class PopJiHuaNumDialog extends PopupWindow {

    private Activity activity;

    private EditText oneNum, twoNum, threeNum, fourNum, fiveNum, sixNum, sevenNum, eightNum, nineNum, ten_month, shiyiNum, shierNum;

    private View dialogView;


    PopJiHuaNumDialog(Activity activity) {
        super(activity);

        this.activity = activity;
        dialogView = activity.getLayoutInflater().inflate(R.layout.pop_edit_jihua, null);
        initView();
        Button commit = dialogView.findViewById(R.id.next_button);
        TextView cancle = dialogView.findViewById(R.id.cancle);
        commit.setOnClickListener(listener);
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


    private void initView() {
        oneNum = dialogView.findViewById(R.id.one_month);
        twoNum = dialogView.findViewById(R.id.two_month);
        threeNum = dialogView.findViewById(R.id.three_month);
        fourNum = dialogView.findViewById(R.id.four_month);
        fiveNum = dialogView.findViewById(R.id.five_month);
        sixNum = dialogView.findViewById(R.id.six_month);
        sevenNum = dialogView.findViewById(R.id.seven_month);
        eightNum = dialogView.findViewById(R.id.eight_month);
        nineNum = dialogView.findViewById(R.id.nine_month);
        ten_month = dialogView.findViewById(R.id.ten_month);
        shiyiNum = dialogView.findViewById(R.id.shiyi_month);
        shierNum = dialogView.findViewById(R.id.shier_month);
    }


    public void setData(List<ChartBO> s) {
        oneNum.setText(s.get(0).getJihuaNum() + "");
        twoNum.setText(s.get(1).getJihuaNum() + "");
        threeNum.setText(s.get(2).getJihuaNum() + "");
        fourNum.setText(s.get(3).getJihuaNum() + "");
        fiveNum.setText(s.get(4).getJihuaNum() + "");
        sixNum.setText(s.get(5).getJihuaNum() + "");
        sevenNum.setText(s.get(6).getJihuaNum() + "");
        eightNum.setText(s.get(7).getJihuaNum() + "");
        nineNum.setText(s.get(8).getJihuaNum() + "");
        ten_month.setText(s.get(9).getJihuaNum() + "");
        shiyiNum.setText(s.get(10).getJihuaNum() + "");
        shierNum.setText(s.get(11).getJihuaNum() + "");
    }


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String one = oneNum.getText().toString().trim();
            String two = twoNum.getText().toString().trim();
            String three = threeNum.getText().toString().trim();
            String four = fourNum.getText().toString().trim();
            String five = fiveNum.getText().toString().trim();
            String six = sixNum.getText().toString().trim();
            String seven = sevenNum.getText().toString().trim();
            String eight = eightNum.getText().toString().trim();
            String nine = nineNum.getText().toString().trim();
            String ten = ten_month.getText().toString().trim();
            String shiyi = shiyiNum.getText().toString().trim();
            String shier = shierNum.getText().toString().trim();
            if (StringUtils.isEmpty(one)) {
                ToastUtils.showShort("请输入1月计划产量！");
                return;
            }
            if (StringUtils.isEmpty(two)) {
                ToastUtils.showShort("请输入2月计划产量！");
                return;
            }
            if (StringUtils.isEmpty(three)) {
                ToastUtils.showShort("请输入3月计划产量！");
                return;
            }
            if (StringUtils.isEmpty(four)) {
                ToastUtils.showShort("请输入4月计划产量！");
                return;
            }
            if (StringUtils.isEmpty(five)) {
                ToastUtils.showShort("请输入5月计划产量！");
                return;
            }
            if (StringUtils.isEmpty(six)) {
                ToastUtils.showShort("请输入6月计划产量！");
                return;
            }
            if (StringUtils.isEmpty(seven)) {
                ToastUtils.showShort("请输入7月计划产量！");
                return;
            }
            if (StringUtils.isEmpty(eight)) {
                ToastUtils.showShort("请输入8月计划产量！");
                return;
            }
            if (StringUtils.isEmpty(nine)) {
                ToastUtils.showShort("请输入9月计划产量！");
                return;
            }
            if (StringUtils.isEmpty(ten)) {
                ToastUtils.showShort("请输入10月计划产量！");
                return;
            }
            if (StringUtils.isEmpty(shiyi)) {
                ToastUtils.showShort("请输入11月计划产量！");
                return;
            }
            if (StringUtils.isEmpty(shier)) {
                ToastUtils.showShort("请输入12月计划产量！");
                return;
            }
            if (commitListener != null) {
                dismiss();
                commitListener.commmit(one, two, three, four, five, six, seven, eight, nine, ten, shiyi, shier);
            }
        }
    };


    /***
     * 显示时将屏幕置为透明
     */
    public void showAtLocation(View parent) {
        super.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
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

    private onCommitListener commitListener;

    void setCommitListener(onCommitListener listener) {
        commitListener = listener;
    }

    interface onCommitListener {

        void commmit(String one, String two, String three, String four, String five, String six,
                     String seven, String eight, String nine, String ten, String shiyi, String shier);
    }
}

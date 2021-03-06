package com.article.oa_article.module.task_allot;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.bean.PersonBO;
import com.article.oa_article.bean.request.AddTaskRequest;
import com.article.oa_article.view.SelectPersonAct;
import com.article.oa_article.widget.DateDialog;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;

public class PopAddTaskWindow extends PopupWindow {

    private Activity activity;
    View dialogView;

    private Button commit;
    private TextView cancle;

    private EditText taskName;
    private RelativeLayout selectPerson;
    private EditText fenpaiNum;
    private EditText danwei;
    private RelativeLayout selectDate;
    private EditText remark;
    private TextView dateText;
    private TextView personName;
    private TextView pop_title;

    private PersonBO personBO;
    private int position = -1;
    AddTaskRequest.OrderTasksBean bean;

    public PopAddTaskWindow(Activity activity) {
        super(activity);

        EventBus.getDefault().register(this);
        this.activity = activity;
        dialogView = activity.getLayoutInflater().inflate(R.layout.pop_add_task, null);
        initView();

        this.setBackgroundDrawable(new ColorDrawable(0));
        this.setContentView(dialogView);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(false);

        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!isOutsideTouchable()) {
                    View mView = getContentView();
                    if (null != mView)
                        mView.dispatchTouchEvent(event);
                }
                return isFocusable() && !isOutsideTouchable();
            }
        });

        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.anim_menu_bottombar);
        //实例化一个ColorDrawable颜色为半透明
        // ColorDrawable dw = new ColorDrawable(0x808080);
        //设置SelectPicPopupWindow弹出窗体的背景
        // this.setBackgroundDrawable(dw);
        this.setOnDismissListener(() -> {
            backgroundAlpha(1f);
            EventBus.getDefault().unregister(this);
        });
    }


    /**
     * 初始化布局
     */
    private void initView() {
        commit = dialogView.findViewById(R.id.next_button);
        cancle = dialogView.findViewById(R.id.cancle);
        taskName = dialogView.findViewById(R.id.edit_task_name);
        selectPerson = dialogView.findViewById(R.id.select_person);
        selectDate = dialogView.findViewById(R.id.select_date);
        fenpaiNum = dialogView.findViewById(R.id.edit_fenpai_num);
        danwei = dialogView.findViewById(R.id.edit_danwei);
        remark = dialogView.findViewById(R.id.edit_beizhu);
        dateText = dialogView.findViewById(R.id.date_text);
        personName = dialogView.findViewById(R.id.person_name);
        pop_title = dialogView.findViewById(R.id.pop_title);

        commit.setOnClickListener(clickListener);
        cancle.setOnClickListener(clickListener);
        selectPerson.setOnClickListener(clickListener);
        selectDate.setOnClickListener(clickListener);
    }

    /**
     * 设置数据
     */
    public void setData(int position, AddTaskRequest.OrderTasksBean bean) {
        this.position = position;
        this.bean = bean;
        personBO = new PersonBO();
        personBO.setName(bean.getNickName());
        personBO.setId(bean.getUserId());
        taskName.setText(bean.getTaskName());
        if (bean.getPlanNum() != 0) {
            fenpaiNum.setText(bean.getPlanNum() + "");
        }
        pop_title.setText("编辑任务");
        danwei.setText(bean.getUnit());
        remark.setText(bean.getRemark());
        personName.setText(personBO.getName());
        if (StringUtils.isEmpty(bean.getPlanCompleteDate())) {
            dateText.setText("");
        } else {
            dateText.setText(bean.getPlanCompleteDate().replaceAll("-", "/"));
        }
    }

    private long endTime;
    private int planNum;

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }


    public void setPlanNum(int planNum, String name, String unit, long time, String remark) {
        this.planNum = planNum;
        fenpaiNum.setText(planNum + "");
        taskName.setText(name);
        danwei.setText(unit);
        this.remark.setText(remark);
        dateText.setText(TimeUtils.millis2String(time,
                new SimpleDateFormat("yyyy/MM/dd")));
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.next_button:
                    String strTaskName = taskName.getText().toString().trim();
                    String strDate = dateText.getText().toString().trim();
                    String num = fenpaiNum.getText().toString().trim();
                    String strDanwei = danwei.getText().toString().trim();
                    String strRemark = remark.getText().toString().trim();
                    if (StringUtils.isEmpty(strTaskName)) {
                        ToastUtils.showShort("请输入任务名称!");
                        return;
                    }
                    if (StringUtils.isEmpty(num)) {
                        ToastUtils.showShort("请输入分派数量!");
                        return;
                    }
                    if (StringUtils.isEmpty(strDate)) {
                        ToastUtils.showShort("请选择任务时限!");
                        return;
                    }
                    if (personBO == null) {
                        ToastUtils.showShort("请选择执行人!");
                        return;
                    }
                    if (listener != null) {
                        listener.commit(position, strTaskName, num, strDanwei, personBO, strDate, strRemark, bean);
                    }
                    dismiss();
                    break;
                case R.id.cancle:
                    dismiss();
                    break;
                case R.id.select_person:
                    Intent intent = new Intent(activity, SelectPersonAct.class);
                    activity.startActivity(intent);
                    break;
                case R.id.select_date:
                    DateDialog.show(activity, dateText, endTime);
                    break;
            }
        }
    };


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PersonBO a) {
        this.personBO = a;
        personName.setText(personBO.getName());
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

        void commit(int position, String name, String num, String danwei, PersonBO personBO, String date, String remark, AddTaskRequest.OrderTasksBean bean);
    }

}

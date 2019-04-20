package com.wul.oa_article.module.task_accept;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.wul.oa_article.R;
import com.wul.oa_article.bean.TaskBO;
import com.wul.oa_article.mvp.MVPBaseFragment;
import com.wul.oa_article.view.AcceptedTaskActivity;
import com.wul.oa_article.view.MyOrderActivity;
import com.wul.oa_article.widget.AlertDialog;

import java.text.SimpleDateFormat;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 接受任务
 */

public class Task_acceptFragment extends MVPBaseFragment<Task_acceptContract.View, Task_acceptPresenter>
        implements Task_acceptContract.View {


    @BindView(R.id.task_bar)
    LinearLayout taskBar;
    @BindView(R.id.task_name)
    TextView taskName;
    @BindView(R.id.task_person)
    TextView taskPerson;
    @BindView(R.id.task_num)
    TextView taskNum;
    @BindView(R.id.task_danwei)
    TextView taskDanwei;
    @BindView(R.id.task_date)
    TextView taskDate;
    @BindView(R.id.task_remart)
    TextView taskRemart;
    @BindView(R.id.next_button)
    Button nextButton;
    @BindView(R.id.my_suress)
    Button mySuress;
    @BindView(R.id.task_fenpai)
    Button taskFenpai;
    @BindView(R.id.buttom_layout)
    LinearLayout buttomLayout;
    Unbinder unbinder;
    @BindView(R.id.task_layout)
    LinearLayout taskLayout;
    @BindView(R.id.task_check)
    CheckBox taskCheck;

    TaskBO.TaskBean taskBean;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_accept_task, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @OnClick(R.id.task_bar)
    public void clickBar() {
        if (taskLayout.getVisibility() == View.VISIBLE) {
            taskLayout.setVisibility(View.GONE);
            taskCheck.setChecked(true);
        } else {
            taskLayout.setVisibility(View.VISIBLE);
            taskCheck.setChecked(false);
        }
    }


    /**
     * 接受任务
     */
    @OnClick(R.id.next_button)
    public void acceptTask() {
        nextButton.setVisibility(View.GONE);
        buttomLayout.setVisibility(View.VISIBLE);
    }


    @OnClick({R.id.my_suress, R.id.task_fenpai})
    public void buttonClick(View view) {
        switch (view.getId()) {
            case R.id.my_suress:   //自己完成
                new AlertDialog(Objects.requireNonNull(getActivity())).builder().setGone().setMsg("选择自己完成后无法分派任务\n是否继续？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", v -> mPresenter.setTaskMode(taskBean.getId(), 0)).show();
                break;
            case R.id.task_fenpai:   //分派任务
                new AlertDialog(Objects.requireNonNull(getActivity())).builder().setGone().setMsg("是否确定分派任务？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", v -> mPresenter.setTaskMode(taskBean.getId(), 1)).show();
                break;
        }
    }


    /**
     * 设置数据
     */
    public void setTask(TaskBO task) {
        this.taskBean = task.getTask();
        taskName.setText(taskBean.getTaskName());
//        taskPerson.setText(taskBean.get);   //执行人
        taskNum.setText(taskBean.getNum() + "");
//        taskDanwei.setText(taskBean.get);   //单位
        taskDate.setText(taskBean.getPlanCompleteDate());
//        taskRemart.setText(taskBean.);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRequestError(String msg) {
        showToast(msg);
    }

    @Override
    public void sourss(int type) {
        if (type == 0) {   //自己完成
            Bundle bundle = new Bundle();
            bundle.putInt("taskId", taskBean.getId());
            gotoActivity(MyOrderActivity.class, bundle, false);
        } else {

        }
    }
}

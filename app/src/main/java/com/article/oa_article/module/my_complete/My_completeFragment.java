package com.article.oa_article.module.my_complete;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.article.oa_article.R;
import com.article.oa_article.bean.TaskDetails;
import com.article.oa_article.bean.event.UpdateTaskEvent;
import com.article.oa_article.mvp.MVPBaseFragment;
import com.article.oa_article.widget.AlertDialog;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 自己完成模块
 */

public class My_completeFragment extends MVPBaseFragment<My_completeContract.View, My_completePresenter>
        implements My_completeContract.View {


    @BindView(R.id.kehu_msg_bar)
    LinearLayout kehuMsgBar;
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
    @BindView(R.id.task_all_num)
    TextView taskAllNum;
    @BindView(R.id.task_num_edit)
    ImageView taskNumEdit;
    @BindView(R.id.my_task_layout)
    LinearLayout myTaskLayout;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    Unbinder unbinder;
    @BindView(R.id.next_button)
    Button nextButton;

    TaskDetails taskBean;
    @BindView(R.id.none_text)
    TextView noneText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_my_complete, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider_inset));
        recycleView.addItemDecoration(itemDecoration);
    }


    @OnClick(R.id.task_num_edit)
    public void editNum() {
        PopTaskNumWindow window = new PopTaskNumWindow(getActivity());
        window.setListener(text -> mPresenter.updateNum(taskBean.getTaskInfo().getId(), text));
        window.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }


    @OnClick(R.id.next_button)
    public void commitTask() {
        new AlertDialog(Objects.requireNonNull(getActivity())).builder().setGone().setMsg("确认完成任务？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", v -> mPresenter.commitTask(taskBean.getTaskInfo().getId())).show();

    }


    /**
     * 设置是否可编辑
     */
    public void setIsEdit(boolean isEdit) {
        if (isEdit) {
            taskNumEdit.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.VISIBLE);
        } else {
            taskNumEdit.setVisibility(View.GONE);
            nextButton.setVisibility(View.GONE);
        }
    }

    /**
     * 设置数据
     */
    public void setTask(TaskDetails task) {
        new Handler().post(() -> {
            My_completeFragment.this.taskBean = task;
            taskName.setText(taskBean.getTaskInfo().getTaskName());
            taskPerson.setText(taskBean.getTaskInfo().getNickName());   //执行人
            taskNum.setText(taskBean.getTaskInfo().getPlanNum() + "");
            taskDanwei.setText(taskBean.getTaskInfo().getUnit());   //单位
            taskDate.setText(TimeUtils.millis2String(taskBean.getTaskInfo().getPlanCompleteDate(), new SimpleDateFormat("yyyy/MM/dd")));
            taskRemart.setText(taskBean.getTaskInfo().getRemark());
            if (!StringUtils.isEmpty(taskBean.getTaskInfo().getActualNum())) {
                taskAllNum.setText(taskBean.getTaskInfo().getActualNum() + "");
            }
            setIsEdit(taskBean.getTaskInfo().getCanEdit() == 1);
            if (task.getTaskHistory() == null || task.getTaskHistory().size() == 0) {
                recycleView.setVisibility(View.GONE);
                noneText.setVisibility(View.VISIBLE);
            } else {
                recycleView.setVisibility(View.VISIBLE);
                noneText.setVisibility(View.GONE);
            }
            setAdapter();
        });
    }


    /**
     * 设置适配器
     */
    private void setAdapter() {
        LGRecycleViewAdapter<TaskDetails.TaskHistoryBean> adapter =
                new LGRecycleViewAdapter<TaskDetails.TaskHistoryBean>(taskBean.getTaskHistory()) {
                    @Override
                    public int getLayoutId(int viewType) {
                        return R.layout.item_task_num;
                    }

                    @SuppressLint("SimpleDateFormat")
                    @Override
                    public void convert(LGViewHolder holder, TaskDetails.TaskHistoryBean taskHistoryBean, int position) {
                        TextView num = (TextView) holder.getView(R.id.num);
                        if (taskHistoryBean.getNum() < 0) {
                            num.setTextColor(Color.parseColor("#FA0707"));
                        } else {
                            num.setTextColor(Color.parseColor("#ffffff"));
                        }
                        num.setText(taskHistoryBean.getNum() + "");
                        holder.setText(R.id.date, TimeUtils.millis2String(taskHistoryBean.getCreateDate(),
                                new SimpleDateFormat("yyyy/MM/dd")));
                    }
                };
        recycleView.setAdapter(adapter);
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
    public void updateNumSuress() {
        showToast("修改成功！");
        EventBus.getDefault().post(new UpdateTaskEvent());
    }

    @Override
    public void commitSuress() {
        showToast("任务完成成功！");
        EventBus.getDefault().post(new UpdateTaskEvent());
    }
}

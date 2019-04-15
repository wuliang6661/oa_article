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

import com.wul.oa_article.R;
import com.wul.oa_article.mvp.MVPBaseFragment;

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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

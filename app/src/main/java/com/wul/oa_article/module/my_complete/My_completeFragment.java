package com.wul.oa_article.module.my_complete;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wul.oa_article.R;
import com.wul.oa_article.mvp.MVPBaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

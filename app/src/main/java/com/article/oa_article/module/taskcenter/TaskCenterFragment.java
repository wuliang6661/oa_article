package com.article.oa_article.module.taskcenter;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.article.oa_article.R;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.TaskCenterBo;
import com.article.oa_article.bean.request.PageRequest;
import com.article.oa_article.mvp.MVPBaseFragment;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * MVPPlugin
 * 任务中心
 */

public class TaskCenterFragment extends MVPBaseFragment<TaskCenterContract.View, TaskCenterPresenter>
        implements TaskCenterContract.View {


    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_task_center, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        PageRequest request = new PageRequest();
        request.setId(Integer.parseInt(MyApplication.getCommonId()));
        mPresenter.getTaskList(request);
    }


    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider_inset));
        recycleView.addItemDecoration(itemDecoration);
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
    public void getTaskList(List<TaskCenterBo> taskCenterBos) {
        setAdapter(taskCenterBos);
    }


    private void setAdapter(List<TaskCenterBo> taskCenterBos) {
        LGRecycleViewAdapter<TaskCenterBo> adapter = new LGRecycleViewAdapter<TaskCenterBo>(taskCenterBos) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_task_center;
            }

            @Override
            public void convert(LGViewHolder holder, TaskCenterBo taskCenterBo, int position) {
                holder.setText(R.id.user_name, taskCenterBo.getUserName());
                holder.setText(R.id.bumen_name, taskCenterBo.getDepartName());
                holder.setText(R.id.task_num, taskCenterBo.getTotle() + "");
                holder.setText(R.id.yuqi_num, taskCenterBo.getOverdue() + "");
                holder.setText(R.id.yuqilv, taskCenterBo.getOverdueRate());
            }
        };
        recycleView.setAdapter(adapter);
    }

}

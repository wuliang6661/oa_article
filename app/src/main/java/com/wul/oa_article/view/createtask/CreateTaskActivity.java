package com.wul.oa_article.view.createtask;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wul.oa_article.R;
import com.wul.oa_article.mvp.MVPBaseActivity;
import com.wul.oa_article.view.createorder.CreateOrderFragment;
import com.wul.oa_article.widget.SlideRecyclerView;
import com.wul.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.wul.oa_article.widget.lgrecycleadapter.LGViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 创建任务
 */

public class CreateTaskActivity extends MVPBaseActivity<CreateTaskContract.View, CreateTaskPresenter>
        implements CreateTaskContract.View {


    @BindView(R.id.kehu_order_check)
    CheckBox kehuOrderCheck;
    @BindView(R.id.order_edit)
    TextView orderEdit;
    @BindView(R.id.kehu_msg_bar)
    LinearLayout kehuMsgBar;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @BindView(R.id.task_check)
    CheckBox taskCheck;
    @BindView(R.id.task_right_button)
    TextView taskRightButton;
    @BindView(R.id.task_bar)
    LinearLayout taskBar;
    @BindView(R.id.task_recycle_view)
    SlideRecyclerView taskRecycleView;
    @BindView(R.id.task_list_layout)
    LinearLayout taskListLayout;
    @BindView(R.id.continue_add)
    TextView continueAdd;
    @BindView(R.id.task_suress)
    TextView taskSuress;
    @BindView(R.id.add_task_layout)
    RelativeLayout addTaskLayout;
    @BindView(R.id.content_view)
    LinearLayout contentView;


    private boolean isShunYan = true;  //默认是一键顺延

    @Override
    protected int getLayout() {
        return R.layout.act_create_task;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("订单详情");

        initView();
    }


    /**
     * 初始化布局
     */
    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        taskRecycleView.setLayoutManager(manager);
        taskRecycleView.setNestedScrollingEnabled(false);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_inset));
        taskRecycleView.addItemDecoration(itemDecoration);

        setTaskAdapter();
    }


    @OnClick({R.id.kehu_msg_bar, R.id.task_bar})
    public void barClick(View view) {
        switch (view.getId()) {
            case R.id.kehu_msg_bar:
                fragmentContainer.setVisibility(View.VISIBLE);
                kehuMsgBar.setVisibility(View.GONE);
                goToFragment(new CreateOrderFragment());
                break;
            case R.id.task_bar:
                if (taskListLayout.getVisibility() == View.VISIBLE) {
                    taskListLayout.setVisibility(View.GONE);
                    addTaskLayout.setVisibility(View.GONE);
                    taskCheck.setChecked(true);
                } else {
                    taskListLayout.setVisibility(View.VISIBLE);
                    addTaskLayout.setVisibility(View.VISIBLE);
                    taskCheck.setChecked(false);
                }
                break;
        }
    }


    @OnClick({R.id.task_right_button, R.id.continue_add, R.id.task_suress})
    public void rightClick(View view) {
        switch (view.getId()) {
            case R.id.task_right_button:
                if (isShunYan) {     //显示顺延弹窗
                    PopShunYanWindow shunYanWindow = new PopShunYanWindow(this);
                    shunYanWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
                } else {     //任务编辑
                    isShunYan = true;
                    taskRightButton.setText("一键顺延");
                    addTaskLayout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.continue_add:    //添加任务(显示添加任务弹窗)
                PopAddTaskWindow window = new PopAddTaskWindow(this);
                window.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.task_suress:   //完成
                isShunYan = false;
                taskRightButton.setText("任务编辑");
                addTaskLayout.setVisibility(View.GONE);
                break;
        }
    }


    /**
     * 设置任务列表布局
     */
    private void setTaskAdapter() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        LGRecycleViewAdapter<String> adapter = new LGRecycleViewAdapter<String>(list) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_task_layout;
            }

            @Override
            public void convert(LGViewHolder holder, String s, int position) {
            }
        };
        adapter.setOnItemClickListener(R.id.tv_delete, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                taskRecycleView.closeMenu();
            }
        });
        taskRecycleView.setAdapter(adapter);
    }


    private Fragment mContent = null;

    /**
     * 修改显示的内容 不会重新加载
     **/
    public void goToFragment(Fragment to) {
        if (mContent != to) {
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            if (!to.isAdded()) { // 先判断是否被add过
                if (mContent != null)
                    transaction.hide(mContent).add(R.id.fragment_container, to).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
                else
                    transaction.add(R.id.fragment_container, to).commitAllowingStateLoss();
            } else {
                if (mContent != null)
                    transaction.hide(mContent).show(to).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
                else
                    transaction.show(to).commitAllowingStateLoss();
            }
            mContent = to;
        }
    }

}

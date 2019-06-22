package com.article.oa_article.view.createmoban;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.article.oa_article.R;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.PersonBO;
import com.article.oa_article.bean.TempleteBO;
import com.article.oa_article.bean.TempleteInfoBo;
import com.article.oa_article.bean.request.AddTempleteBo;
import com.article.oa_article.mvp.MVPBaseActivity;
import com.article.oa_article.widget.AlertDialog;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CreateMoBanActivity extends MVPBaseActivity<CreateMoBanContract.View, CreateMoBanPresenter>
        implements CreateMoBanContract.View {

    @BindView(R.id.next_button)
    Button nextButton;
    @BindView(R.id.edit_moban_name)
    EditText editMobanName;
    @BindView(R.id.edit_remark)
    EditText editRemark;
    @BindView(R.id.renwu_bar)
    LinearLayout renwuBar;
    @BindView(R.id.recycle_view)
    SwipeMenuRecyclerView recycleView;
    @BindView(R.id.add_moban)
    LinearLayout addMoban;

    private boolean isAdd = true;
    private int id;

    List<AddTempleteBo.TaskTemplateDetailsBean> list;
    TempleteInfoBo infoBo;

    TempleteBO templeteBO;

    @Override
    protected int getLayout() {
        return R.layout.act_create_moban;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("新增模板");

        initView();
        setSwipeMenu();
        list = new ArrayList<>();
        isAdd = getIntent().getExtras().getBoolean("isAdd", true);
        templeteBO = new TempleteBO();
        templeteBO.setTaskInfo(new ArrayList<>());
        if (!isAdd) {
            id = getIntent().getExtras().getInt("id");
//            mPresenter.getMoBanInfo(id);
            templeteBO = (TempleteBO) getIntent().getExtras().getSerializable("templete");
            editMobanName.setText(templeteBO.getName());
            editRemark.setText(templeteBO.getRemarks());
            setAddAdapter();
        }
    }


    /**
     * 初始化布局
     */
    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);
        recycleView.setNestedScrollingEnabled(false);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_inset));
        recycleView.addItemDecoration(itemDecoration);
    }


    private void setSwipeMenu() {
        // 创建菜单：
        SwipeMenuCreator mSwipeMenuCreator = (leftMenu, rightMenu, viewType) -> {
            // 2 删除
            SwipeMenuItem deleteItem = new SwipeMenuItem(this);
            deleteItem.setText("删除")
                    .setBackgroundColor(getResources().getColor(R.color.item_delete))
                    .setTextColor(Color.WHITE) // 文字颜色。
                    .setTextSize(15) // 文字大小。
                    .setWidth(SizeUtils.dp2px(63))
                    .setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            rightMenu.addMenuItem(deleteItem);
            // 注意：哪边不想要菜单，那么不要添加即可。
        };
        // 设置监听器。
        recycleView.setSwipeMenuCreator(mSwipeMenuCreator);
        SwipeMenuItemClickListener mMenuItemClickListener = menuBridge -> {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();
            new AlertDialog(CreateMoBanActivity.this).builder().setGone().setMsg("是否确认删除该执行人？")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确定", v -> {
                        templeteBO.getTaskInfo().remove(menuBridge.getAdapterPosition());
                        setAddAdapter();
                    }).show();
        };
        // 菜单点击监听。
        recycleView.setSwipeMenuItemClickListener(mMenuItemClickListener);
    }

    @Override
    public void onRequestError(String msg) {
        showToast(msg);
    }


    @OnClick(R.id.add_moban)
    public void addMoBan() {
        PopAddMoBan shunYanWindow = new PopAddMoBan(this, "添加任务", null, null, 0);
        shunYanWindow.setListener((position, taskName, person) -> addMoBanBean(taskName, person));
        shunYanWindow.showAtLocation(this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    /**
     * 提交
     */
    @OnClick(R.id.next_button)
    public void commit() {
        String strMoBanName = editMobanName.getText().toString().trim();
        String strRemark = editRemark.getText().toString().trim();
        if (StringUtils.isEmpty(strMoBanName)) {   //模板名称为空
            showToast("请输入模板名称！");
            return;
        }
        AddTempleteBo templeteBo = new AddTempleteBo();
        templeteBo.setName(strMoBanName);
        templeteBo.setRemark(strRemark);
        list.clear();
        for (TempleteBO.TaskInfoBean infoBean : templeteBO.getTaskInfo()) {
            AddTempleteBo.TaskTemplateDetailsBean bean = new AddTempleteBo.TaskTemplateDetailsBean();
            bean.setName(infoBean.getTaskName());
            bean.setUserId(infoBean.getUserId());
//            bean.setName(infoBean.getNickName());
            list.add(bean);
        }
        if(list.isEmpty()){
            showToast("请添加执行人！");
            return;
        }
        templeteBo.setTaskTemplateDetails(list);
        templeteBo.setCompanyId(Integer.parseInt(MyApplication.getCommonId()));
        if (isAdd) {    //新增模板
            mPresenter.addTemplete(templeteBo);
        } else {
            templeteBo.setId(id + "");
            mPresenter.editTemplete(templeteBo);
        }
    }


    private void addMoBanBean(String taskName, PersonBO person) {
        TempleteBO.TaskInfoBean bean = new TempleteBO.TaskInfoBean();
        bean.setTaskName(taskName);
        bean.setUserId(person.getId());
        bean.setNickName(person.getName());
        templeteBO.getTaskInfo().add(bean);
        setAddAdapter();
    }


    private void setAddAdapter() {
        LGRecycleViewAdapter<TempleteBO.TaskInfoBean> adapter =
                new LGRecycleViewAdapter<TempleteBO.TaskInfoBean>(templeteBO.getTaskInfo()) {
                    @Override
                    public int getLayoutId(int viewType) {
                        return R.layout.item_moban_task;
                    }

                    @Override
                    public void convert(LGViewHolder holder, TempleteBO.TaskInfoBean taskTemplateDetailsBean, int position) {
                        holder.setText(R.id.task_name, taskTemplateDetailsBean.getTaskName());
                        holder.setText(R.id.person_name, taskTemplateDetailsBean.getNickName());
                    }
                };
        adapter.setOnItemClickListener(R.id.item_layout, (view, position) -> {
            TempleteBO.TaskInfoBean taskInfoBean = templeteBO.getTaskInfo().get(position);
            PopAddMoBan shunYanWindow = new PopAddMoBan(CreateMoBanActivity.this, "修改任务", taskInfoBean.getTaskName(), taskInfoBean.getNickName(),
                    taskInfoBean.getUserId());
            shunYanWindow.setSelectPosition(position);
            shunYanWindow.setListener((selectPosition, taskName, person) -> {
                if (selectPosition != -1) {
                    TempleteBO.TaskInfoBean bean = new TempleteBO.TaskInfoBean();
                    bean.setTaskName(taskName);
                    bean.setUserId(person.getId());
                    bean.setNickName(person.getName());
                    templeteBO.getTaskInfo().set(position, bean);
                    setAddAdapter();
                }
            });
            shunYanWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        });
        recycleView.setAdapter(adapter);
    }

    @Override
    public void getMoBanInfo(TempleteInfoBo infoBo) {
        this.infoBo = infoBo;
        editMobanName.setText(infoBo.getTemplateInfo().getName());
        editRemark.setText(infoBo.getTemplateInfo().getRemark());
//        this.list = infoBo.getTemplateDetailList();
//        setAddAdapter();
    }

    @Override
    public void onSuress() {
        showToast("操作成功！");
        finish();
    }
}

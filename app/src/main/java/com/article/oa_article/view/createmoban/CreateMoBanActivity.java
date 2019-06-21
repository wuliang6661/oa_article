package com.article.oa_article.view.createmoban;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.StringUtils;
import com.article.oa_article.R;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.PersonBO;
import com.article.oa_article.bean.TempleteBO;
import com.article.oa_article.bean.TempleteInfoBo;
import com.article.oa_article.bean.request.AddTempleteBo;
import com.article.oa_article.mvp.MVPBaseActivity;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;

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
    RecyclerView recycleView;
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


    @Override
    public void onRequestError(String msg) {
        showToast(msg);
    }


    @OnClick(R.id.add_moban)
    public void addMoBan() {
        PopAddMoBan shunYanWindow = new PopAddMoBan(this, null, null, 0);
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
            PopAddMoBan shunYanWindow = new PopAddMoBan(CreateMoBanActivity.this, taskInfoBean.getTaskName(), taskInfoBean.getNickName(),
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

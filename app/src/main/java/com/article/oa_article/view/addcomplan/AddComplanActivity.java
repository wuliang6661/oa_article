package com.article.oa_article.view.addcomplan;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.article.oa_article.R;
import com.article.oa_article.bean.ApplyComplanBO;
import com.article.oa_article.mvp.MVPBaseActivity;
import com.article.oa_article.view.NoneFragment;
import com.article.oa_article.widget.AlertDialog;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;
import com.blankj.utilcode.util.FragmentUtils;

import java.util.List;

import butterknife.BindView;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AddComplanActivity extends MVPBaseActivity<AddComplanContract.View, AddComplanPresenter>
        implements AddComplanContract.View {

    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.person_list)
    RecyclerView personList;
    @BindView(R.id.none_fragment)
    FrameLayout noneFragment;

    @Override
    protected int getLayout() {
        return R.layout.act_add_complan;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("企业查询");

        initView();
        setListener();
        mPresenter.getComplanList("");
    }


    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        personList.setLayoutManager(manager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_inset));
        personList.addItemDecoration(itemDecoration);
    }

    private void setListener() {
        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPresenter.getComplanList(editable.toString());
            }
        });
    }


    @Override
    public void onRequestError(String msg) {
        showToast(msg);
    }

    @Override
    public void getComplanList(List<ApplyComplanBO> complanBOS) {
        if (complanBOS.isEmpty()) {
            personList.setVisibility(View.GONE);
            noneFragment.setVisibility(View.VISIBLE);
            FragmentUtils.replace(getSupportFragmentManager(), new NoneFragment(), R.id.none_fragment);
            return;
        }
        personList.setVisibility(View.VISIBLE);
        noneFragment.setVisibility(View.GONE);
        LGRecycleViewAdapter<ApplyComplanBO> adapter = new LGRecycleViewAdapter<ApplyComplanBO>(complanBOS) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_apply_complan;
            }

            @Override
            public void convert(LGViewHolder holder, ApplyComplanBO applyComplanBO, int position) {
                holder.setText(R.id.complan_name, applyComplanBO.getCompanyName());
            }
        };
        adapter.setOnItemClickListener(R.id.select_button, (view, position) -> {
            new AlertDialog(AddComplanActivity.this).builder().setGone().setMsg("您将申请加入" + complanBOS.get(position).getCompanyName() +
                    "\n是否继续？")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确定", v -> mPresenter.applyComplan(complanBOS.get(position).getId())).show();
        });
        personList.setAdapter(adapter);
    }

    @Override
    public void addSounrss() {
        gotoActivity(SuressActivity.class, false);
    }
}

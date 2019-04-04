package com.wul.oa_article.view.createorder;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.StringUtils;
import com.wul.oa_article.R;
import com.wul.oa_article.mvp.MVPBaseActivity;
import com.wul.oa_article.widget.ExpandLayout;
import com.wul.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.wul.oa_article.widget.lgrecycleadapter.LGViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CreateOrderActivity extends MVPBaseActivity<CreateOrderContract.View, CreateOrderPresenter>
        implements CreateOrderContract.View {

    @BindView(R.id.edit_kehu_jiancheng)
    EditText editKehuJiancheng;
    @BindView(R.id.edit_kehu_ordername)
    EditText editKehuOrdername;
    @BindView(R.id.edit_kehu_ordernum)
    EditText editKehuOrdernum;
    @BindView(R.id.ben_expand_layout)
    ExpandLayout benExpandLayout;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.pinglei_expand_layout)
    ExpandLayout pingleiExpandLayout;
    @BindView(R.id.image_recycle)
    RecyclerView imageRecycle;
    @BindView(R.id.update_img_expand)
    ExpandLayout updateImgExpand;
    @BindView(R.id.beizhu_expandlayout)
    ExpandLayout beizhuExpandlayout;
    @BindView(R.id.next_button)
    Button nextButton;
    @BindView(R.id.kehu_expand_layout)
    ExpandLayout kehuExpandLayout;
    @BindView(R.id.edit_pinglei_name)
    EditText editPingleiName;
    @BindView(R.id.edit_pinglei_guige)
    EditText editPingleiGuige;
    @BindView(R.id.edit_pinglei_num)
    EditText editPingleiNum;
    @BindView(R.id.edit_pinglei_danwei)
    EditText editPingleiDanwei;


    List<PingLeiBO> pingLeiBOS;   //添加的品类列表
    List<ImageBO> imageBOS;      //添加的图片列表

    @Override
    protected int getLayout() {
        return R.layout.act_create_order;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goBack();
        setTitleText("创建订单");

        pingLeiBOS = new ArrayList<>();
        imageBOS = new ArrayList<>();
        initView();
    }


    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);
        recycleView.setNestedScrollingEnabled(false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        imageRecycle.setLayoutManager(gridLayoutManager);
        imageRecycle.setNestedScrollingEnabled(false);

        setPingLeiAdapter();
        setImageAdapter();
    }


    /**
     * 品类添加
     */
    @OnClick(R.id.add_img)
    public void addPingLei() {
        String pingName = editPingleiName.getText().toString().trim();
        String pingGuige = editPingleiGuige.getText().toString().trim();
        String pingNum = editPingleiNum.getText().toString().trim();
        String pingDanWei = editPingleiDanwei.getText().toString().trim();
        if (StringUtils.isEmpty(pingName)) {
            showToast("请输入自定义名称！");
            return;
        }
        if (StringUtils.isEmpty(pingGuige)) {
            showToast("请输入规格！");
            return;
        }
        if (StringUtils.isEmpty(pingNum)) {
            showToast("请输入数量！");
            return;
        }
        if (StringUtils.isEmpty(pingDanWei)) {
            showToast("请输入单位！");
            return;
        }
        PingLeiBO pingLeiBO = new PingLeiBO(pingName, pingGuige, pingNum, pingDanWei);
        pingLeiBOS.add(pingLeiBO);
        setPingLeiAdapter();
    }


    /**
     * 设置品类列表显示
     */
    private void setPingLeiAdapter() {
        LGRecycleViewAdapter<PingLeiBO> adapter = new LGRecycleViewAdapter<PingLeiBO>(pingLeiBOS) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_pinglei;
            }

            @Override
            public void convert(LGViewHolder holder, PingLeiBO pingLeiBO, int position) {
                holder.setText(R.id.pinglei_name, pingLeiBO.name);
                holder.setText(R.id.pinglei_guige, pingLeiBO.guige);
                holder.setText(R.id.pinglei_num, pingLeiBO.num);
                holder.setText(R.id.pinglei_danwei, pingLeiBO.danwei);
            }
        };
        recycleView.setAdapter(adapter);
    }


    /**
     * 设置添加图片的适配器
     */
    private void setImageAdapter() {
        ImageAddAdapter addAdapter = new ImageAddAdapter(this, imageBOS);

        imageRecycle.setAdapter(addAdapter);
    }


    /**
     * 折叠布局的展开收缩处理
     */
    @OnClick({R.id.kehu_msg_bar, R.id.bengongsi_msg_bar, R.id.pinglei_bar,
            R.id.update_img_bar, R.id.beizhu_bar})
    public void barClick(View view) {
        switch (view.getId()) {
            case R.id.kehu_msg_bar:
                kehuExpandLayout.toggleExpand();
                break;
            case R.id.bengongsi_msg_bar:
                benExpandLayout.toggleExpand();
                break;
            case R.id.pinglei_bar:
                pingleiExpandLayout.toggleExpand();
                break;
            case R.id.update_img_bar:
                updateImgExpand.toggleExpand();
                break;
            case R.id.beizhu_bar:
                beizhuExpandlayout.toggleExpand();
                break;
        }
    }
}

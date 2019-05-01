package com.article.oa_article.module.order_details;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.article.oa_article.R;
import com.article.oa_article.bean.OrderInfoBo;
import com.article.oa_article.module.create_order.ImageBO;
import com.article.oa_article.module.create_order.PingLeiBO;
import com.article.oa_article.mvp.MVPBaseFragment;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 订单详情模块
 */

public class Order_detailsFragment extends MVPBaseFragment<Order_detailsContract.View, Order_detailsPresenter>
        implements Order_detailsContract.View {


    @BindView(R.id.kehu_order_check)
    CheckBox kehuOrderCheck;
    @BindView(R.id.kehu_msg_bar)
    LinearLayout kehuMsgBar;
    @BindView(R.id.create_name)
    TextView createName;
    @BindView(R.id.kehu_name)
    TextView kehuName;
    @BindView(R.id.kehu_order_num)
    TextView kehuOrderNum;
    @BindView(R.id.kehu_order_name)
    TextView kehuOrderName;
    @BindView(R.id.order_num)
    TextView orderNum;
    @BindView(R.id.order_danwei)
    TextView orderDanwei;
    @BindView(R.id.order_date)
    TextView orderDate;
    @BindView(R.id.chicun_recycle_view)
    RecyclerView chicunRecycleView;
    @BindView(R.id.beizhu)
    TextView beizhu;
    @BindView(R.id.image_recycle)
    RecyclerView imageRecycle;
    @BindView(R.id.kehu_order_layout)
    RelativeLayout kehuOrderLayout;
    Unbinder unbinder;
    @BindView(R.id.order_status_img)
    ImageView orderStatusImg;

    List<PingLeiBO> pingLeiBOS;   //添加的品类列表
    List<ImageBO> imageBOS;      //添加的图片列表

    boolean isTask = true;
    @BindView(R.id.image_title)
    LinearLayout imageTitle;
    @BindView(R.id.blow_line)
    View blowLine;


    public static Order_detailsFragment newInstance(int type, int orderId) {
        Order_detailsFragment fragment = new Order_detailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("orderId", orderId);
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_order_details, null);
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
        chicunRecycleView.setLayoutManager(manager);

        GridLayoutManager imageManager = new GridLayoutManager(getActivity(), 3);
        imageRecycle.setLayoutManager(imageManager);

        chicunRecycleView.setNestedScrollingEnabled(false);
        imageRecycle.setNestedScrollingEnabled(false);
    }

    @OnClick(R.id.kehu_msg_bar)
    public void barOnClick() {
        if (kehuOrderLayout.getVisibility() == View.VISIBLE) {
            kehuOrderLayout.setVisibility(View.GONE);
            kehuOrderCheck.setChecked(true);
        } else {
            kehuOrderLayout.setVisibility(View.VISIBLE);
            kehuOrderCheck.setChecked(false);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    /**
     * 设置品类列表显示
     */
    private void setPingLeiAdapter() {
        LGRecycleViewAdapter<PingLeiBO> adapter = new LGRecycleViewAdapter<PingLeiBO>(pingLeiBOS) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_orderdetails_chicun;
            }

            @Override
            public void convert(LGViewHolder holder, PingLeiBO pingLeiBO, int position) {
                holder.setText(R.id.name, pingLeiBO.name);
                holder.setText(R.id.guige, pingLeiBO.size);
                holder.setText(R.id.num, pingLeiBO.num);
                holder.setText(R.id.danwei, pingLeiBO.unit);
            }
        };
        chicunRecycleView.setAdapter(adapter);
    }

    /**
     * 设置图片显示
     */
    private void setImageAdapter() {
        LGRecycleViewAdapter<ImageBO> adapter = new LGRecycleViewAdapter<ImageBO>(imageBOS) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_add_image;
            }

            @Override
            public void convert(LGViewHolder holder, ImageBO imageBO, int position) {
                holder.getView(R.id.delete_img).setVisibility(View.GONE);
                holder.setImageUrl(getActivity(), R.id.image, imageBO.url);
                holder.setText(R.id.edit_image_name, imageBO.name);
                holder.getView(R.id.edit_image_name).setVisibility(View.INVISIBLE);
            }
        };
        imageRecycle.setAdapter(adapter);
    }


    /**
     * 设置数据
     */
    public void setOrderInfo(OrderInfoBo info) {
        new Handler().post(() -> getOrderInfo(info));
    }


    public void setIsTask(boolean isTask) {
        this.isTask = isTask;
    }


    @Override
    public void getOrderInfo(OrderInfoBo infoBo) {
        stopProgress();
        createName.setText(infoBo.getOrderInfo().getNickName());
        kehuName.setText(infoBo.getOrderInfo().getClientName());
        kehuOrderName.setText(infoBo.getOrderInfo().getClientOrderName());
        kehuOrderNum.setText(infoBo.getOrderInfo().getClientOrderNum());
        orderDanwei.setText(infoBo.getOrderInfo().getUnit());
        orderNum.setText(infoBo.getOrderInfo().getNum() + "");
        orderDate.setText(TimeUtils.millis2String(infoBo.getOrderInfo()
                .getPlanCompleteDate(), new SimpleDateFormat("yyyy/MM/dd")));
        beizhu.setText(infoBo.getOrderInfo().getRemark());
        this.pingLeiBOS = infoBo.getOrderSpecifications();
        this.imageBOS = infoBo.getOrderInfo().getImage();
        if (infoBo.getOrderInfo().getStatus() == 1) {
            orderStatusImg.setVisibility(View.GONE);
        } else if (infoBo.getOrderInfo().getStatus() == 2) {
            orderStatusImg.setVisibility(View.VISIBLE);
            orderStatusImg.setImageResource(R.drawable.order_suress_bigimg);
        } else if (infoBo.getOrderInfo().getStatus() == 3) {
            orderStatusImg.setVisibility(View.VISIBLE);
            orderStatusImg.setImageResource(R.drawable.order_cancle_bigimg);
        } else {
            orderStatusImg.setVisibility(View.GONE);
        }
        if (isTask) {
            orderStatusImg.setVisibility(View.GONE);
        }
        setPingLeiAdapter();
        if (this.imageBOS == null || this.imageBOS.size() == 0) {
            imageTitle.setVisibility(View.GONE);
            imageRecycle.setVisibility(View.GONE);
            blowLine.setVisibility(View.GONE);
        } else {
            imageTitle.setVisibility(View.VISIBLE);
            imageRecycle.setVisibility(View.VISIBLE);
            blowLine.setVisibility(View.VISIBLE);
        }
        setImageAdapter();
    }



    @Override
    public void onRequestError(String msg) {
        stopProgress();
        showToast(msg);
    }
}

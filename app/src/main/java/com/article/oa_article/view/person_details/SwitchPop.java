package com.article.oa_article.view.person_details;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.article.oa_article.R;
import com.article.oa_article.bean.UserOutInfo;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;

import java.util.List;
import java.util.Objects;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/6/1017:02
 * desc   :
 * version: 1.0
 */
public class SwitchPop extends PopupWindow {


    Activity activity;
    RecyclerView recyclerView;
    View dialogView;

    LinearLayout add_complan;
    List<UserOutInfo.CompanysBean> outComplans;
    int select = 0;

    DlalogAdapter adapter;

    public SwitchPop(Activity activity, List<UserOutInfo.CompanysBean> outComplans) {
        super(activity);

        this.activity = activity;
        this.outComplans = outComplans;
        dialogView = activity.getLayoutInflater().inflate(R.layout.pop_switch_complany, null);
        recyclerView = dialogView.findViewById(R.id.recycle_view);

        initView();

        this.setBackgroundDrawable(new ColorDrawable(0));
        this.setContentView(dialogView);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.anim_menu_bottombar);
        //实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(0x808080);
        //设置SelectPicPopupWindow弹出窗体的背景
        // this.setBackgroundDrawable(dw);
        this.setOnDismissListener(() -> backgroundAlpha(1f));
    }


    /**
     * 布局设置值
     */
    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(activity, R.drawable.divider_inset)));
        recyclerView.addItemDecoration(itemDecoration);

        add_complan = dialogView.findViewById(R.id.add_complan);
        add_complan.setVisibility(View.GONE);
        setAdapter();
    }


    private void setAdapter() {
        adapter = new DlalogAdapter(outComplans);
        adapter.setOnItemClickListener(R.id.item_layout, (view, position) -> {
            adapter.notifyDataSetChanged();
            dismiss();
            if (listener != null) {
                listener.selectComplan(position);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public void setSelect(int select) {
        this.select = select;
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }


    /***
     * 显示时将屏幕置为透明
     */
    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        backgroundAlpha(0.5f);
    }


    public void showPop(View view) {
        dialogView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = dialogView.getMeasuredWidth();
        int popupHeight = dialogView.getMeasuredHeight();
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        showAtLocation(view, Gravity.NO_GRAVITY, (location[0] + view.getWidth() / 2) - popupWidth / 2,
                location[1] + location[1]);
        backgroundAlpha(0.5f);
    }


    /**
     * 设置添加屏幕的背景透明度
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }


    class DlalogAdapter extends LGRecycleViewAdapter<UserOutInfo.CompanysBean> {


        public DlalogAdapter(List<UserOutInfo.CompanysBean> dataList) {
            super(dataList);
        }

        @Override
        public int getLayoutId(int viewType) {
            return R.layout.item_complany;
        }

        @Override
        public void convert(LGViewHolder holder, UserOutInfo.CompanysBean companysBean, int position) {
            holder.setText(R.id.complny_name, companysBean.getCompanyName());
            CheckBox checkBox = (CheckBox) holder.getView(R.id.checkbox);
            if (select == position) {
                checkBox.setChecked(true);
            } else {
                checkBox.setChecked(false);
            }

            holder.getView(R.id.msg_num).setVisibility(View.GONE);
        }
    }

    OnSelectComplan listener;

    public void setListener(OnSelectComplan listener) {
        this.listener = listener;
    }


    interface OnSelectComplan {

        void selectComplan(int position);

        void addComplan();
    }


}

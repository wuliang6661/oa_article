package com.article.oa_article.module.chatline;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.bean.UnitBO;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;
import com.blankj.utilcode.util.SizeUtils;

import java.util.List;

/**
 * 选择单位的弹窗
 */
public class PopUnitWindow extends PopupWindow {

    private Activity activity;

    private RecyclerView recyclerView;
    private LinearLayout recycleLayout;


    public PopUnitWindow(Activity activity, int width) {
        super(activity);

        this.activity = activity;
        View dialogView = activity.getLayoutInflater().inflate(R.layout.pop_unit_select, null);

        recyclerView = dialogView.findViewById(R.id.recycle_view);
        recycleLayout = dialogView.findViewById(R.id.recycle_layout);
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,LinearLayout.LayoutParams.WRAP_CONTENT);
//        recycleLayout.setLayoutParams(params);

        this.setWidth(SizeUtils.dp2px(100));
        this.setBackgroundDrawable(new ColorDrawable(0));
        this.setContentView(dialogView);
        //设置PopupWindow弹出窗体的宽
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.anim_menu_bottombar);
        //实例化一个ColorDrawable颜色为半透明
        // ColorDrawable dw = new ColorDrawable(0x808080);
        //设置SelectPicPopupWindow弹出窗体的背景
        // this.setBackgroundDrawable(dw);
        this.setOnDismissListener(() -> backgroundAlpha(1f));
    }


    /**
     * 设置数据
     */
    public void setData(List<UnitBO> unitBOS, int selectPosition) {
        HoldleAdapter adapter = new HoldleAdapter(unitBOS);
        adapter.setSelectPosition(selectPosition);
        adapter.setOnItemClickListener(R.id.item_unit_txt, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                if (onClickItem != null) {
                    ChatLineFragment.selectUnitPosition = position;
                    onClickItem.commit(unitBOS.get(position));
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }


    class HoldleAdapter extends LGRecycleViewAdapter<UnitBO> {


        private int selectPosition = Integer.MAX_VALUE;

        public HoldleAdapter(List<UnitBO> dataList) {
            super(dataList);
        }

        @Override
        public int getLayoutId(int viewType) {
            return R.layout.item_unit_pop;
        }

        @Override
        public void convert(LGViewHolder holder, UnitBO s, int position) {
            TextView unitText = (TextView) holder.getView(R.id.item_unit_txt);
            if (selectPosition == position) {
                unitText.setBackgroundColor(Color.parseColor("#4166BE"));
                unitText.setTextColor(Color.parseColor("#ffffff"));
            } else {
                unitText.setBackgroundColor(Color.parseColor("#000000"));
                unitText.setTextColor(Color.parseColor("#8D8C91"));
            }
            unitText.setText(s.getName());
        }

        public void setSelectPosition(int selectPosition) {
            this.selectPosition = selectPosition;
            notifyDataSetChanged();
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


    public void showPop(View view, int x, int y) {
        backgroundAlpha(0.5f);
        showAsDropDown(view, x, y);
    }

    /**
     * 设置添加屏幕的背景透明度
     */
    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

    onClickItem onClickItem;

    public void setOnClickItem(onClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public interface onClickItem {

        void commit(UnitBO unitBO);
    }
}

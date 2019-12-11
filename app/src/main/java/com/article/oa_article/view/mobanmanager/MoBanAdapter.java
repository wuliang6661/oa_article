package com.article.oa_article.view.mobanmanager;

import android.view.View;

import com.article.oa_article.R;
import com.article.oa_article.bean.TempleteBO;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;

import java.util.Collections;
import java.util.List;

public class MoBanAdapter extends LGRecycleViewAdapter<TempleteBO> implements ItemTouchHelperAdapter {

    private boolean isShowMake;

    private List<TempleteBO> data;

    public MoBanAdapter(List<TempleteBO> dataList, boolean isShowMake) {
        super(dataList);
        this.data = dataList;
        this.isShowMake = isShowMake;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_moban;
    }

    @Override
    public void convert(LGViewHolder holder, TempleteBO templeteBO, int position) {
        holder.setText(R.id.xuhao_text, position + 1 + "");
        holder.setText(R.id.moban_title, templeteBO.getName());
        holder.setText(R.id.moban_message, templeteBO.getRemarks());
        if (isShowMake) {
            holder.getView(R.id.select_button).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.select_button).setVisibility(View.GONE);
        }
    }

    //在RecycleView适配器中实现ItemTouchHelperAdapter接口 完成这两个方法
    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        //swap 交换
        Collections.swap(data, fromPosition, toPosition);
        //注意是Moved的刷新
        notifyItemMoved(fromPosition, toPosition);
//        if (listener != null) {
//            listener.onMove(fromPosition, toPosition);
//        }
    }


    @Override
    public void onItemDissmiss(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    private OnMoveTemplateListener listener;

    public void setMoveListener(OnMoveTemplateListener listener) {
        this.listener = listener;
    }


    public interface OnMoveTemplateListener {
        void onMove(int fromPosition, int toPosition);
    }

}

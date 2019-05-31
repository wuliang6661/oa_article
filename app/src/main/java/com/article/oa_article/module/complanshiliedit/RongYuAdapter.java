package com.article.oa_article.module.complanshiliedit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.article.oa_article.R;
import com.article.oa_article.bean.request.AddComplanRequest;

import java.util.List;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/3116:46
 * desc   :
 * version: 1.0
 */
public class RongYuAdapter extends RecyclerView.Adapter<RongYuAdapter.ViewHodler> {

    List<AddComplanRequest.CompanyHonorsBean> dataList;
    private Context context;

    public RongYuAdapter(Context context, List<AddComplanRequest.CompanyHonorsBean> dataList) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rongyu, null);
        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (dataList.isEmpty()) {
            return 1;
        }
        return dataList.size() + 1;
    }

    class ViewHodler extends RecyclerView.ViewHolder {

        public ViewHodler(View itemView) {
            super(itemView);
        }
    }

}

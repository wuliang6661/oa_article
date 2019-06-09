package com.article.oa_article.module.complanshiliedit;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.bean.request.AddComplanRequest;
import com.article.oa_article.module.complanziyuanedit.ImageRecycleAdapter;
import com.article.oa_article.module.create_order.ImageBO;
import com.article.oa_article.widget.EditMsgText;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

    TimePickerView pvTime;
    @SuppressLint("SimpleDateFormat")
    DateFormat format = new SimpleDateFormat("yyyy/MM/dd");

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
        ImageRecycleAdapter adapter;
        if (dataList.get(position).getHonorImage() == null) {
            adapter = new ImageRecycleAdapter(context, new ArrayList<>());
        } else {
            adapter = new ImageRecycleAdapter(context, dataList.get(position).getHonorImage());
        }
        if (!StringUtils.isEmpty(dataList.get(position).getHonorName())) {
            holder.rongyu_name.setText(dataList.get(position).getHonorName());
        }
        if (!StringUtils.isEmpty(dataList.get(position).getIssueUnit())) {
            holder.banfa_danwei.setText(dataList.get(position).getIssueUnit());
        }
        if (!StringUtils.isEmpty(dataList.get(position).getIssueDate())) {
            holder.personName.setText(dataList.get(position).getIssueDate());
        }
        adapter.setClickPosition(position);
        adapter.setDelete(false);
        adapter.setImageMakeListener((i) -> {
            if (listener != null) {
                listener.addImage(i);
            }
        });
        adapter.setDeleteImageListener((i, imageBOS) -> {
            dataList.get(i).setHonorImage(imageBOS);
            notifyDataSetChanged();
        });
        holder.image_recycle.setAdapter(adapter);
        holder.select_person.setOnClickListener(view -> initTimePicker(holder.personName));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHodler extends RecyclerView.ViewHolder {

        RecyclerView image_recycle;
        RelativeLayout select_person;
        TextView personName;
        EditMsgText banfa_danwei;    //颁发单位
        EditMsgText rongyu_name;     //资质名称

        ViewHodler(View itemView) {
            super(itemView);
            image_recycle = itemView.findViewById(R.id.image_recycle);
            select_person = itemView.findViewById(R.id.select_person);
            personName = itemView.findViewById(R.id.person_name);
            banfa_danwei = itemView.findViewById(R.id.banfa_danwei);
            rongyu_name = itemView.findViewById(R.id.rongyu_name);

            LinearLayoutManager manager = new LinearLayoutManager(context);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            image_recycle.setLayoutManager(manager);
        }
    }


    /**
     * 新增一张图片，设置进来
     */
    public void addImage(int position, ImageBO imageBO) {
        AddComplanRequest.CompanyHonorsBean qualificationsBean = dataList.get(position);
        List<ImageBO> imageBOS = qualificationsBean.getHonorImage() == null ? new ArrayList<>()
                : qualificationsBean.getHonorImage();
        imageBOS.add(imageBO);
        qualificationsBean.setHonorImage(imageBOS);
        dataList.set(position, qualificationsBean);
        notifyDataSetChanged();
    }


    /**
     * 获取第N项的图片列表
     */
    public List<ImageBO> getImageByPosition(int position) {
        if (dataList.size() > position) {
            return dataList.get(position).getHonorImage();
        }
        return new ArrayList<>();
    }


    /**
     * 继续添加
     */
    public void setDataList(List<AddComplanRequest.CompanyHonorsBean> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }


    onMakeZizhi listener;

    public void setListener(onMakeZizhi listener) {
        this.listener = listener;
    }

    interface onMakeZizhi {

        void addImage(int position);

        void deleteImage(int position, List<ImageBO> imageBOS);
    }


    /**
     * 时间选择器
     */
    @SuppressLint("SimpleDateFormat")
    private void initTimePicker(TextView orderDate) {
        Calendar startDate = Calendar.getInstance();
        pvTime = new TimePickerBuilder(context, (date, v) -> orderDate.setText(TimeUtils.date2String(date, format)))
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .setDate(startDate)
                .setLineSpacingMultiplier(1.8f)
                .build();
        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);
            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);
            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.1f);
            }
        }
        pvTime.show();
    }
}

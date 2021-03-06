package com.article.oa_article.view.main.personlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.BumenBO;
import com.article.oa_article.bean.PersonBO;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;

import java.util.List;

public class ExpandAdapter extends BaseExpandableListAdapter {

    private List<BumenBO> list;
    private Context context;
    private boolean isWaiBu;
    private boolean isSelelect = false;   //是否是选择联系人

    public ExpandAdapter(Context context, List<BumenBO> bumenBO, boolean isWaiBu) {
        this.context = context;
        this.list = bumenBO;
        this.isWaiBu = isWaiBu;
    }

    public void setIsSelect(boolean isSelect) {
        this.isSelelect = isSelect;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return list.get(i).getUser().size();
    }

    @Override
    public Object getGroup(int i) {
        return list.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return list.get(i).getUser().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        GroupHodler hodler;
        if (view == null) {
            hodler = new GroupHodler();
            view = LayoutInflater.from(context).inflate(R.layout.item_person_group, null);
            hodler.bumenName = view.findViewById(R.id.bumen_text);
            hodler.checkBox = view.findViewById(R.id.bar_check);
            view.setTag(hodler);
        } else {
            hodler = (GroupHodler) view.getTag();
        }
        if (isWaiBu) {   //外部联系人
            hodler.bumenName.setText(list.get(i).getLabel());
        } else {
            hodler.bumenName.setText(list.get(i).getDepart());
        }
        if (i == 0) {
            hodler.checkBox.setVisibility(View.GONE);
        } else {
            hodler.checkBox.setVisibility(View.VISIBLE);
        }
        hodler.checkBox.setChecked(b);
        return view;
    }


    class GroupHodler {

        TextView bumenName;
        CheckBox checkBox;
    }


    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_person_child, null);
            holder = new ChildHolder();
            holder.personName = view.findViewById(R.id.person_name);
            holder.personImg = view.findViewById(R.id.person_img);
            holder.lableText = view.findViewById(R.id.lable_text);
            holder.bumenText = view.findViewById(R.id.bumen_name);
            holder.child_line = view.findViewById(R.id.child_line);
            holder.editImage = view.findViewById(R.id.edit_img);
            view.setTag(holder);
        } else {
            holder = (ChildHolder) view.getTag();
        }
        BumenBO bumenBO = list.get(i);
        PersonBO personBO = list.get(i).getUser().get(i1);
        Glide.with(context).load(personBO.getImage()).error(R.drawable.person_img_defailt)
                .placeholder(R.drawable.person_img_defailt).into(holder.personImg);
        holder.personName.setText(personBO.getName());
        if (getChildrenCount(i) == i1 + 1) {
            holder.child_line.setVisibility(View.GONE);
        } else {
            holder.child_line.setVisibility(View.VISIBLE);
        }
        if (isWaiBu) {   //外部联系人
            if (StringUtils.isEmpty(personBO.getLabel())) {
                holder.lableText.setVisibility(View.GONE);
            } else {
                if (i == 0) {
                    holder.lableText.setVisibility(View.VISIBLE);
                    holder.lableText.setText(personBO.getLabel());
                } else {
                    holder.lableText.setVisibility(View.GONE);
                }
            }
            holder.bumenText.setText(bumenBO.getLabel());
        } else {
            holder.lableText.setVisibility(View.GONE);
            holder.bumenText.setText(personBO.getDepart());
            if (MyApplication.getCommon().getIsAdmin() == 1 && !isSelelect && i != 0) {
                holder.editImage.setVisibility(View.VISIBLE);
            } else {
                holder.editImage.setVisibility(View.GONE);
            }
        }
        holder.editImage.setOnClickListener(view1 -> {
            if (listener != null) {
                listener.editDeats(i, i1);
            }
        });
        return view;
    }


    class ChildHolder {

        ImageView personImg;
        TextView personName;
        TextView lableText;
        TextView bumenText;
        View child_line;
        ImageView editImage;

    }


    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    onEditListener listener;

    public void setListener(onEditListener listener) {
        this.listener = listener;
    }


    interface onEditListener {

        void editDeats(int groupPosition, int childPosition);
    }

}

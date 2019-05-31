package com.article.oa_article.module.complanziyuanedit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.module.create_order.ImageBO;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/3113:51
 * desc   :   上传图片的列表
 * version: 1.0
 */
public class ImageRecycleAdapter extends RecyclerView.Adapter<ImageRecycleAdapter.ViewHodler> {

    private Context context;
    private List<ImageBO> imageBOS;

    public ImageRecycleAdapter(Context context, List<ImageBO> imageBOS) {
        this.context = context;
        this.imageBOS = imageBOS;
    }


    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_add, null);
        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, int position) {
        if (position >= imageBOS.size()) {
            holder.imageView.setImageResource(R.drawable.image_update_add);
            holder.title.setVisibility(View.INVISIBLE);
            holder.deleteImage.setVisibility(View.GONE);
        } else {
            Glide.with(context).load(imageBOS.get(position).url).into(holder.imageView);
            holder.deleteImage.setVisibility(View.VISIBLE);
            holder.title.setVisibility(View.VISIBLE);
            holder.title.setText(imageBOS.get(position).name);
        }

        holder.imageView.setOnClickListener(view -> {
            if (position >= imageBOS.size()) {
                if (listener != null) {
                    listener.addImage();
                }
            }
        });
        holder.deleteImage.setOnClickListener(view -> {
            imageBOS.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        if (imageBOS.isEmpty()) {
            return 1;
        }
        return imageBOS.size() + 1;
    }


    class ViewHodler extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title;
        ImageView deleteImage;

        ViewHodler(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.image_name);
            deleteImage = itemView.findViewById(R.id.delete_image);
        }
    }


    public void addImageBOS(ImageBO imageBOS) {
        this.imageBOS.add(imageBOS);
        notifyDataSetChanged();
    }


    public List<ImageBO> getImageBOS() {
        return imageBOS;
    }

    private onImageMakeListener listener;

    public void setImageMakeListener(onImageMakeListener listener) {
        this.listener = listener;
    }


    interface onImageMakeListener {

        void addImage();
    }
}

package com.article.oa_article.view.optionsfankui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.article.oa_article.R;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/2015:57
 * desc   :
 * version: 1.0
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHodler> {

    private List<String> imageBOS;
    private Context context;

    ImageAdapter(Context context, List<String> imageBOS) {
        this.context = context;
        this.imageBOS = imageBOS;
    }


    @NonNull
    @Override
    public ImageAdapter.ViewHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fankui, null);
        return new ImageAdapter.ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ViewHodler viewHodler, int i) {
        if (imageBOS.size() == 0 || i == imageBOS.size()) {
            viewHodler.imageView.setImageResource(R.drawable.image_update_add);
        } else {
            Glide.with(context).load(imageBOS.get(i)).into(viewHodler.imageView);
        }
        viewHodler.imageView.setOnClickListener(v -> {
            if (imageBOS.size() == 0 || i == imageBOS.size()) {
                if (listener != null) {
                    listener.addImage();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (imageBOS.size() == 0) {
            return 1;
        }
        if (imageBOS.size() < 6) {
            return imageBOS.size() + 1;
        }
        return 9;
    }

    class ViewHodler extends RecyclerView.ViewHolder {


        RoundedImageView imageView;


        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }

    private onAddImageAdapterListener listener;

    void setListener(onAddImageAdapterListener listener) {
        this.listener = listener;
    }


    public interface onAddImageAdapterListener {

        void addImage();

    }

}

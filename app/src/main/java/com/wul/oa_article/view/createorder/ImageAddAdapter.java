package com.wul.oa_article.view.createorder;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.wul.oa_article.R;

import java.util.List;

public class ImageAddAdapter extends RecyclerView.Adapter<ImageAddAdapter.ViewHodler> {

    private List<ImageBO> imageBOS;
    private Context context;

    public ImageAddAdapter(Context context, List<ImageBO> imageBOS) {
        this.context = context;
        this.imageBOS = imageBOS;
    }


    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_add_image, null);
        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler viewHodler, int i) {
        if (imageBOS.size() == 0 || i == imageBOS.size()) {
            viewHodler.deleteImg.setVisibility(View.GONE);
            viewHodler.imageName.setText("");
            viewHodler.imageView.setImageResource(R.drawable.add_img);
        } else {
            viewHodler.deleteImg.setVisibility(View.VISIBLE);
            viewHodler.imageName.setText(imageBOS.get(i).imageName);
            viewHodler.imageView.setImageBitmap(BitmapFactory.decodeFile(imageBOS.get(i).path));
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
        if (imageBOS.size() < 9) {
            return imageBOS.size() + 1;
        }
        return 9;
    }

    class ViewHodler extends RecyclerView.ViewHolder {


        RoundedImageView imageView;
        ImageView deleteImg;
        TextView imageName;


        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            deleteImg = itemView.findViewById(R.id.delete_img);
            imageName = itemView.findViewById(R.id.edit_image_name);
        }
    }

    private onAddImageAdapterListener listener;

    public void setListener(onAddImageAdapterListener listener) {
        this.listener = listener;
    }


    public interface onAddImageAdapterListener {

        void addImage();

        void deleteImage(int position, ImageBO imageBO);

    }

}

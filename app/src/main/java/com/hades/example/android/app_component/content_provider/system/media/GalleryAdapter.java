package com.hades.example.android.app_component.content_provider.system.media;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hades.example.android.R;
import com.hades.example.android.lib.utils.WindowUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {
    private Context context;
    public List<GalleryItem> list;
    private IItemClickListener itemClickListener;

    public GalleryAdapter(Context context, List<GalleryItem> list) {
        this.context = context;
        this.list = list;
    }

    public void setItemClickListener(IItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GalleryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.content_provider_gallery_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final GalleryViewHolder holder, int position) {
        holder.name.setText(list.get(position).name);
        int screenWidth = WindowUtils.width(context);
        holder.image.setLayoutParams(new LinearLayout.LayoutParams(screenWidth / 5, screenWidth / 5));
        loadImage(holder.image, position, screenWidth / 5, screenWidth / 5);
        holder.itemViewRoot.setOnClickListener(v -> {
            if (null != itemClickListener) {
                itemClickListener.onItemClick(position);
            }
        });
    }

    private void loadImage(ImageView imageView, int position, int width, int height) {
        GalleryItem bean = list.get(position);
        if (null == bean) {
            return;
        }
        RequestCreator requestCreator;
        if (null != bean.uri) {
            requestCreator = Picasso.with(context).load(bean.uri);
        } else {
            requestCreator = Picasso.with(context).load("file://" + bean.path);
        }
        requestCreator.resize(width, height).placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher).into(imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class GalleryViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;
        ProgressBar progressBar;
        View itemViewRoot;

        public GalleryViewHolder(View itemView) {
            super(itemView);
            itemViewRoot = itemView;
            name = (TextView) itemView.findViewById(R.id.galleryItemName);
            image = (ImageView) itemView.findViewById(R.id.galleryItemImage);
        }
    }
}
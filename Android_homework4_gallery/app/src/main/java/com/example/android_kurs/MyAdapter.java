package com.example.android_kurs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int LOAD_MORE = 111;
    public static final int DEFAULT_ITEM = 112;

    private ArrayList<GalleryItem> galleryItems = new ArrayList<>();
    private Context context;
    private View.OnClickListener onClickListener;

    MyAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<GalleryItem> getGalleryItems() {
        return galleryItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LOAD_MORE) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.add_button, parent, false);
            return new AddButtonViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_data, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            GalleryItem ld = galleryItems.get(position);
            Picasso.get()
                    .load(ld.getImageUrl())
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .into(((ViewHolder) holder).imageView);
        } else {
            ((AddButtonViewHolder)holder).button.setOnClickListener(onClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return galleryItems.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == galleryItems.size()) {
            return LOAD_MORE;
        } else {
            return DEFAULT_ITEM;
        }
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void addGalleryItems(ArrayList<GalleryItem> galleryItems) {
        this.galleryItems.addAll(galleryItems);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }

    class AddButtonViewHolder extends RecyclerView.ViewHolder {
        Button button;

        public AddButtonViewHolder(View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.btnId);
        }
    }
}


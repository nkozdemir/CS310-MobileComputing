package com.example.cs310news;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private Context ctx;
    private List<NewsObj> data;

    public NewsAdapter(Context ctx, List<NewsObj> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @NonNull
    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(ctx).inflate(R.layout.news_row_layout, parent, false);
        NewsViewHolder holder = new NewsViewHolder(root);
        holder.setIsRecyclable(false);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsViewHolder holder, int position) {
        holder.dateTextView.setText(data.get(holder.getAdapterPosition()).getDate());
        holder.titleTextView.setText(data.get(holder.getAdapterPosition()).getTitle());
        NewsApp app = (NewsApp) ((AppCompatActivity)ctx).getApplication();
        holder.downloadImage(app.srv, data.get(holder.getAdapterPosition()).getImagePath());

        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ctx, NewsDetail.class);
                i.putExtra("id", data.get(holder.getAdapterPosition()).getId());
                ctx.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView dateTextView;
        TextView titleTextView;
        ConstraintLayout row;
        boolean imageDownloaded;

        Handler imageHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                Bitmap img = (Bitmap)msg.obj;
                imageView.setImageBitmap(img);
                imageDownloaded = true;
                return true;
            }
        });

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.newsImg);
            dateTextView = itemView.findViewById(R.id.dateTxt);
            titleTextView = itemView.findViewById(R.id.titleTxt);
            row = itemView.findViewById(R.id.row_list);

        }

        public void downloadImage(ExecutorService srv, String path){
            if (!imageDownloaded){
                NewsRepo repo = new NewsRepo();
                repo.downloadImage(srv, imageHandler, path);
            }

        }
    }
}

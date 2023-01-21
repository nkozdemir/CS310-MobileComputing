package com.example.cs310news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private Context ctx;
    private List<CommentObj> data;

    public CommentAdapter(Context ctx, List<CommentObj> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @NonNull
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(ctx).inflate(R.layout.comment_row_layout, parent, false);
        CommentViewHolder holder = new CommentViewHolder(root);
        holder.setIsRecyclable(false);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHolder holder, int position) {
        holder.nameTxt.setText(data.get(holder.getAdapterPosition()).getName());
        holder.commentTxt.setText(data.get(holder.getAdapterPosition()).getText());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {

        TextView nameTxt;
        TextView commentTxt;
        ImageView imageView;
        ConstraintLayout row;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.nameTxt);
            commentTxt = itemView.findViewById(R.id.comTxt);
            imageView = itemView.findViewById(R.id.usrImg);
            row = itemView.findViewById(R.id.com_row);

        }
    }
}

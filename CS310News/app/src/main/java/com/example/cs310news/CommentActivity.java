package com.example.cs310news;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentActivity extends AppCompatActivity {

    private int news_id;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            List<CommentObj> data = (List<CommentObj>)message.obj;
            CommentAdapter adp = new CommentAdapter(getApplication(), data);
            recyclerView.setAdapter(adp);
            recyclerView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);

            return true;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        int id = getIntent().getIntExtra("id",1);
        news_id = id;

        recyclerView = findViewById(R.id.recViewCom);
        progressBar = findViewById(R.id.prgBarCom);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);

        NewsRepo repo = new NewsRepo();
        repo.getCommentsByNewsId(((NewsApp)getApplication()).srv, dataHandler, id);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id = item.getItemId();

        if (item_id == android.R.id.home) {
            finish();
            return true;
        }
        else if (item_id == R.id.post_comment) {
            Intent i = new Intent(CommentActivity.this, PostCommentActivity.class);
            i.putExtra("id", news_id);
            (CommentActivity.this).startActivity(i);
            return true;
        }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.postcomment_menu, menu);
        return true;
    }
}

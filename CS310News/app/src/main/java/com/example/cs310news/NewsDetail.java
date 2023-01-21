package com.example.cs310news;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NewsDetail extends AppCompatActivity {

    //private Context ctx;
    private int news_id;

    ImageView imageView;
    TextView titleView;
    TextView dateView;
    TextView textView;

    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            NewsObj obj = (NewsObj)message.obj;
            titleView.setText(obj.getTitle());
            dateView.setText(obj.getDate());
            textView.setText(obj.getText());

            NewsRepo repo = new NewsRepo();
            repo.downloadImage(((NewsApp)getApplication()).srv, imgHandler, obj.getImagePath());

            return true;
        }
    });

    Handler imgHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            Bitmap img = (Bitmap)message.obj;
            imageView.setImageBitmap(img);

            return true;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        int id = getIntent().getIntExtra("id", 1);
        news_id = id;

        imageView = findViewById(R.id.imgDetView);
        titleView = findViewById(R.id.titleDetTxt);
        dateView = findViewById(R.id.dateDetTxt);
        textView = findViewById(R.id.textDetTxt);

        NewsRepo repo = new NewsRepo();
        repo.getNewsById(((NewsApp)getApplication()).srv, dataHandler, id);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if(itemId == android.R.id.home) {
            finish();
            return true;
        }
        else if (itemId == R.id.show_comments) {
            Intent i = new Intent(NewsDetail.this, CommentActivity.class);
            i.putExtra("id", news_id);
            (NewsDetail.this).startActivity(i);
            return true;
        }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.showcomments_menu, menu);
        return true;
    }
}

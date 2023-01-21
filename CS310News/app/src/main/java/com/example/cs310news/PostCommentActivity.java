package com.example.cs310news;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PostCommentActivity extends AppCompatActivity {

    private int news_id;
    ProgressBar prgBar;
    EditText nameTxt;
    EditText comTxt;
    Button postBtn;
    TextView infoTxt;

    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            int result = (int)message.obj;
            prgBar.setVisibility(View.INVISIBLE);
            infoTxt.setVisibility(View.INVISIBLE);

            if (result == 200) {
                finish();
                Intent i = new Intent(PostCommentActivity.this, CommentActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("id", news_id);
                (PostCommentActivity.this).startActivity(i);
                return true;
            }
            else {
                infoTxt.setText("post operation failed");
                infoTxt.setVisibility(View.VISIBLE);
            }

            return false;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_postcomment);

        int id = getIntent().getIntExtra("id", 1);
        news_id = id;

        prgBar = findViewById(R.id.postPrgBar);
        nameTxt = findViewById(R.id.postNameTxt);
        comTxt = findViewById(R.id.postComTxt);
        postBtn = findViewById(R.id.postBtn);
        infoTxt = findViewById(R.id.infoTxt);

        infoTxt.setVisibility(View.INVISIBLE);
        prgBar.setVisibility(View.INVISIBLE);

        postBtn.setOnClickListener(view -> {
            if (nameTxt.getText().toString().isEmpty() || comTxt.getText().toString().isEmpty()) {
                infoTxt.setText("missing field");
                infoTxt.setVisibility(View.VISIBLE);
            }
            else {
                prgBar.setVisibility(View.VISIBLE);
                NewsRepo repo = new NewsRepo();
                repo.postComment(((NewsApp)getApplication()).srv, dataHandler, news_id, nameTxt.getText().toString(), comTxt.getText().toString());
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id = item.getItemId();

        if (item_id == android.R.id.home) {
            finish();
            return true;
        }

        return false;
    }
}

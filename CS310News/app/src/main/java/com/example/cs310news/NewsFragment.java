package com.example.cs310news;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

public class NewsFragment extends Fragment {

    private NewsCategory newsCategory;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    public NewsFragment() {

    }

    public NewsFragment(NewsCategory newsCategory) {
        this.newsCategory = newsCategory;
    }

    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            List<NewsObj> data = (List<NewsObj>)message.obj;
            NewsAdapter adp = new NewsAdapter(getActivity(), data);
            recyclerView.setAdapter(adp);
            recyclerView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);

            return true;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView = v.findViewById(R.id.recView);
        progressBar = v.findViewById(R.id.prgBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);

        NewsRepo repo = new NewsRepo();
        repo.getNewsByCategoryId(((NewsApp)getActivity().getApplication()).srv, dataHandler, newsCategory.getId());

        return v;
    }

}
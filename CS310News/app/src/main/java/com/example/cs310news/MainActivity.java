package com.example.cs310news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    List<NewsCategory> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_custom_round);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewpager);

        NewsCategory c1 = new NewsCategory(1,"Economics");
        NewsCategory c2 = new NewsCategory(2,"Sports");
        NewsCategory c3 = new NewsCategory(3,"Politics");

        categories = new ArrayList<>();
        categories.add(c1);
        categories.add(c2);
        categories.add(c3);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(categories.get(position).getName())
        ).attach();
    }

    class ViewPagerAdapter extends FragmentStateAdapter {

        public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return new NewsFragment(categories.get(position));
        }

        @Override
        public int getItemCount() {
            return categories.size();
        }

    }
}
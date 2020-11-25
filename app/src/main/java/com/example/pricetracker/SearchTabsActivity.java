package com.example.pricetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class SearchTabsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tabs);
        initViewPager();
    }

    private void initViewPager() {
        ViewPager viewPager = findViewById(R.id.search_view_pager);
        TabLayout tabLayout =findViewById(R.id.search_tab_layout);

    }
}
package com.example.pricetracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Switch;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class SearchTabsActivity extends AppCompatActivity {
    private ArrayList<SiteToggler> siteTogglers;
    private String query;
    WebViewFragment webViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tabs);
        getIntentMethod();
        initViewPager();
    }

    private void getIntentMethod() {
        this.siteTogglers = (ArrayList<SiteToggler>) getIntent().getSerializableExtra("site_togglers_array");
        this.query = getIntent().getStringExtra("query");
    }

    private void initViewPager() {
        CustomViewPager viewPager = findViewById(R.id.search_view_pager);
        viewPager.setPagingEnabled(false);
        TabLayout tabLayout = findViewById(R.id.search_tab_layout);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        for (SiteToggler siteToggler : siteTogglers) {
            if (siteToggler.isChecked()) {
                Bundle bundle = new Bundle();
                bundle.putString("url", siteToggler.getSiteName());
                bundle.putString("query", query);
                webViewFragment = new WebViewFragment();
                webViewFragment.setArguments(bundle);
                viewPagerAdapter.addFragment(webViewFragment, siteToggler.getSiteName());
            }
        }
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public static class ViewPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;
        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
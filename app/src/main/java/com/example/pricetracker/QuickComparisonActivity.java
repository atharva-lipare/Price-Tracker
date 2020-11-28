package com.example.pricetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class QuickComparisonActivity extends AppCompatActivity {
    private ArrayList<SiteToggler> siteTogglers;
    private String productName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_comparison);
        getIntentMethod();
    }

    private void getIntentMethod() {
        this.siteTogglers = (ArrayList<SiteToggler>) getIntent().getSerializableExtra("site_togglers_array");
        this.productName = getIntent().getStringExtra("productName");
        Log.e("compare_testing", productName);
    }
}
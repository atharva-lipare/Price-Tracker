package com.example.pricetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

public class QuickComparisonActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ItemViewAdapter itemViewAdapter;
    private ArrayList<SiteToggler> siteTogglers;
    private ArrayList<Product> products;
    private String query;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_comparison);
        context = this;
        getIntentMethod();
        doItCompare doItCompare = new doItCompare();
        doItCompare.execute();
    }

    private void getIntentMethod() {
        this.siteTogglers = (ArrayList<SiteToggler>) getIntent().getSerializableExtra("site_togglers_array");
        this.query = getIntent().getStringExtra("query");
        Log.e("compare_testing", query);
        products = new ArrayList<>();
    }

    public class doItCompare extends AsyncTask<Void, Void, Void> {

        public MyScraper myScraper;
        public Product product;
        ArrayList<Product> productsTemp;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Log.e("compare_testing", query);
                myScraper = new MyScraper(query, "Amazon");
                productsTemp = myScraper.scrapeAmazonProducts();
                products.addAll(productsTemp);
                // TODO: add for other sites

            }catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            for (Product product : products) {
                Log.e("compare_testing", product.getName());
                Log.e("compare_testing", String.valueOf(product.getPrice()));
                Log.e("compare_testing", product.getUrl());
                Log.e("compare_testing", product.getImageUrl());
                Log.e("compare_testing", product.getRating());
            }
            recyclerView = findViewById(R.id.recyclerViewQuickComparison);
            layoutManager = new GridLayoutManager(context, 2);
            recyclerView.setLayoutManager(layoutManager);
            itemViewAdapter = new ItemViewAdapter(context, products);
            recyclerView.setAdapter(itemViewAdapter);
            recyclerView.setHasFixedSize(true);
        }
    }
}
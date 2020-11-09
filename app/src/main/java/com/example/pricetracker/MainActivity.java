package com.example.pricetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SearchView searchView;
    Button scrapeButton;
    TextView amznView, fkartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchView = (SearchView)findViewById(R.id.searchView);
        scrapeButton = (Button)findViewById(R.id.button);
        amznView = (TextView)findViewById(R.id.textView2);
        fkartView = (TextView)findViewById(R.id.textView3);
        scrapeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("success", searchView.getQuery().toString());
                new doIt().execute();
            }
        });
    }

    public class doIt extends AsyncTask<Void, Void, Void> {
        ArrayList<String> as1Names, as1Prices, fs1Names, fs1Prices;
        AmazonScraper as1;
        FlipkartScraper fs1;
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                as1 = new AmazonScraper(searchView.getQuery().toString());
                fs1 = new FlipkartScraper(searchView.getQuery().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            as1Names = as1.getNames();
            as1Prices = as1.getPrices();
            amznView.setText("Amazon\n");
            for (int i = 0; i < Math.min(as1Names.size(), 3); i++) {
                amznView.append(as1Names.get(i) + ": " + as1Prices.get(i) + "\n");
            }
            fs1Names = fs1.getNames();
            fs1Prices = fs1.getPrices();
            fkartView.setText("Flipkart\n");
            for (int i = 0; i < Math.min(fs1Names.size(), 3); i++) {
                fkartView.append(fs1Names.get(i) + ": " + fs1Prices.get(i) + "\n");
            }
        }
    }
}
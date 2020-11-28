package com.example.pricetracker;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
/*
public class FlipkartScraper implements MyScraper {
    public String productName, searchQuery;
    public ArrayList<String> names, prices, recommendations;
    private Document page;
    FlipkartScraper(String productName) throws IOException {
        this.productName = productName;
        searchQuery = "https://www.flipkart.com/search?q=" + productName +
                "&otracker=search&otracker1=search&marketplace=FLIPKART&as-show=on&as=off";
        names = new ArrayList<>();
        prices = new ArrayList<>();
        recommendations = new ArrayList<>();
        page = Jsoup.connect(searchQuery).get();
        setNames();
        setPrices();
    }
    @Override
    public ArrayList<String> getNames() {
        return names;
    }

    @Override
    public ArrayList<String> getPrices() {
        return prices;
    }

    @Override
    public ArrayList<String> getRecommendations() {
        return null;
    }

    @Override
    public void setNames() {
        Elements prodNames = page.select(".col-7-12 > ._3wU53n");
        for (Element e : prodNames) {
            names.add(e.text());
        }
    }

    @Override
    public void setPrices() {
        Elements prodPrices = page.select("._1uv9Cb > ._1vC4OE");
        for (Element e : prodPrices) {
            prices.add(e.text());
        }
    }

    @Override
    public void setRecommendations() {

    }
}
*/
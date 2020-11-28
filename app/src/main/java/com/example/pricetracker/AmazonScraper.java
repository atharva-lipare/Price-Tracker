package com.example.pricetracker;
import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/*
public class AmazonScraper implements MyScraper {

    public String productName, searchQuery;
    public ArrayList<String> names, prices, recommendations;
    private Document page;
    AmazonScraper(String productName) throws IOException {
        this.productName = productName;
        searchQuery = "https://www.amazon.in/s?k=" + productName + "&ref=nb_sb_noss_1";
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
        Elements prodNames = page.select(".a-size-medium");
        for (Element e : prodNames) {
            names.add(e.text());
        }
    }

    @Override
    public void setPrices() {
        Elements prodPrices = page.select(".a-price-whole");
        for (Element e : prodPrices) {
            prices.add(e.text());
        }
    }

    @Override
    public void setRecommendations() {

    }
}
*/
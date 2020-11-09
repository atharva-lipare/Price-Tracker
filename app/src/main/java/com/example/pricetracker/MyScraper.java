package com.example.pricetracker;

import java.util.ArrayList;

public interface MyScraper {
    ArrayList<String> getNames();
    ArrayList<String> getPrices();
    ArrayList<String> getRecommendations();
    void setNames();
    void setPrices();
    void setRecommendations();
}

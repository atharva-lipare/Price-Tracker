package com.example.pricetracker;

import android.graphics.drawable.Drawable;

public class SiteToggler {
    private String siteName;
    private String siteUrl;
    private String searchQuery;
    private boolean checked;
    private int drawable;

    public SiteToggler(String siteName, String siteUrl, String searchQuery, boolean checked, int drawable) {
        this.siteName = siteName;
        this.siteUrl = siteUrl;
        this.searchQuery = searchQuery;
        this.checked = checked;
        this.drawable = drawable;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public boolean isChecked() {
        return checked;
    }

    public int getDrawable() {
        return drawable;
    }
}

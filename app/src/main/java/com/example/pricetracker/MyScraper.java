package com.example.pricetracker;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class MyScraper {
    private static final String ONLY_DIGITS_REGEX = "[^\\d]";
    private static final String ONLY_DIGITS_AND_DECIMAL_REGEX = "[^\\d.]";
    private String query;
    private String site;
    private Product product;
    private Document document;
    private String junk;


    public MyScraper(String query, String site) {
        this.query = query;
        this.site = site;
        product = new Product(query, site);
    }

    public Product getProduct() {
        return product;
    }

    public void scrapeProductInfo() throws IOException {
        switch (site) {
            case "Amazon":
                scrapeAmazonProductInfo();
                break;
            case "Flipkart":
                scrapeFlipkartProductInfo();
                break;
            case "Bigbasket":
                scrapeBigbasketProductInfo();
                break;
            case "JioMart":
                scrapeJioMartProductInfo();
                break;
            case "Myntra":
                //scrapeMyntraProductInfo();   //TODO JSON based site so no jsoup
                break;
            case "Paytm Mall":
                scrapePaytmMallProductInfo();
                break;
            case "Snapdeal":
                scrapeSnapdealProductInfo();
                break;
        }
    }

    private void scrapeSnapdealProductInfo() throws IOException {
        document = Jsoup.connect(query)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:84.0) Gecko/20100101 Firefox/84.0")
                .get();

        Element snapdealName = document.selectFirst(".pdp-e-i-head");
        if (snapdealName != null) {
            product.setName(snapdealName.text());
        }
        else {
            product.setName("NA");
        }

        Element snapdealPrice = document.selectFirst("span.payBlkBig");
        if (snapdealPrice != null) {
            product.setPrice(Double.valueOf(snapdealPrice.text()));
        }
        else {
            product.setPrice(0.0);
        }

        Element snapdealRating = document.selectFirst("span.avrg-rating");
        if (snapdealRating != null) {
            junk = snapdealRating.text();
            product.setRating(junk.replaceAll(ONLY_DIGITS_AND_DECIMAL_REGEX, ""));
        }
        else {
            product.setRating("NA");
        }

        Element snapdealNumRating = document.selectFirst("span.total-rating");
        if (snapdealNumRating != null) {
            junk = snapdealNumRating.text();
            product.setNumberOfRatings(Integer.parseInt(junk.substring(0, junk.indexOf(' ')).replaceAll(ONLY_DIGITS_REGEX, "")));
        }
        else {
            product.setNumberOfRatings(0);
        }

        Log.e("jsoup_testing", product.getName());
        Log.e("jsoup_testing", String.valueOf(product.getPrice()));
        Log.e("jsoup_testing", String.valueOf(product.getRating()));
        Log.e("jsoup_testing", String.valueOf(product.getNumberOfRatings()));
    }

    private void scrapePaytmMallProductInfo() throws IOException {
        document = Jsoup.connect(query)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:84.0) Gecko/20100101 Firefox/84.0")
                .get();

        Element paytmName = document.selectFirst("h1.NZJI");
        if (paytmName != null) {
            product.setName(paytmName.text());
        }
        else {
            product.setName("NA");
        }

        Element paytmPrice = document.selectFirst("span._1V3w");
        if (paytmPrice != null) {
            product.setPrice(Double.valueOf(paytmPrice.text()));
        }
        else {
            product.setPrice(0.0);
        }

        Element paytmRating = document.selectFirst("div._2dWu");
        if (paytmRating != null) {
            junk = paytmRating.text();
            product.setRating(junk.substring(0, junk.indexOf('/')));
        }
        else {
            product.setRating("NA");
        }

        Element paytmNumRating = document.selectFirst("div._38Tb");
        if (paytmNumRating != null) {
            junk = paytmNumRating.text();
            product.setNumberOfRatings(Integer.parseInt(junk.substring(0, junk.indexOf(' ')).replaceAll(ONLY_DIGITS_REGEX, "")));
        }
        else {
            product.setNumberOfRatings(0);
        }

        Log.e("jsoup_testing", product.getName());
        Log.e("jsoup_testing", String.valueOf(product.getPrice()));
        Log.e("jsoup_testing", String.valueOf(product.getRating()));
        Log.e("jsoup_testing", String.valueOf(product.getNumberOfRatings()));
    }

    private void scrapeMyntraProductInfo() throws IOException {
        document = Jsoup.connect(query)
                .timeout(10*1000)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:84.0) Gecko/20100101 Firefox/84.0")
                .get();

        Log.e("jsoup_testing", "myntra loaded");

        Element myntraName = document.selectFirst(".pdp-name");
        if (myntraName != null) {
            product.setName(myntraName.text());
        }
        else {
            product.setName("NA");
        }

        Element myntraPrice = document.selectFirst(".pdp-price");
        if (myntraPrice != null) {
            product.setPrice(Double.valueOf(myntraPrice.text().replaceAll(ONLY_DIGITS_AND_DECIMAL_REGEX, "")));
        }
        else {
            product.setPrice(0.0);
        }

        Element myntraRating = document.selectFirst(".index-averageRating");
        if (myntraRating != null) {
            product.setRating(myntraRating.text());
        }
        else {
            product.setRating("NA");
        }

        Element myntraNumRating = document.selectFirst(".index-countDesc");
        if (myntraNumRating != null) {
            product.setNumberOfRatings(Integer.parseInt(myntraNumRating.text().replaceAll(ONLY_DIGITS_REGEX, "")));
        }
        else {
            product.setNumberOfRatings(0);
        }

        Log.e("jsoup_testing", product.getName());
        Log.e("jsoup_testing", String.valueOf(product.getPrice()));
        Log.e("jsoup_testing", String.valueOf(product.getRating()));
        Log.e("jsoup_testing", String.valueOf(product.getNumberOfRatings()));
    }

    private void scrapeJioMartProductInfo() throws IOException {
        document = Jsoup.connect(query)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:84.0) Gecko/20100101 Firefox/84.0")
                .get();

        Element jioMartName = document.selectFirst(".title-section");
        if (jioMartName != null) {
            product.setName(jioMartName.text());
        }
        else {
            product.setName("NA");
        }

        Element jioMartPrice = document.selectFirst(".final-price");
        if (jioMartPrice != null) {
            product.setPrice(Double.valueOf(jioMartPrice.text().replaceAll(ONLY_DIGITS_AND_DECIMAL_REGEX, "")));
        }
        else {
            product.setPrice(0.0);
        }

        Element jioMartRating = document.selectFirst(".rating-content");
        if (jioMartRating != null) {
            junk = jioMartRating.text();
            product.setRating(junk.substring(0, junk.indexOf('/')));
        }
        else {
            product.setRating("NA");
        }

        Element jioMartNumReviews = document.selectFirst(".total-rating-count");
        if (jioMartNumReviews != null) {
            product.setNumberOfRatings(Integer.parseInt(jioMartNumReviews.text().replaceAll(ONLY_DIGITS_REGEX, "")));
        }
        else {
            product.setNumberOfRatings(0);
        }

        Log.e("jsoup_testing", product.getName());
        Log.e("jsoup_testing", String.valueOf(product.getPrice()));
        Log.e("jsoup_testing", String.valueOf(product.getRating()));
        Log.e("jsoup_testing", String.valueOf(product.getNumberOfRatings()));
    }

    private void scrapeBigbasketProductInfo() throws IOException {
        document = Jsoup.connect(query)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:84.0) Gecko/20100101 Firefox/84.0")
                .get();

        Element bigName = document.selectFirst(".GrE04");
        if (bigName != null) {
            product.setName(bigName.text());
        }
        else {
            product.setName("NA");
        }

        Element bigbasketProductPrice = document.selectFirst("td.IyLvo");
        if (bigbasketProductPrice != null) {
            product.setPrice(Double.valueOf(bigbasketProductPrice.text().replaceAll(ONLY_DIGITS_AND_DECIMAL_REGEX, "")));
        }
        else {
            product.setPrice(0.0);
        }

        Element bigbasketProductRating = document.selectFirst("._2Ze34");
        if (bigbasketProductRating != null) {
            product.setRating(bigbasketProductRating.text());
        }
        else {
            product.setRating("NA");
        }

        Element bigbasketNumRatings = document.selectFirst(".gmwyk");
        if (bigbasketNumRatings != null) {
            junk = bigbasketNumRatings.text();
            product.setNumberOfRatings(Integer.parseInt(junk.substring(0, junk.indexOf(' ')).replaceAll(ONLY_DIGITS_REGEX, "")));
        }
        else {
            product.setNumberOfRatings(0);
        }

        Log.e("jsoup_testing", product.getName());
        Log.e("jsoup_testing", String.valueOf(product.getPrice()));
        Log.e("jsoup_testing", String.valueOf(product.getRating()));
        Log.e("jsoup_testing", String.valueOf(product.getNumberOfRatings()));
    }

    private void scrapeFlipkartProductInfo() throws IOException {
        document = Jsoup.connect(query)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:84.0) Gecko/20100101 Firefox/84.0")
                .get();

        Element flipkartProductName = document.selectFirst(".B_NuCI");
        if (flipkartProductName != null) {
            product.setName(flipkartProductName.text());
        }
        else {
            product.setName("NA");
        }

        Element flipkartProductPrice = document.selectFirst("._30jeq3._16Jk6d");
        if (flipkartProductPrice != null) {
            product.setPrice(Double.valueOf(flipkartProductPrice.text().replaceAll(ONLY_DIGITS_AND_DECIMAL_REGEX, "")));
        }
        else {
            product.setPrice(0.0);
        }

        if (query.contains("marketplace=GROCERY")) {    // grocery items don't have ratings
            product.setRating("NA");
            product.setNumberOfRatings(0);
        }
        else {
            Element flipkartRatingElement = document.selectFirst("._3LWZlK");
            if (flipkartRatingElement != null) {
                product.setRating(document.selectFirst("._3LWZlK").text());
            }
            else {
                product.setRating("NA");
            }
            Element flipkartNumRatingElement = document.selectFirst("._2_R_DZ");
            if (flipkartNumRatingElement != null) {
                junk = document.selectFirst("._2_R_DZ").text(); // in the form of 1234 ratings and 12 reviews, so return only 1234
                product.setNumberOfRatings(Integer.parseInt(junk.substring(0, junk.indexOf(' ')).replaceAll(ONLY_DIGITS_REGEX, "")));
            }
            else {
                product.setNumberOfRatings(0);
            }
        }

        Log.e("jsoup_testing", product.getName());
        Log.e("jsoup_testing", String.valueOf(product.getPrice()));
        Log.e("jsoup_testing", String.valueOf(product.getRating()));
        Log.e("jsoup_testing", String.valueOf(product.getNumberOfRatings()));
    }

    private void scrapeAmazonProductInfo() throws IOException {
        document = Jsoup.connect(query)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:84.0) Gecko/20100101 Firefox/84.0")
                .get();

        Element amazonProductName = document.selectFirst("#productTitle");
        if (amazonProductName != null) {
            product.setName(amazonProductName.text());
        }
        else {
            product.setName("NA");
        }

        Element price_1 = document.selectFirst("#priceblock_ourprice");
        if (price_1 != null) {
            product.setPrice(Double.valueOf(price_1.text().replaceAll(ONLY_DIGITS_AND_DECIMAL_REGEX, "")));
        }
        else product.setPrice(0.0);

        Element amazonRating = document.selectFirst("span.a-size-medium.a-color-base");
        if (amazonRating != null) {
            junk = amazonRating.text();
            product.setRating(junk.substring(0, junk.indexOf(' ')));
        }

        Element amazonNumReviews = document.selectFirst(".a-row.a-spacing-medium.averageStarRatingNumerical");
        if (amazonNumReviews != null) {
            junk = amazonNumReviews.text();
            product.setNumberOfRatings(Integer.parseInt(junk.substring(0, junk.indexOf(' ')).replaceAll(ONLY_DIGITS_REGEX, "")));
        }
        else {
            product.setNumberOfRatings(0);
        }

        Log.e("jsoup_testing", product.getName());
        Log.e("jsoup_testing", String.valueOf(product.getPrice()));
        Log.e("jsoup_testing", String.valueOf(product.getRating()));
        Log.e("jsoup_testing", String.valueOf(product.getNumberOfRatings()));
    }
}

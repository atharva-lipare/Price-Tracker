package com.example.pricetracker;

import android.Manifest;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MyScraper {
    private static final String ONLY_DIGITS_REGEX = "[^\\d]";
    private static final String ONLY_DIGITS_AND_DECIMAL_REGEX = "[^\\d.]";
    private static final int MAX_ELE_TO_SHOW = 4;
    private String query;
    private String site;
    private Product product;
    private Document document;
    private String junk;

    public MyScraper(String query, String site) {
        this.query = query;
        this.site = site;
    }

    public Product getProduct() {
        return product;
    }

    public void scrapeProductInfo() throws IOException {
        product = new Product(query, site);
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

        Element elementImage = document.selectFirst("img.tileImg");
        if (elementImage != null) {
            junk = elementImage.toString();
            product.setImageUrl(junk.substring(junk.indexOf("https://"), junk.indexOf(" class")-1));
        }
        else {
            product.setImageUrl("NA");
        }

        Log.e("jsoup_testing", product.getName());
        Log.e("jsoup_testing", String.valueOf(product.getPrice()));
        Log.e("jsoup_testing", String.valueOf(product.getRating()));
        Log.e("jsoup_testing", String.valueOf(product.getNumberOfRatings()));
        Log.e("jsoup_testing", product.getImageUrl());
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

        Element elementImage = document.selectFirst("div._3_E6._3H7I");
        if (elementImage != null) {
            junk = elementImage.toString();
            product.setImageUrl(junk.substring(junk.indexOf("https://"), junk.indexOf(" role")-1));
        }
        else {
            product.setImageUrl("NA");
        }

        Log.e("jsoup_testing", product.getName());
        Log.e("jsoup_testing", String.valueOf(product.getPrice()));
        Log.e("jsoup_testing", String.valueOf(product.getRating()));
        Log.e("jsoup_testing", String.valueOf(product.getNumberOfRatings()));
        Log.e("jsoup_testing", product.getImageUrl());
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

        Element elementImage = document.selectFirst("img.largeimage");
        if (elementImage != null) {
            junk = elementImage.toString();
            product.setImageUrl(junk.substring(junk.indexOf("https://"), junk.indexOf(" alt")-1));
        }
        else {
            product.setImageUrl("NA");
        }

        Log.e("jsoup_testing", product.getName());
        Log.e("jsoup_testing", String.valueOf(product.getPrice()));
        Log.e("jsoup_testing", String.valueOf(product.getRating()));
        Log.e("jsoup_testing", String.valueOf(product.getNumberOfRatings()));
        Log.e("jsoup_testing", product.getImageUrl());
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

        Element elementImage = document.selectFirst("#thumb_0");
        if (elementImage != null) {
            junk = elementImage.toString();
            product.setImageUrl(junk.substring(junk.indexOf("https://"), junk.indexOf(".jpg")+4));
        }
        else {
            product.setImageUrl("NA");
        }

        Log.e("jsoup_testing", product.getName());
        Log.e("jsoup_testing", String.valueOf(product.getPrice()));
        Log.e("jsoup_testing", String.valueOf(product.getRating()));
        Log.e("jsoup_testing", String.valueOf(product.getNumberOfRatings()));
        Log.e("jsoup_testing", product.getImageUrl());
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

        Element elementImage = document.selectFirst("div.q6DClP");
        if (elementImage != null) {
            junk = elementImage.toString();
            product.setImageUrl(junk.substring(junk.indexOf("https://"), junk.indexOf(")")));
        }
        else {
            product.setImageUrl("NA");
        }

        Log.e("jsoup_testing", product.getName());
        Log.e("jsoup_testing", String.valueOf(product.getPrice()));
        Log.e("jsoup_testing", String.valueOf(product.getRating()));
        Log.e("jsoup_testing", String.valueOf(product.getNumberOfRatings()));
        Log.e("jsoup_testing", product.getImageUrl());
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
        else {
            Element price_2 = document.selectFirst(".price3P");
            if (price_2 != null) {
                product.setPrice(Double.valueOf(price_2.text().replaceAll(ONLY_DIGITS_AND_DECIMAL_REGEX, "")));
            }
            else product.setPrice(0.0);
        }

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

        Element elementImage = document.selectFirst("img.a-dynamic-image.a-stretch-vertical");
        if (elementImage != null) {
            String junk = elementImage.toString();
            product.setImageUrl(junk.substring(junk.indexOf(";https://")+1, junk.indexOf(".jpg&quot")+4));
        }
        else {
            elementImage = document.selectFirst("img#landingImage.a-dynamic-image.a-stretch-horizontal");
            if (elementImage != null) {
                String junk = elementImage.toString();
                product.setImageUrl(junk.substring(junk.indexOf(";https://")+1, junk.indexOf(".jpg&quot")+4));
            }
            else {
                elementImage = document.selectFirst("img#imgBlkFront");
                if (elementImage != null) {
                    String junk = elementImage.toString();
                    product.setImageUrl(junk.substring(junk.indexOf(";https://")+1, junk.indexOf(".jpg&quot")+4));
                }
                else product.setImageUrl("NA");
            }
        }

        Log.e("jsoup_testing", product.getName());
        Log.e("jsoup_testing", String.valueOf(product.getPrice()));
        Log.e("jsoup_testing", String.valueOf(product.getRating()));
        Log.e("jsoup_testing", String.valueOf(product.getNumberOfRatings()));
        Log.e("jsoup_testing", product.getImageUrl());
    }

    public ArrayList<Product> scrapeAmazonProducts() throws IOException {
        ArrayList<Product> sol = new ArrayList<>();
        Document document = Jsoup.connect(getSearchQuery(site, query))
                .userAgent("Mozilla/5.0 (Linux; Android 8.0; Pixel 2 Build/OPD3.170816.012) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.87 Mobile Safari/537.36")
                .get();

        Elements names = document.select("span.a-size-small.a-color-base.a-text-normal");
        int numEle = names.size();
        for (int i = 0; i < Math.min(MAX_ELE_TO_SHOW, numEle); i++) {
            sol.add(new Product());
        }

        //Log.e("compare_testing", String.valueOf(sol.size()));

        if (!names.isEmpty()) {
            for (int i = 0; i < Math.min(MAX_ELE_TO_SHOW, names.size()); i++) {
                sol.get(i).setName(names.get(i).text());
            }
        }

        Elements prices = document.select("span.a-price-whole");
        if (!prices.isEmpty()) {
            for (int i = 0; i < sol.size(); i++) {
                sol.get(i).setPrice(Double.valueOf(prices.get(i).text().replaceAll(ONLY_DIGITS_AND_DECIMAL_REGEX, "")));
            }
        }

        Elements prodLink = document.select("a.a-link-normal.a-text-normal");
        if (!prodLink.isEmpty()) {
            for (int i = 0, j = 0; i < sol.size() * 4; i+=4, j++) {
                //System.out.println("https://www.amazon.in" + prodLink.get(i).attr("href"));
                sol.get(j).setUrl("https://www.amazon.in" + prodLink.get(i).attr("href"));
            }
        }

        Elements imgUrls = document.select("img.s-image");
        if (!imgUrls.isEmpty()) {
            for (int i = 0; i < sol.size(); i++) {
                //System.out.println(imgUrls.get(i).attr("src"));
                sol.get(i).setImageUrl(imgUrls.get(i).attr("src"));
            }
        }

        Elements ratings = document.select("i.a-icon.a-icon-star-small.a-icon-star-small");
        if (!ratings.isEmpty()) {
            for (int i = 0; i < sol.size(); i++) {
                //System.out.println(ratings.get(i).text());
                junk = ratings.get(i).text();
                sol.get(i).setRating(junk.substring(0, junk.indexOf(' ')));
            }
        }

        return sol;
    }

    public ArrayList<Product> scrapeFlipkartProducts() throws IOException {
        /*
        ArrayList<Product> sol = new ArrayList<>();
        Document document = Jsoup.connect(getSearchQuery(site, query))
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:84.0) Gecko/20100101 Firefox/84.0")
                .get();

        Elements names = document.select("div._4rR01T");
        if (!names.isEmpty()) {
            for (int i = 0; i < Math.min(MAX_ELE_TO_SHOW, names.size()); i++) {
                System.out.println(names.get(i).text());
            }
        }
        else {
            names = document.select("a.s1Q9rs");
            if (!names.isEmpty()) {
                for (int i = 0; i < Math.min(MAX_ELE_TO_SHOW, names.size()); i++) {
                    System.out.println(names.get(i).attr("title"));
                }
            }
            else {
                return null;
            }
        }

        for (int i = 0; i < Math.min(MAX_ELE_TO_SHOW, names.size()); i++) {
            sol.add(new Product());
        }

        Elements prices = document.select("div._30jeq3");
        if (prices.size() > 0) {
            for (int i = 0; i < Math.min(MAX_ELE_TO_SHOW, prices.size()); i++) {
                System.out.println(prices.get(i).text());
            }
        }
        else {
            prices = document.select("div._30jeq3");
            if (!prices.isEmpty()) {
                for (int i = 0; i < Math.min(MAX_ELE_TO_SHOW, prices.size()); i++) {
                    System.out.println(prices.get(i).text());
                }
            }
        }

        boolean isImage = false;
        Elements prodLink = document.select("a._1fQZEK");
        ArrayList<String> imgQueries = new ArrayList<>();
        if (!prodLink.isEmpty()) {
            isImage = true;
            for (int i = 0; i < Math.min(MAX_ELE_TO_SHOW, prodLink.size()); i++) {
                System.out.println("https://www.flipkart.com" + prodLink.get(i).attr("href"));
                imgQueries.add("https://www.flipkart.com" + prodLink.get(i).attr("href"));
            }
        }
        else {
            prodLink = document.select("a.s1Q9rs");
            if (!prodLink.isEmpty()) {
                isImage = true;
                for (int i = 0; i < Math.min(MAX_ELE_TO_SHOW, prodLink.size()); i++) {
                    System.out.println("https://www.flipkart.com" + prodLink.get(i).attr("href"));
                    imgQueries.add("https://www.flipkart.com" + prodLink.get(i).attr("href"));
                }
            }
        }

        if (isImage) {
            for (int i = 0; i < Math.min(MAX_ELE_TO_SHOW, imgQueries.size()); i++) {
                Document documentImg = Jsoup.connect(imgQueries.get(i))
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:84.0) Gecko/20100101 Firefox/84.0")
                        .get();
                Element elementImage = documentImg.selectFirst("div.q6DClP");
                if (elementImage != null) {
                    junk = elementImage.toString();
                    System.out.println(junk.substring(junk.indexOf("https://"), junk.indexOf(")")));
                }
                else {
                    System.out.println("NA");
                }
            }
        }

        Elements ratings = document.select("div._3LWZlK");
        if (!ratings.isEmpty()) {
            for (int i = 0; i < Math.min(MAX_ELE_TO_SHOW, ratings.size()); i++) {
                System.out.println(ratings.get(i).text());
            }
        }

         */
        return null;
    }

    public ArrayList<Product> scrapePaytmMallProducts() {
        /*
        Document document = Jsoup.connect(query)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:84.0) Gecko/20100101 Firefox/84.0")
                .get();

        Elements names = document.select("div.UGUy");
        if (!names.isEmpty()) {
            for (int i = 0; i < Math.min(MAX_ELE_TO_SHOW, names.size()); i++) {
                System.out.println(names.get(i).text());
            }
        }

        Elements prices = document.select("div._1kMS");
        if (!prices.isEmpty()) {
            for (int i = 0; i < Math.min(MAX_ELE_TO_SHOW, prices.size()); i++) {
                System.out.println(prices.get(i).text());
            }
        }

        Elements prodLinks = document.select("a._8vVO");
        if (!prodLinks.isEmpty()) {
            for (int i = 0;  i < Math.min(MAX_ELE_TO_SHOW, prodLinks.size()); i++) {
                System.out.println("https://paytmmall.com" + prodLinks.get(i).attr("href"));
            }
        }

        Elements imgUrl = document.select("div._3nWP img");
        if (!imgUrl.isEmpty()) {
            for (int i = 0; i < Math.min(MAX_ELE_TO_SHOW, imgUrl.size()); i++) {
                System.out.println(imgUrl.get(i).attr("src"));
            }
        }

         */
        return null;
    }

    public ArrayList<Product> scrapeSnapdealProducts() {
        /*
        Document document = Jsoup.connect(query)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:84.0) Gecko/20100101 Firefox/84.0")
                .get();

        Elements names = document.select("p.product-title");
        if (!names.isEmpty()) {
            for (int i = 0; i < Math.min(MAX_ELE_TO_SHOW, names.size()); i++) {
                System.out.println(names.get(i).text());
            }
        }

        Elements prices = document.select("span.lfloat.product-price");
        if (!prices.isEmpty()) {
            for (int i = 0; i < Math.min(MAX_ELE_TO_SHOW, prices.size()); i++) {
                System.out.println(prices.get(i).attr("data-price"));
            }
        }

        Elements prodLinks = document.select("div.product-tuple-image a");
        if (!prodLinks.isEmpty()) {
            for (int i = 0; i < Math.min(MAX_ELE_TO_SHOW, prodLinks.size()); i++) {
                System.out.println(prodLinks.get(i).attr("href"));
            }
        }

        Elements imgUrl = document.select("div.product-tuple-image img");
        if (!imgUrl.isEmpty()) {
            for (int i = 0; i < Math.min(MAX_ELE_TO_SHOW, imgUrl.size()); i++) {
                System.out.println(imgUrl.get(i).attr("src"));
            }
        }

        Elements ratings = document.select("div.filled-stars");
        if (!ratings.isEmpty()) {
            for (int i = 0; i < Math.min(MAX_ELE_TO_SHOW, ratings.size()); i++) {
                double sol = 100.00;
                sol = Double.parseDouble(ratings.get(i).attr("style").toString().replaceAll(ONLY_DIGITS_AND_DECIMAL_REGEX, "")) / sol * 5.00;
                System.out.println(sol);
            }
        }

         */
        return null;
    }

    public String getSearchQuery(String marketPlace, String searchQuery) {
        switch (marketPlace) {
            case "Amazon":
                return "https://www.amazon.in/s?k=" + searchQuery.replace(' ', '+') + "&ref=nb_sb_noss";
            case "Flipkart":
                return "https://www.flipkart.com/search?q=" + searchQuery.replace(' ', '+');
            case "Bigbasket":
                return "https://www.bigbasket.com/ps/?q=" + searchQuery.replace(" ", "%20");
            case "JioMart":
                return "https://www.jiomart.com/catalogsearch/result?q=" + searchQuery.replace(" ", "%20");
            case "Myntra":
                return "https://www.myntra.com/" + searchQuery.replace(' ', '-');
            case "Paytm Mall":
                return "https://paytmmall.com/shop/search?userQuery=" + searchQuery.replace(" ", "%20");
            case "Snapdeal":
                return "https://m.snapdeal.com/search?keyword=" + searchQuery.replace(" ", "%20") + "&categoryXPath=ALL";
            default:
                return "https://www.amazon.in/s?k=" + searchQuery.replace(' ', '+') + "&ref=nb_sb_noss";
        }
    }
}

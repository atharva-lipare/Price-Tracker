package com.example.pricetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper {

    private Context context;
    SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");

    public static final String DATABASE_NAME = "Products.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_A_NAME = "products_list";
    private static final String TABLE_B_NAME = "prices_copy";
    private static final String A_COLUMN_NAME = "name";
    private static final String A_COLUMN_URL = "url";
    private static final String A_COLUMN_IMAGE_URL = "image_url";
    private static final String A_COLUMN_MARKETPLACE = "marketplace";
    private static final String A_COLUMN_RATING = "rating";
    private static final String A_COLUMN_CURRENT_PRICE = "current_price";
    private static final String A_COLUMN_NUM_RATINGS = "num_ratings";
    private static final String B_COLUMN_NAME = "name";
    private static final String B_COLUMN_URL = "url";
    private static final String B_COLUMN_MARKETPLACE = "marketplace";
    private static final String B_COLUMN_PRICE = "price";
    private static final String B_COLUMN_TIMESTAMP = "timestamp";

    public MyDBHandler(@Nullable Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query1 = "CREATE TABLE " + TABLE_A_NAME +
                " (" + A_COLUMN_NAME + " TEXT, " +
                A_COLUMN_URL + " TEXT PRIMARY KEY, " +
                A_COLUMN_IMAGE_URL + " TEXT, " +
                A_COLUMN_MARKETPLACE + " TEXT, " +
                A_COLUMN_CURRENT_PRICE + " REAL, " +
                A_COLUMN_RATING + " TEXT, " +
                A_COLUMN_NUM_RATINGS + " INTEGER);";
        sqLiteDatabase.execSQL(query1);

        String query2 = "CREATE TABLE " + TABLE_B_NAME +
                " (" + B_COLUMN_NAME + " TEXT, " +
                B_COLUMN_URL + " TEXT, " +
                B_COLUMN_MARKETPLACE +" TEXT, " +
                B_COLUMN_PRICE + " REAL, " +
                B_COLUMN_TIMESTAMP + " TEXT," +
                "PRIMARY KEY (" + B_COLUMN_URL + ", " + B_COLUMN_TIMESTAMP + ")" +
                ");";
        sqLiteDatabase.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_A_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_B_NAME);
        onCreate(sqLiteDatabase);
    }

    //@RequiresApi(api = Build.VERSION_CODES.O)
    public boolean insertIntoTableA(Product product){
        SQLiteDatabase db = this.getWritableDatabase();
        String url = product.getUrl();
        url = '"' + url + '"';
        /*Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_A_NAME + " WHERE url=" + url,null);
        if(cursor == null || cursor.getCount() == 0) {
            ContentValues cv1 = new ContentValues();
            cv1.put(A_COLUMN_NAME, product.getName());
            cv1.put(A_COLUMN_URL, product.getUrl());
            cv1.put(A_COLUMN_IMAGE_URL, product.getImageUrl());
            cv1.put(A_COLUMN_MARKETPLACE, product.getMarketPlace());
            cv1.put(A_COLUMN_CURRENT_PRICE, product.getPrice());
            cv1.put(A_COLUMN_RATING, product.getRating());
            cv1.put(A_COLUMN_NUM_RATINGS, product.getNumberOfRatings());
            long result = db.insert(TABLE_A_NAME, null, cv1);
            if (result == -1) {
                Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show();
            }
            else Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
        }*/
        ContentValues cv1 = new ContentValues();
        cv1.put(A_COLUMN_NAME, product.getName());
        cv1.put(A_COLUMN_URL, product.getUrl());
        cv1.put(A_COLUMN_IMAGE_URL, product.getImageUrl());
        cv1.put(A_COLUMN_MARKETPLACE, product.getMarketPlace());
        cv1.put(A_COLUMN_CURRENT_PRICE, product.getPrice());
        cv1.put(A_COLUMN_RATING, product.getRating());
        cv1.put(A_COLUMN_NUM_RATINGS, product.getNumberOfRatings());
        long result = db.insert(TABLE_A_NAME, null, cv1);
        return result != -1;
    }

    public boolean insertIntoTableB(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(B_COLUMN_NAME, product.getName());
        cv.put(B_COLUMN_URL, product.getUrl());
        cv.put(B_COLUMN_MARKETPLACE, product.getMarketPlace());
        cv.put(B_COLUMN_PRICE, product.getPrice());
        Calendar cal = Calendar.getInstance();
        cv.put(B_COLUMN_TIMESTAMP, format1.format(cal.getTime()));
        long result = db.insert(TABLE_B_NAME, null, cv);
        return result != -1;
    }

    // Updating prices and adding it to table B
    //@RequiresApi(api = Build.VERSION_CODES.O)
    public void updatePrice(Product product){
        SQLiteDatabase db = this.getWritableDatabase();
        Calendar cal = Calendar.getInstance();
        String datetime = format1.format(cal.getTime());
        Cursor cursor = db.rawQuery("SELECT "+B_COLUMN_TIMESTAMP+" FROM "+TABLE_B_NAME+" WHERE timestamp LIKE "+datetime+"%;",null);

        // New Day case
        if(cursor == null || cursor.getCount()==0) {
            ContentValues cv1 = new ContentValues();
            cv1.put(A_COLUMN_CURRENT_PRICE, product.getPrice());
            ContentValues cv2 = new ContentValues();
            cv2.put(B_COLUMN_NAME, product.getName());
            cv2.put(B_COLUMN_URL, product.getUrl());
            cv2.put(B_COLUMN_MARKETPLACE, product.getMarketPlace());
            cv2.put(B_COLUMN_PRICE, product.getPrice());
            cv2.put(B_COLUMN_TIMESTAMP, format1.format(cal.getTime()));
            // TODO: Need to check it once
            db.update(TABLE_A_NAME,cv1,"timestamp LIKE ?",new String[]{datetime+"%"});
            db.insert(TABLE_B_NAME, null, cv2);
        }
        else{
            // Update the latest entry
            ContentValues cv3 = new ContentValues();
            cv3.put(B_COLUMN_TIMESTAMP, format1.format(cal.getTime()));
            cv3.put(B_COLUMN_PRICE,product.getPrice());
            db.update(TABLE_B_NAME,cv3,"timestamp LIKE ?",new String[]{datetime+"%"});
        }
    }

    public void deleteProduct(Product product){

    }

    public ArrayList<Product> getAllProductsFromTableA(){
        ArrayList<Product> productsList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_A_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
            if (cursor.getCount() == 0) return null;
            while(cursor.moveToNext()) {
                Product p = new Product();
                p.setName(cursor.getString(0));
                p.setUrl(cursor.getString(1));
                p.setImageUrl(cursor.getString(2));
                p.setMarketPlace(cursor.getString(3));
                p.setPrice(cursor.getDouble(4));
                p.setRating(cursor.getString(5));
                p.setNumberOfRatings(cursor.getInt(6));
                productsList.add(p);
            }
        }
        return productsList;
    }

    public ArrayList<Product> getAllProductsFromTableB(){
        ArrayList<Product> productsList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_B_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
            if (cursor.getCount() == 0) return null;
            while(cursor.moveToNext()) {
                Product p = new Product();
                p.setName(cursor.getString(0));
                p.setUrl(cursor.getString(1));
                p.setMarketPlace(cursor.getString(3));
                p.setPrice(cursor.getDouble(4));
                productsList.add(p);
            }
        }
        return productsList;
    }

    public ArrayList<Product> getAllProductsFromTableBForGraph(String url){
        ArrayList<Product> productsList = new ArrayList<>();
        url = '"' + url + '"';
        String query = "SELECT * FROM " + TABLE_B_NAME + " WHERE url=" + url;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
            if (cursor.getCount() == 0) return null;
            while(cursor.moveToNext()) {
                Product p = new Product();
                p.setPrice(cursor.getDouble(3));
                p.setName(cursor.getString(4)); // timestamps
                productsList.add(p);
            }
        }
        return productsList;
    }

    public boolean updateTableA(Product product) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv1 = new ContentValues();
        cv1.put(A_COLUMN_NAME, product.getName());
        cv1.put(A_COLUMN_URL, product.getUrl());
        cv1.put(A_COLUMN_IMAGE_URL, product.getImageUrl());
        cv1.put(A_COLUMN_MARKETPLACE, product.getMarketPlace());
        cv1.put(A_COLUMN_CURRENT_PRICE, product.getPrice());
        cv1.put(A_COLUMN_RATING, product.getRating());
        cv1.put(A_COLUMN_NUM_RATINGS, product.getNumberOfRatings());
        String url = product.getUrl();
        Log.e("db_temp_testing", String.valueOf(product.getPrice()));
        long result = db.update(TABLE_A_NAME, cv1, "url = ?", new String[]{url});
        return result != -1;
    }

    public boolean updateTableB(Product product) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(B_COLUMN_NAME, product.getName());
        cv.put(B_COLUMN_URL, product.getUrl());
        cv.put(B_COLUMN_MARKETPLACE, product.getMarketPlace());
        cv.put(B_COLUMN_PRICE, product.getPrice());
        Calendar cal = Calendar.getInstance();
        cv.put(B_COLUMN_TIMESTAMP, format1.format(cal.getTime()));
        String url = product.getUrl();
        Log.e("db_temp_testing", String.valueOf(product.getPrice()));
        long result = db.update(TABLE_B_NAME, cv, "url = ?", new String[]{url});
        return result != -1;
    }
}

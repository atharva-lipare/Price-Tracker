package com.example.pricetracker;

import android.content.Context;
import android.telephony.mbms.MbmsErrors;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ItemViewAdapter extends RecyclerView.Adapter<ItemViewAdapter.ItemViewHolder> {
    private ArrayList<Product> products;
    Context context;

    public ItemViewAdapter(Context context, ArrayList<Product> products) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.productName.setText(products.get(position).getName());
        int starUnicode = 0x2B50;
        holder.productRating.setText(products.get(position).getRating() + getEmojiByUnicode(starUnicode));
        int inrSign = 0x20B9;
        holder.productPrice.setText(getEmojiByUnicode(inrSign) + products.get(position).getPrice().toString());
        int placeholder = R.drawable.ic_launcher_foreground;
        switch (products.get(position).getName()) {
            case "Amazon":
                placeholder = R.drawable.ic_amazon;
                holder.siteLogo.setImageResource(R.drawable.ic_amazon);
                break;
            case "Flipkart":
                placeholder = R.drawable.ic_flipkart;
                holder.siteLogo.setImageResource(R.drawable.ic_flipkart);
                break;
            case "Bigbasket":
                placeholder = R.drawable.ic_bigbasket;
                holder.siteLogo.setImageResource(R.drawable.ic_bigbasket);
                break;
            case "JioMart":
                placeholder = R.drawable.ic_jiomart;
                holder.siteLogo.setImageResource(R.drawable.ic_jiomart);
                break;
            case "Paytm Mall":
                placeholder = R.drawable.ic_paytmmall;
                holder.siteLogo.setImageResource(R.drawable.ic_paytmmall);
                break;
            case "Snapdeal":
                placeholder = R.drawable.ic_snapdeal;
                holder.siteLogo.setImageResource(R.drawable.ic_snapdeal);
                break;
        }
        Glide.with(context).load(products.get(position).getImageUrl()).placeholder(placeholder).into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView productName;
        TextView productPrice;
        TextView productRating;
        ImageView siteLogo;
        ImageView productImage;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.itemPrice);
            productRating = itemView.findViewById(R.id.itemRating);
            siteLogo = itemView.findViewById(R.id.siteLogo);
            productImage = itemView.findViewById(R.id.itemImage);
        }
    }

    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }
}

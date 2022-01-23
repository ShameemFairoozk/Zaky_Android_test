package com.example.zaky_android_test.models;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.example.zaky_android_test.R;

import java.util.ArrayList;

public class ModelAdapter extends RecyclerView.Adapter    {
    private ArrayList<ItemListGS> modelClassList;
    Context context;


    public ModelAdapter(ArrayList<ItemListGS> modelClassList, Context context) {
        this.modelClassList = modelClassList;
        this.context = context;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


                View itemListView = null;

                itemListView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_adapter, parent, false);

                return new ItemListViewHolder(itemListView);





    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

                ItemListGS itemListGS = modelClassList.get(position);
                ((ItemListViewHolder) holder).setItemList(itemListGS);




    }

    @Override
    public int getItemCount() {
        return modelClassList.size();
    }



    public class ItemListViewHolder extends RecyclerView.ViewHolder {
        ImageView ItemImage;
        TextView ItemName, ItemRate, Quantity;
        RelativeLayout Layout;
        Button WishButton;
        RelativeLayout IncrementButton, DecrementButton;
        public ItemListViewHolder(@NonNull View itemView) {
            super(itemView);
            ItemImage = itemView.findViewById(R.id.itemImage);
            ItemName = itemView.findViewById(R.id.itemName);
            ItemRate = itemView.findViewById(R.id.itemRate);
            Layout = itemView.findViewById(R.id.layout);
            Quantity = itemView.findViewById(R.id.Quantity);
            IncrementButton = itemView.findViewById(R.id.incrementQty);
            DecrementButton = itemView.findViewById(R.id.decrementQty);
            WishButton = itemView.findViewById(R.id.wishButton);

        }
        public void setItemList(final ItemListGS s) {
            ItemName.setText(s.getItemName());
            ItemRate.setText(s.getItemRate());
            if (s.getItemImage() != null) {
                Glide.with(context).load(s.getItemImage()).priority(Priority.IMMEDIATE).placeholder(R.drawable.defaultitem).into(ItemImage);
            } else {
                ItemImage.setImageResource(R.drawable.defaultitem);
            }

            if (s.getWishListStatus() != null) {
                if (s.getWishListStatus().equals("True")) {
                    WishButton.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
                } else {
                    WishButton.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
                }
            } else {
                WishButton.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
            }


        }
    }

}

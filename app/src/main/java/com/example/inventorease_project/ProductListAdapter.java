package com.example.inventorease_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ProductListAdapter extends ArrayAdapter<ProductList> {
    private Context mContext;
    int mResource;

    public ProductListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ProductList> objects) {
        super(context, resource,objects);
        mContext = context;
        mResource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String pname = getItem(position).getPname();
        int quan = getItem(position).getQuantity();
        int cost = getItem(position).getCost();
        int price = getItem(position).getPrice();



        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent,false);



        TextView prodTV = convertView.findViewById(R.id.prodTV);
        TextView quantityTV = convertView.findViewById(R.id.quantityTV);
        TextView costTV = convertView.findViewById(R.id.costTV);
        TextView priceTV = convertView.findViewById(R.id.receiptPriceTV);


        prodTV.setText(pname);
        quantityTV.setText(String.valueOf(quan));
        costTV.setText(String.valueOf(cost));
        priceTV.setText(String.valueOf(price));


        return convertView;
    }
}




















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

public class SalesListAdapter extends ArrayAdapter<SalesArrayClass> {
    private final Context mContext;
    private final int mResource;

    public SalesListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<SalesArrayClass> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String salesProduct = getItem(position).getSalesproduct();
        String salesQty = getItem(position).getSalesqty();
        String salesPrice = getItem(position).getSalesprice();
        String salesTotal = getItem(position).getSalestotal();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvProduct = convertView.findViewById(R.id.tvProduct);
        TextView tvQty = convertView.findViewById(R.id.tvQty);
        TextView tvPrice = convertView.findViewById(R.id.tvPrice);
        TextView tvTotal = convertView.findViewById(R.id.tvTotal);

        tvProduct.setText(salesProduct);
        tvQty.setText(salesQty);
        tvPrice.setText(salesPrice);
        tvTotal.setText(salesTotal);

        return convertView;
    }
}
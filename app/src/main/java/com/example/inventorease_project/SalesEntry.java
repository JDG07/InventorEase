package com.example.inventorease_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;


public class SalesEntry extends AppCompatActivity {
Dialog CDSales;
    public static EditText productsalesET,quantitysoldET;
    public static Button addsales,backsales;

    public static AutoCompleteTextView autoCompleteTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_entry);
        CDSales = new Dialog(this);




        ArrayList <ProductList> products = AddItemViews.productList;
        ArrayList <String> searchproduct = new ArrayList<>();

        for (ProductList product: products) {
            searchproduct.add(product.getPname());
        }

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, searchproduct);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(adapter);



            autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {

                LinearLayout custompopupL = findViewById(R.id.custompopupL);

                CDSales.setContentView(R.layout.custompopupsales);
                productsalesET = CDSales.findViewById(R.id.productsalesET);
                quantitysoldET = CDSales.findViewById(R.id.quantitysoldET);
                backsales= CDSales.findViewById(R.id.backsales);
                addsales= CDSales.findViewById(R.id.addsales);

             // wala pang back button condition

                CDSales.show();

            });









    }
}
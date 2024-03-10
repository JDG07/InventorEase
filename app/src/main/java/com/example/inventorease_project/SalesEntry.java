package com.example.inventorease_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class SalesEntry extends AppCompatActivity {
Dialog CDSales;
    public static EditText productsalesET;
    public static EditText quantitysoldET;
    public static EditText totalsoldET;
    public static EditText pricesoldET;
    public static EditText remainingstockET;

    public static Button addsales,backsales;

    public static TextView prodnameTV, quanTV, priceTV, totalTV;

    public static ArrayList <SalesArrayClass> salesarray = new ArrayList<>();
    public static ProductListAdapter salesadapter;


    public static AutoCompleteTextView autoCompleteTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_sales_entry);
        CDSales = new Dialog(this);





        Button backdash1 = findViewById(R.id.backdash1);

        backdash1.setOnClickListener(view ->{
            Intent intent = new Intent (SalesEntry.this,MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Going Back", Toast.LENGTH_SHORT).show();
        });






        ArrayList <ProductList> products = AddItemViews.productList;
        ArrayList <String> searchproduct = new ArrayList<>();

        for (ProductList product: products) {
            searchproduct.add(product.getPname());
        }

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.salesentryactvlayout,R.id.salesACTV ,searchproduct);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(adapter);



            autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {

                LinearLayout custompopupL = findViewById(R.id.custompopupL);
                CDSales.setContentView(R.layout.custompopupsales);

                productsalesET = CDSales.findViewById(R.id.productsalesET);
                quantitysoldET = CDSales.findViewById(R.id.quantitysoldET);
                totalsoldET = CDSales.findViewById(R.id.totalsoldET);
                pricesoldET = CDSales.findViewById(R.id.pricesoldET);
                remainingstockET = CDSales.findViewById(R.id.remainingquantityET);



                backsales= CDSales.findViewById(R.id.backsales);
                addsales= CDSales.findViewById(R.id.addsales);

               productsalesET.setEnabled(false);
               pricesoldET.setEnabled(false);
               remainingstockET.setEnabled(false);
               totalsoldET.setEnabled(false);

                productsalesET.setText("");
                quantitysoldET.setText("");
                totalsoldET.setText("");
                pricesoldET.setText("");
                remainingstockET.setText("");


                String selectedProductName = (String) parent.getItemAtPosition(position);
                int selectedPosition = searchproduct.indexOf(selectedProductName);

                if (selectedPosition >= 0 && selectedPosition < products.size()) {
                    ProductList searchedProduct = products.get(selectedPosition);

                    productsalesET.setText(searchedProduct.getPname());

                    int priceget = searchedProduct.getPrice();
                    pricesoldET.setText(String.valueOf(priceget));

                    int remainingstockget = searchedProduct.getQuantity();
                    remainingstockET.setText(String.valueOf(remainingstockget));
                } else {

                   Log.e("Error", "Selected position is out of bounds");
                }

                backsales.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CDSales.dismiss();
                        autoCompleteTextView.setText("");
                        productsalesET.setText("");
                        quantitysoldET.setText("");
                        pricesoldET.setText("");
                        totalsoldET.setText("");
                    }
                });

                ListView SalesLV = (ListView) findViewById(R.id.SalesLV);
                ArrayList<SalesArrayClass> zzz = salesarray;
                SalesListAdapter salesadapter = new SalesListAdapter(this, R.layout.saleslistviewlayout, zzz);
                SalesLV.setAdapter(salesadapter);





                addsales.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       addSales();

                    }
                });



                quantitysoldET.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String quantitySoldStr = quantitysoldET.getText().toString().trim();

                        if (!quantitySoldStr.isEmpty()) {
                            int quansold = Integer.parseInt(quantitySoldStr);
                            int pricesold = Integer.parseInt(pricesoldET.getText().toString());

                            int totalprice = quansold * pricesold;
                            totalsoldET.setText(String.valueOf(totalprice));
                        } else {

                            totalsoldET.setText("");
                        }
                    }
                });

                CDSales.show();

            });
    }
    public void addSales(){
        String prodsales = String.valueOf(productsalesET.getText());
        String quansales = String.valueOf(quantitysoldET.getText());
        String pricesales = String.valueOf(pricesoldET.getText());
        String totsales = String.valueOf(totalsoldET.getText());

        SalesArrayClass salesEntry = new SalesArrayClass(prodsales,quansales,pricesales,totsales);
        salesarray.add(salesEntry);

        productsalesET.getText().clear();
        quantitysoldET.getText().clear();
        pricesoldET.getText().clear();
        totalsoldET.getText().clear();

        CDSales.dismiss();
        autoCompleteTextView.setText("");

        Toast.makeText(this, "Product added successfully", Toast.LENGTH_SHORT).show();

    }
}
package com.example.inventorease_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AddStocksView extends AppCompatActivity {

    public static AutoCompleteTextView productstock;
    public static ArrayList<String> prodstocksNames = new ArrayList<>();

    public static TextView autoremainingET, autopriceET,autocostET,autoquanET;

    public static ArrayList<ProductList> prodstocks = AddItemViews.productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_add_stocks_view);
        Button backstocks = findViewById(R.id.backstocks);
        autoremainingET = findViewById(R.id.autoremaining);
        autopriceET = findViewById(R.id.autoprice);
        autoquanET = findViewById(R.id.autoquan);
        autocostET = findViewById(R.id.autocost);
        Button testaddstocks = findViewById(R.id.testaddstocks);



        backstocks.setOnClickListener(view -> {
            Intent intent = new Intent(AddStocksView.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Going Back", Toast.LENGTH_SHORT).show();
        });




        for (ProductList product : prodstocks) {
            prodstocksNames.add(product.getPname());
        }

        productstock = findViewById(R.id.productstock);

        ArrayAdapter <String> adapter = new ArrayAdapter<>(this, R.layout.autocompletetextviewlayout, R.id.ProductACTV,prodstocksNames);
        productstock.setThreshold(1);
        productstock.setAdapter(adapter);

        productstock.setOnItemClickListener((parent, view, position, id) -> {
            // Clear previous selection
            autoremainingET.setText("");
            autopriceET.setText("");
            autocostET.setText("");

            // Get the selected product name from the adapter
            String selectedProductName = (String) parent.getItemAtPosition(position);

            // Find the corresponding ProductList object
            ProductList searchedProduct = null;
            for (ProductList product : prodstocks) {
                if (product.getPname().equals(selectedProductName)) {
                    searchedProduct = product;
                    break;
                }
            }

            if (searchedProduct != null) {
                int auremaining = searchedProduct.getQuantity();
                int auprice = searchedProduct.getPrice();
                int aucost = searchedProduct.getCost();

                autoremainingET.setText(String.valueOf(auremaining));
                autopriceET.setText(String.valueOf(auprice));
                autocostET.setText(String.valueOf(aucost));
            } else {
                Log.e("Error", "Selected product not found");
            }
        });

        testaddstocks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStock();
            }
        });

    }
    public void addStock (){
        String selectedProductName = String.valueOf(productstock.getText());


        String quantityStr = String.valueOf(autoquanET.getText());
        if (quantityStr.isEmpty()) {
            Toast.makeText(this, "Please enter a quantity", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantityToAdd = Integer.parseInt(quantityStr);

        // Find the corresponding ProductList object
        ProductList searchedProduct = null;
        for (ProductList product : prodstocks) {
            if (product.getPname().equals(selectedProductName)) {
                searchedProduct = product;
                break;
            }
        }
        if (searchedProduct != null) {
            int currentQuantity = searchedProduct.getQuantity();
            searchedProduct.setQuantity(currentQuantity + quantityToAdd);


            Toast.makeText(this, "Stock added successfully", Toast.LENGTH_SHORT).show();

            productstock.setText("");
            autocostET.setText("");
            autopriceET.setText("");
            autoremainingET.setText("");
            autoquanET.setText("");
        } else {
            Log.e("Error", "Selected product not found");
            Toast.makeText(this, "Error: Product not found", Toast.LENGTH_SHORT).show();
        }
    }

    }




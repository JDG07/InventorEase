package com.example.inventorease_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class StatisticsView extends AppCompatActivity {
    private TextView productCountTextView;
    private TextView inventoryValueTextView;
    private TextView inventoryCostTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_view);

        productCountTextView = findViewById(R.id.productCountTextView);
        inventoryValueTextView = findViewById(R.id.inventoryValueTextView);
        inventoryCostTextView = findViewById(R.id.inventoryCostTextView);


        updateStatistics();


    }
    private void updateStatistics() {

        ArrayList<ProductList> productList = AddItemViews.productList;

        int productCount = productList.size();


        int inventoryValue = 0;
        int inventoryCost = 0;

        for (ProductList product : productList) {
            int quantity = product.getQuantity();
            int costPerItem = product.getCost();
            int pricePerItem = product.getPrice();

            inventoryValue += (quantity * pricePerItem);
            inventoryCost += (quantity * costPerItem);
        }

        productCountTextView.setText("Number of Products: " + productCount);
        inventoryValueTextView.setText("Current Inventory Value: " + inventoryValue);
        inventoryCostTextView.setText("Current Inventory Cost: " + inventoryCost);
    }
}
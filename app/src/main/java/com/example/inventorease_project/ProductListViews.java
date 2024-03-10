package com.example.inventorease_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ProductListViews extends AppCompatActivity {
Button EditYes, EditNo;


    public static ProductListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_product_list_views);
        Button backproduct = findViewById(R.id.backproduct);

        ListView mListView = (ListView) findViewById(R.id.listviewproducts);
        ArrayList<ProductList> productNames = AddItemViews.productList;

        adapter = new ProductListAdapter(this,R.layout.productslistviewlayout,productNames);
        mListView.setAdapter(adapter);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                ProductList selectedProduct = productNames.get(position);

                Intent intent = new Intent( ProductListViews.this, ProductInfoViews.class);

                intent.putExtra("pname", selectedProduct.getPname());
                intent.putExtra("quantity", selectedProduct.getQuantity());
                intent.putExtra("cost", selectedProduct.getCost());
                intent.putExtra("price", selectedProduct.getPrice());

                startActivity(intent);
            }
        });



        backproduct.setOnClickListener(view ->{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        });
}

///////////////////


    }
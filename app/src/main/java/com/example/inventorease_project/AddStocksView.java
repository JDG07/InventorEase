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
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AddStocksView extends AppCompatActivity {

    public static AutoCompleteTextView productstock;
    public static ArrayList<String> prodstocksNames = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_add_stocks_view);
        Button backstocks = findViewById(R.id.backstocks);


        backstocks.setOnClickListener(view -> {
            Intent intent = new Intent(AddStocksView.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Going Back", Toast.LENGTH_SHORT).show();
        });

        /////////// autocomplete textview should show dropdown with existing product names

        ArrayList<ProductList> prodstocks = AddItemViews.productList;


        for (ProductList product : prodstocks) {
            prodstocksNames.add(product.getPname());
        }

        productstock = findViewById(R.id.productstock);

        ArrayAdapter <String> adapter = new ArrayAdapter<>(this, R.layout.autocompletetextviewlayout, R.id.ProductACTV,prodstocksNames);
        productstock.setThreshold(1);
        productstock.setAdapter(adapter);

    }

   /* private void fillininfo (){
        productstock.setOnItemClickListener((parent, view, position, id) -> {
            /// when item is click it should compare the value to the arraylist
            /// select position and carry the values of the designated value (cost,price,quantity)

        });

    }
*/
}


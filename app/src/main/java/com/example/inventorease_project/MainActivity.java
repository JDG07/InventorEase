package com.example.inventorease_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

// lols

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton additemsbutton = findViewById(R.id.additemsbutton);
        ImageButton addstocksbutton = findViewById(R.id.addstocksbutton);
        ImageButton productlistbutton = findViewById(R.id.productlistbutton);
        ImageButton salesentrybutton = findViewById(R.id.salesentrybutton);
        ImageButton statisticsbutton = findViewById(R.id.statisticsbutton);



        additemsbutton.setOnClickListener(view->{
            Intent c = new Intent(MainActivity.this,AddItemViews.class);
            startActivity(c);
            finish();
        });

        addstocksbutton.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this,AddStocksView.class);
            startActivity(intent);
            finish();
        });

        productlistbutton.setOnClickListener(view->{
            Intent c = new Intent(MainActivity.this,ProductListViews.class);
            startActivity(c);
            finish();
        });



    }
}
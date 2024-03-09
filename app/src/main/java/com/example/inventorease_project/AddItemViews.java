package com.example.inventorease_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddItemViews extends AppCompatActivity {
    public static ArrayList<ProductList> productList = new ArrayList<>();
    public static ArrayList<String> productListNames = new ArrayList<>();
    public static ArrayList<Integer> quantitydata = new ArrayList<>();
    public static ArrayList<Integer> pricedata = new ArrayList<>();
    public static ArrayList<Integer> costdata = new ArrayList<>();

    //vincepogi
    public static AutoCompleteTextView productnameCVT;
    public static EditText quantityET, costET, priceET,prodET;
    Button addpBTN,backitem,plistb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_views);

         prodET =findViewById(R.id.prodET);
         quantityET = findViewById(R.id.quantityET);
         costET = findViewById(R.id.costET);
         priceET = findViewById(R.id.priceET);
         addpBTN = findViewById(R.id.addpBTN);
         backitem= findViewById(R.id.backitem);
         plistb = findViewById(R.id.plistb);


        backitem.setOnClickListener(view ->{
            Intent intent = new Intent (AddItemViews.this,MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Going Back", Toast.LENGTH_SHORT).show();
        });


        addpBTN.setOnClickListener(view ->{
                addProduct();
                printArrayLists();


         });

        plistb.setOnClickListener(view ->{
            Intent intent = new Intent (AddItemViews.this,ProductListViews.class);
            startActivity(intent);
            Toast.makeText(this, "Going to Product List", Toast.LENGTH_SHORT).show();

        });



    }



    private void addProduct(){
        // Get user input from EditText fields
        String productName = prodET.getText().toString();

        // Validate input
        if (productName.isEmpty()) {
            Toast.makeText(this, "Please fill in the product name", Toast.LENGTH_SHORT).show();
            return;
        }

        int productQuantity = 0;
        int productPrice = 0;
        int productCost =0;

        // Parse and validate quantity
        try {
            productQuantity = Integer.parseInt(quantityET.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid quantity", Toast.LENGTH_SHORT).show();
            return;
        }

        // Parse and validate price
        try {
            productCost = Integer.parseInt(costET.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid cost", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            productPrice = Integer.parseInt(priceET.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid price", Toast.LENGTH_SHORT).show();
            return;
        }




        // Create a new Product instance with user input
        ProductList newProduct = new ProductList(productName,productQuantity, productCost,productPrice);

        // Add the new product to the ArrayList
        productList.add(newProduct);
        productListNames.add(productName);


        prodET.getText().clear();
        quantityET.getText().clear();
        costET.getText().clear();
        priceET.getText().clear();


        Toast.makeText(this, "Product added successfully", Toast.LENGTH_SHORT).show();

    }


private void printArrayLists() {
    // This is for debugging purposes, you can remove this in the final version

    Log.d("ArrayLists", "Quantity Data: " + productList);

}
}
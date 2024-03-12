package com.example.inventorease_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
    Button addpBTN,plistb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_add_item_views);

         prodET =findViewById(R.id.prodET);
         quantityET = findViewById(R.id.quantityET);
         costET = findViewById(R.id.costET);
         priceET = findViewById(R.id.priceET);
         addpBTN = findViewById(R.id.addpBTN);
         ImageButton backitem= findViewById(R.id.backitem);
         plistb = findViewById(R.id.plistb);

        final int maxCharacters = 25; // Define  maximum characters

        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(maxCharacters);
        prodET.setFilters(filters);
        /////////////////////////////
        EditText costET = findViewById(R.id.costET);
        final int maxDigits = 6;
        /////////////////////////////
        InputFilter[] filters2 = new InputFilter[1];
        filters2[0] = new InputFilter.LengthFilter(maxDigits);
        //////////////////////////////////
        costET.setFilters(filters2);
        priceET.setFilters(filters2);
        ///////////////////////////////////
        final int quanmaxDigits = 4;

        InputFilter[] filters3 = new InputFilter[1];
        filters3[0] = new InputFilter.LengthFilter(quanmaxDigits);

        quantityET.setFilters(filters3);


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
/* private void backtoast (){
        LayoutInflater inf = getLayoutInflater();
        View byu = inf.inflate(R.layout.toastbackcustomlayout,this.findViewById(R.id.backtoast));

        Toast toastb = new Toast(this);
        toastb.setDuration (Toast.LENGTH_SHORT);
        toastb.setView(byu);
    toastb.show();

}
*/

}
package com.example.inventorease_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;


public class ProductInfoViews extends AppCompatActivity {
    AlertDialog.Builder builderedit;
    public static EditText prodinfo, quaninfo, costinfo,priceinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_app_info_views);

        prodinfo = findViewById(R.id.prodinfoET);
        quaninfo = findViewById(R.id.quaninfoET);
        costinfo = findViewById(R.id.costinfoET);
        priceinfo = findViewById(R.id.priceinfoET);

        Button saveBTN = findViewById(R.id.saveBTN);
        Button deleteBTN = findViewById(R.id.deleteBTN);
        Button editBTN = findViewById(R.id.editBTN);
        Button backplist =findViewById(R.id.backplist);

        //////////////// clickable false
        saveBTN.setClickable(false);
        deleteBTN.setClickable(false);

        //////////////////////////// editable false
        prodinfo.setEnabled(false);
        quaninfo.setEnabled(false);
        costinfo.setEnabled(false);
        priceinfo.setEnabled(false);
        /////////////////////////

        Intent intent = getIntent();
        String productName = intent.getStringExtra("pname");
        int quantity = intent.getIntExtra("quantity", 0);
        int cost = intent.getIntExtra("cost",0);
        int price = intent.getIntExtra("price",0);


        prodinfo.setText(productName);
        quaninfo.setText(String.valueOf(quantity));
        costinfo.setText(String.valueOf(cost));
        priceinfo.setText(String.valueOf(price));

        editBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
            }
        });

        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges(productName);
            }
        });

        deleteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct(productName);
            }
        });

        backplist.setOnClickListener(view -> {
            Intent intent2 = new Intent(ProductInfoViews.this, ProductListViews.class);
            startActivity(intent2);
            Toast.makeText(this, "Going Back", Toast.LENGTH_SHORT).show();
        });

    }
    private void saveChanges(String originalProductName){
        // Retrieve edited values from EditText fields
        String editedProductName = prodinfo.getText().toString();
        int editedQuantity = Integer.parseInt(quaninfo.getText().toString());
        int editedCost = Integer.parseInt(costinfo.getText().toString());
        int editedPrice = Integer.parseInt(priceinfo.getText().toString());

        // Find the corresponding Product object in the productList using a for loop
        for (int i = 0; i < AddItemViews.productList.size(); i++) {
            ProductList product = AddItemViews.productList.get(i);

            if(product.getPname().equals(originalProductName)){

                // Update the values
                product.setPname(editedProductName);
                product.setQuantity(editedQuantity);
                product.setCost(editedCost);
                product.setPrice(editedPrice);

                break;
            }

        }
        ProductListViews.adapter.notifyDataSetChanged();
        finish();

    }
    private void deleteProduct(String productNameToDelete){

        for (int i = 0; i < AddItemViews.productList.size(); i++) {
            ProductList product = AddItemViews.productList.get(i);

            if(product.getPname().equals(productNameToDelete));{

                AddItemViews.productList.remove(i);

                ProductListViews.adapter.notifyDataSetChanged();
                finish();
                return;

            }


        }


    }

    private void showEditDialog() {

        ConstraintLayout EditAD = findViewById(R.id.EditAD);

        builderedit = new AlertDialog.Builder(ProductInfoViews.this);
        View view = LayoutInflater.from(ProductInfoViews.this).inflate(R.layout.editproductinfolayout, EditAD);

        Button EditYes = view.findViewById(R.id.EditYes);
        Button EditNo = view.findViewById(R.id.EditNo);


        builderedit.setView(view);
        final AlertDialog alertDialog = builderedit.create();

        EditNo.findViewById(R.id.EditNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Toast.makeText(ProductInfoViews.this,"Testing",Toast.LENGTH_SHORT).show();
            }
        });

        EditYes.findViewById(R.id.EditYes).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(ProductInfoViews.this,"Testing Yes",Toast.LENGTH_SHORT).show();
                prodinfo.setEnabled(true);
                quaninfo.setEnabled(true);
                costinfo.setEnabled(true);
                priceinfo.setEnabled(true);
                alertDialog.dismiss();
            };
        });

        if (alertDialog.getWindow()!=null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        }
        alertDialog.show();
    }


}
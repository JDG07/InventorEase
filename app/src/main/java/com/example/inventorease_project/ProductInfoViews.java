package com.example.inventorease_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

        int originalQuantity = intent.getIntExtra("quantity", 0);

        prodinfo.setText(productName);
        quaninfo.setText(String.valueOf(quantity));
        costinfo.setText(String.valueOf(cost));
        priceinfo.setText(String.valueOf(price));

        quaninfo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    showQuantityEditDialog(originalQuantity);
                }
            }
        });
        quaninfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                // No action needed before text changes
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // No action needed during text changes
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Check if the entered quantity is greater than the original quantity
                String editedQuantityStr = editable.toString().trim();
                if (!editedQuantityStr.isEmpty()) {
                    int editedQuantity = Integer.parseInt(editedQuantityStr);

                    if (editedQuantity > originalQuantity) {
                        quaninfo.setError("Quantity should not exceed the original quantity");
                        quaninfo.setText(String.valueOf(originalQuantity));
                    }
                }
            }
        });

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
                showDeleteConfirmationDialog(productName);

            }
        });

        backplist.setOnClickListener(view -> {
            Intent intent2 = new Intent(ProductInfoViews.this, ProductListViews.class);
            startActivity(intent2);
            Toast.makeText(this, "Going Back", Toast.LENGTH_SHORT).show();
        });

    }
    private void saveChanges(String originalProductName) {
        // Retrieve edited values from EditText fields
        String editedProductName = prodinfo.getText().toString().trim();
        String editedQuantityStr = quaninfo.getText().toString().trim();
        String editedCostStr = costinfo.getText().toString().trim();
        String editedPriceStr = priceinfo.getText().toString().trim();

        // Validate fields
        if (validateFields(editedProductName, editedQuantityStr, editedCostStr, editedPriceStr)) {
            int editedQuantity = Integer.parseInt(editedQuantityStr);
            int editedCost = Integer.parseInt(editedCostStr);
            int editedPrice = Integer.parseInt(editedPriceStr);

            // Find the corresponding Product object in the productList using a for loop
            for (int i = 0; i < AddItemViews.productList.size(); i++) {
                ProductList product = AddItemViews.productList.get(i);

                if (product.getPname().equals(originalProductName)) {
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
        } else {
            // Show a message if validation fails
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean validateFields(String editedProductName, String editedQuantityStr, String editedCostStr, String editedPriceStr) {
        // Check if any of the fields is empty
        return !editedProductName.isEmpty() &&
                !editedQuantityStr.isEmpty() &&
                !editedCostStr.isEmpty() &&
                !editedPriceStr.isEmpty();
    }
    private void deleteProduct(String productNameToDelete){

        for (int i = 0; i < AddItemViews.productList.size(); i++) {
            ProductList product = AddItemViews.productList.get(i);

            if(product.getPname().equals(productNameToDelete)){

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
            }
        });

        if (alertDialog.getWindow()!=null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    private void showQuantityEditDialog(final int originalQuantity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Quantity");
        builder.setMessage("Are you sure you want to edit the quantity?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User is sure, allow editing
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User canceled, reset quantity to the original value
                quaninfo.setText(String.valueOf(originalQuantity));
                quaninfo.clearFocus();
            }
        });
        builder.show();
    }
    private void showDeleteConfirmationDialog(final String productNameToDelete) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Product");
        builder.setMessage("Are you sure you want to delete this product?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User confirmed, proceed with deletion
                deleteProduct(productNameToDelete);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User canceled, do nothing
            }
        });
        builder.show();
    }
}
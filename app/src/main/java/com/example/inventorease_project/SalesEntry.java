package com.example.inventorease_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class SalesEntry extends AppCompatActivity {
Dialog CDSales;
    public static EditText productsalesET;
    public static EditText quantitysoldET;
    public static EditText totalsoldET;
    public static EditText pricesoldET;
    public static EditText remainingstockET;

    public static Button addsales,backsales,checkoutbtn;

    public static TextView totalpriceTV;

    public static ArrayList <SalesArrayClass> salesarray = new ArrayList<>();
    public static ProductListAdapter salesadapter;


    public static AutoCompleteTextView autoCompleteTextView;
    final static int REQUEST_CODE = 1122;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_sales_entry);
        askPermissions();

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



                 totalpriceTV = findViewById(R.id.totalpriceTV);
                 checkoutbtn = findViewById(R.id.checkoutbtn);

                checkoutbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateQuantityInProductList();
                    }
                });


                addsales.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       addSales();
                       createPDF();

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
        String prodsales = productsalesET.getText().toString();
        String quansales = quantitysoldET.getText().toString();
        String pricesales = pricesoldET.getText().toString();
        String totsales = totalsoldET.getText().toString();

        SalesArrayClass salesEntry = new SalesArrayClass(prodsales,quansales,pricesales,totsales);
        salesarray.add(salesEntry);
        updateTotalPrice();


        productsalesET.getText().clear();
        quantitysoldET.getText().clear();
        pricesoldET.getText().clear();
        totalsoldET.getText().clear();

        CDSales.dismiss();
        autoCompleteTextView.setText("");



        Toast.makeText(this, "Product added successfully", Toast.LENGTH_SHORT).show();

    }
    private void updateTotalPrice() {
        int sum = 0;

        for (SalesArrayClass entry : salesarray) {
            try {
                int total = Integer.parseInt(entry.getSalestotal());
                sum += total;
            } catch (NumberFormatException e) {
                // Handle the case where entry.getSalestotal() is not a valid integer
                Log.e("UpdateTotalPrice", "Invalid integer format in sales total: " + entry.getSalestotal());
            }
        }

        // Update totalpriceTV
        totalpriceTV.setText(String.valueOf(sum));
    }
    private void updateQuantityInProductList(){
        for (SalesArrayClass entry : salesarray){
            String productName = entry.getSalesproduct();
            int soldQuantity = Integer.parseInt(entry.getSalesqty());

            for (ProductList product : AddItemViews.productList) {
                if (product.getPname().equals(productName)){

                    int remainingQuantity = product.getQuantity() - soldQuantity;
                    product.setQuantity(remainingQuantity);
                    break;
                }
            }
        }
    }





    private void askPermissions (){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
    }
    private void createPDF(){
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1080,1920,1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(42);

        String receipttxt = "SALES";
        float x = 500;
        float y = 800;

        canvas.drawText(receipttxt,x,y,paint);
        document.finishPage(page);

        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String filename = "examplereceipt.pdf";
        File file = new File(downloadsDir,filename);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            document.writeTo(fos);
            document.close();
            fos.close();
            Toast.makeText(this, "Sales Receipt", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Log.d("mylog","error while writing" + e.toString());
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

private void convertXMLtoPDF (){
        View view = LayoutInflater.from (this).inflate(R.layout.salesreceiptlayout,null);
        DisplayMetrics displayMetrics = new DisplayMetrics();

        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.R){
        this.getDisplay().getRealMetrics(displayMetrics);

        }
        else this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.measure(View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels,View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(displayMetrics.heightPixels,View.MeasureSpec.EXACTLY));


        PdfDocument document = new PdfDocument();

        int viewWidth = view.getMeasuredWidth();
        int viewHeight = view.getMeasuredHeight();
        Log.d("mylog","width"+viewWidth);
    Log.d("mylog","height"+viewHeight);

        PdfDocument.PageInfo pageinfo = new PdfDocument.PageInfo.Builder(viewWidth,viewHeight,1).create();
        PdfDocument.Page page = document.startPage(pageinfo);

        //
    Canvas canvas = page.getCanvas();
    view.draw(canvas);

    //
    document.finishPage(page);

    //
    File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    String filename = "examplereceipt.pdf";
    File file = new File(downloadsDir,filename);
    try {
        FileOutputStream fos = new FileOutputStream(file);
        document.writeTo(fos);
        document.close();
        fos.close();
        Toast.makeText(this, "Sales Receipt", Toast.LENGTH_SHORT).show();
    } catch (FileNotFoundException e) {
        Log.d("mylog","error while writing" + e.toString());
        throw new RuntimeException(e);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }

}
}
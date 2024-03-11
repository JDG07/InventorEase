package com.example.inventorease_project;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Chart extends AppCompatActivity {
    private ArrayList<BarEntry> barEntries;
    private static String[] barLabels = {"In Stock", "Low Stock", "No Stock"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        BarChart barChart = findViewById(R.id.barchart);

        BarDataSet barDataSet = new BarDataSet(getBarEntries(), "Stock Status");
        barDataSet.setColors(Color.GREEN, Color.YELLOW, Color.RED);
        barDataSet.setValueTextSize(24f);

        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        // Settings for the chart appearance
        barData.setBarWidth(0.5f);
        barChart.getDescription().setEnabled(false);

        // Hide Y-axis labels
        barChart.getAxisLeft().setDrawLabels(false);
        barChart.getAxisRight().setDrawLabels(false);

        // Customize X-axis labels
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter());
        xAxis.setTextSize(24f);


        barChart.invalidate();
    }

    private ArrayList<BarEntry> getBarEntries() {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0, getInStockCount()));
        barEntries.add(new BarEntry(1, getLowStockCount()));
        barEntries.add(new BarEntry(2, getNoStockCount()));
        return barEntries;
    }

    private int getInStockCount() {
        // Get the product list from AddItemViews
        ArrayList<ProductList> productList = AddItemViews.productList;
        int count = 0;

        for (ProductList product : productList) {
            if (product.getQuantity() > 5) {
                count++;
            }
        }

        return count;
    }

    private int getLowStockCount() {
        // Get the product list from AddItemViews
        ArrayList<ProductList> productList = AddItemViews.productList;
        int count = 0;

        for (ProductList product : productList) {
            if (product.getQuantity() > 0 && product.getQuantity() <= 5) {
                count++;
            }
        }

        return count;
    }

    private int getNoStockCount() {

        ArrayList<ProductList> productList = AddItemViews.productList;
        int count = 0;

        for (ProductList product : productList) {
            if (product.getQuantity() == 0) {
                count++;
            }
        }

        return count;
    }

    private static class IndexAxisValueFormatter extends ValueFormatter {
        @Override
        public String getAxisLabel(float value, AxisBase axis) {

            int index = (int) value;
            if (index >= 0 && index < barLabels.length) {
                return barLabels[index];
            } else {
                return "";
        }
    }
}
    }
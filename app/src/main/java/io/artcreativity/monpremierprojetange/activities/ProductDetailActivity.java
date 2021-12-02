package io.artcreativity.monpremierprojetange.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.MatrixCursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.artcreativity.monpremierprojet.R;
import io.artcreativity.monpremierprojetange.entities.Product;
import io.artcreativity.monpremierprojetange.entities.Product;

public class ProductDetailActivity extends AppCompatActivity {

    private List<String> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Product product = (Product) getIntent().getSerializableExtra("MY_PROD");



        list.add("Name : "+product.name );
        list.add("Description : "+product.description );
        list.add("Price : "+product.price );
        list.add("Alert Quantity : "+product.alertQuantity);
        list.add("Name : "+product.name);

        ListView lv = (ListView) findViewById(R.id.lv);
        ListAdapter adapter=new ArrayAdapter<String>(this,R.layout.list,list);
        lv.setAdapter(adapter);

    }
}
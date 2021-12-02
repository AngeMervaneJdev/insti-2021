package io.artcreativity.monpremierprojetange.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.artcreativity.monpremierprojet.R;
import io.artcreativity.monpremierprojetange.dao.DataBaseRoom;
import io.artcreativity.monpremierprojetange.adapters.ProductAdapter;
import io.artcreativity.monpremierprojetange.dao.ProductDao;
import io.artcreativity.monpremierprojetange.dao.ProductRoomDao;
import io.artcreativity.monpremierprojet.databinding.ActivityProductBinding;
import io.artcreativity.monpremierprojetange.entities.Product;
import io.artcreativity.monpremierprojetange.webservices.ProductWebService;

public class ProductActivity extends AppCompatActivity {

    private ActivityProductBinding binding;
    private List<Product> products = new ArrayList<>();
    private ProductAdapter productAdapter;
    final static int MAIN_CALL = 120;
    private ProductRoomDao productRoomDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        ProductDao productDao = new ProductDao(this);
        productRoomDao = DataBaseRoom.getInstance(getApplicationContext()).productRoomDao();
        generateProducts();
        buildCustomAdapter();
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductActivity.this, MainActivity.class);
                startActivityIfNeeded(intent, MAIN_CALL);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==MAIN_CALL) {
            if(resultCode== Activity.RESULT_OK) {

                Product product = (Product) data.getSerializableExtra("MY_ED_PROD");
                if (product!=null){
                    Log.d("TAG", "onActivityResult: "+products.indexOf(product));
                    Toast.makeText(getApplicationContext(),"Mise à jour avec success",Toast.LENGTH_LONG).show();
                    products.set(products.indexOf(product),product);
                    productAdapter.notifyDataSetChanged();
                }

                else {
                    product = (Product) data.getSerializableExtra("MY_PROD");
                    products.add(product);
                    productAdapter.notifyDataSetChanged();

                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void buildCustomAdapter() {
        productAdapter = new ProductAdapter(this, products);
        binding.ourListView.setAdapter(productAdapter);
        binding.ourListView.setOnItemClickListener((adapterView, view, position, id) -> {

            Intent intent = new Intent(getApplicationContext(), ProductDetailActivity.class);
            intent.putExtra("MY_PROD",products.get(position));
            startActivity(intent);
        });
        binding.ourListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                AlertDialog.Builder alertDialog= new AlertDialog.Builder(ProductActivity.this);
                View view1= LayoutInflater.from(getApplicationContext()).inflate(R.layout.alert,null,false);
                Button delete=view1.findViewById(R.id.delete);
                Button edit =view1.findViewById(R.id.edit);
                alertDialog.setView(view1);
                alertDialog.setTitle("Que voulez vous faire ?");
                AlertDialog alertDialog1= alertDialog.create();
                alertDialog1.setCancelable(true);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                    productRoomDao.delete(products.get(position));
                                    ProductWebService service=new ProductWebService();
                                    service.deleteProduct(products.get(position));
                                    products.remove(position);

                            }
                        }).start();
                        Log.d("" +
                                "", "onClick: ++++555555555555555");
                        productAdapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(),"Produit supprimé avec succes ",Toast.LENGTH_LONG).show();
                        alertDialog1.cancel();
                    }
                });
                edit.setOnClickListener(v -> {
                     Intent intent = new Intent(ProductActivity.this, MainActivity.class);

                     intent.putExtra("MY_ED_PROD",products.get(position));
                     alertDialog1.cancel();
                    startActivityIfNeeded(intent, MAIN_CALL);
                });
                alertDialog1.show();
                Toast.makeText(getApplicationContext(), "It's done", Toast.LENGTH_SHORT).show();
                return true;
            }
        });



    }

    private void generateProducts() {

        Thread thread = new Thread(new Runnable() {
            final List<Product> localProducts = new ArrayList<>();
            @Override
            public void run() {
                localProducts.addAll(productRoomDao.findAll());
                if(localProducts.isEmpty()) {
                    ProductWebService service=new ProductWebService();

                    productRoomDao.insert(new Product("Galaxy S21", "Samsung Galaxy S21", 800000, 100, 10));
                    productRoomDao.insert(new Product("Galaxy Note 10", "Samsung Galaxy Note 10", 800000, 100, 10));
                    productRoomDao.insert(new Product("Redmi S11", "Xiaomi Redmi S11", 300000, 100, 10));
                    productRoomDao.insert(new Product("Galaxy S21", "Samsung Galaxy S21", 800000, 100, 10));
                    productRoomDao.insert(new Product("Galaxy S21", "Samsung Galaxy S21", 800000, 100, 10));
                    productRoomDao.insert(new Product("Galaxy S21", "Samsung Galaxy S21", 800000, 100, 10));
                    productRoomDao.insert(new Product("Galaxy S21", "Samsung Galaxy S21", 800000, 100, 10));

                    service.createProduct(new Product("Galaxy S21", "Samsung Galaxy S21", 800000, 100, 10));
                    service.createProduct(new Product("Galaxy Note 10", "Samsung Galaxy Note 10", 800000, 100, 10));
                    service.createProduct(new Product("Redmi S11", "Xiaomi Redmi S11", 300000, 100, 10));
                    service.createProduct(new Product("Galaxy S21", "Samsung Galaxy S21", 800000, 100, 10));
                    service.createProduct(new Product("Galaxy S21", "Samsung Galaxy S21", 800000, 100, 10));


                    localProducts.addAll(productRoomDao.findAll());
                }
                runOnUiThread(()->{
                    products.addAll(localProducts);
                });
            }
        });
        thread.start();


    }
}

package io.artcreativity.monpremierprojet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import io.artcreativity.monpremierprojet.entities.Product;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getCanonicalName();

    private TextInputEditText designationEditText;
    private TextInputEditText descriptionEditText;
    private TextInputEditText priceEditText;
    private TextInputEditText quantityInStockEditText;
    private TextInputEditText alertQuantityEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        designationEditText = findViewById(R.id.name);
        descriptionEditText = findViewById(R.id.description);
        priceEditText = findViewById(R.id.price);
        quantityInStockEditText = findViewById(R.id.quantity_in_stock);
        alertQuantityEditText = findViewById(R.id.alert_quantity);
    }

    public void saveProduct(View view) {
        Log.d(TAG, "saveProduct: ");
        Product product = new Product();
        product.name = designationEditText.getText().toString();
        product.description = descriptionEditText.getText().toString();
        product.price = Double.parseDouble(priceEditText.getText().toString());
        product.quantityInStock = Double.parseDouble(quantityInStockEditText.getText().toString());
        product.alertQuantity = Double.parseDouble(alertQuantityEditText.getText().toString());
        Log.e(TAG, "saveProduct: " + product);
        Toast.makeText(getApplicationContext(), "J'ai clique", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
        intent.putExtra("MY_PROD", product);
        startActivity(intent);
    }
}
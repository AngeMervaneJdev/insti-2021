
package io.artcreativity.monpremierprojetange.activities;

        import androidx.appcompat.app.AppCompatActivity;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.View;
        import android.widget.Toast;

        import com.google.android.material.textfield.TextInputEditText;
        import com.google.android.material.textfield.TextInputLayout;

        import java.util.Collection;
        import java.util.List;

        import io.artcreativity.monpremierprojet.R;
        import io.artcreativity.monpremierprojetange.dao.DataBaseRoom;
        import io.artcreativity.monpremierprojetange.dao.ProductDao;
        import io.artcreativity.monpremierprojetange.dao.ProductRoomDao;
        import io.artcreativity.monpremierprojetange.entities.Product;
        import io.artcreativity.monpremierprojetange.webservices.ProductWebService;

public class MainActivity extends AppCompatActivity{

    private final String TAG = MainActivity.class.getCanonicalName();

    private TextInputEditText designationEditText;
    private TextInputEditText descriptionEditText;
    private TextInputEditText priceEditText;
    private TextInputEditText quantityInStockEditText;
    private TextInputEditText alertQuantityEditText;
    private Product myProduct;
    private ProductRoomDao productRoomDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        productRoomDao = DataBaseRoom.getInstance(getApplicationContext()).productRoomDao();

        setContentView(R.layout.activity_main);
        designationEditText = findViewById(R.id.name);
        descriptionEditText = findViewById(R.id.description);
        priceEditText = findViewById(R.id.price);

        quantityInStockEditText = findViewById(R.id.quantity_in_stock);
        alertQuantityEditText = findViewById(R.id.alert_quantity);
        myProduct= (Product) getIntent().getSerializableExtra("MY_ED_PROD");
        Toast.makeText(getApplicationContext(), "Ok get69955", Toast.LENGTH_SHORT).show();
       if(myProduct!=null){
           Toast.makeText(getApplicationContext(), "Ok get", Toast.LENGTH_SHORT).show();
           edit(myProduct);
       }

    }



    private void edit(Product product){
        designationEditText.setText(product.name);
        descriptionEditText.setText(product.description);
        priceEditText.setText(String.format("%s", product.price));
        quantityInStockEditText.setText(String.format("%s", product.quantityInStock));
        alertQuantityEditText.setText(String.format("%s", product.alertQuantity));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public boolean is_inputs_valids()     {
       boolean returned=true;
        if (designationEditText.getText().toString().equals("")) {
            designationEditText.setError("Ne doit pas etre vide");
            returned=false;
        }
        if (descriptionEditText.getText().toString().equals("")) {
            descriptionEditText.setError("Ne doit pas etre vide");
            returned=false;
        }
        if (priceEditText.getText().toString().equals("")) {
            designationEditText.setError("Ne doit pas etre vide");
            returned=false;
        }
        if (quantityInStockEditText.getText().toString().equals("")) {
            quantityInStockEditText.setError("Ne doit pas etre vide");
            returned=false;
        }
        if (quantityInStockEditText.getText().toString().equals("")) {
            quantityInStockEditText.setError("Ne doit pas etre vide");
            returned=false;
        }
        return returned;
    }

    public void saveProduct(View view) {
        Toast.makeText(getApplicationContext(), "GOOD", Toast.LENGTH_SHORT).show();
        if(is_inputs_valids()){

            Intent intent = getIntent();

            Log.d("TAG", "saveProduct: ");
            Product product = new Product(
                    designationEditText.getText().toString(),
                    descriptionEditText.getText().toString(),
                    Double.parseDouble(priceEditText.getText().toString()),
                    Double.parseDouble(quantityInStockEditText.getText().toString()),
                    Double.parseDouble(alertQuantityEditText.getText().toString())
            );

            if (myProduct!=null){
                product.id=myProduct.id;
                intent.putExtra("MY_ED_PROD",product);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        productRoomDao.update(product);

                    }
                }).start();
            }else{
                Toast.makeText(getApplicationContext(), "This is the good", Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ProductWebService productWebService = new ProductWebService();
                        Product save = productWebService.createProduct(product);
//                    System.out.println(save);
                        System.out.println("save :: " + save);
                        productRoomDao.insert(product);
                    }
                }).start();
                Log.e(TAG, "saveProduct: " + product);
                intent.putExtra("MY_PROD", product);
            }
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }
}


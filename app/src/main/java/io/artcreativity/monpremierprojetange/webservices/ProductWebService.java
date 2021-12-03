package io.artcreativity.monpremierprojetange.webservices;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.artcreativity.monpremierprojetange.entities.Product;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProductWebService {
    OkHttpClient httpClient = new OkHttpClient();
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    String baseUrl = "http://192.168.43.131:8080/api/products";

    public Product createProduct(Product product) {
        RequestBody body = RequestBody.create(gson.toJson(product),
                MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(baseUrl)
                .post(body)
                .build();
        Log.d("TAG", "createProduct: "+request);
        try (Response response = httpClient.newCall(request).execute()) {
            return gson.fromJson(response.body().string(), Product.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<Product> getProducts() {
        Request request = new Request.Builder()
                .url(baseUrl)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            return gson.fromJson(response.body().string(),
                    new TypeToken<List<Product>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();

            return new ArrayList<>();
        }
    }



    public void deleteProduct(Product product){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(baseUrl+'/'+product.serverId).delete().build();

        try (Response response = client.newCall(request).execute()) {
            response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Product updateProduct(Product product) {
        RequestBody body = RequestBody.create(gson.toJson(product),
                MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(baseUrl+"/"+product.serverId)
                .put(body)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            return gson.fromJson(response.body().string(), Product.class);
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }



}

package io.artcreativity.monpremierprojetange.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.artcreativity.monpremierprojetange.entities.Product;

@Dao
public interface ProductRoomDao {
    @Query("SELECT * FROM product")
    List<Product> findAll();

    @Query("SELECT * FROM product WHERE id IN (:userIds)")
    List<Product> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM product WHERE name LIKE :search AND " +
            "description LIKE :search")
    List<Product> findByName(String search);

    @Update
    void update(Product product);

    @Insert
    void insertAll(Product... products);

    @Insert
    void insert(Product product);

    @Delete
    void delete(Product product);
}
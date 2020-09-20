package com.example.a5.room.product;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;



import java.util.List;

@Dao
public interface ProductDao {


    @Query("SELECT * FROM product")
    List<Product> getAll();

    @Query("SELECT * FROM product WHERE uid IN (:productIds)")
    List<Product> loadAllByIds(int[] productIds);



    @Query("SELECT * FROM product WHERE uid LIKE :productId LIMIT 1")
    Product findById(int productId);

    @Query("SELECT * FROM product WHERE title LIKE :first AND " +
            "description LIKE :last LIMIT 1")
    Product findByName(String first, String last);


    @Insert
     void insert(Product product);

    @Insert
    void insertAll(Product... products);

    @Delete
    void delete(Product product);


    @Query("UPDATE product SET ratting=:ratting WHERE uid = :id")
    void updateRatting(int id,Double ratting);


}






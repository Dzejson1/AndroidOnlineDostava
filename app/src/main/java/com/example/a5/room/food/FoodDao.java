package com.example.a5.room.food;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FoodDao {


    @Query("SELECT * FROM food")
    List<Food> getAll();
//
//    @Query("SELECT * FROM cart WHERE uid IN (:cartIds)")
//    List<Cart> loadAllByIds(int[] cartIds);
//
//
//
//    @Query("SELECT * FROM cart WHERE uid LIKE :cartId LIMIT 1")
//    Cart findById(int cartId);

//    @Query("SELECT * FROM product WHERE title LIKE :first AND " +
//            "description LIKE :last LIMIT 1")
//    Product findByName(String first, String last);


//    @Insert
//    void insert(Cart cart);

    @Insert
    void insertAll(Food... foods);

    @Delete
    void delete(Food food);

    @Query("DELETE FROM Food")
    void deleteAll();

    // and totalPrice=price*:quantity
    //id je position
   // @Query("UPDATE food SET quantity=:quantity , totalPrice=price*:quantity WHERE uid = :id")
   //void updateQuantity(int id,Integer quantity);


}

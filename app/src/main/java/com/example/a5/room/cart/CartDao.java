package com.example.a5.room.cart;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CartDao {


    @Query("SELECT * FROM cart")
    List<Cart> getAll();

    @Insert
    void insertAll(Cart... carts);

    @Delete
    void delete(Cart cart);

    @Query("DELETE FROM Cart")
    void deleteAll();

   // and totalPrice=price*:quantity
    //id je position
    @Query("UPDATE cart SET quantity=:quantity , totalPrice=price*:quantity WHERE uid = :id")
    void updateQuantity(int id,Integer quantity);


}

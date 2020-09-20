package com.example.a5.room.cart;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Cart.class}, version = 1, exportSchema = false)
public abstract class CartDB extends RoomDatabase {
    public abstract CartDao cartDao();
}
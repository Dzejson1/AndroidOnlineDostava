package com.example.a5.room.product;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Product.class}, version = 1, exportSchema = false)
public abstract class ProductDB  extends RoomDatabase {
    public abstract ProductDao productDao();
}

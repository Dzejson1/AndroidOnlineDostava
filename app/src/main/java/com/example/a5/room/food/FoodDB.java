package com.example.a5.room.food;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Food.class}, version = 1, exportSchema = false)
public abstract class FoodDB extends RoomDatabase {
    public abstract FoodDao foodDao();
}
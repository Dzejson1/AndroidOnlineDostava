package com.example.a5.room.user;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDB  extends RoomDatabase {
    public abstract UserDao userDao();
}




///*
//        * The Room database that contains the Users table
//        */
//@Database(entities = {User.class}, version = 1)
//public abstract class UserDB  extends RoomDatabase {
//
//    private static volatile UserDB INSTANCE;
//
//    public abstract UserDao userDao();
//
//    public static UserDB getInstance(Context context) {
//        if (INSTANCE == null) {
//            synchronized (UserDB.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                            UserDB.class, "Sample.db")
//                            .build();
//                }
//            }
//        }
//        return INSTANCE;
//    }
//}
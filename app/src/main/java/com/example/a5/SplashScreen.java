package com.example.a5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;


import com.example.a5.room.cart.Cart;
import com.example.a5.room.cart.CartDB;
import com.example.a5.room.cart.CartDao;
import com.example.a5.room.food.FoodDB;
import com.example.a5.room.food.FoodDao;
import com.example.a5.room.product.Product;
import com.example.a5.room.product.ProductDB;
import com.example.a5.room.product.ProductDao;
import com.example.a5.room.user.User;
import com.example.a5.room.user.UserDB;
import com.example.a5.room.user.UserDao;


public class SplashScreen extends AppCompatActivity {


    public static UserDB mUserDB;
    public static UserDao userDao;

    public static ProductDB mProductDB;
    public static ProductDao productDao;



    public static CartDB mCartDB;
    public static CartDao cartDao;


    public static FoodDB mFoodDB;
    public static FoodDao foodDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        mFoodDB= Room.databaseBuilder(getApplicationContext(),
                FoodDB.class, "database-nameFood").build();

        foodDao = mFoodDB.foodDao();


        mProductDB = Room.databaseBuilder(getApplicationContext(),
                ProductDB.class, "database-nameProduct").build();

        productDao = mProductDB.productDao();


        mCartDB = Room.databaseBuilder(getApplicationContext(),
                CartDB.class, "database-nameCart").build();

        cartDao = mCartDB.cartDao();



        mUserDB = Room.databaseBuilder(getApplicationContext(),
                UserDB.class, "database-nameUser").build();

        userDao = mUserDB.userDao();



        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);


            if (!preferences.getBoolean("firstTime", false)) {

                new DatabaseAsyncProduct().execute();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("firstTime", true);
                editor.apply();
        }


        Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

    }


    private static class DatabaseAsyncProduct extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {


            productDao = mProductDB.productDao();

            Product p1=new Product();
            p1.setTitle("Pizza");
            p1.setDescription("Spicy Chiken Pizza");
            p1.setPrice(310.00);
            p1.setRatting(5.0);
            p1.setPhoto(1);


            Product p2=new Product();
            p2.setTitle("Burger");
            p2.setDescription("Beef Burger       ");
            p2.setPrice(350.00);
            p2.setRatting(5.0);
            p2.setPhoto(2);

            Product p3=new Product();
            p3.setTitle("Pizza");
            p3.setDescription("Chiken Pizza      ");
            p3.setPrice(250.00);
            p3.setRatting(5.0);
            p3.setPhoto(3);

            Product p4=new Product();
            p4.setTitle("Burger");
            p4.setDescription("Chicken Burger    ");
            p4.setPrice(350.00);
            p4.setRatting(5.0);
            p4.setPhoto(4);

            Product p5=new Product();
            p5.setTitle("Burger");
            p5.setDescription("Fish Burger       ");
            p5.setPrice(310.00);
            p5.setRatting(5.0);
            p5.setPhoto(5);


            Product p6=new Product();
            p6.setTitle("Mango");
            p6.setDescription("Mango Juice       ");
            p6.setPrice(310.00);
            p6.setRatting(5.0);
            p6.setPhoto(6);

            productDao.insertAll(p1,p2,p3,p4,p5,p6);

            return null;
        }
    }

}





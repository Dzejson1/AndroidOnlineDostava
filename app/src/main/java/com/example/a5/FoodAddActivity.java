package com.example.a5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.a5.room.cart.Cart;
import com.example.a5.room.food.Food;
import com.example.a5.room.food.FoodDao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class FoodAddActivity extends AppCompatActivity {
    EditText description;
    Button addFood;
    FoodDao foodDao;
    Button pictureButton;
    ImageView picture;
    static final int REQUEST_IMAGE_CAPTURE = 1002;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        foodDao=SplashScreen.foodDao;
        setContentView(R.layout.activity_food_add);
        addFood = (Button)findViewById(R.id.btnAddFood);
        description = (EditText)findViewById(R.id.food_description);
        addFood.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 String opis = description.getText().toString();
                                                 Food f=new Food();
                                                 f.setDescription(opis);

                                                 try {
                                                     insertInFood(f);
                                                     finish();
                                                 } catch (ExecutionException e) {
                                                     e.printStackTrace();
                                                 } catch (InterruptedException e) {
                                                     e.printStackTrace();
                                                 }
                                             }
                                         }
        );

    }



    private Void insertInFood(final Food f) throws ExecutionException, InterruptedException {

        return new AsyncTask<Food, Void, Void>() {


            @Override
            protected Void doInBackground(Food... carts) {

                // Double p1=Double.valueOf((double) params[1]);

                //cartDao = mCartDB.cartDao();

                foodDao.insertAll(f);

                Log.i("pop", "u backin inser cart");

                return null;

            }


            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);

            }

        }.execute().get();
        //execute(id)

    }
}

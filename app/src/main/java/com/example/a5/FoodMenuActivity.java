package com.example.a5;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import com.example.a5.room.food.Food;
import com.example.a5.room.food.FoodDB;
import com.example.a5.room.food.FoodDao;
import com.example.a5.room.product.Product;

import java.util.ArrayList;
import java.util.List;

public class FoodMenuActivity extends AppCompatActivity {

    public List<Food> mList = new ArrayList<>();

    public static ToDoAdapter_2 adapter;

    public static FoodDao foodDao;
    public static FoodDB mfoodDB;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_menu);

        foodDao=SplashScreen.foodDao;
        mfoodDB=SplashScreen.mFoodDB;

        listView = (ListView) findViewById(R.id.list_view);
        new QueryDB().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class QueryDB extends AsyncTask<Void, Void, Void> {


        private List<Food> newList = new ArrayList<>();

        @Override
        protected Void doInBackground(Void... voids) {
            newList = foodDao.getAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mList = newList;

            adapter = new ToDoAdapter_2(mList);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }

    }
}

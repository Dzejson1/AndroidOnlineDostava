package com.example.a5;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a5.room.cart.Cart;
import com.example.a5.room.cart.CartDB;
import com.example.a5.room.cart.CartDao;
import com.example.a5.room.product.Product;
import com.example.a5.room.product.ProductDB;
import com.example.a5.room.product.ProductDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MenuFragment_1 extends ListFragment {

    public static List<Cart> mList = new ArrayList<>();

    public static ToDoAdapter_1 adapter;

    public static ProductDB mProductDB;
    public static ProductDao productDao;

    private static CartDB mCartDB;
    private static CartDao cartDao;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProductDB = SplashScreen.mProductDB;
        productDao = SplashScreen.productDao;

        mCartDB = SplashScreen.mCartDB;
        cartDao = SplashScreen.cartDao;

        new QueryDB().execute();

    }


    @SuppressLint("StaticFieldLeak")
    private class QueryDB extends AsyncTask<Void, Void, Void> {


        private List<Cart> newList = new ArrayList<>();

        @Override
        protected Void doInBackground(Void... voids) {

            newList = cartDao.getAll();


            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mList = newList;

            adapter = new ToDoAdapter_1(getActivity(), mList);
            setListAdapter(adapter);
            adapter.notifyDataSetChanged();

        }

    }


}


package com.example.a5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.ListFragment;
import androidx.room.Room;
//import androidx.fragment.app.ListFragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.a5.room.cart.CartDB;
import com.example.a5.room.cart.CartDao;
import com.example.a5.room.product.ProductDB;
import com.example.a5.room.product.ProductDao;
import com.example.a5.room.product.Product;
import com.example.a5.room.product.ProductDB;
import com.example.a5.room.product.ProductDao;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MenuFragment extends ListFragment {

   OnHeadlineSelectedListener mCallback;

    public List<Product> mList = new ArrayList<>();

    public static  ToDoAdapter adapter;

   // public static UserDB mUserDB;
   // public static UserDao userDao;

    public static ProductDB mProductDB;
    public static ProductDao productDao;

    private static CartDB mCartDB;
    private static CartDao cartDao;



    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnHeadlineSelectedListener {
        /** Called by HeadlinesFragment when a list item is selected */
        public void onArticleSelected(int position);
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProductDB=SplashScreen.mProductDB;
        productDao=SplashScreen.productDao;

        mCartDB=SplashScreen.mCartDB;
        cartDao=SplashScreen.cartDao;

        new QueryDB().execute();

    }

    @Override
    public void onStart() {
        super.onStart();
        //ovo je za tablet da kad se setuje jelo da bude highlight-ovano
        if (getFragmentManager().findFragmentById(R.id.article_fragment) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mCallback.onArticleSelected(position);
        getListView().setItemChecked(position, true);

    }

    @SuppressLint("StaticFieldLeak")
            private class QueryDB extends AsyncTask<Void, Void, Void> {


                private List<Product> newList = new ArrayList<>();

                @Override
                protected Void doInBackground(Void... voids) {
                    newList = productDao.getAll();
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);

                    mList = newList;

                    adapter = new ToDoAdapter(mList);
                    setListAdapter(adapter);
                    adapter.notifyDataSetChanged();

        }

    }


}

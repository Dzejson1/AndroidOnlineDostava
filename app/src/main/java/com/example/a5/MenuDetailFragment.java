package com.example.a5;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.a5.room.cart.Cart;
import com.example.a5.room.cart.CartDB;
import com.example.a5.room.cart.CartDao;
import com.example.a5.room.product.Product;
import com.example.a5.room.product.ProductDB;
import com.example.a5.room.product.ProductDao;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class MenuDetailFragment extends Fragment {

    TextView menuTitleView;
    TextView descriptionView;
    TextView menuCostView;
    TextView totalOrderView;
    TextView totalCostView;
    Button increaseTotalCostButton;
    Button decreaseTotalCostButton;
    Button addToListButton;
    //Button showListButton;

    String menuTitleString;
    String menuDescriptionString;
    String menuCostString;
    double menuCostDouble;
    double totalCostDouble;
    int totalOrder=1;


    RatingBar ratingView;
    double ratingValue;
    static int brojacKliknuo;

    // private static UserDB mUserDB;
    //private static UserDao userDao;

    private static ProductDB mProductDB;
    private static ProductDao productDao;


    private static CartDB mCartDB;
    private static CartDao cartDao;

    static String ARG_POSITION = "position";
    int mCurrentPosition = 0;
    //int mCurrentPosition = -1; da se prvi index u start  ne vidi
    Product product = new Product();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mProductDB = SplashScreen.mProductDB;
        productDao = SplashScreen.productDao;


        mCartDB = SplashScreen.mCartDB;
        cartDao = SplashScreen.cartDao;

        View view = inflater.inflate(R.layout.activity_menu_detail, container, false);


        if (savedInstanceState != null) {

            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
            totalOrder = savedInstanceState.getInt("to");

            try {
                int pos = mCurrentPosition + 1;
                product = fetchTodoById(pos);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


        increaseTotalCostButton = (Button) view.findViewById(R.id.increaseTotalCostButton);
        addToListButton = (Button) view.findViewById(R.id.addToListButton);
        decreaseTotalCostButton = (Button) view.findViewById(R.id.decreaseTotalCostButton);
        menuTitleView = (TextView) view.findViewById(R.id.menu_title);
        descriptionView = (TextView) view.findViewById(R.id.description_text_view);
        totalCostView = (TextView) view.findViewById(R.id.total_cost_text_view);
        totalOrderView = (TextView) view.findViewById(R.id.total_item_number);
        descriptionView = (TextView) view.findViewById(R.id.description_text_view);
        menuCostView = (TextView) view.findViewById(R.id.cost_text_view);
        ratingView = (RatingBar) view.findViewById(R.id.ratingBar);

        increaseTotalCostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseTotalCost();
            }
        });
        decreaseTotalCostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseTotalCost();
            }
        });
        addToListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addToList();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        ratingView.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                ratingBar.setRating(rating);
                try {
                    //Integer iii=
                    updateById(product.getUid(), Double.valueOf((double) rating));
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        return view;

    }


    @Override
    public void onStart() {
        super.onStart();

        //https://stackoverflow.com/questions/5425568/how-to-use-setarguments-and-getarguments-methods-in-fragments

        Bundle args = getArguments();
        if (args != null) {

            if (args.getBoolean("klik")) {
                args.putBoolean("klik", false);
                updateArticleView(args.getInt(ARG_POSITION), true);
            } else {
                updateArticleView(args.getInt(ARG_POSITION), false);
            }

        } else

            updateArticleView(mCurrentPosition, false);

    }


    public void updateArticleView(int position, boolean klik) {

        mCurrentPosition = position;

        try {
            int pos = position + 1;
            product = fetchTodoById(pos);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //ako je na klik

        if (klik) {
            menuCostDouble = totalCostDouble = product.getPrice();
            totalOrder = 1;
            ratingValue = product.getRatting();
            ratingView.setRating((float) ratingValue);
        }

        totalCostDouble = totalOrder * product.getPrice();

        displayUpdate();
    }


    @SuppressLint("StaticFieldLeak")
    private Product fetchTodoById(final int todo_id) throws ExecutionException, InterruptedException {

        return new AsyncTask<Integer, Void, Product>() {

            Product p;

            @Override
            protected Product doInBackground(Integer... params) {
                p = productDao.findById(params[0]);
                return p;

            }


            @Override
            protected void onPostExecute(Product todo) {
                super.onPostExecute(todo);

            }

        }.execute(todo_id).get();

    }



    @SuppressLint("StaticFieldLeak")
    private Void updateById(final Integer id, final Double ratting) throws ExecutionException, InterruptedException {

        return new AsyncTask<Integer, Void, Void>() {


            @Override
            protected Void doInBackground(Integer... params) {

                // Double p1=Double.valueOf((double) params[1]);
                productDao.updateRatting(id, ratting);
                return null;

            }


            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);

            }

        }.execute().get();
        //execute(id)

    }


    //    @SuppressLint("StaticFieldLeak")
    private Void insertInCart(final Cart c) throws ExecutionException, InterruptedException {

        return new AsyncTask<Cart, Void, Void>() {


            @Override
            protected Void doInBackground(Cart... carts) {

                // Double p1=Double.valueOf((double) params[1]);

                //cartDao = mCartDB.cartDao();

                cartDao.insertAll(c);

                Log.i("pop", "u backin inser cart");

                return null;

            }


            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);

            }

        }.execute().get();


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }


    public void increaseTotalCost() {
        totalOrder++;
        totalCostDouble = totalOrder * product.getPrice();
        displayUpdate();
    }

    public void decreaseTotalCost() {
        totalOrder = (--totalOrder < 1) ? 1 : totalOrder;
        totalCostDouble = totalOrder * product.getPrice();
        displayUpdate();
    }

    public void displayUpdate() {

        menuTitleView.setText(product.getTitle());
        menuCostView.setText("CENA :  " + Double.toString(product.getPrice()));
        descriptionView.setText(product.getDescription());
        totalOrderView.setText("Kolicina : " + totalOrder);
        totalCostView.setText("Ukupno    : " + new DecimalFormat("#.##").format(totalCostDouble));

    }


    public void addToList() throws ExecutionException, InterruptedException {

        brojacKliknuo++;

        Cart c = new Cart();

        c.setTitle(product.getTitle());
        c.setDescription(product.getDescription());
        c.setPrice(product.getPrice());


        c.setQuantity(totalOrder);

        c.setTotalPrice(c.getQuantity() * product.getPrice());

        insertInCart(c);

        List<Cart> l= getCart();

    }



    @SuppressLint("StaticFieldLeak")
    private List<Cart> getCart() throws ExecutionException, InterruptedException {

        return new AsyncTask<Void, Void, List<Cart>>() {

            List<Cart> p;

            @Override
            protected List<Cart> doInBackground(Void... params) {
                p = cartDao.getAll();
                return p;

            }


            @Override
            protected void onPostExecute(List<Cart> todo) {
                super.onPostExecute(todo);

            }

        }.execute().get();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putInt(ARG_POSITION, mCurrentPosition);
        outState.putInt("to", totalOrder);

    }

}


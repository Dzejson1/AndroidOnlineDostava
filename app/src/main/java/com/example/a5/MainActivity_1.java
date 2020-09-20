package com.example.a5;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.a5.room.cart.Cart;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_1 extends FragmentActivity {

    public static List<Cart> mList = new ArrayList<>();

    public static TextView txt_itemTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_article_1);

        txt_itemTotal = (TextView) findViewById(R.id.totalCoastCart);

        if (findViewById(R.id.fragment_container_1) != null) {

            MenuFragment_1 firstFragment = new MenuFragment_1();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_1, firstFragment).commit();

            Button addButton = (Button) findViewById(R.id.buttonPaymentAdd);


            addButton.setOnClickListener(new View.OnClickListener() {
                //addButton.
                //
                @Override
                public void onClick(View v) {
                    Log.i("pop", "Entered addButton.onClickListener.onClick");
                    Intent intent = new Intent(MainActivity_1.this, PaymentActivity_1.class);

                    startActivity(intent);

                    txt_itemTotal = (TextView) findViewById(R.id.totalCoastCart);

                }
            });
        } else {

            txt_itemTotal = (TextView) findViewById(R.id.totalCoastCart_1);
        }
    }

}

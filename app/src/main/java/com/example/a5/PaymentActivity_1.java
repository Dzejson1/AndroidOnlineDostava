package com.example.a5;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.a5.room.cart.Cart;
import com.example.a5.room.cart.CartDB;
import com.example.a5.room.cart.CartDao;

import java.util.ArrayList;
import java.util.List;

public class PaymentActivity_1 extends AppCompatActivity {

    Button btnSignUp;

    private static CartDB mCartDB;
    private static CartDao cartDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_1);
        mCartDB=SplashScreen.mCartDB;
        cartDao=SplashScreen.cartDao;
        btnSignUp=(Button)findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                Toast.makeText(getApplicationContext(),"Uspesna kupovina",Toast.LENGTH_SHORT).show();
                new DeleteCart().execute();
               // MenuFragment_1.mList.clear();
                Intent intent=new Intent(PaymentActivity_1.this,MainActivity.class);
                startActivity(intent);
              //  finish();
            }
        });

    }
    @SuppressLint("StaticFieldLeak")
    private class DeleteCart extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            cartDao.deleteAll();
            return null;
        }



        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


        }

    }


}

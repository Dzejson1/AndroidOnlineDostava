package com.example.a5;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a5.room.cart.CartDB;
import com.example.a5.room.cart.CartDao;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment_1 extends Fragment {

            Button btnSignUp;
    private static CartDB mCartDB;
    private static CartDao cartDao;

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                    Bundle savedInstanceState) {
                mCartDB=SplashScreen.mCartDB;
                cartDao=SplashScreen.cartDao;


                View view = inflater.inflate(R.layout.activity_payment_1, container, false);
                btnSignUp=(Button)view.findViewById(R.id.btnSignUp);

                btnSignUp.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick (View v){
                        Toast.makeText(getContext(),"Uspesna kupovina",Toast.LENGTH_SHORT).show();
                        new PaymentFragment_1.DeleteCart().execute();
                        // MenuFragment_1.mList.clear();
                        Intent intent=new Intent(getContext(),MainActivity.class);
                        startActivity(intent);
                        //  finish();
                    }
                });

                return view;
//        TextView textView = new TextView(getActivity());
//        textView.setText(R.string.hello_blank_fragment);
//        return textView;
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

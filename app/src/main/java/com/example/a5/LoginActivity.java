package com.example.a5;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a5.room.cart.Cart;
import com.example.a5.room.product.Product;
import com.example.a5.room.user.User;
import com.example.a5.room.user.UserDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    Button registerButton;

    Button loginButton;

    EditText email;
    EditText password;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userDao=SplashScreen.userDao;
        registerButton=(Button)findViewById(R.id.register_button_log);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_top_to_bottom,R.anim.exit_slide_down);

            }
        });
        loginButton=(Button)findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=(EditText)findViewById(R.id.email_log);
                password=(EditText)findViewById(R.id.password_log);
                User user=new User();
                user.setUserName(email.getText().toString());
                user.setPassword(password.getText().toString());
                try {
                    User u=getUser(user);
                    //Log.i("pop10",u.getUserName()+", "+u.getPassword());
                    if(u==null){
                        Toast.makeText(LoginActivity.this, "Pogresan username ili password" , Toast.LENGTH_LONG).show();
                    }else{
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() > 0){
            getFragmentManager().popBackStack();

        }
        else{
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.exit)
                    .setTitle("Ресторан")
                    .setMessage("Jeste li sigurni da hocete da izadjete iz logovanja")
                    .setPositiveButton("Izadji", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("Odustani", null)
                    .show();
        }

    }
    @SuppressLint("StaticFieldLeak")
    private User getUser(User user) throws ExecutionException, InterruptedException {

        return new AsyncTask<User, Void, User>() {

            User u;

            @Override
            protected User doInBackground(User ...params) {
                u=userDao.findByEmailPassword(params[0].getUserName(),params[0].getPassword());
                return u;

            }


            @Override
            protected void onPostExecute(User todo) {
                super.onPostExecute(todo);

            }

        }.execute(user).get();

    }
}

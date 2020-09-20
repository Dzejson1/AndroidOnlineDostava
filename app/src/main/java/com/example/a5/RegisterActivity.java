package com.example.a5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.a5.room.food.Food;
import com.example.a5.room.user.User;
import com.example.a5.room.user.UserDao;

import java.util.concurrent.ExecutionException;

public class RegisterActivity extends AppCompatActivity {

    Button register_button;
    EditText name;
    EditText email;
    EditText password;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userDao=SplashScreen.userDao;
        register_button=(Button)findViewById(R.id.register_button);
        name=(EditText)findViewById(R.id.name);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);



        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user=new User();
                user.setName(name.getText().toString());
                user.setUserName(email.getText().toString());
                user.setPassword(password.getText().toString());
                try {
                    insertInUser(user);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_top_to_bottom,R.anim.exit_slide_down);

            }
        });

    }

    private Void insertInUser(final User u) throws ExecutionException, InterruptedException {

        return new AsyncTask<User, Void, Void>() {


            @Override
            protected Void doInBackground(User... users) {

                // Double p1=Double.valueOf((double) params[1]);

                //cartDao = mCartDB.cartDao();

                userDao.insertAll(u);

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

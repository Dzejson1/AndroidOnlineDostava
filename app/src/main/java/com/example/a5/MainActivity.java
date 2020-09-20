    package com.example.a5;

    import android.annotation.SuppressLint;
    import android.app.AlertDialog;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.net.Uri;
    import android.os.AsyncTask;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.ListView;
    import android.widget.Toast;
    //import android.widget.Toolbar;
    import androidx.appcompat.app.ActionBarDrawerToggle;
    import androidx.appcompat.widget.Toolbar;
    import androidx.core.view.GravityCompat;
    import androidx.drawerlayout.widget.DrawerLayout;
    import androidx.fragment.app.Fragment;
    import androidx.fragment.app.FragmentActivity;
    import androidx.fragment.app.FragmentTransaction;



    import androidx.appcompat.app.AppCompatActivity;
    import androidx.navigation.NavController;
    import androidx.navigation.Navigation;
    import androidx.navigation.ui.AppBarConfiguration;
    import androidx.navigation.ui.NavigationUI;

    import com.example.a5.room.cart.Cart;
    import com.example.a5.room.cart.CartDB;
    import com.example.a5.room.cart.CartDao;
    import com.example.a5.room.product.Product;
    import com.google.android.material.floatingactionbutton.FloatingActionButton;
    import com.google.android.material.navigation.NavigationView;
    import com.google.android.material.snackbar.Snackbar;


    import java.util.ArrayList;
    import java.util.List;
    import java.util.concurrent.ExecutionException;


    public class MainActivity extends AppCompatActivity
            //FragmentActivity
            implements MenuFragment.OnHeadlineSelectedListener ,
                         NavigationView.OnNavigationItemSelectedListener{
        private static final String TAG = "MainActivity";
        public List<Product> mList = new ArrayList<>();

        public List<Cart>listaC=new ArrayList<>();

        private AppBarConfiguration mAppBarConfiguration;
        public static CartDB mCartDB;
        public static CartDao cartDao;
            @Override
            protected void onCreate (Bundle savedInstanceState){

                super.onCreate(savedInstanceState);

                setContentView(R.layout.activity_main);


                Toolbar toolbar = findViewById(R.id.toolbar);


                setSupportActionBar(toolbar);

                FloatingActionButton fab = findViewById(R.id.fab);

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Create a Snackbar.
                        Snackbar snackbar = Snackbar.make(view, "Posaljite nam komentar.", Snackbar.LENGTH_LONG);

                        // Add a button with the button click listener code in snackbar.
                        snackbar.setAction("Send", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // When snackbar button is clicked.
                                // Toast.makeText(MainActivity.this, "You click the send button in snackbar", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(Intent.ACTION_SEND);
                                intent.setType("text/plain");
                                startActivity(intent);
                            }
                        });

                        // Display the snackbar.
                        snackbar.show();
                    }
                });



                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

//               Ovde pravimo da kada kliknemo dugme dobijemo otvaranje fioke(preklop)

                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                        this,
                        drawer,
                        toolbar,
                        R.string.nav_open_drawer,
                        R.string.nav_close_drawer);

                drawer.addDrawerListener(toggle);
                toggle.syncState();


                //Ovaj deo je kada korisnik pritisne odredjeno dugme sa fioke(recimo logout ili show map...)

                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                navigationView.setNavigationItemSelectedListener(this);

                if (findViewById(R.id.fragment_container) != null) {

                    // Ako bi usli klikom na detalje o hrani, i ako bi recimo rotirali ekran
                    // onda bi se preklopila dva fragmenta, zato ide return
                    if (savedInstanceState != null) {
                                       return;
                }

                MenuFragment firstFragment = new MenuFragment();


                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, firstFragment).commit();

                }

                mCartDB=SplashScreen.mCartDB;
                cartDao=SplashScreen.cartDao;

                Log.i("pop4","uso u onCreate u mainActivity");
        }

        public void onArticleSelected(int position) {

           MenuDetailFragment articleFrag = (MenuDetailFragment)
                    getSupportFragmentManager().findFragmentById(R.id.article_fragment);



            Toast.makeText(MainActivity.this, "You have chosen : " + " "+((Product) MenuFragment.adapter.getItem(position)).getDescription() , Toast.LENGTH_LONG).show();

            if (articleFrag != null) {
                articleFrag.updateArticleView(position,true);

            } else {

                MenuDetailFragment newFragment = new MenuDetailFragment();
                Bundle args = new Bundle();
                args.putInt(MenuDetailFragment.ARG_POSITION, position);
                args.putBoolean("klik", true);
                newFragment.setArguments(args);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        }

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            int id = item.getItemId();
            Fragment fragment = null;
            Intent intent = null;

            switch(id) {
                case R.id.nav_notes:
                        intent = new Intent(this,FoodMenuActivity.class);
                        startActivity(intent);
                        break;
                case R.id.nav_add_notes:
                     intent = new Intent(this, FoodAddActivity.class);
                     startActivity(intent);

                    break;
                case R.id.nav_map:
                    Intent geoIntent = new Intent(
                            android.content.Intent.ACTION_VIEW, Uri
                            .parse("geo:0,0?q= novi+sad+restoran+piknik"));
                    startActivity(geoIntent);
                    break;
                case R.id.nav_logout:
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;



            }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        @Override
        public void onBackPressed() {

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            //Ako smo usli u meni, i hocemo da ga zatvorimo
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);


            } else {
                //Ako smo u nekom fragmentu, vratimo se nazad
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                }
                //Inace izadji iz aplikacije
                else {
                    new AlertDialog.Builder(this)
                            .setIcon(R.drawable.exit)
                            .setTitle("Ресторан")
                            .setMessage("Jeste li sigurni da hocete da izadjete iz aplikacije")
                            .setPositiveButton("Izadji", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .setNegativeButton("Odustani", null)
                            .show();
                }
            }
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.main_menu, menu);
            return super.onCreateOptionsMenu(menu);
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
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();

            Intent intent = null;

            if (id == R.id.cart) {

                try {
                    if(getCart().size()==0) {
                        Toast.makeText(MainActivity.this, "Korpa je prazna", Toast.LENGTH_SHORT).show();
                    }else {
                        intent = new Intent(this, MainActivity_1.class);
                        startActivity(intent);
                    }
                } catch (ExecutionException e) {

                    e.printStackTrace();
                } catch (InterruptedException e) {

                }
            }
            return super.onOptionsItemSelected(item);
        }
    }
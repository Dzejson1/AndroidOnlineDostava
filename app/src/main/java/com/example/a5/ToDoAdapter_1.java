    package com.example.a5;

    import android.content.Context;
    import android.content.Intent;
    import android.os.AsyncTask;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.BaseAdapter;
    import android.widget.Button;
    import android.widget.CheckBox;
    import android.widget.ImageView;
    import android.widget.TextView;

    import com.example.a5.room.cart.Cart;
    import com.example.a5.room.cart.CartDB;
    import com.example.a5.room.cart.CartDao;
    import com.example.a5.room.product.Product;

    import java.util.List;
    import java.util.concurrent.ExecutionException;

    public class ToDoAdapter_1 extends BaseAdapter {

        private static CartDB mCartDB;

        private static CartDao cartDao;

        private List<Cart> data;
        Context  context;

        public ToDoAdapter_1(Context c, List<Cart> data) {
            this.context=c;
            this.data = data;
        }

        @Override
        public int getCount() {
            mCartDB=SplashScreen.mCartDB;
            cartDao=SplashScreen.cartDao;
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        TextView txt_itemTotal;

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {



        ImageView buttonInc;
        ImageView buttonDec;


        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item_details_1, parent, false);

            buttonInc = (ImageView) convertView.findViewById(R.id.increaseQuantity);
            buttonDec = (ImageView) convertView.findViewById(R.id.decreaseQuantity);

            buttonInc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    //VAZNO
                    //ZA BAZU SE GLEDA PO ID
                    //ZA PRIKAZ PO POZICIJI

                    Cart c = data.get((Integer) v.getTag());
                    Integer cartId = c.getUid();
                    Integer q = c.getQuantity() + 1;
                    Double p = c.getPrice();

                    c.setQuantity(q);
                    c.setTotalPrice(q * p);


                    try {
                        updateById(cartId, q);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    MenuFragment_1.adapter.notifyDataSetChanged();


                }
            });


            buttonDec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    //VAZNO
                    //ZA BAZU SE GLEDA PO ID
                    //ZA PRIKAZ PO POZICIJI

                    Cart c = data.get((Integer) v.getTag());
                    Integer cartId = c.getUid();
                    Integer q = c.getQuantity() - 1;
                    Double p = c.getPrice();

                    if (q == 0) {

                        data.remove(position);
                        try {
                            deleteCart(c);
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        MenuFragment_1.adapter.notifyDataSetChanged();

                        if(data.size()==0) {
                            Intent i = new Intent(context, MainActivity.class);
                            context.startActivity(i);
                        }
                    }


                    if (q > 0) {

                        c.setQuantity(q);
                        c.setTotalPrice(q * p);


                        try {
                            updateById(cartId, q);
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        MenuFragment_1.adapter.notifyDataSetChanged();

                    }
                }
            });


        }

        TextView txt_itemName = (TextView) convertView.findViewById(R.id.totalPrice);
        TextView txt_itemDescription = (TextView) convertView.findViewById(R.id.itemDescription);
        TextView txt_itemPrice = (TextView) convertView.findViewById(R.id.price);
        TextView txt_itemQuantity = (TextView) convertView.findViewById(R.id.quantity);

        buttonInc = (ImageView) convertView.findViewById(R.id.decreaseQuantity);
        buttonDec = (ImageView) convertView.findViewById(R.id.increaseQuantity);


        buttonInc.setTag(position);
        buttonDec.setTag(position);



        txt_itemName.setText(Double.toString(data.get(position).getTotalPrice()));
        txt_itemDescription.setText(data.get(position).getDescription());
        txt_itemPrice.setText(Double.toString(data.get(position).getPrice()));



        txt_itemQuantity.setText(data.get(position).getQuantity().toString());


        MainActivity_1.txt_itemTotal.setText("TOTAL : " + Double.toString(sumCart()));



        return convertView;

        }

        public void add(Cart item) {
            data.add(item);
            notifyDataSetChanged();
        }

        public void clear() {
            data.clear();
            notifyDataSetChanged();
        }

        public void addData(List<Cart> data){
            this.data = data;
            notifyDataSetChanged();
        }

        public List<Cart> getData(){
            return data;
        }

        private Void updateById(final Integer id, final Integer quantity) throws ExecutionException, InterruptedException {

            return new AsyncTask<Integer, Void, Void>() {


                @Override
                protected Void doInBackground(Integer... params) {

                    // Double p1=Double.valueOf((double) params[1]);
                    cartDao.updateQuantity(id, quantity);
                    return null;

                }


                @Override
                protected void onPostExecute(Void result) {
                    super.onPostExecute(result);

                }

            }.execute().get();
            //execute(id)

        }




        private Void deleteCart(final Cart cart) throws ExecutionException, InterruptedException {

            return new AsyncTask<Integer, Void, Void>() {


                @Override




                protected Void doInBackground(Integer... params) {

                    // Double p1=Double.valueOf((double) params[1]);
                    cartDao.delete(cart);
                    return null;

                }


                @Override
                protected void onPostExecute(Void result) {
                    super.onPostExecute(result);

                }

            }.execute().get();
            //execute(id)

        }


         //

        private Double sumCart()
        {

            Double sum = 0.0;
            for (Cart c : data) {
                Log.i("pop5","uso u for");
                sum += c.getTotalPrice();
               // Log.i("pop ",Double.toString(c.getTotalPrice())+" "+Double.toString(sum));
            }
            return sum;
        }

       //



    }


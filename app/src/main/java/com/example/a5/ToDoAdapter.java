package com.example.a5;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a5.room.product.Product;

import java.util.ArrayList;
import java.util.List;


public class ToDoAdapter extends BaseAdapter {

    private List<Product> data;



    private Integer[] imgid = {
            R.drawable.p1,
            R.drawable.bb2,
            R.drawable.p2,
            R.drawable.bb5,
            R.drawable.bb6,
            R.drawable.d1
    };




    public ToDoAdapter(List<Product> data) {
       this.data = data;
    }

    @Override
    public int getCount() {
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



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.i("pop","POCETAK ADAPTERA");

        CheckBox checkBoxDone;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item_details, parent, false);


        }


        TextView txt_itemName = (TextView) convertView.findViewById(R.id.name);
        TextView txt_itemDescription = (TextView) convertView.findViewById(R.id.itemDescription);
        TextView txt_itemPrice = (TextView) convertView.findViewById(R.id.price);
        ImageView itemImage = (ImageView) convertView.findViewById(R.id.photo);


        txt_itemName.setText(data.get(position).getTitle());
        txt_itemDescription.setText(data.get(position).getDescription());
        txt_itemPrice.setText(Double.toString(data.get(position).getPrice()));

        int si=data.get(position).getPhoto();
        itemImage.setImageResource(imgid[si - 1]);

        return convertView;
    }

    public void add(Product item) {
        data.add(item);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public void addData(List<Product> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public List<Product> getData(){
        return data;
    }
}


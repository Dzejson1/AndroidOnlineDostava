package com.example.a5;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a5.room.food.Food;
import com.example.a5.room.product.Product;

import java.util.List;


public class ToDoAdapter_2 extends BaseAdapter {

    private List<Food> data;



    private Integer[] imgid = {
            R.drawable.p1,
            R.drawable.bb2,
            R.drawable.p2,
            R.drawable.bb5,
            R.drawable.bb6,
            R.drawable.d1
    };




    public ToDoAdapter_2(List<Food> data) {
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


        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.food_item, parent, false);


        }


        TextView txt_desciption = (TextView) convertView.findViewById(R.id.textView2);
        txt_desciption.setText(data.get(position).getDescription());



       // int si=data.get(position).getPhoto();
       // itemImage.setImageResource(imgid[si - 1]);

        return convertView;
    }

    public void add(Food item) {
        data.add(item);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public void addData(List<Food> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public List<Food> getData(){
        return data;
    }
}
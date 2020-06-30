package com.uprank.uprank.teacher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.uprank.uprank.R;
import com.uprank.uprank.teacher.model.MainCategory;

import java.util.ArrayList;

public class HomeAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<MainCategory> arrayList;

    public HomeAdapter(Context context, ArrayList<MainCategory> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.main_category_view, parent, false);


        ImageView imageView = convertView.findViewById(R.id.category_image);
        TextView textView = convertView.findViewById(R.id.text_category_name);

        MainCategory mainCategory = arrayList.get(position);

        textView.setText(mainCategory.getName());
        imageView.setImageResource(mainCategory.getImg_id());

        return convertView;
    }
}

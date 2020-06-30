package com.uprank.uprank.teacher.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uprank.uprank.R;
import com.uprank.uprank.teacher.model.Timetable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class DayAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Timetable> arrayList = new ArrayList<>();
    private HashMap<Integer, String> integerCourseHashMap;
    private HashMap<Integer, String> integerBatchHashMap;
    String[] color_array = {"#D1C4E9", "#C5CAE9", "#E1BEE7", "#F8BBD0", "#C8E6C9", "#FFCCBC"};

    public DayAdapter(Context context, ArrayList<Timetable> arrayList, HashMap<Integer, String> integerCourseHashMap, HashMap<Integer, String> integerBatchHashMap) {
        this.context = context;
        this.arrayList = arrayList;
        this.integerCourseHashMap = integerCourseHashMap;
        this.integerBatchHashMap = integerBatchHashMap;
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
    public View getView(int position, View view, ViewGroup parent) {

        int rnd = new Random().nextInt(color_array.length);

        Timetable timetable = arrayList.get(position);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.timetable_view, parent, false);

        TextView textView_time = view.findViewById(R.id.text_time);
        TextView textView_course = view.findViewById(R.id.text_course);
        TextView textView_batch = view.findViewById(R.id.text_batch);
        ImageView imageView = view.findViewById(R.id.image_subject_icon);
        LinearLayout linearLayout = view.findViewById(R.id.linear1);
        LinearLayout linearLayout1 = view.findViewById(R.id.linear2);

        linearLayout.setBackgroundColor(Color.parseColor(color_array[rnd]));
        linearLayout1.setBackgroundColor(Color.parseColor(color_array[rnd]));

        textView_time.setText(timetable.getStartTime());
        textView_course.setText(integerCourseHashMap.get(timetable.getCourse()));
        textView_batch.setText(integerBatchHashMap.get(timetable.getBatch()));


        return view;
    }
}

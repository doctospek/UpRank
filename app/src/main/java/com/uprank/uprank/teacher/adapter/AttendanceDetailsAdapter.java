package com.uprank.uprank.teacher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.uprank.uprank.R;
import com.uprank.uprank.teacher.model.Attendance;

import java.util.ArrayList;

public class AttendanceDetailsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Attendance> attendances = new ArrayList<>();

    public AttendanceDetailsAdapter(Context context, ArrayList<Attendance> attendances) {
        this.context = context;
        this.attendances = attendances;
    }

    @Override
    public int getCount() {
        return attendances.size();
    }

    @Override
    public Object getItem(int position) {
        return attendances.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.attendance_details_view, parent, false);

        TextView textView_date = convertView.findViewById(R.id.text_date);
        TextView textView_intime = convertView.findViewById(R.id.text_intime);
        TextView textView_outtime = convertView.findViewById(R.id.text_outtime);
        TextView textView_totaltime = convertView.findViewById(R.id.text_totaltime);

        Attendance attendance = attendances.get(position);


        textView_date.setText(attendance.getDate().substring(0,10));
        textView_intime.setText(attendance.getClockIn() + "");
        textView_outtime.setText(attendance.getClockOut() + "");
        textView_totaltime.setText(attendance.getWorkingHours() + "");


        return convertView;
    }
}

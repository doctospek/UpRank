package com.uprank.uprank.teacher.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.uprank.uprank.R;
import com.uprank.uprank.teacher.model.Leave;

import java.util.ArrayList;

public class LeaveDetailsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Leave> leaveArrayList = new ArrayList<>();

    public LeaveDetailsAdapter(Context context, ArrayList<Leave> leaveArrayList) {
        this.context = context;
        this.leaveArrayList = leaveArrayList;
    }

    @Override
    public int getCount() {
        return leaveArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return leaveArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.approved_leave_details, parent, false);

        TextView textView_days = convertView.findViewById(R.id.text_no_days);
        TextView textView_from = convertView.findViewById(R.id.text_from_date);
        TextView textView_to = convertView.findViewById(R.id.text_to_date);
        TextView textView_status = convertView.findViewById(R.id.text_status);

        Leave leave = leaveArrayList.get(position);

        textView_days.setText(leave.getLeaveDayCount() + " Day Off");
        textView_from.setText(leave.getFromDate());
        textView_to.setText(leave.getToDate());
        textView_status.setText(leave.getApprovedStatus());


        if (leave.getApprovedStatus().equals("Not Approved")) {
            textView_status.setTextColor(Color.parseColor("#00FF00"));
        } else {
            textView_status.setTextColor(Color.RED);
        }


        return convertView;
    }
}

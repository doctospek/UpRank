package com.uprank.uprank.teacher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.uprank.uprank.R;
import com.uprank.uprank.teacher.adapter.HomeAdapter;
import com.uprank.uprank.teacher.maincategory.HomeWorkActivity;
import com.uprank.uprank.teacher.maincategory.LeaveActivity;
import com.uprank.uprank.teacher.maincategory.MyAttendanceActivity;
import com.uprank.uprank.teacher.maincategory.NoticeBoardActivity;
import com.uprank.uprank.teacher.maincategory.NotificationsActivity;
import com.uprank.uprank.teacher.maincategory.StudentAttendanceActivity;
import com.uprank.uprank.teacher.maincategory.TimeTableActivity;
import com.uprank.uprank.teacher.model.MainCategory;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    TextView textView_name, textView_institute_name;

    GridView gridView;
    ArrayList<MainCategory> arrayList;
    HomeAdapter homeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        initView();


    }

    private void initView() {


        textView_name = findViewById(R.id.text_name);

        textView_institute_name = findViewById(R.id.text_institute_name);
        gridView = findViewById(R.id.gridview_category);







        setCategoryList();

    }



    private void setCategoryList() {

        arrayList = new ArrayList<>();

        arrayList.add(new MainCategory(1, "Attendance", R.mipmap.attendance));
        arrayList.add(new MainCategory(2, "Timetable", R.mipmap.timetable));
        arrayList.add(new MainCategory(3, "Student \n Attendance", R.mipmap.attendance));
        arrayList.add(new MainCategory(4, "Leave", R.mipmap.leaveicon));
        arrayList.add(new MainCategory(5, "Homework", R.mipmap.homework));
        arrayList.add(new MainCategory(6, "Notes / Study Material", R.mipmap.notes));
        arrayList.add(new MainCategory(7, "Notice Board", R.mipmap.noticeboard));
        arrayList.add(new MainCategory(8, "Exam Schedule", R.mipmap.exam));
        arrayList.add(new MainCategory(9, "Notifications", R.mipmap.notification));
        arrayList.add(new MainCategory(10, "Go Live", R.mipmap.golive));

        homeAdapter = new HomeAdapter(MainActivity.this, arrayList);
        gridView.setAdapter(homeAdapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {

            case 0:
                startActivity(new Intent(MainActivity.this, MyAttendanceActivity.class));
                break;

            case 1:
                startActivity(new Intent(MainActivity.this, TimeTableActivity.class));
                break;

            case 2:
                startActivity(new Intent(MainActivity.this, StudentAttendanceActivity.class));
                break;

            case 3:
                startActivity(new Intent(MainActivity.this, LeaveActivity.class));
                break;

            case 4:
                startActivity(new Intent(MainActivity.this, HomeWorkActivity.class));
                break;

            case 6:
                startActivity(new Intent(MainActivity.this, NoticeBoardActivity.class));
                break;

            case 8:
                startActivity(new Intent(MainActivity.this, NotificationsActivity.class));
                break;


        }
    }


}

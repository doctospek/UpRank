package com.uprank.uprank.teacher.maincategory;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.uprank.uprank.R;
import com.uprank.uprank.teacher.activity.AddHomeworkActivity;
import com.uprank.uprank.teacher.activity.HomeActivity;
import com.uprank.uprank.teacher.adapter.HomeworkAdapter;
import com.uprank.uprank.teacher.model.Batch;
import com.uprank.uprank.teacher.model.BatchResponse;
import com.uprank.uprank.teacher.model.Course;
import com.uprank.uprank.teacher.model.CourseResponse;
import com.uprank.uprank.teacher.model.Homework;
import com.uprank.uprank.teacher.model.HomeworkResponse;
import com.uprank.uprank.teacher.model.Staff;
import com.uprank.uprank.teacher.utility.Pref;
import com.uprank.uprank.teacher.webservices.ApiClient;
import com.uprank.uprank.teacher.webservices.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeWorkActivity extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton floatingActionButton;
    private ApiInterface apiInterface;
    private Pref pref = new Pref();
    private Staff staff;
    private ArrayList<Homework> homeworkArrayList;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work);

        staff = pref.getStaffDataPref(HomeWorkActivity.this);
        apiInterface = ApiClient.getClient(HomeWorkActivity.this).create(ApiInterface.class);

        initView();
    }

    private void initView() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getBaseContext(), HomeActivity.class));
            }
        });

        toolbar.inflateMenu(R.menu.main_menu);

        floatingActionButton = findViewById(R.id.button_add);
        floatingActionButton.setOnClickListener(this);
        listView = findViewById(R.id.list_homework);


        getHomework();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button_add:

                startActivity(new Intent(getBaseContext(), AddHomeworkActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_details:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getHomework() {

        apiInterface.get_homework(Integer.parseInt(staff.getId()), Integer.parseInt(staff.getInstituteId())).enqueue(new Callback<HomeworkResponse>() {
            @Override
            public void onResponse(Call<HomeworkResponse> call, Response<HomeworkResponse> response) {

                if (response.body().getCode().equals("200")) {

                    homeworkArrayList = (ArrayList<Homework>) response.body().getHomework();

                    HomeworkAdapter homeworkAdapter = new HomeworkAdapter(HomeWorkActivity.this, homeworkArrayList);
                    listView.setAdapter(homeworkAdapter);


                } else {

                }
            }

            @Override
            public void onFailure(Call<HomeworkResponse> call, Throwable t) {

            }
        });

    }
}

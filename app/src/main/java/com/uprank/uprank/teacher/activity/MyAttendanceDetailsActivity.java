package com.uprank.uprank.teacher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.uprank.uprank.R;
import com.uprank.uprank.teacher.adapter.AttendanceDetailsAdapter;
import com.uprank.uprank.teacher.maincategory.MyAttendanceActivity;
import com.uprank.uprank.teacher.model.Attendance;
import com.uprank.uprank.teacher.model.AttendanceResponse;
import com.uprank.uprank.teacher.model.Staff;
import com.uprank.uprank.teacher.utility.CommonUtils;
import com.uprank.uprank.teacher.utility.Pref;
import com.uprank.uprank.teacher.webservices.ApiClient;
import com.uprank.uprank.teacher.webservices.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAttendanceDetailsActivity extends AppCompatActivity {

    ListView listView;
    Pref pref = new Pref();
    Staff staff;
    ApiInterface apiInterface;
    ArrayList<Attendance> attendanceArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_attendance_details);

        apiInterface = ApiClient.getClient(MyAttendanceDetailsActivity.this).create(ApiInterface.class);
        staff = pref.getStaffDataPref(MyAttendanceDetailsActivity.this);

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

                startActivity(new Intent(getBaseContext(), MyAttendanceActivity.class));
            }
        });

        toolbar.inflateMenu(R.menu.main_menu);
        listView = findViewById(R.id.listview_attendance);

        getAllAttendance();

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

    private void getAllAttendance() {

        apiInterface.get_staff_attendance(Integer.parseInt(staff.getId()), Integer.parseInt(staff.getInstituteId())).enqueue(new Callback<AttendanceResponse>() {
            @Override
            public void onResponse(Call<AttendanceResponse> call, Response<AttendanceResponse> response) {

                if (response.body().getCode().equals("200")) {

                    CommonUtils.successToast(MyAttendanceDetailsActivity.this, response.body().getMsg());

                    attendanceArrayList = (ArrayList<Attendance>) response.body().getAttendance();

                    AttendanceDetailsAdapter attendanceDetailsAdapter = new AttendanceDetailsAdapter(MyAttendanceDetailsActivity.this, attendanceArrayList);
                    listView.setAdapter(attendanceDetailsAdapter);

                } else {
                    CommonUtils.errorToast(MyAttendanceDetailsActivity.this, response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<AttendanceResponse> call, Throwable t) {

                CommonUtils.errorToast(MyAttendanceDetailsActivity.this, "Failed");
            }
        });

    }
}

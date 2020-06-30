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
import com.uprank.uprank.teacher.adapter.LeaveDetailsAdapter;
import com.uprank.uprank.teacher.maincategory.LeaveActivity;
import com.uprank.uprank.teacher.model.Leave;
import com.uprank.uprank.teacher.model.LeaveResponse;
import com.uprank.uprank.teacher.model.Staff;
import com.uprank.uprank.teacher.utility.CommonUtils;
import com.uprank.uprank.teacher.utility.Pref;
import com.uprank.uprank.teacher.webservices.ApiClient;
import com.uprank.uprank.teacher.webservices.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaveDetailsActivity extends AppCompatActivity {

    ListView listView_pending, listView_history;
    ArrayList<Leave> pendingArrayList;
    ArrayList<Leave> approvedArrayList;
    Pref pref = new Pref();
    Staff staff;
    ApiInterface apiInterface;
    LeaveDetailsAdapter pendingleaveDetailsAdapter;
    LeaveDetailsAdapter approvedleaveDetailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_details);

        staff = pref.getStaffDataPref(LeaveDetailsActivity.this);
        apiInterface = ApiClient.getClient(LeaveDetailsActivity.this).create(ApiInterface.class);

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

                startActivity(new Intent(getBaseContext(), LeaveActivity.class));
            }
        });

        toolbar.inflateMenu(R.menu.main_menu);
        listView_pending = findViewById(R.id.list_pending_leaves);
        listView_history = findViewById(R.id.list_leaves_history);

        getLeaveDetails();
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

    private void getLeaveDetails() {

        pendingArrayList = new ArrayList<>();
        approvedArrayList = new ArrayList<>();

        apiInterface.get_staff_leave_details(Integer.parseInt(staff.getId()), Integer.parseInt(staff.getInstituteId())).enqueue(new Callback<LeaveResponse>() {
            @Override
            public void onResponse(Call<LeaveResponse> call, Response<LeaveResponse> response) {

                if (response.body().getCode().equals("200")) {

                    CommonUtils.successToast(LeaveDetailsActivity.this, response.body().getMsg());

                    for (Leave leave : response.body().getLeave()) {

                        if (leave.getApprovedStatus().equals("Not Approved")) {

                            pendingArrayList.add(leave);
                            pendingleaveDetailsAdapter.notifyDataSetChanged();

                        } else {
                            approvedArrayList.add(leave);
                            approvedleaveDetailsAdapter.notifyDataSetChanged();
                        }
                    }


                } else {

                }
            }

            @Override
            public void onFailure(Call<LeaveResponse> call, Throwable t) {

            }
        });

        pendingleaveDetailsAdapter = new LeaveDetailsAdapter(LeaveDetailsActivity.this, pendingArrayList);
        listView_pending.setAdapter(pendingleaveDetailsAdapter);

        approvedleaveDetailsAdapter = new LeaveDetailsAdapter(LeaveDetailsActivity.this, approvedArrayList);
        listView_history.setAdapter(approvedleaveDetailsAdapter);
    }
}

package com.uprank.uprank.teacher.maincategory;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.uprank.uprank.R;
import com.uprank.uprank.teacher.activity.HomeActivity;
import com.uprank.uprank.teacher.adapter.NoticeBoardAdapter;
import com.uprank.uprank.teacher.model.Notice;
import com.uprank.uprank.teacher.model.NoticeResponse;
import com.uprank.uprank.teacher.model.Staff;
import com.uprank.uprank.teacher.utility.CommonUtils;
import com.uprank.uprank.teacher.utility.Pref;
import com.uprank.uprank.teacher.webservices.ApiClient;
import com.uprank.uprank.teacher.webservices.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeBoardActivity extends AppCompatActivity {

    ListView listView;
    ApiInterface apiInterface;
    Pref pref = new Pref();
    Staff staff;
    ArrayList<Notice> noticeArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board);

        staff = pref.getStaffDataPref(NoticeBoardActivity.this);
        apiInterface = ApiClient.getClient(NoticeBoardActivity.this).create(ApiInterface.class);

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

        listView = findViewById(R.id.list_notice);

        getNotice();

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


    private void getNotice() {

        apiInterface.get_staff_notice(Integer.parseInt(staff.getInstituteId())).enqueue(new Callback<NoticeResponse>() {
            @Override
            public void onResponse(Call<NoticeResponse> call, Response<NoticeResponse> response) {

                if (response.body().getCode().equals("200")) {

                    noticeArrayList = (ArrayList<Notice>) response.body().getNotice();

                    NoticeBoardAdapter noticeBoardAdapter = new NoticeBoardAdapter(NoticeBoardActivity.this, noticeArrayList);
                    listView.setAdapter(noticeBoardAdapter);

                } else {

                    CommonUtils.errorToast(NoticeBoardActivity.this, "No Notice Available");
                }


            }

            @Override
            public void onFailure(Call<NoticeResponse> call, Throwable t) {

            }
        });


    }
}

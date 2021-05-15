package com.uprank.uprank.teacher.maincategory;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.JsonObject;
import com.uprank.uprank.R;
import com.uprank.uprank.teacher.activity.HomeActivity;
import com.uprank.uprank.teacher.activity.MyAttendanceDetailsActivity;
import com.uprank.uprank.teacher.model.Staff;
import com.uprank.uprank.teacher.utility.CommonUtils;
import com.uprank.uprank.teacher.utility.Pref;
import com.uprank.uprank.teacher.webservices.ApiClient;
import com.uprank.uprank.teacher.webservices.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAttendanceActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView_fromDate, textView_toDate, textView_present, textView_absent, select_from_date, select_to_date, text_summary_to_date, textView_view_details;
    private Calendar myCalendar;
    private int presentCount, absentCount;
    private String fromDate, toDate;
    PieChartView pieChartView;
    List<SliceValue> pieData;
    PieChartData pieChartData;
    Pref pref = new Pref();
    Staff staff;
    DatePickerDialog.OnDateSetListener Startdate, endDate;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_attendance);

        staff = pref.getStaffDataPref(MyAttendanceActivity.this);
        apiInterface = ApiClient.getClient(MyAttendanceActivity.this).create(ApiInterface.class);


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

        //toolbar.inflateMenu(R.menu.main_menu);

        myCalendar = Calendar.getInstance();

        select_from_date = findViewById(R.id.text_select_from_date);
        select_to_date = findViewById(R.id.text_select_to_date);
        textView_fromDate = findViewById(R.id.text_from_date);
        textView_toDate = findViewById(R.id.text_to_date);
        textView_present = findViewById(R.id.text_present_days);
        textView_absent = findViewById(R.id.text_absent_days);
        text_summary_to_date = findViewById(R.id.text_summary_to_date);
        textView_view_details = findViewById(R.id.text_viewdetails);
        pieChartView = findViewById(R.id.piechart);

        listeners();

        Startdate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateFromLabel();
            }

        };

        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateToLabel();
            }

        };
    }

    private void listeners() {
        select_from_date.setOnClickListener(this);
        select_to_date.setOnClickListener(this);
        textView_view_details.setOnClickListener(this);
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_details:
                startActivity(new Intent(MyAttendanceActivity.this, MyAttendanceDetailsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.text_select_from_date:

                new DatePickerDialog(MyAttendanceActivity.this, Startdate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                break;

            case R.id.text_select_to_date:
                new DatePickerDialog(MyAttendanceActivity.this, endDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;

            case R.id.text_viewdetails:
                startActivity(new Intent(MyAttendanceActivity.this, MyAttendanceDetailsActivity.class));
                break;
        }

    }

    private void updateFromLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

        textView_fromDate.setText(sdf.format(myCalendar.getTime()));

        fromDate = sdf.format(myCalendar.getTime());
    }

    private void updateToLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

        textView_toDate.setText(sdf.format(myCalendar.getTime()));
        text_summary_to_date.setText(sdf.format(myCalendar.getTime()));

        toDate = sdf.format(myCalendar.getTime());

        if (TextUtils.isEmpty(fromDate) && TextUtils.isEmpty(toDate)) {

            textView_fromDate.setError("Enter Start Date");

        } else {

            staffPresentDays();
            staffAbsentDays();
        }
    }

    private void staffPresentDays() {

        pieData = new ArrayList<>();
        textView_present.setText("");
        textView_absent.setText("");
        presentCount = 0;
        absentCount = 0;

        apiInterface.get_staff_present_days(Integer.parseInt(staff.getId()), fromDate, toDate).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    String code = jsonObject.getString("code");
                    String msg = jsonObject.getString("msg");

                    if (code.equals("200")) {
                        presentCount = jsonObject.getInt("count");
                        textView_present.setText(String.valueOf(presentCount));
                    } else {
                        CommonUtils.errorToast(MyAttendanceActivity.this, msg);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private void staffAbsentDays() {

        apiInterface.get_staff_absent_days(Integer.parseInt(staff.getId()), fromDate, toDate).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    String code = jsonObject.getString("code");
                    String msg = jsonObject.getString("msg");

                    if (code.equals("200")) {
                        absentCount = jsonObject.getInt("count");

                        textView_absent.setText(String.valueOf(absentCount));
                        pieData.add(new SliceValue(presentCount, Color.BLUE).setLabel("Present"));
                        pieData.add(new SliceValue(absentCount, Color.RED).setLabel("Absent"));
                        pieChartData = new PieChartData(pieData);
                        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
                        pieChartData.setHasCenterCircle(true).setCenterText1("My Attendance").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#0097A7"));
                        pieChartView.setPieChartData(pieChartData);
                    } else {

                        CommonUtils.errorToast(MyAttendanceActivity.this, msg);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                CommonUtils.errorToast(MyAttendanceActivity.this, "No Response");
            }
        });
    }
}

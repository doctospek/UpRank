package com.uprank.uprank.teacher.maincategory;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.JsonObject;
import com.uprank.uprank.R;
import com.uprank.uprank.teacher.activity.HomeActivity;
import com.uprank.uprank.teacher.activity.LeaveDetailsActivity;
import com.uprank.uprank.teacher.model.Staff;
import com.uprank.uprank.teacher.utility.CommonUtils;
import com.uprank.uprank.teacher.utility.Pref;
import com.uprank.uprank.teacher.webservices.ApiClient;
import com.uprank.uprank.teacher.webservices.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaveActivity extends AppCompatActivity implements View.OnClickListener {

    Button button_submit_request;
    private TextView textView_fromDate, textView_toDate, textView_teacher_name, textView_leave_count;
    EditText editText_reason;
    DatePickerDialog.OnDateSetListener Startdate, endDate;
    private String fromDate, toDate;
    private Calendar myCalendar;
    private ApiInterface apiInterface;
    Pref pref = new Pref();
    Staff staff;
    int number_of_days;
    private String leave_reason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);

        staff = pref.getStaffDataPref(LeaveActivity.this);
        apiInterface = ApiClient.getClient(LeaveActivity.this).create(ApiInterface.class);
        myCalendar = Calendar.getInstance();

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

        button_submit_request = findViewById(R.id.submit_request_button);
        textView_fromDate = findViewById(R.id.text_from_date);
        textView_toDate = findViewById(R.id.text_to_date);
        textView_leave_count = findViewById(R.id.text_leave_count);
        textView_teacher_name = findViewById(R.id.text_teacher_name);
        editText_reason = findViewById(R.id.edit_leave_reason);

        textView_teacher_name.setText(staff.getFname() + " " + staff.getLname());

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
        button_submit_request.setOnClickListener(this);
        textView_fromDate.setOnClickListener(this);
        textView_toDate.setOnClickListener(this);

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
                startActivity(new Intent(LeaveActivity.this, LeaveDetailsActivity.class));
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.submit_request_button:

                leave_reason = editText_reason.getText().toString();

                if (leave_reason.length() > 0) {

                    if (number_of_days > 0) {

                        addLeave();

                    } else {

                        CommonUtils.showToast(getBaseContext(), "Please Select Valid Dates");
                    }


                } else {

                    editText_reason.setError("Please Enter Valid Reason");
                }


                break;

            case R.id.text_from_date:

                new DatePickerDialog(LeaveActivity.this, Startdate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                break;

            case R.id.text_to_date:
                new DatePickerDialog(LeaveActivity.this, endDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;


        }

    }

    private void showLeaveResponseDialog() {

        final Dialog dialog = new Dialog(LeaveActivity.this);
        dialog.setContentView(R.layout.leave_response_dialog);
        dialog.show();

        TextView textView = dialog.findViewById(R.id.text_cancel);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

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

        toDate = sdf.format(myCalendar.getTime());


        Date from = null, to = null;

        try {
            from = sdf.parse(fromDate);
            to = sdf.parse(toDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff = to.getTime() - from.getTime();
        System.out.println("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));

        number_of_days = (int) diff / (24 * 60 * 60 * 1000);

        textView_leave_count.setText("Number of Requested Days : " + number_of_days);

    }

    private void addLeave() {

        apiInterface.add_staff_leave(Integer.parseInt(staff.getId()), Integer.parseInt(staff.getInstituteId()), number_of_days, fromDate, leave_reason, toDate).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    String code = jsonObject.getString("code");
                    String msg = jsonObject.getString("msg");

                    if (code.equals("200")) {

                        CommonUtils.successToast(LeaveActivity.this, msg);
                        showLeaveResponseDialog();

                    } else {
                        CommonUtils.errorToast(LeaveActivity.this, msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    CommonUtils.errorToast(LeaveActivity.this, "No Response");
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                CommonUtils.errorToast(LeaveActivity.this, "No Response");
            }
        });


    }
}

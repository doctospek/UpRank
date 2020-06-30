package com.uprank.uprank.teacher.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.JsonObject;
import com.manojbhadane.QButton;
import com.uprank.uprank.R;
import com.uprank.uprank.teacher.adapter.HomeAdapter;
import com.uprank.uprank.teacher.maincategory.ExamScheduleActivity;
import com.uprank.uprank.teacher.maincategory.GoLiveActivity;
import com.uprank.uprank.teacher.maincategory.HomeWorkActivity;
import com.uprank.uprank.teacher.maincategory.LeaveActivity;
import com.uprank.uprank.teacher.maincategory.MyAttendanceActivity;
import com.uprank.uprank.teacher.maincategory.NotesActivity;
import com.uprank.uprank.teacher.maincategory.NoticeBoardActivity;
import com.uprank.uprank.teacher.maincategory.NotificationsActivity;
import com.uprank.uprank.teacher.maincategory.StudentAttendanceActivity;
import com.uprank.uprank.teacher.maincategory.TimeTableActivity;
import com.uprank.uprank.teacher.model.Institute;
import com.uprank.uprank.teacher.model.MainCategory;
import com.uprank.uprank.teacher.model.Staff;
import com.uprank.uprank.teacher.utility.CommonUtils;
import com.uprank.uprank.teacher.utility.Pref;
import com.uprank.uprank.teacher.webservices.ApiClient;
import com.uprank.uprank.teacher.webservices.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private QButton textView_clockIn, textView_clockout;
    private GridView gridView;
    private ApiInterface apiInterface;
    private int staff_attendance_id, PERMISSION_ID = 44;
    private String clockIn_time, clockOut_time, total_working_hours, mark, token;
    private Staff staff;
    private Pref pref = new Pref();
    private Institute institute;
    private String latitude, longitude;
    private FusedLocationProviderClient mFusedLocationClient;


    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();

            latitude = String.valueOf(mLastLocation.getLatitude());
            longitude = String.valueOf(mLastLocation.getLongitude());

            //Utility.showToast(getActivity(), 2 + " " + latitude + "---" + latitude);
            Log.e("MyLocation_2", 2 + " " + latitude + "---" + longitude);
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_main, container, false);

        checkPermissions();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        apiInterface = ApiClient.getClient(getActivity()).create(ApiInterface.class);
        staff = pref.getStaffDataPref(getActivity());
        institute = pref.getInstitutePref(getActivity());

        initView(root);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("HomeFragment", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        token = task.getResult().getToken();
                        pref.setBasicLoginFirebaseAccessToken(getActivity(), token);
                        updateFirebase();


                    }
                });


        return root;
    }

    private void initView(View view) {

        TextView textView_name = view.findViewById(R.id.text_name);
        textView_clockIn = view.findViewById(R.id.text_clockin);
        textView_clockout = view.findViewById(R.id.text_clockout);
        TextView textView_institute_name = view.findViewById(R.id.text_institute_name);
        gridView = view.findViewById(R.id.gridview_category);

        listeners();
        setCategoryList();

        textView_name.setText(staff.getFname() + " " + staff.getLname());
        textView_institute_name.setText(institute.getInstituteName());

        if (pref.getAttendancePref(getActivity()) == 1) {


            textView_clockIn.setVisibility(View.GONE);
            textView_clockout.setVisibility(View.VISIBLE);

            clockIn_time = pref.getClockInPref(getActivity());

        } else {

            textView_clockIn.setVisibility(View.VISIBLE);
            textView_clockout.setVisibility(View.GONE);
        }

        getLastLocation();


    }

    private void listeners() {
        gridView.setOnItemClickListener(this);
        textView_clockIn.setOnClickListener(this);
        textView_clockout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.text_clockin:


                Calendar cal = Calendar.getInstance(TimeZone.getDefault());
                Date currentLocalTime = cal.getTime();
                DateFormat date = new SimpleDateFormat("hh:mm a");
// you can get seconds by adding  "...:ss" to it
                date.setTimeZone(TimeZone.getDefault());

                //clockIn_time = date.format(currentLocalTime);
                clockIn_time = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date());

                pref.setClockInPref(getActivity(), clockIn_time);


                CommonUtils.showToast(getActivity(), clockIn_time);


                if (CommonUtils.isNetworkAvailable(getActivity())) {
                    addAttendance();
                } else {
                    CommonUtils.warningToast(getActivity());
                }

                break;

            case R.id.text_clockout:


                Calendar cal1 = Calendar.getInstance(TimeZone.getDefault());
                Date currentLocalTime1 = cal1.getTime();
                DateFormat date1 = new SimpleDateFormat("hh:mm a");
                date1.setTimeZone(TimeZone.getDefault());
                //clockOut_time = date1.format(currentLocalTime1);
                clockOut_time = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date());

                CommonUtils.showToast(getActivity(), clockOut_time);


                calculateWorkingHours();

                break;
        }

    }

    private void setCategoryList() {

        ArrayList<MainCategory> arrayList = new ArrayList<>();

        arrayList.add(new MainCategory(1, "My\nAttendance", R.mipmap.attendance));
        arrayList.add(new MainCategory(2, "Timetable", R.mipmap.timetable));
        arrayList.add(new MainCategory(3, "Student \n Attendance", R.mipmap.attendance));
        arrayList.add(new MainCategory(4, "Leave", R.mipmap.leaveicon));
        arrayList.add(new MainCategory(5, "Homework", R.mipmap.homework));
        arrayList.add(new MainCategory(6, "Notes / Study Material", R.mipmap.notes));
        arrayList.add(new MainCategory(7, "Notice Board", R.mipmap.noticeboard));
        arrayList.add(new MainCategory(8, "Exam Schedule", R.mipmap.exam));
        arrayList.add(new MainCategory(9, "Notifications", R.mipmap.notification));
        arrayList.add(new MainCategory(10, "Go Live", R.mipmap.golive));

        HomeAdapter homeAdapter = new HomeAdapter(getActivity(), arrayList);
        gridView.setAdapter(homeAdapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {

            case 0:
                startActivity(new Intent(getActivity(), MyAttendanceActivity.class));
                break;

            case 1:
                startActivity(new Intent(getActivity(), TimeTableActivity.class));
                break;

            case 2:
                startActivity(new Intent(getActivity(), StudentAttendanceActivity.class));
                break;

            case 3:
                startActivity(new Intent(getActivity(), LeaveActivity.class));
                break;

            case 4:
                startActivity(new Intent(getActivity(), HomeWorkActivity.class));
                break;

            case 5:
                startActivity(new Intent(getActivity(), NotesActivity.class));
                break;

            case 6:
                startActivity(new Intent(getActivity(), NoticeBoardActivity.class));
                break;

            case 7:
                startActivity(new Intent(getActivity(), ExamScheduleActivity.class));
                break;

            case 8:
                startActivity(new Intent(getActivity(), NotificationsActivity.class));
                break;

            case 9:
                startActivity(new Intent(getActivity(), GoLiveActivity.class));
                break;


        }
    }

    private void addAttendance() {

        apiInterface.add_staff_attendance(Integer.parseInt(staff.getId()), clockIn_time, latitude, longitude, Integer.parseInt(staff.getInstituteId())).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    String code = jsonObject.getString("code");
                    String msg = jsonObject.getString("msg");

                    if (code.equals("200")) {

                        staff_attendance_id = jsonObject.getInt("last_id");
                        CommonUtils.successToast(getActivity(), msg);
                        textView_clockIn.setVisibility(View.GONE);
                        textView_clockout.setVisibility(View.VISIBLE);

                        //attemdance preference 1=clockedIn,2=ClockedOut,0=default
                        pref.setAttendancePref(getActivity(), 1);
                        pref.setAttendanceIdPref(getActivity(), staff_attendance_id);

                    } else {
                        CommonUtils.errorToast(getActivity(), msg);
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

    private void checkAttendance() {

        apiInterface.check_staff_today_attendance(Integer.parseInt(staff.getId()), Integer.parseInt(staff.getInstituteId())).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    String code = jsonObject.getString("code");
                    String msg = jsonObject.getString("msg");

                    if (code.equals("200")) {
                        CommonUtils.errorToast(getActivity(), msg);

                    } else {

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

    private void calculateWorkingHours() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = simpleDateFormat.parse(clockIn_time);
            date2 = simpleDateFormat.parse(clockOut_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        long difference = date2.getTime() - date1.getTime();
        long days = (int) (difference / (1000 * 60 * 60 * 24));
        long hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
        long min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
        hours = (hours < 0 ? -hours : hours);

        total_working_hours = hours + ":" + min;

        mark = "Present";

        Toast.makeText(getActivity(), "Total Working Hours : " + total_working_hours, Toast.LENGTH_SHORT).show();

        Log.e("total_working_hours", total_working_hours);

        updateAttendance();

    }

    private void updateAttendance() {

        staff_attendance_id = pref.getAttendanceIdPref(getContext());

        apiInterface.update_staff_attendance(Integer.parseInt(staff.getId()), staff_attendance_id, clockOut_time, total_working_hours, latitude, longitude, mark).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    String code = jsonObject.getString("code");
                    String msg = jsonObject.getString("msg");

                    if (code.equals("200")) {

                        CommonUtils.successToast(getActivity(), msg);

                        textView_clockIn.setVisibility(View.VISIBLE);
                        textView_clockout.setVisibility(View.GONE);

                        pref.setAttendancePref(getActivity(), 2);

                    } else {

                        CommonUtils.errorToast(getActivity(), msg);
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

    private void checkPermissions() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        } else {
            requestPermissions();
        }
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                getActivity(),
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private void getLastLocation() {

        if (isLocationEnabled()) {
            mFusedLocationClient.getLastLocation().addOnCompleteListener(
                    new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location location = task.getResult();
                            if (location == null) {
                                requestNewLocationData();
                            } else {

                                latitude = String.valueOf(location.getLatitude());
                                longitude = String.valueOf(location.getLongitude());

                                //Utility.showToast(getActivity(), 1 + " " + latitude + "---" + longitude);
                                Log.e("MyLocation_1", 1 + " " + latitude + "---" + longitude);


                            }
                        }
                    }
            );
        } else {
            Toast.makeText(getActivity(), "Turn on location", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }


    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    private void requestNewLocationData() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Granted. Start getting the location information
            }
        }
    }


    private void updateFirebase() {

        apiInterface.update_staff_firebase(Integer.parseInt(staff.getId()), token).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    String code = jsonObject.getString("code");

                    if (code.equals("200")) {

                    } else {

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
}
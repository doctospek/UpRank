package com.uprank.uprank.teacher.firebase;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import com.google.gson.JsonObject;
import com.uprank.uprank.teacher.model.Staff;
import com.uprank.uprank.teacher.utility.Pref;
import com.uprank.uprank.teacher.webservices.ApiClient;
import com.uprank.uprank.teacher.webservices.ApiInterface;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformOtherUserService extends JobService {

    Pref sharedPref = new Pref();
    //ArrayList<Student> arrayListStudent;

    Staff staff;
    String selectedClass, selectedDiv, msg;

    @Override
    public boolean onStartJob(JobParameters params) {

        staff = sharedPref.getStaffDataPref(BaseApplication.getAppContext());
        selectedClass = params.getExtras().getString("selectedclass");
        selectedDiv = params.getExtras().getString("selecteddiv");
        msg = params.getExtras().getString("msg");
        /*getStudent();*/
        return false;
    }


/*
    private void getStudent() {

        ArrayList<Student> arrayList = new ArrayList<>();
        arrayListStudent = new ArrayList<>();
        ApiInterface apiInterface = ApiClient.getClient(getBaseContext()).create(ApiInterface.class);
        apiInterface.getStudents(teacher.getSchool_id(), selectedClass, selectedDiv).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Student student = null;
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        String code = jsonObject.getString("code");
                        if (code.equals("200")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                student = new Student();
                                student.setId(Integer.parseInt(object.getString("id")));
                                student.setSchool_id(Integer.parseInt(object.getString("school_id")));
                                student.setName(object.getString("name"));
                                student.setEnrollment_no(object.getString("enrollment_code"));
                                student.setAddmission_no(object.getString("admission_number"));
                                student.setClass_group(object.getString("class_group"));
                                student.setDob(object.getString("dob"));
                                student.setBlood_group(object.getString("blood_group"));
                                student.setAddress(object.getString("address"));
                                student.setClass_name(object.getString("class"));
                                student.setDivision(object.getString("division"));
                                student.setSibling(object.getString("sibling"));
                                student.setPassword(object.getString("password"));
                                student.setCreated_on(object.getString("created_on"));
                                student.setVehicle_no(object.getString("vehicle_no"));
                                student.setFirebaseToken(object.getString("firebaseToken"));
                                arrayList.add(student);
                                arrayListStudent.add(student);
                            }

                            Log.e("InormOtherService", arrayListStudent.toString());
                            onSendNotifiaction(arrayListStudent);
                        } else {

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
            }
        });
    }
*/

/*
    private void onSendNotifiaction(ArrayList<Student> followingDtoList) {
        Log.e("onSendNotifiaction", "in onSendNotifiaction");

        List<String> listFCMToken = new ArrayList<>();
        for (Student student : followingDtoList) {

            Log.e("onSend_student", student.toString());
            listFCMToken.add(student.getFirebaseToken());
            JSONObject notification = new JSONObject();
            JSONObject notifcationBody = new JSONObject();
            try {
                notifcationBody.put("title", "MSP");
                notifcationBody.put("message", " Hi " + student.getName() + "," + msg);
                notifcationBody.put("postUrl", "");

//            notification.put("to", "dCL6A9SzESc:APA91bFQvekUjssjAlC6EISF-2Ojads_Z9rxXEMJKJt6uqiTawxb-ZCNk0pqFaCi2qpOvE0G2d1X574LHrf_kWNvL2z8sRBZnkTswGanbSC7KIo90xJ0lNg24TcpRCfTt_ZADN33stZ-");
                notification.put("to", student.getFirebaseToken());
                notification.put("data", notifcationBody);
            } catch (JSONException e) {
//            Log.e(TAG, "onCreate: " + e.getMessage() );
            }
            FirebaseSendNotification mFirebaseSendNotification = new FirebaseSendNotification(BaseApplication.getAppContext());
            mFirebaseSendNotification.sendNotification(notification);
        }

        saveNotification();

    }
*/

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }


/*
    private void saveNotification() {
        ApiInterface apiInterface = ApiClient.getClient(BaseApplication.getAppContext()).create(ApiInterface.class);
        apiInterface.saveNotification(teacher.getSchool_id(), selectedClass, selectedDiv, msg).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        String code = jsonObject.getString("code");
                        String msg = jsonObject.getString("msg");

                        if (code.equals("200")) {

                            Utility.showToast(BaseApplication.getAppContext(), "Notification Added");

                        } else {
                            Utility.showToast(BaseApplication.getAppContext(), "Notification Not Added");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
*/
}

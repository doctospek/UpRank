package com.uprank.uprank.teacher.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.uprank.uprank.teacher.model.Institute;
import com.uprank.uprank.teacher.model.Staff;

public class Pref {

    private String SHARED_PREF_NAME = "UT";

    public void setStaffDataPref(Context context, Staff staff) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(staff);
        prefsEditor.putString("StaffData", json);
        prefsEditor.apply();
    }

    public Staff getStaffDataPref(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("StaffData", "");
        return gson.fromJson(json, Staff.class);
    }

    public void setInstitutePref(Context context, Institute institute) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(institute);
        prefsEditor.putString("InstituteData", json);
        prefsEditor.apply();
    }

    public Institute getInstitutePref(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("InstituteData", "");
        return gson.fromJson(json, Institute.class);
    }

    public void setAttendancePref(Context context, int mark) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putInt("AttendanceData", mark);
        prefsEditor.apply();
    }

    public void setAttendanceIdPref(Context context, int id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putInt("AttendanceId", id);
        prefsEditor.apply();
    }

    public Integer getAttendanceIdPref(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("AttendanceId", 0);
    }

    public Integer getAttendancePref(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("AttendanceData", 0);
    }

    public void setClockInPref(Context context, String time) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("ClockInTime", time);
        prefsEditor.apply();
    }

    public String getClockInPref(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("ClockInTime", "");
    }

    public String getBasicLoginFirebaseAccessToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, 0);
        return sharedPreferences.getString("token", "");
    }

    public void setBasicLoginFirebaseAccessToken(Context context, String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();
    }


}

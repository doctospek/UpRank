
package com.uprank.uprank.teacher.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Attendance {

    @SerializedName("clock_in")
    private String mClockIn;
    @SerializedName("clock_out")
    private Object mClockOut;
    @SerializedName("clockin_lat")
    private String mClockinLat;
    @SerializedName("clockin_long")
    private String mClockinLong;
    @SerializedName("clockout_lat")
    private Object mClockoutLat;
    @SerializedName("clockout_long")
    private Object mClockoutLong;
    @SerializedName("date")
    private String mDate;
    @SerializedName("id")
    private String mId;
    @SerializedName("institute_id")
    private String mInstituteId;
    @SerializedName("mark")
    private Object mMark;
    @SerializedName("staff_id")
    private String mStaffId;
    @SerializedName("working_hours")
    private Object mWorkingHours;

    public String getClockIn() {
        return mClockIn;
    }

    public void setClockIn(String clockIn) {
        mClockIn = clockIn;
    }

    public Object getClockOut() {
        return mClockOut;
    }

    public void setClockOut(Object clockOut) {
        mClockOut = clockOut;
    }

    public String getClockinLat() {
        return mClockinLat;
    }

    public void setClockinLat(String clockinLat) {
        mClockinLat = clockinLat;
    }

    public String getClockinLong() {
        return mClockinLong;
    }

    public void setClockinLong(String clockinLong) {
        mClockinLong = clockinLong;
    }

    public Object getClockoutLat() {
        return mClockoutLat;
    }

    public void setClockoutLat(Object clockoutLat) {
        mClockoutLat = clockoutLat;
    }

    public Object getClockoutLong() {
        return mClockoutLong;
    }

    public void setClockoutLong(Object clockoutLong) {
        mClockoutLong = clockoutLong;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getInstituteId() {
        return mInstituteId;
    }

    public void setInstituteId(String instituteId) {
        mInstituteId = instituteId;
    }

    public Object getMark() {
        return mMark;
    }

    public void setMark(Object mark) {
        mMark = mark;
    }

    public String getStaffId() {
        return mStaffId;
    }

    public void setStaffId(String staffId) {
        mStaffId = staffId;
    }

    public Object getWorkingHours() {
        return mWorkingHours;
    }

    public void setWorkingHours(Object workingHours) {
        mWorkingHours = workingHours;
    }

}


package com.uprank.uprank.teacher.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Timetable {

    @SerializedName("batch")
    private int mBatch;

    @SerializedName("course")
    private int mCourse;

    @SerializedName("end_time")
    private String mEndTime;

    @SerializedName("id")
    private int mId;

    @SerializedName("institute_id")
    private int mInstituteId;

    @SerializedName("staff_id")
    private int mStaffId;

    @SerializedName("start_time")
    private String mStartTime;

    public int getBatch() {
        return mBatch;
    }

    public void setBatch(int batch) {
        mBatch = batch;
    }

    public int getCourse() {
        return mCourse;
    }

    public void setCourse(int course) {
        mCourse = course;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public void setEndTime(String endTime) {
        mEndTime = endTime;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getInstituteId() {
        return mInstituteId;
    }

    public void setInstituteId(int instituteId) {
        mInstituteId = instituteId;
    }

    public int getStaffId() {
        return mStaffId;
    }

    public void setStaffId(int staffId) {
        mStaffId = staffId;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public void setStartTime(String startTime) {
        mStartTime = startTime;
    }

}


package com.uprank.uprank.teacher.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Batch {

    @SerializedName("end_date")
    private String mEndDate;
    @SerializedName("hall")
    private String mHall;
    @SerializedName("id")
    private int mId;
    @SerializedName("institute_id")
    private int mInstituteId;
    @SerializedName("name")
    private String mName;
    @SerializedName("start_date")
    private String mStartDate;
    @SerializedName("timing")
    private String mTiming;

    public String getEndDate() {
        return mEndDate;
    }

    public void setEndDate(String endDate) {
        mEndDate = endDate;
    }

    public String getHall() {
        return mHall;
    }

    public void setHall(String hall) {
        mHall = hall;
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

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getStartDate() {
        return mStartDate;
    }

    public void setStartDate(String startDate) {
        mStartDate = startDate;
    }

    public String getTiming() {
        return mTiming;
    }

    public void setTiming(String timing) {
        mTiming = timing;
    }

}

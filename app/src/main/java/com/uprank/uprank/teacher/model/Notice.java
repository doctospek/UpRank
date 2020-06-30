
package com.uprank.uprank.teacher.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Notice {

    @SerializedName("id")
    private int mId;
    @SerializedName("institute_id")
    private int mInstituteId;
    @SerializedName("notice")
    private String mNotice;
    @SerializedName("type")
    private String mType;

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

    public String getNotice() {
        return mNotice;
    }

    public void setNotice(String notice) {
        mNotice = notice;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

}

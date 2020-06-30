
package com.uprank.uprank.teacher.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Course {

    @SerializedName("description")
    private String mDescription;
    @SerializedName("id")
    private int mId;
    @SerializedName("institute_id")
    private int mInstituteId;
    @SerializedName("name")
    private String mName;

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
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

}

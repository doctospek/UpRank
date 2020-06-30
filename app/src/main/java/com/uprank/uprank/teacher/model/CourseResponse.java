
package com.uprank.uprank.teacher.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class CourseResponse {

    @SerializedName("code")
    private String mCode;
    @SerializedName("course")
    private List<Course> mCourse;

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public List<Course> getCourse() {
        return mCourse;
    }

    public void setCourse(List<Course> course) {
        mCourse = course;
    }

}

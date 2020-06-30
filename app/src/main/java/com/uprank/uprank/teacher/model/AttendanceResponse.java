
package com.uprank.uprank.teacher.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AttendanceResponse {

    @SerializedName("attendance")
    private List<Attendance> mAttendance;
    @SerializedName("code")
    private String mCode;
    @SerializedName("msg")
    private String mMsg;

    public List<Attendance> getAttendance() {
        return mAttendance;
    }

    public void setAttendance(List<Attendance> attendance) {
        mAttendance = attendance;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

}

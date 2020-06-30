
package com.uprank.uprank.teacher.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class TimetableResponse {

    @SerializedName("code")
    private String mCode;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("timetable")
    private List<Timetable> mTimetable;

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

    public List<Timetable> getTimetable() {
        return mTimetable;
    }

    public void setTimetable(List<Timetable> timetable) {
        mTimetable = timetable;
    }

}

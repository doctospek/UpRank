
package com.uprank.uprank.teacher.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class HomeworkResponse {

    @SerializedName("code")
    private String mCode;
    @SerializedName("homework")
    private List<Homework> mHomework;
    @SerializedName("msg")
    private String mMsg;

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public List<Homework> getHomework() {
        return mHomework;
    }

    public void setHomework(List<Homework> homework) {
        mHomework = homework;
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

}

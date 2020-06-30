
package com.uprank.uprank.teacher.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LeaveResponse {

    @SerializedName("code")
    private String mCode;
    @SerializedName("leave")
    private List<Leave> mLeave;
    @SerializedName("msg")
    private String mMsg;

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public List<Leave> getLeave() {
        return mLeave;
    }

    public void setLeave(List<Leave> leave) {
        mLeave = leave;
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

}

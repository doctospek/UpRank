
package com.uprank.uprank.teacher.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class NoticeResponse {

    @SerializedName("code")
    private String mCode;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("notice")
    private List<Notice> mNotice;

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

    public List<Notice> getNotice() {
        return mNotice;
    }

    public void setNotice(List<Notice> notice) {
        mNotice = notice;
    }

}

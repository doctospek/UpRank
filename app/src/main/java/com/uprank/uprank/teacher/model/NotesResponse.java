
package com.uprank.uprank.teacher.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class NotesResponse {

    @SerializedName("code")
    private String mCode;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("notes")
    private List<Note> mNotes;

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

    public List<Note> getNotes() {
        return mNotes;
    }

    public void setNotes(List<Note> notes) {
        mNotes = notes;
    }

}


package com.uprank.uprank.teacher.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LoginResponse {

    @Expose
    @SerializedName("code")
    private String code;
    @Expose
    @SerializedName("data")
    private List<Staff> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Staff> getData() {
        return data;
    }

    public void setData(List<Staff> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "code='" + code + '\'' +
                ", data=" + data +
                '}';
    }
}

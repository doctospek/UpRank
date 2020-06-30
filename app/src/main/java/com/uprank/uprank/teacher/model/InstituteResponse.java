
package com.uprank.uprank.teacher.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class InstituteResponse {

    @SerializedName("code")
    private String mCode;
    @SerializedName("institute")
    private List<Institute> mInstitute;

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public List<Institute> getInstitute() {
        return mInstitute;
    }

    public void setInstitute(List<Institute> institute) {
        mInstitute = institute;
    }

}


package com.uprank.uprank.teacher.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class BatchResponse {

    @SerializedName("batch")
    private List<Batch> mBatch;
    @SerializedName("code")
    private String mCode;

    public List<Batch> getBatch() {
        return mBatch;
    }

    public void setBatch(List<Batch> batch) {
        mBatch = batch;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

}


package com.uprank.uprank.teacher.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Leave {

    @SerializedName("approved_status")
    private String mApprovedStatus;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("from_date")
    private String mFromDate;
    @SerializedName("id")
    private String mId;
    @SerializedName("institute_id")
    private String mInstituteId;
    @SerializedName("leave_day_count")
    private String mLeaveDayCount;
    @SerializedName("leave_reason")
    private String mLeaveReason;
    @SerializedName("leave_type")
    private Object mLeaveType;
    @SerializedName("staff_id")
    private String mStaffId;
    @SerializedName("to_date")
    private String mToDate;

    public String getApprovedStatus() {
        return mApprovedStatus;
    }

    public void setApprovedStatus(String approvedStatus) {
        mApprovedStatus = approvedStatus;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getFromDate() {
        return mFromDate;
    }

    public void setFromDate(String fromDate) {
        mFromDate = fromDate;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getInstituteId() {
        return mInstituteId;
    }

    public void setInstituteId(String instituteId) {
        mInstituteId = instituteId;
    }

    public String getLeaveDayCount() {
        return mLeaveDayCount;
    }

    public void setLeaveDayCount(String leaveDayCount) {
        mLeaveDayCount = leaveDayCount;
    }

    public String getLeaveReason() {
        return mLeaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        mLeaveReason = leaveReason;
    }

    public Object getLeaveType() {
        return mLeaveType;
    }

    public void setLeaveType(Object leaveType) {
        mLeaveType = leaveType;
    }

    public String getStaffId() {
        return mStaffId;
    }

    public void setStaffId(String staffId) {
        mStaffId = staffId;
    }

    public String getToDate() {
        return mToDate;
    }

    public void setToDate(String toDate) {
        mToDate = toDate;
    }

}

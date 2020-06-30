
package com.uprank.uprank.teacher.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Homework {

    @SerializedName("action")
    private Object mAction;
    @SerializedName("batch")
    private String mBatch;
    @SerializedName("course")
    private String mCourse;
    @SerializedName("evaluation_date")
    private Object mEvaluationDate;
    @SerializedName("file")
    private String mFile;
    @SerializedName("file_tag")
    private String mFileTag;
    @SerializedName("homework_date")
    private String mHomeworkDate;
    @SerializedName("id")
    private String mId;
    @SerializedName("institute_id")
    private String mInstituteId;
    @SerializedName("staff_id")
    private String mStaffId;
    @SerializedName("status")
    private Object mStatus;
    @SerializedName("submission_date")
    private String mSubmissionDate;
    @SerializedName("batch_name")
    private String mBatchName;
    @SerializedName("course_name")
    private String mCourseName;

    public Object getAction() {
        return mAction;
    }

    public void setAction(Object action) {
        mAction = action;
    }

    public String getBatch() {
        return mBatch;
    }

    public void setBatch(String batch) {
        mBatch = batch;
    }

    public String getCourse() {
        return mCourse;
    }

    public void setCourse(String course) {
        mCourse = course;
    }

    public Object getEvaluationDate() {
        return mEvaluationDate;
    }

    public void setEvaluationDate(Object evaluationDate) {
        mEvaluationDate = evaluationDate;
    }

    public String getFile() {
        return mFile;
    }

    public void setFile(String file) {
        mFile = file;
    }

    public String getFileTag() {
        return mFileTag;
    }

    public void setFileTag(String fileTag) {
        mFileTag = fileTag;
    }

    public String getHomeworkDate() {
        return mHomeworkDate;
    }

    public void setHomeworkDate(String homeworkDate) {
        mHomeworkDate = homeworkDate;
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

    public String getStaffId() {
        return mStaffId;
    }

    public void setStaffId(String staffId) {
        mStaffId = staffId;
    }

    public Object getStatus() {
        return mStatus;
    }

    public void setStatus(Object status) {
        mStatus = status;
    }

    public String getSubmissionDate() {
        return mSubmissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        mSubmissionDate = submissionDate;
    }

    public String getmBatchName() {
        return mBatchName;
    }

    public void setmBatchName(String mBatchName) {
        this.mBatchName = mBatchName;
    }

    public String getmCourseName() {
        return mCourseName;
    }

    public void setmCourseName(String mCourseName) {
        this.mCourseName = mCourseName;
    }
}

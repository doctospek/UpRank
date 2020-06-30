
package com.uprank.uprank.teacher.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Note {

    @SerializedName("batch")
    private String mBatch;
    @SerializedName("batch_name")
    private String mBatchName;
    @SerializedName("chapter")
    private String mChapter;
    @SerializedName("course")
    private String mCourse;
    @SerializedName("course_name")
    private String mCourseName;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("file_name")
    private String mFileName;
    @SerializedName("file_tag")
    private String mFileTag;
    @SerializedName("id")
    private String mId;
    @SerializedName("staff_id")
    private String mStaffId;
    @SerializedName("institute_id")
    private String mInstituteId;

    public String getBatch() {
        return mBatch;
    }

    public void setBatch(String batch) {
        mBatch = batch;
    }

    public String getBatchName() {
        return mBatchName;
    }

    public void setBatchName(String batchName) {
        mBatchName = batchName;
    }

    public String getChapter() {
        return mChapter;
    }

    public void setChapter(String chapter) {
        mChapter = chapter;
    }

    public String getCourse() {
        return mCourse;
    }

    public void setCourse(String course) {
        mCourse = course;
    }

    public String getCourseName() {
        return mCourseName;
    }

    public void setCourseName(String courseName) {
        mCourseName = courseName;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getFileName() {
        return mFileName;
    }

    public void setFileName(String fileName) {
        mFileName = fileName;
    }

    public String getFileTag() {
        return mFileTag;
    }

    public void setFileTag(String fileTag) {
        mFileTag = fileTag;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getStaffId() {
        return mStaffId;
    }

    public void setStaffId(String staffId) {
        mStaffId = staffId;
    }

    public String getmInstituteId() {
        return mInstituteId;
    }

    public void setmInstituteId(String mInstituteId) {
        this.mInstituteId = mInstituteId;
    }
}

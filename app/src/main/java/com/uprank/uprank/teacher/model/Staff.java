
package com.uprank.uprank.teacher.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Staff {

    @Expose
    @SerializedName("age")
    private String age;
    @SerializedName("current_address")
    private String currentAddress;
    @Expose
    @SerializedName("date")
    private String date;
    @Expose
    @SerializedName("dob")
    private String dob;
    @Expose
    @SerializedName("email")
    private String email;
    @Expose
    @SerializedName("fname")
    private String fname;
    @Expose
    @SerializedName("gender")
    private String gender;
    @SerializedName("highest_education")
    private String highestEducation;
    @Expose
    @SerializedName("id")
    private String id;
    @SerializedName("institute_id")
    private String instituteId;
    @Expose
    @SerializedName("lname")
    private String lname;
    @Expose
    @SerializedName("mname")
    private String mname;
    @Expose
    @SerializedName("mobile")
    private String mobile;
    @Expose
    @SerializedName("password")
    private String password;
    @Expose
    @SerializedName("percentage")
    private String percentage;
    @SerializedName("permanent_address")
    private String permanentAddress;
    @Expose
    @SerializedName("specialization")
    private String specialization;
    @Expose
    @SerializedName("university")
    private String university;
    @Expose
    @SerializedName("year")
    private String year;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHighestEducation() {
        return highestEducation;
    }

    public void setHighestEducation(String highestEducation) {
        this.highestEducation = highestEducation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(String instituteId) {
        this.instituteId = instituteId;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "age='" + age + '\'' +
                ", currentAddress='" + currentAddress + '\'' +
                ", date='" + date + '\'' +
                ", dob='" + dob + '\'' +
                ", email='" + email + '\'' +
                ", fname='" + fname + '\'' +
                ", gender='" + gender + '\'' +
                ", highestEducation='" + highestEducation + '\'' +
                ", id='" + id + '\'' +
                ", instituteId='" + instituteId + '\'' +
                ", lname='" + lname + '\'' +
                ", mname='" + mname + '\'' +
                ", mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                ", percentage='" + percentage + '\'' +
                ", permanentAddress='" + permanentAddress + '\'' +
                ", specialization='" + specialization + '\'' +
                ", university='" + university + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}

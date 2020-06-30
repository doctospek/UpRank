package com.uprank.uprank.teacher.webservices;

import com.google.gson.JsonObject;
import com.uprank.uprank.teacher.model.AttendanceResponse;
import com.uprank.uprank.teacher.model.BatchResponse;
import com.uprank.uprank.teacher.model.CourseResponse;
import com.uprank.uprank.teacher.model.HomeworkResponse;
import com.uprank.uprank.teacher.model.InstituteResponse;
import com.uprank.uprank.teacher.model.LeaveResponse;
import com.uprank.uprank.teacher.model.LoginResponse;
import com.uprank.uprank.teacher.model.NotesResponse;
import com.uprank.uprank.teacher.model.NoticeResponse;
import com.uprank.uprank.teacher.model.TimetableResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("login.php")
    Call<LoginResponse> login(@Query("mobile") String mobile, @Query("password") String password);

    @GET("get_staff_institute.php")
    Call<InstituteResponse> get_staff_institute(@Query("id") int id);

    @GET("check_staff_today_attendance.php")
    Call<JsonObject> check_staff_today_attendance(@Query("staff_id") int staff_id, @Query("institute_id") int institute_id);

    @GET("add_staff_attendance.php")
    Call<JsonObject> add_staff_attendance(@Query("staff_id") int staff_id,
                                          @Query("clock_in") String clock_in,
                                          @Query("clockin_lat") String clockin_lat,
                                          @Query("clockin_long") String clockin_long,
                                          @Query("institute_id") int institute_id);

    @GET("update_staff_attendance.php")
    Call<JsonObject> update_staff_attendance(@Query("staff_id") int staff_id,
                                             @Query("id") int id,
                                             @Query("clock_out") String clock_out,
                                             @Query("total_working_hours") String total_working_hours,
                                             @Query("clockout_lat") String clockout_lat,
                                             @Query("clockout_long") String clockout_long,
                                             @Query("mark") String mark);

    @GET("staff_timetable.php")
    Call<TimetableResponse> staff_timetable(@Query("staff_id") int staff_id, @Query("institute_id") int institute_id, @Query("day") String day);

    @GET("get_staff_present_days.php")
    Call<JsonObject> get_staff_present_days(@Query("staff_id") int staff_id, @Query("from") String from, @Query("to") String to);

    @GET("get_staff_absent_days.php")
    Call<JsonObject> get_staff_absent_days(@Query("staff_id") int staff_id, @Query("from") String from, @Query("to") String to);

    @GET("add_staff_leave.php")
    Call<JsonObject> add_staff_leave(@Query("staff_id") int staff_id,
                                     @Query("institute_id") int institute_id,
                                     @Query("leave_day_count") int leave_day_count,
                                     @Query("from_date") String from,
                                     @Query("leave_reason") String leave_reason,
                                     @Query("to_date") String to);

    @GET("update_staff_firebase.php")
    Call<JsonObject> update_staff_firebase(@Query("staff_id") int staff_id,
                                           @Query("token") String token);

    @GET("get_all_batch.php")
    Call<BatchResponse> get_all_batch(@Query("institute_id") int institute_id);

    @GET("get_all_course.php")
    Call<CourseResponse> get_all_course(@Query("institute_id") int institute_id);

    @GET("delete_notice.php")
    Call<JsonObject> delete_notice(@Query("id") int id);

    @GET("get_staff_notice.php")
    Call<NoticeResponse> get_staff_notice(@Query("institute_id") int institute_id);

    @GET("get_staff_attendance.php")
    Call<AttendanceResponse> get_staff_attendance(@Query("staff_id") int staff_id, @Query("institute_id") int institute_id);

    @GET("get_staff_leave_details.php")
    Call<LeaveResponse> get_staff_leave_details(@Query("staff_id") int staff_id, @Query("institute_id") int institute_id);

    @GET("get_homework.php")
    Call<HomeworkResponse> get_homework(@Query("staff_id") int staff_id, @Query("institute_id") int institute_id);

    @GET("delete_homework.php")
    Call<JsonObject> delete_homework(@Query("id") int id);

    @GET("get_notes.php")
    Call<NotesResponse> get_notes(@Query("staff_id") int staff_id);

}

package com.uprank.uprank.teacher.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.uprank.uprank.R;
import com.uprank.uprank.teacher.adapter.DayAdapter;
import com.uprank.uprank.teacher.model.Batch;
import com.uprank.uprank.teacher.model.BatchResponse;
import com.uprank.uprank.teacher.model.Course;
import com.uprank.uprank.teacher.model.CourseResponse;
import com.uprank.uprank.teacher.model.Staff;
import com.uprank.uprank.teacher.model.Timetable;
import com.uprank.uprank.teacher.model.TimetableResponse;
import com.uprank.uprank.teacher.utility.CommonUtils;
import com.uprank.uprank.teacher.utility.Pref;
import com.uprank.uprank.teacher.webservices.ApiClient;
import com.uprank.uprank.teacher.webservices.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayFragment extends Fragment {

    private Pref pref = new Pref();
    private Staff staff;
    private ApiInterface apiInterface;
    private ArrayList<Batch> batchArrayList;
    private ArrayList<Course> courseArrayList;
    private HashMap<Integer, String> integerCourseHashMap;
    private HashMap<Integer, String> integerBatchHashMap;
    private ListView listView;
    private ArrayList<Timetable> timetableArrayList;
    private String day;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_day, container, false);

        staff = pref.getStaffDataPref(getActivity());
        apiInterface = ApiClient.getClient(getActivity()).create(ApiInterface.class);
        getAllBatch();

        initView(view);

        return view;
    }

    private void initView(View view) {

        listView = view.findViewById(R.id.listview_timetable);

        day = getArguments().getString("day");

    }

    private void getAllBatch() {

        integerBatchHashMap = new HashMap<>();

        apiInterface.get_all_batch(Integer.parseInt(staff.getInstituteId())).enqueue(new Callback<BatchResponse>() {
            @Override
            public void onResponse(Call<BatchResponse> call, Response<BatchResponse> response) {

                if (response.body().getCode().equals("200")) {

                    //CommonUtils.successToast(getActivity(), "All Batch Retrieved");

                    batchArrayList = (ArrayList<Batch>) response.body().getBatch();

                    for (Batch batch : batchArrayList) {

                        integerBatchHashMap.put(batch.getId(), batch.getName());
                    }
                    getAllCourse();

                } else {
                    //CommonUtils.errorToast(getActivity(), "No Batch Retrieved");

                }

            }

            @Override
            public void onFailure(Call<BatchResponse> call, Throwable t) {

            }
        });

    }

    private void getAllCourse() {

        integerCourseHashMap = new HashMap<>();
        apiInterface.get_all_course(Integer.parseInt(staff.getInstituteId())).enqueue(new Callback<CourseResponse>() {
            @Override
            public void onResponse(Call<CourseResponse> call, Response<CourseResponse> response) {


                if (response.body().getCode().equals("200")) {

                    courseArrayList = (ArrayList<Course>) response.body().getCourse();

                    for (Course course : courseArrayList) {

                        integerCourseHashMap.put(course.getId(), course.getName());
                    }

                    getTimetable();

                } else {

                }
            }

            @Override
            public void onFailure(Call<CourseResponse> call, Throwable t) {

            }
        });
    }

    private void getTimetable() {

        apiInterface.staff_timetable(Integer.parseInt(staff.getId()), Integer.parseInt(staff.getInstituteId()), day).enqueue(new Callback<TimetableResponse>() {
            @Override
            public void onResponse(Call<TimetableResponse> call, Response<TimetableResponse> response) {

                if (response.body().getCode().equals("200")) {

                    //CommonUtils.successToast(getActivity(), response.body().getMsg());

                    timetableArrayList = (ArrayList<Timetable>) response.body().getTimetable();

                    DayAdapter dayAdapter = new DayAdapter(getActivity(), timetableArrayList, integerCourseHashMap, integerBatchHashMap);
                    listView.setAdapter(dayAdapter);

                } else {
                    //CommonUtils.errorToast(getActivity(), response.body().getMsg());
                }

            }

            @Override
            public void onFailure(Call<TimetableResponse> call, Throwable t) {

            }
        });


    }


}

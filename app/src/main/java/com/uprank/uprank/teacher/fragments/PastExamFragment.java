package com.uprank.uprank.teacher.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.uprank.uprank.R;
import com.uprank.uprank.teacher.adapter.ExamAdapter;
import com.uprank.uprank.teacher.model.Exam;
import com.uprank.uprank.teacher.model.ExamResponse;
import com.uprank.uprank.teacher.model.Staff;
import com.uprank.uprank.teacher.utility.CommonUtils;
import com.uprank.uprank.teacher.utility.Pref;
import com.uprank.uprank.teacher.webservices.ApiClient;
import com.uprank.uprank.teacher.webservices.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PastExamFragment extends Fragment {

    private ListView listView;
    private Staff staff;
    private Pref pref = new Pref();
    private ApiInterface apiInterface;
    private ExamAdapter examAdapter;
    private ArrayList<Exam> examArrayList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_past_exam, container, false);

        staff = pref.getStaffDataPref(getActivity());
        apiInterface = ApiClient.getClient(getActivity()).create(ApiInterface.class);

        initView(view);


        return view;
    }

    private void initView(View view) {


        listView = view.findViewById(R.id.listview_past);

        getExams();
    }

    private void getExams() {

        apiInterface.get_past_exams(Integer.parseInt(staff.getId())).enqueue(new Callback<ExamResponse>() {
            @Override
            public void onResponse(Call<ExamResponse> call, Response<ExamResponse> response) {

                if (response.body().getCode().equals("200")) {

                    examArrayList = (ArrayList<Exam>) response.body().getExam();
                    examAdapter = new ExamAdapter(getActivity(), examArrayList);
                    listView.setAdapter(examAdapter);

                } else {
                    //CommonUtils.errorToast(getActivity(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ExamResponse> call, Throwable t) {

            }
        });
    }
}

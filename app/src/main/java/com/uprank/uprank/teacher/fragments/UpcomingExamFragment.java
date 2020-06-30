package com.uprank.uprank.teacher.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.uprank.uprank.R;
import com.uprank.uprank.teacher.activity.AddExamActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingExamFragment extends Fragment implements View.OnClickListener {


    FloatingActionButton floatingActionButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upcoming_exam, container, false);

        initView(view);


        return view;
    }

    private void initView(View view) {

        floatingActionButton = view.findViewById(R.id.add_exam);
        floatingActionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.add_exam:

                startActivity(new Intent(getActivity(), AddExamActivity.class));

                break;
        }
    }
}

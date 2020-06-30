package com.uprank.uprank.teacher.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.uprank.uprank.teacher.fragments.PastExamFragment;
import com.uprank.uprank.teacher.fragments.UpcomingExamFragment;

public class ExamScheduleTabLayoutAdapter extends FragmentPagerAdapter {


    private int totalTabs;
    private Context myContext;


    public ExamScheduleTabLayoutAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm, totalTabs);

        this.myContext = context;
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:

                UpcomingExamFragment upcomingExamFragment = new UpcomingExamFragment();
                return upcomingExamFragment;

            case 1:

                PastExamFragment pastExamFragment = new PastExamFragment();
                return pastExamFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}

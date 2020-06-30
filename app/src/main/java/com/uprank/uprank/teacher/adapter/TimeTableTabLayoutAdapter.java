package com.uprank.uprank.teacher.adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.uprank.uprank.teacher.fragments.DayFragment;


import java.util.ArrayList;

public class TimeTableTabLayoutAdapter extends FragmentPagerAdapter {


    private int totalTabs;
    private Context myContext;
    private ArrayList<String> day = new ArrayList<>();


    public TimeTableTabLayoutAdapter(Context context, FragmentManager fm, int totalTabs, ArrayList<String> day) {
        super(fm, totalTabs);

        this.myContext = context;
        this.totalTabs = totalTabs;
        this.day = day;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Bundle bundle = new Bundle();
                bundle.putString("day", "Monday");
                DayFragment DayFragment = new DayFragment();
                DayFragment.setArguments(bundle);

                return DayFragment;
            case 1:
                Bundle bundle1 = new Bundle();
                bundle1.putString("day", "Tuesday");

                DayFragment DayFragment1 = new DayFragment();
                DayFragment1.setArguments(bundle1);

                return DayFragment1;
            case 2:
                Bundle bundle2 = new Bundle();
                bundle2.putString("day", "Wednesday");

                DayFragment DayFragment2 = new DayFragment();
                DayFragment2.setArguments(bundle2);

                return DayFragment2;
            case 3:
                Bundle bundle3 = new Bundle();
                bundle3.putString("day", "Thursday");

                DayFragment DayFragment3 = new DayFragment();
                DayFragment3.setArguments(bundle3);

                return DayFragment3;
            case 4:
                Bundle bundle4 = new Bundle();
                bundle4.putString("day", "Friday");

                DayFragment DayFragment4 = new DayFragment();
                DayFragment4.setArguments(bundle4);

                return DayFragment4;
            case 5:
                Bundle bundle5 = new Bundle();
                bundle5.putString("day", "Saturday");

                DayFragment DayFragment5 = new DayFragment();
                DayFragment5.setArguments(bundle5);

                return DayFragment5;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}

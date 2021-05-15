package com.uprank.uprank.teacher.maincategory;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.uprank.uprank.R;
import com.uprank.uprank.teacher.activity.MainActivity;
import com.uprank.uprank.teacher.adapter.TimeTableTabLayoutAdapter;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class TimeTableActivity extends AppCompatActivity {

    private static ViewPager tabPager;
    TabLayout tabLayout;
    HashMap<String, Integer> hashMap = new HashMap<>();
    String dayOfTheWeek;
    ArrayList<String> dateStringArray = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        initView();

    }

    private void initView() {

        tabLayout = findViewById(R.id.tabs);
        tabPager = findViewById(R.id.pagerTab);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //startActivity(new Intent(getBaseContext(), MainActivity.class));
                onBackPressed();
            }
        });

        //toolbar.inflateMenu(R.menu.main_menu);

        Date c = Calendar.getInstance().getTime();
        dayOfTheWeek = (String) DateFormat.format("EEEE", c); // Thursday
        String day = (String) DateFormat.format("dd", c); // 20
        String monthString = (String) DateFormat.format("MMM", c); // Jun
        String monthNumber = (String) DateFormat.format("MM", c); // 06
        String year = (String) DateFormat.format("yyyy", c); // 2013


        Calendar calendar = Calendar.getInstance();
        // Set the calendar to monday of the current week
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        // Print dates of the current week starting on Monday
        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
        for (int i = 0; i < 7; i++) {
            dateStringArray.add(df.format(calendar.getTime()));
            calendar.add(Calendar.DATE, 1);
        }

        tabLayout.addTab(tabLayout.newTab().setText("Monday"));
        tabLayout.addTab(tabLayout.newTab().setText("Tuesday"));
        tabLayout.addTab(tabLayout.newTab().setText("Wednesday"));
        tabLayout.addTab(tabLayout.newTab().setText("Thursday"));
        tabLayout.addTab(tabLayout.newTab().setText("Friday"));
        tabLayout.addTab(tabLayout.newTab().setText("Saturday"));

        hashMap.put("Monday", 0);
        hashMap.put("Tuesday", 1);
        hashMap.put("Wednesday", 2);
        hashMap.put("Thursday", 3);
        hashMap.put("Friday", 4);
        hashMap.put("Saturday", 5);

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#0039cb"));
        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#0039cb"));
        tabPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        TimeTableTabLayoutAdapter timeTableTabLayoutAdapter = new TimeTableTabLayoutAdapter(TimeTableActivity.this, getSupportFragmentManager(), tabLayout.getTabCount(), dateStringArray);
        tabPager.setAdapter(timeTableTabLayoutAdapter);

        if (hashMap.containsKey(dayOfTheWeek)) {
            tabPager.setCurrentItem(hashMap.get(dayOfTheWeek));
        } else {
            tabPager.setCurrentItem(0);
        }


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_details:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

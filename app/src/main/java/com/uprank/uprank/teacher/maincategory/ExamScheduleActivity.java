package com.uprank.uprank.teacher.maincategory;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.uprank.uprank.R;
import com.uprank.uprank.teacher.activity.AddExamActivity;
import com.uprank.uprank.teacher.activity.HomeActivity;
import com.uprank.uprank.teacher.adapter.ExamScheduleTabLayoutAdapter;

public class ExamScheduleActivity extends AppCompatActivity implements View.OnClickListener {

    private static ViewPager tabPager;
    TabLayout tabLayout;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_schedule);

        initView();
    }

    private void initView() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getBaseContext(), HomeActivity.class));
            }
        });

        toolbar.inflateMenu(R.menu.main_menu);

        tabLayout = findViewById(R.id.tabs);
        tabPager = findViewById(R.id.pagerTab);

        tabLayout.addTab(tabLayout.newTab().setText("Upcoming Exams"));
        tabLayout.addTab(tabLayout.newTab().setText("Past Exams"));

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#0039cb"));
        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#0039cb"));
        tabPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        ExamScheduleTabLayoutAdapter examScheduleTabLayoutAdapter = new ExamScheduleTabLayoutAdapter(getBaseContext(), getSupportFragmentManager(), tabLayout.getTabCount());
        tabPager.setAdapter(examScheduleTabLayoutAdapter);

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

        floatingActionButton = findViewById(R.id.button_add);
        floatingActionButton.setOnClickListener(this);

    }

    @Override
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
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button_add:

                startActivity(new Intent(ExamScheduleActivity.this, AddExamActivity.class));

                break;
        }
    }
}

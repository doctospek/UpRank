package com.uprank.uprank.teacher.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.uprank.uprank.R;
import com.uprank.uprank.teacher.utility.Pref;

public class LogoutActivity extends AppCompatActivity {

    Pref pref = new Pref();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        pref.clearSharedPref(LogoutActivity.this);
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }
}
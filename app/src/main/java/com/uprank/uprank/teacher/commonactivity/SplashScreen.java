package com.uprank.uprank.teacher.commonactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.uprank.uprank.R;
import com.uprank.uprank.teacher.activity.HomeActivity;
import com.uprank.uprank.teacher.activity.LoginActivity;
import com.uprank.uprank.teacher.model.Staff;
import com.uprank.uprank.teacher.utility.Pref;

public class SplashScreen extends AppCompatActivity {

    Pref pref = new Pref();
    Staff staff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        staff = pref.getStaffDataPref(SplashScreen.this);


        ImageView imageView = findViewById(R.id.imageView_splashlogo);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_effect); // Create the animation.
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if (staff == null) {
                    startActivity(new Intent(getBaseContext(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getBaseContext(), HomeActivity.class));
                }


            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        imageView.startAnimation(animation);
    }
}

package com.uprank.uprank.teacher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.uprank.uprank.R;
import com.uprank.uprank.teacher.model.Institute;
import com.uprank.uprank.teacher.model.InstituteResponse;
import com.uprank.uprank.teacher.model.LoginResponse;
import com.uprank.uprank.teacher.model.Staff;
import com.uprank.uprank.teacher.utility.CommonUtils;
import com.uprank.uprank.teacher.utility.Pref;
import com.uprank.uprank.teacher.webservices.ApiClient;
import com.uprank.uprank.teacher.webservices.ApiInterface;
import com.google.android.material.textfield.TextInputEditText;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText textInputEditText;
    ShowHidePasswordEditText showHidePasswordEditText;
    Button button;
    TextView textView_forgot_password;
    ApiInterface apiInterface;
    String str_mobile, str_password;
    Staff staff;
    Pref pref = new Pref();
    Institute institute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {

        textInputEditText = findViewById(R.id.email);
        showHidePasswordEditText = findViewById(R.id.password);
        button = findViewById(R.id.button_login);
        textView_forgot_password = findViewById(R.id.text_forgot_password);
        apiInterface = ApiClient.getClient(LoginActivity.this).create(ApiInterface.class);
        button.setOnClickListener(this);
        textView_forgot_password.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.text_forgot_password:
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
                break;

            case R.id.button_login:
                str_mobile = textInputEditText.getText().toString();
                str_password = showHidePasswordEditText.getText().toString();

                if (CommonUtils.isNetworkAvailable(LoginActivity.this)) {

                    Login();

                } else {
                    CommonUtils.warningToast(LoginActivity.this);
                }


                break;

        }

    }

    private void Login() {

        apiInterface.login(str_mobile, str_password).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.body().getCode().equals("200")) {


                    staff = response.body().getData().get(0);
                    pref.setStaffDataPref(LoginActivity.this, staff);

                    getInstitute();

                } else {


                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }

    private void getInstitute() {

        apiInterface.get_staff_institute(Integer.parseInt(staff.getInstituteId())).enqueue(new Callback<InstituteResponse>() {
            @Override
            public void onResponse(Call<InstituteResponse> call, Response<InstituteResponse> response) {

                if (response.body().getCode().equals("200")) {
                    CommonUtils.successToast(LoginActivity.this, "Login Successful !");


                    institute = response.body().getInstitute().get(0);
                    pref.setInstitutePref(LoginActivity.this, institute);

                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));


                } else {

                    CommonUtils.errorToast(LoginActivity.this, "Login Failed !");


                }
            }

            @Override
            public void onFailure(Call<InstituteResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(LoginActivity.this, ExitActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);

        startActivity(intent);
    }

}

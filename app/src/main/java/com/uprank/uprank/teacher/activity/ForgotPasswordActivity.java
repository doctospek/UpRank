package com.uprank.uprank.teacher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.uprank.uprank.R;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editText_email;
    ShowHidePasswordEditText editText_new_password, editText_confirm_password;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initView();
    }

    private void initView() {

        editText_email = findViewById(R.id.edit_email);
        editText_new_password = findViewById(R.id.edit_password);
        editText_confirm_password = findViewById(R.id.edit_confirm_password);
        button = findViewById(R.id.button_update_password);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getBaseContext(), LoginActivity.class));
            }
        });

        toolbar.inflateMenu(R.menu.main_menu);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.button_update_password) {
            startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
        }

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
}

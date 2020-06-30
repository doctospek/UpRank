package com.uprank.uprank.teacher.maincategory;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.uprank.uprank.R;
import com.uprank.uprank.teacher.activity.AddNotesActivity;
import com.uprank.uprank.teacher.activity.HomeActivity;
import com.uprank.uprank.teacher.adapter.NotesAdapter;
import com.uprank.uprank.teacher.model.Note;
import com.uprank.uprank.teacher.model.NotesResponse;
import com.uprank.uprank.teacher.model.Staff;
import com.uprank.uprank.teacher.utility.CommonUtils;
import com.uprank.uprank.teacher.utility.Pref;
import com.uprank.uprank.teacher.webservices.ApiClient;
import com.uprank.uprank.teacher.webservices.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotesActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private ApiInterface apiInterface;
    private Staff staff;
    private Pref pref = new Pref();
    private NotesAdapter notesAdapter;
    private List<Note> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        staff = pref.getStaffDataPref(NotesActivity.this);
        apiInterface = ApiClient.getClient(NotesActivity.this).create(ApiInterface.class);

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

        floatingActionButton = findViewById(R.id.button_add);
        recyclerView = findViewById(R.id.recyclerview_notes);

        floatingActionButton.setOnClickListener(this::onClick);

        getNotes();
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

                startActivity(new Intent(NotesActivity.this, AddNotesActivity.class));

                break;

        }

    }

    private void getNotes() {

        apiInterface.get_notes(Integer.parseInt(staff.getId())).enqueue(new Callback<NotesResponse>() {
            @Override
            public void onResponse(Call<NotesResponse> call, Response<NotesResponse> response) {

                if (response.body().getCode().equals("200")) {

                    noteList = response.body().getNotes();

                    notesAdapter = new NotesAdapter(NotesActivity.this, noteList);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(notesAdapter);


                } else {

                    CommonUtils.errorToast(NotesActivity.this, response.body().getMsg());
                }

            }

            @Override
            public void onFailure(Call<NotesResponse> call, Throwable t) {

            }
        });
    }
}

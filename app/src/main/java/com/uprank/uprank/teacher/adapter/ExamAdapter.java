package com.uprank.uprank.teacher.adapter;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.gson.JsonObject;
import com.uprank.uprank.R;
import com.uprank.uprank.teacher.maincategory.ExamScheduleActivity;
import com.uprank.uprank.teacher.model.Exam;
import com.uprank.uprank.teacher.model.Staff;
import com.uprank.uprank.teacher.utility.CommonUtils;
import com.uprank.uprank.teacher.utility.Pref;
import com.uprank.uprank.teacher.webservices.ApiClient;
import com.uprank.uprank.teacher.webservices.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExamAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Exam> examArrayList;
    private static final String URL = "http://saiinfra.co.in/drline.saiinfra.co.in/uprank/app_api/teacher_api/uploads/";
    private ProgressDialog progressDialog;
    private ApiInterface apiInterface;
    private Staff staff;
    private Pref pref = new Pref();


    public ExamAdapter(Context context, ArrayList<Exam> examArrayList) {
        this.context = context;
        this.examArrayList = examArrayList;
        apiInterface = ApiClient.getClient(context).create(ApiInterface.class);
        staff = pref.getStaffDataPref(context);
    }

    @Override
    public int getCount() {
        return examArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return examArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.upcoming_examview, parent, false);

        TextView textView_title = convertView.findViewById(R.id.text_exam_title);
        TextView text_course_name = convertView.findViewById(R.id.text_course_name);
        TextView text_batch_name = convertView.findViewById(R.id.text_batch_name);
        TextView text_subject_name = convertView.findViewById(R.id.text_subject_name);
        TextView text_chapter_name = convertView.findViewById(R.id.text_chapter_name);
        TextView text_exam_date = convertView.findViewById(R.id.text_exam_date);
        ImageView imageView_download = convertView.findViewById(R.id.image_download);
        ImageView imageView_delete = convertView.findViewById(R.id.delete_exam);

        Exam exam = examArrayList.get(position);

        textView_title.setText(exam.getExamTitle());
        text_course_name.setText(exam.getCourseName());
        text_batch_name.setText(exam.getBatchName());
        text_subject_name.setText(exam.getSubject());
        text_chapter_name.setText(exam.getChapter());
        text_exam_date.setText(exam.getExamDate());

        imageView_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Download().execute(URL + exam.getQuationPaper(), exam.getQuationPaper());

            }
        });

        imageView_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDeleteDialog(Integer.parseInt(exam.getId()));
            }
        });


        return convertView;
    }

    private class Download extends AsyncTask<String, Void, Void> {

        File direct = null;
        String fileName = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Downloading");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            fileName = strings[1];  // -> maven.pdf

            direct =
                    new File(Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                            .getAbsolutePath() + "/" + "UpRank" + "/");


            if (!direct.exists()) {
                direct.mkdir();
                Log.d("DOWNLOADIMAGE", "dir created for first time");
            }

            DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri downloadUri = Uri.parse(fileUrl);
            DownloadManager.Request request = new DownloadManager.Request(downloadUri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle(fileName)
                    .setMimeType("*/*")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                            File.separator + "UpRank" + File.separator + fileName);

            dm.enqueue(request);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            super.onPostExecute(aVoid);

            try {
                if (direct != null) {

                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Download");
                    alertDialogBuilder.setMessage("Exam Schedule Downloaded Successfully");
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.dismiss();
                        }
                    });

                    alertDialogBuilder.show();

                } else {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                        }
                    }, 3000);

                    Log.e("Download Failed", "Download Failed");

                    CommonUtils.errorToast(context, "Download Failed");

                }
            } catch (Exception e) {
                e.printStackTrace();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 3000);
                Log.e("Download Failed", "Download Failed with Exception - " + e.getLocalizedMessage());

                CommonUtils.errorToast(context, "Download Failed");

            }

        }
    }

    private void deleteExam(int id) {

        apiInterface.delete_exam(id, Integer.parseInt(staff.getId())).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    String code = jsonObject.getString("code");
                    String msg = jsonObject.getString("msg");

                    if (code.equals("200")) {

                        CommonUtils.successToast(context, msg);
                        context.startActivity(new Intent(context, ExamScheduleActivity.class));

                    } else {
                        CommonUtils.errorToast(context, msg);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }

    private void showDeleteDialog(int id) {


        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Do you want to delete ?");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

                dialog.dismiss();
            }
        });

        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteExam(id);
                    }
                });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}

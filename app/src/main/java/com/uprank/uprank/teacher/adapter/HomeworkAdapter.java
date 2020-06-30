package com.uprank.uprank.teacher.adapter;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
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
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;

import com.google.gson.JsonObject;
import com.uprank.uprank.R;
import com.uprank.uprank.teacher.maincategory.HomeWorkActivity;
import com.uprank.uprank.teacher.maincategory.NoticeBoardActivity;
import com.uprank.uprank.teacher.model.Batch;
import com.uprank.uprank.teacher.model.Course;
import com.uprank.uprank.teacher.model.Homework;
import com.uprank.uprank.teacher.model.Staff;
import com.uprank.uprank.teacher.utility.CommonUtils;
import com.uprank.uprank.teacher.utility.FileDownloader;
import com.uprank.uprank.teacher.utility.Pref;
import com.uprank.uprank.teacher.webservices.ApiClient;
import com.uprank.uprank.teacher.webservices.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeworkAdapter extends BaseAdapter {

    private static final String IMAGE_URL = ApiClient.BASE_URL + "uploads/";
    private Context context;
    private ArrayList<Homework> arrayList;
    private ProgressDialog progressDialog;
    private ArrayList<Batch> batchArrayList;
    private ArrayList<Course> courseArrayList;
    private HashMap<String, Integer> integerCourseHashMap;
    private HashMap<String, Integer> integerBatchHashMap;
    private String[] color_array = {"#D1C4E9", "#C5CAE9", "#E1BEE7", "#F8BBD0", "#C8E6C9", "#FFCCBC"};
    private ApiInterface apiInterface;
    private Staff staff;


    public HomeworkAdapter(Context context, ArrayList<Homework> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        Pref pref = new Pref();
        staff = pref.getStaffDataPref(context);
        apiInterface = ApiClient.getClient(context).create(ApiInterface.class);

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.homework_view, parent, false);

        TextView textView_batch = convertView.findViewById(R.id.text_batch);
        TextView textView_course = convertView.findViewById(R.id.text_course);
        TextView textView_homework_date = convertView.findViewById(R.id.text_homework_date);
        TextView textView_submission_date = convertView.findViewById(R.id.text_submission_date);
        TextView textView_download = convertView.findViewById(R.id.text_download);
        CardView cardView = convertView.findViewById(R.id.card);
        ImageView imageView = convertView.findViewById(R.id.button_delete);


        Homework homework = arrayList.get(position);

        textView_batch.setText(homework.getmBatchName());
        textView_course.setText(homework.getmCourseName());
        textView_homework_date.setText(homework.getHomeworkDate().substring(0, 10));
        textView_submission_date.setText(homework.getSubmissionDate());

        textView_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (homework.getFileTag().equals("img")) {

                    new DownloadImage().execute(IMAGE_URL + homework.getFile(), homework.getFile());

                } else {

                    new DownloadFile().execute(IMAGE_URL + homework.getFile(), homework.getFile());
                }
            }
        });

        int rnd = new Random().nextInt(color_array.length);
        cardView.setCardBackgroundColor(Color.parseColor(color_array[rnd]));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDeleteDialog(Integer.parseInt(homework.getId()));

            }
        });

        return convertView;
    }

    private void grantAllUriPermissions(Context context, Intent intent, Uri uri) {
        List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        File pdfFile = null;
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

            //File path = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);

            File path =
                    new File(Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                            .getAbsolutePath());


            pdfFile = new File(path, fileName);

            try {
                pdfFile.createNewFile();
            } catch (IOException e) {

                //Utility.showLog("DownloadError", e.getMessage());
                e.printStackTrace();
            }
            FileDownloader.downloadFile(context, fileUrl, pdfFile);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            progressDialog.dismiss();
            try {
                if (pdfFile != null) {

                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Document");
                    alertDialogBuilder.setMessage("Document Downloaded Successfully");
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.dismiss();
                        }
                    });

                    alertDialogBuilder.setNegativeButton("Open report", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
//                            File pdfFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/" + fileName);

                            File pdfFile = new File(Environment
                                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                                    .getAbsolutePath() + "/" + fileName);

                            Uri path = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", pdfFile);


                            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                            pdfIntent.setDataAndType(path, "application/pdf");
                            pdfIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Intent intentChooser = Intent.createChooser(pdfIntent, "Choose Pdf Application");

                            try {
                                context.startActivity(intentChooser);
                            } catch (ActivityNotFoundException e) {
                                Toast.makeText(context, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    alertDialogBuilder.show();
                    Toast.makeText(context, "Document Downloaded Successfully", Toast.LENGTH_SHORT).show();
                } else {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                        }
                    }, 3000);

                    Log.e("Download Failed", "Download Failed");

                }
            } catch (Exception e) {
                e.printStackTrace();

                //Change button text if exception occurs

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 3000);
                Log.e("Download Failed", "Download Failed with Exception - " + e.getLocalizedMessage());

            }


            super.onPostExecute(aVoid);


        }
    }

    private class DownloadImage extends AsyncTask<String, Void, Void> {

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
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
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
                    .setMimeType("image/jpeg")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,
                            File.separator + "UpRank" + File.separator + fileName);

            dm.enqueue(request);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();

            try {
                if (direct != null) {

                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Image");
                    alertDialogBuilder.setMessage("Image Downloaded Successfully");
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.dismiss();
                        }
                    });

                    alertDialogBuilder.show();
                    Toast.makeText(context, "Image Downloaded Successfully", Toast.LENGTH_SHORT).show();
                } else {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                        }
                    }, 3000);

                    Log.e("Download Failed", "Download Failed");

                }
            } catch (Exception e) {
                e.printStackTrace();

                //Change button text if exception occurs

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 3000);
                Log.e("Download Failed", "Download Failed with Exception - " + e.getLocalizedMessage());

            }


            super.onPostExecute(aVoid);


        }
    }


    private void deleteNotice(int id) {

        apiInterface.delete_homework(id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    String code = jsonObject.getString("code");
                    String msg = jsonObject.getString("msg");

                    if (code.equals("200")) {

                        CommonUtils.successToast(context, msg);
                        context.startActivity(new Intent(context, HomeWorkActivity.class));

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
                        deleteNotice(id);
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

package com.uprank.uprank.teacher.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;
import com.uprank.uprank.R;
import com.uprank.uprank.teacher.firebase.BaseApplication;
import com.uprank.uprank.teacher.firebase.InformOtherUserService;
import com.uprank.uprank.teacher.maincategory.HomeWorkActivity;
import com.uprank.uprank.teacher.model.Batch;
import com.uprank.uprank.teacher.model.BatchResponse;
import com.uprank.uprank.teacher.model.Course;
import com.uprank.uprank.teacher.model.CourseResponse;
import com.uprank.uprank.teacher.model.Staff;
import com.uprank.uprank.teacher.utility.CommonUtils;
import com.uprank.uprank.teacher.utility.Pref;
import com.uprank.uprank.teacher.utility.VolleyMultipartRequest;
import com.uprank.uprank.teacher.webservices.ApiClient;
import com.uprank.uprank.teacher.webservices.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddHomeworkActivity extends AppCompatActivity implements View.OnClickListener {

    private SmartMaterialSpinner spinner_batch, spinner_course;
    private TextView textView;
    private Button button_selectFile, button_addAttachment;
    private ImageView imageView;
    private String submissionDate, selectedFilename, selectedBatch, selectedCourse;
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;
    private ApiInterface apiInterface;
    private Pref pref = new Pref();
    private Staff staff;
    private ArrayList<Batch> batchArrayList;
    private ArrayList<Course> courseArrayList;
    private HashMap<String, Integer> integerCourseHashMap;
    private HashMap<String, Integer> integerBatchHashMap;
    private Bitmap bitmap;
    private int GALLERY = 1, CAMERA = 2, STORAGE_PERMISSION_CODE = 123, PICK_PDF_REQUEST = 3, uploadUrl, course, batch;
    private Uri filePath;
    private byte[] inputData;
    public static final String BASE_IMAGE_URL = ApiClient.BASE_URL + "add_image_homework.php?apicall=uploadpic";
    public static final String BASE_PDF_URL = ApiClient.BASE_URL + "add_homework.php?apicall=uploadpic";
    private ArrayList<String> arrayListBatch;
    private ArrayList<String> arrayListCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_homework);

        checkPermission(Manifest.permission.CAMERA, CAMERA);
        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);

        staff = pref.getStaffDataPref(AddHomeworkActivity.this);
        apiInterface = ApiClient.getClient(AddHomeworkActivity.this).create(ApiInterface.class);
        myCalendar = Calendar.getInstance();

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
        spinner_batch = findViewById(R.id.spinner_batch);
        spinner_course = findViewById(R.id.spinner_course);
        textView = findViewById(R.id.text_submission_date);
        button_selectFile = findViewById(R.id.button_selectFile);
        button_addAttachment = findViewById(R.id.button_add_attachment);
        imageView = findViewById(R.id.image_selected_file);

        //integerCourseHashMap= (HashMap<String, Integer>) getIntent().getSerializableExtra("course");
        //integerBatchHashMap= (HashMap<String, Integer>) getIntent().getSerializableExtra("batch");

        listeners();

        getAllBatch();

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateFromLabel();
            }

        };


        spinner_course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedCourse = (String) parent.getSelectedItem();

                course = integerCourseHashMap.get(selectedCourse);

                Log.e("COURSE_ID",course+"");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_batch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                selectedBatch = (String) parent.getSelectedItem();

                batch = integerBatchHashMap.get(selectedBatch);

                Log.e("BATCH_ID",batch+"");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void listeners() {

        textView.setOnClickListener(this::onClick);
        button_selectFile.setOnClickListener(this::onClick);
        button_addAttachment.setOnClickListener(this::onClick);
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

            case R.id.text_submission_date:

                new DatePickerDialog(AddHomeworkActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();


                break;

            case R.id.button_selectFile:

                showFileFormat();

                break;

            case R.id.button_add_attachment:

                if (uploadUrl == 1) {
                    addHomework(bitmap);
                } else if (uploadUrl == 2) {

                    addPdfHomework(filePath);
                }

                break;
        }

    }

    private void updateFromLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

        textView.setText(sdf.format(myCalendar.getTime()));

        submissionDate = sdf.format(myCalendar.getTime());
    }

    private void getAllBatch() {

        integerBatchHashMap = new HashMap<>();
        arrayListBatch = new ArrayList<>();
        apiInterface.get_all_batch(Integer.parseInt(staff.getInstituteId())).enqueue(new Callback<BatchResponse>() {
            @Override
            public void onResponse(Call<BatchResponse> call, Response<BatchResponse> response) {

                if (response.body().getCode().equals("200")) {

                    //CommonUtils.successToast(getActivity(), "All Batch Retrieved");

                    batchArrayList = (ArrayList<Batch>) response.body().getBatch();

                    for (Batch batch : batchArrayList) {

                        integerBatchHashMap.put(batch.getName(), batch.getId());
                        arrayListBatch.add(batch.getName());
                    }

                    spinner_batch.setItem(arrayListBatch);

                    getAllCourse();

                } else {
                    //CommonUtils.errorToast(getActivity(), "No Batch Retrieved");

                }

            }

            @Override
            public void onFailure(Call<BatchResponse> call, Throwable t) {

            }
        });

    }

    private void getAllCourse() {

        arrayListCourse = new ArrayList<>();
        integerCourseHashMap = new HashMap<>();
        apiInterface.get_all_course(Integer.parseInt(staff.getInstituteId())).enqueue(new Callback<CourseResponse>() {
            @Override
            public void onResponse(Call<CourseResponse> call, Response<CourseResponse> response) {


                if (response.body().getCode().equals("200")) {

                    courseArrayList = (ArrayList<Course>) response.body().getCourse();

                    for (Course course : courseArrayList) {

                        integerCourseHashMap.put(course.getName(), course.getId());
                        arrayListCourse.add(course.getName());
                    }

                    spinner_course.setItem(arrayListCourse);

                } else {

                }
            }

            @Override
            public void onFailure(Call<CourseResponse> call, Throwable t) {

            }
        });
    }

    //method to show file chooser
    private void showFileChooser() {
        uploadUrl = 1;

        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Photo Gallery",
                "Camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    private void showFileFormat() {

        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "PDF File",
                "Image"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                showPdfFileChooser();
                                break;
                            case 1:
                                showFileChooser();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    private void showPdfFileChooser() {

        uploadUrl = 2;
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);
    }


    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }


    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                filePath = data.getData();
                File file = new File(filePath.getPath());

                selectedFilename = removeSpace(file.getName());


                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                    // String path = saveImage(bitmap);
                    imageView.setImageBitmap(bitmap);
                    CommonUtils.showToast(AddHomeworkActivity.this, "Image Saved!");

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            bitmap = (Bitmap) data.getExtras().get("data");

            imageView.setImageBitmap(bitmap);


            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(getApplicationContext(), bitmap);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            File finalFile = new File(getRealPathFromURI(tempUri));

            selectedFilename = removeSpace(finalFile.getName());


        } else if (requestCode == PICK_PDF_REQUEST) {

            filePath = data.getData();
            File myFile = new File(filePath.getPath());

            selectedFilename = removeSpace(myFile.getName());


            generateImageFromPdf(filePath);

        }
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        if (bitmap == null) {

            CommonUtils.showToast(getBaseContext(), "Please select Image File");

        } else {
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(AddHomeworkActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {


            ActivityCompat.requestPermissions(AddHomeworkActivity.this,
                    new String[]{permission},
                    requestCode);
        } else {
//            Toast.makeText(ForgetPasswordActivity.this,
//                    "Permission already granted",
//                    Toast.LENGTH_SHORT)
//                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        if (requestCode == CAMERA) {

            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(AddHomeworkActivity.this,
                        "Camera Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(AddHomeworkActivity.this,
                        "CAMERA Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(AddHomeworkActivity.this,
                        "STORAGE_PERMISSION permission granted",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(AddHomeworkActivity.this,
                        "STORAGE_PERMISSION Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }


    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    private String removeSpace(String str) {

        String string_ = str.replaceAll("[^a-zA-Z0-9]", " ");

        String string = string_.replaceAll("\\s", "");


        return string;
    }

    void generateImageFromPdf(Uri pdfUri) {
        int pageNumber = 0;
        PdfiumCore pdfiumCore = new PdfiumCore(this);
        try {
            //http://www.programcreek.com/java-api-examples/index.php?api=android.os.ParcelFileDescriptor
            ParcelFileDescriptor fd = getContentResolver().openFileDescriptor(pdfUri, "r");
            PdfDocument pdfDocument = pdfiumCore.newDocument(fd);
            pdfiumCore.openPage(pdfDocument, pageNumber);
            int width = pdfiumCore.getPageWidthPoint(pdfDocument, pageNumber);
            int height = pdfiumCore.getPageHeightPoint(pdfDocument, pageNumber);
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            pdfiumCore.renderPageBitmap(pdfDocument, bmp, pageNumber, 0, 0, width, height);

            imageView.setImageBitmap(bmp);
            //saveImage(bmp);
            pdfiumCore.closeDocument(pdfDocument); // important!
        } catch (Exception e) {
            //todo with exception
        }
    }

    private void scheduleJobFirebaseToRoomDataUpdate(String class_name, String div) {
        JobScheduler jobScheduler = (JobScheduler) BaseApplication.getAppContext().getSystemService(JOB_SCHEDULER_SERVICE);
        ComponentName componentName = new ComponentName(BaseApplication.getAppContext(), InformOtherUserService.class);

        PersistableBundle bundle = new PersistableBundle();
        bundle.putString("selectedclass", class_name);
        bundle.putString("selecteddiv", div);
        bundle.putString("msg", "New Homework is Added.Please check.");

        JobInfo.Builder mJobBuilder = new JobInfo.Builder(1, componentName).setExtras(bundle);

        JobInfo jobInfo = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//            jobInfo = new JobInfo.Builder(1, componentName)
//                    .setPeriodic(86400000).setRequiredNetworkType(
//                            JobInfo.NETWORK_TYPE_NOT_ROAMING)
//                    .setPersisted(true).build();
            mJobBuilder.setPeriodic(86400000).setRequiredNetworkType(JobInfo.NETWORK_TYPE_NOT_ROAMING).setPersisted(true).build();
        }

        jobInfo = mJobBuilder.build();
        assert jobInfo != null;
        jobScheduler.schedule(jobInfo);
    }


    private void addHomework(final Bitmap bitmap) {

        //getting the tag from the edittext
        //final String tags = editTextTags.getText().toString().trim();

        ProgressDialog progressDialog = new ProgressDialog(AddHomeworkActivity.this);
        progressDialog.setMessage("Updating");
        progressDialog.show();


        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, BASE_IMAGE_URL,
                new com.android.volley.Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {

                            progressDialog.dismiss();

                            JSONObject obj = new JSONObject(new String(response.data));
                            Log.e("JSONRESPONSE : ", obj.toString());

                            String code = obj.getString("code");
                            if (code.equals("200")) {
//                                scheduleJobFirebaseToRoomDataUpdate(selectedClass, selectedDiv);
                                Toast.makeText(getBaseContext(), "Homework Added successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getBaseContext(), HomeWorkActivity.class));

                                //finish();

                            } else if (code.equals("400")) {
                                Toast.makeText(getBaseContext(), "Teacher not found", Toast.LENGTH_SHORT).show();
                            } else {
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("file_tag", "img");
                params.put("staff_id", staff.getId());
                params.put("institute_id", staff.getInstituteId());
                params.put("batch", String.valueOf(batch));
                params.put("course", String.valueOf(course));
                params.put("submission_date", submissionDate);
                params.put("file", selectedFilename + ".png");


                Log.e("VOlleyPARAMETERS", params.toString());
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(selectedFilename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        volleyMultipartRequest.setRetryPolicy(policy);
        Volley.newRequestQueue(this).add(volleyMultipartRequest);

    }

    private void addPdfHomework(Uri pdffile) {

        //getting the tag from the edittext
        //final String tags = editTextTags.getText().toString().trim();

        ProgressDialog progressDialog = new ProgressDialog(AddHomeworkActivity.this);
        progressDialog.setMessage("Updating");
        progressDialog.show();

        InputStream iStream = null;
        try {
            iStream = getContentResolver().openInputStream(pdffile);
            inputData = getBytes(iStream);

        } catch (IOException e) {
            e.printStackTrace();
        }


        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, BASE_PDF_URL,
                new com.android.volley.Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {

                            progressDialog.dismiss();

                            JSONObject obj = new JSONObject(new String(response.data));
                            Log.e("JSONRESPONSE : ", obj.toString());

                            String code = obj.getString("code");
                            if (code.equals("200")) {
                                //scheduleJobFirebaseToRoomDataUpdate(selectedClass, selectedDiv);
                                CommonUtils.successToast(getBaseContext(), "Homework Added successfully");

                                startActivity(new Intent(getBaseContext(), HomeWorkActivity.class));

                            } else if (code.equals("400")) {

                                CommonUtils.errorToast(getBaseContext(), "Homework Not Added successfully");

                            } else {
                                CommonUtils.errorToast(getBaseContext(), "Homework Not Added successfully");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("file_tag", "pdf");
                params.put("staff_id", staff.getId());
                params.put("institute_id", staff.getInstituteId());
                params.put("batch", String.valueOf(batch));
                params.put("course", String.valueOf(course));
                params.put("submission_date", submissionDate);
                params.put("file", selectedFilename + ".pdf");

                Log.e("VOlleyPARAMETERS", params.toString());
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long pdf = System.currentTimeMillis();
                params.put("pdf", new DataPart(selectedFilename + ".pdf", inputData));
                return params;
            }
        };

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        volleyMultipartRequest.setRetryPolicy(policy);
        Volley.newRequestQueue(this).add(volleyMultipartRequest);

    }
}

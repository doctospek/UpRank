package com.uprank.uprank.teacher.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import com.uprank.uprank.R;
import com.uprank.uprank.teacher.maincategory.ExamScheduleActivity;
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

public class AddExamActivity extends AppCompatActivity implements View.OnClickListener {

    private SmartMaterialSpinner spinner_batch, spinner_course;
    private Button button_selectFile, button_addAttachment;
    private TextView textView, textViewDate;
    private ApiInterface apiInterface;
    private Pref pref = new Pref();
    private Staff staff;
    private ArrayList<Batch> batchArrayList;
    private ArrayList<Course> courseArrayList;
    private HashMap<String, Integer> integerCourseHashMap;
    private HashMap<String, Integer> integerBatchHashMap;
    private String exam_date,exam_title, subject_name, chapterName, selectedFilename, selectedBatch, selectedCourse, file_tag;
    private Bitmap bitmap;
    private int CAMERA = 2, STORAGE_PERMISSION_CODE = 123, PICK_REQUEST = 3, uploadUrl, course, batch;
    private Uri filePath;
    private byte[] inputData;
    private ArrayList<String> arrayListBatch;
    private ArrayList<String> arrayListCourse;
    private String ADD_EXAM_URL = ApiClient.BASE_URL + "add_exam.php?apicall=uploadpic";
    private EditText editText_subject, editText_chapter, editText_exam;
    private Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exam);

        checkPermission(Manifest.permission.CAMERA, CAMERA);
        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);

        staff = pref.getStaffDataPref(AddExamActivity.this);
        apiInterface = ApiClient.getClient(AddExamActivity.this).create(ApiInterface.class);


        initView();
    }

    private void initView() {

        myCalendar = Calendar.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        toolbar.inflateMenu(R.menu.main_menu);

        spinner_batch = findViewById(R.id.spinner_batch);
        spinner_course = findViewById(R.id.spinner_course);
        button_selectFile = findViewById(R.id.button_selectFile);
        button_addAttachment = findViewById(R.id.button_add_attachment);
        editText_chapter = findViewById(R.id.text_chapter_name);
        editText_subject = findViewById(R.id.text_subject_name);
        editText_exam = findViewById(R.id.text_exam_name);
        textView = findViewById(R.id.text_selected_file_name);
        textViewDate = findViewById(R.id.text_select_date);

        listeners();

        getAllBatch();

        spinner_course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedCourse = (String) parent.getSelectedItem();

                course = integerCourseHashMap.get(selectedCourse);

                Log.e("COURSE_ID", course + "");

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

                Log.e("BATCH_ID", batch + "");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        DatePickerDialog.OnDateSetListener examDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
                textViewDate.setText(sdf.format(myCalendar.getTime()));
            }

        };

        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddExamActivity.this, examDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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

    private void listeners() {

        button_selectFile.setOnClickListener(this::onClick);
        button_addAttachment.setOnClickListener(this::onClick);
    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(AddExamActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(AddExamActivity.this,
                    new String[]{permission},
                    requestCode);
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
                Toast.makeText(AddExamActivity.this,
                        "Camera Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(AddExamActivity.this,
                        "Camera Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(AddExamActivity.this,
                        "Storage permission granted",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(AddExamActivity.this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }


    }

    private void showFileChooser() {

        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select From Storage",
                "Open Camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                selectFromStorage();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_selectFile:

                showFileChooser();

                break;

            case R.id.button_add_attachment:

                chapterName = editText_chapter.getText().toString();
                subject_name = editText_subject.getText().toString();
                exam_title = editText_exam.getText().toString();
                exam_date=textViewDate.getText().toString();

                if (filePath == null) {

                } else {
                    addExam(filePath);
                }


                break;
        }
    }

    private void selectFromStorage() {

        Intent intent = new Intent();
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_REQUEST);

    }

    private void takePhotoFromCamera() {

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == PICK_REQUEST) {
            if (data != null) {
                filePath = data.getData();

                File file = new File(filePath.getPath());

                textView.setText(getFilePathFromURI(AddExamActivity.this, filePath));

                selectedFilename = removeSpace(file.getName());


                if (filePath.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
                    //If scheme is a content
                    final MimeTypeMap mime = MimeTypeMap.getSingleton();
                    file_tag = mime.getExtensionFromMimeType(getBaseContext().getContentResolver().getType(filePath));
                } else {
                    //If scheme is a File
                    //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
                    file_tag = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(filePath.getPath())).toString());

                }

                //CommonUtils.successToast(AddExamActivity.this, file_tag);

            }

        } else if (requestCode == CAMERA) {

            bitmap = (Bitmap) data.getExtras().get("data");


            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            filePath = getImageUri(AddExamActivity.this, bitmap);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            File finalFile = new File(getRealPathFromURI(filePath));

            textView.setText(finalFile.getAbsolutePath());

            file_tag = finalFile.getAbsolutePath().substring(finalFile.getAbsolutePath().lastIndexOf("."));

            selectedFilename = removeSpace(finalFile.getName());

            //CommonUtils.successToast(AddExamActivity.this, file_tag);


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
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title" + " - " + (Calendar.getInstance().getTime()), null);
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

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(android.provider.MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
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


    private static String getFilePathFromURI(Context context, Uri contentUri) {
        //copy file and send new file path
        String fileName = getFileName(contentUri);
        if (!TextUtils.isEmpty(fileName)) {
            String TEMP_DIR_PATH = Environment.getExternalStorageDirectory().getPath();
            File copyFile = new File(TEMP_DIR_PATH + File.separator + fileName);
            Log.d("DREG", "FilePath copyFile: " + copyFile);
            //copy(context, contentUri, copyFile);
            return copyFile.getAbsolutePath();
        }
        return null;
    }

    public static String getFileName(Uri uri) {
        if (uri == null) return null;
        String fileName = null;
        String path = uri.getPath();
        int cut = path.lastIndexOf('/');
        if (cut != -1) {
            fileName = path.substring(cut + 1);
        }
        return fileName;
    }

    private void addExam(Uri pdffile) {

        //getting the tag from the edittext
        //final String tags = editTextTags.getText().toString().trim();

        ProgressDialog progressDialog = new ProgressDialog(AddExamActivity.this);
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
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, ADD_EXAM_URL,
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
                                CommonUtils.successToast(getBaseContext(), "Exam Added successfully");

                                startActivity(new Intent(getBaseContext(), ExamScheduleActivity.class));

                            } else if (code.equals("400")) {

                                CommonUtils.errorToast(getBaseContext(), "Exam Not Added successfully");

                            } else {
                                CommonUtils.errorToast(getBaseContext(), "Notes Not Added successfully");
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
                params.put("file_tag", file_tag);
                params.put("staff_id", staff.getId());
                params.put("batch", String.valueOf(batch));
                params.put("course", String.valueOf(course));

                if (file_tag.contains(".")) {
                    params.put("quation_paper", selectedFilename + file_tag);
                } else {
                    params.put("quation_paper", selectedFilename + "." + file_tag);
                }
                params.put("chapter", chapterName);
                params.put("institute_id", staff.getInstituteId());
                params.put("exam_title", exam_title);
                params.put("subject", subject_name);
                params.put("exam_date", exam_date);

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

                if (file_tag.contains(".")) {
                    params.put("file", new DataPart(selectedFilename + file_tag, inputData));
                } else {
                    params.put("file", new DataPart(selectedFilename + "." + file_tag, inputData));
                }
                return params;
            }
        };

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        volleyMultipartRequest.setRetryPolicy(policy);
        Volley.newRequestQueue(this).add(volleyMultipartRequest);

    }
}

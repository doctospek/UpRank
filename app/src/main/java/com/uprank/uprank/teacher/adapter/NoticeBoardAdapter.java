package com.uprank.uprank.teacher.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.devs.readmoreoption.ReadMoreOption;
import com.google.gson.JsonObject;
import com.uprank.uprank.R;
import com.uprank.uprank.teacher.maincategory.NoticeBoardActivity;
import com.uprank.uprank.teacher.model.Notice;
import com.uprank.uprank.teacher.utility.CommonUtils;
import com.uprank.uprank.teacher.webservices.ApiClient;
import com.uprank.uprank.teacher.webservices.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeBoardAdapter extends BaseAdapter {

    private ReadMoreOption readMoreOption;
    private Context context;
    private ArrayList<Notice> noticeArrayList = new ArrayList<>();
    ApiInterface apiInterface;
    String[] color_array = {"#D1C4E9", "#C5CAE9", "#E1BEE7", "#F8BBD0", "#C8E6C9", "#FFCCBC"};

    public NoticeBoardAdapter(Context context, ArrayList<Notice> noticeArrayList) {
        this.context = context;
        this.noticeArrayList = noticeArrayList;
        apiInterface = ApiClient.getClient(context).create(ApiInterface.class);
    }

    @Override
    public int getCount() {
        return noticeArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return noticeArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.noticeboard_view, parent, false);

        Notice notice = noticeArrayList.get(position);

        TextView textView_title = convertView.findViewById(R.id.text_title);
        TextView textView_desc = convertView.findViewById(R.id.text_desc);
        ImageView imageView = convertView.findViewById(R.id.image_delete_menu);
        CardView cardView = convertView.findViewById(R.id.cardview);


        readMoreOption = new ReadMoreOption.Builder(context).build();

        readMoreOption.addReadMoreTo(textView_desc, Html.fromHtml(notice.getNotice()));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDeleteDialog(notice.getId());
            }
        });

        int rnd = new Random().nextInt(color_array.length);

        cardView.setCardBackgroundColor(Color.parseColor(color_array[rnd]));


        return convertView;
    }

    private void deleteNotice(int id) {

        apiInterface.delete_notice(id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    String code = jsonObject.getString("code");
                    String msg = jsonObject.getString("msg");

                    if (code.equals("200")) {

                        CommonUtils.successToast(context, msg);
                        context.startActivity(new Intent(context, NoticeBoardActivity.class));

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


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
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

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}

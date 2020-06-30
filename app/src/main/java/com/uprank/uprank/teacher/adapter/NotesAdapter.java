package com.uprank.uprank.teacher.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uprank.uprank.R;
import com.uprank.uprank.teacher.model.Note;
import com.uprank.uprank.teacher.model.Staff;
import com.uprank.uprank.teacher.utility.Pref;
import com.uprank.uprank.teacher.webservices.ApiClient;
import com.uprank.uprank.teacher.webservices.ApiInterface;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private Context context;
    private List<Note> noteArrayList;
    private ApiInterface apiInterface;
    Pref pref = new Pref();
    private Staff staff;

    public NotesAdapter(Context context, List<Note> noteArrayList) {
        this.context = context;
        this.noteArrayList = noteArrayList;

        apiInterface = ApiClient.getClient(context).create(ApiInterface.class);
        staff = pref.getStaffDataPref(context);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.notes_view, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Note note = noteArrayList.get(position);

        holder.textView_chapter.setText(note.getChapter());
        holder.textView_batch.setText(note.getBatchName());
        holder.textView_created.setText(note.getCreatedAt());
        holder.textView_course.setText(note.getCourseName());
        holder.textView_file_name.setText(note.getFileName());

      /*  int video_id;

        ContentResolver crThumb = context.getContentResolver();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        Bitmap curThumb = MediaStore.Video.Thumbnails.getThumbnail(crThumb, video_id, MediaStore.Video.Thumbnails.MICRO_KIND, options);
        holder.imageView_file.setImageBitmap(curThumb);*/

    }

    @Override
    public int getItemCount() {
        return noteArrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView_file, imageView_download;
        TextView textView_chapter, textView_file_name, textView_created, textView_batch, textView_course;

        ViewHolder(View itemView) {
            super(itemView);
            this.imageView_file = (ImageView) itemView.findViewById(R.id.image_file);
            this.imageView_download = (ImageView) itemView.findViewById(R.id.image_download);
            this.textView_chapter = (TextView) itemView.findViewById(R.id.text_chapter_name);
            this.textView_file_name = (TextView) itemView.findViewById(R.id.text_file_name);
            this.textView_created = (TextView) itemView.findViewById(R.id.text_created_date);
            this.textView_batch = (TextView) itemView.findViewById(R.id.text_batch_name);
            this.textView_course = (TextView) itemView.findViewById(R.id.text_course_name);


        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}

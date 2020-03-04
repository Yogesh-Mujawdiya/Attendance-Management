package com.yk.attendancemanagement.Adapter;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.yk.attendancemanagement.Class.Student;
import com.yk.attendancemanagement.Class.Subject;
import com.yk.attendancemanagement.Controller.StoreData;
import com.yk.attendancemanagement.R;

import java.util.ArrayList;
import java.util.List;


public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.AttendanceViewHolder> {
    private List<Student> StudentList;
    StoreData controller;
    Context context;
    private int progressbar_states;

    public StudentAdapter(Context context, List<Student> StudentList) {
        this.context = context ;
        this.StudentList=StudentList;
        controller = new StoreData(context);
    }

    @Override
    public StudentAdapter.AttendanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_attendance, null);
        return new StudentAdapter.AttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final StudentAdapter.AttendanceViewHolder holder, final int position) {
        final Student student = StudentList.get(position);
        int TotalClass = student.getPresentClass()+student.getAbsentClass();
        holder.textViewRollNo.setText(student.getRollNo());
        holder.textViewTotalClass.setText(Integer.toString(TotalClass));
        holder.textViewAttendClass.setText(Integer.toString(student.getPresentClass()));
        int P = 100;
        if(TotalClass!=0)
            P=(student.getPresentClass()*100)/TotalClass;
        if(P>=75)
            holder.progressBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.progressbar_states_green));
        else if(P>=50)
            holder.progressBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.progressbar_states_yellow));
        else
            holder.progressBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.progressbar_states_rad));
        holder.progressBar.setProgress(P);
        holder.textViewProgressBar.setText(holder.progressBar.getProgress() +"%");
    }

    @Override
    public int getItemCount() {
        return StudentList.size();
    }

    class AttendanceViewHolder extends RecyclerView.ViewHolder {

        TextView textViewRollNo, textViewTotalClass, textViewAttendClass, textViewProgressBar;
        ProgressBar progressBar;

        public AttendanceViewHolder(View itemView) {
            super(itemView);
            textViewRollNo = itemView.findViewById(R.id.textViewRollNo);
            textViewTotalClass = itemView.findViewById(R.id.textViewTotalClass);
            textViewAttendClass = itemView.findViewById(R.id.textViewAttendClass);
            textViewProgressBar = itemView.findViewById(R.id.tv);
            progressBar = itemView.findViewById(R.id.pb);
        }
    }

}
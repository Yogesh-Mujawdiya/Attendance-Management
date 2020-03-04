package com.yk.attendancemanagement.Adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yk.attendancemanagement.AttendanceActivity;
import com.yk.attendancemanagement.Class.Student;
import com.yk.attendancemanagement.Class.Subject;
import com.yk.attendancemanagement.Class.User;
import com.yk.attendancemanagement.Controller.StoreData;
import com.yk.attendancemanagement.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder> {
    private List<Student> StudentList;
    StoreData controller;
    Context context;

    public AttendanceAdapter(Context context, List<Student> StudentList) {
        this.context = context ;
        this.StudentList=StudentList;
        controller = new StoreData(context);
    }

    @Override
    public AttendanceAdapter.AttendanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkbox_list, null);
        return new AttendanceAdapter.AttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AttendanceAdapter.AttendanceViewHolder holder, final int position) {
        final Student student = StudentList.get(position);
        holder.checkBoxRollNo.setText(student.getRollNo().substring(student.getRollNo().length()-3));
        holder.checkBoxRollNo.setChecked(student.getStatus());
        holder.checkBoxRollNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentList.get(position).setStatus(holder.checkBoxRollNo.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return StudentList.size();
    }

    public String getPresentRollNo(){
        ArrayList<String> Present = new ArrayList<>();
        for(int i=0;i<StudentList.size();i++){
            if(StudentList.get(i).getStatus())
                Present.add(StudentList.get(i).getRollNo());
        }
        return TextUtils.join(",", Present);
    }

    public String getAbsentRollNo(){
        ArrayList<String> Absent = new ArrayList<>();
        for(int i=0;i<StudentList.size();i++){
            if(!StudentList.get(i).getStatus())
                Absent.add(StudentList.get(i).getRollNo());
        }
        return TextUtils.join(",", Absent);
    }

    class AttendanceViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBoxRollNo;

        public AttendanceViewHolder(View itemView) {
            super(itemView);
            checkBoxRollNo = itemView.findViewById(R.id.checkbox);
        }
    }

}
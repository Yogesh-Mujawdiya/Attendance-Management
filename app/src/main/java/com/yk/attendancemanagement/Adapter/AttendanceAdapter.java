package com.yk.attendancemanagement.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import androidx.recyclerview.widget.RecyclerView;
import com.yk.attendancemanagement.Class.Student;
import com.yk.attendancemanagement.Controller.StoreData;
import com.yk.attendancemanagement.R;
import java.util.ArrayList;
import java.util.List;

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
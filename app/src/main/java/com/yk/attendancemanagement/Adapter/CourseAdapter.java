package com.yk.attendancemanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yk.attendancemanagement.Class.Course;
import com.yk.attendancemanagement.Class.Subject;
import com.yk.attendancemanagement.Controller.StoreData;
import com.yk.attendancemanagement.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private List<Course>  CourseList;
    Context context;

    public CourseAdapter(Context context, List<Course> courseList) {
        this.context = context ;
        this.CourseList=courseList;
    }

    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkbox_list, null);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CourseAdapter.CourseViewHolder holder, int position) {
        Course course = CourseList.get(position);
        holder.checkBoxCourse.setText(course.getCourseName());
        holder.checkBoxCourse.setChecked(Boolean.valueOf(course.isStatus()));

    }

    @Override
    public int getItemCount() {
        return CourseList.size();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBoxCourse;

        public CourseViewHolder(View itemView) {
            super(itemView);
            checkBoxCourse = itemView.findViewById(R.id.checkbox);
        }
    }

}

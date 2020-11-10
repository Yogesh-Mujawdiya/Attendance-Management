package com.yk.attendancemanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import androidx.recyclerview.widget.RecyclerView;
import com.yk.attendancemanagement.Class.Course;
import com.yk.attendancemanagement.R;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private List<Course>  CourseList;
    Context context;

    public CourseAdapter(Context context, List<Course> courseList) {
        this.context = context ;
        this.CourseList = courseList;
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

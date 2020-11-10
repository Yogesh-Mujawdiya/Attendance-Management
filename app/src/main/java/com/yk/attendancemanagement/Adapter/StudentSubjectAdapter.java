package com.yk.attendancemanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.yk.attendancemanagement.Class.Subject;
import com.yk.attendancemanagement.R;
import java.util.List;

public class StudentSubjectAdapter extends RecyclerView.Adapter<StudentSubjectAdapter.SubjectViewHolder> {

    private List<Subject> SubjectList;
    Context context;

    public StudentSubjectAdapter(Context context, List<Subject> SubjectList) {
        this.context = context ;
        this.SubjectList=SubjectList;
    }

    @Override
    public StudentSubjectAdapter.SubjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_card_attendance, null);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final StudentSubjectAdapter.SubjectViewHolder holder, int position) {
        final Subject subject = SubjectList.get(position);
        holder.textViewRollNo.setText(subject.getName());
        holder.textViewTotalClass.setText(Integer.toString(subject.getTotal()));
        holder.textViewAttendClass.setText(Integer.toString(subject.getAttend()));
        int P = 100;
        if(subject.getTotal()!=0)
            P=(subject.getAttend()*100)/subject.getTotal();
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
        return SubjectList.size();
    }

    class SubjectViewHolder extends RecyclerView.ViewHolder {

        TextView textViewRollNo, textViewTotalClass, textViewAttendClass, textViewProgressBar;
        ProgressBar progressBar;

        public SubjectViewHolder(View itemView) {
            super(itemView);
            textViewRollNo = itemView.findViewById(R.id.textViewRollNo);
            textViewTotalClass = itemView.findViewById(R.id.textViewTotalClass);
            textViewAttendClass = itemView.findViewById(R.id.textViewAttendClass);
            textViewProgressBar = itemView.findViewById(R.id.tv);
            progressBar = itemView.findViewById(R.id.pb);
        }
    }
}

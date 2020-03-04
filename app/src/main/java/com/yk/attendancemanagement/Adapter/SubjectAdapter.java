package com.yk.attendancemanagement.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yk.attendancemanagement.AttendanceActivity;
import com.yk.attendancemanagement.Class.Course;
import com.yk.attendancemanagement.Class.Subject;
import com.yk.attendancemanagement.Class.User;
import com.yk.attendancemanagement.Controller.StoreData;
import com.yk.attendancemanagement.FacultyActivity;
import com.yk.attendancemanagement.LoginSignupActivity;
import com.yk.attendancemanagement.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {

    private List<Subject> SubjectList;
    StoreData controller;
    private Dictionary CourseList = new Hashtable();
    Context context;

    private String Host , getSubject_url, getCourse_url, saveSubject_url, getFacultySubject_url;

    public SubjectAdapter(Context context, List<Subject> SubjectList) {
        this.context = context ;
        this.SubjectList=SubjectList;
        controller = new StoreData(context);
        Host = controller.getHost();
        getCourse_url =  Host+"/getCourse.php";
    }

    @Override
    public SubjectAdapter.SubjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_list, null);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SubjectAdapter.SubjectViewHolder holder, int position) {
        final Subject subject = SubjectList.get(position);
        holder.textViewSubject.setText(subject.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCourse(subject);
            }
        });
    }


    private void getCourse(final Subject subject) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_box_select_course);
        dialog.setTitle("Choose Specification Name, Batch And Section");
        final Spinner spinnerSelectBatch = dialog.findViewById((R.id.spinnerSelectBatch));
        final AutoCompleteTextView autoCompleteTextView = dialog.findViewById(R.id.autoTextViewCourseName);
        final Spinner spinnerSelectSection = dialog.findViewById((R.id.spinnerSelectSection));
        int year = Calendar.getInstance().get(Calendar.YEAR);
        ArrayList<String> spinnerArray = new ArrayList<String>();
        for(int i=0;i<=3;i++){
            spinnerArray.add(Integer.toString(year-i));
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, spinnerArray);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerSelectBatch.setAdapter(spinnerArrayAdapter);
        final String SubjectId = subject.getID();
        final User user = controller.getCurrentUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getCourse_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            final String success = json.getString("status");
                            String message = json.getString("message");
                            if(success.equalsIgnoreCase("True")) {
                                JSONArray jArray = json.getJSONArray("CourseList");
                                final String[] Courses = new String[jArray.length()];
                                for (int i = 0; i < jArray.length(); i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    Courses[i] = json_data.getString("Name");
                                    CourseList.put(Courses[i],new Course(
                                            json_data.getString("DId"),
                                            json_data.getString("Id"),
                                            json_data.getString("Name"),
                                            Integer.parseInt(json_data.getString("Section_Count"))));
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.select_dialog_item, Courses);
                                autoCompleteTextView.setThreshold(1);
                                autoCompleteTextView.setAdapter(adapter);

                                autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                        if( CourseList.get(autoCompleteTextView.getText().toString()) !=null) {
                                            Course course = (Course) CourseList.get(autoCompleteTextView.getText().toString());
                                            int SectionCount = course.getSectionCount();
                                            String[] Section_List = new String[SectionCount+1];
                                            Section_List[0]="Combine Class";
                                            for (int i = 1; i <= SectionCount; i++) {
                                                Section_List[i] = ""+(char) ('A' + i-1);
                                            }

                                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, Section_List);
                                            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                                            spinnerSelectSection.setAdapter(spinnerArrayAdapter);

                                        }
                                        else {
                                            Toast.makeText(context, "Error : Degree not Found" , Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                                Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);
                                dialogButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent i = new Intent(context, AttendanceActivity.class);
                                        String Degree = autoCompleteTextView.getText().toString();
                                        Course course = (Course) CourseList.get(Degree);
                                        String ID = course.getCourseId();
                                        i.putExtra("Course_Id", course.getCourseId());
                                        i.putExtra("Course_Name", course.getCourseName());
                                        i.putExtra("Subject_Id",subject.getID());
                                        i.putExtra("Subject_Name",subject.getName());
                                        i.putExtra("Department_Id",course.getDepartmentId());
                                        i.putExtra("Section",spinnerSelectSection.getSelectedItem().toString());
                                        i.putExtra("Batch",spinnerSelectBatch.getSelectedItem().toString());
                                        dialog.dismiss();
                                        context.startActivity(i);
                                    }
                                });
                                dialog.show();
                            }
                            else{
                                Toast.makeText( context, "Error : " + message , Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Error : " + e.toString() , Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("SubjectId",subject.getID());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    @Override
    public int getItemCount() {
        return SubjectList.size();
    }

    class SubjectViewHolder extends RecyclerView.ViewHolder {

        TextView textViewSubject;

        public SubjectViewHolder(View itemView) {
            super(itemView);
            textViewSubject = itemView.findViewById(R.id.textViewSubject);
        }
    }

}

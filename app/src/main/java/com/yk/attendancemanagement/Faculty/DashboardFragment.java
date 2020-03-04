package com.yk.attendancemanagement.Faculty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yk.attendancemanagement.Adapter.StudentAdapter;
import com.yk.attendancemanagement.Adapter.SubjectAdapter;
import com.yk.attendancemanagement.Class.Course;
import com.yk.attendancemanagement.Class.Student;
import com.yk.attendancemanagement.Class.Subject;
import com.yk.attendancemanagement.Class.User;
import com.yk.attendancemanagement.Controller.StoreData;
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

public class DashboardFragment extends Fragment {

    private StoreData controller;
    private RecyclerView recyclerView;
    List<Student> ALLStudent ;
    private Dictionary SubjectList = new Hashtable()  ,CourseList = new Hashtable(),StudentList = new Hashtable();
    private StudentAdapter adapter;
    private List<Subject> MySubjects;
    private String Host , getDegree_url, getSubject_url, getCourse_url, saveSubject_url, getFacultySubject_url, getStudentData;
    private Button buttonSearch,buttonNext,buttonBack;
    private AppCompatAutoCompleteTextView autoTextViewSubjectName, autoTextViewCourseName;
    private Spinner spinnerSelectSection, spinnerSelectBatch;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_faculty_dashboard, container, false);

        autoTextViewSubjectName = root.findViewById(R.id.autoTextViewSubjectName);
        autoTextViewCourseName  = root.findViewById(R.id.autoTextViewCourseName);
        spinnerSelectSection = root.findViewById(R.id.spinnerSelectSection);
        spinnerSelectBatch   = root.findViewById(R.id.spinnerSelectBatch);
        buttonSearch = root.findViewById(R.id.SearchButton);
        buttonBack = root.findViewById(R.id.BackButton);
        buttonNext = root.findViewById(R.id.NextButton);
        Host = getString(R.string.localhost);
        getDegree_url =  Host+"/getAllDegree.php";
        getSubject_url =  Host+"/getSubject.php";
        getCourse_url =  Host+"/getCourse.php";
        saveSubject_url =  Host+"/saveSubject.php";
        getFacultySubject_url =  Host+"/getFacultySubject.php";
        getStudentData =  Host+"/getStudentAttendance.php";
        controller = new StoreData(getActivity());
        recyclerView = root.findViewById(R.id.recyclerViewStudentAttendance);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        getMySubject();

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(autoTextViewSubjectName.isShown()) {
                    getCourse();
                }
                else {
                    setData();
                }
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(autoTextViewCourseName.isShown()){
                    autoTextViewSubjectName.setVisibility(View.VISIBLE);
                    autoTextViewCourseName.setVisibility(View.GONE);
                    buttonBack.setVisibility(View.GONE);
                }
                else if(spinnerSelectBatch.isShown()){
                    spinnerSelectBatch.setVisibility(View.GONE);
                    spinnerSelectSection.setVisibility(View.GONE);
                    autoTextViewCourseName.setVisibility(View.VISIBLE);
                    buttonNext.setVisibility(View.VISIBLE);
                    buttonSearch.setVisibility(View.GONE);
                }
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllData();
            }
        });
        return root;
    }

    private void setData() {
        if(CourseList.get(autoTextViewCourseName.getText().toString()) ==null){
            Toast.makeText(getActivity(), "Error : Course Not Found !" , Toast.LENGTH_SHORT).show();
            return;
        }
        int year = Calendar.getInstance().get(Calendar.YEAR);
        ArrayList<String> spinnerArray = new ArrayList<String>();
        for(int i=0;i<=5;i++){
            spinnerArray.add(Integer.toString(year-i));
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, spinnerArray);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerSelectBatch.setAdapter(spinnerArrayAdapter);
        Course course = (Course) CourseList.get(autoTextViewCourseName.getText().toString());
        int SectionCount = course.getSectionCount();
        String[] Section_List = new String[SectionCount+1];
        Section_List[0]="Combine Class";
        for (int i = 1; i <= SectionCount; i++) {
            Section_List[i] = ""+(char) ('A' + i-1);
        }

        ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, Section_List);
        spinnerArrayAdapter1.setDropDownViewResource(R.layout.spinner_item);
        spinnerSelectSection.setAdapter(spinnerArrayAdapter1);
        spinnerSelectSection.setVisibility(View.VISIBLE);
        spinnerSelectBatch.setVisibility(View.VISIBLE);
        autoTextViewCourseName.setVisibility(View.GONE);
        buttonNext.setVisibility(View.GONE);
        buttonSearch.setVisibility(View.VISIBLE);
    }

    private void getMySubject(){
        User user = controller.getCurrentUser();
        final String FacultyId = user.getUserId();
        final String FacultyPassword = user.getPassword();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getFacultySubject_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            MySubjects = new ArrayList<>();
                            JSONObject json = new JSONObject(response);
                            String success = json.getString("status");
                            String message = json.getString("message");
                            if(success.equalsIgnoreCase("True")) {
                                JSONArray jArray = json.getJSONArray("SubjectList");
                                String[] subjectData = new String[jArray.length()];
                                for (int i = 0; i < jArray.length(); i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    subjectData[i] = json_data.getString("Name");
                                    SubjectList.put(json_data.getString("Name"), json_data.getString("Id"));
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, subjectData);
                                autoTextViewSubjectName.setThreshold(1);
                                autoTextViewSubjectName.setAdapter(adapter);
                            }
                            else{
                                Toast.makeText(getActivity(), "Error : " + message , Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Error : " + e.toString() , Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("Id",FacultyId);
                params.put("Password",FacultyPassword);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void getCourse() {
        if(SubjectList.get(autoTextViewSubjectName.getText().toString().trim())==null) {
            Toast.makeText(getActivity(), "Error : Subject Not Found !" , Toast.LENGTH_SHORT).show();
            return;
        }
        final String SubjectId = (String) SubjectList.get(autoTextViewSubjectName.getText().toString().trim());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getCourse_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            String success = json.getString("status");
                            String message = json.getString("message");
                            if(success.equalsIgnoreCase("True")) {
                                JSONArray jArray = json.getJSONArray("CourseList");
                                String[] Course = new String[jArray.length()];
                                for (int i = 0; i < jArray.length(); i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    Course[i] = json_data.getString("Name");
                                    CourseList.put(Course[i],new Course(
                                            json_data.getString("DId"),
                                            json_data.getString("Id"),
                                            json_data.getString("Name"),
                                            json_data.getString("Qualification_Code"),
                                            Integer.parseInt(json_data.getString("Student_Count")),
                                            Integer.parseInt(json_data.getString("Section_Count"))));
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, Course);
                                autoTextViewCourseName.setThreshold(1);
                                autoTextViewCourseName.setAdapter(adapter);
                                buttonBack.setVisibility(View.VISIBLE);
                                autoTextViewCourseName.setVisibility(View.VISIBLE);
                                autoTextViewSubjectName.setVisibility(View.GONE);
                            }
                            else{
                                Toast.makeText(getActivity(), "Error : " + message , Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Error : " + e.toString() , Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("SubjectId",SubjectId);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void getAllData(){
        final String SubjectId = (String) SubjectList.get(autoTextViewSubjectName.getText().toString().trim());
        final String FacultyId = controller.getCurrentUser().getUserId();
        Course course = (Course) CourseList.get(autoTextViewCourseName.getText().toString());
        final String CourseId = course.getCourseId();
        final int StudentCount = course.getStudentCount();
        final String Qualification_Code = course.getQualification_Code();
        final String DepartmentId = course.getDepartmentId() ;
        final String Section = spinnerSelectSection.getSelectedItem().toString();
        final String Batch = spinnerSelectBatch.getSelectedItem().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getStudentData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject json = new JSONObject(response);
                            String success = json.getString("status");
                            String message = json.getString("message");
                            ALLStudent = new ArrayList<>();
                            if(success.equalsIgnoreCase("True")) {
                                JSONArray jArray = json.getJSONArray("StudentList");
                                for (int i = 0; i < jArray.length(); i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    Student S = new Student(json_data.getString("RollNo"),
                                                            Integer.parseInt(json_data.getString("Present")),
                                                            Integer.parseInt(json_data.getString("Absent")));
                                    ALLStudent.add(S);
                                    StudentList.put(json_data.getString("RollNo"),S);
                                }
                                adapter = new StudentAdapter(getActivity(),ALLStudent);
                                recyclerView.setAdapter(adapter);
                            }

                            else{
                                Toast.makeText(getActivity(), "Error : " + message , Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Error : " + e.toString() +'\n' +response , Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("SubjectId",SubjectId);
                params.put("CourseId",CourseId);
                params.put("DepartmentId",DepartmentId);
                params.put("Section",Section);
                params.put("Qualification_Code",Qualification_Code);
                params.put("Batch",Batch);
                params.put("Student_Count",Integer.toString(StudentCount));
                params.put("FacultyId",FacultyId);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
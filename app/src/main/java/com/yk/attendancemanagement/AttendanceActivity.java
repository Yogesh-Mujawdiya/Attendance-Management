package com.yk.attendancemanagement;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yk.attendancemanagement.Adapter.AttendanceAdapter;
import com.yk.attendancemanagement.Class.Student;
import com.yk.attendancemanagement.Controller.StoreData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceActivity extends Activity {

    private RecyclerView recyclerView;
    private AttendanceAdapter adapter;
    private List<Student> StudentsList = new ArrayList<>();
    private Button SaveAttendanceBtn;
    StoreData controller;
    String Host, getStudentData_url, saveAttendance_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        recyclerView = findViewById(R.id.AttendanceList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,5);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(new GridLayoutManager(this,5));
        Host = getString(R.string.localhost);
        getStudentData_url =  Host+"/getStudentRollNo.php";
        saveAttendance_url =  Host+"/saveAttendance.php";
        controller = new StoreData(this);
        SaveAttendanceBtn = findViewById(R.id.SaveAttendanceBtn);

        SaveAttendanceBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                SaveAttendance();
            }
        });
        getStudentData();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void SaveAttendance() {
        Bundle extras = getIntent().getExtras();
        final String SubjectId = extras.getString("Subject_Id");
        final String FacultyId = controller.getCurrentUser().getUserId();
        final String Password = controller.getCurrentUser().getPassword();
        final String PresentRollNo = adapter.getPresentRollNo();

        final String AbsentRollNo = adapter.getAbsentRollNo();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, saveAttendance_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            String success = json.getString("status");
                            String message = json.getString("message");
                            if(success.equalsIgnoreCase("True")) {
                                Toast.makeText(getApplicationContext(), "Success : " + message, Toast.LENGTH_SHORT).show();
                                AttendanceActivity.super.onBackPressed();
                            }
                            else
                                Toast.makeText(getApplicationContext(), "Error : " + message , Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error : " + e.toString() + "\n" + response, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("FacultyId",FacultyId);
                params.put("SubjectId",SubjectId);
                params.put("PresentRollNo",PresentRollNo);
                params.put("AbsentRollNo",AbsentRollNo);
                params.put("Password",Password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void getStudentData() {
        Bundle extras = getIntent().getExtras();
        final String SubjectId= extras.getString("Subject_Id");
        final String CourseId= extras.getString("Course_Id");
        final String DepartmentId= extras.getString("Department_Id");
        final String Batch= extras.getString("Batch");
        final String Section= extras.getString("Section");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getStudentData_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            String success = json.getString("status");
                            String message = json.getString("message");
                            JSONArray JArray  = json.getJSONArray("SList");
                            for (int i = 0; i < JArray.length(); i++)
                            {
                                String Data = JArray.getString(i);
                                StudentsList.add(new Student(Data));
                            }
                            adapter = new AttendanceAdapter(getApplicationContext(),StudentsList);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error : " + e.toString() , Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("SubjectId",SubjectId);
                params.put("CourseId",CourseId);
                params.put("DepartmentId",DepartmentId);
                params.put("Batch",Batch);
                params.put("Section",Section);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}

package com.yk.attendancemanagement.Student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
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
import com.yk.attendancemanagement.Adapter.StudentSubjectAdapter;
import com.yk.attendancemanagement.Adapter.SubjectAdapter;
import com.yk.attendancemanagement.Class.Subject;
import com.yk.attendancemanagement.Class.User;
import com.yk.attendancemanagement.Controller.StoreData;
import com.yk.attendancemanagement.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private Dictionary SubjectList = new Hashtable();
    private List<Subject> MySubjects;
    private StoreData controller;
    private StudentSubjectAdapter adapter;
    private RecyclerView recyclerView;
    private String Host , getStudentSubject_url;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_student_home, container, false);
        Host = getString(R.string.localhost);
        getStudentSubject_url =  Host+"/getStudentSubject.php";
        controller = new StoreData(getActivity());
        recyclerView = root.findViewById(R.id.recyclerViewStudentSubjects);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        getMySubject();

        return root;
    }

    private void getMySubject(){
        User user = controller.getCurrentUser();
        final String Id = user.getUserId();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getStudentSubject_url,
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
                                for (int i = 0; i < jArray.length(); i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    MySubjects.add(new Subject(json_data.getString("Id"), json_data.getString("Name")
                                            , Integer.parseInt(json_data.getString("Total")), Integer.parseInt(json_data.getString("Attend"))));
                                }
                                adapter = new StudentSubjectAdapter(getActivity(),MySubjects);
                                recyclerView.setAdapter(adapter);
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
                params.put("Id",Id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}


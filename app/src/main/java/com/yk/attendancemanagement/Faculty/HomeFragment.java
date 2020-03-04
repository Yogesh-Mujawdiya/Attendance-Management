package com.yk.attendancemanagement.Faculty;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yk.attendancemanagement.Adapter.SubjectAdapter;
import com.yk.attendancemanagement.Class.Course;
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

    private FloatingActionButton AddSubjectBtn;
    private CardView AddSubjectCard;
    private Dictionary SubjectList = new Hashtable();
    private Button SaveSubjectBtn;
    private List<Subject> MySubjects;
    private AppCompatAutoCompleteTextView autoTextViewSubjectName;
    private RelativeLayout AddSubjectLayout;
    private ProgressBar progressBarAddSubject;
    private StoreData controller;
    private RecyclerView recyclerView;
    private SubjectAdapter adapter;
    private String Host , getSubject_url, saveSubject_url, getFacultySubject_url;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_faculty_home, container, false);
        AddSubjectBtn = root.findViewById(R.id.AddSubjectButton);
        AddSubjectCard = root.findViewById(R.id.AddSubjectCard);
        autoTextViewSubjectName = root.findViewById(R.id.autoTextViewSubjectName);
        AddSubjectLayout = root.findViewById(R.id.AddSubjectLayout);
        progressBarAddSubject = root.findViewById(R.id.progressBarAddSubject);
        SaveSubjectBtn = root.findViewById(R.id.SaveSubjectButton);
        Host = getString(R.string.localhost);
        getSubject_url =  Host+"/getSubject.php";
        saveSubject_url =  Host+"/saveSubject.php";
        getFacultySubject_url =  Host+"/getFacultySubject.php";
        controller = new StoreData(getActivity());
        recyclerView = root.findViewById(R.id.recyclerViewSubjects);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        getMySubject();

        AddSubjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddSubject();
            }
        });

        SaveSubjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveSubject();
            }
        });

        return root;
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
                                String[] Course = new String[jArray.length()];
                                for (int i = 0; i < jArray.length(); i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    MySubjects.add(new Subject(json_data.getString("Id"), json_data.getString("Name")));
                                }
                                adapter = new SubjectAdapter(getActivity(),MySubjects);
                                recyclerView.setAdapter(adapter);
                            }
                            else{
                                Toast.makeText(getActivity(), "Error : " + message , Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Error : " + e.toString() , Toast.LENGTH_SHORT).show();
                        }
                        progressBarAddSubject.setVisibility(View.GONE);
                        AddSubjectLayout.setVisibility(View.VISIBLE);
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

    private void AddSubject() {
        if(AddSubjectCard.isShown()) {
            AddSubjectCard.setVisibility(View.GONE);
            final OvershootInterpolator interpolator = new OvershootInterpolator();
            ViewCompat.animate(AddSubjectBtn).
                    rotation(0f).
                    withLayer().
                    setDuration(300).
                    setInterpolator(interpolator).
                    start();
        }else {
            AddSubjectCard.setVisibility(View.VISIBLE);
            AddSubjectLayout.setVisibility(View.GONE);
            progressBarAddSubject.setVisibility(View.VISIBLE);
            getSubject();
            final OvershootInterpolator interpolator = new OvershootInterpolator();
            ViewCompat.animate(AddSubjectBtn).
                    rotation(45f).
                    withLayer().
                    setDuration(300).
                    setInterpolator(interpolator).
                    start();
        }
    }

    private void getSubject() {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, getSubject_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject json = new JSONObject(response);
                                JSONArray jArray = json.getJSONArray("SubjectList");
                                String[] Subject = new String[jArray.length()];
                                for(int i=0; i<jArray.length(); i++){
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    Subject[i]= json_data.getString("Name");
                                    SubjectList.put(Subject[i],json_data.getString("Id"));
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, Subject);
                                autoTextViewSubjectName.setThreshold(1);
                                autoTextViewSubjectName.setAdapter(adapter);
                                progressBarAddSubject.setVisibility(View.GONE);
                                AddSubjectLayout.setVisibility(View.VISIBLE);
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
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
    }

    public void SaveSubject() {
        if (SubjectList.get(autoTextViewSubjectName.getText().toString().trim()) == null) {
            Toast.makeText(getActivity(), "Error : " + "Subject Name Not Found !", Toast.LENGTH_SHORT).show();
            return;
        }
        final String SubjectId = (String) SubjectList.get(autoTextViewSubjectName.getText().toString().trim());
        User user = controller.getCurrentUser();
        final String FacultyId = user.getUserId();
        final String FacultyPassword = user.getPassword();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, saveSubject_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            String success = json.getString("status");
                            String message = json.getString("message");
                            if(success.equalsIgnoreCase("True")) {
                                autoTextViewSubjectName.setText("");
                                getMySubject();
                                Toast.makeText(getActivity(), "Success : " + message , Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getActivity(), "Error : " + message , Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Error : " + e.toString() , Toast.LENGTH_SHORT).show();
                        }
                        progressBarAddSubject.setVisibility(View.GONE);
                        AddSubjectLayout.setVisibility(View.VISIBLE);
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
                params.put("Id",FacultyId);
                params.put("Password",FacultyPassword);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}
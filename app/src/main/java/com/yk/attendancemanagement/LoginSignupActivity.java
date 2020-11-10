package com.yk.attendancemanagement;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yk.attendancemanagement.Adapter.AttendanceAdapter;
import com.yk.attendancemanagement.Adapter.SubjectAdapter;
import com.yk.attendancemanagement.Class.Course;
import com.yk.attendancemanagement.Class.Subject;
import com.yk.attendancemanagement.Class.User;
import com.yk.attendancemanagement.Controller.StoreData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class LoginSignupActivity extends Activity {

    EditText editTextLoginId, editTextLoginPassword;
    SwitchCompat switchCompatLoginUserType;
    EditText editTextRegisterUserID, editTextRegisterUserName, editTextRegisterMobile, editTextRegisterPassword, editTextRegisterEmail;
    SwitchCompat switchCompatRegisterUserType;
    StoreData controller;
    private String Host , login_url, register_url,webmail_login_url;
    private Button LoginBtn, RegisterBtn;
    ProgressBar progressBarRegister,progressBarLogin;
    View content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_signup);

        controller = new StoreData(this);
        // reference ui
        content = findViewById(R.id.content);
        editTextLoginId = findViewById(R.id.editTextEmail);
        editTextLoginPassword = findViewById(R.id.editTextPassword);
        switchCompatLoginUserType = findViewById(R.id.userType);

        editTextRegisterUserName = findViewById(R.id.editTextSignUpUserName);
        editTextRegisterUserID = findViewById(R.id.editTextSignUpUserId);
        editTextRegisterMobile = findViewById(R.id.editTextSignUpUserMobile);
        editTextRegisterEmail = findViewById(R.id.editTextSignUpUserEmail);
        editTextRegisterPassword = findViewById(R.id.editTextSignUpUserPassword);
        switchCompatRegisterUserType = findViewById(R.id.signUpUserType);
        LoginBtn = findViewById(R.id.btn_Login);
        RegisterBtn = findViewById(R.id.btn_Register);
        progressBarLogin = findViewById(R.id.progressBarLogin);
        progressBarRegister = findViewById(R.id.progressBarRegister);
        Host = getString(R.string.localhost);
        login_url =  Host+"/Login.php";
        register_url = Host+"/SignUp.php";

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginBtn.setVisibility(View.GONE);
                progressBarLogin.setVisibility(View.VISIBLE);
                LoginUser();
            }
        });

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                RegisterBtn.setVisibility(View.GONE);
                progressBarRegister.setVisibility(View.VISIBLE);
                RegisterUser();
            }
        });

        findViewById(R.id.textViewSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.login_layout).setVisibility(View.GONE);
                findViewById(R.id.signUp_layout).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.textViewLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.login_layout).setVisibility(View.VISIBLE);
                findViewById(R.id.signUp_layout).setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void LoginUser() {

        final String userid = editTextLoginId.getText().toString().toLowerCase().trim();
        final String password = editTextLoginPassword.getText().toString().trim();
        final String userType;
        if(switchCompatLoginUserType.isChecked())
            userType = "Faculty";
        else
            userType = "Student";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("status");
                            String message = jsonObject.getString("message");
                            Intent i;
                            if (success.equals("True")) {
                                String name = jsonObject.getString("Name");
                                String mobile = jsonObject.getString("Mobile");
                                String email = jsonObject.getString("Email");
                                Toast.makeText(LoginSignupActivity.this, message , Toast.LENGTH_SHORT).show();
                                if(userType=="Faculty")
                                    i = new Intent(LoginSignupActivity.this, FacultyActivity.class);
                                else
                                    i = new Intent(LoginSignupActivity.this, StudentActivity.class);
                                User user = new User(userid, name, mobile, email, password, userType);
                                controller.setCurrentUser(user);
                                startActivity(i);
                                finish();
                            }
                            else {
                                Toast.makeText(LoginSignupActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginSignupActivity.this, "Error : " + e.toString() , Toast.LENGTH_SHORT).show();
                        }
                        progressBarLogin.setVisibility(View.GONE);
                        LoginBtn.setVisibility(View.VISIBLE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginSignupActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        progressBarLogin.setVisibility(View.GONE);
                        LoginBtn.setVisibility(View.VISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("userId", userid);
                params.put("password", password);
                params.put("userType",userType);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void RegisterUser() {
        final String userId = editTextRegisterUserID.getText().toString();
        final String userName = editTextRegisterUserName.getText().toString();
        final String mobile = editTextRegisterMobile.getText().toString();
        final String email = editTextRegisterEmail.getText().toString();
        final String password = editTextRegisterPassword.getText().toString();
        final String userType;

        if(switchCompatRegisterUserType.isChecked())
            userType = "Faculty";
        else
            userType = "Student";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, register_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("status");
                            String message = jsonObject.getString("message");
                            Toast.makeText(LoginSignupActivity.this, "Response Register Success ! \n" + message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginSignupActivity.this, "Register Error ! " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                        RegisterBtn.setVisibility(View.VISIBLE);
                        progressBarRegister.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginSignupActivity.this, "Register -- Error ! " + error.toString(), Toast.LENGTH_SHORT).show();
                        RegisterBtn.setVisibility(View.VISIBLE);
                        progressBarRegister.setVisibility(View.GONE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userType", userType);
                params.put("userId", userId);
                params.put("userName", userName);
                params.put("mobile", mobile);
                params.put("email", email);
                params.put("pass", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(LoginSignupActivity.this);
        requestQueue.add(stringRequest);

    }

}

package com.yk.attendancemanagement.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import com.yk.attendancemanagement.Class.User;

public class StoreData {

    private static final String KEY_PREFERENCES_NAME = "libraryAppPreferences";

    private Context context;

    public StoreData(Context context)
    {
        this.context = context;
    }


    public void setCurrentUser(User user)
    {
        SharedPreferences pref = context.getSharedPreferences(KEY_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("token", "True");
        editor.putString("Type", user.getUserType());
        editor.putString("Id", user.getUserId());
        editor.putString("Name", user.getName());
        editor.putString("Mobile", user.getMobile());
        editor.putString("Email", user.getEmail());
        editor.putString("Password", user.getPassword());
        editor.commit();
    }

    public boolean isFaculty(){
        SharedPreferences pref = context.getSharedPreferences(KEY_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return pref.getString("Type", "").equals("Faculty");
    }
    public void setHost(String host){
        SharedPreferences pref = context.getSharedPreferences(KEY_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("Host", host);
        editor.commit();
    }
    public String getHost(){
        SharedPreferences pref = context.getSharedPreferences(KEY_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return pref.getString("Host", "");
    }
    public User getCurrentUser()
    {
        SharedPreferences pref = context.getSharedPreferences(KEY_PREFERENCES_NAME, Context.MODE_PRIVATE);

        if (!pref.contains("token"))
            return null;
        User user = new User();
        user.setUserType(pref.getString("Type", ""));
        user.setUserId(pref.getString("Id", ""));
        user.setName(pref.getString("Name", ""));
        user.setEmail(pref.getString("Email", ""));
        user.setMobile(pref.getString("Mobile",""));
        user.setPassword(pref.getString("Password",""));
        return user;
    }

    public void logoutUser(){
        SharedPreferences pref = context.getSharedPreferences(KEY_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear().commit();
    }

    public boolean isLogin(){
        SharedPreferences pref = context.getSharedPreferences(KEY_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return pref.contains("token");
    }
}

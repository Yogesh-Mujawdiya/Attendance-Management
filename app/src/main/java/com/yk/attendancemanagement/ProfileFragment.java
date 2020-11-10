package com.yk.attendancemanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.yk.attendancemanagement.Class.User;
import com.yk.attendancemanagement.Controller.StoreData;

public class ProfileFragment extends Fragment {

    private StoreData controller;
    private ImageButton imageButton_Profile;
    private TextView Username, Name, Sex, Email, Age, Contact;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        controller = new StoreData(getActivity());
        Username = root.findViewById(R.id.Profile_Username_TV);
        Name = root.findViewById(R.id.Profile_Name_TV);
        Sex = root.findViewById(R.id.Profile_Gender_TV);
        Email = root.findViewById(R.id.Profile_Email_TV);
        Age = root.findViewById(R.id.Profile_Age_TV);
        Contact = root.findViewById(R.id.Profile_Contact_TV);
        setValue();
        return root;
    }

    void setValue(){
        User user = controller.getCurrentUser();
        Username.setText(user.getUserId());
        Name.setText(user.getName());
        Sex.setText(user.getSex());
        Email.setText(user.getEmail());
        Contact.setText(user.getMobile());

    }
}
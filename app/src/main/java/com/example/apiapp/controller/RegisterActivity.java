package com.example.apiapp.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.apiapp.R;
import com.example.apiapp.model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    // component
    private TextInputEditText txtUserName;
    private TextInputEditText txtEmail;
    private TextInputEditText txtPhone;
    private TextInputEditText txtPassword;
    public static final String USER = "USER";
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button btnRegister = findViewById(R.id.registerButton);
        txtUserName = findViewById(R.id.txtUserName);
        txtPhone = findViewById(R.id.txtPhone);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

    }

    // if when file attribute and user clicked on back button clear date
    // because return the user fill it
    @Override
    protected void onStop() {
        super.onStop();
        txtUserName.getText().clear();
        txtPhone.getText().clear();
        txtEmail.getText().clear();
        txtPassword.getText().clear();

    }


    // when clicked on register button save data on sharedPreferences
    // and add teh added user to the array list
    public void registerAction(View view) {

        // Initialize usersList
        ArrayList<User> usersList = new ArrayList<>();

        // return previous users
        String usersJson = sharedPreferences.getString(USER, "");

        // If there are existing users, parse the JSON
        if (!usersJson.isEmpty()) {
            Type userListType = new TypeToken<ArrayList<User>>() {}.getType();
            usersList = new Gson().fromJson(usersJson, userListType);
        }

        // if redundancy data input
        for (int i = 0; i < usersList.size(); i++) {

            if(txtUserName.getText().toString().equals(usersList.get(i).getUserName()) && txtPassword.getText().toString().equals(usersList.get(i).getPassword())){
                Toast.makeText(RegisterActivity.this,"Already exit this user",Toast.LENGTH_SHORT).show();
                txtUserName.getText().clear();
                txtPassword.getText().clear();
                txtPhone.getText().clear();
                txtEmail.getText().clear();
                return;
            }
        }

        // new suer
        User user = new User(txtUserName.getText().toString(),
                txtEmail.getText().toString(),
                txtPhone.getText().toString(),
                txtPassword.getText().toString());

        usersList.add(user);

        Gson gson = new Gson();
        // Set the array to sharedPreferences
        String userObject = gson.toJson(usersList);
        editor.putString(USER, userObject);

        System.out.println("list size = " + usersList.size());

        editor.commit();

        Toast.makeText(this, "Register Successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
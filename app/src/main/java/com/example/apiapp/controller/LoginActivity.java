package com.example.apiapp.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.apiapp.R;
import com.example.apiapp.model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;


public class LoginActivity extends AppCompatActivity  {

    // components
    private CheckBox checkBox;
    private TextInputEditText userName;
    private TextInputEditText password;
    private boolean flag = false;

    public static SharedPreferences sharedPreferencesSave;
    public static SharedPreferences.Editor editorSave ;
    public static final String  NAME  = "NAME";
    public static final String  PASSWORD  = "PASSWORD";
    public static final String FLAG = "FLAG";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // call methods
        setupComponents();
        checkPref();
    }



    // action button when user clicked to login
    public void actionLogin(View view) {
        // if empty text filed
        if(userName.getText().toString().isEmpty() || userName.getText().toString().isEmpty()){
            Toast.makeText(LoginActivity.this, "Please Enter the data", Toast.LENGTH_SHORT).show();
        }
        else{
            Gson gson = new Gson();
            String user = RegisterActivity.sharedPreferences.getString(RegisterActivity.USER,"");
            // if return data is not empty from shared pref
            if(user.toString() != ""){
                User[] usersArray = gson.fromJson(user,User[].class);
                boolean isUserFound = false;
                for (int i = 0; i < usersArray.length; i++) {
                    System.out.println(usersArray[i]);
                    if(usersArray[i].getUserName().equals(userName.getText().toString())&& usersArray[i].getPassword().equals(password.getText().toString())) {
                        // when user clicked on check box to save date into another shared pref
                        saveData(userName.getText().toString(),password.getText().toString());

                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        isUserFound = true;
                        break;
                    }
                }
                 if(!isUserFound){
                    Toast.makeText(LoginActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(LoginActivity.this, "Not Found User", Toast.LENGTH_SHORT).show();
                return;
            }

        }
    }

    // go to activity register
    public void actionRegister(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    // when user select check box to save data into shared pref
    public void saveData(String userName , String password){

        if(checkBox.isChecked()){
            flag=false;
            if(!flag){
                String existingUserName = RegisterActivity.sharedPreferences.getString(NAME, "");
                String existingPassword = RegisterActivity.sharedPreferences.getString(PASSWORD, "");

                // Update values only if they are different from the new values
                if (!existingUserName.equals(userName)) {
                    editorSave.putString(NAME, userName);
                }

                if (!existingPassword.equals(password)) {
                    editorSave.putString(PASSWORD, password);
                }

                editorSave.putBoolean(FLAG, true);
                editorSave.commit();

                // Update the fields in the current user
                this.userName.setText(userName);
                this.password.setText(password);
                checkBox.setChecked(true);
            }

        }
    }


    // when open the activity login to return saved data from shared pref and set to the text fild
    private void checkPref(){
        RegisterActivity.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        flag = sharedPreferencesSave.getBoolean(FLAG,false);
        if(flag){
            String name = sharedPreferencesSave.getString(NAME, "");
            String passwd = sharedPreferencesSave.getString(PASSWORD, "");
            this.userName.setText(name);
            this.password.setText(passwd);
            checkBox.setChecked(true);
        }
    }

    void setupComponents() {
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRegister = findViewById(R.id.btnRegister);
        checkBox = findViewById(R.id.checkBox);
        TextInputLayout txtUserName = findViewById(R.id.txtUserName);
        TextInputLayout txtPassword = findViewById(R.id.txtPassword);
        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        sharedPreferencesSave = PreferenceManager.getDefaultSharedPreferences(this);
        editorSave = sharedPreferencesSave.edit();
    }




}
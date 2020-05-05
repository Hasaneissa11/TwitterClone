package com.example.twitterclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    EditText emailLoginEditText , passwordLoginEditText ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);


        initializeAllUiComponent();


    }

    public void rootTapped(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE) ;
        inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(),0);


    }

    public void Login(View view) {

        final ProgressDialog progressDialog =new ProgressDialog(this);


        if (emailLoginEditText.getText().toString().equals("")||passwordLoginEditText.getText().toString().equals(""))
        {
            Toast.makeText(Login.this ,"Some Fields are Empty",Toast.LENGTH_SHORT).show();
        }
        else
            {

                progressDialog.setMessage("Login");
                progressDialog.show();
            ParseUser.logInInBackground(emailLoginEditText.getText().toString(),passwordLoginEditText.getText().toString(),new LogInCallback()
            {
                @Override
                public void done(ParseUser user, ParseException e) {

                    if (e==null )
                    {
                        Intent intent = new Intent(Login.this,SocialMedia.class);
                        progressDialog.dismiss();
                        startActivity(intent);


                    }
                    else
                    {
                        Toast.makeText(Login.this,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }


    public void initializeAllUiComponent(){
        emailLoginEditText = findViewById(R.id.emailLoginEditText);
        passwordLoginEditText = findViewById(R.id.passwordLoginEditText);
    }


}

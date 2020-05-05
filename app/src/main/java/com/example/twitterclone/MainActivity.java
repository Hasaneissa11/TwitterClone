package com.example.twitterclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
        EditText emailET , passwordEt ,usernameET ;
        Button signUpBtn ;
        TextView textView ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        initializeAllUiComponent();
        if (ParseUser.getCurrentUser()!=null)
        {
            transitionToSocialMedia();
        }


    }

    public void SignUp(View view) {
        if (usernameET.getText().toString().equals("")||
                passwordEt.getText().toString().equals("")||
                emailET.getText().toString().equals(""))
        {
            Toast.makeText(MainActivity.this ,"Some field are Empty",Toast.LENGTH_SHORT).show();

        }
        else {

            ParseUser parseUser = new ParseUser();
            parseUser.setEmail(emailET.getText().toString());
            parseUser.setUsername(usernameET.getText().toString());
            parseUser.setPassword(passwordEt.getText().toString());

            final ProgressDialog progressDialog =new ProgressDialog(this);
            progressDialog.setMessage("Signing Up");
            progressDialog.show();

            parseUser.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e==null) {
                        transitionToSocialMedia();

                    }
                    else
                    {
                        Toast.makeText(MainActivity.this , e.getMessage().toString(),Toast.LENGTH_SHORT).show();

                    }
                    progressDialog.dismiss();
                }
            });
        }
    }


     public void initializeAllUiComponent(){

        emailET = findViewById(R.id.emailEditText);
        passwordEt = findViewById(R.id.passwordEditText);
        usernameET = findViewById(R.id.usernameEditText);
        signUpBtn = findViewById(R.id.signUpBtn);
        textView = findViewById(R.id.logInTextView);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Login.class);
                startActivity(intent);
            }
        });



        passwordEt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if (i==keyEvent.KEYCODE_ENTER&&keyEvent.getAction()==KeyEvent.ACTION_DOWN)
                {
                    signUpBtn.callOnClick();                }
                return false;
            }
        });



     };
    public void transitionToSocialMedia( ){

        Intent intent = new Intent(MainActivity.this,SocialMedia.class);
        startActivity(intent);
    }

    public void rootTapped(View view ){
        InputMethodManager inputMethodManager =(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE) ;
        inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(),0);


    }

}


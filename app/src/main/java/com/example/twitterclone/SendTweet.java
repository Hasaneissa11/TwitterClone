package com.example.twitterclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class SendTweet extends AppCompatActivity {
    EditText editText ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6886c5")));
        getSupportActionBar().setTitle("");
        setContentView(R.layout.activity_send_tweet);
        editText = findViewById(R.id.tweet);

    }

    public void SendTweets(View view) {
        ParseObject parseObject = new ParseObject("MyTweets");
        parseObject.put("username", ParseUser.getCurrentUser().getUsername());
        parseObject.put("tweets",editText.getText().toString());
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if (e==null) {
                    Toast.makeText(SendTweet.this, "Done", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(SendTweet.this, "SomeThing Error", Toast.LENGTH_SHORT).show();


                }
                progressDialog.dismiss();
                Intent intent = new Intent(SendTweet.this,UsersTwiter.class);
                startActivity(intent);
                finish();

            }});





    }
}

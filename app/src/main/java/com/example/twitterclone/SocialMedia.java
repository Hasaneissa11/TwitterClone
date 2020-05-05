package com.example.twitterclone;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class SocialMedia extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView ;
    ArrayList<String> nameOfUsers ;
    ArrayAdapter<String> adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6886c5")));
        getSupportActionBar().setTitle("Friends");

        initializeAllComponent();
        getUsersName();

    }

    public void initializeAllComponent(){


        listView = findViewById(R.id.listUsers);
        nameOfUsers = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_checked,nameOfUsers);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(SocialMedia.this);


    }

    public void getUsersName(){

        ParseQuery parseQuery = ParseUser.getQuery();
            parseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
            parseQuery.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if (e==null)
                    {
                        if (objects.size()>0)
                        {
                            for (ParseUser users:objects)
                            {
                                nameOfUsers.add(users.getUsername());


                            }
                            listView.setAdapter(adapter);
                            if (ParseUser.getCurrentUser().getList("fanOf")!=null) {
                                for (String twitterUsers : nameOfUsers) {
                                    ParseUser.getCurrentUser().getList("fanOf").contains(twitterUsers);
                                    listView.setItemChecked(nameOfUsers.indexOf(twitterUsers), true);

                                }
                            }
                        }

                    }
                }
            });
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId()==R.id.LogOutUserItem)
            {
                Intent intent = new Intent(SocialMedia.this , MainActivity.class);
                startActivity(intent);
                ParseUser.logOut();
                finish();
            }
            else if (item.getItemId()==R.id.sendTweet)
            {

                Intent intent = new Intent(SocialMedia.this , SendTweet.class);
                startActivity(intent);
            }
            else if (item.getItemId()==R.id.Home)

            {
                Intent intent = new Intent(SocialMedia.this,UsersTwiter.class);
                startActivity(intent);

            }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        CheckedTextView checkedTextView = (CheckedTextView) view ;
        if (checkedTextView.isChecked())
        {
            Toast.makeText(SocialMedia.this , "You are following "+nameOfUsers.get(i),Toast.LENGTH_SHORT).show();
            ParseUser.getCurrentUser().add("fanOf",nameOfUsers.get(i));


        }
        else {
            Toast.makeText(SocialMedia.this , "You are un following "+nameOfUsers.get(i),Toast.LENGTH_SHORT).show();
            ParseUser.getCurrentUser().getList("fanOf").remove(nameOfUsers.get(i));
            List fanOfUsers = ParseUser.getCurrentUser().getList("fanOf");
            ParseUser.getCurrentUser().put("fanOf",fanOfUsers);

        }
        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e!=null)
                {
                    e.printStackTrace();
                }
            }
        });

    }


}


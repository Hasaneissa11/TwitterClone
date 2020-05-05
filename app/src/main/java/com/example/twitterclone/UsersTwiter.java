package com.example.twitterclone;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

    public class UsersTwiter extends AppCompatActivity {
        ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6886c5")));
        getSupportActionBar().setTitle("Home");

        setContentView(R.layout.activity_users_twiter);
        listView = findViewById(R.id.twitterList);
        final ArrayList<HashMap<String,String>> tweetList = new ArrayList<>();
        final SimpleAdapter simpleAdapter = new SimpleAdapter(UsersTwiter.this , tweetList ,
                android.R.layout.simple_list_item_2,new String[]{"usernameTweet","tweetValue"},
                new int[]{android.R.id.text1,android.R.id.text2});

        try
        {
            ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("MyTweets");
            parseQuery.whereContainedIn("username", ParseUser.getCurrentUser().getList("fanOf"));
            parseQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (objects.size()>0 && e ==null)
                    {
                        for (ParseObject parseObject :objects)
                        {
                            HashMap<String,String> userTweet = new HashMap<>();
                            userTweet.put("usernameTweet",parseObject.getString("username"));
                            userTweet.put("tweetValue",parseObject.getString("tweets"));
                            tweetList.add(userTweet);

                        }
                            listView.setAdapter(simpleAdapter);
                    }

                }
            });

        }
        catch (Exception e)
        {}
    }
}

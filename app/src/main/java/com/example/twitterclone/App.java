package com.example.twitterclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("nea3FnHyxky0xvDl3Fy7Nv8CkvBk6zrTX3EqCubZ")
                .clientKey("huqWBBKLaTC2vWuax9XOcU5wSJKjYfFbmHiq3YpJ")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
    }


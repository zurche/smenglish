package com.smenglish;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.concurrent.TimeUnit;

public class Splash extends Activity {

    public static final long THREE_SECONDS_IN_MILLIS = TimeUnit.SECONDS.toMillis(3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent openBaseActivity = new Intent(Splash.this, BaseActivity.class);
                startActivity(openBaseActivity);
                finish();
            }
        };

        handler.postDelayed(runnable, THREE_SECONDS_IN_MILLIS);
    }
}

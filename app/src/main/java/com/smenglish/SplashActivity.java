package com.smenglish;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();

    @BindView(R.id.login_button)
    LoginButton login_button;

    @BindView(R.id.unlogged_user_message)
    LinearLayout unlogged_user_message;
    @BindView(R.id.logging_user_message)
    LinearLayout logging_user_message;

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        callbackManager = CallbackManager.Factory.create();

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logging_user_message.setVisibility(View.VISIBLE);
                unlogged_user_message.setVisibility(View.GONE);
            }
        });

        login_button.setReadPermissions("email");
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "Success facebook login");
                startHomeActivity();
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "Cancelled facebook login");
                Toast.makeText(SplashActivity.this, "Error en login a facebook", Toast.LENGTH_SHORT).show();
                logging_user_message.setVisibility(View.GONE);
                unlogged_user_message.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(FacebookException exception) {
                Log.e(TAG, "Error facebook login", exception);
                Toast.makeText(SplashActivity.this, "Error en login a facebook", Toast.LENGTH_SHORT).show();
                logging_user_message.setVisibility(View.GONE);
                unlogged_user_message.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != AccessToken.getCurrentAccessToken()) {
            if (!AccessToken.getCurrentAccessToken().isExpired()) {
                startHomeActivity();
            }
        }
    }

    private void startHomeActivity() {
        Intent startHome = new Intent(this, BaseActivity.class);
        startActivity(startHome);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}

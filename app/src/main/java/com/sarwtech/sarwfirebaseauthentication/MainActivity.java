package com.sarwtech.sarwfirebaseauthentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.sarwtech.sarw_firebase_authentication.SarwAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SarwAuth sarwAuth = new SarwAuth(MainActivity.this, FirebaseAuth.getInstance(), new Intent(MainActivity.this, SampleActivity.class));
        sarwAuth.init();
    }
}
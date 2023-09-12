package com.sarwtech.sarw_firebase_authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

/*
Created by: Hasham Sarwar
Dated: September 12, 2023
 */
public class SarwRegisterActivity extends AppCompatActivity {
    private ProgressDialog mRegisterProgress;
    private TextView registerSignInBtn;
    private Button registerBtn;
    private EditText registerEmail, registerPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sarw_register);
        getSupportActionBar().hide();

        registerSignInBtn = findViewById(R.id.registerSignInBtn);
        registerBtn = findViewById(R.id.registerBtn);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);

        mRegisterProgress = new ProgressDialog(this);
        mRegisterProgress.setTitle("Registration");
        mRegisterProgress.setMessage("Please Wait ...");
        mRegisterProgress.setCancelable(false);

        registerSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SarwRegisterActivity.this, SarwLoginActivity.class));
                finish();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInput();
            }
        });
    }
    private void validateInput() {
//        String name = binding.registerName.getText().toString().trim();
        String email = registerEmail.getText().toString().trim();
        String password = registerPassword.getText().toString().trim();
//        boolean isNameValid = name.length() > 2;
        boolean isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        boolean isPasswordValid = password.length() >= 8;
        if (isEmailValid && isPasswordValid){
            mRegisterProgress.show();
            try {
                SarwReceived.getAuth().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            startActivity(SarwReceived.getSuccessIntent());
                            finish();
                            mRegisterProgress.dismiss();
                        }else{
                            Toast.makeText(SarwRegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            mRegisterProgress.dismiss();
                        }
                    }
                });
            }catch (Exception e){

            }
        }else{
//            if (!isNameValid){
//                binding.registerName.setError("Please Enter the valid name");
//            }
            if (!isEmailValid){
                registerEmail.setError("Please Enter the valid email address");
            }
            if (!isPasswordValid){
                registerPassword.setError("Please Enter valid password");
            }
        }
    }
}
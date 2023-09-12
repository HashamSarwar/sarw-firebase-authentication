package com.sarwtech.sarw_firebase_authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
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
public class SarwLoginActivity extends AppCompatActivity {
    private ProgressDialog mLoginProgress;
    private Button loginBtn;
    private TextView loginSignUpBtn,loginForgotPassword;
    private EditText loginEmail, loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sarw_login);
        getSupportActionBar().hide();

        loginBtn = findViewById(R.id.loginBtn);
        loginSignUpBtn = findViewById(R.id.loginSignUpBtn);
        loginForgotPassword = findViewById(R.id.loginForgotPassword);
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);

        mLoginProgress = new ProgressDialog(this);
        mLoginProgress.setTitle("Login");
        mLoginProgress.setMessage("Please Wait ...");
        mLoginProgress.setCancelable(false);

        loginSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SarwLoginActivity.this, SarwRegisterActivity.class));
                finish();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInput();
            }
        });

        loginForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassword();
            }
        });

    }
    private void forgotPassword() {
        ProgressDialog forgotPasswordProgress = new ProgressDialog(this);
        forgotPasswordProgress.setTitle("Forgot Password");
        forgotPasswordProgress.setMessage("Please Wait ...");
        forgotPasswordProgress.setCancelable(false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.sarw_forgot_password_dialog, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        EditText email = view.findViewById(R.id.forgotPasswordEmail);
        Button cancel = view.findViewById(R.id.forgotPasswordCancelBtn);
        Button send = view.findViewById(R.id.forgotPasswordSendBtn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String e = email.getText().toString().trim();
                boolean isEmailValid = Patterns.EMAIL_ADDRESS.matcher(e).matches();
                if (isEmailValid){
                    try {
                        forgotPasswordProgress.show();
                        SarwReceived.getAuth().sendPasswordResetEmail(e).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    forgotPasswordProgress.dismiss();
                                    dialog.dismiss();
                                    Toast.makeText(SarwLoginActivity.this, "Check your registered email", Toast.LENGTH_LONG).show();
                                }else{
                                    forgotPasswordProgress.dismiss();
                                    Toast.makeText(SarwLoginActivity.this, task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }catch (Exception ee){

                    }
                }else{
                    email.setError("Please enter a valid email address");
                }
            }
        });
        dialog.show();
    }

    private void validateInput() {
        String email = loginEmail.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();
        boolean isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        boolean isPasswordValid = password.length() >= 8;
        if (isEmailValid && isPasswordValid){
            mLoginProgress.show();
            try {
                SarwReceived.getAuth().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            startActivity(SarwReceived.getSuccessIntent());
                            mLoginProgress.dismiss();
                        }else{
                            Toast.makeText(SarwLoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            mLoginProgress.dismiss();
                        }
                    }
                });
            }catch (Exception e){

            }
        }else{
            if (!isEmailValid){
                loginEmail.setError("Please Enter the valid email address");
            }
            if (!isPasswordValid){
                loginPassword.setError("Please Enter valid password");
            }
        }
    }
}
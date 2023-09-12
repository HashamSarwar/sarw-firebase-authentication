package com.sarwtech.sarw_firebase_authentication;

import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
/*
Created by: Hasham Sarwar
Dated: September 12, 2023
 */
public class SarwReceived {
    public static FirebaseAuth mAuth;
    public static Intent mSuccessIntent;

    public static void setAuth(FirebaseAuth mAuth) {
        SarwReceived.mAuth = mAuth;
    }

    public static void setSuccessIntent(Intent mSuccessIntent) {
        SarwReceived.mSuccessIntent = mSuccessIntent;
    }

    public static FirebaseAuth getAuth() {
        return mAuth;
    }

    public static Intent getSuccessIntent() {
        return mSuccessIntent;
    }
}

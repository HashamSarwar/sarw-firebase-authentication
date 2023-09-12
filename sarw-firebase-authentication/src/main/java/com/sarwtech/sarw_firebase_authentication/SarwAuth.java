package com.sarwtech.sarw_firebase_authentication;

import android.app.Activity;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;

/*
Created by: Hasham Sarwar
Dated: September 12, 2023
 */
public class SarwAuth {
    private Activity mContext;
    private Intent mSuccessIntent;



    public SarwAuth(Activity mContext, FirebaseAuth mAuth, Intent mSuccessIntent) {
        this.mContext = mContext;
        this.mSuccessIntent = mSuccessIntent;
        SarwReceived.setAuth(mAuth);
        SarwReceived.setSuccessIntent(mSuccessIntent);
    }

    public void init (){
//        mContext.startActivity(new Intent(mContext, SarwLoginActivity.class));
        if (SarwReceived.getAuth().getCurrentUser() != null){
            mContext.startActivity(mSuccessIntent);
        }else{
            mContext.startActivity(new Intent(mContext, SarwLoginActivity.class));
        }
        mContext.finish();
    }

    public static void logout(Activity activity){
        SarwReceived.getAuth().signOut();
        activity.startActivity(new Intent(activity, SarwLoginActivity.class));
        activity.finish();
    }
}

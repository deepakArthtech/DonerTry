package com.ats.blooddonor.Model;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreManager {
    private  static String SHARED_PREF_NAME = "manageaccesstoken";
    public SharedPreferences sharedPreferences;
    Context context;
    private SharedPreferences.Editor editor;

    public SharedPreManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

//    public SharedPreManager() {
//        getAcces_token();
//    }

    public void saveUser(String access_token,String type){


        editor = sharedPreferences.edit();
        editor.putString("access_token", access_token);
        editor.putString("token_type",type);
        editor.putBoolean("logged", true);
        editor.apply();
        editor.commit();
    }


    public String getAcces_token(){
        return sharedPreferences.getString("access_token","");
    }

    public boolean isLoggedIn(){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getBoolean("logged", false);


    }

    public void logout(){
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}

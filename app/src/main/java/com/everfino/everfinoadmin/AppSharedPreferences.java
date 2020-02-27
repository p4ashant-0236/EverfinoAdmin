package com.everfino.everfinoadmin;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class AppSharedPreferences {
     SharedPreferences sharedPreferences;
     Context context;

     int adminid;
     String username;

     final String prefname="EVERFINO_PREF";
     public  AppSharedPreferences(Context context)
     {  this.context=context;
         sharedPreferences=context.getSharedPreferences(prefname,Context.MODE_PRIVATE);
     }

     public void setPref(int adminid,String username)
     {
         this.adminid=adminid;
         this.username=username;


         sharedPreferences.edit().putInt("adminid",this.adminid).putString("username",this.username).commit();
     }

     public HashMap<String,String> getPref()
     {  HashMap<String,String> map=new HashMap<>();
         map.put("adminid",sharedPreferences.getInt("adminid",0)+"");
         map.put("username",sharedPreferences.getString("username",""));

         return map;
     }
     public  void clearPref()
     {
         sharedPreferences.edit().clear().commit();
     }
}

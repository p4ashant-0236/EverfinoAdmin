package com.everfino.everfinoadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.everfino.everfinoadmin.ApiConnection.Api;
import com.everfino.everfinoadmin.ApiConnection.ApiClient;
import com.everfino.everfinoadmin.Model.AdminLoginResponse;
import com.google.gson.JsonObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edt_usernme, edt_password;
    Button btn_login;
    ProgressDialog progressDialog;
    private static Api apiService;
    AppSharedPreferences appSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_usernme = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);

        btn_login = findViewById(R.id.btn_login);

        apiService= ApiClient.getClient().create(Api.class);
        appSharedPreferences=new AppSharedPreferences(this);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(true);
                progressDialog.setMessage("Validating...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                admin_login();
            }
        });
    }

    public void admin_login() {

        JsonObject inputData=new JsonObject();
        inputData.addProperty("username",edt_usernme.getText().toString());
        inputData.addProperty("password",edt_password.getText().toString());

        Call<AdminLoginResponse> call = apiService.admin_login(inputData);
        call.enqueue(new Callback<AdminLoginResponse>() {
            @Override
            public void onResponse(Call<AdminLoginResponse> call, Response<AdminLoginResponse> response) {
                progressDialog.dismiss();
                Log.e("####res",response.body().getStatus().toString());
                if(response.body().getStatus()==true) {
                    appSharedPreferences.setPref(response.body().getAdminid(), response.body().getEmail());
                    Intent i=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "check username and password", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<AdminLoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("####err",t.toString());
            }
        });
    }
}

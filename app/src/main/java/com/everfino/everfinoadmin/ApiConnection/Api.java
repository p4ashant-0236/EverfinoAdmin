package com.everfino.everfinoadmin.ApiConnection;

import com.everfino.everfinoadmin.Model.AdminLoginResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {

    @POST("admin_login")
    Call<AdminLoginResponse> admin_login(
            @Body JsonObject object);

}

package com.everfino.everfinoadmin.ApiConnection;

import com.everfino.everfinoadmin.Model.AdminLoginResponse;
import com.everfino.everfinoadmin.Model.RestList;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @POST("admin_login")
    Call<AdminLoginResponse> admin_login(
            @Body JsonObject object);

    @GET("rest/")
    Call<List<RestList>> get_Rest();
    @POST("rest/add")
    Call<RestList> add_Rest(@Body JsonObject object);

    @PUT("rest/modify/{restid}")
    Call<RestList> update_Rest(@Path("restid") int restid, @Body RestList obj);

    @DELETE("rest/delete/{restid}")
    Call<RestList> delete_Rest(@Path("restid") int restid);


}

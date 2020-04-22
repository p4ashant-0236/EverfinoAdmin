package com.everfino.everfinoadmin.Fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.everfino.everfinoadmin.ApiConnection.Api;
import com.everfino.everfinoadmin.ApiConnection.ApiClient;
import com.everfino.everfinoadmin.AppSharedPreferences;
import com.everfino.everfinoadmin.LoginActivity;
import com.everfino.everfinoadmin.MainActivity;
import com.everfino.everfinoadmin.Model.AdminLoginResponse;
import com.everfino.everfinoadmin.R;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    Button Adminlogout;
    Button Edituserbtn, editinfobtn;
    TextView email, mobile;
    EditText eemail, epassword, cepassword, emobileno;
    LinearLayout details, edit;
    private static Api apiService;
    AppSharedPreferences appSharedPreferences;
    HashMap<String, String> map;
    AdminLoginResponse a;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        Adminlogout = view.findViewById(R.id.btnadminlogout);

        email = view.findViewById(R.id.email);

        mobile = view.findViewById(R.id.mobile);
        editinfobtn = view.findViewById(R.id.editinfo);
        cepassword = view.findViewById(R.id.cepassword);
        eemail = view.findViewById(R.id.eemail);
        epassword = view.findViewById(R.id.epassword);
        emobileno = view.findViewById(R.id.emobileno);
        Edituserbtn = view.findViewById(R.id.editbtn);
        details = view.findViewById(R.id.userdatails);
        edit = view.findViewById(R.id.edituserdetails);

        apiService = ApiClient.getClient().create(Api.class);
        appSharedPreferences = new AppSharedPreferences(getContext());

        load_data();
        edit.setVisibility(View.GONE);

        editinfobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                details.setVisibility(View.GONE);
                edit.setVisibility(View.VISIBLE);

            }
        });


        Edituserbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_data();
            }
        });
        Adminlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppSharedPreferences appSharedPreferences = new AppSharedPreferences(getContext());
                appSharedPreferences.clearPref();
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });
        return view;
    }


    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void load_data() {
        Log.e("####", "sfs");
        map = appSharedPreferences.getPref();
        Call<AdminLoginResponse> call = apiService.get_Admin(Integer.parseInt(map.get("adminid")));
        call.enqueue(new Callback<AdminLoginResponse>() {
            @Override
            public void onResponse(Call<AdminLoginResponse> call, Response<AdminLoginResponse> response) {

                email.setText(response.body().getEmail());
                mobile.setText(response.body().getMobileno());

                Log.e("####", response.body().getEmail());
                eemail.setText(response.body().getEmail());
                emobileno.setText(response.body().getMobileno());
                epassword.setText(response.body().getPassword());

                a = response.body();
            }

            @Override
            public void onFailure(Call<AdminLoginResponse> call, Throwable t) {
                Log.e("####", t.getMessage());
            }
        });
    }


    public void update_data() {
        map = appSharedPreferences.getPref();
        if (emobileno.getText().length() == 0) {
            emobileno.setError("Mobile No is Required!");
        } else if (epassword.getText().length() == 0) {
            epassword.setError("Password is Required!");
        } else if (eemail.getText().length() == 0) {
            eemail.setError("Email is Required!");
        } else if (epassword.getText() != cepassword.getText()) {
            cepassword.setError("Confirm password is not same as password");
        } else {
            a.setMobileno(emobileno.getText().toString());

            a.setPassword(epassword.getText().toString());
            a.setEmail(eemail.getText().toString());

            Call<AdminLoginResponse> call = apiService.update_Admin(a.getAdminid(), a);
            call.enqueue(new Callback<AdminLoginResponse>() {
                @Override
                public void onResponse(Call<AdminLoginResponse> call, Response<AdminLoginResponse> response) {
                    ProfileFragment profileFragment = new ProfileFragment();
                    loadFragment(profileFragment);
                }

                @Override
                public void onFailure(Call<AdminLoginResponse> call, Throwable t) {

                }
            });
        }
    }
}

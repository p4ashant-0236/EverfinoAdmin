package com.everfino.everfinoadmin.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.everfino.everfinoadmin.ApiConnection.Api;
import com.everfino.everfinoadmin.ApiConnection.ApiClient;
import com.everfino.everfinoadmin.Model.RestList;
import com.everfino.everfinoadmin.Model.UserList;
import com.everfino.everfinoadmin.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditUserFragment extends Fragment {



    EditText name,password,mobileno,email,dob;
    RadioButton gender;
    RadioGroup genderGroup;
    Spinner status;
    String[] state = { "activate", "deactivate"};
    Button edituserbtn,cancelbtn;

    private static Api apiService;
    UserList u;
    public EditUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        SimpleDateFormat f = new SimpleDateFormat("dd-mm-yyyy");
        Date dobirth = new Date();
        try {
             dobirth = f.parse(getArguments().getString("dob"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        u=new UserList(Integer.parseInt(getArguments().getString("userid")),getArguments().getString("name"),getArguments().getString("password"),getArguments().getString("mobileno"),getArguments().getString("email"),dobirth,getArguments().getString("gender"),getArguments().getString("status"));
        Log.e("###########3",u.email);
        final View view= inflater.inflate(R.layout.fragment_edit_user, container, false);
        apiService= ApiClient.getClient().create(Api.class);

        name = view.findViewById(R.id.name);
        password = view.findViewById(R.id.password);
        mobileno = view.findViewById(R.id.mobileno);
        email = view.findViewById(R.id.email);
        dob = view.findViewById(R.id.dob);
        status = view.findViewById(R.id.status);
        genderGroup = view.findViewById(R.id.radioGender);

        name.setText(u.name);
        password.setText(u.password);
        mobileno.setText(u.mobileno);
        email.setText(u.email);
        dob.setText(u.dob+"");

        ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,state);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        status.setAdapter(aa);

        edituserbtn=view.findViewById(R.id.edituserbtn);
        cancelbtn=view.findViewById(R.id.ecancelbtn);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=new RestFragment();

                loadFragment(fragment);
            }
        });

        edituserbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                u.setName(name.getText().toString());
                u.setPassword(password.getText().toString());
                u.setMobileno(mobileno.getText().toString());
                u.setEmail(email.getText().toString());
                SimpleDateFormat f = new SimpleDateFormat("dd-mm-yyyy");
                Date dobirth = null;
                try {
                    dobirth = f.parse(dob.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

               u.setDob(dobirth);
                u.setStatus(status.getSelectedItem().toString());
                int id = genderGroup.getCheckedRadioButtonId();
                if(id!=-1) {

                    Log.e("$$", id + "   " + R.id.radioMale);
                    gender = view.findViewById(id);

                    u.setGender(gender.getText().toString());
                }else {
                    Toast.makeText(getActivity(), "Nothing Selected", Toast.LENGTH_SHORT).show();
                }
                Call<UserList> call=apiService.update_User(u.userid,u);
                call.enqueue(new Callback<UserList>() {

                    @Override
                    public void onResponse(Call<UserList> call, Response<UserList> response) {
                        Log.e("###","----------------------");

                        Toast.makeText(getContext(), "edited", Toast.LENGTH_SHORT).show();
                        Fragment fragment=new UserFragment();

                        loadFragment(fragment);
                    }

                    @Override
                    public void onFailure(Call<UserList> call, Throwable t) {
                        Toast.makeText(getContext(), t.toString()+"Try Again", Toast.LENGTH_SHORT).show();
                    }
                });
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
}


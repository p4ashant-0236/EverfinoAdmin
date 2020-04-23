package com.everfino.everfinoadmin.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.everfino.everfinoadmin.ApiConnection.Api;
import com.everfino.everfinoadmin.ApiConnection.ApiClient;
import com.everfino.everfinoadmin.Model.RestList;
import com.everfino.everfinoadmin.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditRestFragment extends Fragment {


    EditText restname, restdesc, mobileno, city, email, address;
    Button editrestbtn, cancelbtn;
    Spinner status;
    String[] state = {"Activate", "Deactivate"};
    private static Api apiService;
    RestList r;

    public EditRestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        r = new RestList(Integer.parseInt(getArguments().getString("restid")), getArguments().getString("mobileno"), getArguments().getString("email"), getArguments().getString("restname"), getArguments().getString("restdesc"), getArguments().getString("address"), getArguments().getString("city"), getArguments().getString("status"));

        View view = inflater.inflate(R.layout.fragment_edit_rest, container, false);
        apiService = ApiClient.getClient().create(Api.class);

        restname = view.findViewById(R.id.restname);
        restdesc = view.findViewById(R.id.restdesc);
        mobileno = view.findViewById(R.id.mobileno);
        city = view.findViewById(R.id.city);
        email = view.findViewById(R.id.email);
        status = view.findViewById(R.id.status);
        address = view.findViewById(R.id.address);

        ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, state);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        status.setAdapter(aa);

        restname.setText(r.getRestname());
        restdesc.setText(r.getRestdesc());
        mobileno.setText(r.getMobileno());
        city.setText(r.getCity());
        email.setText(r.getEmail());

        address.setText(r.getAddress());

        editrestbtn = view.findViewById(R.id.editrestbtn);
        cancelbtn = view.findViewById(R.id.ecancelbtn);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new RestFragment();

                loadFragment(fragment);
            }
        });

        editrestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (restname.getText().length() == 0) {
                    restname.setError("Restaurant Name is Required!");
                } else if (restdesc.getText().length() == 0) {
                    restdesc.setError("Rest Desc is Required!");
                } else if (mobileno.getText().length() == 0) {
                    mobileno.setError("Mobile No is Required!");
                } else if (city.getText().length() == 0) {
                    city.setError("City is Required!");
                } else if (email.getText().length() == 0) {
                    email.setError("Email is Required!");
                } else if (address.getText().length() == 0) {
                    address.setError("Address is Required!");
                } else {
                    r.setRestname(restname.getText().toString());
                    r.setRestdesc(restdesc.getText().toString());
                    r.setMobileno(mobileno.getText().toString());
                    r.setCity(city.getText().toString());
                    r.setEmail(email.getText().toString());
                    r.setStatus(status.getSelectedItem().toString());
                    r.setAddress(address.getText().toString());

                    Call<RestList> call = apiService.update_Rest(r.restid, r);
                    call.enqueue(new Callback<RestList>() {

                        @Override
                        public void onResponse(Call<RestList> call, Response<RestList> response) {
                            Log.e("###", "----------------------");

                            Toast.makeText(getContext(), "edited", Toast.LENGTH_SHORT).show();
                            Fragment fragment = new RestFragment();

                            loadFragment(fragment);
                        }

                        @Override
                        public void onFailure(Call<RestList> call, Throwable t) {
                            Toast.makeText(getContext(), t.toString() + "Try Again", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
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

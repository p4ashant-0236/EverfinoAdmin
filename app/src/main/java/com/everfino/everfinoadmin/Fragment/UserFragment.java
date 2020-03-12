package com.everfino.everfinoadmin.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.everfino.everfinoadmin.Adapter.RestAdapter;
import com.everfino.everfinoadmin.Adapter.UserAdapter;
import com.everfino.everfinoadmin.ApiConnection.Api;
import com.everfino.everfinoadmin.ApiConnection.ApiClient;
import com.everfino.everfinoadmin.Model.RestList;
import com.everfino.everfinoadmin.Model.UserList;
import com.everfino.everfinoadmin.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {


    RecyclerView rcv_user;
    EditText search;
    UserAdapter adapter;
    List<HashMap<String,String>> ls_user=new ArrayList<>();
    private static Api apiService;
    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view =  inflater.inflate(R.layout.fragment_user, container, false);
        rcv_user=view.findViewById(R.id.rcv_user);
        search=view.findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        apiService= ApiClient.getClient().create(Api.class);

        fetch_user();
        return view;
    }


    private void filter(String text) {

        List<HashMap<String,String>> ls=new ArrayList<>();

        for (HashMap<String,String> s : ls_user) {
                Log.e("abcccccc",s.toString());
            if (s.toString().toLowerCase().contains(text.toLowerCase())) {
                Log.e("true", String.valueOf(s));
                ls.add(s);
            }
        }

        adapter.filterList(ls);

    }

    private void fetch_user(){

        ls_user.clear();
        rcv_user.setLayoutManager(new GridLayoutManager(getContext(),1));

        Call<List<UserList>> call=apiService.get_User();
        call.enqueue(new Callback<List<UserList>>() {
            @Override
            public void onResponse(Call<List<UserList>> call, Response<List<UserList>> response) {
                for(UserList item: response.body()) {

                    HashMap<String,String> map=new HashMap<>();
                    map.put("userid",item.getUserid()+"");
                    map.put("name",item.getName());
                    map.put("password",item.getPassword());
                    map.put("mobileno",item.getMobileno());
                    map.put("email",item.getEmail());
                    map.put("dob",item.getDob()+"");
                    map.put("gender",item.getGender());
                    map.put("status",item.getStatus());


                    ls_user.add(map);
                }

                adapter=new UserAdapter(getContext(),ls_user);
                rcv_user.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<UserList>> call, Throwable t) {
                Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }
    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
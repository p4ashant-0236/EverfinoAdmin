package com.everfino.everfinoadmin.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    List<HashMap<String,String>> ls_menu=new ArrayList<>();
    private static Api apiService;
    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view =  inflater.inflate(R.layout.fragment_user, container, false);
        rcv_user=view.findViewById(R.id.rcv_user);

        apiService= ApiClient.getClient().create(Api.class);

        fetch_user();
        return view;
    }

    private void fetch_user(){

        ls_menu.clear();
        rcv_user.setLayoutManager(new GridLayoutManager(getContext(),1));

        Call<List<UserList>> call=apiService.get_User();
        call.enqueue(new Callback<List<UserList>>() {
            @Override
            public void onResponse(Call<List<UserList>> call, Response<List<UserList>> response) {
                Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();
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


                    ls_menu.add(map);
                }

                UserAdapter adapter=new UserAdapter(getContext(),ls_menu);
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
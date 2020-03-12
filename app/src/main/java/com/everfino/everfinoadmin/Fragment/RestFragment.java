package com.everfino.everfinoadmin.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.everfino.everfinoadmin.Adapter.RestAdapter;
import com.everfino.everfinoadmin.ApiConnection.Api;
import com.everfino.everfinoadmin.ApiConnection.ApiClient;
import com.everfino.everfinoadmin.Model.RestList;
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
public class RestFragment extends Fragment {



    RecyclerView rcv_rest;
    FloatingActionButton rest_add_btn;
    List<HashMap<String,String>> ls_menu=new ArrayList<>();
    private static Api apiService;
    public RestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view =  inflater.inflate(R.layout.fragment_rest, container, false);
        rcv_rest=view.findViewById(R.id.rcv_rest);

        apiService= ApiClient.getClient().create(Api.class);

        fetch_rest();
        return view;
    }

    private void fetch_rest(){

        ls_menu.clear();
        rcv_rest.setLayoutManager(new GridLayoutManager(getContext(),1));

        Call<List<RestList>> call=apiService.get_Rest();
        call.enqueue(new Callback<List<RestList>>() {
            @Override
            public void onResponse(Call<List<RestList>> call, Response<List<RestList>> response) {
                 for(RestList item: response.body()) {

                    HashMap<String,String> map=new HashMap<>();
                     map.put("restid",item.getRestid()+"");
                    map.put("mobileno",item.getMobileno());
                            map.put("email",item.getEmail());
                                    map.put("restname",item.getRestname());
                                            map.put("restdesc",item.getRestdesc());
                                                    map.put("address",item.getAddress());
                                                            map.put("city",item.getCity());
                                                                    map.put("status",item.getStatus());
                    ls_menu.add(map);
                }

                RestAdapter adapter=new RestAdapter(getContext(),ls_menu);
                rcv_rest.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<RestList>> call, Throwable t) {
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

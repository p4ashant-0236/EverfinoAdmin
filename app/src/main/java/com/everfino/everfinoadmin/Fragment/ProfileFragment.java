package com.everfino.everfinoadmin.Fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.everfino.everfinoadmin.AppSharedPreferences;
import com.everfino.everfinoadmin.LoginActivity;
import com.everfino.everfinoadmin.MainActivity;
import com.everfino.everfinoadmin.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    Button Adminlogout;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_profile, container, false);
        Adminlogout=view.findViewById(R.id.btnadminlogout);
        Adminlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppSharedPreferences appSharedPreferences=new AppSharedPreferences(getContext());
                appSharedPreferences.clearPref();
                Intent i=new Intent(getContext(), LoginActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });
        return  view;
    }

}

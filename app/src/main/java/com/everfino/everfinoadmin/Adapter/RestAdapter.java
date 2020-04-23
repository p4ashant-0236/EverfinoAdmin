package com.everfino.everfinoadmin.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import com.everfino.everfinoadmin.ApiConnection.Api;
import com.everfino.everfinoadmin.ApiConnection.ApiClient;
import com.everfino.everfinoadmin.Fragment.EditRestFragment;
import com.everfino.everfinoadmin.Fragment.RestFragment;
import com.everfino.everfinoadmin.Model.RestList;
import com.everfino.everfinoadmin.R;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RestAdapter extends RecyclerView.Adapter<RestAdapter.Viewholder> {

    Context context;
    List<HashMap<String,String>> ls;
    HashMap<String, String> map;


    public RestAdapter(Context context, List<HashMap<String,String>> ls) {
        this.context=context;
        this.ls=ls;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.restlist_design, null);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        map=ls.get(position);
        if(map.get("status").equals("Activate")){
            holder.side_bar.setBackgroundColor(Color.parseColor("#008900"));
        }else {
            holder.side_bar.setBackgroundColor(Color.parseColor("#FF0000"));
        }
        holder.txt_restname.setText(map.get("restname"));
        holder.txt_restdesc.setText(map.get("restdesc"));
        holder.txt_restemail.setText(map.get("email"));
        holder.txt_restmobile.setText(map.get("mobileno"));

        holder.txt_restcity.setText(map.get("city"));

    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView txt_restname,txt_restdesc,txt_restcity,txt_restemail,txt_restmobile;
        private Api apiService;
        LinearLayout side_bar;


        public Viewholder(@NonNull final View itemView) {
            super(itemView);
            apiService= ApiClient.getClient().create(Api.class);
            txt_restname=itemView.findViewById(R.id.txt_restname);
            txt_restdesc=itemView.findViewById(R.id.txt_restdesc);
            txt_restcity=itemView.findViewById(R.id.txt_restcity);
            txt_restmobile=itemView.findViewById(R.id.txt_restmobile);
            txt_restemail=itemView.findViewById(R.id.txt_restemail);
            side_bar=itemView.findViewById(R.id.side_bar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Fragment fragment=new EditRestFragment();
                    Bundle b=new Bundle();

                    b.putString("restid",ls.get(getAdapterPosition()).get("restid"));
                    b.putString("mobileno",ls.get(getAdapterPosition()).get("mobileno"));
                    b.putString("email",ls.get(getAdapterPosition()).get("email"));
                    b.putString("restname",ls.get(getAdapterPosition()).get("restname"));
                    b.putString("restdesc",ls.get(getAdapterPosition()).get("restdesc"));
                    b.putString("address",ls.get(getAdapterPosition()).get("address"));
                    b.putString("city",ls.get(getAdapterPosition()).get("city"));
                    b.putString("status",ls.get(getAdapterPosition()).get("status"));
                    fragment.setArguments(b);

                    loadFragment(fragment,itemView);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(context, "long press"+getAdapterPosition(), Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder al=new AlertDialog.Builder(v.getContext());
                    al.setMessage("Do you want to delete");
                    al.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Call<RestList> call=apiService.delete_Rest(Integer.parseInt(ls.get(getAdapterPosition()).get("restid")));
                            call.enqueue(new Callback<RestList>() {
                                @Override
                                public void onResponse(Call<RestList> call, Response<RestList> response) {
                                    Toast.makeText(itemView.getContext(), "deleted", Toast.LENGTH_SHORT).show();
                                    Fragment fragment=new RestFragment();
                                    loadFragment(fragment,itemView);
                                }

                                @Override
                                public void onFailure(Call<RestList> call, Throwable t) {

                                }
                            });
                        }
                    });
                    al.setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Fragment fragment=new RestFragment();
                            loadFragment(fragment,itemView);
                        }
                    });

                    AlertDialog a=al.create();
                    a.show();
                    return false;
                }
            });
        }

        public void loadFragment(Fragment fragment,View v) {
            AppCompatActivity activity=(AppCompatActivity) v.getContext();
            FragmentTransaction transaction =activity.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }


    }
    public void filterList(List<HashMap<String,String>> ls)
    {
        this.ls=ls;
        notifyDataSetChanged();
    }

}

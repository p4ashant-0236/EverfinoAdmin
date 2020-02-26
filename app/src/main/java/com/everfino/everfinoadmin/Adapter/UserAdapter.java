package com.everfino.everfinoadmin.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.everfino.everfinoadmin.Fragment.EditUserFragment;
import com.everfino.everfinoadmin.Fragment.RestFragment;
import com.everfino.everfinoadmin.Fragment.UserFragment;
import com.everfino.everfinoadmin.Model.RestList;
import com.everfino.everfinoadmin.Model.UserList;
import com.everfino.everfinoadmin.R;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.Viewholder> {
    Context context;
    List<HashMap<String,String>> ls;
    HashMap<String, String> map;


    public UserAdapter(Context context, List<HashMap<String,String>> ls) {
        this.context=context;
        this.ls=ls;
    }

    @NonNull
    @Override
    public UserAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.restlist_design, null);

        return new UserAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.Viewholder holder, int position) {
        map=ls.get(position);
        holder.txtdemo.setText(map.get("name"));

    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView txtdemo;
        private Api apiService;


        public Viewholder(@NonNull final View itemView) {
            super(itemView);
            apiService= ApiClient.getClient().create(Api.class);
            txtdemo=itemView.findViewById(R.id.txtdemo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Fragment fragment=new EditUserFragment();
                    Bundle b=new Bundle();

                    b.putString("userid",ls.get(getAdapterPosition()).get("userid"));
                    b.putString("name",ls.get(getAdapterPosition()).get("name"));
                    b.putString("password",ls.get(getAdapterPosition()).get("password"));
                    b.putString("mobileno",ls.get(getAdapterPosition()).get("mobileno"));
                    b.putString("email",ls.get(getAdapterPosition()).get("email"));
                    b.putString("dob",ls.get(getAdapterPosition()).get("dob"));
                    b.putString("gender",ls.get(getAdapterPosition()).get("gender"));
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
                            Call<UserList> call=apiService.delete_User(Integer.parseInt(ls.get(getAdapterPosition()).get("userid")));
                            call.enqueue(new Callback<UserList>() {
                                @Override
                                public void onResponse(Call<UserList> call, Response<UserList> response) {
                                    Toast.makeText(itemView.getContext(), "deleted", Toast.LENGTH_SHORT).show();
                                    Fragment fragment=new UserFragment();
                                    loadFragment(fragment,itemView);
                                }

                                @Override
                                public void onFailure(Call<UserList> call, Throwable t) {

                                }
                            });
                        }
                    });
                    al.setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Fragment fragment=new UserFragment();
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


}



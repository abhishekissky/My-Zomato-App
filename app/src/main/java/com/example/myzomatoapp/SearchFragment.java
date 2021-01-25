package com.example.myzomatoapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SearchFragment extends Fragment {
    RecyclerView recyclerView1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView1 = view.findViewById(R.id.recycler_view_fragment);
        String query = getArguments().getString("query");
        Log.v("Query",query);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://developers.zomato.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ZomatoApiInterface zomatoApiInterface = retrofit.create(ZomatoApiInterface.class);
        zomatoApiInterface.getSearchData(query).enqueue(new Callback<Zomato>() {
            @Override
            public void onResponse(Call<Zomato> call, Response<Zomato> response) {
                Zomato zomato = response.body();
                List<Restaurants> list = new ArrayList<>(Arrays.asList(zomato.getRestaurants()));
                for (Restaurants restaurants:list){
                    Log.v("SearchView",restaurants.toString());
                    setData(list);
                }
            }

            @Override
            public void onFailure(Call<Zomato> call, Throwable t) {

            }
        });

        return view;
    }

    private void setData(List<Restaurants> list) {
        Adapter adapter = new Adapter(getContext(),list);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView1.setAdapter(adapter);
    }
}
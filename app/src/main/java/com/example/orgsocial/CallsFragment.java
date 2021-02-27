package com.example.orgsocial;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.orgsocial.adapters.callListAdapter;
import com.example.orgsocial.models.CallList;

import java.util.ArrayList;
import java.util.List;


public class CallsFragment extends Fragment {
    List<CallList> calls = new ArrayList<>();
    public CallsFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calls, container, false);
        RecyclerView recycle = view.findViewById(R.id.calls_recycler);
        addCalls();
        callListAdapter adapter = new callListAdapter(calls,getContext());
        recycle.setAdapter(adapter);
        recycle.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
  //CallList(String userID, String userName, String callDate, String userProfileUrl, String callType)

    public void addCalls(){
       // calls.clear();
        //calls.add(new CallList("2","Natasha","02/05/2020",null,"Sent"));
        //calls.add(new CallList("1","Madalitso","17/08/2020","https://z-p3-scontent.flun3-1.fna.fbcdn.net/v/t1.0-9/112479472_2955646284557810_8615307189397659936_o.jpg?_nc_cat=104&_nc_sid=09cbfe&_nc_ohc=7MGGMCQsCvYAX8NqMCK&_nc_ht=z-p3-scontent.flun3-1.fna&oh=c74f8f98a4b70fb9ea522d3da61f0a98&oe=5F639220","Recieved"));

    }
}
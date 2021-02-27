package com.example.orgsocial;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.orgsocial.adapters.chatListAdapter;
import com.example.orgsocial.databinding.FragmentChatsBinding;
import com.example.orgsocial.models.ChatList;
import com.example.orgsocial.models.Chats;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Timer;


public class ChatsFragment extends Fragment {
    List<ChatList> list = new ArrayList<>();
    List<Chats> list1 = new ArrayList<>();
    List<Chats> list2 = new ArrayList<>();
    List<ChatList> list3 = new ArrayList<>();
    List<Chats> full = new ArrayList<>();

    ArrayList<String> usersId = new ArrayList<>();
    private FragmentChatsBinding binding;
    private FirebaseUser user;
    private DatabaseReference ref;
    private FirebaseFirestore firestore;
    private chatListAdapter adapter;
    private ValueEventListener chatListener;
    private ValueEventListener chatListener1;
    Thread getData;
    private Boolean datachecked;
    public FloatingActionButton FAB;
    private Bundle savedInstance;
    private Long ChatDataSize;
    private Boolean Callable;

    public ChatsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_chats, container, false);
        Callable = true;
        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference();
        firestore = FirebaseFirestore.getInstance();
        savedInstance = savedInstanceState;
        FAB.setVisibility(View.GONE);
        datachecked = false;
        readChats();



        chatListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long dataSize = dataSnapshot.getChildrenCount();


                    if(isNetworkAvailable(getContext())){
                        FAB.setVisibility(View.VISIBLE);
                        binding.networkErrorLayout.setVisibility(View.GONE);
                        binding.chatRecycler.setVisibility(View.VISIBLE);
                        if (dataSnapshot.exists()) {

                            if(dataSize != ChatDataSize){
                                usersId.clear();
                                for (DataSnapshot current : dataSnapshot.getChildren()) {
                                    String userID = current.getKey();
                                    usersId.add(userID);
                                    Log.d("ChatsFragment", "getUserData: " + usersId.size());

                                }
                                ChatDataSize = dataSize;
                            }

                        // getUserData();

                        } else {
                            binding.chatsListProgress.setVisibility(View.GONE);
                            binding.noChatMessage.setVisibility(View.VISIBLE);
                            binding.addChats.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(getContext(), ContactsActivity.class));
                                }
                            });
                        }
                    }else{
                        binding.chatsListProgress.setVisibility(View.GONE);
                        FAB.setVisibility(View.GONE);
                        binding.networkErrorLayout.setVisibility(View.VISIBLE);
                        binding.chatRecycler.setVisibility(View.GONE);
                    }





        }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
            chatListener1 =new ValueEventListener() {


                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list1.clear();
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        list2.clear();
                        Chats chats ;
                        for(DataSnapshot snapshot1:snapshot.getChildren()){
                            chats = snapshot1.getValue(Chats.class);
                            list2.add(chats);

                        }
                        list1.add(list2.get(list2.size()-1));
                    }

                    getUserData();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };



        load();


        adapter = new chatListAdapter(list,getContext());
        binding.chatRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load();
            }
        });
        binding.chatRecycler.setAdapter(adapter);
        return binding.getRoot();
    }

    private void load() {
        binding.networkErrorLayout.setVisibility(View.GONE);
        if(savedInstance != null){
            binding.chatsListProgress.setVisibility(View.GONE);
        }else{
            binding.chatsListProgress.setVisibility(View.VISIBLE);
        }
        if(isNetworkAvailable(getContext())){
            FAB.setVisibility(View.VISIBLE);
            getChatList();

        }else{
            binding.chatsListProgress.setVisibility(View.GONE);
            binding.networkErrorLayout.setVisibility(View.VISIBLE);
        }

    }

    public void getChatList(){
        binding.chatsListProgress.setVisibility(View.VISIBLE);

        ref.child("Chats").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(chatListener);

    }

    public synchronized void  getUserData() {
           /* if (savedInstance == null) {
                readChats();
            }*/

            list.clear();
            int index = 0;
            Log.d("ChatsFrag", "getUserData: " + "UserId :" + usersId.size());
            for (String userID : usersId) {
                final int i = index;
                firestore.collection("Users").document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Date date = new Date(Long.parseLong(list1.get(i).getDateTime().toString()));
                        Date dateJoined = documentSnapshot.getDate("dateJoined");
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                        String joined = formatter.format(dateJoined);
                        String about = documentSnapshot.getString("description");
                        String Phone = documentSnapshot.getString("userPhone");
                        list.add(new ChatList(documentSnapshot.getString("userId"), documentSnapshot.getString("userName"), list1.get(i).getTextMessage(), date, documentSnapshot.getString("userPhotoUrl"), documentSnapshot.getString("userEmail"), about, joined, Phone));
                        Collections.sort(list, Collections.reverseOrder());


                        if (adapter != null) {
                            adapter.notifyDataSetChanged();
                        }
                        binding.noChatMessage.setVisibility(View.GONE);
                        binding.chatsListProgress.setVisibility(View.GONE);


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        binding.chatsListProgress.setVisibility(View.GONE);
                        e.printStackTrace();
                        if (e instanceof FirebaseNetworkException) {
                            binding.networkErrorLayout.setVisibility(View.VISIBLE);
                            binding.chatRecycler.setVisibility(View.GONE);
                            FAB.setVisibility(View.GONE);
                        } else if (e instanceof FirebaseFirestoreException) {
                            binding.networkErrorLayout.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }


                    }
                });
                index++;
                Log.d("ChatsFragment", "getUserData: " + usersId.size());
            }


    }
    private void readChats() {
        try{

            ref.child("Chats").child(user.getUid()).addValueEventListener(chatListener1);
        }catch (Exception e){
            if(e instanceof FirebaseNetworkException){
                Toast.makeText(getContext(),"failed to load please check your network",Toast.LENGTH_LONG).show();
            }else{
                Log.d("ChatsActivity", "readChats: "+ e.getMessage());
            }
        }

    }

    @Override
    public void onResume() {
        ref.child("Chats").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(chatListener);
        ref.child("Chats").child(user.getUid()).addValueEventListener(chatListener1);
        super.onResume();

    }

    @Override
    public void onPause() {
        if(chatListener != null && chatListener1 != null && ref != null){
            ref.child("Chats").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeEventListener(chatListener);
            ref.child("Chats").child(user.getUid()).removeEventListener(chatListener1);



        }
        super.onPause();
    }

    @Override
    public void onStop() {
        if(chatListener != null && chatListener1 != null && ref != null){
            ref.child("Chats").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeEventListener(chatListener);
            ref.child("Chats").child(user.getUid()).removeEventListener(chatListener1);
        }
        super.onStop();
    }

    public static boolean isNetworkAvailable(Context con) {
        try {
            ConnectivityManager cm = (ConnectivityManager) con
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
               return true;


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}

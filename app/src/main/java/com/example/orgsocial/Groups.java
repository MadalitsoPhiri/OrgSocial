package com.example.orgsocial;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.orgsocial.adapters.ChatsAdapter;
import com.example.orgsocial.adapters.chatListAdapter;
import com.example.orgsocial.adapters.groupChatListAdapter;
import com.example.orgsocial.databinding.FragmentChatsBinding;
import com.example.orgsocial.databinding.FragmentGroupsBinding;
import com.example.orgsocial.models.ChatList;
import com.example.orgsocial.models.Chats;
import com.example.orgsocial.models.Group;
import com.example.orgsocial.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.example.orgsocial.ChatsFragment.isNetworkAvailable;


public class Groups extends Fragment {
    List<ChatList> list = new ArrayList<>();
    List<Chats> list1 = new ArrayList<>();
    List<Chats> list2 = new ArrayList<>();
    List<ChatList> list3 = new ArrayList<>();

    ArrayList<String> groupsId = new ArrayList<>();
    private FragmentGroupsBinding binding;
    private FirebaseUser user;
    private DatabaseReference ref;
    private FirebaseFirestore firestore;
    public FloatingActionButton FAB;
    private groupChatListAdapter adapter;
    private ValueEventListener chatListener,chatListener1,chatListener2;
    Bundle savedInstance;
    private Boolean dataChecked;
    private Boolean resumed;
    int counter = 0;
    public Groups() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_groups, container, false);

        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference();

        savedInstance = savedInstanceState;
        dataChecked = false;


        FAB.setVisibility(View.GONE);
        firestore = FirebaseFirestore.getInstance();




        adapter = new groupChatListAdapter(list,getContext());
        binding.chatRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        readChats();
        chatListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupsId.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot current: dataSnapshot.getChildren()){
                        String groupId = current.getKey();
                        groupsId.add(groupId);

                    }
                }else{
                    binding.chatsListProgress.setVisibility(View.GONE);
                    binding.noChatMessage.setVisibility(View.VISIBLE);
                    binding.addChats.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(getContext(),ParticipantsActivity.class));
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        chatListener1 = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list1.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    list2.clear();
                    Chats chats;
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        chats = snapshot1.getValue(Chats.class);
                        list2.add(chats);
                    }
                    list1.add(list2.get(list2.size() - 1));
                }

                getData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        chatListener2 = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                      if(isNetworkAvailable(getContext())){
                          binding.networkErrorLayout.setVisibility(View.GONE);

                          binding.chatRecycler.setVisibility(View.VISIBLE);







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
        load();
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
            getGroupList();
            groupChatsListener();
        }else{
            binding.chatsListProgress.setVisibility(View.GONE);
            binding.networkErrorLayout.setVisibility(View.VISIBLE);
        }
    }

    public void getGroupList(){
        binding.chatsListProgress.setVisibility(View.VISIBLE);
        ref.child("GroupList").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(chatListener);

    }

    public synchronized void getData() {

        /*if(savedInstance == null){
            readChats();
        }*/

        list.clear();
        int index = 0;

            for (String groupID : groupsId) {
                final int i = index;
                firestore.collection("Groups").document(groupID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Date date = new Date(Long.parseLong(list1.get(i).getDateTime().toString()));
                        String DESC;
                        if (list1.get(i).getSender().equals("orgsocial@info.com") || list1.get(i).getSender().equals(user.getEmail())) {
                            DESC = list1.get(i).getTextMessage();
                        } else {
                            DESC = list1.get(i).getSender() + ": " + list1.get(i).getTextMessage();
                        }

                        list.add(new ChatList(documentSnapshot.getString("groupId"), documentSnapshot.getString("groupSubject"), DESC, date, documentSnapshot.getString("groupPhotoUrl"), "orgsocial@info.com","","",""));

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
                        e.printStackTrace();
                        binding.chatsListProgress.setVisibility(View.GONE);
                        if (e instanceof FirebaseFirestoreException) {
                            binding.networkErrorLayout.setVisibility(View.VISIBLE);
                        } else if (e instanceof FirebaseNetworkException) {
                            binding.networkErrorLayout.setVisibility(View.VISIBLE);
                        } else {
                            Log.d("Groups", "onFailure: " + e.getMessage());
                        }
                    }
                });
                index++;
                Log.d("ChatsFragment", "getUserData: " + groupsId.size());
            }


    }
    private void groupChatsListener(){
        ref.child("GroupChats").addValueEventListener(chatListener2);
    }
    private void readChats() {
        try {

            ref.child("GroupChats").addValueEventListener(chatListener1);

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
        ref.child("GroupList").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(chatListener);
        ref.child("GroupChats").addValueEventListener(chatListener1);
        ref.child("GroupChats").addValueEventListener(chatListener2);



        super.onResume();

    }



    @Override
    public void onPause() {
        if(chatListener != null && chatListener1 != null && chatListener2 != null && ref != null){
            ref.child("GroupList").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeEventListener(chatListener);
            ref.child("GroupChats").removeEventListener(chatListener1);
            ref.child("GroupChats").removeEventListener(chatListener2);

        }

        super.onPause();
    }

    @Override
    public void onStop() {
        if(chatListener != null && chatListener1 != null && chatListener2 != null && ref != null){
            ref.child("GroupList").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeEventListener(chatListener);
            ref.child("GroupChats").removeEventListener(chatListener1);
            ref.child("GroupChats").removeEventListener(chatListener2);
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
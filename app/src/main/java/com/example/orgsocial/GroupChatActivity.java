package com.example.orgsocial;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.orgsocial.adapters.ChatsAdapter;
import com.example.orgsocial.databinding.ActivityGroupChatBinding;
import com.example.orgsocial.databinding.ActivityGroupSubjectBinding;
import com.example.orgsocial.models.Chats;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class GroupChatActivity extends AppCompatActivity {
    ActivityGroupChatBinding binding;
    FirebaseFirestore firestore;
    FirebaseAuth mAuth;
    DatabaseReference myRef ;
    ChatsAdapter adapter;
    List<Chats> list;
    String groupId;
    String groupSubject;
    String groupPhotoUrl;
    String userEmail;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_group_chat);
        Intent intent = getIntent();
        myRef = FirebaseDatabase.getInstance().getReference();
          groupId = intent.getStringExtra("groupId");
        groupSubject = intent.getStringExtra("subject");
        groupPhotoUrl = intent.getStringExtra("photoUrl");
       userEmail = "Orgsocial@info.com";

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL,true);
        manager.setStackFromEnd(true);
        binding.chatRecycler.setLayoutManager(manager);

        readChats(groupId);

        binding.chatScreenBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.userName.setText(groupSubject);
        binding.lastSeen.setText("tap for group info");
        if(groupId != null){
            if(!groupPhotoUrl.equals("")) {

                Glide.with(getApplicationContext()).load(groupPhotoUrl).listener(new RequestListener<Drawable>() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        binding.profileProgress.setVisibility(View.GONE);
                        Drawable dr = getDrawable(R.drawable.ic_group_9);
                        binding.userProfile.setImageDrawable(dr);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        binding.profileProgress.setVisibility(View.GONE);
                        return false;
                    }
                }).into(binding.userProfile);
            }else{
                binding.profileProgress.setVisibility(View.GONE);
                Drawable dr = getDrawable(R.drawable.ic_group_9);
                binding.userProfile.setImageDrawable(dr);
            }
        }

        binding.messsageField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(TextUtils.isEmpty(binding.messsageField.getText().toString())){
                    binding.chatScreenFab.setImageDrawable(getDrawable(R.drawable.ic_baseline_keyboard_voice_24));
                    binding.chatScreenFab.setOnClickListener(null);
                    binding.chatScreenFab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getApplicationContext(),"Mic tapped", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    binding.chatScreenFab.setImageDrawable(getDrawable(R.drawable.ic_baseline_send_24));
                    binding.chatScreenFab.setOnClickListener(null);
                    binding.chatScreenFab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            sendTextMessage(binding.messsageField.getText().toString(),userEmail);
                            binding.messsageField.setText("");
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }


        });
        binding.attachFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"attachment clicked",Toast.LENGTH_LONG).show();
            }
        });
        binding.cameraPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"camera clicked",Toast.LENGTH_LONG).show();
            }
        });
        binding.emojis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"emojis clicked",Toast.LENGTH_LONG).show();
            }
        });
        binding.chatScreenFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"send button clicked",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void readChats(String userId) {
        try{
            myRef.child("GroupChats").child(groupId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list.clear();
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Chats chats = snapshot.getValue(Chats.class);
                        list.add(chats);
                    }
                    Collections.reverse(list);
                    if(adapter != null){
                        adapter.notifyDataSetChanged();

                    }else{
                        adapter = new ChatsAdapter(list,GroupChatActivity.this);
                        binding.chatRecycler.setAdapter(adapter);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch (Exception e){
            if(e instanceof FirebaseNetworkException){
                Toast.makeText(getApplicationContext(),"failed to load please check your network",Toast.LENGTH_LONG).show();
            }else{
                Log.d("ChatsActivity", "readChats: "+ e.getMessage());
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void sendTextMessage(String toString, String userEmail) {
        final Chats chat = new Chats(
                toString,
                "TEXT",
                Objects.requireNonNull(mAuth.getCurrentUser()).getEmail(),userEmail);
        myRef.child("GroupChats").child(groupId).push().setValue(chat).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });



    }
}
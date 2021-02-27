package com.example.orgsocial;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ActivityOptions;
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
import com.example.orgsocial.databinding.ActivityChatsBinding;
import com.example.orgsocial.models.Chats;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ChatsActivity extends AppCompatActivity {
    ActivityChatsBinding binding;
    FirebaseFirestore firestore;
    FirebaseAuth mAuth;
    DatabaseReference myRef ;
    ChatsAdapter adapter;
    List<Chats> list;
     String userName ;
     String userId ;
    String LastSeen ;
    String userPhotoUrl;
     String userEmail ;
     String dateJoined ;
     String phone ;
     String about;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_chats);
        Intent intent = getIntent();
        myRef = FirebaseDatabase.getInstance().getReference();
         userName = intent.getStringExtra("userName");
         userId = intent.getStringExtra("userId");
         LastSeen = intent.getStringExtra("date");
         userPhotoUrl = intent.getStringExtra("userPhotoUrl");
         userEmail = intent.getStringExtra("userEmail");
         dateJoined = intent.getStringExtra("joined");
         phone = intent.getStringExtra("phone");
         about = intent.getStringExtra("about");
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this,RecyclerView.VERTICAL,true);
        manager.setStackFromEnd(true);
        binding.chatRecycler.setLayoutManager(manager);

        readChats(userId);

        binding.chatScreenBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.userName.setText(userName);
        binding.lastSeen.setText(LastSeen);
        if(userId != null){
            if(!userPhotoUrl.equals("")) {

                Glide.with(getApplicationContext()).load(userPhotoUrl).listener(new RequestListener<Drawable>() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        binding.profileProgress.setVisibility(View.GONE);
                        return false;
                    }
                }).into(binding.userProfile);
                binding.userProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(new Intent(ChatsActivity.this, ChatProfileActivity.class));
                        i.putExtra("userId",userId);
                        i.putExtra("userPhotoUrl",userPhotoUrl);
                        i.putExtra("userName",userName);
                        i.putExtra("chatType","chat");
                        i.putExtra("dateJoined",dateJoined);
                        i.putExtra("date",LastSeen);
                        i.putExtra("phone",phone);
                        i.putExtra("userEmail",userEmail);
                        i.putExtra("about",about);



                        ActivityOptions options = ActivityOptions
                                .makeSceneTransitionAnimation(ChatsActivity.this, v, "profImg");
                        startActivity(i, options.toBundle());
                        finish();
                    }
                });
            }else{
                binding.profileProgress.setVisibility(View.GONE);
                Drawable dr = getDrawable(R.drawable.ic_group_13);
                binding.userProfile.setImageDrawable(dr);
                binding.userProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(new Intent(ChatsActivity.this, ChatProfileActivity.class));
                        i.putExtra("userId",userId);
                        i.putExtra("userPhotoUrl",userPhotoUrl);
                        i.putExtra("userName",userName);
                        i.putExtra("chatType","chat");
                        i.putExtra("dateJoined",dateJoined);
                        i.putExtra("date",LastSeen);
                        i.putExtra("phone",phone);
                        i.putExtra("userEmail",userEmail);
                        i.putExtra("about",about);



                        ActivityOptions options = ActivityOptions
                                .makeSceneTransitionAnimation(ChatsActivity.this, v, "profImg");
                        startActivity(i, options.toBundle());
                        finish();
                    }
                });
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
                            Toast.makeText(getApplicationContext(),"Mic tapped",Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    binding.chatScreenFab.setImageDrawable(getDrawable(R.drawable.ic_baseline_send_24));
                    binding.chatScreenFab.setOnClickListener(null);
                    binding.chatScreenFab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            sendTextMessage(binding.messsageField.getText().toString(),userId,userEmail);
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
            myRef.child("Chats").child(mAuth.getCurrentUser().getUid()).child(userId).addValueEventListener(new ValueEventListener() {
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
                        adapter = new ChatsAdapter(list,ChatsActivity.this);
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
    private void sendTextMessage(String toString, final String userId, String userEmail) {
      /*  //get and format date
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String today = formatter.format(date);
        //get and format Time
        SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm");
        String currentTime = formatter1.format(date);*/
        final Chats chat = new Chats(
                toString,
                "TEXT",
                Objects.requireNonNull(mAuth.getCurrentUser()).getEmail(),userEmail);
        myRef.child("Chats").child(mAuth.getCurrentUser().getUid()).child(userId).push().setValue(chat).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                  myRef.child("Chats").child(userId).child(mAuth.getCurrentUser().getUid()).push().setValue(chat).addOnSuccessListener(new OnSuccessListener<Void>() {
                      @Override
                      public void onSuccess(Void aVoid) {

                      }
                  }).addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {
                          e.printStackTrace();
                      }
                  });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });



    }


}
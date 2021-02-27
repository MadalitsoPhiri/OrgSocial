package com.example.orgsocial;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.orgsocial.adapters.GroupParticipantsAdapter;
import com.example.orgsocial.databinding.ActivityGroupProfileBinding;
import com.example.orgsocial.models.Common;
import com.example.orgsocial.models.GroupUser;
import com.google.android.gms.common.util.DataUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class GroupProfileActivity extends AppCompatActivity {
    ActivityGroupProfileBinding binding;
    String groupPhoto,groupName,groupId,Subject;
    List<GroupUser>list = new ArrayList<>();
    List<GroupUser>list1 = new ArrayList<>();
    FirebaseUser currentuser;
    Bundle savedInstance;
    Boolean isAdmin;
    Boolean dataChecked;
    FirebaseFirestore store;
    GroupParticipantsAdapter Adapter;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_group_profile);
        Intent intent = getIntent();
        groupPhoto = intent.getStringExtra("userPhotoUrl");
        groupName = intent.getStringExtra("userName");
        groupId = intent.getStringExtra("groupId");
        isAdmin = false;
        savedInstance = savedInstanceState;
        currentuser = FirebaseAuth.getInstance().getCurrentUser();
        Adapter = new GroupParticipantsAdapter(list,this,currentuser.getUid());
        binding.participantsRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.participantsRecycler.setAdapter(Adapter);
        store = FirebaseFirestore.getInstance();
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        dataChecked = false;
        binding.addParticipants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // public Group(String groupPhotoUrl, String groupId, String groupSubject, String creator,String Description
              startActivity(new Intent(GroupProfileActivity.this,addParticipants.class).putExtra("participants",(Serializable)list).putExtra("groupId",groupId).putExtra("groupPhotoUrl",groupPhoto).putExtra("subject",groupName));
            }
        });
        binding.groupSettingsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GroupProfileActivity.this,GroupSettings.class).putExtra("final",(Serializable) list).putExtra("groupId",groupId));
            }
        });
        binding.editGroupSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(GroupProfileActivity.this,EditGroupSubject.class).putExtra("groupId",groupId).putExtra("subject",Subject));
            }
        });
        if(groupPhoto.equals("")){
            binding.profileImage.setImageDrawable(getDrawable(R.drawable.ic_group_14));
            binding.progress.setVisibility(View.GONE);
            binding.profileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(GroupProfileActivity.this,binding.profileImage,"profImg");
                   startActivity(new Intent(GroupProfileActivity.this,ViewImageActivity.class).putExtra("photoUrl",groupPhoto).putExtra("PhotoType","group"),compat.toBundle());
                }
            });
        }else{
            Glide.with(this).load(groupPhoto).addListener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    binding.progress.setVisibility(View.GONE);
                    binding.profileImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Drawable dr = binding.profileImage.getDrawable();
                            Common.IMAGE_BITMAP = ((BitmapDrawable)dr.getCurrent()).getBitmap();
                            ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(GroupProfileActivity.this,binding.profileImage,"profImg");
                            startActivity(new Intent(GroupProfileActivity.this,ViewImageActivity.class).putExtra("photoUrl",groupPhoto).putExtra("PhotoType","group"),compat.toBundle());
                        }
                    });
                    return false;
                }
            }).into(binding.profileImage);

        }
        binding.username.setText(groupName);
         getGroupInfo();
    }
   public synchronized void getGroupInfo(){
        list.clear();
        list1.clear();
       store.collection("Groups").document(groupId).collection("Participants").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
           @Override
           public void onComplete(@NonNull Task<QuerySnapshot> task) {
               if (task.isSuccessful()) {
                   List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                   for(DocumentSnapshot current : myListOfDocuments){
                       String userId = current.getString("userId");
                       String name = current.get("userName").toString();
                       Boolean admin = current.getBoolean("admin");
                       Boolean creator = current.getBoolean("creator");
                       String Description = current.get("description").toString();
                       String Email = current.getString("userEmail");
                       String phone = current.getString("userPhone");
                       String photo = current.getString("userPhotoUrl");

                       GroupUser currentUser = new GroupUser(userId,name,Email,phone,photo,Description,admin,creator);
                       if(userId.equals(currentuser.getUid())){
                           list.add(currentUser);
                       }else{
                           list1.add(currentUser);
                       }

                   }
                   list.addAll(list1);
                   if(list.get(0).getAdmin()){
                       isAdmin = true;
                       binding.addParticipantsIcon.setVisibility(View.VISIBLE);
                       binding.editGroupSubject.setVisibility(View.VISIBLE);
                       binding.groupSettingsLayout.setVisibility(View.VISIBLE);
                       binding.addParticipants.setVisibility(View.VISIBLE);
                   }
                   getGroupDetails();
                   binding.progressBar.setVisibility(View.GONE);
                   binding.created.setVisibility(View.VISIBLE);
                   binding.participantsLayout.setVisibility(View.VISIBLE);
                   binding.exitGroupLayout.setVisibility(View.VISIBLE);
                   Adapter.notifyDataSetChanged();
                   binding.participantsCount.setText(list.size()+" Participants");
                   binding.participantsCount.setVisibility(View.VISIBLE);

               }
           }
       });
    }

    private void getGroupDetails() {
        store.collection("Groups").document(groupId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Date mDate1 = documentSnapshot.getDate("dateCreated");
                    Subject = documentSnapshot.getString("groupSubject");
                   final String Description = documentSnapshot.getString("description");
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                String Date= formatter.format(mDate1);
                binding.username.setText(Subject);
                binding.created.setText("Created on "+Date);

                if(Description.equals("") && isAdmin){
                    binding.descriptionLayout.setVisibility(View.VISIBLE);
                   binding.addDescriptionLayout.setVisibility(View.VISIBLE);
                   binding.addDescriptionLayout.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           startActivity(new Intent(GroupProfileActivity.this,GroupDescription.class).putExtra("groupId",groupId));
                       }
                   });
                }else if(Description.equals("") && !isAdmin){

                }else if(!Description.equals("") && isAdmin){
                    binding.description.setText(Description);
                    binding.descriptionLayout.setVisibility(View.VISIBLE);
                    binding.addDescriptionLayout.setVisibility(View.GONE);
                    binding.viewDescriptionLayout.setVisibility(View.VISIBLE);
                    binding.editDescription.setVisibility(View.VISIBLE);
                    binding.editDescription.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(GroupProfileActivity.this,GroupDescription.class).putExtra("groupId",groupId).putExtra("description",Description));
                        }
                    });
                }
                else{
                    binding.description.setText(Description);
                    binding.descriptionLayout.setVisibility(View.VISIBLE);
                    binding.viewDescriptionLayout.setVisibility(View.VISIBLE);


                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    @Override
    protected void onResume() {

        if(!dataChecked){
           dataChecked = true;
        }else{
            getGroupInfo();
        }



        super.onResume();
    }
}
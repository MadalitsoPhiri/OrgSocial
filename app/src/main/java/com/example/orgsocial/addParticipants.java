package com.example.orgsocial;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.orgsocial.adapters.ParticipantsAdapter;
import com.example.orgsocial.adapters.participantsAddedAdapter;
import com.example.orgsocial.databinding.ActivityAddParticipantsBinding;
import com.example.orgsocial.models.Chats;
import com.example.orgsocial.models.Group;
import com.example.orgsocial.models.GroupUser;
import com.example.orgsocial.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class addParticipants extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    DatabaseReference mRef;
    ActivityAddParticipantsBinding binding;
    List<User> users = new ArrayList<User>();
    List<String> participantsId = new ArrayList<>();;
    List<User> superusers = new ArrayList<>();
    List<User> finalList = new ArrayList<>();
    participantsAddedAdapter adapter1;
    ProgressDialog Dialog;
    String groupId,subject,photoUrl;
    ParticipantsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_participants);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference();
        Dialog = new ProgressDialog(this);
        Intent intent = getIntent();
        groupId = intent.getStringExtra("groupId");
        photoUrl = intent.getStringExtra("groupPhotoUrl");
        subject = intent.getStringExtra("subject");
        List<GroupUser> participants = (List<GroupUser>)intent.getSerializableExtra("participants");
        participantsId.clear();
        for(GroupUser current:participants){
            participantsId.add(current.getUserId());
        }
        adapter1 = new participantsAddedAdapter(finalList,addParticipants.this,binding.participantsRecyclerView,superusers);
        binding.participantsAddedRecyclerView.setAdapter(adapter1);
        adapter1.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                if(finalList.size() == 0){
                    binding.RecyclerSeparator.setVisibility(View.GONE);
                    binding.add.setVisibility(View.GONE);
                }else{
                    binding.RecyclerSeparator.setVisibility(View.VISIBLE);
                    binding.add.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.participantsAddedRecyclerView.setLayoutManager(new LinearLayoutManager(addParticipants.this, LinearLayoutManager.HORIZONTAL,false));
        binding.participantsAddedRecyclerView.setVisibility(View.VISIBLE);


        firestore = FirebaseFirestore.getInstance();
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        binding.add.setOnClickListener(new View.OnClickListener() {
                                           //code to add participant
                                           @Override
                                           public void onClick(View view) {
                                               saveParticipants();
                                           }
                                       });
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        if(mAuth.getCurrentUser() != null){
            getContacts();
        }
     }

    private void saveParticipants() {
        Dialog.setMessage("adding participants please wait....");
        Dialog.setCanceledOnTouchOutside(false);
        Dialog.show();
        WriteBatch batch = firestore.batch();
        final Group info = new Group(photoUrl, groupId, subject);
        for (final User current : finalList) {
            mRef.child("GroupList").child(current.getUserId()).child(groupId).push().setValue(info).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    final Chats chat = new Chats(
                            current.getUserName() +" added",
                            "ANNOUNCEMENT",
                            "orgsocial@info.com","orgsocial@info.com");
                    mRef.child("GroupChats").child(groupId).push().setValue(chat).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"An Error Occurred try again later!",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            });
        }

        for(final User current: finalList){
            GroupUser groupMember =  new GroupUser(current.getUserId(),current.getUserName(),current.getUserEmail(),current.getUserPhone(),current.getUserPhotoUrl(),current.getDescription(),false,false);
           DocumentReference myRef = firestore.collection("Groups").document(groupId).collection("Participants").document(current.getUserId());
           batch.set(myRef,groupMember);
        }
        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Dialog.dismiss();
                String message;
                if(finalList.size() > 1){
                    message = finalList.size()+" participants added";
                }else{
                    message = finalList.size()+" participant added";
                }
                Toast toast = Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        // Handle your menu item clicks here
        if(id == R.id.group_menu_search){
            Toast.makeText(getApplication(),"Search icon clicked",Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.group_menu,menu);
        return true;
    }
    private void getContacts() {
        firestore.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for(QueryDocumentSnapshot doc:queryDocumentSnapshots){
                    User user = new User();
                    String userId = doc.getString("userId");
                    user.setUserId(doc.getString("userId"));
                    user.setVerified(doc.getBoolean("verified"));
                    user.setUserName(doc.getString("userName"));
                    user.setUserEmail(doc.getString("userEmail"));
                    user.setDescription(doc.getString("description"));
                    user.setUserPhotoUrl(doc.getString("userPhotoUrl"));
                    user.setUserPhone(doc.getString("userPhone"));


                    if(userId != null && !userId.equals(mAuth.getCurrentUser().getUid()) && user.isVerified()){
                        if(user.getUserEmail().equals("luyanganwawa@gmail.com") || user.getUserEmail().equals("bsangunde@gmail.com")){
                            superusers.add(user);
                        }else{
                            users.add(user);
                        }

                    }


                }
                superusers.addAll(users);
                adapter = new ParticipantsAdapter(superusers,finalList,addParticipants.this,adapter1,participantsId);
                binding.participantsRecyclerView.setAdapter(adapter);
                binding.participantsRecyclerView.setLayoutManager(new LinearLayoutManager(addParticipants.this));
                adapter1.mAdapter = adapter;
                adapter1.notifyDataSetChanged();
                adapter.notifyDataSetChanged();
                binding.participantsProgress.setVisibility(View.GONE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}
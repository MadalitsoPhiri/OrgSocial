package com.example.orgsocial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.orgsocial.adapters.GroupsAdapter;
import com.example.orgsocial.adapters.groupChatListAdapter;
import com.example.orgsocial.databinding.ActivityGroupListBinding;
import com.example.orgsocial.models.Group;
import com.example.orgsocial.models.User;
import com.example.orgsocial.settings.SettingsActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityGroupList extends AppCompatActivity {
   ActivityGroupListBinding binding;
    RecyclerView groupsRecycler;
    FirebaseFirestore store;
    DatabaseReference mRef;
    FirebaseAuth mAuth;
    String groupSubject;
    List<String> groupsList;
    List<Group> groupsList1;
    ProgressBar progress;
    GroupsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     binding = DataBindingUtil.setContentView(this,R.layout.activity_group_list);
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_baseline_more_vert_24);
        groupsRecycler = binding.recyclerView;
        store = FirebaseFirestore.getInstance();
        groupsList = new ArrayList<String>();
        groupsList1 = new ArrayList<Group>();
        adapter = new GroupsAdapter(groupsList1,getApplicationContext());
        groupsRecycler.setAdapter(adapter);
        groupsRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        progress = binding.groupListProgress;
        mRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        getGroupList();
        binding.toolbar.setOverflowIcon(drawable);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.newGroupLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityGroupList.this,ParticipantsActivity.class));
            }
        });
    }

    private void getGroupList() {
        mRef.child("GroupList").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    groupsList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        String currentGroupId = snapshot.getKey();
                        groupsList.add(currentGroupId);


                    }
                    getListInfo();
                } else {
                    progress.setVisibility(View.GONE);
                    binding.newGroupLayout.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progress.setVisibility(View.GONE);
                binding.newGroupLayout.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getListInfo() {
        store.collection("Groups").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot current: queryDocumentSnapshots.getDocuments()){
                    String creator = current.getString("creator");
                    List<User> participants = (List<User>) current.get("participants");
                    Date dateCreated = current.getDate("dateCreated");
                    String Description = current.getString("description");
                    String groupPhotoUrl = current.getString("groupPhotoUrl");
                    String groupSubject = current.getString("groupSubject");
                    String groupId = current.getString("groupId");
                    List<User> admins = (List<User>)current.get("admins");
                    Group group = new Group(groupPhotoUrl,groupId,groupSubject);
                    group.setDateCreated(dateCreated);
                    if(groupsList.contains(groupId)){
                        groupsList1.add(group);
                    }

                }
                progress.setVisibility(View.GONE);
                binding.newGroupLayout.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        // Handle your menu item clicks here
        if(id == R.id.inviteContact){
            Toast.makeText(getApplication(),"Invite a contact",Toast.LENGTH_SHORT).show();
        }else if(id == R.id.help){
            Toast.makeText(getApplication(),"help clicked",Toast.LENGTH_SHORT).show();
        }else if(id == R.id.group_menu_search){
            Toast.makeText(getApplication(),"Search icon clicked",Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.group_menu,menu);
        return true;
    }
}
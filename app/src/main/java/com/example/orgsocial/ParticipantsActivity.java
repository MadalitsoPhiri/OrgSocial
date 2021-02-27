package com.example.orgsocial;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.orgsocial.adapters.ContactsAdapter;
import com.example.orgsocial.adapters.ParticipantsAdapter;
import com.example.orgsocial.adapters.participantsAddedAdapter;
import com.example.orgsocial.databinding.ActivityParticipantsGroupsBinding;
import com.example.orgsocial.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ParticipantsActivity extends AppCompatActivity {
    ActivityParticipantsGroupsBinding binding;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    List<User> users = new ArrayList<User>();
    List<User> superusers = new ArrayList<>();
    List<User> finalList = new ArrayList<>();
    participantsAddedAdapter adapter1;
    ParticipantsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      binding = DataBindingUtil.setContentView(this,R.layout.activity_participants_groups);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mAuth = FirebaseAuth.getInstance();

        adapter1 = new participantsAddedAdapter(finalList,ParticipantsActivity.this,binding.participantsRecyclerView,superusers);
        binding.participantsAddedRecyclerView.setAdapter(adapter1);
        adapter1.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                if(finalList.size() == 0){
                   binding.participantCount.setText("Add participants");
                    binding.RecyclerSeparator.setVisibility(View.GONE);
                }else{
                    binding.participantCount.setText(finalList.size() + " of "+superusers.size()+" selected");
                    binding.RecyclerSeparator.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.participantsAddedRecyclerView.setLayoutManager(new LinearLayoutManager(ParticipantsActivity.this, LinearLayoutManager.HORIZONTAL,false));
        binding.participantsAddedRecyclerView.setVisibility(View.VISIBLE);


        firestore = FirebaseFirestore.getInstance();
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(finalList.size() == 0){
                    Toast toast = Toast.makeText(getApplicationContext(),"Atleast 1 contact must be selected",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else{
                    Intent intent = new Intent(ParticipantsActivity.this,groupSubjectActivity.class);
                    intent.putExtra("final",(Serializable) finalList);
                    startActivity(intent);

                }

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

                adapter = new ParticipantsAdapter(superusers,ParticipantsActivity.this,finalList,adapter1);
                binding.participantsRecyclerView.setAdapter(adapter);
                binding.participantsRecyclerView.setLayoutManager(new LinearLayoutManager(ParticipantsActivity.this));
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
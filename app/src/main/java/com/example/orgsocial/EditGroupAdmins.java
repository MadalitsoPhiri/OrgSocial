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
import android.view.View;
import android.widget.Toast;

import com.example.orgsocial.adapters.AdminAdapter;
import com.example.orgsocial.adapters.AdminAddedAdapter;
import com.example.orgsocial.adapters.ParticipantsAdapter;
import com.example.orgsocial.adapters.participantsAddedAdapter;
import com.example.orgsocial.databinding.ActivityEditGroupAdminsBinding;
import com.example.orgsocial.models.GroupUser;
import com.example.orgsocial.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class EditGroupAdmins extends AppCompatActivity {
   List<GroupUser> list;
   ActivityEditGroupAdminsBinding binding;
    FirebaseAuth mAuth;
    ProgressDialog Dialog;
    String groupId;
    FirebaseFirestore store;
    List<GroupUser> finalList = new ArrayList<>();
    AdminAddedAdapter adapter1;
    AdminAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding  = DataBindingUtil.setContentView(this,R.layout.activity_edit_group_admins);
        Intent intent = getIntent();
        list = (List<GroupUser>) intent.getSerializableExtra("participants");
        groupId = intent.getStringExtra("groupId");
        store = FirebaseFirestore.getInstance();
        Dialog = new ProgressDialog(this);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mAuth = FirebaseAuth.getInstance();

        adapter1 = new AdminAddedAdapter(finalList,EditGroupAdmins.this,binding.participantsRecyclerView,list);
        binding.participantsAddedRecyclerView.setAdapter(adapter1);
        adapter1.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                if(finalList.size() == 0){
                    binding.participantCount.setText("Tap to add Admin");
                    binding.RecyclerSeparator.setVisibility(GONE);
                    binding.save.setVisibility(GONE);
                }else{
                    binding.participantCount.setText(finalList.size() + " of "+list.size()+" selected");
                    binding.RecyclerSeparator.setVisibility(View.VISIBLE);
                    binding.save.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.participantsAddedRecyclerView.setLayoutManager(new LinearLayoutManager(EditGroupAdmins.this, LinearLayoutManager.HORIZONTAL,false));
        binding.participantsAddedRecyclerView.setVisibility(View.VISIBLE);



        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    updateAdminInfo();

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

    private void updateAdminInfo() {
        Dialog.setMessage("updating admin info please wait....");
        Dialog.setCanceledOnTouchOutside(false);
        Dialog.show();

        // Get a new write batch
        WriteBatch batch = store.batch();
        for(GroupUser current: finalList){
            list.remove(current);
            DocumentReference docRef = store.collection("Groups").document(groupId).collection("Participants").document(current.getUserId());
            batch.update(docRef,"admin",true);
        }
        for(GroupUser current:list){
           DocumentReference docRef2 = store.collection("Groups").document(groupId).collection("Participants").document(current.getUserId());
           batch.update(docRef2,"admin",false);
        }



// Commit the batch
        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Dialog.dismiss();
                finish();
            }
        });


    }

    private void getContacts() {
        for(GroupUser current:list){
            if(current.getAdmin()){
                finalList.add(current);

            }
        }
        adapter = new AdminAdapter(list,finalList,EditGroupAdmins.this,adapter1);
        binding.participantsRecyclerView.setAdapter(adapter);
        binding.participantsRecyclerView.setLayoutManager(new LinearLayoutManager(EditGroupAdmins.this));
        adapter1.mAdapter = adapter;
        adapter1.notifyDataSetChanged();
        adapter.notifyDataSetChanged();
        binding.participantsProgress.setVisibility(GONE);
    }
}
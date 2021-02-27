package com.example.orgsocial;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.orgsocial.adapters.ContactsAdapter;
import com.example.orgsocial.databinding.ActivityContactsBinding;
import com.example.orgsocial.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ContactsActivity extends AppCompatActivity {
  ActivityContactsBinding binding;
  FirebaseAuth mAuth;
  FirebaseFirestore firestore;
    List<User> users = new ArrayList<User>();
    List<User> superusers = new ArrayList<>();
    List<User> finalList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_contacts);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));


        mAuth = FirebaseAuth.getInstance();

        firestore = FirebaseFirestore.getInstance();
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_baseline_more_vert_24);
        binding.toolbar.setOverflowIcon(drawable);

        if(mAuth.getCurrentUser() != null){
            getContacts();
        }



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
        getMenuInflater().inflate(R.menu.contacts_menu,menu);
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


                    if(userId != null && !userId.equals(mAuth.getCurrentUser().getUid()) && user.isVerified()){
                        if(user.getUserEmail().equals("luyanganwawa@gmail.com") || user.getUserEmail().equals("bsangunde@gmail.com")){
                           superusers.add(user);
                        }else{
                            users.add(user);
                        }

                    }


                }
                superusers.addAll(users);

                ContactsAdapter adapter = new ContactsAdapter(superusers,ContactsActivity.this);
                binding.recyclerView.setAdapter(adapter);
                binding.contactListProgress.setVisibility(View.GONE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
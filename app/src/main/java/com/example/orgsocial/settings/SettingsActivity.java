package com.example.orgsocial.settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.model.content.ShapeTrimPath;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.orgsocial.ProfileActivity;
import com.example.orgsocial.R;
import com.example.orgsocial.databinding.ActivityAccountSettingsBinding;
import com.example.orgsocial.databinding.ActivitySettingsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {

    //Declare Activity properties or variables
    private static final String TAG = "Settings";
    ActivitySettingsBinding binding;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the initial activity screen with the databinding Dependency

        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);

        //Initialize the Properties and variables
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        store = FirebaseFirestore.getInstance();

        //Custom Back button for the androidx toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //check if User is logged in
        if (firebaseUser != null) {
            getInfo();
        }

        //add a click listener to the profile linearlayout to enable click event
        initClickAction();
    }

    private void initClickAction() {
        binding.lnProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this, ProfileActivity.class));
            }
        });
        binding.accountSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start account Settings
              Intent intent = new Intent(SettingsActivity.this, AccountSettings.class);
              startActivity(intent);

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseUser != null) {
            getInfo();
        }
    }

    //Get User profile Info from Firebase
    private void getInfo() {
        store.collection("Users").document(firebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {


            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot != null && documentSnapshot.exists()) {

                   String User = documentSnapshot.get("userName").toString();
                    String Bio = documentSnapshot.get("description").toString();
                    String photoUrl = documentSnapshot.get("userPhotoUrl").toString();
                    if (Bio == "") {
                        binding.userBio.setText("Hey there i am using Orgsocial");
                    } else {
                        binding.userBio.setText(Bio);
                    }
                    if (User == null) {
                        binding.username.setText("Unknown User");
                    } else {
                        binding.username.setText(User);
                    }
                    if (photoUrl == "") {
                        binding.profileImgProgress.setVisibility(View.GONE);
                        Drawable dr = getDrawable(R.drawable.ic_group_13);
                        binding.profilePhoto.setImageDrawable(dr);
                    } else {
                        Glide.with(getApplicationContext()).load(photoUrl).listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                binding.profileImgProgress.setVisibility(View.GONE);
                                Drawable dr = getDrawable(R.drawable.ic_group_13);
                                binding.profilePhoto.setImageDrawable(dr);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                binding.profileImgProgress.setVisibility(View.GONE);
                                return false;
                            }
                        } ).into(binding.profilePhoto);
                    }
                }else{
                    //Handle Empty snapshot
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof FirebaseNetworkException) {
                    Log.d(TAG, "onFailure: " + e.getMessage());
                    Toast.makeText(getApplicationContext(), "Network Error Check your connection", Toast.LENGTH_LONG).show();
                } else {
                    Log.d(TAG, "onFailure: " + e.getMessage());
                    Toast.makeText(getApplicationContext(), "An Error Occured.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
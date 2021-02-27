package com.example.orgsocial;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.orgsocial.databinding.ActivityChatProfileBinding;
import com.example.orgsocial.models.Common;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChatProfileActivity extends AppCompatActivity {
   ActivityChatProfileBinding binding;
   String userId,username,dateJoined,phone,about,email,photoUrl,lastSeen;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_chat_profile);
       Intent intent = getIntent();
       userId = intent.getStringExtra("userId");
       phone = intent.getStringExtra("phone");
       username = intent.getStringExtra("userName");
       dateJoined = intent.getStringExtra("dateJoined");
       lastSeen = intent.getStringExtra("date");
       photoUrl = intent.getStringExtra("userPhotoUrl");
       email = intent.getStringExtra("userEmail");
       about =  intent.getStringExtra("about");
       binding.back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               onBackPressed();
           }
       });
       if(photoUrl.equals("")){
           binding.progress.setVisibility(View.GONE);
           Drawable dr = getDrawable(R.drawable.ic_group_13);
           binding.profileImage.setImageDrawable(dr);
           binding.profileImage.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

                       ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(ChatProfileActivity.this,binding.profileImage,"profImg");
                       Intent intent = new Intent(ChatProfileActivity.this,ViewImageActivity.class);
                       intent.putExtra("photoUrl","");
                       intent.putExtra("PhotoType","chat");
                       startActivity(intent,compat.toBundle());

               }
           });
           binding.chatBtnLayout.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent intent = new Intent(ChatProfileActivity.this,ChatsActivity.class);
                   intent.putExtra("userId",userId).putExtra("userName",username).putExtra("userPhotoUrl",photoUrl).putExtra("userEmail",email).putExtra("date",lastSeen).putExtra("joined",dateJoined).putExtra("phone",phone).putExtra("about",about);
                   startActivity(intent);
                   finish();
               }
           });
       }else{
           Glide.with(this).load(photoUrl).listener(new RequestListener<Drawable>() {
               @Override
               public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                   return false;
               }

               @Override
               public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                   binding.progress.setVisibility(View.GONE);
                   binding.chatBtnLayout.setVisibility(View.VISIBLE);
                   binding.profileImage.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           if(photoUrl.equals("")){
                               ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(ChatProfileActivity.this,binding.profileImage,"profImg");
                               Intent intent = new Intent(ChatProfileActivity.this,ViewImageActivity.class);
                               intent.putExtra("photoUrl","");
                               intent.putExtra("PhotoType","chat");
                               startActivity(intent,compat.toBundle());
                           }else{

                               Drawable dr = binding.profileImage.getDrawable();
                               Common.IMAGE_BITMAP = ((BitmapDrawable)dr.getCurrent()).getBitmap();
                               ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(ChatProfileActivity.this,binding.profileImage,"profImg");
                               Intent intent = new Intent(ChatProfileActivity.this,ViewImageActivity.class);
                               intent.putExtra("photoUrl",photoUrl);
                               intent.putExtra("PhotoType","chat");
                               startActivity(intent,compat.toBundle());
                           }
                       }
                   });
                   binding.chatBtnLayout.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           Intent intent = new Intent(ChatProfileActivity.this,ChatsActivity.class);
                           intent.putExtra("userId",userId).putExtra("userName",username).putExtra("userPhotoUrl",photoUrl).putExtra("userEmail",email).putExtra("date",lastSeen).putExtra("joined",dateJoined).putExtra("phone",phone).putExtra("about",about);
                           startActivity(intent);
                              finish();
                       }
                   });
                   return false;
               }
           }).into(binding.profileImage);
       }

        binding.username.setText(intent.getStringExtra("userName"));

        if(about.equals("")){
            binding.detailBio.setText("Hey there i am using Orgsocial");
        }else{
            binding.detailBio.setText(about);
        }

        binding.detailEmail.setText(email);
        binding.detailPhone.setText(phone);
        binding.detailDateJoined.setText(dateJoined);


    }

}
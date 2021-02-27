package com.example.orgsocial;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.orgsocial.models.Common;

public class DialogActivity extends AppCompatActivity {
    ProgressBar profileImgProgress;
    ImageView profileImage;
    ImageButton chat,profileInfo;
    TextView username;
    String photoUrl,userName,chatType,phone,about,joined,groupId;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dialog);
        final Intent intent = getIntent();
        photoUrl = intent.getStringExtra("photoUrl");
        userName = intent.getStringExtra("username");
        chatType = intent.getStringExtra("chatType");
        joined = intent.getStringExtra("joined");
        about = intent.getStringExtra("about");
        phone = intent.getStringExtra("phone");
        groupId = intent.getStringExtra("groupId");

        profileImgProgress = findViewById(R.id.profileCardProgress);
        profileImage = findViewById(R.id.profileImage);
        username = findViewById(R.id.Username);
        chat = findViewById(R.id.chat);
        profileInfo = findViewById(R.id.profileInfo);

        //Click Listener for the chat icon on dialog
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chatType.equals("chat")){
                    startActivity(new Intent(DialogActivity.this, ChatsActivity.class).putExtra("userId",intent.getStringExtra("userId")).putExtra("userName",intent.getStringExtra("userName")).putExtra("userPhotoUrl",intent.getStringExtra("userPhotoUrl")).putExtra("userEmail",intent.getStringExtra("userEmail")).putExtra("date",intent.getStringExtra("date")).putExtra("about",about).putExtra("phone",phone).putExtra("dateJoined",joined));
                }else{
                    startActivity(new Intent(DialogActivity.this, GroupChatActivity.class).putExtra("groupId",intent.getStringExtra("groupId")).putExtra("subject",intent.getStringExtra("subject")).putExtra("photoUrl",intent.getStringExtra("photoUrl")).putExtra("lastseen",intent.getStringExtra("lastseen")));
                }
                finish();
            }
        });

        //click Listener for the profile info icon in dialog
        profileInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chatType.equals("chat")){
                    startActivity(new Intent(DialogActivity.this,ChatProfileActivity.class).putExtra("userId",intent.getStringExtra("userId")).putExtra("userName",intent.getStringExtra("userName")).putExtra("userPhotoUrl",intent.getStringExtra("userPhotoUrl")).putExtra("userEmail",intent.getStringExtra("userEmail")).putExtra("date",intent.getStringExtra("date")).putExtra("about",intent.getStringExtra("about")).putExtra("phone",intent.getStringExtra("phone")).putExtra("dateJoined",intent.getStringExtra("joined")));
                   finish();
                }else{
                   startActivity(new Intent(DialogActivity.this,GroupProfileActivity.class).putExtra("userName",userName).putExtra("userPhotoUrl",photoUrl).putExtra("groupId",groupId));
                   finish();
                }
            }
        });

        //click Listener for the profile photo in dialog

        //Set the dialog Username
        username.setText(userName);
        if(photoUrl.equals("") && chatType.equals("chat")){
            profileImgProgress.setVisibility(View.GONE);
            Drawable dr = getDrawable(R.drawable.ic_group_13);
            profileImage.setImageDrawable(dr);
            profileImage.setClickable(true);
            profileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //check if user has a profile photo uploaded and load image accordingly
                    if(photoUrl.equals("")){
                        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(DialogActivity.this,profileImage,"profImg");
                        Intent intent = new Intent(DialogActivity.this,ViewImageActivity.class);
                        intent.putExtra("photoUrl","");
                        intent.putExtra("PhotoType",chatType);
                        startActivity(intent,compat.toBundle());
                        finish();
                    }else{

                        Drawable dr = profileImage.getDrawable();
                        Common.IMAGE_BITMAP = ((BitmapDrawable)dr.getCurrent()).getBitmap();
                        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(DialogActivity.this,profileImage,"profImg");
                        Intent intent = new Intent(DialogActivity.this,ViewImageActivity.class);
                        intent.putExtra("photoUrl",photoUrl);
                        intent.putExtra("PhotoType",chatType);
                        startActivity(intent,compat.toBundle());
                        finish();
                    }

                }
            });
        }else if(photoUrl.equals("") && chatType.equals("group")){
            profileImgProgress.setVisibility(View.GONE);
            Drawable dr = getDrawable(R.drawable.ic_group_14);
            profileImage.setImageDrawable(dr);
            profileImage.setClickable(true);
            profileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //check if user has a profile photo uploaded and load image accordingly
                    if(photoUrl.equals("")){
                        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(DialogActivity.this,profileImage,"profImg");
                        Intent intent = new Intent(DialogActivity.this,ViewImageActivity.class);
                        intent.putExtra("photoUrl","");
                        intent.putExtra("PhotoType",chatType);
                        startActivity(intent,compat.toBundle());
                        finish();
                    }else{

                        Drawable dr = profileImage.getDrawable();
                        Common.IMAGE_BITMAP = ((BitmapDrawable)dr.getCurrent()).getBitmap();
                        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(DialogActivity.this,profileImage,"profImg");
                        Intent intent = new Intent(DialogActivity.this,ViewImageActivity.class);
                        intent.putExtra("photoUrl",photoUrl);
                        intent.putExtra("PhotoType",chatType);
                        startActivity(intent,compat.toBundle());
                        finish();
                    }

                }
            });
        }else{
            Glide.with(getApplicationContext()).load(photoUrl).listener(new RequestListener<Drawable>() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    profileImgProgress.setVisibility(View.GONE);
                    profileImage.setClickable(true);
                    profileImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //check if user has a profile photo uploaded and load image accordingly
                            if(photoUrl.equals("")){
                                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(DialogActivity.this,profileImage,"profImg");
                                Intent intent = new Intent(DialogActivity.this,ViewImageActivity.class);
                                intent.putExtra("photoUrl","");
                                intent.putExtra("PhotoType",chatType);
                                startActivity(intent,compat.toBundle());
                                finish();
                            }else{

                                Drawable dr = profileImage.getDrawable();
                                Common.IMAGE_BITMAP = ((BitmapDrawable)dr.getCurrent()).getBitmap();
                                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(DialogActivity.this,profileImage,"profImg");
                                Intent intent = new Intent(DialogActivity.this,ViewImageActivity.class);
                                intent.putExtra("photoUrl",photoUrl);
                                intent.putExtra("PhotoType",chatType);
                                startActivity(intent,compat.toBundle());
                                finish();
                            }

                        }
                    });
                    return false;
                }
            } ).into(profileImage);
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Rect viewRect = new Rect();
        FrameLayout layout = findViewById(R.id.layout);
        layout.getGlobalVisibleRect(viewRect);
        if (!viewRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
           finishAfterTransition();
        }
        return super.dispatchTouchEvent(ev);
    }


}
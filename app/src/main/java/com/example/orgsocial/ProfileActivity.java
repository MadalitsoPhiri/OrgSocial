package com.example.orgsocial;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.orgsocial.databinding.ActivityProfileBinding;
import com.example.orgsocial.models.Common;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;
    FirebaseFirestore store;
    FirebaseAuth mAuth;
    FirebaseUser User;
    FirebaseStorage cloudStorage;
    StorageReference storageRef;
    ProgressDialog Dialog;
    String photoUrl;
    BottomSheetDialog bottomSheetDialog;
    private int IMAGE_GALLERY_REQUEST_CODE = 100;
    Uri ImageUri;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_profile);
        store = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        User = mAuth.getCurrentUser();
        Dialog = new ProgressDialog(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        cloudStorage = FirebaseStorage.getInstance();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /**set Onclick listeners*/
        binding.detailNameEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showBottomSheetEditName();
            }
        });
        binding.detailBioEdit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                showBottomSheetEditBio();
            }
        });

        binding.detailPhoneEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetEditPhone();
            }
        });

        binding.profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(photoUrl != ""){
                    Drawable dr = binding.profileImg.getDrawable();
                    Common.IMAGE_BITMAP = ((BitmapDrawable)dr.getCurrent()).getBitmap();
                    ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(ProfileActivity.this,binding.profileImg,"profImg");
                    Intent intent = new Intent(ProfileActivity.this,ViewImageActivity.class);
                    intent.putExtra("photoUrl",photoUrl);
                    intent.putExtra("PhotoType","chat");
                    startActivity(intent,compat.toBundle());
                }else{
                    ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(ProfileActivity.this,binding.profileImg,"profImg");
                    Intent intent = new Intent(ProfileActivity.this,ViewImageActivity.class);
                    intent.putExtra("photoUrl","");
                    intent.putExtra("PhotoType","chat");
                    startActivity(intent,compat.toBundle());
                }

            }
        });


        binding.fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetPicker();
            }
        });

        /**get Info when the activity first starts*/
        if(User != null){
            getInfo();
        }

    }



    private void showBottomSheetEditPhone() {
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_edit_phone,null);

        final EditText phone = view.findViewById(R.id.edit_phone);
        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
        view.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(phone.getText().toString().trim()) == false){
                    updateInfo(phone.getText().toString().trim(),"userPhone");
                }else{
                    Toast.makeText(getApplicationContext(),"Phone number can't be empty",Toast.LENGTH_LONG).show();
                }

                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.setContentView(view);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Objects.requireNonNull(bottomSheetDialog.getWindow()).addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                bottomSheetDialog = null;
            }
        });
        bottomSheetDialog.show();
    }

    private void showBottomSheetEditBio() {
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_edit_about,null);

        final EditText about = view.findViewById(R.id.edit_about);
        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
        view.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(about.getText().toString().trim()) == false){
                    updateInfo(about.getText().toString().trim(),"description");
                }else{
                    Toast.makeText(getApplicationContext(),"about can't be empty",Toast.LENGTH_LONG).show();
                }

                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.setContentView(view);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Objects.requireNonNull(bottomSheetDialog.getWindow()).addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                bottomSheetDialog = null;
            }
        });
        bottomSheetDialog.show();

    }

    private void showBottomSheetEditName() {
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_edit_name,null);

    final EditText name = view.findViewById(R.id.edit_username);
        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
        view.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(name.getText().toString().trim()) == false){
                    updateInfo(name.getText().toString().trim(),"userName");
                }else{
                    Toast.makeText(getApplicationContext(),"Name can't be empty",Toast.LENGTH_LONG).show();
                }

                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.setContentView(view);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Objects.requireNonNull(bottomSheetDialog.getWindow()).addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                bottomSheetDialog = null;
            }
        });
        bottomSheetDialog.show();

    }

    private void updateInfo(String data,String field) {
        Dialog.setMessage("Updating");
        Dialog.setCanceledOnTouchOutside(false);
        Dialog.show();
        store.collection("Users").document(mAuth.getCurrentUser().getUid()).update(field,data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                getInfo();
                Dialog.dismiss();
                Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Dialog.dismiss();
                if(e instanceof FirebaseNetworkException){
                    Toast.makeText(getApplicationContext(),"Network Error. Please Check your connection",Toast.LENGTH_LONG).show();
                }else{
                    e.printStackTrace();
                    Log.d("ProfileActivity", "onFailure: "+ e.getMessage());
                }
            }
        });
    }

    // Get file extension from Image Uri for the firebase storage transaction
    private String getFileExtension(Uri imageUri) {
        ContentResolver resolver = getContentResolver();
        MimeTypeMap map = MimeTypeMap.getSingleton();
        return map.getExtensionFromMimeType(resolver.getType(imageUri));
    }

    /** standard code for the back button navigation in toolbar*/

    @Override
    public boolean onSupportNavigateUp() {
       onBackPressed();
        return true;
    }
    /** For the bottom sheet picker used to choose between camera an gallery*/
    private void showBottomSheetPicker() {
        bottomSheetDialog = new BottomSheetDialog(this);
       View view = getLayoutInflater().inflate(R.layout.bottom_sheet_picker,null);
       view.findViewById(R.id.gallery_button).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               openGallery();
             bottomSheetDialog.dismiss();
           }
       });
       view.findViewById(R.id.camera_button).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               openCamera();
               Toast.makeText(getApplicationContext(),"Camera",Toast.LENGTH_SHORT).show();
               bottomSheetDialog.dismiss();
           }
       });
       bottomSheetDialog.setContentView(view);
       if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
           Objects.requireNonNull(bottomSheetDialog.getWindow()).addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
       }
       bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
           @Override
           public void onDismiss(DialogInterface dialogInterface) {
               bottomSheetDialog = null;
           }
       });
       bottomSheetDialog.show();

    }

    /** opens the camera to take a profile photo*/
    private void openCamera() {


    }

    /** Handling of intent results such as the opening of the gallery results*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Handle gallery image selection when image picked
        if(requestCode == IMAGE_GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null){
             ImageUri = data.getData();
            // upload to Firebase
            uploadFirebaseBucket(ImageUri);
             //change the bitmap or photo of the profile when the user chooses a photo

        }else{
            Toast.makeText(getApplicationContext(),"An Error Occurred.",Toast.LENGTH_SHORT).show();
        }
    }

    /** uploads a profile photo to Firebase bucket for the current user*/
    private void uploadFirebaseBucket(Uri uri) {
        if(uri != null){
            //show progress bar here
            Dialog.setMessage("Uploading photo please wait....");
            Dialog.setCanceledOnTouchOutside(false);
            Dialog.show();
        storageRef = cloudStorage.getReference().child("images/profiles/" + System.currentTimeMillis() + "." + getFileExtension(ImageUri));
        storageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isSuccessful());
                Uri uploadedImgUrl = uriTask.getResult();

                final String downloadUrl = String.valueOf(uploadedImgUrl);


                //Update the users profile info
                store.collection("Users").document(mAuth.getCurrentUser().getUid()).update("userPhotoUrl", downloadUrl).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        Dialog.dismiss();
                        getInfo();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                           Dialog.dismiss();
                           if(e instanceof FirebaseNetworkException){
                               Toast.makeText(getApplicationContext(),"Network Error. Please Check your connection",Toast.LENGTH_LONG).show();
                           }else{
                               e.printStackTrace();
                               Log.d("ProfileActivity", "onFailure: "+ e.getMessage());
                           }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Dialog.dismiss();
                if(e instanceof FirebaseNetworkException){
                    Toast.makeText(getApplicationContext(),"Network Error. Please Check your connection",Toast.LENGTH_LONG).show();
                }else{
                    e.printStackTrace();
                    Log.d("ProfileActivity", "onFailure: "+ e.getMessage());
                }
            }
        });
    }
    }

   /**opens an intent to the gallery or photos in android to pick a photo*/
    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent,"select profile image"),IMAGE_GALLERY_REQUEST_CODE);
    }

    /** loads user info onto the layout or screen*/
    private void getInfo() {
        binding.profileImg.setClickable(false);
        store.collection("Users").document(User.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                 String name = documentSnapshot.get("userName").toString();
                 String phone = documentSnapshot.get("userPhone").toString();
                 String email = documentSnapshot.get("userEmail").toString();
                 photoUrl = documentSnapshot.get("userPhotoUrl").toString();
                 String about = documentSnapshot.get("description").toString();

                 binding.detailName.setText(name);
                 binding.detailPhone.setText(phone);
                 binding.detailEmail.setText(email);
                if (about == "") {
                    binding.detailBio.setText("Hey there i am using Orgsocial");
                } else {
                    binding.detailBio.setText(about);
                }

                if(photoUrl == ""){
                   binding.profileImgProgress.setVisibility(View.GONE);
                   binding.profileImg.setImageDrawable(getDrawable(R.drawable.ic_group_13));
                    binding.profileImg.setClickable(true);
                }else{
                    //use glide to load photo from the photo url
                    Glide.with(getApplicationContext()).load(photoUrl).listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            binding.profileImgProgress.setVisibility(View.GONE);
                            binding.profileImg.setClickable(true);
                            return false;
                        }
                    } ).into(binding.profileImg);

                    }

                }



        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle Exceptions here
                if(e instanceof FirebaseNetworkException){
                    Toast.makeText(getApplicationContext(),"Network Error please check your connection",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"An Error Occurred",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
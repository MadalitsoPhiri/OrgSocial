package com.example.orgsocial;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orgsocial.adapters.participantsGridLayoutAdapter;
import com.example.orgsocial.databinding.ActivityGroupSubjectBinding;
import com.example.orgsocial.models.Chats;
import com.example.orgsocial.models.GroupUser;
import com.example.orgsocial.models.User;
import com.example.orgsocial.models.Group;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class groupSubjectActivity extends AppCompatActivity {
    ActivityGroupSubjectBinding binding;
    BottomSheetDialog bottomSheetDialog;
    public static final int IMAGE_GALLERY_REQUEST_CODE = 100;
    public static final int CAMERA_REQUEST_CODE = 200;
    FirebaseFirestore store;
    DatabaseReference mRef;
    FirebaseAuth mAuth;
    String groupSubject;
    FirebaseStorage cloudStorage;
    StorageReference storageRef;
    ProgressDialog Dialog;
    List<User> list;
    Uri ImageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_group_subject);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        list = (List<User>) getIntent().getSerializableExtra("final");
        mRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        cloudStorage = FirebaseStorage.getInstance();
        store = FirebaseFirestore.getInstance();
        Dialog = new ProgressDialog(this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,4);
        binding.participantsDrawerRecycler.setLayoutManager(layoutManager);
        participantsGridLayoutAdapter adapter = new participantsGridLayoutAdapter(list,this);
        binding.participantsDrawerRecycler.setAdapter(adapter);
        binding.participantsDrawerRecycler.setHasFixedSize(true);
        int participants = list.size();
        binding.participants.setText("Participants: "+participants);
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.groupIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetPicker();
            }
        });
        binding.confirmGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.groupSubject.getText().toString().isEmpty()){
                    Toast toast = Toast.makeText(getApplicationContext(),"Please Enter a subject",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else{
                    createGroup(ImageUri);
                }

            }
        });

    }

    private void createGroup(Uri imageUri) {
        //show progress bar here
        Dialog.setMessage("creating group please wait....");
        Dialog.setCanceledOnTouchOutside(false);
        Dialog.show();
        groupSubject = binding.groupSubject.getText().toString().trim();
        //group image upload here
    if(!(imageUri == null)){
        uploadFirebaseBucket(imageUri,groupSubject);
    }else{
        //firebase database group creation here
        String photoUrl = "";
        if(groupSubject.isEmpty()){
            saveGroupInfo(groupSubject,photoUrl);
        }else{
            saveGroupInfo(groupSubject,photoUrl);
        }

    }


    }

    private void showBottomSheetPicker() {
        bottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_picker,null);
        TextView title = view.findViewById(R.id.bottomSheetTitle);
        title.setText("Group Icon");
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
    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent,"select profile image"),IMAGE_GALLERY_REQUEST_CODE);
    }
    private void openCamera() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null){
            ImageUri = data.getData();
            // upload to Firebase
            //change the bitmap or photo of the profile when the user chooses a photo
             try{
                 Bitmap map = MediaStore.Images.Media.getBitmap(getContentResolver(),ImageUri);
                 binding.groupIcon.setImageBitmap(map);
             }catch(Exception e){
               e.printStackTrace();
                 Toast.makeText(getApplicationContext(),"An Error Occurred",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast toast = Toast.makeText(getApplicationContext(),"No photo Selected",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }
    }
    private String getFileExtension(Uri imageUri) {
        ContentResolver resolver = getContentResolver();
        MimeTypeMap map = MimeTypeMap.getSingleton();
        return map.getExtensionFromMimeType(resolver.getType(imageUri));
    }
    private void uploadFirebaseBucket(Uri imageUri, final String groupSubject) {
        if(imageUri != null){

            storageRef = cloudStorage.getReference().child("images/groups/" + System.currentTimeMillis() + "." + getFileExtension(ImageUri));
            storageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while(!uriTask.isSuccessful());
                    Uri uploadedImgUrl = uriTask.getResult();

                    final String downloadUrl = String.valueOf(uploadedImgUrl);
                          saveGroupInfo(groupSubject,downloadUrl);
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

    private void saveGroupInfo(final String groupSubject, final String photoUrl) {
        store.collection("Users").document(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                final String userId = documentSnapshot.get("userId").toString();
                String name = documentSnapshot.get("userName").toString();
                String phone = documentSnapshot.get("userPhone").toString();
                String email = documentSnapshot.get("userEmail").toString();
                String photo = documentSnapshot.get("userPhotoUrl").toString();
                String description = documentSnapshot.get("description").toString();



                User current = new User(userId,name,email,phone);
                final GroupUser groupCreator =  new GroupUser(userId,name,email,phone,photo,description,true,true);
                final String currentKey =  mRef.child("Groups").push().getKey();
                List<User> admin = new ArrayList<>();
                admin.add(current);

                final Group info = new Group(photoUrl,currentKey,groupSubject);
                store.collection("Groups").document(currentKey).set(info).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //loop to add data to firebase

                        for(User current: list){
                            mRef.child("GroupList").child(current.getUserId()).child(currentKey).push().setValue(info).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                        //Add the participants to each

                        store.collection("Groups").document(currentKey).collection("Participants").document(mAuth.getCurrentUser().getUid()).set(groupCreator).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                for(User current: list){
                                    GroupUser groupMember =  new GroupUser(current.getUserId(),current.getUserName(),current.getUserEmail(),current.getUserPhone(),current.getUserPhotoUrl(),current.getDescription(),false,false);
                                    store.collection("Groups").document(currentKey).collection("Participants").document(current.getUserId()).set(groupMember).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Dialog.dismiss();
                                            if(e instanceof FirebaseNetworkException){
                                                Toast.makeText(getApplicationContext(),"network Error please check your network!",Toast.LENGTH_SHORT).show();
                                                return;
                                            }else{
                                                Toast.makeText(getApplicationContext(),"There was an Error try again later",Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                        }
                                    });

                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

                        mRef.child("GroupList").child(userId).child(currentKey).push().setValue(info).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onSuccess(Void aVoid) {
                               //Send First Message to group
                                final Chats chat = new Chats(
                                        "group Created",
                                        "ANNOUNCEMENT",
                                        "orgsocial@info.com","orgsocial@info.com");
                                mRef.child("GroupChats").child(currentKey).push().setValue(chat).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Dialog.dismiss();
                                        Toast toast = Toast.makeText(getApplicationContext(),"Group Successfully Created",Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                        Intent intent = new Intent(groupSubjectActivity.this, MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); startActivity(intent);
                                        intent.putExtra("groups","groups");
                                        groupSubjectActivity.this.finish();
                                        startActivity(intent);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                e.printStackTrace();
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle Exceptions here
                        Dialog.dismiss();
                        if(e instanceof FirebaseNetworkException){

                            Toast.makeText(getApplicationContext(),"Network Error please check your connection",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"An Error Occurred",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle Exceptions here
                Dialog.dismiss();
                if(e instanceof FirebaseNetworkException){
                    Toast.makeText(getApplicationContext(),"Network Error please check your connection",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"An Error Occurred",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
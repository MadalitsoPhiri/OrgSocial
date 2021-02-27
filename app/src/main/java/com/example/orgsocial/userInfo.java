package com.example.orgsocial;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.orgsocial.MainActivity;
import com.example.orgsocial.R;
import com.example.orgsocial.authentication.LoginActivity;
import com.example.orgsocial.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.Map;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;


public class userInfo extends Fragment {
    private CircularImageView profile;
    private FloatingActionButton profile_btn;
    private EditText about;
    private Button next;
    private FirebaseFirestore store;
    private FirebaseAuth mAuth;
    private String description;
    private ProgressDialog dialog;
    private BottomSheetDialog bottomSheetDialog;
    FirebaseStorage cloudStorage;
    StorageReference storageRef;
    private int IMAGE_GALLERY_REQUEST_CODE = 100;
    Uri ImageUri;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog =new ProgressDialog(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        next = view.findViewById(R.id.next);
        about = view.findViewById(R.id.about);
        profile_btn = view.findViewById(R.id.profileImg_fab);
        profile = view.findViewById(R.id.profile_image);
        store = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        cloudStorage = FirebaseStorage.getInstance();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doUpdate();

            }
        });

        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetPicker();
            }
        });

        return view;
    }
    private void showBottomSheetPicker() {
        bottomSheetDialog = new BottomSheetDialog(getActivity());
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
                Toast.makeText(getActivity(),"Camera",Toast.LENGTH_SHORT).show();
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

    private void openCamera() {
        Toast.makeText(getActivity(),"Camera",Toast.LENGTH_SHORT).show();
    }


    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == IMAGE_GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null){
            ImageUri = data.getData();

            // upload to Firebase
            uploadFirebaseBucket(ImageUri);

        }else{
            Toast.makeText(getActivity(),"An Error Occurred.",Toast.LENGTH_SHORT).show();
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent,"select profile image"),IMAGE_GALLERY_REQUEST_CODE);
    }

    private void doUpdate() {
        //dialogbox for when loading
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle("Processing");
        dialog.setMessage("Please Wait");
        dialog.show();
        if (!about.getText().toString().trim().isEmpty()) {
            description = about.getText().toString().trim();
        }else{
            description = "Hey there iam using Orgsocial";
        }


        //updating the firebasefirestore
        FirebaseUser current = mAuth.getCurrentUser();
        if(current != null){
            store.collection("Users").document(current.getUid()).update("description",description).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        dialog.dismiss();
                        startActivity(new Intent(getActivity(), MainActivity.class));
                    }else{
                        dialog.dismiss();
                        try{
                            throw task.getException();
                        }catch (FirebaseNetworkException e){
                            Toast.makeText(getActivity(),"Network Error.Please Check your connection",Toast.LENGTH_LONG).show();
                        }catch(Exception e){
                            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }

    }
    private String getFileExtension(Uri imageUri) {
        ContentResolver resolver = getActivity().getContentResolver();
        MimeTypeMap map = MimeTypeMap.getSingleton();
        return map.getExtensionFromMimeType(resolver.getType(imageUri));
    }
    private void uploadFirebaseBucket(Uri uri) {
        if(uri != null){
            //show progress bar here
            dialog.setMessage("Uploading photo please wait....");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
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
                            store.collection("Users").document(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    String photoUrl = documentSnapshot.get("userPhotoUrl").toString();
                                    Toast.makeText(getContext(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    next.setText("next");
                                    Glide.with(getContext()).load(photoUrl).into(profile);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    dialog.dismiss();
                                    if(e instanceof FirebaseNetworkException){
                                        Toast.makeText(getActivity(),"Network Error. Please Check your connection",Toast.LENGTH_LONG).show();
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
                            dialog.dismiss();
                            if(e instanceof FirebaseNetworkException){
                                Toast.makeText(getActivity(),"Network Error. Please Check your connection",Toast.LENGTH_LONG).show();
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
                    dialog.dismiss();
                    if(e instanceof FirebaseNetworkException){
                        Toast.makeText(getActivity(),"Network Error. Please Check your connection",Toast.LENGTH_LONG).show();
                    }else{
                        e.printStackTrace();
                        Log.d("ProfileActivity", "onFailure: "+ e.getMessage());
                    }
                }
            });
        }
    }
}
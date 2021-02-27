package com.example.orgsocial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.orgsocial.databinding.ActivityEditGroupAdminsBinding;
import com.example.orgsocial.databinding.ActivityEditGroupSubjectBinding;
import com.example.orgsocial.databinding.ActivityEditGroupSubjectBindingImpl;
import com.example.orgsocial.databinding.ActivityGroupSubjectBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditGroupSubject extends AppCompatActivity {
    FirebaseFirestore store;
    ActivityEditGroupSubjectBinding binding;
    ProgressDialog Dialog;
    String groupId,groupDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_group_subject);
        Intent intent = getIntent();
        groupId = intent.getStringExtra("groupId");
        groupDescription = intent.getStringExtra("subject");
        store = FirebaseFirestore.getInstance();
        Dialog = new ProgressDialog(this);
        if(groupDescription != null){
            binding.groupSubjectInput.setText(groupDescription);
        }
        binding.emojiIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Emoji Clicked",Toast.LENGTH_SHORT).show();
            }
        });
        binding.ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.groupSubjectInput.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please add your update text",Toast.LENGTH_LONG).show();
                }else{
                    updateDescription();
                }

            }
        });
        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void updateDescription() {

        Dialog.setMessage("updating group Subject please wait....");
        Dialog.setCanceledOnTouchOutside(false);
        Dialog.show();
        store.collection("Groups").document(groupId).update("groupSubject",binding.groupSubjectInput.getText().toString().trim()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Dialog.dismiss();
                Toast.makeText(getApplicationContext(),"updated successfully",Toast.LENGTH_SHORT).show();
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Dialog.dismiss();
                if(e instanceof FirebaseNetworkException){
                    Toast.makeText(getApplicationContext(),"Failed to update please check your internet.",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
}
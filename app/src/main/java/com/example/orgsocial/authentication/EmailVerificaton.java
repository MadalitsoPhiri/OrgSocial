package com.example.orgsocial.authentication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orgsocial.MainActivity;
import com.example.orgsocial.R;
import com.example.orgsocial.userInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;


public class EmailVerificaton extends Fragment {
    private static final String TAG = "EMAIL VERIFICATION";
    private Button verify;
    private TextView email_text;
    private String email,password;
    private FirebaseFirestore store;
    private String login_message;
    private FirebaseAuth mAuth;
    private ProgressDialog loginDialog ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginDialog = new ProgressDialog(getContext());


         Bundle bundle = getArguments();
        email = bundle.getString("email");
        password = bundle.getString("password");


        try{
            login_message = bundle.getString("header");
        }catch (Exception e){
            Log.d(TAG, "onCreate: "+ e.getMessage());
            login_message = null;
        }




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_email_verificaton, container, false);
        mAuth = FirebaseAuth.getInstance();
        store = FirebaseFirestore.getInstance();
        email_text = view.findViewById(R.id.custom_email_text);
        if(login_message != null){
            email_text.setText(login_message+email);
        }else{
            email_text.setText("Email was sent to "+ email);
        }

        verify = view.findViewById(R.id.verify);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginDialog.setTitle("Verifying");
                loginDialog.setMessage("Checking your Credentials");
                loginDialog.setCanceledOnTouchOutside(false);
                loginDialog.show();

               mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           // Check if the email is verified
                           if(mAuth.getCurrentUser().isEmailVerified()){
                               store.collection("Users").document(mAuth.getCurrentUser().getUid()).update("verified",true).addOnCompleteListener(new OnCompleteListener<Void>() {
                                   @Override
                                   public void onComplete(@NonNull Task<Void> task) {
                                       if(task.isSuccessful()){
                                           loginDialog.setMessage("Logging in");
                                           loginDialog.dismiss();
                                           getFragmentManager().beginTransaction().replace(R.id.container, new userInfo(),"userInfo").commit();
                                       }else{
                                           loginDialog.dismiss();
                                           try{
                                               throw task.getException();
                                           }catch (FirebaseNetworkException e){
                                               Toast.makeText(getContext(),"Network Error.Please Check your connection",Toast.LENGTH_LONG).show();
                                           }catch(Exception e){
                                               Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                                           }
                                       }
                                   }
                               });



                           }else{
                               loginDialog.dismiss();
                               Toast.makeText(getContext(),"Please Verify your email",Toast.LENGTH_LONG).show();
                           }

                       }else{
                           try {
                               throw task.getException();
                           }
                           catch (FirebaseNetworkException e){
                               loginDialog.dismiss();
                               Toast.makeText(getContext(),"Network Error. Check your connection",Toast.LENGTH_LONG).show();

                           }

                           catch (Exception e) {
                               loginDialog.dismiss();
                               Log.e(TAG, e.getMessage());

                           }
                       }
                   }
               });



            }
        });
        return view;
    }
}
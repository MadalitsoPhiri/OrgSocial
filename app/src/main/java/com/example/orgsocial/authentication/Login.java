package com.example.orgsocial.authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orgsocial.MainActivity;
import com.example.orgsocial.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

public class Login extends Fragment {
  Button login_button;
    private FirebaseAuth mAuth;
    private ProgressDialog loginDialog;
    private FirebaseFirestore db;
    private boolean password_fieldIsVisible = false;
  TextView emailError,passwordError,signup_text;
  EditText Email,Password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        //Initialize the widgets
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        loginDialog = new ProgressDialog(getContext());
        loginDialog.setCanceledOnTouchOutside(false);
        Email = view.findViewById(R.id.login_email);
        Password = view.findViewById(R.id.login_password);
        emailError = view.findViewById(R.id.emailError);
        passwordError = view.findViewById(R.id.passwordError);
        login_button = view.findViewById(R.id.email_login_button);
        signup_text = view.findViewById(R.id.sigup_text);


        SpannableString myString = new SpannableString("We may collect information you provide us in order to show you targeted ads as described in our Privacy Policy");
        SpannableString myString1 = new SpannableString("Dont have an account? Sign up");

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
               Toast.makeText(getContext(),"Privacy policy clicked",Toast.LENGTH_LONG).show();
            }
        };

        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                getFragmentManager().beginTransaction().replace(R.id.container,new Signup()).commit();
            }
        };

        //For Click
        myString.setSpan(clickableSpan,96,110, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        myString1.setSpan(clickableSpan2,22,29,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //For UnderLine
       // myString.setSpan(new UnderlineSpan(),96,110,0);

        //For Bold
        myString.setSpan(new StyleSpan(Typeface.BOLD),96,110,0);
        myString1.setSpan(new StyleSpan(Typeface.BOLD),22,29,0);
        //Finally you can set to textView.

        TextView textView = view.findViewById(R.id.privacy_sentence);
        textView.setText(myString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        signup_text.setText(myString1);
        signup_text.setMovementMethod(LinkMovementMethod.getInstance()
        );
        //set listeners
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(Email.getText().toString().trim().toLowerCase(),Password.getText().toString().trim());
            }
        });


        //add toogle listener for password visibility
        Password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (Password.getRight() - Password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        //toogle password password visibility
                        if(password_fieldIsVisible){
                            password_fieldIsVisible = false;
                            Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            Password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                        }else {
                            password_fieldIsVisible = true;
                            Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            Password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0);

                        }

                        return true;
                    }
                }
                return false;
            }
        });


        return view;
    }

    private void login(final String email, final String password) {
        loginDialog.setTitle("Login");
        loginDialog.setMessage("Checking your Credentials");
        loginDialog.setCanceledOnTouchOutside(false);
        loginDialog.show();

        //Verify login credentials if valid login, if not let the user know why
        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
               // Log.d(TAG,""+task.getResult().getSignInMethods().size());
                if(task.isSuccessful()) {
                    if (task.getResult().getSignInMethods().size() == 0) {
                        passwordError.setText("An account with those credentials does not exist\n Please sign up");
                        loginDialog.dismiss();

                        passwordError.setVisibility(View.VISIBLE);
                        Email.setBackgroundResource(R.drawable.btn3);
                        Password.setBackgroundResource(R.drawable.btn3);
                    } else {
                        Email.setBackgroundResource(R.drawable.btn1);
                        Password.setBackgroundResource(R.drawable.btn1);
                        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    loginDialog.setMessage("Logging in Please wait...");
                                    if(mAuth.getCurrentUser().isEmailVerified()){
                                        loginDialog.setMessage("Logging in");
                                        loginDialog.dismiss();
                                        UpdateUI(mAuth.getCurrentUser());
                                    }else{
                                        loginDialog.dismiss();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("email",email);
                                        bundle.putString("password",password);
                                        bundle.putString("header","An Email was previously sent to ");
                                        Fragment emailVerification = new EmailVerificaton();
                                        emailVerification.setArguments(bundle);
                                        getFragmentManager().beginTransaction().replace(R.id.container,emailVerification).commit();
                                    }

                                } else {
                                    try {
                                        throw task.getException();
                                    }
                                    catch (FirebaseNetworkException e){
                                        loginDialog.dismiss();
                                        Toast.makeText(getContext(),"Network Error. Check your connection",Toast.LENGTH_LONG).show();

                                    }catch(FirebaseAuthInvalidCredentialsException e){
                                        loginDialog.dismiss();
                                        passwordError.setText("Invalid password");
                                        Password.setBackgroundResource(R.drawable.btn3);
                                        passwordError.setVisibility(View.VISIBLE);
                                    }

                                    catch (Exception e) {
                                        loginDialog.dismiss();
                                        Log.e(TAG, e.getMessage());

                                    }
                                }
                            }
                        });
                    }
                }else{
                    //Handle commomn Errors
                    try {
                        throw task.getException();
                    }catch (FirebaseNetworkException e){
                        loginDialog.dismiss();
                        Toast.makeText(getContext(),"Network Error. Check your connection",Toast.LENGTH_LONG).show();
                    }

                    catch (Exception e) {
                        Log.e(TAG, e.getMessage());

                    }
                }
            }

            private void UpdateUI(FirebaseUser currentUser) {
                startActivity(new Intent(getActivity(), MainActivity.class));
                loginDialog.dismiss();
                Toast.makeText(getActivity(),"Logged in",Toast.LENGTH_LONG).show();
                getActivity().finish();

            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e instanceof FirebaseNetworkException){
                    loginDialog.dismiss();
                    Toast.makeText(getContext(),"Network Error. Check your connection",Toast.LENGTH_LONG).show();
                } else {
                    Log.e(TAG, e.getMessage());
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
package com.example.orgsocial.authentication;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.orgsocial.R;
import com.example.orgsocial.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class Signup extends Fragment {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private boolean password_fieldIsVisible = false;
    private boolean password_fieldIsVisible2 = false;
    private ProgressDialog loginDialog;
    private Button signupBtn;
    private TextView usernameError, emailError, phoneError, confirmPasswordError, passwordError, login_text;
    private EditText username, email, password, confirmPassword, phone;
    private String pattern = "(?=.*[a-zA-Z0-9@#$%^&+=?.!*])(?=\\S+$).{8,}";//password validation pattern
    private String pattern1 = "^[a-zA-Z0-9\\s.]+$";//name validation pattern
    private String pattern_phone = "\\d{10}";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        //intialize widgets
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        loginDialog = new ProgressDialog(getContext());
        loginDialog.setCanceledOnTouchOutside(false);
        signupBtn = view.findViewById(R.id.signup_button);
        username = view.findViewById(R.id.name);
        phone = view.findViewById(R.id.phone);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        confirmPassword = view.findViewById(R.id.confirm_password);
        usernameError = view.findViewById(R.id.nameInputError);
        emailError = view.findViewById(R.id.emailInputError);
        phoneError = view.findViewById(R.id.phoneInputError);
        confirmPasswordError = view.findViewById(R.id.confirmInputError);
        passwordError = view.findViewById(R.id.passwordInputError);
        login_text = view.findViewById(R.id.login_text);

        SpannableString myString = new SpannableString("We may collect information you provide us in order to show you targeted ads as described in our Privacy Policy");
        SpannableString myString1 = new SpannableString("Already have an account? Login");

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Toast.makeText(getContext(),"Privacy policy clicked",Toast.LENGTH_LONG).show();
            }
        };

        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                getFragmentManager().beginTransaction().replace(R.id.container, new Login()).commit();
            }
        };

        //For Click
        myString.setSpan(clickableSpan,96,110, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        myString1.setSpan(clickableSpan2,25,30,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //For UnderLine
        // myString.setSpan(new UnderlineSpan(),96,110,0);

        //For Bold
        myString.setSpan(new StyleSpan(Typeface.BOLD),96,110,0);
        myString1.setSpan(new StyleSpan(Typeface.BOLD),25,30,0);
        //Finally you can set to textView.

        TextView textView = view.findViewById(R.id.privacy_sentence);
        textView.setText(myString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        login_text.setText(myString1);
        login_text.setMovementMethod(LinkMovementMethod.getInstance()
        );
        //Set Listeners
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup(username, phone, email, password, confirmPassword);
            }
        });



        //add toogle listener for password visibility
        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (password.getRight() - password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        //toogle password password visibility
                        if(password_fieldIsVisible){
                            password_fieldIsVisible = false;
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                        }else {
                            password_fieldIsVisible = true;
                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0);

                        }

                        return true;
                    }
                }
                return false;
            }
        });

        //add toogle listener for confirm password visibility
        confirmPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (confirmPassword.getRight() - confirmPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        //toogle password password visibility
                        if(password_fieldIsVisible2){
                            password_fieldIsVisible2 = false;
                            confirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            confirmPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                        }else {
                            password_fieldIsVisible2 = true;
                            confirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            confirmPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0);

                        }

                        return true;
                    }
                }
                return false;
            }
        });

        return view;
    }

    private void signup(EditText name, EditText phone, final EditText email, EditText password, EditText confirmPassword) {

        //Toast.makeText(getContext(), "Signing up", Toast.LENGTH_LONG).show();

        EditText[] input = {name, phone, email, password, confirmPassword};

        final String Name = name.getText().toString().trim();
        final String Phone = phone.getText().toString().trim();
        final String Email = email.getText().toString().trim().toLowerCase();
        final String Password = password.getText().toString().trim();
        final String Confirm_Password = confirmPassword.getText().toString().trim();


        int index = 0;
        boolean NameError = false;
        boolean PhoneError = false;
        boolean EmailError = false;
        boolean PasswordError = false;
        boolean ConfirmPasswordError = false;
        //Verify if signup credentials are okay if not let user know why

        for (EditText current : input) {
            if (index == 0) {
                //validate name
                if (!Name.matches(pattern1) && !TextUtils.isEmpty(Name)) {
                   //Name does not match pattern
                    input[0].setBackgroundResource(R.drawable.btn3);
                    usernameError.setText("username can contain only numbers, letters, a dot or whitespace");
                    usernameError.setVisibility(View.VISIBLE);
                    NameError = true;
                } else if(TextUtils.isEmpty(Name)){
                    //Name Empty
                    usernameError.setText("username can't be empty");
                    usernameError.setVisibility(View.VISIBLE);
                    input[0].setBackgroundResource(R.drawable.btn3);
                    NameError = true;
                }else {
                    NameError = false;
                    usernameError.setVisibility(View.GONE);
                    input[0].setBackgroundResource(R.drawable.btn1);
                }
            } else if (index == 1) {
                //validate phone
                if (!Phone.matches(pattern_phone)) {
                    //Name does not match pattern
                    input[1].setBackgroundResource(R.drawable.btn3);
                    phoneError.setText("Please enter a ten digit number starting with 0");
                    phoneError.setVisibility(View.VISIBLE);
                    PhoneError = true;
                } else if(TextUtils.isEmpty(Phone)){
                    //Name Empty
                    input[1].setBackgroundResource(R.drawable.btn3);
                    phoneError.setText("Please enter a phone number");
                    phoneError.setVisibility(View.VISIBLE);
                    PhoneError = true;
                }else {
                    PhoneError = false;
                    phoneError.setVisibility(View.GONE);
                    input[1].setBackgroundResource(R.drawable.btn1);
                }
            } else if (index == 2) {
                //validate email
                if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    //Name does not match pattern
                    emailError.setText("Please enter a valid email address");
                    emailError.setVisibility(View.VISIBLE);
                    input[2].setBackgroundResource(R.drawable.btn3);
                    EmailError = true;
                } else if(TextUtils.isEmpty(Email)){
                    //Name Empty
                    input[2].setBackgroundResource(R.drawable.btn3);
                    emailError.setText("Please enter an email address");
                    emailError.setVisibility(View.VISIBLE);
                    EmailError = true;
                }else {
                    EmailError = false;
                    emailError.setVisibility(View.GONE);
                    input[2].setBackgroundResource(R.drawable.btn1);
                }
            } else if (index == 3) {
                // validate password
                if (!Password.matches(pattern) && !TextUtils.isEmpty(Password)) {
                    //Name does not match pattern
                    input[3].setBackgroundResource(R.drawable.btn3);
                    passwordError.setText("Password should be at least 8 characters long with \natleast one digit, one lower case, one upper case, \n one special character and no spaces");
                    passwordError.setVisibility(View.VISIBLE);
                    PasswordError = true;
                } else if(TextUtils.isEmpty(Password)){
                    //Name Empty
                    passwordError.setText("Password is required");
                    passwordError.setVisibility(View.VISIBLE);
                    input[3].setBackgroundResource(R.drawable.btn3);
                    PasswordError = true;
                }else {
                    PasswordError = false;
                    passwordError.setVisibility(View.GONE);
                    input[3].setBackgroundResource(R.drawable.btn1);
                }
            } else if (index == 4) {
                // validate confirm password
                if (!Confirm_Password.equals(Password)) {
                    //Name does not match pattern
                    input[4].setBackgroundResource(R.drawable.btn3);
                    confirmPasswordError.setText("Your Passwords don't match");
                    confirmPasswordError.setVisibility(View.VISIBLE);
                    ConfirmPasswordError = true;
                }else if(TextUtils.isEmpty(Confirm_Password)){
                    //Name Empty
                    confirmPasswordError.setText("Please Confirm Your Password");
                    confirmPasswordError.setVisibility(View.VISIBLE);
                    input[4].setBackgroundResource(R.drawable.btn3);
                    ConfirmPasswordError = true;
                } else {
                    ConfirmPasswordError = false;
                    confirmPasswordError.setVisibility(View.GONE);
                    input[4].setBackgroundResource(R.drawable.btn1);
                }
            }


            index++;
        }

        if (!NameError && !PhoneError && !EmailError && !PasswordError && !ConfirmPasswordError) {
            //send data to server for registration
            loginDialog.setTitle("Signing up");
            loginDialog.setMessage("Checking your Credentials");
            loginDialog.setCanceledOnTouchOutside(false);
            loginDialog.show();
            //Initiate the progress bar here
            mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        String userUuid = mAuth.getCurrentUser().getUid();
                        // User(String userId, String userName, String userEmail, String userPhone, String userPhotoUrl, String description, List<String> groups, boolean status,boolean verified)
                        User user = new User(userUuid,Name,Email,Phone,"","",false,false);
                        db.collection("Users").document(user.getUserId()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(),"Signup succsessfull",Toast.LENGTH_LONG).show();

                                mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Bundle bundle = new Bundle();
                                                        bundle.putString("email",Email);
                                                        bundle.putString("password",Password);
                                                        Fragment verification = new EmailVerificaton();
                                                        verification.setArguments(bundle);
                                                        loginDialog.dismiss();
                                                        getFragmentManager().beginTransaction().replace(R.id.container,verification).commit();
                                                    }else{
                                                        Exception e = task.getException();
                                                        if(e instanceof FirebaseNetworkException){
                                                            Toast.makeText(getContext(),"Signup failed please check your connection and try again",Toast.LENGTH_LONG).show();
                                                        }else{
                                                            Toast.makeText(getContext(),"An Error Occurred please try again",Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                }
                                            });
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
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                               if(e instanceof FirebaseNetworkException){
                                   loginDialog.dismiss();
                                   Toast.makeText(getContext(),"Network Error. Check your connection",Toast.LENGTH_LONG).show();
                               } else {
                                   loginDialog.dismiss();
                                   Log.e(TAG, e.getMessage());
                                   Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                               }


                            }
                        });

                    }else{
                        //Handle multiple firebaseAuth Exceptions
                        try {
                            throw task.getException();
                        }catch (FirebaseNetworkException e){
                            loginDialog.dismiss();
                            Toast.makeText(getContext(),"Network Error. Check your connection",Toast.LENGTH_LONG).show();
                        }catch (FirebaseAuthUserCollisionException e){
                            loginDialog.dismiss();
                            emailError.setText("User with this email already registered");
                            email.setBackgroundResource(R.drawable.btn3);
                            emailError.setVisibility(View.VISIBLE);
                        }

                        catch (Exception e) {
                            loginDialog.dismiss();
                            Log.e(TAG, e.getMessage());

                        }

                    }
                }
            });
        }


    }


}


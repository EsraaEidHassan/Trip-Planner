package com.app.egh.tripplanner.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.egh.tripplanner.R;
import com.app.egh.tripplanner.activities.HomeActivity;
import com.app.egh.tripplanner.activities.SignInActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignUpFragment extends Fragment {
    //TextView username;
    TextView password;
    TextView confirmPassword;
    TextView email;
    Button signUp;
    TextView signIn;

    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        email = view.findViewById(R.id.email_signup);
        password = view.findViewById(R.id.password_signup);
        confirmPassword = view.findViewById(R.id.confirmPassword_signup);
        signUp = view.findViewById(R.id.signupBtn);
        signIn = view.findViewById(R.id.goto_signIn);
        signIn.setPaintFlags(signIn.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        progressDialog = new ProgressDialog(getContext());

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            // user already signed in
            getActivity().finish();
            Intent intent = new Intent(getContext(), HomeActivity.class);
            startActivity(intent);
        }

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                Intent intent = new Intent(getContext(), SignInActivity.class);
                startActivity(intent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
                //Intent intent = new Intent(getContext(), HomeActivity.class);
                //startActivity(intent);
            }
        });

        return view;
    }

    private void registerUser(){

        String email = this.email.getText().toString().trim();
        String password = this.password.getText().toString();
        String confirmPassword = this.confirmPassword.getText().toString();

        if(email.equalsIgnoreCase(""))
        {
            this.email.setHint("please enter email");//it gives user to hint
            this.email.setError("please enter email");//it gives user to info message //use any one //
            return;
        }
        if(password.equalsIgnoreCase(""))
        {
            this.password.setHint("please enter password");//it gives user to hint
            this.password.setError("please enter password");//it gives user to info message //use any one //
            return;
        }
        if(!confirmPassword.equals(password))
        {
            this.confirmPassword.setHint("passwords don't match");//it gives user to hint
            this.confirmPassword.setError("passwords don't match");//it gives user to info message //use any one //
            return;
        }

        if(isNetworkConnected()) {

            progressDialog.setMessage("Registering User ... ");
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        getActivity().finish();
                        Intent intent = new Intent(getContext(), HomeActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getContext(), "Can't Register this user !", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
            });
        }else {
           // Toast.makeText(getContext(), "Please check your connection !", Toast.LENGTH_LONG).show();

            Snackbar snackbar= Snackbar.make(getView(),  "Check your internet connection ", Snackbar.LENGTH_LONG);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(getContext().getResources().getColor(R.color.colorAccent));
            snackbar.show();
        }

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

}

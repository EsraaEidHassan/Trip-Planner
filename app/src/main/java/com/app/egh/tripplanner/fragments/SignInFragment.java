package com.app.egh.tripplanner.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.egh.tripplanner.R;
import com.app.egh.tripplanner.activities.HomeActivity;
import com.app.egh.tripplanner.activities.SignUpActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;


public class SignInFragment extends Fragment {


    TextView emailAddress;
    TextView password;
    Button signIn;
    Button fbLogin;
    TextView createAccount;
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private GoogleSignInClient mGoogleSignInClient;


    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;

    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        emailAddress = view.findViewById(R.id.emailText);
        password = view.findViewById(R.id.passwordText);
        signIn = view.findViewById(R.id.signInBtn);
        fbLogin = view.findViewById(R.id.fbLoginBtn);
        createAccount = view.findViewById(R.id.goto_signup);
        createAccount.setPaintFlags(createAccount.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

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
                signin();
                //getActivity().finish();
                //make sure of user credential then login
                //Intent intent = new Intent(getContext(), HomeActivity.class);
                //startActivity(intent);
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                Intent intent = new Intent(getContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        fbLogin.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
               // Toast.makeText(getContext(), "Sign In with facebook", Toast.LENGTH_LONG).show();
                GoogleSignInOptions gsn = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();


                mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gsn);

                // [START initialize_auth]
                mAuth = FirebaseAuth.getInstance();
                // [END initialize_auth]

            }
        });
        return view;
    }

    private void signin(){

        String email = this.emailAddress.getText().toString().trim();
        String password = this.password.getText().toString();

        if(email.equalsIgnoreCase(""))
        {
            this.emailAddress.setHint("please enter email");//it gives user to hint
            this.emailAddress.setError("please enter email");//it gives user to info message //use any one //
            return;
        }
        if(password.equalsIgnoreCase(""))
        {
            this.password.setHint("please enter password");//it gives user to hint
            this.password.setError("please enter password");//it gives user to info message //use any one //
            return;
        }

        if(isNetworkConnected()) {

            progressDialog.setMessage("Registering User ... ");
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        getActivity().finish();
                        Intent intent = new Intent(getContext(), HomeActivity.class);
                        startActivity(intent);

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Can't sign in !", Toast.LENGTH_LONG).show();

                    }
                }
            });
        }else {
            Toast.makeText(getContext(), "Please check your connection !", Toast.LENGTH_LONG).show();
        }

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

}

package com.app.egh.tripplanner.fragments;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
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


public class SignInFragment extends Fragment {


    TextView emailAddress;
    TextView password;
    Button signIn;
    Button fbLogin;
    TextView createAccount;
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
        createAccount = view.findViewById(R.id.createAccountText);
        createAccount.setPaintFlags(createAccount.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //make sure of user credential then login
                Intent intent = new Intent(getContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        fbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Sign In with facebook", Toast.LENGTH_LONG).show();

            }
        });
        return view;
    }

}

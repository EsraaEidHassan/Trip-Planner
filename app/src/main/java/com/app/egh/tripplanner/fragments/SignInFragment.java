package com.app.egh.tripplanner.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.app.egh.tripplanner.R;
import com.app.egh.tripplanner.activities.HomeActivity;


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

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //make sure of user credential then login
                Intent intent = new Intent(getContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}

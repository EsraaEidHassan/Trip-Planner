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
import com.app.egh.tripplanner.activities.SignInActivity;


public class SignUpFragment extends Fragment {
    TextView username;
    TextView password;
    TextView confirmPassword;
    TextView email;
    Button signUp;
    TextView signIn;
    Button fbSignup;
    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        username = view.findViewById(R.id.username_signup);
        email = view.findViewById(R.id.email_signup);
        password = view.findViewById(R.id.password_signup);
        confirmPassword = view.findViewById(R.id.confirmPassword_signup);
        signUp = view.findViewById(R.id.signupBtn);
        signIn = view.findViewById(R.id.signInFromSignUp);
        signIn.setPaintFlags(signIn.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        fbSignup = view.findViewById(R.id.fbSignup);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SignInActivity.class);
                startActivity(intent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        fbSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Sign up with facebook", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

}

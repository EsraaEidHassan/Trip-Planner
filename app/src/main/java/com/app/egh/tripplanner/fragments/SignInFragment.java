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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.egh.tripplanner.R;
import com.app.egh.tripplanner.activities.HomeActivity;
import com.app.egh.tripplanner.activities.SignUpActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.app.egh.tripplanner.data.model.Adapter;
import com.app.egh.tripplanner.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInFragment extends Fragment {

    private static final String TAG = "SignInFragment";

    TextView emailAddress;
    TextView password;
    Button signIn;
    Button googleLogin;
    TextView createAccount;

    private GoogleApiClient mGoogleApiClient;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    private static final int RC_SIGN_IN = 9001;

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
        googleLogin = view.findViewById(R.id.fbLoginBtn);
        createAccount = view.findViewById(R.id.goto_signup);
        createAccount.setPaintFlags(createAccount.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        progressDialog = new ProgressDialog(getContext());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
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

        googleLogin.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
               // Toast.makeText(getContext(), "Sign In with facebook", Toast.LENGTH_LONG).show();
                if(isNetworkConnected()) {

                    startGoogleConfigurations();
                    signInWithGoogle();
                }
                else {
                    Toast.makeText(getContext(), "Please check your connection !", Toast.LENGTH_LONG).show();
                }

            }
        });
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("onActivityResult child");
        System.out.println("RequestCode: " + requestCode);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                System.out.println("Account ");
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
            }
        }

        else{
            System.out.println("Problem with request code");
        }
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

            progressDialog.setMessage("Sign in User ... ");
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        getTripsFromFireBase();
                        progressDialog.dismiss();
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

    private void getTripsFromFireBase(){
        final Adapter dbAdapter = new Adapter(getContext());
        dbAdapter.deleteAllTrips();

        if(isNetworkConnected()) {

            //progressDialog.setMessage("Retrieving Trips ... ");
            //progressDialog.show();

             ValueEventListener userListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get Post object and use the values to update the UI
                    firebaseUser = firebaseAuth.getCurrentUser();
                    User user = dataSnapshot.child(firebaseUser.getUid()).getValue(User.class);
                    if (user != null) {
                        if(user.trips != null) {
                            for (int i = 0; i < user.trips.size(); i++) {
                                dbAdapter.insert_trip(user.trips.get(i));
                            }
                            getActivity().finish();
                            Intent intent = new Intent(getContext(), HomeActivity.class);
                            startActivity(intent);
                        }
                    }
                    //progressDialog.dismiss();
                    // ...
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                    //progressDialog.dismiss();
                    Toast.makeText(getContext(), "Cannot load trips !", Toast.LENGTH_LONG).show();
                }
            };
            databaseReference.addListenerForSingleValueEvent(userListener);
        }else{
            Toast.makeText(getContext(), "Please check your connection !", Toast.LENGTH_LONG).show();
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        progressDialog.setMessage("Sign in User ... ");
        progressDialog.show();
        System.out.println("firebaseAuthWithGoogle");
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                           // Toast.makeText(getContext(), "signInWithCredential:success", Toast.LENGTH_LONG).show();

                            getTripsFromFireBase();
                            progressDialog.dismiss();

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Can't sign in !", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void startGoogleConfigurations(){


            GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .enableAutoManage(getActivity() /* FragmentActivity */, null /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                    .build();


    }

    private void signInWithGoogle(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        getActivity().startActivityForResult(signInIntent, RC_SIGN_IN);
    }
}

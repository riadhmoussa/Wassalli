package com.moussa.wassalliapp;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidstudy.networkmanager.Monitor;
import com.androidstudy.networkmanager.Tovuti;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText et_mail,et_password;
    String statusCompte,typeUser;
    DatabaseReference mDatabase;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_login);
        et_mail = (EditText)findViewById(R.id.EDTUser);
        et_password = (EditText)findViewById(R.id.EDTpassword);

        mAuth = FirebaseAuth.getInstance();

    }

    public void signup(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUp.class);
        startActivity(intent);
    }

    private void updateUI(FirebaseUser user) {
        String Email=user.getEmail();
        String Uid= user.getUid();
        // Toast.makeText(getApplicationContext(),Uid.toString(),Toast.LENGTH_LONG).show();
    }


    public void login(View view) {
        showProgressDialog();
        mAuth.signInWithEmailAndPassword(et_mail.getText().toString(), et_password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            //Log.d(TAG, "signInWithEmail:success");
                            final FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            mDatabase= FirebaseDatabase.getInstance().getReference();

                            id=mDatabase.child("Users").child(user.getUid().toString()).getKey();

                            mDatabase.child("Users").child(id).child("statusCompte").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    statusCompte=snapshot.getValue().toString();
                                    if(statusCompte.equals("Active")){
                                        mDatabase.child("Users").child(id).child("typeUser").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot snapshot) {
                                                typeUser=snapshot.getValue().toString();
                                                if(typeUser.equals("Citoyen")){
                                                    SharedRef sharedRef=new SharedRef(getApplicationContext());
                                                    sharedRef.SaveData(user.getUid(),"Citoyen");
                                                    Intent intent = new Intent(getApplicationContext(), Dashboard_Citoyen.class);
                                                    finish();
                                                    startActivity(intent);
                                                    hideProgressDialog();
                                                }else{
                                                    mDatabase.child("Users").child(id).child("Expired").addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            String sDate1 = dataSnapshot.getValue().toString();
                                                            Date now = new Date();
                                                            Date date1 = null;
                                                            try {
                                                                date1 = new SimpleDateFormat("yyyy/MM/dd").parse(sDate1);
                                                            } catch (ParseException e) {
                                                                e.printStackTrace();
                                                            }

                                                            if (date1.after(now)) {
                                                                SharedRef sharedRef=new SharedRef(getApplicationContext());
                                                                sharedRef.SaveData(user.getUid(),"Transporteur");
                                                                Intent intent = new Intent(getApplicationContext(), Dashboard_Transporteur.class);
                                                                startService(new Intent(Login.this, MyServiceClient.class));

                                                                finish();
                                                                startActivity(intent);
                                                                hideProgressDialog();
                                                            } else {

                                                                hideProgressDialog();
                                                                Toast.makeText(getApplicationContext(),"Your account is Expired",Toast.LENGTH_LONG).show();
                                                            }

                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    });

                                                }

                                            }
                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                            }
                                        });
                                    }else{
                                        hideProgressDialog();
                                        Toast.makeText(getApplicationContext(),"Sorry your account is locked now please check administration",Toast.LENGTH_LONG).show();
                                    }

                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });


                        } else {
                            // If sign in fails, display a message to the user.
                            hideProgressDialog();

                            AlertDialog.Builder alert= new AlertDialog.Builder(Login.this);
                            alert.setMessage("Your password is incorrect")
                                    .setIcon(android.R.drawable.stat_notify_error)
                                    .setTitle("Alert")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //do some thing
                                        }
                                    })
                                    .show();
                        }

                        // ...
                    }
                });
    }

    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

}

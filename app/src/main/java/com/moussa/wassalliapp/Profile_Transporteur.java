package com.moussa.wassalliapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;
import com.squareup.picasso.Picasso;

public class Profile_Transporteur extends AppCompatActivity {

    DatabaseReference database;

    TextView tv_name,tv_phone,tv_email,tv_adresse;
    ImageView imageView;
    Button btnClaim;
    public static String Uid="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__transporteur);
        tv_name=(TextView)findViewById(R.id.tv_name);
        tv_phone=(TextView)findViewById(R.id.tv_phone);
        tv_email=(TextView)findViewById(R.id.tv_email);
        tv_adresse=(TextView)findViewById(R.id.tv_adresse);
        imageView = (ImageView) findViewById(R.id.iv_profile);
        btnClaim = (Button)findViewById(R.id.btnClaim);

        Bundle extras = getIntent().getExtras();

        Uid = extras.getString("Uid");

        database = FirebaseDatabase.getInstance().getReference();

        database.child("Users").child(Uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tv_name.setText(dataSnapshot.child("First_Name_LastName").getValue().toString());
                tv_phone.setText(dataSnapshot.child("numeroTelephone").getValue().toString());
                tv_email.setText(dataSnapshot.child("Email").getValue().toString());
                tv_adresse.setText(dataSnapshot.child("Adresse").getValue().toString());
                Picasso.get()
                        .load(dataSnapshot.child("ImageUrl").getValue().toString())
                        .into(imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnClaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.FragmentManager fm= getFragmentManager();
                PopClaim popClaim=new PopClaim();
                popClaim.show(fm,"Show fragment");

            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}

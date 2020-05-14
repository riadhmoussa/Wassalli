package com.moussa.wassalliapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class CitoyerProfile extends AppCompatActivity {

    String str = "";

    DatabaseReference database;

    TextView tvName,txTel,txEmail,tvAdresse,DateB;
    ImageView ivprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citoyer_profile);


        tvName = (TextView)findViewById(R.id.tvName);
        txTel = (TextView)findViewById(R.id.txTel);
        txEmail = (TextView)findViewById(R.id.txEmail);
        tvAdresse = (TextView)findViewById(R.id.tvAdresse);
        DateB = (TextView)findViewById(R.id.DateB);

        ivprofile = (ImageView) findViewById(R.id.ivprofile);


        database = FirebaseDatabase.getInstance().getReference();
        SharedRef sharedRef = new SharedRef(this);
        database.child("Users").child(sharedRef.LoadDataUID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tvName.setText(dataSnapshot.child("First_Name_LastName").getValue().toString());
                txTel.setText(dataSnapshot.child("numeroTelephone").getValue().toString());
                txEmail.setText(dataSnapshot.child("Email").getValue().toString());
                tvAdresse.setText(dataSnapshot.child("Adresse").getValue().toString());
                DateB.setText(dataSnapshot.child("DateBirth").getValue().toString());
                Picasso.get()
                        .load(dataSnapshot.child("ImageUrl").getValue().toString())
                        .into(ivprofile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu_citoyen, menu);




        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.Disconnection:
                SharedRef sharedRef = new SharedRef(getApplicationContext());
                FirebaseAuth.getInstance().signOut();

                sharedRef.Deconnection();
                //Toast.makeText(this,"Seeting",Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

package com.moussa.wassalliapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Transporteur_Settings extends AppCompatActivity {

    EditText oldEmail;
    private FirebaseAuth auth;
    String str = "";

    CardForm cardForm2;
    Button buy2;
    AlertDialog.Builder alertBuilderr;
    SharedRef sharedRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transporteur__settings);
        oldEmail = (EditText)findViewById(R.id.oldEmail);
        auth = FirebaseAuth.getInstance();

        cardForm2 = findViewById(R.id.card_form2);
        buy2 = findViewById(R.id.btnAch);

        cardForm2.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("Un SMS est requis sur ce numéro")
                .setup(Transporteur_Settings.this);
        cardForm2.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);

        buy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardForm2.isValid()) {
                    alertBuilderr = new AlertDialog.Builder(Transporteur_Settings.this);
                    alertBuilderr.setTitle("Confirmer avant l'achat");
                    alertBuilderr.setMessage("Numéro de carte: " + cardForm2.getCardNumber() + "\n" +
                            "Date d'expiration de la carte: " + cardForm2.getExpirationDateEditText().getText().toString() + "\n" +
                            "Carte CVV: " + cardForm2.getCvv() + "\n" +
                            "Code postal: " + cardForm2.getPostalCode() + "\n" +
                            "Numéro de téléphone: " + cardForm2.getMobileNumber());

                    alertBuilderr.setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            Calendar cal = Calendar.getInstance();
                            cal.add(Calendar.YEAR, 1); // to get previous year add -1
                            Date nextYear = cal.getTime();
                            DateFormat df= new SimpleDateFormat("yyyy/MM/dd");


                            sharedRef=new SharedRef(getApplicationContext());

                            // Write a message to the database
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference ref = database.getReference("Users").child(sharedRef.LoadDataUID()).child("Expired");

                            ref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    // This method is called once with the initial value and again
                                    // whenever data at this location is updated.
                                    String value = dataSnapshot.getValue(String.class);
                                    String[] parts = value.split("/");
                                    String part1 = parts[0];
                                    String part2 = parts[1];
                                    String part3 = parts[2];
                                    int val = Integer.parseInt(part1)+1;
                                    String fin = val+"/"+part2+"/"+part3;

                                    DatabaseReference myref= FirebaseDatabase.getInstance().getReference();

                                    myref.child("Users").child(sharedRef.LoadDataUID()).child("Expired").setValue(fin);
                                    finish();
                                    Toast.makeText(Transporteur_Settings.this, "Merci d'avoir acheté", Toast.LENGTH_LONG).show();


                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                    // Failed to read value

                                }
                            });

                        }
                    });

                    AlertDialog alertDialog = alertBuilderr.create();
                    alertDialog.show();

                } else {
                    Toast.makeText(Transporteur_Settings.this, "Veuillez remplir le formulaire", Toast.LENGTH_LONG).show();
                }
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

    public void ResetPassword(View view) {
        if (!oldEmail.getText().toString().trim().equals("")) {
            auth.sendPasswordResetEmail(oldEmail.getText().toString().trim())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Transporteur_Settings.this, "Reset password. email is sent!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Transporteur_Settings.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            oldEmail.setError("Entrez votre e-mail");
        }

    }
}

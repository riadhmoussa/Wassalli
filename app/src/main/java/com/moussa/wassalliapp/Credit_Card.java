package com.moussa.wassalliapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Credit_Card extends AppCompatActivity {

    CardForm cardForm;
    Button buy;
    AlertDialog.Builder alertBuilder;
    Spinner spinner1,spinner2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_credit__card);
        cardForm = findViewById(R.id.card_form);
        buy = findViewById(R.id.btnBuy);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);


        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("Un SMS est requis sur ce numéro")
                .setup(Credit_Card.this);
        cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardForm.isValid()) {
                    alertBuilder = new AlertDialog.Builder(Credit_Card.this);
                    alertBuilder.setTitle("Confirmer avant l'achat");
                    alertBuilder.setMessage("Numéro de carte: " + cardForm.getCardNumber() + "\n" +
                            "Date d'expiration de la carte: " + cardForm.getExpirationDateEditText().getText().toString() + "\n" +
                            "Carte CVV: " + cardForm.getCvv() + "\n" +
                            "Code postal: " + cardForm.getPostalCode() + "\n" +
                            "Numéro de téléphone: " + cardForm.getMobileNumber());

                    alertBuilder.setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            Calendar cal = Calendar.getInstance();
                            Date today = cal.getTime();
                            cal.add(Calendar.YEAR, 1); // to get previous year add -1
                            Date nextYear = cal.getTime();
                            DateFormat df= new SimpleDateFormat("yyyy/MM/dd");

                            GlobalInfo.TypePermis=String.valueOf(spinner1.getSelectedItem());
                            GlobalInfo.TypeVehicule=String.valueOf(spinner2.getSelectedItem());

                            GlobalInfo.Expired = df.format(nextYear).toString();
                            GlobalInfo.UpdatesInfo(GlobalInfo.UserID,
                                    GlobalInfo.imgUrl,
                                    GlobalInfo.statusCompte,
                                    GlobalInfo.typeUser,
                                    GlobalInfo.First_Name_LastName,
                                    GlobalInfo.numeroTelephone,
                                    GlobalInfo.Email,
                                    GlobalInfo.Adresse,
                                    GlobalInfo.Expired,
                                    GlobalInfo.DateBirth,
                                    GlobalInfo.TypePermis,
                                    GlobalInfo.TypeVehicule
                            );
                            finish();
                            Intent intent=new Intent(getApplicationContext(), Login.class);
                            startActivity(intent);
                            Toast.makeText(Credit_Card.this, "Merci d'avoir acheté", Toast.LENGTH_LONG).show();
                        }
                    });

                    AlertDialog alertDialog = alertBuilder.create();
                    alertDialog.show();

                } else {
                    Toast.makeText(Credit_Card.this, "Veuillez remplir le formulaire", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}

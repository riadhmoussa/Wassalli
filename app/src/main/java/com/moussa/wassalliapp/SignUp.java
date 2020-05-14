package com.moussa.wassalliapp;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.androidstudy.networkmanager.Monitor;
import com.androidstudy.networkmanager.Tovuti;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


import android.os.Bundle;

public class SignUp extends AppCompatActivity {

    EditText et_mail,et_password,et_nom_prenom,et_numTel,et_adresse;
    DatePicker picker;

    private FirebaseAuth mAuth;

    ImageView ivUserImage ;

    public static String Type="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_sign_up);
        et_mail = (EditText)findViewById(R.id.et_mail);
        et_password = (EditText)findViewById(R.id.et_password);
        et_nom_prenom = (EditText)findViewById(R.id.et_nom_prenom);
        ivUserImage = (ImageView)findViewById(R.id.ivUserImage);
        et_numTel = (EditText)findViewById(R.id.et_numTel);
        et_adresse = (EditText)findViewById(R.id.et_adresse);
        picker =(DatePicker)findViewById(R.id.timePicker1);


        mAuth = FirebaseAuth.getInstance();

        ivUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckUserPermsions();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // updateUI(currentUser);
    }


    private void updateUI(FirebaseUser user) {
        String Email=user.getEmail();
        String Uid= user.getUid();
        // Toast.makeText(getApplicationContext(),Uid.toString(),Toast.LENGTH_LONG).show();
    }


    public void BuRegister(View view) {
        showProgressDialog();
        mAuth.createUserWithEmailAndPassword(et_mail.getText().toString(), et_password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseStorage storage=FirebaseStorage.getInstance();

                            // Create a storage reference from our app
                            StorageReference storageRef = storage.getReferenceFromUrl("gs://wassalli-84ff0.appspot.com");
                            DateFormat df = new SimpleDateFormat("ddMMyyHHmmss");
                            Date dateobj = new Date();

                            // System.out.println(df.format(dateobj));
// Create a reference to "mountains.jpg"
                            final String ImagePath= df.format(dateobj) +".jpg";
                            StorageReference mountainsRef = storageRef.child("images/"+ ImagePath);
                            ivUserImage.setDrawingCacheEnabled(true);
                            ivUserImage.buildDrawingCache();
                            // Bitmap bitmap = imageView.getDrawingCache();
                            BitmapDrawable drawable=(BitmapDrawable)ivUserImage.getDrawable();
                            Bitmap bitmap =drawable.getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] data = baos.toByteArray();
                            UploadTask uploadTask = mountainsRef.putBytes(data);

                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                    Toast.makeText(getApplicationContext(),taskSnapshot.getUploadSessionUri().toString(),Toast.LENGTH_LONG).show();
                                    String downloadUrl = taskSnapshot.getStorage().getName().toString();
                                    String name="";



                                    // Toast.makeText(getApplicationContext(),downloadUrl,Toast.LENGTH_LONG).show();
                /*try {
                    //for space with name
                    name = java.net.URLEncoder.encode( etName.getText().toString() , "UTF-8");
                    downloadUrl= java.net.URLEncoder.encode(downloadUrl , "UTF-8");
                } catch (UnsupportedEncodingException e) {

                }
                //TODO:  login and register
                String url="http://10.0.2.2/~hussienalrubaye/twitterserver/register.php?first_name="+name+"&email="+etEmail.getText().toString()+"&password="+etPassword.getText().toString()+"&picture_path="+ downloadUrl;

                new MyAsyncTaskgetNews().execute(url);
                //
*/             //Toast.makeText(getApplicationContext(),"createUserWithEmail:success",Toast.LENGTH_LONG).show();
                                    // Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);

                                    //Toast.makeText(getApplicationContext(),downloadUrl,Toast.LENGTH_LONG).show();

                                    String lien="https://firebasestorage.googleapis.com/v0/b/wassalli-84ff0.appspot.com/o/images%2F"+downloadUrl+"?alt=media&token=b334dbd0-3830-46e1-849d-0805155c1044";
                                    GlobalInfo.UserID=user.getUid().toString();
                                    GlobalInfo.imgUrl=lien;
                                    GlobalInfo.statusCompte="underRevision";
                                    GlobalInfo.typeUser=Type;
                                    GlobalInfo.First_Name_LastName=et_nom_prenom.getText().toString();
                                    GlobalInfo.Email = et_mail.getText().toString();
                                    GlobalInfo.numeroTelephone = et_numTel.getText().toString();
                                    GlobalInfo.Adresse = et_adresse.getText().toString();
                                    GlobalInfo.DateBirth =  picker.getDayOfMonth()+"/"+picker.getFirstDayOfWeek()+"/"+picker.getYear();
                                    if(GlobalInfo.typeUser.equalsIgnoreCase("Transporteur")){
                                        hideProgressDialog();
                                        Intent intent = new Intent(getApplicationContext(),Credit_Card.class);
                                        startActivity(intent);

                                    }else{
                                        GlobalInfo.Expired = null;
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

                                        hideProgressDialog();

                                        finish();
                                        Intent intent=new Intent(getApplicationContext(), Login.class);
                                        startActivity(intent);
                                    }

                                }
                            });





                        } else {

                            hideProgressDialog();
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(),"createUserWithEmail:failure",Toast.LENGTH_LONG).show();
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
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




    void CheckUserPermsions(){
        if ( Build.VERSION.SDK_INT >= 23){
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED  ){
                requestPermissions(new String[]{
                                android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return ;
            }
        }

        LoadImage();// init the contact list

    }

    //get acces to location permsion
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    int RESULT_LOAD_IMAGE=346;
    void LoadImage(){
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            ivUserImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_pirates:
                if (checked)

                    Type="Transporteur";
                break;
            case R.id.radio_ninjas:
                if (checked)
                    Type="Citoyen";
                break;
        }
    }
}

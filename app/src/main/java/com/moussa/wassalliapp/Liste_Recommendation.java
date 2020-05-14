package com.moussa.wassalliapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Liste_Recommendation extends AppCompatActivity {

    ListView lv_recommendation;
    ArrayList<Recommandation> listnewsData = new ArrayList<Recommandation>();
    MyCustomAdapter myadapter;
    ArrayList<Recommandation> arrayList;
    ArrayList<Order> arrayList_Order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste__recommendation);



        lv_recommendation = (ListView)findViewById(R.id.lv_recommendation);



        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference =    mFirebaseDatabase.getReference("Recommandation");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList=new ArrayList<>();
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    // str = str+ "+ "+childDataSnapshot.child("Name").getValue();
                    SharedRef sharedRef=new SharedRef(getApplicationContext());
                    if(childDataSnapshot.child("Uid_Transporteur").getValue().toString().equalsIgnoreCase(sharedRef.LoadDataUID())){

                        listnewsData.add(new Recommandation(childDataSnapshot.child("UidPost").getValue().toString(),
                                childDataSnapshot.child("Uid_Transporteur").getValue().toString(),
                                childDataSnapshot.child("Name").getValue().toString()));
                    }

                /*  listnewsData.add(new Order(childDataSnapshot.child("Uid").getValue().toString(),
                            childDataSnapshot.child("Name").getValue().toString(),
                            childDataSnapshot.child("Date").getValue().toString(),
                            childDataSnapshot.child("LocationStart").getValue().toString(),
                            childDataSnapshot.child("LocationEnd").getValue().toString(),
                            childDataSnapshot.child("Description").getValue().toString(),
                            childDataSnapshot.getKey().toString(),
                            childDataSnapshot.child("Adresse_depart").getValue().toString(),
                            childDataSnapshot.child("Adresse_final").getValue().toString()));*/



                    // Log.v(TAG,""+ childDataSnapshot.getKey()); //displays the key for the node
                    // Log.v(TAG,""+ childDataSnapshot.child(--ENTER THE KEY NAME eg. firstname or email etc.--).getValue());   //gives the value for given keyname
                }

              /*  FirebaseDatabase riadh = FirebaseDatabase.getInstance();
                DatabaseReference moussa =    riadh.getReference("Orders");
                moussa.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        arrayList_Order=new ArrayList<>();
                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {


                            arrayList_Order.add(new Order(childDataSnapshot.child("Uid").getValue().toString(),
                                    childDataSnapshot.child("Name").getValue().toString(),
                                    childDataSnapshot.child("Date").getValue().toString(),
                                    childDataSnapshot.child("LocationStart").getValue().toString(),
                                    childDataSnapshot.child("LocationEnd").getValue().toString(),
                                    childDataSnapshot.child("Description").getValue().toString(),
                                    childDataSnapshot.getKey().toString(),
                                    childDataSnapshot.child("Adresse_depart").getValue().toString(),
                                    childDataSnapshot.child("Adresse_final").getValue().toString()));
                            // str = str+ "+ "+childDataSnapshot.child("Name").getValue();
                /*    Toast.makeText(getApplicationContext(),childDataSnapshot.child("Uid").getValue().toString(),Toast.LENGTH_LONG).show();
                    boolean bol = false;
                    String str="";
                    int i = 0;
                    while (bol==false){
                        if(arrayList.get(i).Uid.equalsIgnoreCase(childDataSnapshot.child("Uid").getValue().toString())){
                            str=arrayList.get(i).Content;
                            bol=true;
                        }
                    }

                    if(bol==true){
                        listnewsData.add(new AdapterItems(childDataSnapshot.child("Uid").getValue().toString(),
                                childDataSnapshot.child("Name").getValue().toString(),
                                childDataSnapshot.child("Date").getValue().toString(),
                                childDataSnapshot.child("Description").getValue().toString(),
                                childDataSnapshot.child("Adresse_depart").getValue().toString(),
                                childDataSnapshot.child("Adresse_final").getValue().toString(),
                                str
                        ));

                    }*/





                            // Log.v(TAG,""+ childDataSnapshot.getKey()); //displays the key for the node
                            // Log.v(TAG,""+ childDataSnapshot.child(--ENTER THE KEY NAME eg. firstname or email etc.--).getValue());   //gives the value for given keyname
                      /*  }
                        Toast.makeText(getApplicationContext(),"hello world!",Toast.LENGTH_LONG).show();
                        for(int j=0;j<arrayList_Order.size();j++){
                            boolean bol = false;
                            String str="";
                            int i = 0;
                            while (bol==false){
                                if(arrayList.get(i).Uid.equalsIgnoreCase(arrayList_Order.get(j).Uid_post_)){
                                    str=arrayList.get(i).Content;
                                    bol=true;
                                }
                            }

                            if(bol==true){
                                listnewsData.add(new AdapterItems(arrayList_Order.get(j).Uid_post_,
                                        arrayList_Order.get(j).Name_post_,
                                        arrayList_Order.get(j).Date_post_,
                                        arrayList_Order.get(j).Description_post_,
                                        arrayList_Order.get(j).Adresse_depart_,
                                        arrayList_Order.get(j).Adresse_final_,
                                        str
                                ));

                            }
                        }
                        listnewsData.add(new AdapterItems(null,
                                null,
                                null,
                                null,
                    null,
                                null,
                                null
                        ));
                        myadapter=new MyCustomAdapter(listnewsData);
                        lv_recommendation.setAdapter(myadapter);//intisal with data
                        // myadapter=new MyCustomAdapter(listnewsData);
                        //  lv.setAdapter(myadapter);//intisal with data
                        //Toast.makeText(getApplicationContext(),str,Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/


                myadapter=new MyCustomAdapter(listnewsData);
               lv_recommendation.setAdapter(myadapter);//intisal with data
                //Toast.makeText(getApplicationContext(),str,Toast.LENGTH_LONG).show();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    public  String Search(String str){
        for (int i=0;i<arrayList.size();i++){
            if(arrayList.get(i).Uid.equalsIgnoreCase(str)){
                return arrayList.get(i).Content;
            }
        }
        return null;
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
    private class MyCustomAdapter extends BaseAdapter {
        public ArrayList<Recommandation> listnewsDataAdpater ;

        public MyCustomAdapter(ArrayList<Recommandation>  listnewsDataAdpater) {
            this.listnewsDataAdpater=listnewsDataAdpater;
        }


        @Override
        public int getCount() {
            return listnewsDataAdpater.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            LayoutInflater mInflater = getLayoutInflater();
            View myView = mInflater.inflate(R.layout.news_ticket, null);

            final   Recommandation s = listnewsDataAdpater.get(position);

             final TextView etDesc=( TextView)myView.findViewById(R.id.etDesc);
            final TextView txtJobTitle=( TextView)myView.findViewById(R.id.txtJobTitle);
            final TextView txt_news_date=( TextView)myView.findViewById(R.id.txt_news_date);
            final TextView tv_Prop=( TextView)myView.findViewById(R.id.tv_Prop);

            etDesc.setText("Votre proposition : "+s.Content);
            FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference =    mFirebaseDatabase.getReference("Orders").child(s.Uid);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String Name=dataSnapshot.child("Name").getValue().toString();
                    String Description = dataSnapshot.child("Description").getValue().toString();
                    String Date = dataSnapshot.child("Date").getValue().toString();
                    tv_Prop.setText("Description de Publication : "+Description);
                    txtJobTitle.setText(Name);
                    txt_news_date.setText(Date);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            myView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    Query applesQuery = ref.child("Recommandation");
                    applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            AlertDialog.Builder alertBuilderDelet;

                            for (final DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                alertBuilderDelet = new AlertDialog.Builder(Liste_Recommendation.this);
                                alertBuilderDelet.setTitle("Confirmer la suppression de la recommandation");
                                alertBuilderDelet.setMessage("Voulez-vous vraiment supprimer l'élément");
                                alertBuilderDelet.setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        appleSnapshot.getRef().removeValue();
                                        listnewsData.remove(listnewsData.get(position));
                                        myadapter.notifyDataSetChanged();
                                        Toast.makeText(Liste_Recommendation.this, "Supprimé avec succès", Toast.LENGTH_LONG).show();
                                    }
                                });
                                alertBuilderDelet.setNegativeButton("Ignorer", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                    }
                                });
                                AlertDialog alertDialog = alertBuilderDelet.create();
                                alertDialog.show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Log.e(TAG, "onCancelled", databaseError.toException());
                        }
                    });
                    return false;
                }
            });

            return myView;
        }

    }


}

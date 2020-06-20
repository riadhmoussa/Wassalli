package com.moussa.wassalliapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidstudy.networkmanager.Monitor;
import com.androidstudy.networkmanager.Tovuti;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Article_History extends AppCompatActivity {
    ListView lsNews;
    ArrayList<Order> listnewsData;
    MyCustomAdapter myadapter;
    SharedRef  sharedRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article__history);
        listnewsData = new ArrayList<Order>();


        //add data and view it

        lsNews=(ListView)findViewById(R.id.LVNews);




        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        sharedRef=new SharedRef(getApplicationContext());
        DatabaseReference databaseReference =    mFirebaseDatabase.getReference("Orders");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listnewsData.removeAll(listnewsData);
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    // str = str+ "+ "+childDataSnapshot.child("Name").getValue();
                    if(childDataSnapshot.child("Uid").getValue().toString().equalsIgnoreCase(sharedRef.LoadDataUID())){
                        listnewsData.add(new Order(childDataSnapshot.getKey().toString(),
                                childDataSnapshot.child("Name").getValue().toString(),
                                childDataSnapshot.child("Date").getValue().toString(),
                                childDataSnapshot.child("LocationStart").getValue().toString(),
                                childDataSnapshot.child("LocationEnd").getValue().toString(),
                                childDataSnapshot.child("Description").getValue().toString(),
                                childDataSnapshot.getKey(),
                                childDataSnapshot.child("Adresse_depart").getValue().toString(),
                                childDataSnapshot.child("Adresse_final").getValue().toString(),
                                childDataSnapshot.child("Confirmed").getValue().toString()));

                    }

                    // Log.v(TAG,""+ childDataSnapshot.getKey()); //displays the key for the node
                    // Log.v(TAG,""+ childDataSnapshot.child(--ENTER THE KEY NAME eg. firstname or email etc.--).getValue());   //gives the value for given keyname
                }
                myadapter=new MyCustomAdapter(listnewsData);
                lsNews.setAdapter(myadapter);//intisal with data
                //Toast.makeText(getApplicationContext(),str,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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

    private class MyCustomAdapter extends BaseAdapter {
        public ArrayList<Order> listnewsDataAdpater ;

        public MyCustomAdapter(ArrayList<Order>  listnewsDataAdpater) {
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

            final   Order s = listnewsDataAdpater.get(position);

            TextView txtJobTitle=( TextView)myView.findViewById(R.id.txtJobTitle);
            txtJobTitle.setText(s.Name_post_);
            TextView etDesc=(TextView)myView.findViewById(R.id.etDesc);
            etDesc.setText(s.Description_post_);
            TextView txt_news_date=(TextView)myView.findViewById(R.id.txt_news_date);
            txt_news_date.setText(s.Date_post_);

            myView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),Master_details_client.class);
                    intent.putExtra("Uid", s.Uid_post_);
                    intent.putExtra("Name_post", s.Name_post_);
                    intent.putExtra("Date_post", s.Date_post_);
                    intent.putExtra("Start_post", s.Start_post_);
                    intent.putExtra("End_post", s.End_post_);
                    intent.putExtra("Desc",s.Description_post_);
                    intent.putExtra("Confirmed",s.Confirmed_);
                    startActivity(intent);
                }
            });

            myView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    Query applesQuery = ref.child("Orders");

                    applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (final DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                AlertDialog.Builder alertBuilderDelet;

                                alertBuilderDelet = new AlertDialog.Builder(Article_History.this);
                                alertBuilderDelet.setTitle("Confirmer la suppression de la publication");
                                alertBuilderDelet.setMessage("êtes-vous sûr de supprimer l'élément");
                                alertBuilderDelet.setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        appleSnapshot.getRef().removeValue();
                                        listnewsData.remove(listnewsData.get(position));
                                        myadapter.notifyDataSetChanged();
                                        Toast.makeText(Article_History.this, "Supprimé avec succès", Toast.LENGTH_LONG).show();
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
          /*  lsNews.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    Query applesQuery = ref.child("Orders");

                    applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (final DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                alertBuilderDelet = new AlertDialog.Builder(Article_History.this);
                                alertBuilderDelet.setTitle("Confirm Delete Order");
                                alertBuilderDelet.setMessage("are you sure to delete item");
                                alertBuilderDelet.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        appleSnapshot.getRef().removeValue();
                                        listnewsData.remove(listnewsData.get(position));
                                        myadapter.notifyDataSetChanged();
                                        Toast.makeText(Article_History.this, "Delete succssufly", Toast.LENGTH_LONG).show();
                                    }
                                });
                                alertBuilderDelet.setNegativeButton("Ignore", new DialogInterface.OnClickListener() {
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
            });*/



            // TextView txtJobTitle=( TextView)myView.findViewById(R.id.txt_user_name);
            // WebView webView=( WebView)myView.findViewById(R.id.wv_map);


         /*   webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            webView.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);
            webView.getSettings().setMediaPlaybackRequiresUserGesture(false);

            if (Build.VERSION.SDK_INT >= 21) {
                webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
            }

            if (android.os.Build.VERSION.SDK_INT < 16) {
                webView.setBackgroundColor(0x00000000);
            } else {
                webView.setBackgroundColor(Color.argb(1, 0, 0, 0));
            }

            webView.setWebViewClient(new WebViewClient() {

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        view.loadUrl(request.getUrl().toString());
                    }
                    return super.shouldOverrideUrlLoading(view, request);
                }

                @Override
                public void onPageStarted(WebView webview, String url, Bitmap favicon) {
                    super.onPageStarted(webview, url, favicon);
                    webview.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onPageFinished(WebView webview, String url) {

                    webview.setVisibility(View.VISIBLE);
                    super.onPageFinished(webview, url);

                }
            });
            webView.setWebChromeClient(new WebChromeClient());

            webView.loadUrl("https://maps.google.com/maps?saddr=43.0054446,-87.9678884&daddr=42.9257104,-88.0508355");
*/
            //txtJobTitle.setText(s.);

            return myView;
        }

    }


    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);


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

package com.moussa.wassalliapp;

import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import androidx.annotation.RequiresApi;



public class PopClaim extends DialogFragment implements View.OnClickListener {
    View view;
    Button BuDoneClaim;
    EditText etContentClaim;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.pop_claim,container,false);


        BuDoneClaim=(Button)view.findViewById(R.id.BuDoneClaim);
        etContentClaim=(EditText)view.findViewById(R.id.etContentClaim);
        BuDoneClaim.setOnClickListener(this);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        Claim claim = new Claim();
        SharedRef sharedRef=new SharedRef(getContext());
        claim.Content_Claim = etContentClaim.getText().toString();
        claim.Uid_Client = sharedRef.LoadDataUID();
        claim.Uid_Transporteur = Profile_Transporteur.Uid ;
        claim.UpdatesInfo(claim.Uid_Client,claim.Content_Claim,claim.Uid_Transporteur);
        this.dismiss();
    }
}

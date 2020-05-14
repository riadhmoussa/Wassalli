package com.moussa.wassalliapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class Recherche_Mission  extends DialogFragment implements View.OnClickListener{
    View view;
    DatePicker datePicker;
    EditText etLoocatin;
    Button buDone;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.pop_recherche,container,false);
        datePicker=(DatePicker)view.findViewById(R.id.PickDate);
        etLoocatin=(EditText)view.findViewById(R.id.etLocation);
        buDone=(Button)view.findViewById(R.id.BuDone);
        buDone.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        this.dismiss();
        Liste_Publication publication=(Liste_Publication)getActivity();
        String Date =  datePicker.getDayOfMonth()+"/"+datePicker.getFirstDayOfWeek()+"/"+datePicker.getYear();
        publication.ListePublication(etLoocatin.getText().toString(),Date);

    }
}

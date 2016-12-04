package com.example.chenx2.travelplanner.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.DatePicker;

import com.example.chenx2.travelplanner.OnMessageFragmentAnswer;

import java.util.Calendar;

public  class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    private OnMessageFragmentAnswer onMessageFragmentAnswer = null;
    private String type;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof  OnMessageFragmentAnswer){
            onMessageFragmentAnswer = (OnMessageFragmentAnswer)context;
        }else{
            throw new RuntimeException("Not implement interface");
        }
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        type = getArguments().getString(AttractionFragment.DAY);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        onMessageFragmentAnswer.onDateSelected(year,month,day,type);
    }
}
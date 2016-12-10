package com.example.chenx2.travelplanner.fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TimePicker;

import com.example.chenx2.travelplanner.OnMessageFragmentAnswer;
import com.example.chenx2.travelplanner.R;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {
    private OnMessageFragmentAnswer onMessageFragmentAnswer = null;
    String type;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof  OnMessageFragmentAnswer){
            onMessageFragmentAnswer = (OnMessageFragmentAnswer)context;
        }else{
            throw new RuntimeException(context.getString(R.string.not_implement_interfaces));
        }
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        type = getArguments().getString(AttractionFragment.TIME);
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        onMessageFragmentAnswer.onTimeSelected(hourOfDay,minute,type);
    }
}
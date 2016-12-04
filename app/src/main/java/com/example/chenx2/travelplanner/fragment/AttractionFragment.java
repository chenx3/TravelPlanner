package com.example.chenx2.travelplanner.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chenx2.travelplanner.AddPlanActivity;
import com.example.chenx2.travelplanner.AddTripActivity;
import com.example.chenx2.travelplanner.PlanDetail;
import com.example.chenx2.travelplanner.R;
import com.example.chenx2.travelplanner.TripDetail;
import com.example.chenx2.travelplanner.Util;
import com.example.chenx2.travelplanner.adapter.PlanListAdapter;
import com.example.chenx2.travelplanner.data.Plan;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AttractionFragment extends Fragment {
    private TextView pick_time;
    private TextView pick_day;
    private EditText name;
    private EditText address;
    private ImageView icon;
    private Button button;
    private Date date;
    private String type;
    public static final String TIME = "TIME";
    public static final String DAY = "DAY";
    public static final String TAG = "AttractionFragment";
    public Plan plan;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.attraction_fragment_layout, null);
        type = ((AddPlanActivity) getActivity()).type;
        button = (Button) root.findViewById(R.id.attraction_save);
        name = (EditText) root.findViewById(R.id.attraction_name);
        address = (EditText) root.findViewById(R.id.attraction_location);
        pick_time = (TextView) root.findViewById(R.id.attraction_pick_time);
        icon = (ImageView) root.findViewById(R.id.attraction_layout_icon);
        setupIcon();
        setupInitialTime(root);
        setupDatePicker();
        setupSaveButton();
        if (((AddPlanActivity) getActivity()).intent == ((AddPlanActivity) getActivity()).EDIT) {
            plan = ((AddPlanActivity) getActivity()).plan;
            fillUI();
        }
        return root;
    }

    private void fillUI() {
        name.setText(plan.getName());
        if (plan.getAddress() != null) {
            address.setText(plan.getAddress());
        }
        plan.getStartTime().setMonth(plan.getStartTime().getMonth() - 1);
        pick_time.setText(plan.getStartTime().getHours() + ":" + Util.formatMinute(plan.getStartTime().getMinutes()));
        pick_day.setText(new SimpleDateFormat("dd/MM/yyyy").format(plan.getStartTime()));
        date = plan.getStartTime();
    }

    private void setupIcon() {
        if (type.compareTo("Restaurant") == 0) {
            icon.setImageResource(R.drawable.black_ic_restaurant);
        } else if (type.compareTo("Others") == 0) {
            icon.setImageResource(R.drawable.black_ic_note_add);
        }
    }

    private void setupSaveButton() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plan = new Plan();
                plan.setStartLocation(address.getText().toString());
                if (name == null || name.getText().toString().compareTo("") == 0) {
                    name.setError("Name cannot be empty");
                    return;
                } else {
                    plan.setName(name.getText().toString());
                }
                plan.setStartTime(date);
                plan.setType(type);
                plan.setTrip(((AddPlanActivity) getActivity()).trip);
                plan.save();
                Intent result = new Intent();
                if (((AddPlanActivity) getActivity()).intent == AddPlanActivity.EDIT) {
                    result.putExtra(PlanListAdapter.PLAN_ID, ((AddPlanActivity) getActivity()).id);
                    result.putExtra(PlanListAdapter.POSITION_TO_EDIT, ((AddPlanActivity) getActivity()).position);
                }
                result.putExtra(AddTripActivity.KEY_ITEM, plan);
                getActivity().setResult(PlanDetail.RESULT_OK, result);
                getActivity().finish();
            }
        });
    }

    private void setupDatePicker() {
        pick_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(DAY, "START");
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.setArguments(bundle);
                newFragment.show(getChildFragmentManager(), "datePicker");
            }
        });
        pick_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(TIME, "START");
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.setArguments(bundle);
                newFragment.show(getChildFragmentManager(), "timePicker");
            }
        });
    }

    private void setupInitialTime(View root) {
        Calendar c = Calendar.getInstance();
        date = c.getTime();
        int month = c.get(Calendar.MONTH) + 1;
        pick_time.setText(c.get(Calendar.HOUR_OF_DAY) + ":" + Util.formatMinute(c.get(Calendar.MINUTE)));
        pick_day = (TextView) root.findViewById(R.id.attraction_pick_day);
        pick_day.setText(c.get(Calendar.DAY_OF_MONTH) + "/" + month + "/" + c.get(Calendar.YEAR));
    }

    public void onDateSelected(int year, int month, int day, String type) {
        month = month + 1;
        date.setMonth(month);
        date.setYear(year - 1900);
        date.setDate(day);
        if (type.compareTo("START") == 0) {
            pick_day.setText(day + "/" + month + "/" + year);
        }
    }


    public void onTimeSelected(int hourOfDay, int minute, String type) {
        date.setHours(hourOfDay);
        date.setMinutes(minute);
        if (type.compareTo("START") == 0) {
            pick_time.setText(hourOfDay + ":" + Util.formatMinute(minute));
        }
    }
}

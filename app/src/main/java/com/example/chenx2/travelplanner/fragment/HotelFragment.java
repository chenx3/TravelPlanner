package com.example.chenx2.travelplanner.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chenx2.travelplanner.AddPlanActivity;
import com.example.chenx2.travelplanner.AddTripActivity;
import com.example.chenx2.travelplanner.PlanDetail;
import com.example.chenx2.travelplanner.R;
import com.example.chenx2.travelplanner.Util;
import com.example.chenx2.travelplanner.adapter.PlanListAdapter;
import com.example.chenx2.travelplanner.data.Plan;
import com.nightonke.boommenu.Eases.Linear;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HotelFragment extends Fragment {
    public static final String TAG = "HotelFragment";
    private TextView pick_time;
    private TextView pick_end_day;
    private TextView pick_end_time;
    private TextView pick_day;
    private Date date;
    private Date endDate;
    private String type;
    private Button button;
    private TextInputLayout name;
    private TextInputLayout departure_address;
    private TextInputLayout arrival_address;
    private TextInputLayout address;
    private TextInputLayout note;
    private ImageView icon;
    private LinearLayout address_container;
    private LinearLayout departure_container;
    private LinearLayout arrival_container;
    private TextView departure_label;
    private TextView arrival_label;
    private Plan plan;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.hotel_fragment_layout, null);
        type = ((AddPlanActivity) getActivity()).type;
        button = (Button) root.findViewById(R.id.hotel_save);
        name = (TextInputLayout) root.findViewById(R.id.hotel_name);
        address = (TextInputLayout) root.findViewById(R.id.hotel_address);
        departure_address = (TextInputLayout) root.findViewById(R.id.departure_address);
        arrival_address = (TextInputLayout) root.findViewById(R.id.arrival_address);
        note = (TextInputLayout) root.findViewById(R.id.reference_number);
        icon = (ImageView) root.findViewById(R.id.hotel_icon);
        address_container = (LinearLayout) root.findViewById(R.id.hotel_address_container);
        departure_container = (LinearLayout) root.findViewById(R.id.departure_address_container);
        arrival_container = (LinearLayout) root.findViewById(R.id.arrival_address_container);
        departure_label = (TextView) root.findViewById(R.id.departure_time_label);
        arrival_label = (TextView) root.findViewById(R.id.arrival_time_label);
        setupIcon();
        setupNote();
        setupAddress();
        setupNameView();
        setupButton();
        setupTimeDateInitialValue(root);
        setupPicker();
        setupLabel();
        if (((AddPlanActivity) getActivity()).intent == ((AddPlanActivity) getActivity()).EDIT) {
            plan = ((AddPlanActivity) getActivity()).plan;
            fillUI();
        }
        return root;
    }

    private void fillUI() {
        name.getEditText().setText(plan.getName());
        if (plan.getAddress() != null) {
            address.getEditText().setText(plan.getAddress());
        }
        plan.getStartTime().setMonth(plan.getStartTime().getMonth() - 1);
        pick_time.setText(plan.getStartTime().getHours() + ":" + Util.formatMinute(plan.getStartTime().getMinutes()));
        pick_day.setText(new SimpleDateFormat("dd/MM/yyyy").format(plan.getStartTime()));
        date = plan.getStartTime();

        plan.getEndTime().setMonth(plan.getEndTime().getMonth() - 1);
        pick_end_time.setText(plan.getEndTime().getHours() + ":" + Util.formatMinute(plan.getEndTime().getMinutes()));
        pick_end_day.setText(new SimpleDateFormat("dd/MM/yyyy").format(plan.getEndTime()));
        endDate = plan.getEndTime();

        note.getEditText().setText(plan.getNotes());
        departure_address.getEditText().setText(plan.getStartLocation());
        arrival_address.getEditText().setText(plan.getEndLocation());
    }

    private void setupLabel() {
        if (type.compareTo("Hotel") != 0) {
            departure_label.setText("Departure Time");
            arrival_label.setText("Arrival Time");
        }
    }

    private void setupAddress() {
        if (type.compareTo("Hotel") != 0) {
            address_container.setVisibility(View.GONE);
            departure_container.setVisibility(View.VISIBLE);
            arrival_container.setVisibility(View.VISIBLE);
        }
    }

    private void setupNote() {
        if (type.compareTo("Hotel") != 0) {
            note.setHint("Note");
        }
    }

    private void setupNameView() {
        if (type.compareTo("Flight") == 0) {
            name.setHint("Flight Number");
        } else if (type.compareTo("Train") == 0) {
            name.setHint("Train Number");
        } else if (type.compareTo("Transport") == 0) {
            name.setHint("Transport Type");
        }
    }

    private void setupIcon() {
        if (type.compareTo("Flight") == 0) {
            icon.setImageResource(R.drawable.black_ic_action_plane);
        } else if (type.compareTo("Train") == 0) {
            icon.setImageResource(R.drawable.black_ic_action_train);
        } else if (type.compareTo("Transport") == 0) {
            icon.setImageResource(R.drawable.ic_directions_car);
        }
    }

    private void setupButton() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Plan plan = new Plan();
                if (type.compareTo("Hotel") == 0) {
                    plan.setStartLocation(address.getEditText().getText().toString());
                } else {
                    plan.setStartLocation(departure_address.getEditText().getText().toString());
                    plan.setEndLocation(arrival_address.getEditText().getText().toString());
                }
                if (name == null || name.getEditText().getText().toString().compareTo("") == 0) {
                    name.setError("Name cannot be empty");
                    return;
                } else {
                    plan.setName(name.getEditText().getText().toString());
                }
                plan.setStartTime(date);
                plan.setEndTime(endDate);
                plan.setNotes(note.getEditText().getText().toString());
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

    private void setupPicker() {
        pick_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(AttractionFragment.DAY, "START");
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.setArguments(bundle);
                newFragment.show(getChildFragmentManager(), "datePicker");
            }
        });
        pick_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(AttractionFragment.TIME, "START");
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.setArguments(bundle);
                newFragment.show(getChildFragmentManager(), "timePicker");
            }
        });
        pick_end_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(AttractionFragment.DAY, "END");
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.setArguments(bundle);
                newFragment.show(getChildFragmentManager(), "datePicker");
            }
        });
        pick_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(AttractionFragment.TIME, "END");
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.setArguments(bundle);
                newFragment.show(getChildFragmentManager(), "timePicker");
            }
        });
    }

    private void setupTimeDateInitialValue(View root) {
        pick_time = (TextView) root.findViewById(R.id.pick_hotel_time);
        Calendar c = Calendar.getInstance();
        date = c.getTime();
        endDate = c.getTime();
        int month = c.get(Calendar.MONTH)+1;
        pick_time.setText(c.get(Calendar.HOUR_OF_DAY) + ":" + Util.formatMinute(c.get(Calendar.MINUTE)));
        pick_day = (TextView) root.findViewById(R.id.pick_hotel_date);
        pick_day.setText(c.get(Calendar.DAY_OF_MONTH) + "/" + month + "/" + c.get(Calendar.YEAR));
        pick_end_time = (TextView) root.findViewById(R.id.hotel_pick_end_time);
        pick_end_time.setText(c.get(Calendar.HOUR_OF_DAY) + ":" + Util.formatMinute(c.get(Calendar.MINUTE)));
        pick_end_day = (TextView) root.findViewById(R.id.hotel_pick_end_day);
        pick_end_day.setText(c.get(Calendar.DAY_OF_MONTH) + "/" + month + "/" + c.get(Calendar.YEAR));
    }

    public void onDateSelected(int year, int month, int day, String type) {
        month+=1;
        if (type.compareTo("START") == 0) {
            date.setMonth(month);
            date.setYear(year-1900);
            date.setDate(day);
            pick_day.setText(day + "/" + month + "/" + year);
        } else {
            endDate.setMonth(month);
            endDate.setYear(year-1900);
            endDate.setDate(day);
            pick_end_day.setText(day + "/" + month + "/" + year);
        }
    }


    public void onTimeSelected(int hourOfDay, int minute, String type) {
        if (type.compareTo("START") == 0) {
            date.setHours(hourOfDay);
            date.setMinutes(minute);
            pick_time.setText(hourOfDay + ":" + Util.formatMinute(minute));
        } else {
            endDate.setHours(hourOfDay);
            endDate.setMinutes(minute);
            pick_end_time.setText(hourOfDay + ":" + Util.formatMinute(minute));
        }

    }
}
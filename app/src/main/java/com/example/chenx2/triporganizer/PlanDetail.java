package com.example.chenx2.triporganizer;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chenx2.triporganizer.data.Plan;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlanDetail extends AppCompatActivity {
    Plan plan;
    @BindView(R.id.detail_container)
    LinearLayout detail_container;
    @BindView(R.id.plan_address)
    TextView plan_address;
    @BindView(R.id.plan_note)
    TextView plan_note;
    @BindView(R.id.plan_time)
    TextView plan_time;
    @BindView(R.id.plan_expense)
    TextView plan_expense;
    @BindView(R.id.address_container)
    LinearLayout address_container;
    @BindView(R.id.note_container)
    LinearLayout note_container;
    @BindView(R.id.time_container)
    LinearLayout time_container;
    @BindView(R.id.expense_container)
    LinearLayout expense_container;
    @BindView(R.id.plan_title)
    TextView plan_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_detail);
        ButterKnife.bind(this);
        plan = (Plan) getIntent().getSerializableExtra("PLAN_OBJECT");
        plan_title.setText(plan.getName());
        if (plan.getType().compareTo("Hotel") == 0) {
            setUpTimeLayout();
        } else if (plan.getType().compareTo("Train") == 0 || plan.getType().compareTo("Flight") == 0 || plan.getType().compareTo("Transport") == 0) {
            setUpTimeLayout();
            setupAddress();
            address_container.setVisibility(View.GONE);

        }
        setupTime();

        setupHotelAddress();

        setupExpense();

        setupNotes();
    }

    private void setupNotes() {
        if (plan.getNotes() != null && plan.getNotes().compareTo("") != 0) {
            plan_note.setText(plan.getNotes());
        } else {
            note_container.setVisibility(View.GONE);
        }
    }

    private void setupExpense() {
        if (plan.getExpenses() != 0.0) {
            plan_expense.setText(String.valueOf(plan.getExpenses()));
        } else {
            expense_container.setVisibility(View.GONE);
        }
    }

    private void setupHotelAddress() {
        if (plan.getAddress() != null) {
            plan_address.setText(plan.getAddress());
        } else {
            address_container.setVisibility(View.GONE);
        }
        address_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String longitude = String.valueOf(plan.getLongitude());
                String latitude = String.valueOf(plan.getLatitude());
                String label = plan.getName();
                String uriBegin = "geo:" + latitude + "," + longitude;
                String query = latitude + "," + longitude + "(" + label + ")";
                String encodedQuery = Uri.encode(query);
                String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                Uri uri = Uri.parse(uriString);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    private void setupTime() {
        if (plan.getStartTime() != null) {
            plan.getStartTime().setMonth(plan.getStartTime().getMonth());
            plan_time.setText(new SimpleDateFormat("MM/dd/yyyy HH:mm").format(plan.getStartTime()));
        } else {
            time_container.setVisibility(View.GONE);
        }
    }

    private void setupAddress() {
        TextView arrival_text = (TextView) findViewById(R.id.arrival_text);
        TextView departure_text = (TextView) findViewById(R.id.departure_text);
        if (plan.getStartLocation() == null || plan.getStartLocation().compareTo("") == 0) {
            departure_text.setText("Departure Point");
        } else {
            departure_text.setText(plan.getStartLocation());
        }
        if (plan.getEndLocation() == null || plan.getEndLocation().compareTo("") == 0) {
            arrival_text.setText("Arrival Point");
        } else {
            arrival_text.setText(plan.getEndLocation());
        }
    }

    private void setUpTimeLayout() {
        View child = getLayoutInflater().inflate(R.layout.time_layout, null);
        detail_container.addView(child, 1);
        TextView detail_end_date = (TextView) findViewById(R.id.detail_end_date);
        TextView detail_end_hour = (TextView) findViewById(R.id.detail_end_hour);
        TextView detail_start_date = (TextView) findViewById(R.id.detail_start_date);
        TextView detail_start_hour = (TextView) findViewById(R.id.detail_start_hour);
        TextView duration = (TextView) findViewById(R.id.duration);
        detail_end_date.setText(new DateFormatSymbols().getMonths()[plan.getEndTime().getMonth()].substring(0, 3) + "." + plan.getEndTime().getDate());
        detail_start_date.setText(new DateFormatSymbols().getMonths()[plan.getEndTime().getMonth()].substring(0, 3) + "." + plan.getStartTime().getDate());
        detail_end_hour.setText(plan.getEndTime().getHours() + " : " + Util.formatMinute(plan.getEndTime().getMinutes()));
        detail_start_hour.setText(plan.getStartTime().getHours() + " : " + Util.formatMinute(plan.getEndTime().getMinutes()));
        duration.setText("");
        time_container.setVisibility(View.GONE);
    }
}

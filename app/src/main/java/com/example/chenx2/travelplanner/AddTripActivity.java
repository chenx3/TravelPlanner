package com.example.chenx2.travelplanner;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.chenx2.travelplanner.adapter.TripListAdapter;
import com.example.chenx2.travelplanner.data.Trip;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddTripActivity extends Activity {
    public static final String KEY_ITEM = "KEY_ITEM";
    public static final String POSITION = "POSITION";
    @BindView(R.id.btn_cancel)
    Button btn_cancel;
    @BindView(R.id.btn_save)
    Button btn_save;
    @BindView(R.id.trip_to_add)
    EditText trip_to_add;
    private int position;
    private String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        ButterKnife.bind(this);
        if (getIntent() != null
                && getIntent().hasExtra(TripListAdapter.ITEM_TO_EDIT)) {
            position = getIntent().getIntExtra(TripListAdapter.POSITION_TO_EDIT,0);
            title = getIntent().getStringExtra(TripListAdapter.ITEM_TO_EDIT);
            trip_to_add.setText(title);
        }
    }

    @OnClick(R.id.btn_save)
    public void save() {
        if (trip_to_add.getText().toString().compareTo("") != 0) {
            Trip trip = new Trip(trip_to_add.getText().toString());
            Intent result = new Intent();
            result.putExtra(KEY_ITEM, trip);
            if (getIntent() != null
                    && getIntent().hasExtra(TripListAdapter.ITEM_TO_EDIT)){
                result.putExtra(POSITION,position);
            }
            setResult(RESULT_OK, result);
            finish();
        }else{
            trip_to_add.setError(getString(R.string.title_none_empty));
        }
    }

    @OnClick(R.id.btn_cancel)
    public void cancel() {
        finish();
    }
}

package com.example.chenx2.triporganizer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.example.chenx2.triporganizer.AddTripActivity;
import com.example.chenx2.triporganizer.R;
import com.example.chenx2.triporganizer.TripDetail;

import com.example.chenx2.triporganizer.data.Plan;
import com.example.chenx2.triporganizer.data.Trip;


import java.util.List;

public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.ViewHolder> {
    public static final int REQUEST_CODE_EDIT = 11;
    public static final String ITEM_TO_EDIT = "ITEM_TO_EDIT";
    public static final String POSITION_TO_EDIT = "POSITION_TO_EDIT";
    private List<Trip> trips;
    private Context context;

    public TripListAdapter(Context context) {
        this.context = context;
        trips = Trip.listAll(Trip.class);
    }

    @Override
    public TripListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View todoRow = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.trip_row, null, false);
        return new ViewHolder(todoRow);
    }

    @Override
    public void onBindViewHolder(final TripListAdapter.ViewHolder holder, int position) {
        holder.tvTitle.setText(trips.get(position).getTitle());
        setupSwipeLayout(holder);
        holder.row_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentShowDetail = new Intent();
                intentShowDetail.setClass(context, TripDetail.class);
                intentShowDetail.putExtra("TRIP_OBJECT", trips.get(holder.getAdapterPosition()));
                intentShowDetail.putExtra("TRIP_OBJECT_ID", trips.get(holder.getAdapterPosition()).getId());
                context.startActivity(intentShowDetail);
            }
        });
    }

    private void setupSwipeLayout(final ViewHolder holder) {
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, holder.bottom_wrapper);
        holder.delete_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(holder.getAdapterPosition());
            }
        });
        holder.edit_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentShowEdit = new Intent();
                intentShowEdit.setClass(context, AddTripActivity.class);
                intentShowEdit.putExtra(ITEM_TO_EDIT,trips.get(holder.getAdapterPosition()).getTitle());
                intentShowEdit.putExtra(POSITION_TO_EDIT,holder.getAdapterPosition());
                ((Activity)context).startActivityForResult(intentShowEdit, REQUEST_CODE_EDIT);
            }
        });
        holder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onClose(SwipeLayout layout) {
                //when the SurfaceView totally cover the BottomView.
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                //you are swiping.
            }

            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                //when user's hand released.
            }
        });
    }

    public void edit(String title, int position) {
        trips.get(position).setTitle(title);
        trips.get(position).save();
        notifyItemChanged(position);
    }
    @Override
    public int getItemCount() {
        return trips.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView delete_trip;
        private TextView edit_trip;
        private TextView tvTitle;
        private RelativeLayout row_container;
        private SwipeLayout swipeLayout;
        private LinearLayout bottom_wrapper;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            edit_trip = (TextView) itemView.findViewById(R.id.edit_trip);
            delete_trip = (TextView) itemView.findViewById(R.id.delete_trip);
            row_container = (RelativeLayout) itemView.findViewById(R.id.container);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipeLayout);
            bottom_wrapper = (LinearLayout)itemView.findViewById(R.id.bottom_wrapper);
        }
    }

    public void addItem(Trip trip) {
        trip.save();
        trips.add(trip);
        notifyItemInserted(trips.size());
    }

    public void delete(int position) {
        for(Plan plan: trips.get(position).getPlans()){
            plan.delete();
        }
        trips.get(position).delete();
        trips.remove(position);
        notifyItemRemoved(position);
    }
}

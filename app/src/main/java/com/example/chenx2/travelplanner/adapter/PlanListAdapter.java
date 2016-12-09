package com.example.chenx2.travelplanner.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.example.chenx2.travelplanner.AddPlanActivity;
import com.example.chenx2.travelplanner.AddTripActivity;
import com.example.chenx2.travelplanner.MessageEvent;
import com.example.chenx2.travelplanner.PlanDetail;
import com.example.chenx2.travelplanner.R;
import com.example.chenx2.travelplanner.TripDetail;
import com.example.chenx2.travelplanner.data.Plan;
import com.example.chenx2.travelplanner.data.Trip;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.List;


public class PlanListAdapter extends RecyclerView.Adapter<PlanListAdapter.ViewHolder> {
    public static final String PLAN_TO_EDIT = "PLAN_TO_EDIT";
    public static final String TRIP_ID = "TRIP_ID";
    public static final int REQUEST_CODE_EDIT_PLAN = 200;
    public static final String POSITION_TO_EDIT = "POSITION_TO_EDIT";
    public static final String PLAN_ID = "PLAN_ID";
    private Trip trip;
    private List<Plan> plans;
    Context context;

    public PlanListAdapter(Context context, Trip trip) {
        this.trip = trip;
        this.context = context;
        plans = trip.getPlans();
    }

    @Override
    public PlanListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View todoRow = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.plan_row, null, false);
        return new ViewHolder(todoRow);
    }

    @Override
    public void onBindViewHolder(PlanListAdapter.ViewHolder holder, int position) {
        if (holder.date_layout.getVisibility() == View.VISIBLE) {
            holder.date_layout.setVisibility(View.GONE);
        }
        setupSwipeLayout(holder);
        final int index = holder.getAdapterPosition();
        if (plans.get(holder.getAdapterPosition()).getType().compareTo("Transport") == 0 || plans.get(holder.getAdapterPosition()).getType().compareTo("Flight") == 0 || plans.get(holder.getAdapterPosition()).getType().compareTo("Train") == 0) {
            if (plans.get(holder.getAdapterPosition()).getStartLocation() == null || plans.get(holder.getAdapterPosition()).getStartLocation().compareTo("") == 0) {
                holder.tvEndLocation.setText("Arrival Point");
                holder.tvStartLocation.setText("Departure Point");
            } else {
                holder.tvEndLocation.setText(plans.get(holder.getAdapterPosition()).getEndLocation());
                holder.tvStartLocation.setText(plans.get(holder.getAdapterPosition()).getStartLocation());
            }
        } else {
            holder.tvEndLocation.setText("");
            holder.tvEndTime.setText("");
            holder.tvStartLocation.setText(plans.get(holder.getAdapterPosition()).getName());
        }
        if (plans.get(holder.getAdapterPosition()).getEndTime() != null) {
            String endTime = new SimpleDateFormat("HH:mm").format(plans.get(holder.getAdapterPosition()).getEndTime());
            holder.tvEndTime.setText(endTime);
        }
        if (plans.get(holder.getAdapterPosition()).getStartTime() != null) {
            String startTime = new SimpleDateFormat("HH:mm").format(plans.get(holder.getAdapterPosition()).getStartTime());
            holder.tvStartTime.setText(startTime);
            if (plans.get(holder.getAdapterPosition()).isFirst()) {
                holder.date_layout.setVisibility(View.VISIBLE);
                holder.tvDate.setText(new SimpleDateFormat("MM/dd/yyyy").format(plans.get(holder.getAdapterPosition()).getStartTime()));
            }
        }

        holder.plan_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentShowDetail = new Intent();
                intentShowDetail.setClass(context, PlanDetail.class);
                intentShowDetail.putExtra("PLAN_OBJECT", plans.get(index));
                context.startActivity(intentShowDetail);
            }
        });
        setPlanIcon(trip.getPlans().get(holder.getAdapterPosition()), holder);
    }

    private void setPlanIcon(Plan plan, ViewHolder holder) {
        String type = plan.getType();
        switch (type) {
            case "Attraction":
                holder.plan_icon.setImageResource(R.drawable.black_ic_action_camera);
                break;
            case "Hotel":
                holder.plan_icon.setImageResource(R.drawable.black_ic_hotel);
                break;
            case "Flight":
                holder.plan_icon.setImageResource(R.drawable.black_ic_action_plane);

                break;
            case "Train":
                holder.plan_icon.setImageResource(R.drawable.black_ic_action_train);

                break;
            case "Transport":
                holder.plan_icon.setImageResource(R.drawable.ic_directions_car);
                break;
            case "Others":
                holder.plan_icon.setImageResource(R.drawable.black_ic_note_add);

                break;
            case "Restaurant":
                holder.plan_icon.setImageResource(R.drawable.ic_restaurant);

                break;

            default:
                holder.plan_icon.setImageResource(R.drawable.black_ic_note_add);

                break;
        }
    }

    private void setupSwipeLayout(final ViewHolder holder) {
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, holder.bottom_wrapper);
        holder.delete_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(holder.getAdapterPosition());
            }
        });
        holder.edit_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentShowEdit = new Intent();
                intentShowEdit.setClass(context, AddPlanActivity.class);
                intentShowEdit.putExtra(PLAN_TO_EDIT, plans.get(holder.getAdapterPosition()));
                intentShowEdit.putExtra(POSITION_TO_EDIT, holder.getAdapterPosition());
                intentShowEdit.putExtra(PLAN_ID, plans.get(holder.getAdapterPosition()).getId());
                ((TripDetail) context).currentFragment.startActivityForResult(intentShowEdit, REQUEST_CODE_EDIT_PLAN);
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

    @Override
    public int getItemCount() {
        return plans.size();
    }

    public void addItem(Plan plan) {
        plans.add(plan);
        trip.setPlans(plans);
        plans = trip.getPlans();
        notifyDataSetChanged();
        for (int i = 0; i < plans.size(); i++) {
            notifyItemChanged(i);
        }
        EventBus.getDefault().post(new MessageEvent("Add"));
    }

    public void delete(int position) {
        plans.get(position).delete();
        plans = trip.getPlans();
        notifyDataSetChanged();
        for (int i = 0; i < plans.size(); i++) {
            notifyItemChanged(i);
        }
        EventBus.getDefault().post(new MessageEvent("Delete"));
    }

    public void editItem(int position, Plan newItem, long id) {
        plans.set(position, newItem);
        plans.get(position).setTrip(trip);
        plans.get(position).setId(id);
        plans.get(position).save();
        plans = trip.getPlans();
        notifyDataSetChanged();
        for (int i = 0; i < plans.size(); i++) {
            notifyItemChanged(i);
        }
        EventBus.getDefault().post(new MessageEvent("Edit"));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvEndLocation;
        private TextView tvStartLocation;
        private TextView tvEndTime;
        private TextView tvStartTime;
        private ImageView plan_icon;
        private TextView delete_plan;
        private TextView edit_plan;
        private LinearLayout date_layout;
        private TextView tvDate;
        private LinearLayout plan_container;
        private SwipeLayout swipeLayout;
        private LinearLayout bottom_wrapper;

        public ViewHolder(View itemView) {
            super(itemView);
            delete_plan = (TextView) itemView.findViewById(R.id.plan_delete_trip);
            edit_plan = (TextView) itemView.findViewById(R.id.plan_edit_trip);
            tvEndLocation = (TextView) itemView.findViewById(R.id.tvEndLocation);
            tvStartLocation = (TextView) itemView.findViewById(R.id.tvStartLocation);
            tvStartTime = (TextView) itemView.findViewById(R.id.tvStartTime);
            tvEndTime = (TextView) itemView.findViewById(R.id.tvEndTime);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            date_layout = (LinearLayout) itemView.findViewById(R.id.date_layout);
            plan_container = (LinearLayout) itemView.findViewById(R.id.plan_container);
            plan_icon = (ImageView) itemView.findViewById(R.id.plan_icon);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.planSwipeLayout);
            bottom_wrapper = (LinearLayout) itemView.findViewById(R.id.plan_bottom_wrapper);
        }
    }
}

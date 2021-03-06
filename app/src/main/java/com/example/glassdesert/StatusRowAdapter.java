package com.example.glassdesert;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.glassdesert.DataStructures.Buildings.Building;
import com.example.glassdesert.DataStructures.Fighter;
import com.example.glassdesert.DataStructures.Location;

import java.util.ArrayList;

public class StatusRowAdapter extends RecyclerView.Adapter<StatusRowAdapter.StatusRowViewHolder> {
    final private ClickListener onClickListener;
    private ArrayList<String> names;
    private ArrayList<String> statuses;
    private ArrayList<String> timesRemaining;

    public interface ClickListener {
        void onStatusRowClick(int clickedStatusRow);
    }

    public StatusRowAdapter(ClickListener onClickListener,
                            ArrayList<String> names,
                            ArrayList<String> statuses,
                            ArrayList<String> timesRemaining) {
        this.onClickListener = onClickListener;
        this.names = names;
        this.statuses = statuses;
        this.timesRemaining = timesRemaining;
    }

    // "mode" refers to which fighters to show.
    // 0 - all fighters
    // 1 - deployed fighters
    public StatusRowAdapter(ClickListener onClickListener,
                            ArrayList<Fighter> fighters,
                            int mode) {
        this.onClickListener = onClickListener;
        names = new ArrayList<>();
        statuses = new ArrayList<>();
        timesRemaining = new ArrayList<>();

        for (Fighter fighter : fighters) {
            switch (mode) {
                case 0:
                    addFighter(fighter);
                    break;
                case 1:
                    if (fighter.getDeployment() != null)
                        addFighter(fighter);
            }
        }
    }
    // small helper function to improve readability
    public void addFighter(Fighter fighter) {
        names.add(fighter.name);
        statuses.add(fighter.getStatusString());
        timesRemaining.add(fighter.getTimeRemaining());
    }

    public StatusRowAdapter(ClickListener onClickListener,
                            ArrayList<Building> buildings) {
        this.onClickListener = onClickListener;
        names = new ArrayList<>();
        statuses = new ArrayList<>();
        timesRemaining = new ArrayList<>();

        for (Building building : buildings) {
            names.add(building.getName());
            statuses.add(building.getUpgradingString());
            timesRemaining.add(building.getTimeRemaining());
        }
    }

    public StatusRowAdapter(ClickListener onClickListener,
                            ArrayList<Location> locations,
                            Boolean erasureBad) {
        this.onClickListener = onClickListener;
        names = new ArrayList<>();
        statuses = new ArrayList<>();
        timesRemaining = new ArrayList<>();

        for (Location location : locations) {
            names.add(location.name);
            statuses.add(location.getDistanceString());
            timesRemaining.add(" ");
        }
    }

    @NonNull
    @Override
    public StatusRowViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int listItemLayoutId = R.layout.status_row;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachToParentImmediately = false;

        View view = inflater.inflate(listItemLayoutId, parent, attachToParentImmediately);
        return new StatusRowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusRowViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    class StatusRowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_item_name;
        TextView tv_item_status;
        TextView tv_item_time_remaining;

        private StatusRowViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            tv_item_status = itemView.findViewById(R.id.tv_item_status);
            tv_item_time_remaining = itemView.findViewById(R.id.tv_item_time_remaining);
        }

        private void bind(int index) {
            tv_item_name.setText(names.get(index));
            tv_item_status.setText(statuses.get(index));
            tv_item_time_remaining.setText(timesRemaining.get(index));
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            onClickListener.onStatusRowClick(clickedPosition);
        }
    }
}

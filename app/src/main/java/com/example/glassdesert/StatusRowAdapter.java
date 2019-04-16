package com.example.glassdesert;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.glassdesert.DataStructures.Fighter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StatusRowAdapter extends RecyclerView.Adapter<StatusRowAdapter.StatusRowViewHolder> {
    private ArrayList<String> names;
    private ArrayList<String> statuses;
    private ArrayList<String> timesRemaining;

    public StatusRowAdapter(ArrayList<String> names,
                            ArrayList<String> statuses,
                            ArrayList<String> timesRemaining) {
        this.names = names;
        this.statuses = statuses;
        this.timesRemaining = timesRemaining;
    }

    public StatusRowAdapter(ArrayList<Fighter> fighters) {
        names = new ArrayList<>();
        statuses = new ArrayList<>();
        timesRemaining = new ArrayList<>();

        for (Fighter fighter : fighters) {
            if (fighter.deployment != null) {
                names.add(fighter.name);
                statuses.add(fighter.deployment.getDeploymentString());
                timesRemaining.add(fighter.deployment.timeRemaining);
            }
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

    class StatusRowViewHolder extends RecyclerView.ViewHolder {
        TextView tv_item_name;
        TextView tv_item_status;
        TextView tv_item_time_remaining;

        private StatusRowViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            tv_item_status = itemView.findViewById(R.id.tv_item_status);
            tv_item_time_remaining = itemView.findViewById(R.id.tv_item_time_remaining);
        }

        private void bind(int index) {
            tv_item_name.setText(names.get(index));
            tv_item_status.setText(statuses.get(index));
            tv_item_time_remaining.setText(timesRemaining.get(index));
        }
    }
}
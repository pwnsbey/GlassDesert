package com.example.glassdesert.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.glassdesert.DataStructures.Fighter;
import com.example.glassdesert.R;
import com.example.glassdesert.StatusRowAdapter;
import com.example.glassdesert.Utility.Archivist;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CohortActivity extends AppCompatActivity implements StatusRowAdapter.ClickListener {
    private Archivist archivist;
    private ArrayList<Fighter> fighters;

    private RecyclerView rvFighterCohort;
    private StatusRowAdapter cohortAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cohort);

        // set activity variables
        archivist = new Archivist();
        fighters = archivist.getFighterCohort();

        // set up recycler view
        rvFighterCohort = findViewById(R.id.rv_fighter_cohort);
        LinearLayoutManager cohortLayoutManager = new LinearLayoutManager(this);
        rvFighterCohort.setLayoutManager(cohortLayoutManager);
        rvFighterCohort.setHasFixedSize(true);
        cohortAdapter = new StatusRowAdapter(this, fighters, 0);
        rvFighterCohort.setAdapter(cohortAdapter);
    }

    public void onNavButtonClick(View view) {
        switch (view.getId()) {
            case (R.id.btn_misc):
                // TODO: add navigation to misc
                break;
            case (R.id.btn_cohort):
                // we are already here...
                break;
            case (R.id.btn_overview):
                startActivity(new Intent(this, OverviewActivity.class));
                break;
            case (R.id.btn_buildings):
                // TODO: add navigation to buildings
                break;
            case (R.id.btn_inventory):
                // TODO: add navigation to inventory
                break;
        }
    }

    @Override
    public void onStatusRowClick(int clickedStatusRow) {
        Intent intent = new Intent(this, FighterDetailsActivity.class);
        intent.putExtra("fighterId", fighters.get(clickedStatusRow).id);
        startActivity(intent);
    }
}

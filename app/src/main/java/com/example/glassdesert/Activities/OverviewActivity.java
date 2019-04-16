package com.example.glassdesert.Activities;

import android.os.Bundle;

import com.example.glassdesert.R;
import com.example.glassdesert.StatusRowAdapter;
import com.example.glassdesert.Utility.Archivist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OverviewActivity extends AppCompatActivity {
    private Archivist archivist;
    private StatusRowAdapter deploymentsAdapter;
    private StatusRowAdapter buildingsAdapter;
    private StatusRowAdapter projectsAdapter;
    private RecyclerView rvCurrentDeployments;
    private RecyclerView rvBuildingUpgrades;
    private RecyclerView rvBaseProjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        // set activity variables
        archivist = new Archivist();
        rvCurrentDeployments = findViewById(R.id.rv_current_deployments);
        rvBuildingUpgrades = findViewById(R.id.rv_building_upgrades);
        rvBaseProjects = findViewById(R.id.rv_base_projects);


        LinearLayoutManager deploymentsLayoutManager = new LinearLayoutManager(this);
        rvCurrentDeployments.setLayoutManager(deploymentsLayoutManager);
        rvCurrentDeployments.setHasFixedSize(true);
        // TODO: Add adapters for the other ones, too
        deploymentsAdapter = new StatusRowAdapter(archivist.getFighterCohort());
        rvCurrentDeployments.setAdapter(deploymentsAdapter);
        LinearLayoutManager buildingLayoutManager = new LinearLayoutManager(this);
        rvBuildingUpgrades.setLayoutManager(buildingLayoutManager);
        rvBuildingUpgrades.setHasFixedSize(true);
        LinearLayoutManager projectsLayoutManager = new LinearLayoutManager(this);
        rvBaseProjects.setLayoutManager(projectsLayoutManager);
        rvBaseProjects.setHasFixedSize(true);
    }
}

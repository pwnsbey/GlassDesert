package com.example.glassdesert.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.glassdesert.DataStructures.Buildings.Building;
import com.example.glassdesert.DataStructures.Fighter;
import com.example.glassdesert.R;
import com.example.glassdesert.StatusRowAdapter;
import com.example.glassdesert.Utility.Archivist;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OverviewActivity extends AppCompatActivity implements StatusRowAdapter.ClickListener {
    // TODO: We use archivist literally everywhere, it should be made into a singleton
    private Archivist archivist;
    private ArrayList<Fighter> fighters;
    private ArrayList<Building> buildings;

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
        archivist = new Archivist(this);
        // init DB test data
        // TODO: BY ALL THAT IS GOOD AND HOLY, REMOVE THIS AFTER TESTING
        initTestData();
        fighters = archivist.getFighterCohort();
        buildings = archivist.getBuildingList();

        // set and hook up recycler views
        rvCurrentDeployments = findViewById(R.id.rv_current_deployments);
        rvBuildingUpgrades = findViewById(R.id.rv_building_upgrades);
        rvBaseProjects = findViewById(R.id.rv_base_projects);

        LinearLayoutManager deploymentsLayoutManager = new LinearLayoutManager(this);
        rvCurrentDeployments.setLayoutManager(deploymentsLayoutManager);
        rvCurrentDeployments.setHasFixedSize(true);
        deploymentsAdapter = new StatusRowAdapter(this, fighters, 1);
        rvCurrentDeployments.setAdapter(deploymentsAdapter);

        LinearLayoutManager buildingLayoutManager = new LinearLayoutManager(this);
        rvBuildingUpgrades.setLayoutManager(buildingLayoutManager);
        rvBuildingUpgrades.setHasFixedSize(true);
        buildingsAdapter = new StatusRowAdapter(this, buildings);
        rvBuildingUpgrades.setAdapter(buildingsAdapter);

        // TODO: Combine building upgrades/projects into one? What do we do with this one then?
        LinearLayoutManager projectsLayoutManager = new LinearLayoutManager(this);
        rvBaseProjects.setLayoutManager(projectsLayoutManager);
        rvBaseProjects.setHasFixedSize(true);
    }

    private void initTestData() {
        //init tables
        archivist.setupTestModeTables();

        // add Locke
        archivist.archiveFighter("Locke Arran", "Male", "Eynsham");

        // add Locke's deployment
        archivist.archiveDeployment("Aion", "Locke Arran");

        // add Tierras, no deployment because she is cleaning up Locke's mess back at base probably
        archivist.archiveFighter("Tierras Lin", "Female", "Eynsham");

        // add buildings
        archivist.archiveBuilding();
    }

    public void onNavButtonClick(View view) {
        switch (view.getId()) {
            case (R.id.btn_misc):
                // TODO: add navigation to misc
                break;
            case (R.id.btn_cohort):
                startActivity(new Intent(this, CohortActivity.class));
                break;
            case (R.id.btn_overview):
                // we are already here...
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
        intent.putExtra("fighterName", fighters.get(clickedStatusRow).name);
        startActivity(intent);
    }
}

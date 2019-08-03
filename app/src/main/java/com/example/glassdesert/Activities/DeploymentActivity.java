package com.example.glassdesert.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.glassdesert.DataStructures.Deployment;
import com.example.glassdesert.DataStructures.Location;
import com.example.glassdesert.R;
import com.example.glassdesert.StatusRowAdapter;
import com.example.glassdesert.Utility.Archivist;

import java.util.ArrayList;

public class DeploymentActivity extends AppCompatActivity implements StatusRowAdapter.ClickListener{
    private Archivist archivist;
    private ArrayList<Location> locations;
    private String fighterName;

    private StatusRowAdapter locationsAdapter;
    private RecyclerView rvLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deployment);

        archivist = new Archivist(this);
        locations = archivist.getDiscoveredLocations();
        fighterName = getIntent().getStringExtra("fighterName");

        rvLocations = findViewById(R.id.rv_deployment_locations);
        LinearLayoutManager deploymentLocationsLayoutManager = new LinearLayoutManager(this);
        rvLocations.setLayoutManager(deploymentLocationsLayoutManager);
        rvLocations.setHasFixedSize(true);
        locationsAdapter = new StatusRowAdapter(this, locations, true);
    }

    @Override
    public void onStatusRowClick(int clickedStatusRow) {
        // what to do when a location is selected
        Deployment deployment = new Deployment(locations.get(clickedStatusRow), 0, archivist.getFighter(fighterName));
        deployment.stageDeployment(10000);
        finish();
    }
}

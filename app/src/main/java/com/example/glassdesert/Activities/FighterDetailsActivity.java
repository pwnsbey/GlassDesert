package com.example.glassdesert.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.glassdesert.DataStructures.Fighter;
import com.example.glassdesert.R;
import com.example.glassdesert.Utility.Archivist;

public class FighterDetailsActivity extends AppCompatActivity {
    private Archivist archivist;
    private Fighter fighter;

    private TextView tvName;
    private TextView tvGenderRace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fighter_details);

        // set activity variables
        //todo: move this to its own thread... somehow...
        archivist = new Archivist(this);

        fighter = archivist.getFighter(getIntent().getStringExtra("fighterName"));

        tvName = findViewById(R.id.tv_name);
        tvGenderRace = findViewById(R.id.tv_gender_race);

        tvName.setText(fighter.name);
        String genderRaceText = fighter.race + " " + fighter.gender;
        tvGenderRace.setText(genderRaceText);
    }

    public void openDeploymentActivity() {
        Intent intent = new Intent(this, DeploymentActivity.class);
        intent.putExtra("fighterName", fighter.name);
        startActivity(intent);
    }
}

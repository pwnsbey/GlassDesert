package com.example.glassdesert.Activities;

import android.os.Bundle;
import android.widget.TextView;

import com.example.glassdesert.DataStructures.Fighter;
import com.example.glassdesert.R;
import com.example.glassdesert.Utility.Archivist;

import androidx.appcompat.app.AppCompatActivity;

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
        archivist = new Archivist();

        fighter = archivist.getFighterById(getIntent().getIntExtra("fighterId", -1));

        tvName = findViewById(R.id.tv_name);
        tvGenderRace = findViewById(R.id.tv_gender_race);

        tvName.setText(fighter.name);
        tvGenderRace.setText(fighter.race + " " + fighter.gender);
    }
}

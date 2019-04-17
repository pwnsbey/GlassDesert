package com.example.glassdesert.Utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.glassdesert.DataStructures.Buildings.Building;
import com.example.glassdesert.DataStructures.Buildings.CommandBuilding;
import com.example.glassdesert.DataStructures.Deployment;
import com.example.glassdesert.DataStructures.Fighter;

import java.util.ArrayList;

import androidx.annotation.Nullable;

// A utility class for database (Archive) access
public class Archivist extends SQLiteOpenHelper {
    private ArrayList<Fighter> fighters;
    private ArrayList<Building> buildings;

    private static final String DATABASE_NAME = "Archive.db";

    private static final String FIGHTERS_TABLE_NAME = "fighters";
    private static final String FIGHTERS_COLUMN_ID = "id";
    private static final String FIGHTERS_COLUMN_NAME = "name";
    private static final String FIGHTERS_COLUMN_GENDER = "gender";
    private static final String FIGHTERS_COLUMN_RACE = "race";

    private static final String DEPLOYMENTS_TABLE_NAME = "deployments";
    private static final String DEPLOYMENTS_COLUMN_LOCATION = "location";
    private static final String DEPLOYMENTS_FIGHTER_NAME = "fighterName";

    public Archivist(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createDeploymentsTable(db);
        createFightersTable(db);
    }

    public void setupTestModeTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DROP TABLE IF EXISTS " + FIGHTERS_TABLE_NAME + ";";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS " + DEPLOYMENTS_TABLE_NAME + ";";
        db.execSQL(sql);
        // I KNOW IT'S GROSS, I'M SORRY
        createFightersTable(db);
        createDeploymentsTable(db);
    }

    private void createDeploymentsTable(SQLiteDatabase db) {
        String sql =
                "CREATE TABLE IF NOT EXISTS " + DEPLOYMENTS_TABLE_NAME + " (" +
                DEPLOYMENTS_COLUMN_LOCATION + " TEXT, " +
                DEPLOYMENTS_FIGHTER_NAME + " INTEGER, " +
                "CONSTRAINT fk_" + DEPLOYMENTS_FIGHTER_NAME + " " +
                "FOREIGN KEY (" + DEPLOYMENTS_FIGHTER_NAME + ")" +
                "REFERENCES " + FIGHTERS_TABLE_NAME + "(" + FIGHTERS_COLUMN_ID + ")" +
                ");";
        db.execSQL(sql);
    }

    private void createFightersTable(SQLiteDatabase db) {
        String sql =
                "CREATE TABLE IF NOT EXISTS " + FIGHTERS_TABLE_NAME + " (" +
                FIGHTERS_COLUMN_NAME + " TEXT, " +
                FIGHTERS_COLUMN_GENDER + " TEXT, " +
                FIGHTERS_COLUMN_RACE + " TEXT" +
                ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private void initFighterCohort() {
        fighters = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String fighterSql = "SELECT * FROM " + FIGHTERS_TABLE_NAME;
        Cursor fighterRes = db.rawQuery(fighterSql, null);
        fighterRes.moveToFirst();

        Cursor deploymentRes;
        while (!fighterRes.isAfterLast()) {
            // fill fighter data
            String name = fighterRes
                    .getString(fighterRes.getColumnIndex(FIGHTERS_COLUMN_NAME));
            String gender = fighterRes
                    .getString(fighterRes.getColumnIndex(FIGHTERS_COLUMN_GENDER));
            String race = fighterRes
                    .getString(fighterRes.getColumnIndex(FIGHTERS_COLUMN_RACE));

            // deployments take a little extra work since they're on a different table
            Deployment deployment = null;
            String deploymentSql = "SELECT * FROM " + DEPLOYMENTS_TABLE_NAME +
                    " WHERE " + DEPLOYMENTS_FIGHTER_NAME + " = \"" + name + "\";";
            deploymentRes = db.rawQuery(deploymentSql, null);
            if (deploymentRes.getCount() > 0) {
                deploymentRes.moveToFirst();
                String deploymentLocation = deploymentRes
                        .getString(deploymentRes.getColumnIndex(DEPLOYMENTS_COLUMN_LOCATION));
                deployment = new Deployment(deploymentLocation, 1, "2:22:22");
                deploymentRes.close();
            }
            fighters.add(new Fighter(name, gender, race, deployment));
            fighterRes.moveToNext();
        }
        fighterRes.close();
    }

    public ArrayList<Fighter> getFighterCohort() {
        if (fighters == null)
            initFighterCohort();
        return fighters;
    }

    public Fighter getFighterByName(String name) {
        for (Fighter fighter : fighters) {
            if (fighter.name.equals(name)) {
                return fighter;
            }
        }
        return null;
    }

    public void archiveFighter(String name, String gender, String race) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIGHTERS_COLUMN_NAME, name);
        values.put(FIGHTERS_COLUMN_GENDER, gender);
        values.put(FIGHTERS_COLUMN_RACE, race);

        db.insert(FIGHTERS_TABLE_NAME, null, values);
    }

    // TODO: Add time, status, and such to this function
    public void archiveDeployment(String location, String assignedFighter) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DEPLOYMENTS_COLUMN_LOCATION, location);
        values.put(DEPLOYMENTS_FIGHTER_NAME, assignedFighter);

        db.insert(DEPLOYMENTS_TABLE_NAME, null, values);
    }

    public ArrayList<Building> getBuildingList() {
        return buildings;
    }

    public void archiveBuilding() {
        // TODO: switch these to database data
        buildings = new ArrayList<>();
        buildings.add(new CommandBuilding(0));
    }
}

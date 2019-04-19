package com.example.glassdesert.Utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.glassdesert.DataStructures.Buildings.Building;
import com.example.glassdesert.DataStructures.Buildings.CommandBuilding;
import com.example.glassdesert.DataStructures.Deployment;
import com.example.glassdesert.DataStructures.Fighter;
import com.example.glassdesert.DataStructures.Location;

import java.util.ArrayList;

// A utility class for database (Archive) access
public class Archivist extends SQLiteOpenHelper {
    private ArrayList<Building> buildings;

    private static final String DATABASE_NAME = "Archive.db";

    private static final String FIGHTERS_TABLE_NAME = "fighters";
    private static final String FIGHTERS_COLUMN_ID = "id";
    private static final String FIGHTERS_COLUMN_NAME = "name";
    private static final String FIGHTERS_COLUMN_GENDER = "gender";
    private static final String FIGHTERS_COLUMN_RACE = "race";

    private static final String DEPLOYMENTS_TABLE_NAME = "deployments";
    private static final String DEPLOYMENTS_COLUMN_LOCATION_NAME = "locationName";
    private static final String DEPLOYMENTS_COLUMN_FIGHTER_NAME = "fighterName";

    private static final String LOCATIONS_TABLE_NAME = "locations";
    private static final String LOCATIONS_COLUMN_NAME = "name";
    private static final String LOCATIONS_COLUMN_IS_DISCOVERED = "isDiscovered";

    public Archivist(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createFightersTable(db);
        createDeploymentsTable(db);
        createLocationsTable(db);
    }

    public void setupTestModeTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DROP TABLE IF EXISTS " + FIGHTERS_TABLE_NAME + ";";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS " + DEPLOYMENTS_TABLE_NAME + ";";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS " + LOCATIONS_TABLE_NAME + ";";
        db.execSQL(sql);
        createFightersTable(db);  // I KNOW IT'S GROSS, I'M SORRY
        createDeploymentsTable(db);
        createLocationsTable(db);
    }

    private void createLocationsTable(SQLiteDatabase db) {
        String sql =
                "CREATE TABLE IF NOT EXISTS " + LOCATIONS_TABLE_NAME + " (" +
                LOCATIONS_COLUMN_NAME + " TEXT, " +
                LOCATIONS_COLUMN_IS_DISCOVERED + " BOOLEAN" +
                ");";
        db.execSQL(sql);
    }

    private void createDeploymentsTable(SQLiteDatabase db) {
        String sql =
                "CREATE TABLE IF NOT EXISTS " + DEPLOYMENTS_TABLE_NAME + " (" +
                DEPLOYMENTS_COLUMN_LOCATION_NAME + " TEXT, " +
                DEPLOYMENTS_COLUMN_FIGHTER_NAME + " INTEGER, " +
                "FOREIGN KEY (" + DEPLOYMENTS_COLUMN_FIGHTER_NAME + ") " +
                "REFERENCES " + FIGHTERS_TABLE_NAME + "(" + FIGHTERS_COLUMN_ID + "), " +
                "FOREIGN KEY (" + DEPLOYMENTS_COLUMN_LOCATION_NAME + ") " +
                "REFERENCES " + LOCATIONS_TABLE_NAME + "(" + LOCATIONS_COLUMN_NAME + ")" +
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

    private ArrayList<Fighter> processFighterResults(Cursor fighterRes, SQLiteDatabase db) {
        ArrayList<Fighter> fighters = new ArrayList<>();

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
            // we don't have a dedicated function for this since we'll never get deployments
            // on their own without the fighters.
            Deployment deployment = null;
            String deploymentSql = "SELECT * FROM " + DEPLOYMENTS_TABLE_NAME +
                    " WHERE " + DEPLOYMENTS_COLUMN_FIGHTER_NAME + " = \"" + name + "\";";
            deploymentRes = db.rawQuery(deploymentSql, null);
            if (deploymentRes.getCount() > 0) {
                deploymentRes.moveToFirst();
                String deploymentLocation = deploymentRes
                        .getString(deploymentRes.getColumnIndex(DEPLOYMENTS_COLUMN_LOCATION_NAME));
                Location location = getLocationByName(deploymentLocation, db);
                deployment = new Deployment(location, 1, "2:22:22");
                deploymentRes.close();
            }
            fighters.add(new Fighter(name, gender, race, deployment));
            fighterRes.moveToNext();
        }
        fighterRes.close();
        return fighters;
    }

    public ArrayList<Fighter> getFighterCohort() {
        SQLiteDatabase db = this.getReadableDatabase();

        String fighterSql = "SELECT * FROM " + FIGHTERS_TABLE_NAME;
        Cursor fighterRes = db.rawQuery(fighterSql, null);
        return processFighterResults(fighterRes, db);
    }

    public Fighter getFighterByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        String fighterSql = "SELECT * FROM " + FIGHTERS_TABLE_NAME +
                            " WHERE " + FIGHTERS_TABLE_NAME + " = \"" + name + "\";";
        Cursor fighterRes = db.rawQuery(fighterSql, null);
        return processFighterResults(fighterRes, db).get(0);
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
    public void archiveDeployment(Deployment deployment, String assignedFighter) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DEPLOYMENTS_COLUMN_LOCATION_NAME, deployment.location.name);
        values.put(DEPLOYMENTS_COLUMN_FIGHTER_NAME, assignedFighter);

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

    private ArrayList<Location> processLocationResults(Cursor locationRes, SQLiteDatabase db) {
        ArrayList<Location> locations = new ArrayList<>();

        locationRes.moveToFirst();
        while (!locationRes.isAfterLast()) {
            String name = locationRes
                    .getString(locationRes.getColumnIndex(LOCATIONS_COLUMN_NAME));
            boolean isDiscovered = locationRes
                    .getInt(locationRes.getColumnIndex(LOCATIONS_COLUMN_IS_DISCOVERED)) > 0;
            locations.add(new Location(name, 22, isDiscovered));
        }
        locationRes.close();
        return locations;
    }

    public ArrayList<Location> getLocations() {
        SQLiteDatabase db = this.getReadableDatabase();

        String locationSql = "SELECT * FROM " + LOCATIONS_TABLE_NAME;
        Cursor locationRes = db.rawQuery(locationSql, null);
        return processLocationResults(locationRes, db);
    }

    public ArrayList<Location> getDiscoveredLocations() {
        SQLiteDatabase db = this.getReadableDatabase();

        String locationSql = "SELECT * FROM " + LOCATIONS_TABLE_NAME +
                             " WHERE " + LOCATIONS_COLUMN_IS_DISCOVERED + " = 1;";
        Cursor locationRes = db.rawQuery(locationSql, null);
        return processLocationResults(locationRes, null);
    }

    public Location getLocationByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        String locationSql = "SELECT * FROM " + LOCATIONS_TABLE_NAME +
                             " WHERE " + LOCATIONS_COLUMN_NAME + " =\"" + name + "\";";
        Cursor locationRes = db.rawQuery(locationSql, null);
        return processLocationResults(locationRes, db).get(0);
    }

    private Location getLocationByName(String name, SQLiteDatabase db) {
        String locationSql = "SELECT * FROM " + LOCATIONS_TABLE_NAME +
                " WHERE " + LOCATIONS_COLUMN_NAME + " =\"" + name + "\";";
        Cursor locationRes = db.rawQuery(locationSql, null);
        return processLocationResults(locationRes, db).get(0);
    }

    public void archiveLocation(Location location) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LOCATIONS_COLUMN_NAME, location.name);
        values.put(LOCATIONS_COLUMN_IS_DISCOVERED, location.isDiscovered);

        db.insert(LOCATIONS_TABLE_NAME, null, values);
    }
}

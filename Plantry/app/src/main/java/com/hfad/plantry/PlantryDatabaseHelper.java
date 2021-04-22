package com.hfad.plantry;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Time;

public class PlantryDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "plantry";
    private static final int DB_VERSION = 2;

    PlantryDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION); //we're calling the constructor of the sqliteopenhelper superclass, and passing it the database name and version
        //the null parameter is an advanced feature relating to cursors.
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        updateMyDatabase(db, 0, DB_VERSION);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("Create Table RECIPE (" + "_id INTEGER PRIMARY KEY AUTOINCREMENT," + " NAME TEXT,"+"STEPS TEXT," + "LEVEL TEXT," + "TIME TIME);"); //this creates the RECIPE table
            db.execSQL("Create Table RECIPEITEMS (" + "RECIPE_ID INTEGER," + " NAME TEXT," + "WEIGHT DOUBLE," + "TYPE TEXT, "+"FOREIGN KEY(RECIPE_ID) REFERENCES RECIPE(_id));"); //this creates the RECIPE table



        }

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){ //onUpgrade() gets called when the database needs to be upgraded.
        updateMyDatabase(db, oldVersion, newVersion);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private static void insertRecipe(SQLiteDatabase db, String name, String steps, String level, Time time) { //this method is used to insert several drinks
        ContentValues recipeValues = new ContentValues();
        recipeValues.put("NAME", name);
        recipeValues.put("STEPS", steps);
        recipeValues.put("LEVEL", level);
        recipeValues.put("TIME", time.toString());
        db.insert("RECIPE", null, recipeValues);
    }

    private static void insertRecipeItem(SQLiteDatabase db, int recipe_id, String name, double weight, String type) { //this method is used to insert several drinks
        ContentValues recipeItemValues = new ContentValues();
        recipeItemValues.put("RECIPE_ID", recipe_id);
        recipeItemValues.put("NAME", name);
        recipeItemValues.put("WEIGHT", weight);
        recipeItemValues.put("TYPE", type);
        db.insert("RECIPEITEMS", null, recipeItemValues);
    }

}

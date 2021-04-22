package com.hfad.plantry;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
            db.execSQL("Create Table RECIPEITEMS (" + "RECIPE_ID INTEGER," + " NAME TEXT," + "WEIGHT INTEGER, "+"FOREIGN KEY(RECIPE_ID) REFERENCES RECIPE(_id));"); //this creates the RECIPE table


            insertDrink(db, "Latte", "Espresso and steamed milk", R.drawable.latte);
            insertDrink(db, "Cappuccino", "Espresso, hot milk and steamed-milk foam", R.drawable.cappuccino);
            insertDrink(db, "Filter", "Our best drip coffee", R.drawable.filter);

            db.execSQL("Create Table FOOD (" + "_id INTEGER PRIMARY KEY AUTOINCREMENT," + " NAME TEXT," + "DESCRIPTION TEXT," + "IMAGE_RESOURCE_ID INTEGER);"); //this creates the DRINK table

            insertFood(db, "Pizza", "Cheese and red sauce", R.drawable.pizza);
            insertFood(db, "Pasta", "Meatballs and marinara sauce", R.drawable.pasta);
            insertFood(db, "Chicken Parmesan", "Parmesan cheese and marinara sauce on chicken cutlet", R.drawable.chicken_parmesan);
        }

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){ //onUpgrade() gets called when the database needs to be upgraded.
        updateMyDatabase(db, oldVersion, newVersion);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private static void insertDrink(SQLiteDatabase db, String name, String description, int resourceId) { //this method is used to insert several drinks
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("NAME", name);
        drinkValues.put("DESCRIPTION", description);
        drinkValues.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert("DRINK", null, drinkValues);
    }

    private static void insertFood(SQLiteDatabase db, String name, String description, int resourceId) { //this method is used to insert several drinks
        ContentValues foodValues = new ContentValues();
        foodValues.put("NAME", name);
        foodValues.put("DESCRIPTION", description);
        foodValues.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert("FOOD", null, foodValues);
    }

}

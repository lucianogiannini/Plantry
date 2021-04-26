package com.hfad.plantry;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Time;

public class PlantryDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "plantry";
    private static final int DB_VERSION = 3;

    PlantryDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION); //we're calling the constructor of the sqliteopenhelper superclass, and passing it the database name and version
        //the null parameter is an advanced feature relating to cursors.
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        updateMyDatabase(db, 0, DB_VERSION);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("Create Table RECIPE (" + "_id INTEGER PRIMARY KEY AUTOINCREMENT," + " NAME TEXT,"+"STEPS TEXT," + "LEVEL TEXT," + "TIME TEXT," + "IMAGE_RESOURCE_ID INTEGER," + "IMAGE_RESOURCE_ID_LARGE INTEGER);"); //this creates the RECIPE table
            db.execSQL("Create Table RECIPEITEMS (" + "RECIPE_ID INTEGER," + " NAME TEXT," + "WEIGHT DOUBLE," + "TYPE TEXT, "+"FOREIGN KEY(RECIPE_ID) REFERENCES RECIPE(_id));"); //this creates the RECIPEITEMS table
            db.execSQL("Create Table PANTRY (" + " NAME TEXT," + "WEIGHT DOUBLE," + "TYPE TEXT);");
            db.execSQL("Create Table SHOPPINGLIST (" + "RECIPE_ID INTEGER," + " NAME TEXT," + "WEIGHT DOUBLE," + "TYPE TEXT, "+"FOREIGN KEY(RECIPE_ID) REFERENCES RECIPE(_id));");

            insertRecipe(db,"Chicago-Style Deep Dish Pizza with Italian Sausage",String.valueOf(R.string.Deep_Dish),"MEDIUM","02:55:00",R.drawable.chicago_style_deep_dish_small,R.drawable.chicago_style_deep_dish);
            insertRecipeItem(db, 1, "Flour", 16, "ounces");
            insertRecipeItem(db, 1, "Yellow Cornmeal", 1, "ounces");
            insertRecipeItem(db, 1, "Salt", 0.167, "teaspoon");
            insertRecipeItem(db, 1, "Instant Yeast", 0.167, "teaspoon");
            insertRecipeItem(db, 1, "Sugar", 0.334, "teaspoon");
            insertRecipeItem(db, 1, "Olive Oil", 0.167, "teaspoon");
            insertRecipeItem(db, 1, "Butter", 1.5, "tablespoon");
            insertRecipeItem(db, 1, "Italian Sausage", 16, "ounces");
            insertRecipeItem(db, 1, "Mozzarella Cheese", 8, "ounces");
            insertRecipeItem(db, 1, "Parmesan", 2, "ounces");
            insertRecipeItem(db, 1, "Onion", 6, "ounces");
            insertRecipeItem(db, 1, "Garlic", 0.5, "tablespoon");
            insertRecipeItem(db, 1, "Oregano", 0.334, "teaspoon");
            insertRecipeItem(db, 1, "Dried Rosemary", 0.167, "teaspoon");
            insertRecipeItem(db, 1, "Crushed Tomatoes", 25, "ounces");

            insertRecipe(db,"Best Beef Chili",String.valueOf(R.string.Beef_Chili),"MEDIUM","01:45:00",R.drawable.beef_chili_small,R.drawable.beef_chili);
            insertRecipeItem(db, 2, "Olive Oil", 1, "ounces");
            insertRecipeItem(db, 2, "Onion", 12, "ounces");
            insertRecipeItem(db, 2, "Garlic", 0.5, "tablespoon");
            insertRecipeItem(db, 2, "Ground Beef", 32, "ounces");
            insertRecipeItem(db, 2, "Salt", 0.167, "teaspoon");
            insertRecipeItem(db, 2, "Chili Powder", 0.5, "tablespoon");
            insertRecipeItem(db, 2, "Chipotle Chili Powder", 0.0416667, "teaspoon");
            insertRecipeItem(db, 2, "Ground Cumin", 0.5, "tablespoon");
            insertRecipeItem(db, 2, "Oregano", 0.334, "teaspoon");
            insertRecipeItem(db, 2, "Sweet Bell Peppers", 16, "ounces");
            insertRecipeItem(db, 2, "Whole Peeled Tomato", 28, "ounces");
            insertRecipeItem(db, 2, "Water", 16, "ounces");
            insertRecipeItem(db, 2, "Black Beans", 30, "ounces");
            insertRecipeItem(db, 2, "Corn Kernels", 8, "ounces");

            insertRecipe(db,"Feijoada, Brazilian Black Bean Stew",String.valueOf(R.string.Feijoada),"HARD","05:10:00",R.drawable.feijoada_small,R.drawable.feijoada);
            insertRecipeItem(db, 3, "Dry Black Beans", 16, "ounces");
            insertRecipeItem(db, 3, "Olive Oil", 2, "tablespoon");
            insertRecipeItem(db, 3, "Pork Shoulder", 16, "ounces");
            insertRecipeItem(db, 3, "Onion", 12, "ounces");
            insertRecipeItem(db, 3, "Garlic", 0.5, "tablespoon");
            insertRecipeItem(db, 3, "Corned Beef", 16, "ounces");
            insertRecipeItem(db, 3, "Kielbasa", 8, "ounces");
            insertRecipeItem(db, 3, "Ham Hock", 7, "ounces");
            insertRecipeItem(db, 3, "Bay Leaves", 2, "ounces");
            insertRecipeItem(db, 3, "Crushed Tomatoes", 14, "ounces");
            insertRecipeItem(db, 3, "Salt", 0.334, "teaspoon");

            insertRecipe(db,"Spaghetti and Meatballs",String.valueOf(R.string.Spaghetti_Meatball),"EASY","01:00:00",R.drawable.spaghetti_and_meatballs_small,R.drawable.spaghetti_and_meatballs);
            insertRecipeItem(db, 4, "Olive Oil", 1.5, "tablespoon");
            insertRecipeItem(db, 4, "Onion", 4, "ounces");
            insertRecipeItem(db, 4, "Garlic", 0.5, "tablespoon");
            insertRecipeItem(db, 4, "Carrots", 8, "ounces");
            insertRecipeItem(db, 4, "Brown Mushroom", 12, "ounces");
            insertRecipeItem(db, 4, "Italian Plum Tomatoes", 56, "ounces");
            insertRecipeItem(db, 4, "Parsley", 3, "ounces");
            insertRecipeItem(db, 4, "Basil", 3, "ounces");
            insertRecipeItem(db, 4, "Tomato Paste", 1.5, "tablespoon");
            insertRecipeItem(db, 4, "Parmesan", 4, "ounces");
            insertRecipeItem(db, 4, "Red Wine", 2, "ounces");
            insertRecipeItem(db, 4, "Ground Beef", 16, "ounces");
            insertRecipeItem(db, 4, "Italian Sausage", 8, "ounces");
            insertRecipeItem(db, 4, "Eggs", 2, "eggs");
            insertRecipeItem(db, 4, "Breadcrumbs", 6, "ounces");
            insertRecipeItem(db, 4, "Sea Salt", 0.334, "teaspoon");
            insertRecipeItem(db, 4, "Black Pepper", 0.334, "teaspoon");
            insertRecipeItem(db, 4, "Spaghetti", 24, "ounces");

            insertRecipe(db,"Arugula Salad With Beets and Goat Cheese",String.valueOf(R.string.Arugula_Salad),"EASY","01:10:00",R.drawable.arugula_salad_small,R.drawable.arugula_salad);
            insertRecipeItem(db, 5, "Beet", 8, "ounces");
            insertRecipeItem(db, 5, "Baby Arugula", 2.5, "tablespoon");
            insertRecipeItem(db, 5, "Goat Cheese", 1.5, "tablespoon");
            insertRecipeItem(db, 5, "Walnuts", 2, "ounces");
            insertRecipeItem(db, 5, "Olive Oil", 1.5, "tablespoon");
            insertRecipeItem(db, 5, "Lemon Juice", 1.25, "ounces");
            insertRecipeItem(db, 5, "Dry Powdered Mustard", 0.167, "teaspoon");
            insertRecipeItem(db, 5, "Sugar", 0.167, "teaspoon");
            insertRecipeItem(db, 5, "Salt", 0.167, "teaspoon");
            insertRecipeItem(db, 5, "Pepper", 0.167, "teaspoon");

            insertRecipe(db,"Easy Shepherd’s Pie",String.valueOf(R.string.Shepherds_Pie),"EASY","01:05:00",R.drawable.shepherds_pie_small,R.drawable.shepherds_pie);
            insertRecipeItem(db, 6, "Potatoes", 32, "ounces");
            insertRecipeItem(db, 6, "Butter", 4, "ounces");
            insertRecipeItem(db, 6, "Onion", 12, "ounces");
            insertRecipeItem(db, 6, "Mixed Vegetables", 16, "ounces");
            insertRecipeItem(db, 6, "Ground Beef", 24, "ounces");
            insertRecipeItem(db, 6, "Beef Broth", 8, "ounces");
            insertRecipeItem(db, 6, "Worcestershire sauce ", 0.167, "teaspoon");



    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){ //onUpgrade() gets called when the database needs to be upgraded.
        updateMyDatabase(db, oldVersion, newVersion);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private static void insertRecipe(SQLiteDatabase db, String name, String steps, String level, String time, int resource_id, int resource_id_large) { //this method is used to insert several drinks
        ContentValues recipeValues = new ContentValues();
        recipeValues.put("NAME", name);
        recipeValues.put("STEPS", steps);
        recipeValues.put("LEVEL", level);
        recipeValues.put("TIME", time);
        recipeValues.put("IMAGE_RESOURCE_ID",resource_id);
        recipeValues.put("IMAGE_RESOURCE_ID_LARGE",resource_id_large);
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

    private static void insertPantry(SQLiteDatabase db, String name, double weight, String type) { //this method is used to insert several drinks
        ContentValues recipeItemValues = new ContentValues();
        recipeItemValues.put("NAME", name);
        recipeItemValues.put("WEIGHT", weight);
        recipeItemValues.put("TYPE", type);
        db.insert("PANTRY", null, recipeItemValues);
    }


    private static void insertShoppingList(SQLiteDatabase db, int recipe_id, String name, double weight, String type) { //this method is used to insert several drinks
        ContentValues recipeItemValues = new ContentValues();
        recipeItemValues.put("RECIPE_ID", recipe_id);
        recipeItemValues.put("NAME", name);
        recipeItemValues.put("WEIGHT", weight);
        recipeItemValues.put("TYPE", type);
        db.insert("SHOPPINGLIST", null, recipeItemValues);
    }

}

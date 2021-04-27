package com.hfad.plantry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {
    public static final String recipeName = "NAME";
    private SQLiteDatabase db;
    private Cursor cursor;
    private ArrayList <Item> recipeItemsList = new ArrayList<Item>(10);
    private ArrayList <Item> pantryItemsList = new ArrayList<Item>(10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        String NAME = (String) getIntent().getExtras().get(recipeName);

        SQLiteOpenHelper plantryDatabaseHelper = new PlantryDatabaseHelper(this);
        try {
            db = plantryDatabaseHelper.getReadableDatabase();
            cursor = db.query("RECIPE",
                    new String[]{"*"},
                    "NAME = ?",
                    new String[]{NAME}, null, null, null);

            cursor.moveToFirst();
            int _id = cursor.getInt(0);
            String name = cursor.getString(1);
            String steps = cursor.getString(2);
            String level = cursor.getString(3);
            String time = cursor.getString(4);
            int imageId = cursor.getInt(6);//using IMAGE_RESOURCE_ID_LARGE

            TextView recipe_textView = (TextView) findViewById(R.id.recipe_name_textview);
            recipe_textView.setText(name);

            ImageView recipe_imageView = (ImageView) findViewById(R.id.recipe_imageView);
            recipe_imageView.setImageResource(imageId);

            TextView recipe_level = (TextView) findViewById(R.id.recipe_level_textview);
            recipe_level.setText("Level: "+level);

            TextView recipe_time = (TextView)findViewById(R.id.recipe_time_textview);
            recipe_time.setText("Time: "+time);

            TextView recipe_items = (TextView)findViewById(R.id.recipe_items_textview);
            String recipeItems = "Ingredients: \n";

            cursor = db.query("RECIPEITEMS",
                    new String[]{"*"},
                    "RECIPE_ID = ?",
                    new String[]{Integer.toString(_id)}, null, null, null);
            cursor.moveToFirst();
            int count = 0; //this is for adding items to the recipeItem array
            do{
                String tempItemAllFields = "";
                int tempId = cursor.getInt(0);
                String tempName = cursor.getString(1);
                double tempWeight = cursor.getDouble(2);
                String tempType = cursor.getString(3);

                ///recipeItems method to insert it in.
                Item tempItem = new Item(tempId, tempName, tempWeight, tempType);
                recipeItemsList.add(tempItem);
                /////
                if (!tempType.equals("ounces") && !tempType.equals("Eggs")) {
                    if (tempType.equals("teaspoon"))
                        tempItemAllFields += convertOuncesToTeaspoon(tempWeight) + " " + tempType + " of " + tempName + "\n";
                    else
                        tempItemAllFields += convertOuncesToTablespoon(tempWeight) + " " + tempType + " of " + tempName + "\n";
                }//end if
                else {
                    if (tempType.equals("ounces")) {
                        if (tempWeight > 7.9) {
                            tempItemAllFields += convertOuncesToCup(tempWeight) + " " + "cups" + " of " + tempName + "\n";
                        } else {
                            tempItemAllFields += tempWeight + " " + tempType + " of " + tempName + "\n";
                        }
                    }else{
                        tempItemAllFields += tempWeight + " " + tempName + "\n";
                    }
                }//end else
                recipeItems += tempItemAllFields;



            }while(cursor.moveToNext());

            recipe_items.setText(recipeItems);


            //add in recipe directions from db
            TextView recipe_steps = (TextView) findViewById(R.id.recipe_steps_textview);
            int resourceId = this.getResources().getIdentifier(steps, "string", this.getPackageName());
            recipe_steps.setText(getResources().getString(resourceId));

            cursor.close();
            db.close();


        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT); //Display a pop-up message if a SQLiteException is thrown
            toast.show();
        }
    }

    public void addRecipeToShoppingList(View view){
        Button add_recipe_to_shopping_list_button = (Button) findViewById(R.id.add_recipe_to_shopping_list_button);
        add_recipe_to_shopping_list_button.setEnabled(false);

        //get items that user has from the PANTRY table
        SQLiteOpenHelper plantryDatabaseHelper = new PlantryDatabaseHelper(this);
        try {
            db = plantryDatabaseHelper.getReadableDatabase();
            cursor = db.query("PANTRY",
                    new String[]{"*"},
                    null,
                    null, null, null, null);
            if(cursor.moveToFirst()) {
                do {
                    String tempName = cursor.getString(0);
                    double tempWeight = cursor.getDouble(1);
                    String tempType = cursor.getString(2);

                    ///recipeItems method to insert it in.
                    Item tempItem = new Item(tempName, tempWeight, tempType);
                    pantryItemsList.add(tempItem);
                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();
        }catch(Exception w){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT); //Display a pop-up message if a SQLiteException is thrown
            toast.show();
        }
        //

        //compare recipeItemsList with pantryItemsList


        for(int i = 0; i < pantryItemsList.size(); i++){
            boolean got_it = false;
            for(int j = 0; j < recipeItemsList.size(); j++){
                if(pantryItemsList.get(i).getName().toLowerCase().equals(recipeItemsList.get(j).getName().toLowerCase())){
                    System.out.println(pantryItemsList.get(i).getName().toLowerCase());
                    System.out.println(recipeItemsList.get(j).getName().toLowerCase());
                    System.out.println();
                    got_it = true;
                    if(pantryItemsList.get(i).getWeight() <= recipeItemsList.get(j).getWeight()){
                        try {
                            double amount_need_to_buy = recipeItemsList.get(j).getWeight() - pantryItemsList.get(i).getWeight();
                            ContentValues cv = new ContentValues();
                            cv.put("_id",1);
                            cv.put("NAME",pantryItemsList.get(i).getName());
                            cv.put("WEIGHT",amount_need_to_buy);
                            cv.put("TYPE",pantryItemsList.get(i).getType());
                            db = plantryDatabaseHelper.getWritableDatabase();
                            db.insert("SHOPPINGLIST",null,cv);
                            db.close();
                        }
                        catch (Exception e){
                            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT); //Display a pop-up message if a SQLiteException is thrown
                            toast.show();
                        }
                    }
                }

            }
            if(!got_it){
                try {
                    ContentValues cv = new ContentValues();
                    cv.put("_id",1);
                    cv.put("NAME",recipeItemsList.get(i).getName());
                    cv.put("WEIGHT",recipeItemsList.get(i).getWeight());
                    cv.put("TYPE",recipeItemsList.get(i).getType());
                    db = plantryDatabaseHelper.getWritableDatabase();
                    db.insert("SHOPPINGLIST",null,cv);
                    db.close();
                }
                catch (Exception e){
                    Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT); //Display a pop-up message if a SQLiteException is thrown
                    toast.show();
                }
            }


        }



        Intent intent = new Intent(this, ShoppingListActivity.class);
        startActivity(intent); //start new activity
    }



    public String convertOuncesToTablespoon(double ounces) {

        double tablespoons = 0;
        tablespoons = ounces * 2; //convert ounces to tablespoons
        double number = ((int) (tablespoons * 2 + 0.5)) / 2.0; //rounding to the nearest half value.
        String tablespoon = number + ""; //converting the value to a string
        return tablespoon; //returning string value of tablespoons

    }

    public String convertOuncesToTeaspoon(double ounces) {

        double teaspoons = 0;
        teaspoons = ounces * 6; //convert ounces to teaspoons
        double number = ((int) (teaspoons * 2 + 0.5)) / 2.0; //rounding to the nearest half value.
        String teaspoon = number + ""; //converting the value to a string
        return teaspoon; //returning string value of teaspoons

    }

    public String convertOuncesToCup(double ounces) {

        double cups = 0;
        cups = ounces/8; //convert ounces to cups
        double number = ((int) (cups * 2 + 0.5)) / 2.0; //rounding to the nearest half value.
        String cup = number + ""; //converting the value to a string
        return cup; //returning string value of cups

    }

    public void goBack(View view){
        finish();
    }


}
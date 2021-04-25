package com.hfad.plantry;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;
import java.util.Scanner;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SQLiteOpenHelper plantryDatabaseHelper = new PlantryDatabaseHelper(this);
        try {
            db = plantryDatabaseHelper.getReadableDatabase();
            cursor = db.query("RECIPE",
                    new String[]{"*"},
                    null,
                    null, null, null, null);
            Random rng = new Random();
            int randomInt = rng.nextInt(6);
            cursor.moveToPosition(randomInt);

            //insertRecipe(db,"Chicago-Style Deep Dish Pizza with Italian Sausage",String.valueOf(R.string.Deep_Dish),"MEDIUM","02:55:00",R.drawable.chicago_style_deep_dish);
            //int recipeId = cursor.getInt(0);
            //System.out.println(recipeId);
            String name = cursor.getString(1);
            System.out.println(name);
            String steps = cursor.getString(2);
            System.out.println(steps);
            String level = cursor.getString(3);
            System.out.println(level);
            String time = cursor.getString(4);
            System.out.println(time);
            int imageId = cursor.getInt(5);
            System.out.println(imageId);
            TextView recipe_of_the_week_textview = (TextView) findViewById(R.id.recipe_of_the_week_textview);
            recipe_of_the_week_textview.setText(name);

            Button recipe_of_the_week_button = (Button) findViewById(R.id.recipe_of_the_week_button);

            Drawable img = recipe_of_the_week_button.getContext().getResources().getDrawable(imageId);
            recipe_of_the_week_button.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);

            cursor = db.query("RECIPEITEMS",
                    new String[]{"*"},
                    "RECIPE_ID = ?",
                    new String[]{Integer.toString(randomInt)}, null, null, null);
            cursor.moveToFirst();

            String recipe_of_the_week_text = "Ingredients:\n";
            recipe_of_the_week_button.setTextSize(10);


            for (int i = 0; i <= 6; i++) {
                if (i > 0) {
                    cursor.moveToNext();
                }
                recipe_of_the_week_text += "• ";
                String tempName = cursor.getString(1);
                Double tempWeight = cursor.getDouble(2);
                String tempType = cursor.getString(3);
                if (!tempType.equals("ounces")) {
                    if (tempType.equals("teaspoon"))
                        recipe_of_the_week_text += convertOuncesToTeaspoon(tempWeight) + " " + tempType + " of " + tempName + "\n";
                    else
                        recipe_of_the_week_text += convertOuncesToTablespoon(tempWeight) + " " + tempType + " of " + tempName + "\n";
                }//end if
                else {
                    recipe_of_the_week_text += tempWeight + " " + tempType + " of " + tempName + "\n";
                }//end else
            }//end for loop
            recipe_of_the_week_text += "Time : " + time + "\t\t" + "Level: " + level;
            recipe_of_the_week_button.setText(recipe_of_the_week_text);
            cursor.close(); //close cursor
            db.close(); //close the database
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT); //Display a pop-up message if a SQLiteException is thrown
            toast.show();
        }


        //adding in all the data from all recipes


        for (int i = 0; i < 6; i++) {
            plantryDatabaseHelper = new PlantryDatabaseHelper(this);
            try {
                db = plantryDatabaseHelper.getReadableDatabase();
                cursor = db.query("RECIPE",
                        new String[]{"*"},
                        null,
                        null, null, null, null);

                cursor.moveToPosition(i);

                //insertRecipe(db,"Chicago-Style Deep Dish Pizza with Italian Sausage",String.valueOf(R.string.Deep_Dish),"MEDIUM","02:55:00",R.drawable.chicago_style_deep_dish);
                //int recipeId = cursor.getInt(0);
                //System.out.println(recipeId);
                String name = cursor.getString(1);
                System.out.println(name);
                String steps = cursor.getString(2);
                System.out.println(steps);
                String level = cursor.getString(3);
                System.out.println(level);
                String time = cursor.getString(4);
                System.out.println(time);
                int imageId = cursor.getInt(5);
                System.out.println(imageId);
                TextView recipe_textview;
                Button recipe_button;


                switch (i + 1) {
                    case 1:
                        recipe_textview = (TextView) findViewById(R.id.recipe_1_textview);
                        recipe_button = (Button) findViewById(R.id.recipe_1_button);
                        break;
                    case 2:
                        recipe_textview = (TextView) findViewById(R.id.recipe_2_textview);
                        recipe_button = (Button) findViewById(R.id.recipe_2_button);
                        break;
                    case 3:
                        recipe_textview = (TextView) findViewById(R.id.recipe_3_textview);
                        recipe_button = (Button) findViewById(R.id.recipe_3_button);
                        break;
                    case 4:
                        recipe_textview = (TextView) findViewById(R.id.recipe_4_textview);
                        recipe_button = (Button) findViewById(R.id.recipe_4_button);
                        break;
                    case 5:
                        recipe_textview = (TextView) findViewById(R.id.recipe_5_textview);
                        recipe_button = (Button) findViewById(R.id.recipe_5_button);
                        break;
                    case 6:
                        recipe_textview = (TextView) findViewById(R.id.recipe_6_textview);
                        recipe_button = (Button) findViewById(R.id.recipe_6_button);
                        break;
                    default:
                        recipe_textview = (TextView) findViewById(R.id.recipe_1_textview);
                        recipe_button = (Button) findViewById(R.id.recipe_1_button);
                        break;

                }
                recipe_textview.setText(name);

                Drawable img = recipe_button.getContext().getResources().getDrawable(imageId);
                recipe_button.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);

                cursor = db.query("RECIPEITEMS",
                        new String[]{"*"},
                        "RECIPE_ID = ?",
                        new String[]{Integer.toString(i + 1)}, null, null, null);
                cursor.moveToFirst();

                String recipe_text = "Ingredients:\n";
                recipe_button.setTextSize(10);


                for (int j = 0; j <= 6; j++) {
                    if (j > 0) {
                        cursor.moveToNext();
                    }
                    recipe_text += "• ";
                    String tempName = cursor.getString(1);
                    Double tempWeight = cursor.getDouble(2);
                    String tempType = cursor.getString(3);
                    if (!tempType.equals("ounces")) {
                        if (tempType.equals("teaspoon"))
                            recipe_text += convertOuncesToTeaspoon(tempWeight) + " " + tempType + " of " + tempName + "\n";
                        else
                            recipe_text += convertOuncesToTablespoon(tempWeight) + " " + tempType + " of " + tempName + "\n";
                    }//end if
                    else {
                        recipe_text += tempWeight + " " + tempType + " of " + tempName + "\n";
                    }//end else
                }//end for loop
                recipe_text += "Time : " + time + "\t\t" + "Level: " + level;
                recipe_button.setText(recipe_text);
                cursor.close();
                db.close(); //close the database
            } catch (SQLiteException e) {
                Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT); //Display a pop-up message if a SQLiteException is thrown
                toast.show();
            }

        }



        /*plantryDatabaseHelper = new PlantryDatabaseHelper(this);
            db = plantryDatabaseHelper.getReadableDatabase();
            cursor = db.query("RECIPE",
                    new String[]{"*"},
                    null,
                    null, null, null, null);
        */


    }


    public void goToPantry(View view) {
        Intent intent = new Intent(this, PantryActivity.class);
        startActivity(intent); //start new activity
    }

    public void addRecipe(View view) {
        Intent intent = new Intent(this, MakeRecipeActivity.class);
        startActivity(intent); //start new activity
    }
    public void goToShoppingList(View view){
        Intent intent = new Intent(this, ShoppingListActivity.class);
        startActivity(intent); //start new activity
    }

    public void goToRecipe(View view) {
        Intent intent = new Intent(this, RecipeActivity.class);
        String tag = (view.getTag()).toString();
        System.out.println(tag);
        TextView recipe;

        switch (tag) {

            case "0":
                recipe = (TextView) findViewById(R.id.recipe_of_the_week_textview);
                break;
            case "1":
                recipe = (TextView) findViewById(R.id.recipe_1_textview);
                break;
            case "2":
                recipe = (TextView) findViewById(R.id.recipe_2_textview);
                break;
            case "3":
                recipe = (TextView) findViewById(R.id.recipe_3_textview);
                break;
            case "4":
                recipe = (TextView) findViewById(R.id.recipe_4_textview);
                break;
            case "5":
                recipe = (TextView) findViewById(R.id.recipe_5_textview);
                break;
            case "6":
                recipe = (TextView) findViewById(R.id.recipe_6_textview);
                break;
            default:
                recipe = (TextView) findViewById(R.id.recipe_of_the_week_textview);
                break;

        }
        intent.putExtra("NAME", recipe.getText());
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



}
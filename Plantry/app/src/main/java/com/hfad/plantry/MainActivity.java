package com.hfad.plantry;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Scanner;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteOpenHelper plantryDatabaseHelper = new PlantryDatabaseHelper(this);

        db = plantryDatabaseHelper.getReadableDatabase();
        cursor = db.query("RECIPE",
                new String[]{"_id", "*"},
                null,
                null, null, null, null);
    }

    public void goToPantry(View view) {
        Intent intent = new Intent(this, PantryActivity.class);
        startActivity(intent); //start new activity
    }

    public void addRecipe(View view) {
        Intent intent = new Intent(this, MakeRecipeActivity.class);
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
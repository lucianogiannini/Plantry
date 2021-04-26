package com.hfad.plantry;

import androidx.appcompat.app.AppCompatActivity;

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

public class RecipeActivity extends AppCompatActivity {
    public static final String recipeName = "NAME";
    private SQLiteDatabase db;
    private Cursor cursor;

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

            do{
                String tempItemAllFields = "";
                String tempName = cursor.getString(1);
                Double tempWeight = cursor.getDouble(2);
                String tempType = cursor.getString(3);
                if (!tempType.equals("ounces")) {
                    if (tempType.equals("teaspoon"))
                        tempItemAllFields += convertOuncesToTeaspoon(tempWeight) + " " + tempType + " of " + tempName + "\n";
                    else
                        tempItemAllFields += convertOuncesToTablespoon(tempWeight) + " " + tempType + " of " + tempName + "\n";
                }//end if
                else {
                    tempItemAllFields += tempWeight + " " + tempType + " of " + tempName + "\n";
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
}
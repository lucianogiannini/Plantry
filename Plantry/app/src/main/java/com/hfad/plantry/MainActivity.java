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
    public void goToPantry(View view){
        Intent intent = new Intent(this, PantryActivity.class);
        startActivity(intent); //start new activity
    }

    public void addRecipe(View view){
        Intent intent = new Intent(this, MakeRecipeActivity.class);
        startActivity(intent); //start new activity
    }
    public void goToRecipe(View view){
        Intent intent = new Intent(this, RecipeActivity.class);
        TextView recipe_of_the_week = (TextView)findViewById(R.id.recipe_of_the_week_textview);
        intent.putExtra("NAME",recipe_of_the_week.getText());
        startActivity(intent); //start new activity
    }
}
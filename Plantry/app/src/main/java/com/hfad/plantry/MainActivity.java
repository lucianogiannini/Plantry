package com.hfad.plantry;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Scanner;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteOpenHelper plantryDatabaseHelper = new PlantryDatabaseHelper(this);
    }
    public void goToPantry(View view){
        Intent intent = new Intent(this, PantryActivity.class);
        startActivity(intent); //start new activity
    }

    public void addRecipe(View view){
        Intent intent = new Intent(this, RecipeActivity.class);
        startActivity(intent); //start new activity
    }
}
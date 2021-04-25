package com.hfad.plantry;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PantryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);
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
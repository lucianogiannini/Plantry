package com.hfad.plantry;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ItemActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        int _id = (int) getIntent().getExtras().get("_id");

        SQLiteOpenHelper plantryDatabaseHelper = new PlantryDatabaseHelper(this);
        try {
            db = plantryDatabaseHelper.getReadableDatabase();
            cursor = db.query("PANTRY",
                    new String[]{"*"},
                    "_id = ?",
            new String[]{Integer.toString(_id)}, null, null, null);
            if (cursor.moveToFirst()) {
                int tempId = cursor.getInt(0);
                String tempName = cursor.getString(1);
                double tempWeight = cursor.getDouble(2);
                String tempType = cursor.getString(3);

                TextView title_textview = (TextView) findViewById(R.id.item_title);
                title_textview.setText(tempName);


                String weight = "";
                Spinner unit_spinner = (Spinner)findViewById(R.id.unit_spinner);
                int unit = 0;
                if(tempType.equals("ounces"))
                {
                    unit = 0;
                    weight = Double.toString(tempWeight);
                }
                else if(tempType.equals("tablespoon"))
                {
                    unit = 1;
                    weight = convertOuncesToTablespoon(tempWeight);
                }
                else if (tempType.equals("teaspoon"))
                {
                    unit = 2;
                    weight = convertOuncesToTeaspoon(tempWeight);
                }
                else{
                    unit = 3;
                }

                EditText weight_edittext = (EditText)findViewById(R.id.item_weight);
                weight_edittext.setText(weight);

                unit_spinner.setSelection(unit);
            }
        }//end try
        catch(Exception w){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT); //Display a pop-up message if a SQLiteException is thrown
            toast.show();
        }
    }//end on Create

    public String convertOuncesToTablespoon(double ounces) {

        double tablespoons = 0;
        tablespoons = ounces * 2; //convert ounces to tablespoons
        double number = ((int) (tablespoons * 2 + 0.5)) / 2.0; //rounding to the nearest half value.
        String tablespoon = number + ""; //converting the value to a string
        return tablespoon; //returning string value of tablespoons

    }
    public double convertTableSpoonToOunces(double tablespoons) {

        double ounces = 0;
        ounces = tablespoons / 2; //convert ounces to tablespoons
        ounces = Math.round(ounces * 100.0) / 100.0;

        return ounces; //returning string value of tablespoons

    }

    public String convertOuncesToTeaspoon(double ounces) {

        double teaspoons = 0;
        teaspoons = ounces * 6; //convert ounces to teaspoons
        double number = ((int) (teaspoons * 2 + 0.5)) / 2.0; //rounding to the nearest half value.
        String teaspoon = number + ""; //converting the value to a string
        return teaspoon; //returning string value of teaspoons

    }
    public double convertTeaspoonToOunces(double teaspoons) {

        double ounces = 0;
        ounces = teaspoons / 6; //convert ounces to teaspoons
        ounces = Math.round(ounces * 100.0) / 100.0;
        return ounces; //returning string value of teaspoons

    }

    public String convertOuncesToCup(double ounces) {

        double cups = 0;
        cups = ounces/8; //convert ounces to cups
        double number = ((int) (cups * 2 + 0.5)) / 2.0; //rounding to the nearest half value.
        String cup = number + ""; //converting the value to a string
        return cup; //returning string value of cups

    }
    public double convertCupToOunces(double cups) {

        double ounces = 0;
        ounces = cups*8; //convert ounces to cups
        ounces = Math.round(ounces * 100.0) / 100.0;
        return ounces; //returning string value of cups

    }
}
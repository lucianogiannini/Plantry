package com.hfad.plantry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddItemActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
    }

    public void addItem(View view){

        EditText name_editText = (EditText)findViewById(R.id.item_title);
        String name = String.valueOf(name_editText.getText());

        EditText weight_editText = (EditText)findViewById(R.id.item_weight);
        String weight_string = String.valueOf(weight_editText.getText());
        double weight_ = Double.parseDouble(weight_string);

        Spinner unit_spinner = (Spinner)findViewById(R.id.unit_spinner);
        String unit = unit_spinner.getSelectedItem().toString();

        SQLiteOpenHelper plantryDatabaseHelper = new PlantryDatabaseHelper(this);
        try {
            if(unit.equals("tablespoon"))
            {

                weight_ = convertTableSpoonToOunces(weight_);
            }
            else if (unit.equals("teaspoon"))
            {

                weight_ = convertTeaspoonToOunces(weight_);
            }
            ContentValues cv = new ContentValues();
            cv.put("NAME",name);
            cv.put("WEIGHT",weight_);
            cv.put("TYPE",unit);
            db = plantryDatabaseHelper.getWritableDatabase();
            db.insert("PANTRY",null,cv);
            db.close();
        }//end try
        catch(Exception e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT); //Display a pop-up message if a SQLiteException is thrown
            toast.show();
        }

        Intent intent = new Intent(AddItemActivity.this,PantryActivity.class);

        startActivity(intent);

    }



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
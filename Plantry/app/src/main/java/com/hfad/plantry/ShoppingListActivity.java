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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ShoppingListActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        TextView shopping_list_textview = (TextView) findViewById(R.id.shopping_list_editText);
        String shopping_list_text = "";

        SQLiteOpenHelper plantryDatabaseHelper = new PlantryDatabaseHelper(this);
        try {
            db = plantryDatabaseHelper.getReadableDatabase();
            cursor = db.query("SHOPPINGLIST",
                    new String[]{"*"},
                    null,
                    null, null, null, null);
            if(cursor.moveToFirst()) {

                do {
                    String tempItemAllFields = "";
                    int tempId = cursor.getInt(0);
                    String tempName = cursor.getString(1);
                    double tempWeight = cursor.getDouble(2);
                    String tempType = cursor.getString(3);

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
                        } else {
                            tempItemAllFields += tempWeight + " " + tempName + "\n";
                        }
                    }//end else
                    shopping_list_text += tempItemAllFields;


                } while (cursor.moveToNext());
            }
            shopping_list_textview.setText(shopping_list_text);

            cursor.close();
            db.close();


        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT); //Display a pop-up message if a SQLiteException is thrown
            toast.show();
        }
    }

    public void goBackToHome(View view){

        EditText list = (EditText)findViewById(R.id.shopping_list_editText);
        String list_string = String.valueOf(list.getText());
        String [] ingreds = list_string.split("\n");
        SQLiteOpenHelper plantryDatabaseHelper = new PlantryDatabaseHelper(this);
        try {
            db = plantryDatabaseHelper.getWritableDatabase();
            db.delete("SHOPPINGLIST", "_id = ?", new String[]{Integer.toString(1)});
            db.close();
            for(int i = 0; i < ingreds.length; i++){
                String [] temp = ingreds[i].split(" ");
                String weight = temp[0];
                String type = temp[1];
                String name = "Eggs";
                if(!type.equals("Eggs")){
                    name = "";
                    for(int j = 3; j < temp.length; j++){
                        if(j == 3){
                            name += temp[j];
                        }
                        else{
                            name += " " + temp[j];
                        }
                    }
                }
                double weight_ = Double.parseDouble(weight);
                if(type.equals("teaspoon")){
                    weight_ = convertTeaspoonToOunces(weight_);
                }
                else if(type.equals("tablespoon")){
                    weight_ = convertTableSpoonToOunces(weight_);
                }

                try {
                    ContentValues cv = new ContentValues();
                    cv.put("_id",1);
                    cv.put("NAME",name);
                    cv.put("WEIGHT",weight_);
                    cv.put("TYPE",type);
                    db = plantryDatabaseHelper.getWritableDatabase();
                    db.insert("SHOPPINGLIST",null,cv);
                    db.close();
                }
                catch (Exception e){
                    Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT); //Display a pop-up message if a SQLiteException is thrown
                    toast.show();
                }
            }
        }catch (Exception e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT); //Display a pop-up message if a SQLiteException is thrown
            toast.show();

        }


        finish();
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
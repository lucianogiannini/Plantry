package com.hfad.plantry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PantryActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);


        ListView lv = (ListView) findViewById(R.id.pantry_listview);

        // Instanciating an array list (you don't need to do this,
        // you already have yours).
        List<String> your_pantry_list = new ArrayList<String>();

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.


        //db stuff

        SQLiteOpenHelper plantryDatabaseHelper = new PlantryDatabaseHelper(this);
        try {
            db = plantryDatabaseHelper.getReadableDatabase();
            cursor = db.query("PANTRY",
                    new String[]{"*"},
                    null,
                    null, null, null, null);
            if(cursor.moveToFirst()) {

                do {
                    String tempItemAllFields = "";

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
                    }//end else'
                    System.out.println(tempItemAllFields);
                    your_pantry_list.add(tempItemAllFields);


                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();


        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT); //Display a pop-up message if a SQLiteException is thrown
            toast.show();
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                your_pantry_list );

        lv.setAdapter(arrayAdapter);
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener(){ //creates the listener
            public void onItemClick(AdapterView<?> listView, //list view that was used
                                    View itemView,//item that was clicked
                                    int position,//postition in the list
                                    long id) {//and the row ID

                    Intent intent = new Intent(PantryActivity.this, ItemActivity.class);
                    intent.putExtra("_id",position+1);
                    startActivity(intent);

            }
        };

        //Add the listener to the list view

        lv.setOnItemClickListener(itemClickListener); //adds the listener to the list view

    }

    public void goBack(View view){
        Intent intent = new Intent(PantryActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void addItem(View view){
        Intent intent = new Intent(PantryActivity.this, AddItemActivity.class);
        startActivity(intent);

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
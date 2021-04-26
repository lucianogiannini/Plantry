import java.util.*;
public class Item {
    private int recipe_id;
    private String name;
    private double weight;
    private String type;

    public Item(){
        recipe_id = 0;
        name = "";
        weight = 0.0;
        type = "";
    }
    public Item(int recipe_id, String name, double weight, String tpye){
        this.recipe_id = recipe_id;
        this.name = name;
        this.weight = weight;
        this.type = type;
    }




    public int getRecipeId(){
        return this.recipe_id;
    }
    public String getName(){
        return this.name;
    }
    public double getWeight(){
        return this.weight;
    }
    public String getType(){
        return this.type;
    }
    public void setRecipeId(int recipe_id){
        this.recipe_id = recipe_id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setWeight(double weight){
        this.weight = weight;
    }
    public void setType(String type){
        this.type = type;
    }


}

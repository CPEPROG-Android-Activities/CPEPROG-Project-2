/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team_bam.virtualchef;

import java.sql.*;
import java.util.*;

/**
 * This program will Write a recipe selected based on the input of a
 * user.
 */
public class RecipeWriter {
    private static Scanner input = new Scanner(System.in);

    /**
     * This method creates the name for the ingredients column in the database,
     * the name of this ingredients will create/link the new ingredients table
     * of a certain recipe.
     * @param recipeTitle The title of the recipe which will be formatted to
     * become the name of the ingredients table, and the link to the MainIndex.
     * @return Name of the ingredients table of this recipe.
     */
    public String ingredientsName(String recipeTitle){
        if(recipeTitle.contains(" ")){
            recipeTitle = recipeTitle.replace(" ", "_").concat("_ing").toLowerCase();
        }else{
            recipeTitle = recipeTitle.concat("_ing").toLowerCase();
        }
        return recipeTitle;
    }

    /**
     * This method creates the name for the steps column in the database,
     * the name of this ingredients will create/link the new ingredients table
     * of a certain recipe.
     * @param recipeTitle The title of the recipe which will be formatted to
     * become the name of the steps table, and the link to the MainIndex.
     * @return Name of the steps table of this recipe.
     */
    public String stepsName(String recipeTitle){
        if(recipeTitle.contains(" ")){
            recipeTitle = recipeTitle.replace(" ", "_").concat("_steps").toLowerCase();
        }else{
            recipeTitle = recipeTitle.concat("_steps").toLowerCase();
        }
        return recipeTitle;
    }
    public static void main(String[] args)throws SQLException{
        //Checking of Connection.
        /*String url="jdbc:mysql://localhost:3306/recipes";
        Properties prop=new Properties();
        prop.setProperty("user","root");
        prop.setProperty("password","");
        Driver d=new com.mysql.jdbc.Driver();
        Connection con = d.connect(url,prop);
        if(con==null)   {
            System.out.println("connection failed");
            return;
        }else{
            System.out.println("Connected.");
        }
        Statement stat = con.createStatement();
        RecipeWriter recipeWriter = new RecipeWriter();
        //Writing the Recipe
        String recipeTitle;
        String recipeType;
        String servingSize;
        String ingredients;
        String steps;
        
        System.out.print("Recipe Title: ");
        recipeTitle = input.nextLine();
        
        System.out.print("Recipe Type<Appetizer, Main Course, Dessert>: ");
        recipeType = input.nextLine();
        
        System.out.print("Recipe Serving Size: ");
        servingSize = input.nextLine();
        
        ingredients = recipeWriter.ingredientsName(recipeTitle);
        System.out.println(ingredients);
        
        steps = recipeWriter.stepsName(recipeTitle);
        System.out.println(steps);
        
        stat.execute("insert into MainIndex(`Recipe Title`,`Recipe Type`,`Serving "
                + "Size`,`Ingredients`,`Steps`)\n"
                + "values(\'"+recipeTitle+"\',\'"+recipeType+"\',\'"+servingSize
                +"\',\'"+ingredients+"\',\'"+steps+"\')");
        stat.execute("create table "+ingredients+"(\n"
                + "Ingredient varchar(80) not null primary key,\n"
                + "`Measurement Type` varchar(20) not null,\n"
                + "`Measurement Size` varchar(10) not null)");
        stat.execute("create table "+steps+"(\n"
                + "`Step Number` int not null primary key auto_increment,\n"
                + "`Content` blob not null)\n");
        
        Boolean stop = false;
        while(!stop){
            System.out.println("-----------------Ingredients-----------------");
            System.out.print("Ingredient: ");
            String ingredient = input.nextLine();
            
            System.out.print("Measurement Type: ");
            String measurementType = input.nextLine();
            
            System.out.print("Measurement Size: ");
            String measurementSize = input.nextLine();
            
            stat.execute("insert into "+ingredients+"(`Ingredient`,`Measurement"
                    + " Type`,`Measurement Size`)\n"
                    + "values(\'"+ingredient+"\',\'"+measurementType+"\',\'"
                    +measurementSize+"\')");
            String choice;
            System.out.print("Do you have another Ingredient to add<y/n>? ");
            choice = input.nextLine();
            if(choice.equals("n")){
                stop = true;
            }
        }
        stop = false;
        int stepsCount = 1;
        while(!stop){
            System.out.println("-----------------steps-----------------");
            System.out.print(stepsCount+"(state the procedure): ");
            String content = input.nextLine();
            
            stat.execute("insert into "+steps+"(`Step Number`,`Content`)\n"
                    + "values(\'"+stepsCount+"\',\'"+content+"\')");
            String choice;
            System.out.print("Do you have another steps to add<y/n>? ");
            choice = input.nextLine();
            if(choice.equals("n")){
                stop = true;
            }
            stepsCount++;
        }*/
    }
}

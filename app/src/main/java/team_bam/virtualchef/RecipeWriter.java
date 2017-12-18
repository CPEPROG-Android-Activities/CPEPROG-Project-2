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

}

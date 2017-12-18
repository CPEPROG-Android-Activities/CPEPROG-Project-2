package team_bam.virtualchef;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DisplayRecipeActivity extends AppCompatActivity {
    TextView displayRecipeName, displayRecipeType, displayServingSize;
    TextView displayIngredients, displayProcedure;
    String recipeName="", recipeType="", servingSize="", ingred="", steps="";
    String fullIngredients = "";
    String fullProcedure = "";
    Button search, home;
    ProgressDialog progressDialog;
    ConnectionClass connectionClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_recipe);

        //Initialize elements used.
        displayRecipeName = findViewById(R.id.txt_displayRecipeName);
        displayRecipeType = findViewById(R.id.txt_displayRecipeType);
        displayServingSize = findViewById(R.id.txt_displayServingSize);
        displayIngredients = findViewById(R.id.txt_displayIngredients);
        displayProcedure = findViewById(R.id.txt_displayProcedures);
        search = findViewById(R.id.btn_search);
        home = findViewById(R.id.btn_home);

        //Get data from search
        Intent intent = getIntent();
        Bundle bun = intent.getExtras();
        recipeName = bun.getString("recipeTitle");
        recipeType = bun.getString("recipeType");
        servingSize = bun.getString("servingSize");
        ingred = bun.getString("ingredients");
        steps = bun.getString("steps");

        //Display
        displayRecipeName.setText(recipeName);
        displayRecipeType.setText(recipeType);
        displayServingSize.setText(servingSize);

        //Initialize background components.
        connectionClass = new ConnectionClass();
        progressDialog = new ProgressDialog(this);

        Doregister doregister = new Doregister();
        doregister.execute("");

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullIngredients = "";
                fullProcedure = "";
                Intent in = new Intent(DisplayRecipeActivity.this,SearchActivity.class);
                startActivity(in);
                finish();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(DisplayRecipeActivity.this, MainActivity.class);
                startActivity(in);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        displayProcedure.setText(fullProcedure);
        displayIngredients.setText(fullIngredients);
    }

    private class Doregister extends AsyncTask<String,String,String> {
        String z = "";
        boolean isSuccess = false;

        @Override
        protected void onPreExecute(){
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... strings) {
                try {
                    Connection con = connectionClass.CONN();
                    if(con == null){
                        z = "check internet connection";
                        Toast.makeText(getBaseContext(),z,Toast.LENGTH_LONG).show();
                    }else{
                            //SQL Commands Start here
                            String query = "Select * from `" + ingred + "`;";
                            Statement state = con.createStatement();
                            ResultSet ingredientsBreakdown = state.executeQuery(query);
                            while (ingredientsBreakdown.next()) {
                                fullIngredients += "\n"
                                        +ingredientsBreakdown.getString(2) + "\t"
                                        + ingredientsBreakdown.getString(3) + "\t"
                                        + ingredientsBreakdown.getString(1);
                                System.out.println(fullIngredients);
                            }
                            ResultSet stepsBreakdoawn = state.executeQuery("Select * from "
                                    + steps);
                            while (stepsBreakdoawn.next()) {
                                fullProcedure+="\n"
                                        +stepsBreakdoawn.getInt(1) + "\t"
                                        + stepsBreakdoawn.getString(2);

                                System.out.println(fullProcedure);
                            }
                            z = "Register Successful";
                            isSuccess = true;
                    }
                }catch (Exception ex){
                    isSuccess = false;
                    z = "Exceptions: " + ex;

                }

            return z;
        }

        @Override
        protected void onPostExecute(String s){
            if (isSuccess){
                Toast.makeText(getBaseContext(), z,Toast.LENGTH_LONG).show();
                displayIngredients.setText(fullIngredients);
                displayProcedure.setText(fullProcedure);
            }
            progressDialog.hide();
        }
    }
}

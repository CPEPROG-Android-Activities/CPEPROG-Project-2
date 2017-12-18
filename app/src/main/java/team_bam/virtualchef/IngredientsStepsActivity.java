package team_bam.virtualchef;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class IngredientsStepsActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_INGREDIENTS = 464;
    public static final int REQUEST_CODE_PROCEDURE = 7837;
    private Button addIngredients, addProcedures, save;
    private TextView ingredients, procedures, recipeTitle, recipeType;
    private String displayIngredients = "";
    private String displayProcedures = "";
    private String tableNameIngredients = "";
    private String tableNameProcedure = "";
    public int procedureCount=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_steps);
        //Initialize all elements used.
        recipeTitle = findViewById(R.id.txt_showRecipeName);
        recipeType = findViewById(R.id.txt_showRecipeType);
        ingredients = findViewById(R.id.txt_showIngredients);
        procedures = findViewById(R.id.txt_showProcedures);
        addIngredients = findViewById(R.id.btn_addIngredient);
        addProcedures = findViewById(R.id.btn_addProcedure);
        save = findViewById(R.id.btn_save);

        //Gather data from CreateActivity.java.
        final Bundle bun = getIntent().getExtras();
        recipeTitle.setText(bun.getString("recipeTitle"));
        recipeType.setText(bun.getString("recipeType"));
        tableNameIngredients = bun.getString("ingredients");
        tableNameProcedure = bun.getString("steps");

        //Button Commands
        addIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Display
                Toast.makeText(getBaseContext(),"Ingredient",Toast.LENGTH_SHORT).show();

                //Initializing of adding an ingredient
                Intent in = new Intent(IngredientsStepsActivity.this,AddIngredientsActivity.class);
                in.putExtra("ingredientsTable",tableNameIngredients);
                startActivityForResult(in, REQUEST_CODE_INGREDIENTS);
            }
        });

        addProcedures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Display
                Toast.makeText(getBaseContext(),"Procedure",Toast.LENGTH_SHORT).show();

                //Initializing of adding an ingredient
                Intent in = new Intent(IngredientsStepsActivity.this,AddStepsActivity.class);
                in.putExtra("stepsTable",tableNameProcedure);
                in.putExtra("procedureCount",procedureCount);
                startActivityForResult(in, REQUEST_CODE_PROCEDURE);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(),"Recipe Saved",Toast.LENGTH_LONG).show();

                Intent in = new Intent(IngredientsStepsActivity.this,SearchActivity.class);
                in.putExtra("recipeTitle",bun.getString("recipeTitle"));
                in.putExtra("recipeType",bun.getString("recipeType"));
                in.putExtra("servingSize",bun.getString("servingSize"));
                in.putExtra("ingredients",bun.getString("ingredients"));
                startActivity(in);
                finish();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_INGREDIENTS) {
            if (resultCode == Activity.RESULT_OK) {
                if (ingredients.equals("Ingredients Display Here")) {
                    ingredients.setText("");
                }
                //Gather data returned.
                Bundle bun = data.getExtras();
                String name = bun.getString("name");
                String measure = bun.getString("measure");
                String type = bun.getString("type");

                //Display ingredients to scrollview.
                displayIngredients += measure + "\t" + type + "\t" + name + "\n";
                System.out.println(displayIngredients);
                ingredients.setText(displayIngredients);
            }
        }else if(requestCode==REQUEST_CODE_PROCEDURE){
                if(resultCode==Activity.RESULT_OK){
                    if(ingredients.equals("Procedures Display Here")){
                        ingredients.setText("");
                    }
                    //Gather data returned.
                    String procedure = data.getStringExtra("step");
                    displayProcedures += ""+String.valueOf(procedureCount)+"\t"+procedure+"\n";
                    procedures.setText(displayProcedures);
                    procedureCount++;
                }
        }
    }
}

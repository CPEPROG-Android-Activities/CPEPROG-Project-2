package team_bam.virtualchef;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Statement;

public class AddIngredientsActivity extends AppCompatActivity {
    EditText ingredientName, measurement, measurementType;
    Button addIngredient;

    //Background components.
    ProgressDialog progressDialog;
    ConnectionClass connectionClass;

    //Gathering ingredients table name of recipe.
    String ingredientsTableName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredients);
        //Initialize the elements used.
        ingredientName = findViewById(R.id.et_ingredientName);
        measurement = findViewById(R.id.et_measurement);
        measurementType = findViewById(R.id.et_measurementType);
        addIngredient = findViewById(R.id.btn_returnIngredientValue);
        Intent in = getIntent();
        ingredientsTableName = in.getStringExtra("ingredientsTable");

        //Initialize background components.
        connectionClass = new ConnectionClass();
        progressDialog = new ProgressDialog(this);

        //Button Commands
        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Gather Data from user
                String name = ingredientName.getText().toString();
                String measure = measurement.getText().toString();
                String type = measurementType.getText().toString();


                Bundle bun = new Bundle();
                Intent intent = new Intent();

                //Gather Data to return it to the previous screen
                bun.putString("name",name);
                bun.putString("measure",measure);
                bun.putString("type",type);
                intent.putExtras(bun);
                setResult(Activity.RESULT_OK,intent);
                Doregister doregister = new Doregister();
                doregister.execute("");
                finish();
            }
        });

    }

    private class Doregister extends AsyncTask<String,String,String> {
        String z = "";
        boolean isSuccess = false;

        //Gather Data from user
        String name = ingredientName.getText().toString();
        String measure = measurement.getText().toString();
        String type = measurementType.getText().toString();

        //Initialize data recovery elements
        Intent in = new Intent();
        Bundle bun = new Bundle();
        @Override
        protected void onPreExecute(){
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... strings) {
            if(name.trim().equals("")||measure.trim().equals("")||type.trim().equals("")){
                z = "Please Enter all Fields...";
            }else{
                try {
                    Connection con = connectionClass.CONN();
                    if(con == null){
                        z = "check internet connection";
                        Toast.makeText(getBaseContext(),z,Toast.LENGTH_LONG).show();
                    }else{
                        //SQL Commands Start here
                        String query = "insert into `"+ingredientsTableName+"`(`Ingredient`,`Measurement Type`,`Measurement"
                                +" Size`) "
                                +"values(\'"+name+"\',\'"+measure+"\',\'"+type+"\');";
                        Statement state = con.createStatement();
                        state.execute(query);
                        z = "Input Successful";
                        isSuccess = true;
                    }
                }catch (Exception ex){
                    isSuccess = false;
                    z = "Exceptions: " + ex;

                }
            }
            return z;
        }

        @Override
        protected void onPostExecute(String s){
            if (isSuccess){
                Toast.makeText(getBaseContext(), z,Toast.LENGTH_LONG).show();
            }
            progressDialog.hide();
        }
    }
}

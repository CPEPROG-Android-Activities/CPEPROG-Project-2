package team_bam.virtualchef;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SearchActivity extends AppCompatActivity {
    EditText recipeTitle;
    Button search;
    ProgressDialog progressDialog;
    ConnectionClass connectionClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Initialize the elements.
        recipeTitle = findViewById(R.id.et_titlesearch);
        search = findViewById(R.id.btn_searchrecipe);

        //Initialize background components.
        connectionClass = new ConnectionClass();
        progressDialog = new ProgressDialog(this);

        //Set Button Commands.
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Doregister doregister = new Doregister();
                doregister.execute("");
            }
        });
    }
    private class Doregister extends AsyncTask<String,String,String> {
        RecipeWriter writer = new RecipeWriter();
        String title = recipeTitle.getText().toString();
        String z = "";
        String recipeType = "";
        String servingSize = "";
        String ingredients = "";
        String steps = "";
        Bundle bundle = new Bundle();
        boolean isSuccess = false;

        @Override
        protected void onPreExecute(){
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... strings) {
            if(title.trim().equals("")){
                z = "Please Enter all Fields...";
            }else{
                try {
                    Connection con = connectionClass.CONN();
                    if(con == null){
                        z = "check internet connection";
                        Toast.makeText(getBaseContext(),z,Toast.LENGTH_LONG).show();
                    }else{
                        String query = "Select * from MainIndex where `Recipe Title` = \'"
                                +title+"\';";
                        Statement state = con.createStatement();
                        ResultSet result = state.executeQuery(query);
                        if(result.next()){
                            recipeType = result.getString(2);
                            servingSize = result.getString(3);
                            ingredients = result.getString(4);
                            steps = result.getString(5);
                            bundle.putString("recipeTitle",title);
                            bundle.putString("recipeType",recipeType);
                            bundle.putString("servingSize",servingSize);
                            bundle.putString("ingredients",ingredients);
                            bundle.putString("steps",steps);
                            z = "Search Successful";
                            System.out.println(title);
                            System.out.println("Type: " + recipeType);
                            System.out.println("Serving Size: " + servingSize);
                        }else{
                            z = "Recipe is not in database";
                        }
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
                Intent in = new Intent(SearchActivity.this,DisplayRecipeActivity.class);
                in.putExtras(bundle);
                startActivity(in);
            }
            progressDialog.hide();
        }
    }
}

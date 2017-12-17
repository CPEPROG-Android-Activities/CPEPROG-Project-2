package team_bam.virtualchef;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {//use logCat() for convenient debugging (like s.out.println) [then check Logcat in Android Studio]
    EditText recipeTitle, recipeType, servingSize;
    Button register;
    ProgressDialog progressDialog;
    ConnectionClass connectionClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        recipeTitle = findViewById(R.id.et_title);
        recipeType = findViewById(R.id.et_type);
        servingSize = findViewById(R.id.et_servingsize);
        register = findViewById(R.id.btn_register);

        connectionClass = new ConnectionClass();

        progressDialog = new ProgressDialog(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Doregister doregister = new Doregister();
                doregister.execute("");
            }
        });
    }

    public class Doregister extends AsyncTask<String,String,String>{
        RecipeWriter writer = new RecipeWriter();
        String title = recipeTitle.getText().toString();
        String type = recipeType.getText().toString();
        String size = servingSize.getText().toString();
        String ingredients = writer.ingredientsName(title);
        String steps = writer.stepsName(title);
        String z = "";
        boolean isSuccess = false;

        @Override
        protected void onPreExecute(){
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... strings) {
            if(title.trim().equals("") || type.trim().equals("") || size.trim().equals("")){
                z = "Please Enter all Fields...";
            }else{
                try {
                    Connection con = connectionClass.CONN();
                    if(con == null){
                        z = "check internet connection";
                        //no toast for ya://java.lang.RuntimeException: Can't toast on a thread that has not called Looper.prepare()
//                        Toast.makeText(getBaseContext(),z,Toast.LENGTH_LONG).show();
                        logCat(z);
                    }else{
                        String query = "insert into MainIndex(`Recipe Title`,`Recipe Type`,`Serving"
                                +" Size`,`Ingredients`,`Steps`)"
                                +"values(\'"+title+"\',\'"+type+"\',\'"+size+"\',\'"+ingredients
                                +"\',\'"+steps+"\')";
                        Statement state = con.createStatement();
                        state.executeUpdate(query);
                        z = "Register Successful";
                        isSuccess = true;
                    }
                }catch (Exception ex){
                    isSuccess = false;
                    z = "Exceptions: " + ex;
                    logCat(z,ex);
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

    static void logCat(String msg){
        Log.e("Author Debug",msg);
    }
    static void logCat(String msg,Throwable error){
        Log.e("Author Debug",msg,error);
    }
}

package team_bam.virtualchef;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Statement;

public class AddStepsActivity extends AppCompatActivity {
    TextView procedureNumber;
    int stepNumber = 0;
    EditText procedure;
    Button addProcedure;

    //Background components.
    ProgressDialog progressDialog;
    ConnectionClass connectionClass;

    String stepsTableName;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_steps);

        //Initialize the elements used.
        procedure = findViewById(R.id.et_procedure);
        procedureNumber = findViewById(R.id.txt_procedureNumber);
        addProcedure = findViewById(R.id.btn_returnStepValue);
        stepNumber = getIntent().getIntExtra("procedureCount",0);
        procedureNumber.setText("Step "+String.valueOf(stepNumber));

        stepsTableName = getIntent().getStringExtra("stepsTable");


        //Initialize background components.
        connectionClass = new ConnectionClass();
        progressDialog = new ProgressDialog(this);

        //Button Commands
        addProcedure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Gather Data from user
                String step = procedure.getText().toString();
                //Initialize data recovery elements
                Intent in = new Intent();
                in.putExtra("step",step);
                setResult(Activity.RESULT_OK,in);

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
        String step = procedure.getText().toString();


        @Override
        protected void onPreExecute(){
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... strings) {
            if(step.trim().equals("")){
                z = "Please Enter all Fields...";
            }else{
                try {
                    Connection con = connectionClass.CONN();
                    if(con == null){
                        z = "check internet connection";
                        Toast.makeText(getBaseContext(),z,Toast.LENGTH_LONG).show();
                    }else{
                        String query = "insert into `"+stepsTableName+"`(`Content`) "
                                +"values(\'"+step+"\');";
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

    public void number(int num){
        stepNumber = num;
    }
}

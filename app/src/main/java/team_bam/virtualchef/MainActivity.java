package team_bam.virtualchef;

import android.app.ProgressDialog;
import android.content.Intent;
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
    Button search, create;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search = findViewById(R.id.btn_choosesearch);
        create = findViewById(R.id.btn_choosecreate);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(in);
                finish();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this, CreateActivity.class);
                startActivity(in);
                finish();
            }
        });
    }

    static void logCat(String msg){
        Log.e("Author Debug",msg);
    }
    static void logCat(String msg,Throwable error){
        Log.e("Author Debug",msg,error);
    }
}

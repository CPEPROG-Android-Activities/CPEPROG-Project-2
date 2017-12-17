package team_bam.virtualchef;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created for the VIRTUAL CHEF DATABASE.
 */

public class ConnectionClass {
    String clas = "com.mysql.jdbc.Driver";
    //IP addresses: [Toph:192.168.1.4],[Pat:192.168.0.158]
    String url = "jdbc:mysql://192.168.0.158/recipes";//change numbers to your IP for the meantime (cmd>ipconfig>IPv4 address)
    //TODO @Neil,Toph: please create account (user:team_bam, no password) with all privileges on phpMyAdmin so we don't need to change every now and then
    String userName = "team_bam"; //Username and Passcode is needed because it does not synce of "root" is the one used
    String password = "";

    @SuppressLint("NewApi")
    public Connection CONN(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String conURL = null;
        try{
            Class.forName(clas);

            conn = DriverManager.getConnection(url,userName,password);
            System.out.println("Connected");
        }catch (SQLException se){
            Log.e("ERROR",se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERROR",e.getMessage());
        }catch(Exception e){
            Log.e("ERROR",e.getMessage());
        }
        return conn;
    }
}

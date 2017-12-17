package team_bam.virtualchef;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created for the VIRTUAL CHEF DATABASE.
 */

public class ConnectionClass {
    private String clas = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://192.168.1.4/recipes";
    private String userName = "Toph"; //Username and Passcode is needed because it does not synce of "root" is the one used
    private String password = "11514";

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

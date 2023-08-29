/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;
import java.sql.*;

/**
 *
 * @author ar
 */
public class DBHelper {
    private static Connection conn;
    public static void connect(){
        if(conn == null){
            try {
                String url = "jdbc:mysql://localhost:3306/sistemakademik";
                String user = "root";
                String password = "";
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                conn = DriverManager.getConnection(url, user, password);
                System.out.println(conn);
                 
            } catch (SQLException e) {
                System.out.println("Error koneksi");
            }
        }
    }
    
    public static int insertQueryGetId(String query){
        connect();
        int num = 0;
        int result = -1;
        try {
            Statement stmt = conn.createStatement();
            num = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            
            ResultSet rs = stmt.getGeneratedKeys();
            
            if(rs.next()) result = rs.getInt(1);
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            result = -1;
        }
        return result;
    }
    
    public static boolean executeQuery(String query){ 
        connect(); 
        boolean result = false; 
         
        try{ 
            Statement stmt = conn.createStatement(); 
            stmt.executeUpdate(query);
            result = true;
            stmt.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    
      
    public static ResultSet selectQuery(String query){ 
        connect(); 
        ResultSet rs = null; 
         
        try{ 
            Statement stmt = conn.createStatement(); 
            rs = stmt.executeQuery(query); 
        } 
        catch (Exception e){
            e.printStackTrace(); 
        }
         
        return rs; 
    }
}

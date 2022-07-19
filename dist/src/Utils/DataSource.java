/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sanabenfadhel
 */
public class DataSource {

    private static DataSource data;
    private Connection con;
    
    private final String URL = "jdbc:mysql://localhost/amen";
    private final String LOGIN = "root";
    private final String PASSWORD = "";

    private DataSource() {
        try {
            con = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            System.out.println("Connecting !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public static DataSource getInstance() {
        if (data == null) {
            data = new DataSource();
        }
        return data;
    }

    public Connection getConnection() {
        return con;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sppfatahillah;
import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author Dhimas
 */
public class Koneksi {
    Connection con;
    String user = "root";
    String pass = "";
    String url = "jdbc:MYSQL://localhost:3306/pembayaran";
    
    public void config() {
        try{
            Class.forName("org.gjt.mm.mysql.Driver");
            con = DriverManager.getConnection(url,user,pass);
            System.out.println("Database konek");
        }catch (Exception e) {
            System.out.println("Database Tidak Konek" +e.getMessage());
        }
    } 
}

